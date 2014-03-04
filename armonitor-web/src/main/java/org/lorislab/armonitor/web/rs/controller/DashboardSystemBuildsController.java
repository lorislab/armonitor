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
import org.lorislab.armonitor.web.rs.ejb.DashboardSystemBuildsServiceBean;
import org.lorislab.armonitor.web.rs.model.DashboardSystemBuilds;

/**
 * The dashboard system builds service controller.
 * 
 * @author Andrej Petras
 */
@Named
@SessionScoped
public class DashboardSystemBuildsController implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -7979821270894998990L;
    
    /**
     * The dashboard system service.
     */
    @EJB
    private DashboardSystemBuildsServiceBean service;
    /**
     * The system GUID.
     */
    private String guid;
    /**
     * The dashboard system model.
     */
    private DashboardSystemBuilds system;

    /**
     * Gets the system.
     *
     * @param guid the system GUID.
     * @return the system.
     */
    public DashboardSystemBuilds getSystem(String guid) {
        if (this.guid == null || !this.guid.equals(guid)) {
            reloadSystem(guid);
        }
        return system;
    }

    /**
     * Reloads the system.
     *
     * @param guid the system GUID.
     * @return the system.
     */
    public DashboardSystemBuilds reloadSystem(String guid) {
        system = service.getSystem(guid);
        this.guid = guid;
        return system;
    }   
    
}
