/*
 * Copyright 2013 lorislab.org.
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

package org.lorislab.armonitor.jira.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.config.ejb.ConfigurationServiceLocal;
import org.lorislab.armonitor.config.rs.model.JiraConfig;
import org.lorislab.armonitor.jira.client.JIRAClient;
import org.lorislab.armonitor.jira.client.model.Project;
import org.lorislab.armonitor.jira.client.services.ProjectClient;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@Local(JiraServiceLocal.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class JiraServiceBean implements JiraServiceLocal {
    
    @EJB
    private ConfigurationServiceLocal configService;
    
    public List<Project> getProjects() {
        List<Project> result = null;
        JiraConfig config = configService.getConfiguration(JiraConfig.class);
        
        try {
            JIRAClient client = new JIRAClient(config.server, config.user, config.password);
            ProjectClient projectClient = client.createProjectClient();
            result = projectClient.getProjects();
            
        } catch (Exception ex) {
            Logger.getLogger(JiraServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return result;
    }
}
