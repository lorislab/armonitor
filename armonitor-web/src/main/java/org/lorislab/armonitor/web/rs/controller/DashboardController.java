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
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.lorislab.armonitor.web.rs.ejb.DashboardServiceBean;
import org.lorislab.armonitor.web.rs.model.Dashboard;
import org.lorislab.armonitor.web.rs.model.DashboardApplication;
import org.lorislab.armonitor.web.rs.model.DashboardApplicationSystem;
import org.lorislab.armonitor.web.rs.model.DashboardProject;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The dashboard controller.
 *
 * @author Andrej Petras
 */
@Named
@SessionScoped
@CdiServiceMethod
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
     * The dashboard.
     */
    private Dashboard dashboard;
    /**
     * The map of the dashboard application systems.
     */
    private Map<String, DashboardApplicationSystem> systems = new HashMap<>();

    /**
     * Gets the dashboard application.
     *
     * @param project the project.
     * @param application the application.
     * @return the dashboard application.
     * 
     * @throws Exception if the method fails.
     */
    private DashboardApplication getApplication(String project, String application) throws Exception {
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
     * 
     * @throws Exception if the method fails.
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
     * 
     * @throws Exception if the method fails.
     */
    public DashboardApplicationSystem updateSystemBuild(String system) throws Exception {
        DashboardApplicationSystem result = systems.get(system);
        if (result != null) {
            DashboardApplication app = getApplication(result.project, result.application);
            service.updateSystemBuild(app, result);
        }
        return result;
    }

    /**
     * Gets the dashboard.
     *
     * @return the dashboard.
     * 
     * @throws Exception if the method fails.
     */
    public Dashboard getDashboard() throws Exception {
        if (dashboard == null) {
            return reload();
        }
        return dashboard;
    }

    /**
     * Reloads the dashboard.
     *
     * @return the dashboard.
     * 
     * @throws Exception if the method fails.
     */
    public Dashboard reload() throws Exception {
        Dashboard tmp = service.getDashboard();
        if (dashboard != null && tmp != null) {
            tmp.msg = dashboard.msg;
        }
        dashboard = tmp;
        systems = service.updateSystems(dashboard);
        return dashboard;
    }

}
