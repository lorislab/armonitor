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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.criteria.StoreProjectCriteria;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.ejb.StoreProjectServiceBean;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.web.rs.model.Application;
import org.lorislab.armonitor.web.rs.model.BTSystem;
import org.lorislab.armonitor.web.rs.model.Project;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ProjectServiceBean {

    private static final Logger LOGGER = Logger.getLogger(ProjectServiceBean.class.getName());

    @EJB
    private StoreProjectServiceBean service;

    @EJB
    private StoreApplicationServiceBean appService;

    public void addApplication(String guid, String app) {
        StoreApplication tmp = appService.getApplication(app);
        if (tmp != null) {
            StoreProject sp = service.getProject(guid);
            if (sp != null) {
                tmp.setProject(sp);
                appService.saveApplication(tmp);
            } else {
                LOGGER.log(Level.WARNING, "Project not found {0}", guid);
            }
        } else {
            LOGGER.log(Level.WARNING, "Applicaiton not found application {0}", app);
        }
    }

    public Set<Application> getApplications(String guid) {
        StoreProjectCriteria criteria = new StoreProjectCriteria();
        criteria.setGuid(guid);
        criteria.setFetchApplication(true);
        StoreProject sys = service.getProject(criteria);
        if (sys != null) {
            return Mapper.map(sys.getApplications(), Application.class);
        }
        return null;
    }

    public BTSystem getBTSystem(String guid) {
        StoreProjectCriteria criteria = new StoreProjectCriteria();
        criteria.setGuid(guid);
        criteria.setFetchBTS(true);
        StoreProject sys = service.getProject(criteria);
        if (sys != null) {
            return Mapper.map(sys.getBts(), BTSystem.class);
        }
        return null;
    }

    public Project create() throws Exception {
        return Mapper.create(StoreProject.class, Project.class);
    }

    public Project get(String guid) throws Exception {
        StoreProject tmp = service.getProject(guid);
        return Mapper.map(tmp, Project.class);
    }

    public List<Project> get() throws Exception {
        List<StoreProject> tmp = service.getProjects();
        return Mapper.map(tmp, Project.class);
    }

    public Project save(Project project) throws Exception {
        Project result;
        StoreProject tmp = service.getProject(project.guid);
        if (tmp != null) {
            tmp = Mapper.update(tmp, project);
        } else {
            tmp = Mapper.create(project, StoreProject.class);
        }
        tmp = service.saveProject(tmp);
        result = Mapper.map(tmp, Project.class);
        return result;
    }

}
