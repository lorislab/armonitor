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

package org.lorislab.armonitor.jira.rs.services;

import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.config.ejb.ConfigurationServiceBean;
import org.lorislab.armonitor.config.rs.model.JiraConfig;
import org.lorislab.armonitor.jira.rs.controller.JiraController;
import org.lorislab.armonitor.jira.rs.model.JiraProject;

/**
 *
 * @author Andrej Petras
 */
@Path("jira")
public class JiraService {

    @Inject
    private JiraController controller;
    
    @EJB
    private ConfigurationServiceBean service;
    
    @GET
    @Path("projects")
    @Produces(MediaType.APPLICATION_JSON)    
    public List<JiraProject> getProjects() {
        return controller.getProjects();
    }

    @GET
    @Path("reload")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JiraProject> reloadProjects() {
        return controller.reloadProjects();
    }
    
    @Path("cf")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JiraConfig getJiraConfig() {
        return service.getConfiguration(JiraConfig.class);
    }
    
    @Path("cf")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JiraConfig setJiraConfig(JiraConfig jiraConfig) {
        return service.setConfiguration(jiraConfig);
    }
}
