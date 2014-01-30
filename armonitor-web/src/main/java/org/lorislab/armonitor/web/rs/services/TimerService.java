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

import java.util.Date;
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

/**
 *
 * @author Andrej Petras
 */
@Path("timer")
public class TimerService {
    
    @EJB
    private ConfigurationServiceBean configService;
    
    @EJB
    private TimerServiceBean timerService;
    
    @Path("start")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Date start() {
        timerService.start();
        return status();
    }
    
    @Path("stop")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Date stop() {
        timerService.stop();
        return status();
    }
    
    @Path("status")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Date status() {
        Timer timer = timerService.getTimer();
        if (timer != null) {
            return timer.getNextTimeout();
        }
        return null;
    }
    
    @Path("cf")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TimerConfig getJiraConfig() {
        return configService.getConfiguration(TimerConfig.class);
    }
    
    @Path("cf")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TimerConfig setJiraConfig(TimerConfig jiraConfig) {
        return configService.setConfiguration(jiraConfig);
    }    
}
