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

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.config.ejb.ConfigurationServiceLocal;
import org.lorislab.armonitor.timer.model.TimerConfig;

/**
 *
 * @author Andrej Petras
 */
@Singleton
@Startup
@TransactionAttribute(TransactionAttributeType.NEVER)
public class TimerServiceBean {

    private static final String AGENT_TIMER_INFO = TimerServiceBean.class.getName();

    private static final Logger LOGGER = Logger.getLogger(TimerServiceBean.class.getName());

    @Resource
    private TimerService timerService;

    @EJB
    private ConfigurationServiceLocal configService;
    
    @EJB
    private TimerProcessServiceBean processService;
    
    @PostConstruct
    public void initialize() {
        start();
    }

    public void restart() {
        stop();
        start();
    }

    public void start() {
        Timer timer = getTimer();
        if (timer == null) {
            TimerConfig config = configService.getConfiguration(TimerConfig.class);
            if (config.start) {
                ScheduleExpression expression = new ScheduleExpression();
                expression.second(config.second).minute(config.minute).hour(config.hour);

                javax.ejb.TimerConfig timerConfig = new javax.ejb.TimerConfig(AGENT_TIMER_INFO, false);
                Timer tmp = timerService.createCalendarTimer(expression, timerConfig);
                
                LOGGER.log(Level.INFO, "The timer service was started with next timeout: {0}", tmp.getNextTimeout());
            } else {
                LOGGER.log(Level.INFO, "The timer {0} is not started.", AGENT_TIMER_INFO);
            }
        } else {
            LOGGER.log(Level.INFO, "The timer {0} is all ready running.", AGENT_TIMER_INFO);
        }
    }

    public void stop() {
        Timer timer = getTimer();
        if (timer != null) {
            timer.cancel();
            LOGGER.log(Level.INFO, "The timer {0} was cancelled.", AGENT_TIMER_INFO);
        } else {
            LOGGER.log(Level.INFO, "The timer {0} is not running.", AGENT_TIMER_INFO);
        }
    }

    public Timer getTimer() {
        Collection<Timer> timers = timerService.getTimers();
        for (Timer timer : timers) {
            if (AGENT_TIMER_INFO.equals(timer.getInfo())) {
                return timer;
            }
        }
        return null;
    }

    @Timeout
    public void execute() {
        TimerConfig config = configService.getConfiguration(TimerConfig.class);
        if (config.enabled) {
            processService.timerService();
        } else {
            LOGGER.log(Level.FINEST, "The timer {0} excution is disabled.", AGENT_TIMER_INFO);
        }
    }

}
