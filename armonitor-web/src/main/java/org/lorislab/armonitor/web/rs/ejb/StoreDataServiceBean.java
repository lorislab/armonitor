/*
 * Copyright 2014 lorislab.org.
 *
 * Licensed under the Apache License, StoreVersion 2.0 (the "License");
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
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.agent.ejb.AgentClientServiceBean;
import org.lorislab.armonitor.agent.rs.model.Version;
import org.lorislab.armonitor.store.criteria.StoreAgentCriteria;
import org.lorislab.armonitor.store.criteria.StoreApplicationCriteria;
import org.lorislab.armonitor.store.criteria.StoreProjectCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemCriteria;
import org.lorislab.armonitor.store.ejb.StoreAgentServiceBean;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.ejb.StoreProjectServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreAgent;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.AppSystem;
import org.lorislab.armonitor.web.rs.model.Application;
import org.lorislab.armonitor.web.rs.model.Project;
import org.lorislab.armonitor.web.rs.model.StoreProjectResult;
import org.lorislab.armonitor.web.rs.model.SystemStatus;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreDataServiceBean {

    @EJB
    private StoreProjectServiceBean projectService;

    @EJB
    private StoreApplicationServiceBean applicationService;

    @EJB
    private StoreSystemServiceBean systemService;

    @EJB
    private StoreAgentServiceBean agentService;
    
    @EJB
    private AgentClientServiceBean agentClientService;
    
    public SystemStatus getSystemStatus(String guid) {
        SystemStatus result = null;
        
        StoreAgentCriteria criteria = new StoreAgentCriteria();
        criteria.setSystem(guid);
        List<StoreAgent> agents = agentService.getAgents(criteria);
        if (agents != null && !agents.isEmpty()) {
            StoreAgent agent = agents.get(0);            
//            Version version = agentClientService.getVersion();
        }
        return result;
    }
    
    public StoreProjectResult getProjects() {
        StoreProjectResult result = new StoreProjectResult();

        StoreProjectCriteria projectCriteria = new StoreProjectCriteria();
        projectCriteria.setEnabled(Boolean.TRUE);

        List<StoreProject> projects = projectService.getProjects(projectCriteria);
        if (projects != null) {
            for (StoreProject project : projects) {
                Project p = new Project();
                p.guid = project.getGuid();
                p.name = project.getName();
                p.jiraKey = project.getJiraKey();
                result.addProject(p);
            }

            StoreApplicationCriteria appCriteria = new StoreApplicationCriteria();
            appCriteria.setEnabled(Boolean.TRUE);
            appCriteria.setProjects(result.getProjectGuids());

            List<StoreApplication> applications = applicationService.getApplications(appCriteria);
            if (applications != null) {

                for (StoreApplication app : applications) {
                    Application a = new Application();
                    a.guid = app.getGuid();
                    a.name = app.getName();
                    result.addApplication(app.getProject(), a);
                }

                Set<String> guids2 = result.getApplicationGuids();
                if (!guids2.isEmpty()) {

                    StoreSystemCriteria sysCriteria = new StoreSystemCriteria();
                    sysCriteria.setEnabled(Boolean.TRUE);
                    sysCriteria.setApplications(guids2);
                    List<StoreSystem> systems = systemService.getSystems(sysCriteria);
                    if (systems != null) {
                        for (StoreSystem system : systems) {
                            AppSystem s = new AppSystem();
                            s.guid = system.getGuid();
                            s.name = system.getName();
                            result.addAppSystem(system.getGuid(), s);
                        }
                    }
                }
            }
        }
        return result;
    }
}
