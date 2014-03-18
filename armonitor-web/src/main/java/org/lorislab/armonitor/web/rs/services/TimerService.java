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
import javax.ejb.Timer;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.config.ejb.ConfigurationServiceBean;
import org.lorislab.armonitor.timer.ejb.TimerServiceBean;
import org.lorislab.armonitor.timer.model.TimerConfig;
import org.lorislab.armonitor.web.rs.model.TimerStatus;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The timer rest service.
 * 
 * @author Andrej Petras
 */
@Path("ad/timer")
@CdiServiceMethod
public class TimerService {
    
    @EJB
    private ConfigurationServiceBean configService;
    
    @EJB
    private TimerServiceBean timerService;
    
    @Path("start")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TimerStatus start() throws Exception {
        timerService.start();
        return status();
    }
    
    @Path("stop")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TimerStatus stop() throws Exception {
        timerService.stop();
        return status();
    }
    
    @Path("status")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TimerStatus status() throws Exception {
        TimerStatus result = new TimerStatus();        
        Timer timer = timerService.getTimer();
        if (timer != null) {
            result.run = true;
            result.next = timer.getNextTimeout();
        }
        return result;
    }
    
    @Path("cf")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TimerConfig getConfig() throws Exception {
        return configService.getConfiguration(TimerConfig.class);
    }
    
    @Path("cf")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TimerConfig setConfig(TimerConfig jiraConfig) throws Exception {
        return configService.setConfiguration(jiraConfig);
    }    
}
