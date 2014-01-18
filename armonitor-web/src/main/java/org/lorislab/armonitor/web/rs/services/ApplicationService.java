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
import org.lorislab.armonitor.web.rs.ejb.ApplicationServiceBean;
import org.lorislab.armonitor.web.rs.model.Application;

/**
 *
 * @author Andrej Petras
 */
@Path("app")
public class ApplicationService {
    
    @EJB
    private ApplicationServiceBean service;
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Application create() throws Exception {
        return service.create();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Application save(Application app) throws Exception {
        return service.save(app);
    }

    @GET
    @Path("{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Application get(@PathParam("guid") String guid) throws Exception {
        return service.get(guid);
    }  
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Application> get() throws Exception {
        return service.get();
    }    
}
