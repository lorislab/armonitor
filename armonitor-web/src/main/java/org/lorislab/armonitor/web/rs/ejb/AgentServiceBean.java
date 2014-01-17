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

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.store.ejb.StoreAgentServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreAgent;
import org.lorislab.armonitor.store.model.StoreAgentType;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.Agent;
import org.lorislab.armonitor.web.rs.model.AgentChangePasswordRequest;
import org.lorislab.armonitor.web.rs.model.AgentType;

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
        StoreAgent tmp = new StoreAgent();
        return map(tmp);
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
        StoreAgent tmp = service.loadAgent(agent.guid);
        if (tmp != null) {
            tmp = update(tmp, agent);        
            tmp = service.saveAgent(tmp);
            result = map(tmp);
        } else {
            tmp = new StoreAgent();
            tmp.setGuid(agent.guid);            
            update(tmp, agent);
            StoreSystem system = systemService.getSystem(agent.system);
            if (system != null) {
                tmp.setSystem(system);
                tmp = service.saveAgent(tmp);
                result = map(tmp);
            }
        }
        return result;
    }

    public Agent get(String guid) throws Exception {
        StoreAgent tmp = service.loadAgent(guid);
        return map(tmp);
    }

    public Agent getBySystem(String guid) throws Exception {
        StoreAgent tmp = service.loadAgentBySystem(guid);
        return map(tmp);
    }

    public List<Agent> get() {
        List<StoreAgent> tmp = service.getAgents();
        return map(tmp);
    }    

    private StoreAgent update(StoreAgent tmp, Agent agent) {
        if (tmp != null) {
            tmp.setAuthentication(agent.authentication);
            tmp.setService(agent.service);
            tmp.setType(StoreAgentType.SERVICE);
            if (AgentType.INTEGRATION == agent.type) {
                tmp.setType(StoreAgentType.INTEGRATION);
            }            
            tmp.setUrl(agent.url);
            tmp.setUser(agent.user);            
        }
        return tmp;
    }
    
    private List<Agent> map(List<StoreAgent> tmp) {
        List<Agent> result = null;
        if (tmp != null) {
            result = new ArrayList<>();
            for (StoreAgent item : tmp) {
                Agent agent = map(item);
                if (agent != null) {
                    result.add(agent);
                }
            }
        }
        return result;
    }

    private Agent map(StoreAgent agent) {
        Agent result = null;
        if (agent != null) {            
            result = new Agent();
            result.guid = agent.getGuid();
            result.user = agent.getUser();
            result.authentication = agent.isAuthentication();
            result.service = agent.getService();
            result.type = AgentType.SERVICE;
            if (StoreAgentType.INTEGRATION == agent.getType()) {
                result.type = AgentType.INTEGRATION;
            }
            if (agent.getSystem() != null) {
                result.system = agent.getSystem().getGuid();                
            }            
            result.url = agent.getUrl();
        }
        return result;
    }    
}
