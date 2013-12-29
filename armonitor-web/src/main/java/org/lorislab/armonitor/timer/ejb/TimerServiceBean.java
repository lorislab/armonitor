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

package org.lorislab.armonitor.timer.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerService;

/**
 *
 * @author Andrej Petras
 */
@Singleton
@Startup
public class TimerServiceBean {
    
    @Resource
    private TimerService timerService;
    
    @PostConstruct
    public void initialize(){
//        ScheduleExpression expression = new ScheduleExpression();
//        expression.second("*/1").minute("*").hour("*");
//        timerService.createCalendarTimer(expression);
    }

    @Timeout
    public void execute(){
//        System.out.println("----Invoked: " + System.currentTimeMillis());
    }    
    
}
