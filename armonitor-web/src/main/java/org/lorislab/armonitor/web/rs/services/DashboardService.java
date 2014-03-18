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

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.web.rs.controller.DashboardController;
import org.lorislab.armonitor.web.rs.model.Dashboard;
import org.lorislab.armonitor.web.rs.model.DashboardApplicationSystem;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The dashboard rest-service.
 *
 * @author Andrej Petras
 */
@Path("db")
@CdiServiceMethod
public class DashboardService {

    /**
     * The dashboard service.
     */
    @Inject
    private DashboardController controller;  
    
    /**
     * Disables the message info in the dashboard.
     * 
     * @throws Exception if the method fails.
     */
    @GET
    @Path("msg")
    @Produces(MediaType.APPLICATION_JSON)
    public void disableMsg() throws Exception {
        controller.disableMsg();
    }
    
    /**
     * Gets the list of dashboard projects.
     *
     * @return the list of dashboard projects.
     * 
     * @throws Exception if the method fails.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Dashboard getDashboard() throws Exception {
        return controller.getDashboard();
    }

    /**
     * Reloads the dashboard data.
     *
     * @return the new loaded dashboard data.
     * 
     * @throws Exception if the method fails.
     */
    @GET
    @Path("reload")
    @Produces(MediaType.APPLICATION_JSON)
    public Dashboard reload() throws Exception {
        return controller.reload();
    }

    /**
     * Updates the system build in the dashboard object.
     *
     * @param sys the system GUID.
     * @return the dashboard application system.
     * 
     * @throws Exception if the method fails.
     */
    @GET
    @Path("sys/{sys}/build")
    @Produces(MediaType.APPLICATION_JSON)
    public DashboardApplicationSystem updateSystemBuild(@PathParam("sys") String sys) throws Exception {
        return controller.updateSystemBuild(sys);
    }

}
