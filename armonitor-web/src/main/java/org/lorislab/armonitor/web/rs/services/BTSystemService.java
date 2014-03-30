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
import org.lorislab.armonitor.web.rs.ejb.BTSystemServiceBean;
import org.lorislab.armonitor.web.rs.model.BTSystem;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;
import org.lorislab.armonitor.web.rs.model.Project;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The bug tracking system rest service.
 *
 * @author Andrej Petras
 */
@Path("ad/bts")
@CdiServiceMethod
public class BTSystemService {

    /**
     * The bug tracking system service.
     */
    @EJB
    private BTSystemServiceBean service;
    
    /**
     * Gets the list of the bug tracking systems.
     *
     * @return the map of ID and name.
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getList() {
        return service.getList();
    }

    /**
     * Gets the set of project for the bug tracking system.
     *
     * @param guid the GUID.
     * @return the set of project.
     * @throws Exception if the method fails.
     */
    @GET
    @Path("{guid}/project")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Project> getApplications(@PathParam("guid") String guid) throws Exception {
        return service.getProjects(guid);
    }

    /**
     *
     * @param guid
     * @param project
     * @throws Exception if the method fails.
     */
    @PUT
    @Path("{guid}/project/{project}")
    public void addProject(@PathParam("guid") String guid, @PathParam("project") String project) throws Exception {
        service.addProject(guid, project);
    }

    @GET
    @Path("types")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getTypes() {
        return service.getTypes();
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
    public List<BTSystem> get() {
        return service.get();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public BTSystem create() {
        return service.create();
    }

    @GET
    @Path("{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public BTSystem get(@PathParam("guid") String guid) {
        return service.get(guid);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BTSystem save(BTSystem scm) {
        return service.save(scm);
    }

    @DELETE
    @Path("{guid}")
    public void delete(@PathParam("guid") String guid) throws Exception {
        service.delete(guid);
    }
    
    @GET
    @Path("{guid}/test")
    @Produces(MediaType.APPLICATION_JSON)
    public void test(@PathParam("guid") String guid) throws Exception {
        service.testConnection(guid);
    } 
    
    @GET
    @Path("{guid}/test/{project}")
    @Produces(MediaType.APPLICATION_JSON)
    public void testAccess(@PathParam("guid") String guid, @PathParam("project") String project) throws Exception {
        service.testAccess(guid, project);
    }    
}
