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

import java.util.Set;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.web.rs.controller.DeployController;
import org.lorislab.armonitor.web.rs.model.DeployRequest;
import org.lorislab.armonitor.web.rs.model.DeploySystem;
import org.lorislab.armonitor.web.rs.model.DeploySystemBuild;
import org.lorislab.armonitor.web.rs.model.DeploySystemBuilds;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The deployment rest service.
 *
 * @author Andrej Petras
 */
@Path("deploy")
@CdiServiceMethod
public class DeployService {

    /**
     * The deployment controller.
     */
    @Inject
    private DeployController controller;

    /**
     * Send deployment request.
     *
     * @param request the deployment request.
     * @return the deploy system build.
     * @throws Exception if the method fails.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DeploySystemBuild deploy(DeployRequest request) throws Exception {
        return controller.deploy(request);
    }

    /**
     * Gets the set of deployment systems.
     *
     * @return the set of deployment systems.
     * @throws Exception if the method fails.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<DeploySystem> getSystems() throws Exception {
        return controller.getSystems();
    }

    /**
     * Gets the system builds.
     *
     * @param sys the system GUID.
     * @param reload reload flag.
     * @return the system builds.
     * @throws Exception if the method fails.
     */
    @GET
    @Path("{sys}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeploySystemBuilds getSystemBuilds(@PathParam("sys") String sys, @QueryParam("reload") @DefaultValue("false") boolean reload) throws Exception {
        return controller.getSystemBuilds(sys, reload);
    }

    /**
     * Gets the system builds.
     *
     * @param sys the system GUID.
     * @param build the build GUID.
     * @param reload the reload flag
     * @return the system builds.
     * @throws Exception if the method fails.
     */
    @GET
    @Path("{sys}/{build}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeploySystemBuild getSystemBuild(@PathParam("sys") String sys, @PathParam("build") String build, @QueryParam("reload") @DefaultValue("false") boolean reload) throws Exception {
        return controller.getSystemBuild(sys, build, reload);
    }

}
