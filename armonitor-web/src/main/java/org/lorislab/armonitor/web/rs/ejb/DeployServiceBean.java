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
package org.lorislab.armonitor.web.rs.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.process.ejb.ProcessServiceBean;
import org.lorislab.armonitor.store.criteria.StoreSystemBuildCriteria;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemBuildServiceBean;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.store.model.enums.StoreSystemBuildType;
import org.lorislab.armonitor.web.rs.model.DeployRequest;
import org.lorislab.armonitor.web.rs.model.DeploySystemBuild;
import org.lorislab.armonitor.web.rs.model.DeploySystemBuilds;
import org.lorislab.armonitor.web.rs.resources.Errors;
import org.lorislab.armonitor.web.rs.wrapper.DeploySystemBuildWrapper;
import org.lorislab.jel.ejb.exception.ServiceException;

/**
 * The deploy service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DeployServiceBean {

    /**
     * The process service.
     */
    @EJB
    private ProcessServiceBean service;

    /**
     * The store system build service.
     */
    @EJB
    private StoreSystemBuildServiceBean systemBuildService;

    /**
     * The application service.
     */
    @EJB
    private StoreApplicationServiceBean appService;

    /**
     * Deploy the build on the system and send notification.
     *
     * @param request the deploy request.
     * @return the deploy system build.
     * 
     * @throws ServiceException if the method fails.
     */
    public DeploySystemBuild deploy(DeployRequest request) throws ServiceException {
        if (request == null) {
            throw new ServiceException(Errors.DEPLOY_REQUEST_IS_NULL);
        }
        // deploy the build on the system
        StoreSystemBuild ssb = service.deploy(request.system, request.build, StoreSystemBuildType.MANUAL);
        // send notification
        if (request.notification) {
            service.sendNotificationForSystemBuild(ssb.getGuid());
        }
        
        return getSystemBuild(request.system, request.build);
    }

    /**
     * Gets the system builds for the system.
     *
     * @param sys the system GUID.
     * @return the system builds for the system.
     *
     * @throws ServiceException if the method fails.
     */
    public DeploySystemBuilds getSystemBuilds(String sys) throws ServiceException {
        StoreApplication app = appService.getApplicationForDeployment(sys);
        return Mapper.map(app, DeploySystemBuilds.class);
    }

    /**
     * Gets the system build information.
     *
     * @param sys the system GUID.
     * @param build the build GUID.
     * @return the deploy system build.
     * @throws ServiceException if the method fails.
     */
    public DeploySystemBuild getSystemBuild(String sys, String build) throws ServiceException {

        // load current system build
        StoreSystemBuildCriteria ssbc = new StoreSystemBuildCriteria();
        ssbc.setSystem(sys);
        ssbc.setFetchBuild(true);
        ssbc.setMaxDate(Boolean.TRUE);
        StoreSystemBuild ssb = systemBuildService.getSystemBuild(ssbc);

        // load the project, application, system and build information
        StoreApplication app = appService.getApplicationForDeployment(sys, build);

        // create wrapper
        DeploySystemBuildWrapper wrapper = new DeploySystemBuildWrapper();
        wrapper.setApplication(app);
        wrapper.setSystemBuild(ssb);

        // create the result
        return Mapper.map(wrapper, DeploySystemBuild.class);
    }

}
