/*
 * Copyright 2013 lorislab.org.
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
package org.lorislab.armonitor.agent.rs.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.agent.rs.model.Request;
import org.lorislab.armonitor.agent.rs.model.Version;

/**
 * The version rest-service.
 *
 * @author Andrej Petras
 */
@Path("lorislab/ver")
public interface VersionService {

    /**
     * Gets the agent version.
     *
     * @param manifest the manifest flag.
     * @return the version of the agent.
     * @throws Exception if the method fails.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Version getAgentVersion(@QueryParam("manifest") boolean manifest) throws Exception;

    /**
     * Gets all versions.
     *
     * @param request the request.
     * @return the list of version.
     * @throws Exception if the method fails.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Version> getVersion(Request request) throws Exception;
}
