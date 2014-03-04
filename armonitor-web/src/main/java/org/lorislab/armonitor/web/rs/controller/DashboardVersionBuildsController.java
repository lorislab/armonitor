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
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.lorislab.armonitor.web.rs.ejb.DashboardVersionBuildsServiceBean;
import org.lorislab.armonitor.web.rs.model.DashboardAppBuilds;
import org.lorislab.armonitor.web.rs.model.DashboardVersionBuilds;

/**
 * The dashboard version builds service controller.
 * 
 * @author Andrej Petras
 */
@Named
@SessionScoped
public class DashboardVersionBuildsController implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -7979821270894998990L;
    
    /**
     * The dashboard system service.
     */
    @EJB
    private DashboardVersionBuildsServiceBean service;
    /**
     * The application GUID.
     */
    private String app;
    /**
     * The version.
     */
    private String version;    
    /**
     * The dashboard version builds model.
     */
    private DashboardVersionBuilds builds;

    /**
     * Gets the version builds.
     *
     * @param app the application GUID.
     * @param version the version.
     * @return the version builds.
     */
    public DashboardVersionBuilds getVersion(String app, String version) {
        if (this.app == null || !this.app.equals(app) || this.version == null || !this.version.equals(version)) {
            reloadVersion(app, version);
        }
        return builds;
    }

    /**
     * Reloads the version builds.
     *
     * @param app the application GUID.
     * @param version the version.
     * @return the version builds.
     */
    public DashboardVersionBuilds reloadVersion(String app, String version) {
        builds = service.getVersion(app, version);
        this.app = app;
        this.version = version;
        return builds;
    }   
    
}
