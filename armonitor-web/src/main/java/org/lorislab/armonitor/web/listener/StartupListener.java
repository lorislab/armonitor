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
package org.lorislab.armonitor.web.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.timer.ejb.TimerServiceBean;
import org.lorislab.jel.log.service.LogService;

/**
 * The startup application listener.
 *
 * @author Andrej Petras
 */
@WebListener
public class StartupListener implements ServletContextListener {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(StartupListener.class.getName());
    /**
     * The MAVEN property file.
     */
    private static final String PROPERTY_FILE = "/META-INF/maven/org.lorislab.armonitor/armonitor-web/pom.properties";
    /**
     * The version key.
     */
    private static final String KEY_VERSION = "version";
    /**
     * The timer service.
     */
    @EJB
    private TimerServiceBean timerService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {        
        String version = getVersion(sce.getServletContext());
        LOGGER.log(Level.INFO, "### Start the release monitor. [Version: {0}] ###", version);
        LOGGER.log(Level.INFO, "### Start the application services. ###");
        LogService.getContextLogger();
        Mapper.map("start", String.class);
        timerService.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.log(Level.INFO, "Shutdown the release monitor.");
    }

    /**
     * Gets the version of the application.
     *
     * @return the version of the application.
     */
    public String getVersion(ServletContext context) {
        Properties properties = new Properties();
        InputStream input = context.getResourceAsStream(PROPERTY_FILE);
        try {
            properties.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
            // do nothing
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    // do nothing
                }
            }
        }
        return properties.getProperty(KEY_VERSION);
    }
}
