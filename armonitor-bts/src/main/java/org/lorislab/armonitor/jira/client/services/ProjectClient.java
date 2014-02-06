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
package org.lorislab.armonitor.jira.client.services;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.jira.client.model.ProjectIssue;
import org.lorislab.armonitor.jira.client.model.Project;
import org.lorislab.armonitor.jira.client.model.Version;

/**
 * The JIRA project client.
 *
 * @author Andrej Petras
 */
@Path("project")
public interface ProjectClient {

    /**
     * Gets the list of all projects.
     *
     * @return the list of all projects.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Project> getProjects();

    /**
     * Gest the project by
     *
     * @param id the project id.
     * @return the project.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Project getProject(@PathParam("id") String id);

    /**
     * Gets all version of the project.
     *
     * @param id the project id.
     * @return the list of all version for the project.
     */
    @GET
    @Path("{id}/versions")
    @Produces(MediaType.APPLICATION_JSON)
    List<Version> getVersions(@PathParam("id") String id);

    /**
     * Gets the list of project issue statuses.
     *
     * @param id the project id.
     * @return the list of project issue statuses.
     */
    @GET
    @Path("{id}/statuses")
    @Produces(MediaType.APPLICATION_JSON)
    List<ProjectIssue> getStatuses(@PathParam("id") String id);
}
