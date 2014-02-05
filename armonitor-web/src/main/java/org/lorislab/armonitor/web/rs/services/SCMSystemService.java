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
import org.lorislab.armonitor.web.rs.ejb.SCMSystemServiceBean;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;
import org.lorislab.armonitor.web.rs.model.SCMSystem;
import org.lorislab.armonitor.web.rs.model.enums.SCMSystemType;

/**
 *
 * @author Andrej Petras
 */
@Path("scm")
public class SCMSystemService {
    
    @EJB
    private SCMSystemServiceBean service;
    
    @GET
    @Path("types")
    @Produces(MediaType.APPLICATION_JSON)
    public SCMSystemType[] getTypes() {
        return SCMSystemType.values();
    }
    
    @POST
    @Path("{guid}/password")
    @Consumes(MediaType.APPLICATION_JSON)
    public void changePassword(@PathParam("guid") String guid, ChangePasswordRequest reqeust) {
        service.changePassword(guid, reqeust);
    }
    
    @PUT
    @Path("{guid}/password")
    @Produces(MediaType.APPLICATION_JSON)
    public ChangePasswordRequest createchangePassword(@PathParam("guid") String guid) {
        return new ChangePasswordRequest();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SCMSystem> get() {
        return service.get();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public SCMSystem create() {
        return service.create();
    }

    @GET
    @Path("{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public SCMSystem get(@PathParam("guid") String guid) {
        return service.get(guid);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SCMSystem save(SCMSystem scm) {
        return service.save(scm);
    }
}
