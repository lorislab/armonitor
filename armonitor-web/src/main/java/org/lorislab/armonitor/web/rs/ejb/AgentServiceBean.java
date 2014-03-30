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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.process.ejb.TestServiceBean;
import org.lorislab.armonitor.store.criteria.StoreAgentCriteria;
import org.lorislab.armonitor.store.ejb.StoreAgentServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreAgent;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.Agent;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;
import org.lorislab.armonitor.web.rs.model.Build;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;
import org.lorislab.armonitor.web.rs.model.enums.AgentType;
import org.lorislab.jel.ejb.exception.ServiceException;

/**
 * The agent service.
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
    
    @EJB
    private TestServiceBean testService;
    
    public Agent create() throws Exception {
        return Mapper.create(StoreAgent.class, Agent.class);
    }
    
    public Set<ApplicationSystem> getSystems(String guid) {
        StoreAgentCriteria criteria = new StoreAgentCriteria();
        criteria.setGuid(guid);
        criteria.setFetchSystem(true);
        StoreAgent sys = service.loadAgent(criteria);
        if (sys != null) {
            return Mapper.map(sys.getSystems(), ApplicationSystem.class);
        }
        return null;
    }
    
    public void addSystem(String guid, String sys) {
        StoreAgentCriteria criteria = new StoreAgentCriteria();
        criteria.setGuid(guid);
        criteria.setFetchSystem(true);
        StoreAgent tmp = service.loadAgent(criteria);
        if (tmp != null) {
            StoreSystem system = systemService.getSystem(sys);
            if (system != null) {
                system.setAgent(tmp);
                systemService.saveSystem(system);
            } else {
                LOGGER.log(Level.WARNING, "The system not found {0}", sys);
            }
        } else {
            LOGGER.log(Level.WARNING, "The agent not found {0}", guid);
        }
    }
    
    public void changePassword(String guid, ChangePasswordRequest reqeust) {
        StoreAgent tmp = service.getAgent(guid);
        if (tmp != null) {
            tmp.setPassword(reqeust.p1.toCharArray());
            service.saveAgent(tmp);
        }
    }
    
    public Agent save(Agent agent) throws Exception {
        Agent result = null;
        if (agent != null) {
            StoreAgent tmp = service.getAgent(agent.guid);
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
        StoreAgent tmp = service.getAgent(guid);
        return Mapper.map(tmp, Agent.class);
    }
    
    public List<Agent> get() {
        List<StoreAgent> tmp = service.getAgents();
        return Mapper.map(tmp, Agent.class);
    }
    
    public void delete(String guid) throws ServiceException {
        service.deleteAgent(guid);
    }    
    
    public Map<String, String> getList() {
        List<StoreAgent> tmp = service.getAgents();
        return Mapper.convert(tmp, String.class);
    }
    
    public Map<String, String> getTypes() {
        Map<String, String> result = new HashMap<>();
        for (AgentType t : AgentType.values()) {
            result.put(t.name(), t.name());
        }        
        return result;
    }
    
    public Build testConnection(String guid) throws ServiceException {
        StoreBuild build = testService.testAgent(guid);
        return Mapper.map(build, Build.class);
    }
}
