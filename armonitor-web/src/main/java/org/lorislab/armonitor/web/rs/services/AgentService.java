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
import org.lorislab.armonitor.web.rs.ejb.AgentServiceBean;
import org.lorislab.armonitor.web.rs.model.Agent;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;

/**
 *
 * @author Andrej Petras
 */
@Path("agent")
public class AgentService {

    @EJB
    private AgentServiceBean service;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Agent create() throws Exception {
        return service.create();
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
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Agent save(Agent agent) throws Exception {
        return service.save(agent);
    }

    @GET
    @Path("{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Agent get(@PathParam("guid") String guid) throws Exception {
        return service.get(guid);
    }

    @GET
    @Path("{guid}/system")
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationSystem getSystem(@PathParam("guid") String guid) throws Exception {
        return service.getSystem(guid);
    }
    
    @PUT
    @Path("{guid}/sys/{sys}")
    public void addSystem(@PathParam("guid") String guid, @PathParam("sys") String sys) throws Exception {
        service.addSystem(guid, sys);
    }
    
    @GET
    @Path("system/{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Agent getBySystem(@PathParam("guid") String guid) throws Exception {
        return service.getBySystem(guid);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Agent> get() throws Exception {
        return service.get();
    }

}
