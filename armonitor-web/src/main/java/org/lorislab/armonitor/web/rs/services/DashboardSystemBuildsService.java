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
import org.lorislab.armonitor.web.rs.controller.DashboardSystemBuildsController;
import org.lorislab.armonitor.web.rs.model.DashboardSystemBuilds;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The dashboard system builds rest-service.
 *
 * @author Andrej Petras
 */
@Path("dsb")
@CdiServiceMethod
public class DashboardSystemBuildsService {

    /**
     * The dashboard system service controller.
     */
    @Inject
    private DashboardSystemBuildsController controller;

    /**
     * Gets the dashboard system.
     *
     * @param guid the system GUID.
     * @return the dashboard system.
     */
    @Path("{guid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DashboardSystemBuilds getSystem(@PathParam("guid") String guid) {
        return controller.getSystem(guid);
    }

    /**
     * Reloads the dashboard system.
     *
     * @param guid the system GUID.
     * @return the dashboard system.
     */
    @Path("{guid}/reload")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DashboardSystemBuilds reloadSystem(@PathParam("guid") String guid) {
        return controller.reloadSystem(guid);
    }
}
