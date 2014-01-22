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
import org.lorislab.armonitor.ejb.ProcessServiceBean;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.rs.model.Request;
import org.lorislab.armonitor.rs.model.Result;
import org.lorislab.armonitor.rs.model.Status;
import org.lorislab.armonitor.rs.service.MonitorService;
import org.lorislab.armonitor.store.model.StoreBuild;

/**
 *
 * @author Andrej Petras
 */
public class MonitorServiceImpl implements MonitorService {

    @EJB
    private ProcessServiceBean service;
    
    @Override
    public Result buildRequest(Request request) throws Exception {
        Result result = new Result();
        result.status = Status.ERROR;
        if (request != null) {
            try {
                StoreBuild tmp = Mapper.map(request.version, StoreBuild.class);
                service.process(request.key, tmp);                                        
                result.status = Status.OK;
            } catch (Exception ex) {
                result.message = ex.getMessage();
            }
        } else {
            result.message = "Missing request object!";
        }
        return result;
    }
    
}
