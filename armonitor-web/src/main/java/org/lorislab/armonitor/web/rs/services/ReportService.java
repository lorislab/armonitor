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

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.lorislab.armonitor.ejb.ProcessServiceBean;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 *
 * @author Andrej Petras
 */
@Path("ad/report")
@CdiServiceMethod
public class ReportService {
   
    @EJB
    private ProcessServiceBean service;
    
    @GET
    @Path("{guid}")
    public void report(@PathParam("guid") String guid) {
        service.sendReportAsync(guid);
    }
    
}
