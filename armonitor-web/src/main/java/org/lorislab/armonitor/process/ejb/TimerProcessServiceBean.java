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
package org.lorislab.armonitor.process.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.agent.ejb.AgentClientServiceBean;
import org.lorislab.armonitor.store.criteria.StoreSystemBuildCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemCriteria;
import org.lorislab.armonitor.store.ejb.StoreSystemBuildServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.store.model.enums.StoreSystemBuildType;
import org.lorislab.jel.base.resources.ResourceManager;
import org.lorislab.jel.ejb.exception.ServiceException;

/**
 * The timer process service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class TimerProcessServiceBean {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(TimerProcessServiceBean.class.getName());

    /**
     * The store system service.
     */
    @EJB
    private StoreSystemServiceBean systemService;

    /**
     * The agent client service.
     */
    @EJB
    private AgentClientServiceBean agentClientService;

    /**
     * The store system build service.
     */
    @EJB
    private StoreSystemBuildServiceBean systemBuildService;

    /**
     * The process service.
     */
    @EJB
    private ProcessServiceBean processService;

    /**
     * The timer process method.
     */
    public void timerService() {
        StoreSystemCriteria criteria = new StoreSystemCriteria();
        criteria.setFetchAgent(true);
        criteria.setTimer(Boolean.TRUE);
        criteria.setEnabled(Boolean.TRUE);
        List<StoreSystem> systems = systemService.getSystems(criteria);
        if (systems != null) {
            for (StoreSystem system : systems) {
                try {
                    StoreBuild build = agentClientService.getBuild(system.getAgent(), system.getService());
                    if (build != null) {
                        check(system, build);
                    } else {
                        LOGGER.log(Level.WARNING, "Could not get the build for the system {0}", system.getGuid());
                    }
                } catch (ServiceException e) {
                    LOGGER.log(Level.SEVERE, "Error: {0}", ResourceManager.getMessage(e.getResourceMessage(), null));
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Error reading the build information from system: {0}", system.getName());
                }
            }
        } else {
            LOGGER.log(Level.FINEST, "No systems to check");
        }
    }

    /**
     * The check before deploy the build.
     *
     * @param system the system.
     * @param build the build.
     * @throws ServiceException if the method fails.
     */
    private void check(StoreSystem system, StoreBuild build) throws ServiceException {

        //check if the build exist for the system if not deploy!!!
        StoreSystemBuildCriteria ssbc = new StoreSystemBuildCriteria();
        ssbc.setSystem(system.getGuid());
        ssbc.setMaxDate(Boolean.TRUE);
        ssbc.setFetchBuild(true);
        StoreSystemBuild tmp = systemBuildService.getSystemBuild(ssbc);

        boolean deploy = false;
        if (tmp != null) {
            Date odate = tmp.getBuild().getDate();
            Date ndate = build.getDate();
            if (odate != null && ndate != null) {
                deploy = odate.equals(ndate);
            }
        }

        if (deploy) {
            processService.deploy(system, build, StoreSystemBuildType.TIMER);
        } else {
            LOGGER.log(Level.INFO, "The build {0}{1} already deploy on the system {2}", new Object[]{build.getMavenVersion(), build.getBuild(), system.getName()});
        }
    }
}
