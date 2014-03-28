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
import org.lorislab.armonitor.config.ejb.ConfigurationServiceBean;
import org.lorislab.armonitor.process.ejb.TimerProcessServiceBean;
import org.lorislab.armonitor.timer.model.TimerConfig;

/**
 * The timer service.
 *
 * @author Andrej Petras
 */
@Singleton
@Startup
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class TimerServiceBean {

    /**
     * The timer info.
     */
    private static final String TIMER_INFO = TimerServiceBean.class.getName();

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(TimerServiceBean.class.getName());

    /**
     * The timer service.
     */
    @Resource
    private TimerService timerService;

    /**
     * The configuration service.
     */
    @EJB
    private ConfigurationServiceBean configService;
    /**
     * The process service.
     */
    @EJB
    private TimerProcessServiceBean processService;

    /**
     * Initialise after start.
     */
    @PostConstruct
    public void initialize() {
        start();
    }

    /**
     * Restart the timer.
     */
    public void restart() {
        stop();
        start();
    }

    /**
     * Start the timer.
     */
    public void start() {
        Timer timer = getTimer();
        if (timer == null) {
            TimerConfig config = configService.getConfiguration(TimerConfig.class);
            if (config.start) {
                ScheduleExpression expression = new ScheduleExpression();
                expression.second(config.second).minute(config.minute).hour(config.hour);

                javax.ejb.TimerConfig timerConfig = new javax.ejb.TimerConfig(TIMER_INFO, false);
                Timer tmp = timerService.createCalendarTimer(expression, timerConfig);

                LOGGER.log(Level.INFO, "The timer service was started with next timeout: {0}", tmp.getNextTimeout());
            } else {
                LOGGER.log(Level.INFO, "The timer {0} is not started.", TIMER_INFO);
            }
        } else {
            LOGGER.log(Level.INFO, "The timer {0} is all ready running.", TIMER_INFO);
        }
    }

    /**
     * Stop the timer.
     */
    public void stop() {
        Timer timer = getTimer();
        if (timer != null) {
            timer.cancel();
            LOGGER.log(Level.INFO, "The timer {0} was cancelled.", TIMER_INFO);
        } else {
            LOGGER.log(Level.INFO, "The timer {0} is not running.", TIMER_INFO);
        }
    }

    /**
     * Gets the timer.
     *
     * @return the timer.
     */
    public Timer getTimer() {
        Collection<Timer> timers = timerService.getTimers();
        for (Timer timer : timers) {
            if (TIMER_INFO.equals(timer.getInfo())) {
                return timer;
            }
        }
        return null;
    }

    /**
     * Execution method.
     */
    @Timeout
    public void execute() {
        TimerConfig config = configService.getConfiguration(TimerConfig.class);
        if (config.enabled) {
            processService.timerService();
        } else {
            LOGGER.log(Level.FINEST, "The timer {0} excution is disabled.", TIMER_INFO);
        }
    }

}
