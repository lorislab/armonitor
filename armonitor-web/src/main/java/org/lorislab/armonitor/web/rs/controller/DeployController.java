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
package org.lorislab.armonitor.web.rs.controller;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.web.rs.ejb.DeployServiceBean;
import org.lorislab.armonitor.web.rs.model.Dashboard;
import org.lorislab.armonitor.web.rs.model.DashboardApplication;
import org.lorislab.armonitor.web.rs.model.DashboardProject;
import org.lorislab.armonitor.web.rs.model.DeployRequest;
import org.lorislab.armonitor.web.rs.model.DeploySystem;
import org.lorislab.armonitor.web.rs.model.DeploySystemBuild;
import org.lorislab.armonitor.web.rs.model.DeploySystemBuilds;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The deployment controller.
 *
 * @author Andrej Petras
 */
@Named
@SessionScoped
@CdiServiceMethod
public class DeployController implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -3762775047914313462L;
    /**
     * The deployment service.
     */
    @EJB
    private DeployServiceBean service;

    /**
     * The dashboard service.
     */
    @Inject
    private DashboardController dashboardController;

    /**
     * The system builds.
     */
    private DeploySystemBuilds systemBuilds;

    /**
     * The system build.
     */
    private DeploySystemBuild systemBuild;

    /**
     * Deploys the build on the system.
     *
     * @param request the deployment request.
     * @throws Exception if the method fails.
     */
    public void deploy(DeployRequest request) throws Exception {
        service.deploy(request);
    }

    /**
     * Gets the list of system for the deployment.
     *
     * @return the list of the systems.
     * @throws Exception if the method fails.
     */
    public Set<DeploySystem> getSystems() throws Exception {
        Set<DeploySystem> result = new HashSet<>();
        Dashboard ds = dashboardController.getDashboard();
        if (ds != null) {
            if (0 < ds.size) {
                if (ds.projects != null) {
                    for (DashboardProject p : ds.projects) {
                        if (p.applications != null) {
                            for (DashboardApplication a : p.applications) {
                                Set<DeploySystem> tmp = Mapper.map(a.systems, DeploySystem.class);
                                if (tmp != null) {
                                    result.addAll(tmp);
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Gets the system builds from session or load new system builds.
     *
     * @param sys the system GUID.
     * @param reload the reload flag.
     * @return the system builds.
     * @throws Exception if the method fails.
     */
    public DeploySystemBuilds getSystemBuilds(String sys, boolean reload) throws Exception {
        if (reload) {
            systemBuilds = null;
        }
        if (sys == null) {
            systemBuilds = null;
        } else {
            if (systemBuilds == null || !systemBuilds.guid.equals(sys)) {
                systemBuilds = service.getSystemBuilds(sys);
            }
        }
        return systemBuilds;
    }  

    /**
     * Gets the system build.
     *
     * @param sys the system GUID.
     * @param build the build GUID.
     * @param reload the reload flag.
     * @return the system build.
     * @throws Exception if the method fails.
     */
    public DeploySystemBuild getSystemBuild(String sys, String build, boolean reload) throws Exception {
        if (reload) {
            systemBuild = null;
        }
        if (sys == null || build == null) {
            systemBuild = null;
        } else {
            if (systemBuild == null || !systemBuild.systemGuid.equals(sys) || !systemBuild.buildGuid.equals(build)) {
                systemBuild = service.getSystemBuild(sys, build);
            }
        }
        return systemBuild;        
    }
    
}
