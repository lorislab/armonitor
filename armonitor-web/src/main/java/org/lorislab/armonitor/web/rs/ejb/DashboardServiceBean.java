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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.criteria.StoreSystemBuildCriteria;
import org.lorislab.armonitor.store.ejb.StoreProjectServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemBuildServiceBean;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.web.rs.model.Dashboard;
import org.lorislab.armonitor.web.rs.model.DashboardApplication;
import org.lorislab.armonitor.web.rs.model.DashboardApplicationSystem;
import org.lorislab.armonitor.web.rs.model.DashboardProject;
import org.lorislab.armonitor.web.rs.model.DashboardSystemBuild;
import org.lorislab.armonitor.web.rs.resources.Errors;
import org.lorislab.jel.ejb.exception.ServiceException;

/**
 * The dashboard service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DashboardServiceBean {

    /**
     * The store project service.
     */
    @EJB
    private StoreProjectServiceBean service;

    /**
     * The store system build service.
     */
    @EJB
    private StoreSystemBuildServiceBean systemBuildService;

    /**
     * Updates the system build.
     *
     * @param app the application.
     * @param system the system.
     *
     * @throws ServiceException if the method fails.
     */
    public void updateSystemBuild(DashboardApplication app, DashboardApplicationSystem system) throws ServiceException {
        try {
            StoreSystemBuildCriteria criteria = new StoreSystemBuildCriteria();
            criteria.setSystem(system.guid);
            criteria.setFetchBuild(true);
            criteria.setFetchBuildParam(true);
            criteria.setFetchSystem(true);
            criteria.setFetchSystemApplication(true);
            criteria.setMaxDate(Boolean.TRUE);
            StoreSystemBuild ssb = systemBuildService.getSystemBuild(criteria);
            DashboardSystemBuild dsb = Mapper.map(ssb, DashboardSystemBuild.class);
            system.systemBuild = dsb;
        } catch (Exception ex) {
            throw new ServiceException(Errors.DASHBOARD_UPDATE_SYSTEM_BUILD_ERROR);
        }
    }

    /**
     * Gets the list of dashboard projects.
     *
     * @return the list of dashboard projects.
     *
     * @throws ServiceException if the method fails.
     */
    public Dashboard getDashboard() throws ServiceException {
        Dashboard result;
        try {
            result = new Dashboard();
            List<StoreProject> tmp = service.getDashboardProjects();
            result.projects = Mapper.set(tmp, DashboardProject.class);
            result.date = new Date();
            if (tmp != null) {
                result.size = tmp.size();
            }            
        } catch (Exception ex) {
            throw new ServiceException(Errors.DASHBOARD_LOAD_ERROR, null, ex);            
        }
        return result;
    }

    /**
     * Updates the systems in the dashboard.
     *
     * @param dashboard the dashboard.
     * @return the map of the dashboard application systems.
     *
     * @throws Exception if the method fails.
     */
    public Map<String, DashboardApplicationSystem> updateSystems(Dashboard dashboard) throws Exception {
        Map<String, DashboardApplicationSystem> systems = new HashMap<>();
        if (dashboard.projects != null) {
            for (DashboardProject project : dashboard.projects) {
                if (project != null && project.applications != null) {
                    for (DashboardApplication app : project.applications) {
                        if (app != null && app.systems != null) {
                            for (DashboardApplicationSystem sys : app.systems) {
                                systems.put(sys.guid, sys);
                            }
                        }
                    }
                }
            }
        }

        StoreSystemBuildCriteria criteria = new StoreSystemBuildCriteria();
        criteria.setSystems(systems.keySet());
        criteria.setFetchBuild(true);
        criteria.setFetchBuildParam(true);
        criteria.setFetchSystem(true);
        criteria.setFetchSystemApplication(true);
        criteria.setMaxDate(Boolean.TRUE);
        List<StoreSystemBuild> ssb = systemBuildService.getSystemBuilds(criteria);
        List<DashboardSystemBuild> builds = Mapper.map(ssb, DashboardSystemBuild.class);
        if (builds != null) {
            for (DashboardSystemBuild build : builds) {
                DashboardApplicationSystem das = systems.get(build.system);
                if (das != null) {
                    das.systemBuild = build;
                }
            }
        }
        return systems;
    }
}
