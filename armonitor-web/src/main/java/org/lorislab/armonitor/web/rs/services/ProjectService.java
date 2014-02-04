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

/**
 *
 * @author Andrej Petras
 */
@Path("project")
public class ProjectService {
    
    @EJB
    private ProjectServiceBean service;
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Project create() throws Exception {
        return service.create();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Project save(Project project) throws Exception {
        return service.save(project);
    }

    @GET
    @Path("{guid}/app")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Application> getApplications(@PathParam("guid") String guid) throws Exception {
        return service.getApplications(guid);
    }   
    
    @GET
    @Path("{guid}/bts")
    @Produces(MediaType.APPLICATION_JSON)
    public BTSystem getBTSystem(@PathParam("guid") String guid) throws Exception {
        return service.getBTSystem(guid);
    }
    
    @PUT
    @Path("{guid}/bts/{bts}")
    public void addBTSystem(@PathParam("guid") String guid, @PathParam("bts") String bts) throws Exception {
        service.addBTSystem(guid, bts);
    }
    
    @GET
    @Path("{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Project get(@PathParam("guid") String guid) throws Exception {
        return service.get(guid);
    }  
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> get() throws Exception {
        return service.get();
    }       
}
