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
package org.lorislab.armonitor.web.rs.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.ejb.StoreAgentServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreAgent;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.Agent;
import org.lorislab.armonitor.web.rs.model.AgentChangePasswordRequest;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NEVER)
public class AgentServiceBean {

    @EJB
    private StoreAgentServiceBean service;

    @EJB
    private StoreSystemServiceBean systemService;

    public Agent create() throws Exception {
        return Mapper.create(StoreAgent.class, Agent.class);
    }

    public void changePassword(AgentChangePasswordRequest reqeust) {
        StoreAgent tmp = service.loadAgent(reqeust.guid);
        if (tmp != null) {
            String password = tmp.getPassword();
            if (password == null || password.equals(reqeust.old)) {
                tmp.setPassword(reqeust.p1);
                service.saveAgent(tmp);
            }
        }
    }

    public Agent save(Agent agent) throws Exception {
        Agent result = null;
        if (agent != null) {
            StoreAgent tmp = service.loadAgent(agent.guid);
            if (tmp != null) {
                tmp = Mapper.update(tmp, agent);
            } else {
                tmp = Mapper.create(agent, StoreAgent.class);
                StoreSystem system = systemService.getSystem(agent.system);
                if (system != null) {
                    tmp.setSystem(system);
                } else {
                    throw new Exception("Missing system for the agent!");
                }
            }
            tmp = service.saveAgent(tmp);
            result = Mapper.map(tmp, Agent.class);
        }
        return result;
    }

    public Agent get(String guid) throws Exception {
        StoreAgent tmp = service.loadAgent(guid);
        return Mapper.map(tmp, Agent.class);
    }

    public Agent getBySystem(String guid) throws Exception {
        StoreAgent tmp = service.loadAgentBySystem(guid);
        return Mapper.map(tmp, Agent.class);
    }

    public List<Agent> get() {
        List<StoreAgent> tmp = service.getAgents();
        return Mapper.map(tmp, Agent.class);
    }

}
