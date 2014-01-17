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

package org.lorislab.armonitor.web.rs.services;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.lorislab.armonitor.web.rs.ejb.StoreDataServiceBean;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;
import org.lorislab.armonitor.web.rs.model.Project;
import org.lorislab.armonitor.web.rs.model.StoreProjectResult;

/**
 *
 * @author Andrej Petras
 */
@Named
@SessionScoped
public class StoreController implements Serializable {
    
    private static final long serialVersionUID = -5968008702433289136L;
    
    @EJB
    private StoreDataServiceBean storeService;
    
    private StoreProjectResult projects;
    
    public ApplicationSystem updateSystem(String guid) {
        
//        ApplicationSystem sys = storeService.getSystemStatus(guid);
//        if (sys != null) {
//            projects.a
//        }
//        return sys;
        return null;
    }
    
    public List<Project> getProjects() {
        if (projects != null) {
            return projects.getProjects();
        }
        return reloadProjects();
    }
    
    public List<Project> reloadProjects() {
        projects = storeService.getProjects();
        return projects.getProjects();
    }
}
