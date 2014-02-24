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
package org.lorislab.armonitor.web.rs.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.lorislab.armonitor.web.rs.ejb.BuildServiceBean;
import org.lorislab.armonitor.web.rs.ejb.DashboardServiceBean;
import org.lorislab.armonitor.web.rs.model.BuildCriteria;
import org.lorislab.armonitor.web.rs.model.BuildsCriteria;
import org.lorislab.armonitor.web.rs.model.Dashboard;
import org.lorislab.armonitor.web.rs.model.DashboardApplication;
import org.lorislab.armonitor.web.rs.model.DashboardApplicationSystem;
import org.lorislab.armonitor.web.rs.model.TimelineBuild;
import org.lorislab.armonitor.web.rs.model.DashboardProject;
import org.lorislab.armonitor.web.rs.model.DashboardSystemBuild;

/**
 * The dashboard controller.
 *
 * @author Andrej Petras
 */
@Named
@SessionScoped
public class DashboardController implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 4654267545134703641L;

    /**
     * The dashboard service.
     */
    @EJB
    private DashboardServiceBean service;

    /**
     * The build service.
     */
    @EJB
    private BuildServiceBean buildService;

    /**
     * The dashboard.
     */
    private Dashboard dashboard;
    /**
     * The map of the dashboard application systems.
     */
    private Map<String, DashboardApplicationSystem> systems = new HashMap<>();
    /**
     * The builds criteria.
     */
    private BuildsCriteria criteria;

    /**
     * Gets the dashboard application.
     *
     * @return the dashboard application.
     */
    public DashboardApplication getApplication() {
        return getApplication(criteria.project, criteria.app);
    }

    /**
     * Sets the builds criteria.
     *
     * @param bc the builds criteria.
     */
    public void setCriteria(BuildsCriteria bc) {
        criteria = bc;
    }

    /**
     * Gets the time line builds.
     *
     * @return the time line builds.
     */
    public List<TimelineBuild> getDashboardBuilds() {
        if (criteria != null) {
            BuildCriteria tmp = new BuildCriteria();
            tmp.application = criteria.app;
            tmp.params = false;
            tmp.mavenVersion = criteria.version;
            tmp.orderByDate = true;
            return buildService.getDashboardBuilds(tmp);
        }
        return null;
    }

    /**
     * Gets the dashboard application.
     *
     * @param project the project.
     * @param application the application.
     * @return the dashboard application.
     */
    private DashboardApplication getApplication(String project, String application) {
        if (dashboard != null) {
            DashboardProject p = dashboard.projects.get(project);
            if (p != null) {
                return p.applications.get(application);
            }
        }
        return null;
    }

    /**
     * Disable the message in the dashboard.
     */
    public void disableMsg() {
        if (dashboard != null) {
            dashboard.msg = true;
        }
    }

    /**
     * Updates the system build.
     *
     * @param system the system GUID.
     * @return the updated dashboard application system.
     */
    public DashboardApplicationSystem updateSystemBuild(String system) {
        DashboardApplicationSystem result = systems.get(system);
        if (result != null) {
            DashboardSystemBuild build = service.updateSystemBuild(system);
            result.systemBuild = build;
        }
        return result;
    }

    /**
     * Gets the dashboard.
     *
     * @return the dashboard.
     */
    public Dashboard getDashboard() {
        if (dashboard == null) {
            return reload();
        }
        return dashboard;
    }

    /**
     * Reloads the dashboard.
     *
     * @return the dashboard.
     */
    public Dashboard reload() {
        Dashboard tmp = service.getDashboard();
        if (dashboard != null && tmp != null) {
            tmp.msg = dashboard.msg;
        }
        dashboard = tmp;
        systems = service.updateSystems(dashboard);
        return dashboard;
    }

}
