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
import org.lorislab.armonitor.web.rs.ejb.ProjectServiceBean;
import org.lorislab.armonitor.web.rs.model.Application;
import org.lorislab.armonitor.web.rs.model.BTSystem;
import org.lorislab.armonitor.web.rs.model.Project;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The project rest-service.
 *
 * @author Andrej Petras
 */
@Path("ad/pr")
@CdiServiceMethod
public class ProjectService {

    /**
     * The project service.
     */
    @EJB
    private ProjectServiceBean service;

    /**
     * Creates the new project.
     *
     * @return the new created project.
     * @throws Exception if the method fails.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Project create() throws Exception {
        return service.create();
    }

    /**
     *
     * @param project
     * @return
     * @throws Exception if the method fails.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Project save(Project project) throws Exception {
        return service.save(project);
    }

    /**
     *
     * @param guid
     * @return
     * @throws Exception if the method fails.
     */
    @GET
    @Path("{guid}/app")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Application> getApplications(@PathParam("guid") String guid) throws Exception {
        return service.getApplications(guid);
    }

    /**
     *
     * @param guid
     * @param app
     * @throws Exception if the method fails.
     */
    @PUT
    @Path("{guid}/app/{app}")
    @Produces(MediaType.APPLICATION_JSON)
    public void addApplication(@PathParam("guid") String guid, @PathParam("app") String app) throws Exception {
        service.addApplication(guid, app);
    }

    /**
     *
     * @param guid
     * @return
     * @throws Exception if the method fails.
     */
    @GET
    @Path("{guid}/bts")
    @Produces(MediaType.APPLICATION_JSON)
    public BTSystem getBTSystem(@PathParam("guid") String guid) throws Exception {
        return service.getBTSystem(guid);
    }

    /**
     * Add the bug tracking system to the project.
     * 
     * @param guid the project id.
     * @param bts the bug tracking system id.
     * @throws Exception if the method fails.
     */
    @PUT
    @Path("{guid}/bts/{bts}")
    public void addBTSystem(@PathParam("guid") String guid, @PathParam("bts") String bts) throws Exception {
        service.addBTSystem(guid, bts);
    }
    
    /**
     * Add the bug tracking system to the project.
     * 
     * @param guid the project id.
     * @throws Exception if the method fails.
     */
    @PUT
    @Path("{guid}/bts")
    public void addBTSystem(@PathParam("guid") String guid) throws Exception {
        service.addBTSystem(guid, null);
    }
    
    /**
     * Gets the project by GUID.
     *
     * @param guid the GUID.
     * @return the corresponding project.
     * @throws Exception if the method fails.
     */
    @GET
    @Path("{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Project get(@PathParam("guid") String guid) throws Exception {
        return service.get(guid);
    }

    /**
     * Gets the list of all projects.
     *
     * @return the list of all projects.
     * @throws Exception if the method fails.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> get() throws Exception {
        return service.get();
    }
    
    @DELETE
    @Path("{guid}")
    public void delete(@PathParam("guid") String guid) throws Exception {
        service.delete(guid);      
    }    
}
