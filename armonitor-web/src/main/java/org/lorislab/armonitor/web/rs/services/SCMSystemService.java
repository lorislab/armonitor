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
import javax.inject.Inject;
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
import org.lorislab.armonitor.web.rs.controller.MessageController;
import org.lorislab.armonitor.web.rs.ejb.SCMSystemServiceBean;
import org.lorislab.armonitor.web.rs.model.Application;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;
import org.lorislab.armonitor.web.rs.model.SCMSystem;
import org.lorislab.armonitor.web.rs.resources.Messages;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The SCM system rest service.
 * 
 * @author Andrej Petras
 */
@Path("ad/scm")
@CdiServiceMethod
public class SCMSystemService {
   
    /**
     * The SCM system service.
     */
    @EJB
    private SCMSystemServiceBean service;
    
    /**
     * The test service.
     */
    @EJB
    private TestServiceBean testService;
    
    @Inject
    private MessageController msg;
    
    /**
     * Gets the list of the source code systems.
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
    @Path("{guid}/app/{app}")
    @Produces(MediaType.APPLICATION_JSON)
    public void addApplication(@PathParam("guid") String guid, @PathParam("app") String app) throws Exception {
        service.addApplication(guid, app);
    }
    
    @GET
    @Path("{guid}/app")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Application> getApplications(@PathParam("guid") String guid) throws Exception {
        return service.getApplications(guid);
    }  
    
    @GET
    @Path("types")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getTypes() throws Exception {
        return service.getTypes();
    }
    
    @POST
    @Path("{guid}/password")
    @Consumes(MediaType.APPLICATION_JSON)
    public void changePassword(@PathParam("guid") String guid, ChangePasswordRequest reqeust) throws Exception {
        service.changePassword(guid, reqeust);
    }
    
    @PUT
    @Path("{guid}/password")
    @Produces(MediaType.APPLICATION_JSON)
    public ChangePasswordRequest createchangePassword(@PathParam("guid") String guid) throws Exception {
        return new ChangePasswordRequest();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SCMSystem> get() throws Exception {
        return service.get();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public SCMSystem create() throws Exception {
        return service.create();
    }

    @GET
    @Path("{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public SCMSystem get(@PathParam("guid") String guid) throws Exception {
        return service.get(guid);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SCMSystem save(SCMSystem scm) throws Exception {
        SCMSystem result = service.save(scm);
        msg.addMessage(Messages.SCM_SAVE, scm.guid, scm.server);
        return result;
    }
    
    @DELETE
    @Path("{guid}")
    public void delete(@PathParam("guid") String guid) throws Exception {
        service.delete(guid);
        msg.addMsgRef(Messages.SCM_DELETE, guid);        
    }
    
    @GET
    @Path("{guid}/test")
    @Produces(MediaType.APPLICATION_JSON)
    public void test(@PathParam("guid") String guid) throws Exception {
        testService.testSCM(guid);
    }    
}
