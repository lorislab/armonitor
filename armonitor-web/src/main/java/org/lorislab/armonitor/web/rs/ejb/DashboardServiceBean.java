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
import org.lorislab.armonitor.web.rs.mapper.DashboardApplicationSystemMapperKey;
import org.lorislab.armonitor.web.rs.model.Dashboard;
import org.lorislab.armonitor.web.rs.model.DashboardApplication;
import org.lorislab.armonitor.web.rs.model.DashboardApplicationSystem;
import org.lorislab.armonitor.web.rs.model.DashboardProject;
import org.lorislab.armonitor.web.rs.model.DashboardSystemBuild;

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
     * @param system the system.
     * @return the dashboard system build.
     */
    public DashboardSystemBuild updateSystemBuild(String system) {
        StoreSystemBuildCriteria criteria = new StoreSystemBuildCriteria();
        criteria.setSystem(system);
        criteria.setFetchBuild(true);
        criteria.setFetchBuildParam(true);
        criteria.setFetchSystem(true);
        criteria.setMaxDate(Boolean.TRUE);
        StoreSystemBuild ssb = systemBuildService.getSystemBuild(criteria);
        return Mapper.map(ssb, DashboardSystemBuild.class, "dashboard");
    }

    /**
     * Gets the list of dashboard projects.
     *
     * @return the list of dashboard projects.
     */
    public Dashboard getDashboard() {

        Dashboard result = new Dashboard();
        List<StoreProject> tmp = service.getDashboardProjects();
        result.projects = Mapper.convert(tmp, DashboardProject.class);
        result.date = new Date();
        if (tmp != null) {
            result.size = tmp.size();
        }
        return result;
    }

    /**
     * Updates the systems in the dashboard.
     *
     * @param dashboard the dashboard.
     * @return the map of the dashboard application systems.
     */
    public Map<String, DashboardApplicationSystem> updateSystems(Dashboard dashboard) {
        Map<String, DashboardApplicationSystem> systems = new HashMap<>();
        if (dashboard.projects != null) {
            for (DashboardProject project : dashboard.projects.values()) {
                if (project != null && project.applications != null) {
                    for (DashboardApplication app : project.applications.values()) {
                        systems.putAll(app.systems);
                    }
                }
            }
        }

        StoreSystemBuildCriteria criteria = new StoreSystemBuildCriteria();
        criteria.setSystems(systems.keySet());
        criteria.setFetchBuild(true);
        criteria.setFetchBuildParam(true);
        criteria.setFetchSystem(true);
        criteria.setMaxDate(Boolean.TRUE);
        List<StoreSystemBuild> ssb = systemBuildService.getSystemBuilds(criteria);
        List<DashboardSystemBuild> builds = Mapper.map(ssb, DashboardSystemBuild.class, "dashboard");
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
