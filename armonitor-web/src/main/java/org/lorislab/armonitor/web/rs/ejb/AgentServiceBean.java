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

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.criteria.StoreAgentCriteria;
import org.lorislab.armonitor.store.ejb.StoreAgentServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreAgent;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.Agent;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AgentServiceBean {

    private static final Logger LOGGER = Logger.getLogger(AgentServiceBean.class.getName());
    
    @EJB
    private StoreAgentServiceBean service;

    @EJB
    private StoreSystemServiceBean systemService;

    public Agent create() throws Exception {
        return Mapper.create(StoreAgent.class, Agent.class);
    }

    public ApplicationSystem getSystem(String guid) {
        StoreAgentCriteria criteria = new StoreAgentCriteria();
        criteria.setGuid(guid);
        criteria.setFetchSystem(true);
        StoreAgent sys = service.loadAgent(criteria);
        if (sys != null) {
            return Mapper.map(sys.getSystem(), ApplicationSystem.class);
        }       
        return null;
    }
    
    public void addSystem(String guid, String sys) {
        StoreAgentCriteria criteria = new StoreAgentCriteria();
        criteria.setGuid(guid);
        criteria.setFetchSystem(true);        
        StoreAgent tmp = service.loadAgent(criteria);
        if (tmp != null) {
            if (tmp.getSystem() == null) {             
                StoreSystem system = systemService.getSystem(sys);
                if (system != null) {
                    tmp.setSystem(system);
                    service.saveAgent(tmp);
                } else {
                    LOGGER.log(Level.WARNING,"Missing system {0}", sys);
                }
            } else {
                LOGGER.log(Level.WARNING,"The agent {0} has already system", guid);
            }
        } else {
            LOGGER.log(Level.WARNING,"Missing agent {0}", guid);
        }
    }
    
    public void changePassword(String guid, ChangePasswordRequest reqeust) {
        StoreAgent tmp = service.loadAgent(guid);
        if (tmp != null) {
            char[] password = tmp.getPassword();
            if (password == null || Arrays.equals(password,reqeust.old.toCharArray())) {
                tmp.setPassword(reqeust.p1.toCharArray());
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
