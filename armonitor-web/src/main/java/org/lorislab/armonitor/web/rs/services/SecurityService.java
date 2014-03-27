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

import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.web.rs.controller.SecurityController;
import org.lorislab.armonitor.web.rs.ejb.SecurityServiceBean;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;
import org.lorislab.armonitor.web.rs.model.LoginRequest;
import org.lorislab.armonitor.web.rs.model.User;
import org.lorislab.armonitor.web.rs.resources.Errors;
import org.lorislab.jel.base.exception.SystemException;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 *
 * @author Andrej Petras
 */
@Path("sec")
@CdiServiceMethod
public class SecurityService {

    private static final Logger LOGGER = Logger.getLogger(SecurityService.class.getName());

    @Inject
    private SecurityController controller;
    
    @EJB
    private SecurityServiceBean service;
    
    @Context
    private HttpServletRequest httpRequest;
    
    @POST
    @Path("pr/roles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Boolean> isUserInRole(Set<String> roles) {
        return service.isUserInRole(roles);
    }
    
    @GET
    @Path("pr")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser() {
        return controller.getUser();
    }
    
    @POST
    @Path("pr")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User saveUser(User user) {
        return controller.saveUser(user);
    }

    @POST
    @Path("pr/password")
    @Consumes(MediaType.APPLICATION_JSON)
    public void changePassword(ChangePasswordRequest reqeust) throws Exception {
        controller.changePassword(reqeust);
    }    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User login(LoginRequest request) throws Exception {
        User result = controller.getUser();
        if (result == null) {
            User tmp = service.getUser(request);
            if (tmp != null) {
                try {
                    httpRequest.login(tmp.guid, request.password);
                    result = controller.setUser(tmp);
                } catch (ServletException ex) {
                    LOGGER.log(Level.SEVERE, "Error login the user " + tmp.guid, ex);
                    throw new SystemException(Errors.WRONG_USER_OR_PASSWORD);
                }
            } else {
                throw new SystemException(Errors.WRONG_USER_OR_PASSWORD);
            }
        }
        return result;
    }

    @GET
    @Path("pr/logout")
    public void logout() {
        String guid = null;
        try {
            User user = controller.getUser();
            if (user != null) {
                controller.setUser(null);
                guid = user.guid;
            }
            httpRequest.getSession().invalidate();
            httpRequest.logout();
        } catch (ServletException ex) {
            LOGGER.log(Level.SEVERE, "Error logout the user " + guid, ex);
        }
    }
}
