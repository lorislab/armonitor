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
import org.lorislab.armonitor.web.rs.ejb.UserServiceBean;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;
import org.lorislab.armonitor.web.rs.model.Role;
import org.lorislab.armonitor.web.rs.model.User;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 *
 * @author Andrej Petras
 */
@Path("ad/user")
@CdiServiceMethod
public class UserService {

    @EJB
    private UserServiceBean service;

    @GET
    @Path("{guid}/role")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Role> getRoles(@PathParam("guid") String guid) {
        return service.getRoles(guid);
    }

    @PUT
    @Path("{guid}/role/{role}")
    public void addRole(@PathParam("guid") String guid, @PathParam("role") String role) {
        service.addRole(guid, role);
    }

    @DELETE
    @Path("{guid}/role/{role}")
    public void removeRole(@PathParam("guid") String guid, @PathParam("role") String role) {
        service.removeRole(guid, role);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> get() {
        return service.get();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public User create() {
        return service.create();
    }

    /**
     * Gets the user by GUID.
     *
     * @param guid the user GUID.
     * @return the user corresponding to the GUID.
     */
    @GET
    @Path("{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public User get(@PathParam("guid") String guid) {
        return service.get(guid);
    }

    /**
     * Saves the user.
     *
     * @param user the user.
     * @return the saved user.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User save(User user) {
        return service.save(user);
    }
    
    @POST
    @Path("{guid}/password")
    @Consumes(MediaType.APPLICATION_JSON)
    public void changePassword(@PathParam("guid") String guid, ChangePasswordRequest reqeust) throws Exception {
        service.changePassword(guid, reqeust);
    }

    @PUT
    @Path("{guid}/password")
    @Consumes(MediaType.APPLICATION_JSON)
    public void resetPassword(@PathParam("guid") String guid, ChangePasswordRequest reqeust) throws Exception {
        service.resetPassword(guid, reqeust);
    }
    
    @DELETE
    @Path("{guid}")
    public void delete(@PathParam("guid") String guid) throws Exception {
        service.delete(guid);      
    }      
}
