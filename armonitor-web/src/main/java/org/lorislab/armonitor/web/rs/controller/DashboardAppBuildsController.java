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
import org.lorislab.armonitor.web.rs.ejb.DashboardAppBuildsServiceBean;
import org.lorislab.armonitor.web.rs.model.DashboardAppBuilds;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The dashboard application builds service controller.
 * 
 * @author Andrej Petras
 */
@Named
@SessionScoped
@CdiServiceMethod
public class DashboardAppBuildsController implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -7979821270894998990L;
    
    /**
     * The dashboard system service.
     */
    @EJB
    private DashboardAppBuildsServiceBean service;
    /**
     * The application GUID.
     */
    private String guid;
    /**
     * The dashboard application model.
     */
    private DashboardAppBuilds app;

    /**
     * Gets the application.
     *
     * @param guid the application GUID.
     * @return the application.
     */
    public DashboardAppBuilds getApplication(String guid) {
        if (this.guid == null || !this.guid.equals(guid)) {
            reloadApplication(guid);
        }
        return app;
    }

    /**
     * Reloads the application.
     *
     * @param guid the application GUID.
     * @return the application.
     */
    public DashboardAppBuilds reloadApplication(String guid) {
        app = service.getApplication(guid);
        this.guid = guid;
        return app;
    }   
    
}
