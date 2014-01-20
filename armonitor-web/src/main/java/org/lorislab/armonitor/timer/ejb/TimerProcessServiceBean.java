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
package org.lorislab.armonitor.timer.ejb;

import java.util.Date;
import java.util.List;
import javax.jms.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import org.lorislab.armonitor.store.criteria.StoreAgentCriteria;
import org.lorislab.armonitor.store.ejb.StoreAgentServiceBean;
import org.lorislab.armonitor.store.model.StoreAgent;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.agent.ejb.AgentClientServiceBean;
import org.lorislab.armonitor.store.criteria.StoreBuildCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemBuildCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemCriteria;
import org.lorislab.armonitor.store.ejb.StoreBuildServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemBuildServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.store.model.StoreSystemBuild;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NEVER)
public class TimerProcessServiceBean {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(TimerProcessServiceBean.class.getName());

    @EJB
    private AgentClientServiceBean agentClientService;

    @EJB
    private StoreBuildServiceBean buildService;

    @EJB
    private StoreSystemServiceBean systemService;

    @EJB
    private StoreSystemBuildServiceBean systemBuildService;

    /**
     * The store queue.
     */
    @Resource(mappedName = "java:/queue/timerProcess")
    private Queue queue;
    /**
     * The connection factory.
     */
    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory jmsConnectionFactory;

    public void timerService() {
        StoreSystemCriteria criteria = new StoreSystemCriteria();
        criteria.setTimer(Boolean.TRUE);
        criteria.setEnabled(Boolean.TRUE);
        List<StoreSystem> systems = systemService.getSystems(criteria);
        if (systems != null) {
            send(systems);
        } else {
            LOGGER.log(Level.FINEST, "No agents to check");
        }
    }

    /**
     * Send the notification asynchrony.
     *
     * @param version the version.
     */
    private void send(List<StoreSystem> systems) {
        Connection connection = null;
        try {
            connection = jmsConnectionFactory.createConnection();
            javax.jms.Session session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(queue);
            connection.start();
            for (StoreSystem system : systems) {
                ObjectMessage message = session.createObjectMessage(system);
                publisher.send(message);
            }
        } catch (JMSException exc) {
            LOGGER.log(Level.SEVERE, "Error by seding the process message.", exc);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    LOGGER.log(Level.SEVERE, "Error by closing the queue connection.", e);
                }
            }
        }
    }

    public void process(StoreSystem system) {
        if (system != null) {
            StoreSystemCriteria criteria = new StoreSystemCriteria();
            criteria.setGuid(system.getGuid());
            criteria.setFetchAgent(true);
            criteria.setFetchApplication(true);
            criteria.setTimer(Boolean.TRUE);
            criteria.setEnabled(Boolean.TRUE);
            StoreSystem tmp = systemService.getSystem(criteria);
            if (tmp != null) {
                StoreBuild build = agentClientService.getAppBuild(system.getAgent());
                if (build != null) {

                    StoreBuildCriteria buildCriteria = new StoreBuildCriteria();
                    buildCriteria.setDate(build.getDate());
                    buildCriteria.setApplication(tmp.getApplication().getGuid());
                    StoreBuild buildOld = buildService.getBuild(buildCriteria);

                    boolean createSystemBuild = false;
                    if (buildOld == null) {
                        build.setApplication(tmp.getApplication());
                        buildOld = buildService.saveBuild(build);
                        createSystemBuild = true;
                    } else {
                        StoreSystemBuildCriteria sbc = new StoreSystemBuildCriteria();
                        sbc.setBuild(buildOld.getGuid());
                        sbc.setSystem(tmp.getGuid());
                        StoreSystemBuild sb = systemBuildService.getSystemBuild(sbc);
                        if (sb == null) {
                            createSystemBuild = true;
                        }
                    }

                    if (createSystemBuild) {
                        StoreSystemBuild sb = new StoreSystemBuild();
                        sb.setBuild(buildOld);
                        sb.setSystem(tmp);
                        sb.setDate(new Date());
                        systemBuildService.saveSystemBuild(sb);
                    }
                } else {
                    LOGGER.log(Level.WARNING, "Could not get the build for the system {0}", system.getGuid());
                }
            }
        }
    }
}
