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
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.agent.rs.model.Request;
import org.lorislab.armonitor.agent.rs.model.Version;
import org.lorislab.armonitor.agent.rs.service.VersionService;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.model.StoreAgent;
import org.lorislab.armonitor.store.model.StoreAgentType;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.util.RestClient;

/**
 * The agent client service.
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

    /**
     * The agent REST link
     */
    private static final String AGENT_SERVICE = "/armonitor-agent/rs";

    /**
     * Gets all applications build.
     *
     * @param agent the agent.
     * @return the list of applications build.
     */
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
                    StoreBuild tmp = Mapper.map(version, StoreBuild.class);
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

    /**
     * Gets the agent service build.
     *
     * @param agent the agent.
     * @return the agent service build.
     */
    public StoreBuild getAgentBuild(StoreAgent agent) {
        StoreBuild result = null;

        try {
            VersionService service = createClientService(agent);
            Request request = new Request();
            request.manifest = true;
            request.service = agent.getService();
            request.uid = UUID.randomUUID().toString();

            Version version = service.getAgentVersion(request);
            result = Mapper.map(version, StoreBuild.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error reading the version of the agent " + agent.getGuid(), ex);
        }
        return result;
    }

    /**
     * Gets the application build.
     *
     * @param agent the agent.
     * @return the build of the application.
     */
    public StoreBuild getAppBuild(StoreAgent agent) {
        StoreBuild result = null;

        try {
            VersionService service = createClientService(agent);
            Request request = new Request();
            request.manifest = true;
            request.service = agent.getService();
            request.uid = UUID.randomUUID().toString();

            Version version = service.getAppVersion(request);
            result = Mapper.map(version, StoreBuild.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error reading the application version for the the agent " + agent.getGuid(), ex);
        }
        return result;
    }

    /**
     * Creates the version service.
     *
     * @param agent the agent.
     * @return the version service for the agent.
     */
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
