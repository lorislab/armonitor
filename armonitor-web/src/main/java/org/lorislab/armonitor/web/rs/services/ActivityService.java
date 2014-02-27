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
import org.lorislab.armonitor.web.rs.controller.ActivityController;
import org.lorislab.armonitor.web.rs.model.Activity;

/**
 * The activity rest service.
 *
 * @author Andrej Petras
 */
@Path("ac")
public class ActivityService {

    /**
     * The activity controller.
     */
    @Inject
    private ActivityController controller;

    /**
     * Gets the activity for the build.
     *
     * @param guid the build GUID.
     * @return the activity for the build.
     */
    @GET
    @Path("/build/{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Activity getActivityForBuild(@PathParam("guid") String guid) {
        return controller.getActivity(guid);
    }
    
    /**
     * Reloads the activity for the build.
     *
     * @param guid the build GUID.
     * @return the activity for the build.
     */
    @GET
    @Path("/build/{guid}/reload")
    @Produces(MediaType.APPLICATION_JSON)
    public Activity reloadActivityForBuild(@PathParam("guid") String guid) {
        return controller.reloadActivity(guid);
    }    
}
