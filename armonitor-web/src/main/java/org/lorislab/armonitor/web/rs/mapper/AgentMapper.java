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
package org.lorislab.armonitor.web.rs.mapper;

import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreAgent;
import org.lorislab.armonitor.store.model.enums.StoreAgentType;
import org.lorislab.armonitor.web.rs.model.Agent;
import org.lorislab.armonitor.web.rs.model.enums.AgentType;

/**
 * The agent mapper.
 *
 * @author Andrej Petras
 */
public class AgentMapper implements MapperService<StoreAgent, Agent> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Agent map(StoreAgent data, String profile) {
        Agent result = new Agent();
        result.guid = data.getGuid();
        result.user = data.getUser();
        result.authentication = data.isAuthentication();
        result.type = null;
        if (data.getType() != null) {
            result.type = AgentType.valueOf(data.getType().name());
        }
        result.url = data.getUrl();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAgent update(StoreAgent entity, Agent data, String profile) {
        entity.setAuthentication(data.authentication);
        entity.setType(null);
        if (data.type != null) {
            entity.setType(StoreAgentType.valueOf(data.type.name()));
        }
        entity.setUrl(data.url);
        entity.setUser(data.user);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAgent create(Agent data, String profile) {
        StoreAgent result = new StoreAgent();
        result.setGuid(data.guid);
        result = update(result, data, profile);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Agent create(String profile) {
        StoreAgent agent = new StoreAgent();
        return map(agent, profile);
    }

}