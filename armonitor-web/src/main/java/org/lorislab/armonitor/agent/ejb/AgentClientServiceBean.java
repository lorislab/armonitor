/*
 * Copyright 2014 lorislab.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lorislab.armonitor.agent.ejb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.agent.rs.model.Request;
import org.lorislab.armonitor.agent.rs.model.Version;
import org.lorislab.armonitor.agent.rs.service.VersionService;
import org.lorislab.armonitor.store.model.StoreAgent;
import org.lorislab.armonitor.store.model.StoreAgentType;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreBuildParameter;
import org.lorislab.armonitor.store.model.StoreBuildParameterType;
import org.lorislab.armonitor.util.RestClient;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AgentClientServiceBean {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(AgentClientServiceBean.class.getName());

    private static final String AGENT_SERVICE = "/armonitor-agent/rs";

    public List<StoreBuild> getAppBuilds(StoreAgent agent) {
        List<StoreBuild> result = new ArrayList<>();

        try {
            VersionService service = createClientService(agent);
            Request request = new Request();
            request.manifest = true;
            request.service = agent.getService();
            request.uid = UUID.randomUUID().toString();

            List<Version> versions = service.getAllVersion(request);
            if (versions != null) {

                for (Version version : versions) {
                    StoreBuild tmp = map(version);
                    if (tmp != null) {
                        result.add(tmp);
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error reading all application versions for the agent " + agent.getGuid(), ex);
        }
        return result;
    }

    public StoreBuild getAgentBuild(StoreAgent agent) {
        StoreBuild result = null;

        try {
            VersionService service = createClientService(agent);
            Request request = new Request();
            request.manifest = true;
            request.service = agent.getService();
            request.uid = UUID.randomUUID().toString();

            Version version = service.getAgentVersion(request);
            result = map(version);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error reading the version of the agent " + agent.getGuid(), ex);
        }
        return result;
    }

    public StoreBuild getAppBuild(StoreAgent agent) {
        StoreBuild result = null;

        try {
            VersionService service = createClientService(agent);
            Request request = new Request();
            request.manifest = true;
            request.service = agent.getService();
            request.uid = UUID.randomUUID().toString();

            Version version = service.getAppVersion(request);
            result = map(version);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error reading the application version for the the agent " + agent.getGuid(), ex);
        }
        return result;
    }

    private static StoreBuild map(Version version) {
        StoreBuild result = null;
        if (version != null) {            
            result = new StoreBuild();
            result.setUid(version.uid);
            result.setBuild(version.build);
            result.setArtifactId(version.artifactId);
            result.setGroupdId(version.groupdId);
            result.setMavenVersion(version.version);
            result.setDate(version.date);
            result.setRelease(version.release);
            result.setScm(version.scm);
            result.setService(version.service);
            result.setVer(version.ver);
            result.setParameters(new HashSet<StoreBuildParameter>());            
            result.getParameters().addAll(createStoreBuildParameter(version.manifest, StoreBuildParameterType.MANIFEST));
            result.getParameters().addAll(createStoreBuildParameter(version.other, StoreBuildParameterType.OTHER));
        }        
        return result;
    }
    
    private static List<StoreBuildParameter> createStoreBuildParameter(Map<String, String> params, StoreBuildParameterType type) {
        List<StoreBuildParameter> result = new ArrayList<>();
        if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
                StoreBuildParameter param = new StoreBuildParameter();
                param.setType(type);
                param.setName(entry.getKey());
                param.setValue(entry.getValue());
            }
        }
        return result;
    }

    private static VersionService createClientService(StoreAgent agent) {
        VersionService result = null;
        if (agent != null) {

            String user = null;
            String password = null;
            if (agent.isAuthentication()) {
                user = agent.getUser();
                password = agent.getPassword();
            }

            String url = agent.getUrl();
            if (StoreAgentType.SERVICE.equals(agent.getType())) {
                url = url + AGENT_SERVICE;
            }
            try {
                result = RestClient.getClient(VersionService.class, url, user, password);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error creating the version service for the agent " + agent.getGuid(), ex);
            }
        }
        return result;
    }

}
