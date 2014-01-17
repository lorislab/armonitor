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
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.web.rs.ejb.ApplicationSystemServiceBean;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;

/**
 *
 * @author Andrej Petras
 */
@Path("sys")
public class ApplicationSystemService {

    @EJB
    private ApplicationSystemServiceBean service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ApplicationSystem> get() {
        return service.get();
    }

    @GET
    @Path("{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationSystem get(@PathParam("uid") String uid) {
        return service.get(uid);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationSystem create() throws Exception {
        return service.create();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApplicationSystem save(ApplicationSystem system) throws Exception {
        return service.save(system);
    }

}
