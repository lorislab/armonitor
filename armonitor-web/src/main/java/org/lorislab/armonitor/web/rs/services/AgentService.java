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
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.process.ejb.TestServiceBean;
import org.lorislab.armonitor.web.rs.ejb.AgentServiceBean;
import org.lorislab.armonitor.web.rs.model.Agent;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;
import org.lorislab.armonitor.web.rs.model.Build;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The agent rest-service.
 * 
 * @author Andrej Petras
 */
@Path("ad/agent")
@CdiServiceMethod
public class AgentService {

    /**
     * The agent service.
     */
    @EJB
    private AgentServiceBean service;
    
    /**
     * Gets the list of the agents.
     *
     * @return the map of ID and name.
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getList() {
        return service.getList();
    } 
    
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
    @Path("{guid}/sys")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ApplicationSystem> getSystem(@PathParam("guid") String guid) throws Exception {
        return service.getSystems(guid);
    }
    
    @PUT
    @Path("{guid}/sys/{sys}")
    public void addSystem(@PathParam("guid") String guid, @PathParam("sys") String sys) throws Exception {
        service.addSystem(guid, sys);
    }
       
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Agent> get() throws Exception {
        return service.get();
    }

    @DELETE
    @Path("{guid}")
    public void delete(@PathParam("guid") String guid) throws Exception {
        service.delete(guid);      
    }     
    
    @GET
    @Path("types")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getTypes() throws Exception {
        return service.getTypes();
    }
    
    @GET
    @Path("{guid}/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Build test(@PathParam("guid") String guid) throws Exception {
        return service.testConnection(guid);
    } 
    
    @GET
    @Path("{guid}/services")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Build> services(@PathParam("guid") String guid) throws Exception {
        return service.getServices(guid);
    }     
}
