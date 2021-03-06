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
import org.lorislab.armonitor.store.model.enums.StoreAgentType;
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
    private static final String AGENT_SERVICE = "/rs";

    /**
     * Gets all applications build.
     *
     * @param agent the agent.
     * @return the list of applications build.
     */
    public List<StoreBuild> getBuilds(StoreAgent agent) {
        List<StoreBuild> result = new ArrayList<>();

        try {
            VersionService client = createClientService(agent);
            Request request = new Request();
            request.manifest = true;
            request.uid = UUID.randomUUID().toString();

            List<Version> versions = client.getVersion(request);
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
     * 
     * @throws Exception if the method fails.
     */
    public StoreBuild getAgentBuild(StoreAgent agent) throws Exception {
        StoreBuild result = null;

        try {
            VersionService client = createClientService(agent);
            Version version = client.getAgentVersion(true);
            result = Mapper.map(version, StoreBuild.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error reading the version of the agent {0}", agent.getGuid());
            throw ex;
        }
        return result;
    }

    /**
     * Gets the application build.
     *
     * @param agent the agent.
     * @param service the service name.
     * @return the build of the application.
     */
    public StoreBuild getBuild(StoreAgent agent, String service) {
        StoreBuild result = null;

        try {
            VersionService client = createClientService(agent);
            Request request = new Request();
            request.manifest = true;
            request.service = service;
            request.uid = UUID.randomUUID().toString();

            List<Version> versions = client.getVersion(request);
            Version version = null;
            if (versions != null && !versions.isEmpty()) {
                version = versions.get(0);
            }
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

            StringBuilder sb = new StringBuilder();
            sb.append(agent.getUrl());
            
            if (StoreAgentType.SERVICE.equals(agent.getType())) {
                sb.append(AGENT_SERVICE);
            }
            try {
                result = RestClient.getClient(VersionService.class, sb.toString(), agent.isAuthentication(), agent.getUser(), agent.getPassword());
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error creating the version service for the agent " + agent.getGuid(), ex);
            }
        }
        return result;
    }

}
