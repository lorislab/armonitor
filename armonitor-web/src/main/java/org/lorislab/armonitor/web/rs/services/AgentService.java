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
import org.lorislab.armonitor.store.ejb.StoreAgentServiceBean;
import org.lorislab.armonitor.store.model.StoreAgent;
import org.lorislab.armonitor.util.MappingStrategy;
import org.lorislab.armonitor.web.rs.model.AgentChangePasswordRequest;

/**
 *
 * @author Andrej Petras
 */
@Path("agent")
public class AgentService {

    @EJB
    private StoreAgentServiceBean service;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public StoreAgent create() throws Exception {
        return new StoreAgent();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void changePassword(AgentChangePasswordRequest reqeust) {
        StoreAgent tmp = service.getAgent(reqeust.guid);
        if (tmp != null) {
            String password = tmp.getPassword();
            if (password == null || password.equals(reqeust.old)) {
                tmp.setPassword(reqeust.p1);
                service.saveAgent(tmp);
            }
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public StoreAgent save(StoreAgent agent) throws Exception {
        return service.saveAgent(agent);
    }

    @GET
    @Path("{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public StoreAgent get(@PathParam("guid") String guid) throws Exception {
        return service.getAgent(guid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<StoreAgent> get() {
        return service.getAgents();
    }
}
