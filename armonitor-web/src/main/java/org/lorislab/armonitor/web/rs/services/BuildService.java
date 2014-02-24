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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.web.rs.ejb.BuildServiceBean;
import org.lorislab.armonitor.web.rs.model.Build;
import org.lorislab.armonitor.web.rs.model.BuildCriteria;

/**
 * The build rest-service.
 *
 * @author Andrej Petras
 */
@Path("build")
public class BuildService {

    /**
     * The build service.
     */
    @EJB
    private BuildServiceBean service;

    /**
     * Gets the list of builds by criteria.
     *
     * @param criteria the criteria.
     * @return the list of builds corresponding to the criteria.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Build> getBuilds(BuildCriteria criteria) {
        return service.getBuilds(criteria);
    }

    /**
     * Gets the build by GUID.
     * 
     * @param guid the build GUID.
     * @return the build.
     */
    @GET
    @Path("{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Build getBuild(@PathParam("guid") String guid) {
        return service.getBuild(guid);
    }    
}
