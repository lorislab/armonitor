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

package org.lorislab.armonitor.jira.rs.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.lorislab.armonitor.jira.client.model.Project;
import org.lorislab.armonitor.jira.ejb.JiraServiceLocal;
import org.lorislab.armonitor.jira.rs.model.JiraProject;

/**
 *
 * @author Andrej Petras
 */
@Named
@SessionScoped
public class JiraController implements Serializable {
    
    private static final long serialVersionUID = -6903348700879478427L;
    
    private List<JiraProject> projects = null;

    @EJB
    private JiraServiceLocal service;
    
    public List<JiraProject> reloadProjects() {
        projects = null;        
        List<Project> tmp = service.getProjects();
        if (tmp != null && !tmp.isEmpty()) {
            
            projects = new ArrayList<>();
            for (Project project : tmp) {
                JiraProject p = new JiraProject();
                p.id = project.getId();
                p.key = project.getKey();
                p.name = project.getName();
                projects.add(p);
            }            
        }
        return projects;
    }

    public List<JiraProject> getProjects() {
        if (projects == null) {
            return reloadProjects();
        }
        return projects;
    }
    
            
}
