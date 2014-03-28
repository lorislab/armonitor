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
import org.lorislab.armonitor.web.rs.ejb.ApplicationSystemServiceBean;
import org.lorislab.armonitor.web.rs.model.Agent;
import org.lorislab.armonitor.web.rs.model.Application;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;
import org.lorislab.armonitor.web.rs.model.Role;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 *
 * @author Andrej Petras
 */
@Path("ad/sys")
@CdiServiceMethod
public class ApplicationSystemService {

    @EJB
    private ApplicationSystemServiceBean service;
    
    @GET
    @Path("{guid}/role")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Role> getRoles(@PathParam("guid") String guid) {
        return service.getRoles(guid);
    }

    @PUT
    @Path("{guid}/role/{role}")
    @Produces(MediaType.APPLICATION_JSON)
    public void addRole(@PathParam("guid") String guid, @PathParam("role") String role) {
        service.addRole(guid, role);
    }

    @DELETE
    @Path("{guid}/role/{role}")
    @Produces(MediaType.APPLICATION_JSON)
    public void removeRole(@PathParam("guid") String guid, @PathParam("role") String role) {
        service.removeRole(guid, role);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ApplicationSystem> get() {
        return service.get();
    }

    @GET
    @Path("{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationSystem get(@PathParam("guid") String uid) {
        return service.get(uid);
    }

    @GET
    @Path("{guid}/app")
    @Produces(MediaType.APPLICATION_JSON)
    public Application getApplication(@PathParam("guid") String guid) {
        return service.getApplication(guid);
    }

    /**
     * Add the application to the system.
     *
     * @param guid the system id.
     * @param app the application id.
     * @throws Exception if the method fails.
     */
    @PUT
    @Path("{guid}/app/{app}")
    public void addApplication(@PathParam("guid") String guid, @PathParam("app") String app) throws Exception {
        service.addApplication(guid, app);
    }

    /**
     * Remove the application to the system.
     *
     * @param guid the system id.
     * @throws Exception if the method fails.
     */
    @PUT
    @Path("{guid}/app")
    public void removeApplication(@PathParam("guid") String guid) throws Exception {
        service.addApplication(guid, null);
    }

    /**
     * Gets the agent of the system
     *
     * @param guid the system id.
     * @return the corresponding agent.
     */
    @GET
    @Path("{guid}/agent")
    @Produces(MediaType.APPLICATION_JSON)
    public Agent getAgent(@PathParam("guid") String guid) {
        return service.getAgent(guid);
    }

    /**
     * Add the agent to the system.
     *
     * @param guid the system id.
     * @param agent the agent id.
     * @throws Exception if the method fails.
     */
    @PUT
    @Path("{guid}/agent/{agent}")
    public void addAgent(@PathParam("guid") String guid, @PathParam("agent") String agent) throws Exception {
        service.addAgent(guid, agent);
    }

    /**
     * Remove the agent to the system.
     *
     * @param guid the system id.
     * @throws Exception if the method fails.
     */
    @PUT
    @Path("{guid}/agent")
    public void removeAgent(@PathParam("guid") String guid) throws Exception {
        service.addAgent(guid, null);
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

    /**
     * Deletes the system key.
     * @param guid the system GUID.
     * @return the system.
     * @throws Exception if the method fails.
     */
    @DELETE
    @Path("{guid}/key")
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationSystem deleteKey(@PathParam("guid") String guid) throws Exception {
        return service.deleteKey(guid);
    }

    /**
     * Generates new key for the system.
     * @param guid the system GUID.
     * @return the system.
     * @throws Exception if the method fails.
     */    
    @GET
    @Path("{guid}/key")
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationSystem generatedKey(@PathParam("guid") String guid) throws Exception {
        return service.generatedKey(guid);
    }

    @DELETE
    @Path("{guid}")
    public void delete(@PathParam("guid") String guid) throws Exception {
        service.delete(guid);
    }
}
