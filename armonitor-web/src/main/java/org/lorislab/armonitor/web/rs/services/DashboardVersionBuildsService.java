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
import org.lorislab.armonitor.web.rs.controller.DashboardVersionBuildsController;
import org.lorislab.armonitor.web.rs.model.DashboardVersionBuilds;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The dashboard version builds rest-service.
 *
 * @author Andrej Petras
 */
@Path("dvb")
@CdiServiceMethod
public class DashboardVersionBuildsService {

    /**
     * The dashboard version service controller.
     */
    @Inject
    private DashboardVersionBuildsController controller;

    /**
     * Gets the dashboard version builds.
     *
     * @param app the application GUID.
     * @param version the version.
     * @return the dashboard application.
     */
    @Path("{app}/{version}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DashboardVersionBuilds getVersion(@PathParam("app") String app, @PathParam("version") String version) {
        return controller.getVersion(app, version);
    }

    /**
     * Reloads the dashboard version builds.
     *
     * @param app the application GUID.
     * @param version the version.
     * @return the dashboard application.
     */
    @Path("{app}/{version}/reload")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DashboardVersionBuilds reloadVersion(@PathParam("app") String app, @PathParam("version") String version) {
        return controller.reloadVersion(app, version);
    }
}
