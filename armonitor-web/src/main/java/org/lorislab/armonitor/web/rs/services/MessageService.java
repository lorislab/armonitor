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

import java.util.Collection;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.web.rs.controller.MessageController;
import org.lorislab.armonitor.web.rs.model.Message;
import org.lorislab.armonitor.web.rs.model.MessageInfo;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The message rest service.
 * 
 * @author Andrej Petras
 */
@Path("msg")
@CdiServiceMethod
public class MessageService {
    
    @Inject
    private MessageController controller;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Message> getMessages() {
        return controller.getMessages();
    }
    
    @GET
    @Path("info")
    @Produces(MediaType.APPLICATION_JSON)
    public MessageInfo getInfo() {
        return controller.getInfo();
    }
    
    @GET
    @Path("close")
    @Produces(MediaType.APPLICATION_JSON)
    public MessageInfo close() {
        return controller.close();
    } 
    
    @GET
    @Path("trash")
    @Produces(MediaType.APPLICATION_JSON)
    public  MessageInfo trash() {
        return controller.trash();
    } 
    
    @GET
    @Path("trash/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MessageInfo trash(@PathParam("id") String id) {
        return controller.trash(id);
    }     
}
