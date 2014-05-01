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

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.process.ejb.ProcessServiceBean;
import org.lorislab.armonitor.store.criteria.StoreApplicationCriteria;
import org.lorislab.armonitor.store.criteria.StoreBuildCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemCriteria;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.ejb.StoreBuildServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.store.model.enums.StoreSystemBuildType;
import org.lorislab.armonitor.web.rs.model.DeployRequest;
import org.lorislab.armonitor.web.rs.model.DeploySystemBuilds;
import org.lorislab.armonitor.web.rs.resources.Errors;
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
     * The store system service.
     */
    @EJB
    private StoreSystemServiceBean systemService;
    
    /**
     * The build service.
     */
    @EJB
    private StoreBuildServiceBean buildService;
    
    /**
     * The application service.
     */
    @EJB
    private StoreApplicationServiceBean appService;
    
    /**
     * Deploy the build on the system and send notification.
     *
     * @param request the deploy request.
     * @throws ServiceException if the method fails.
     */
    public void deploy(DeployRequest request) throws ServiceException {
        if (request == null) {
            throw new ServiceException(Errors.DEPLOY_REQUEST_IS_NULL);
        }
        // deploy the build on the system
        StoreSystemBuild ssb = service.deploy(request.system, request.build, StoreSystemBuildType.MANUAL);
        // send notification
        if (request.notification) {
            service.sendNotificationForSystemBuild(ssb.getGuid());
        }
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

}
