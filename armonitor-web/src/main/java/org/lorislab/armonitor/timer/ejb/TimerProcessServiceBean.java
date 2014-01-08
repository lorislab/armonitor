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

/**
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
    
    @EJB
    private StoreAgentServiceBean agentService;
    
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
        StoreAgentCriteria criteria = new StoreAgentCriteria();
        criteria.setTimer(Boolean.TRUE);
        List<StoreAgent> agents = agentService.getAgents(criteria);
        if (agents != null) {
            send(agents);
        } else {
            LOGGER.log(Level.FINEST, "No agents to check");            
        }
    }
    
    /**
     * Send the notification asynchrony.
     *
     * @param version the version.
     */
    private void send(List<StoreAgent> agents) {
        Connection connection = null;
        try {
            connection = jmsConnectionFactory.createConnection();
            javax.jms.Session session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(queue);
            connection.start();
            for (StoreAgent agent : agents) {
                ObjectMessage message = session.createObjectMessage(agent);
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
    
    public void process(StoreAgent agent) {
        if (agent != null) {
            LOGGER.log(Level.INFO, "Check agent {0}", agent.getUrl());
        }
    }
}
