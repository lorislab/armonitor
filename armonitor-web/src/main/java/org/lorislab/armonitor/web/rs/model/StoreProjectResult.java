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

package org.lorislab.armonitor.web.rs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Andrej Petras
 */
public class StoreProjectResult implements Serializable {
    
    private static final long serialVersionUID = -2669126975853016980L;
    
    private List<Project> projects = new ArrayList<>();
    
    private Map<String, Project> cacheProjects = new HashMap<>();
    
    private Map<String, Application> cacheApplications = new HashMap<>();
    
    private Map<String, AppSystem> cacheSystems = new HashMap<>();

    public void updateAppSystem(AppSystem system) {
        
    }
    
    public void addAppSystem(String app, AppSystem system) {
        Application a = cacheApplications.get(app);
        a.systems.add(system);
        cacheSystems.put(system.guid, system);
    }
    
    public void addApplication(String project, Application application) {
        Project p = cacheProjects.get(project);
        p.applications.add(application);
        cacheApplications.put(application.guid, application);
    }
    
    public void addProject(Project project) {
        projects.add(project);
        cacheProjects.put(project.guid, project);
    }
    
    public Set<String> getProjectGuids() {
        return cacheProjects.keySet();
    }
    
    public Set<String> getApplicationGuids() {
        return cacheApplications.keySet();
    }
    
    /**
     * @return the projects
     */
    public List<Project> getProjects() {
        return projects;
    }   
    
}
