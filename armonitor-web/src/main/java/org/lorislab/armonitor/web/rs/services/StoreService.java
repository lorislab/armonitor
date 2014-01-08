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

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.web.rs.model.AppSystem;
import org.lorislab.armonitor.web.rs.model.Project;

/**
 *
 * @author Andrej Petras
 */
@Path("store")
public class StoreService {
    
    @Inject
    private StoreController controller;
    
    @GET
    @Path("projects")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> getProjects() {
        return controller.getProjects();
    }
    
    @GET
    @Path("projects/reload")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> reloadProjects() {
        return controller.reloadProjects();
    }
    
    @GET
    @Path("system/update/{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public AppSystem updateSystem(@PathParam("guid") String guid) {
        return controller.updateSystem(guid);
    }
}
