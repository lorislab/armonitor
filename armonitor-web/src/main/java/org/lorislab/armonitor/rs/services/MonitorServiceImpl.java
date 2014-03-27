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
package org.lorislab.armonitor.rs.services;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import org.lorislab.armonitor.process.ejb.ProcessServiceBean;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.rs.model.Request;
import org.lorislab.armonitor.rs.model.Result;
import org.lorislab.armonitor.rs.model.Status;
import org.lorislab.armonitor.rs.service.MonitorService;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.store.model.enums.StoreSystemBuildType;
import org.lorislab.jel.base.resources.ResourceManager;
import org.lorislab.jel.ejb.exception.ServiceException;

/**
 * The monitor rest-service implementation.
 *
 * @author Andrej Petras
 */
public class MonitorServiceImpl implements MonitorService {

    /**
     * The process service.
     */
    @EJB
    private ProcessServiceBean service;

    /**
     * The HTTP request.
     */
    @Context
    private HttpServletRequest httpRequest;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Result deploy(Request request) {
        Result result = new Result();
        result.status = Status.ERROR;
        if (request != null) {
            try {
                StoreBuild tmp = Mapper.map(request.version, StoreBuild.class);
                // deploy the build
                StoreSystemBuild ssb = service.deploy(request.key, tmp, StoreSystemBuildType.MANUAL);
                // send notification
                if (ssb != null) {
                    service.sendNotificationForSystemBuild(ssb.getGuid());                                
                }
                result.status = Status.OK;
            } catch (ServiceException ex) {
                result.message = ResourceManager.getMessage(ex.getResourceMessage(), httpRequest.getLocale());
            }
        } else {
            result.message = "Missing request object!";
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Result install(Request request) {
        Result result = new Result();
        result.status = Status.ERROR;
        if (request != null) {
            try {
                StoreBuild tmp = Mapper.map(request.version, StoreBuild.class);
                service.install(request.key, tmp);
                result.status = Status.OK;
            } catch (ServiceException ex) {
                result.message = ResourceManager.getMessage(ex.getResourceMessage(), httpRequest.getLocale());
            }
        } else {
            result.message = "Missing request object!";
        }
        return result;
    }

}
