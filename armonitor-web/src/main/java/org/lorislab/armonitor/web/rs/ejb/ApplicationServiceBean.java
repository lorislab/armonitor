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
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.criteria.StoreApplicationCriteria;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.ejb.StoreProjectServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSCMSystemServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.store.model.StoreSCMSystem;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.Application;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;
import org.lorislab.armonitor.web.rs.model.Project;
import org.lorislab.armonitor.web.rs.model.SCMSystem;
import org.lorislab.jel.ejb.exception.ServiceException;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ApplicationServiceBean {

    private static final Logger LOGGER = Logger.getLogger(ApplicationServiceBean.class.getName());

    @EJB
    private StoreApplicationServiceBean service;

    @EJB
    private StoreSystemServiceBean sysService;

    @EJB
    private StoreProjectServiceBean projectService;
    
    @EJB
    private StoreSCMSystemServiceBean scmService;
    
    public Set<ApplicationSystem> getSystems(String guid) {
        StoreApplicationCriteria criteria = new StoreApplicationCriteria();
        criteria.setGuid(guid);
        criteria.setFetchSystem(true);
        StoreApplication app = service.getApplication(criteria);
        if (app != null) {
            return Mapper.map(app.getSystems(), ApplicationSystem.class);
        }     
        return null;
    }
    
    public void addSystem(String guid, String sys) {
        StoreSystem tmp = sysService.getSystem(sys);
        if (tmp != null) {
            StoreApplication application = service.getApplication(guid);
            if (application != null) {
                tmp.setApplication(application);
                sysService.saveSystem(tmp);
            } else {
                LOGGER.log(Level.WARNING, "No applicaiton found {0}", guid);
            }
        } else {
            LOGGER.log(Level.WARNING, "No system found {0}", sys);
        }
    }

    public Project getProject(String guid) {
        StoreApplicationCriteria criteria = new StoreApplicationCriteria();
        criteria.setGuid(guid);
        criteria.setFetchProject(true);
        StoreApplication sys = service.getApplication(criteria);
        if (sys != null) {
            return Mapper.map(sys.getProject(), Project.class);
        }
        return null;
    }

    public SCMSystem getSCMSystem(String guid) {
        StoreApplicationCriteria criteria = new StoreApplicationCriteria();
        criteria.setGuid(guid);
        criteria.setFetchSCM(true);
        StoreApplication sys = service.getApplication(criteria);
        if (sys != null) {
            return Mapper.map(sys.getScm(), SCMSystem.class);
        }
        return null;
    }

    public Application create() throws Exception {
        return Mapper.create(StoreApplication.class, Application.class);
    }

    public Application get(String guid) throws Exception {
        StoreApplication tmp = service.getApplication(guid);
        return Mapper.map(tmp, Application.class);
    }

    public List<Application> get() throws Exception {
        List<StoreApplication> tmp = service.getApplications();
        return Mapper.map(tmp, Application.class);
    }

    public Application save(Application app) throws Exception {
        Application result;
        StoreApplication tmp = service.getApplication(app.guid);
        if (tmp != null) {
            tmp = Mapper.update(tmp, app);
        } else {
            tmp = Mapper.create(app, StoreApplication.class);
        }
        tmp = service.saveApplication(tmp);
        result = Mapper.map(tmp, Application.class);
        return result;
    }

    public void delete(String guid) throws ServiceException {
        service.deleteApplication(guid);
    }     

    public Map<String, String> getList() {
        List<StoreApplication> tmp = service.getApplications();
        return Mapper.convert(tmp, String.class);
    }

    public void addSCMSystem(String guid, String scm) {
        StoreApplication tmp = service.getApplication(guid);
        if (tmp != null) {
            if (scm != null) {
                StoreSCMSystem system = scmService.getSCMSystem(scm);
                if (system != null) {
                    tmp.setScm(system);
                    service.saveApplication(tmp);
                } else {
                    LOGGER.log(Level.WARNING, "The source code system not found {0}", scm);
                }
            } else {
                 tmp.setScm(null);
                 service.saveApplication(tmp);
            }
        } else {
            LOGGER.log(Level.WARNING, "Application not found {0}", guid);
        }
    }

    public void addProject(String guid, String project) {
        StoreApplication tmp = service.getApplication(guid);
        if (tmp != null) {
            if (project != null) {
                StoreProject pr = projectService.getProject(project);
                if (pr != null) {
                    tmp.setProject(pr);
                    service.saveApplication(tmp);
                } else {
                    LOGGER.log(Level.WARNING, "The project not found {0}", project);
                }
            } else {
                 tmp.setProject(null);
                 service.saveApplication(tmp);
            }
        } else {
            LOGGER.log(Level.WARNING, "Application not found {0}", guid);
        }
    }
}
