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
import org.lorislab.armonitor.web.rs.controller.DashboardAppBuildsController;
import org.lorislab.armonitor.web.rs.model.DashboardAppBuilds;

/**
 * The dashboard application builds rest-service.
 *
 * @author Andrej Petras
 */
@Path("dab")
public class DashboardAppBuildsService {

    /**
     * The dashboard application service controller.
     */
    @Inject
    private DashboardAppBuildsController controller;

    /**
     * Gets the dashboard application.
     *
     * @param guid the application GUID.
     * @return the dashboard application.
     */
    @Path("{guid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DashboardAppBuilds getApplication(@PathParam("guid") String guid) {
        return controller.getApplication(guid);
    }

    /**
     * Reloads the dashboard application.
     *
     * @param guid the application GUID.
     * @return the dashboard application.
     */
    @Path("{guid}/reload")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DashboardAppBuilds reloadApplication(@PathParam("guid") String guid) {
        return controller.reloadApplication(guid);
    }
}
