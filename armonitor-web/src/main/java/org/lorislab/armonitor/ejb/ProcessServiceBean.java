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
package org.lorislab.armonitor.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.agent.ejb.AgentClientServiceBean;
import org.lorislab.armonitor.mail.ejb.MailServiceBean;
import org.lorislab.armonitor.mail.model.Mail;
import org.lorislab.armonitor.store.criteria.StoreBuildCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemBuildCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemCriteria;
import org.lorislab.armonitor.store.ejb.StoreBuildServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemBuildServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.ejb.StoreUserServiceBean;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.store.model.enums.StoreSystemBuildType;

/**
 * The core process service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ProcessServiceBean {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ProcessServiceBean.class.getName());

    /**
     * The new build deploy template.
     */
    private static final String MAIL_BUILD_DEPLOYED_TEMPLATE = "buildDeployed";

    /**
     * The agent client service.
     */
    @EJB
    private AgentClientServiceBean agentClientService;

    /**
     * The build service.
     */
    @EJB
    private StoreBuildServiceBean buildService;

    /**
     * The system service.
     */
    @EJB
    private StoreSystemServiceBean systemService;

    /**
     * The system build service.
     */
    @EJB
    private StoreSystemBuildServiceBean systemBuildService;

    /**
     * The mail service.
     */
    @EJB
    private MailServiceBean mailService;

    /**
     * The user service.
     */
    @EJB
    private StoreUserServiceBean userService;

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

    /**
     * The timer process method.
     */
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
     * Attached the build to the system. This is use only for the manual
     * process.
     *
     * @param key the system key.
     * @param build the build
     * @throws Exception if the method fails.
     */
    public void process(String key, StoreBuild build) throws Exception {
        if (key != null) {
            if (build != null) {
                StoreSystemCriteria criteria = new StoreSystemCriteria();
                criteria.setKey(key);
                StoreSystem tmp = systemService.getSystem(criteria);
                if (tmp != null) {
                    process(tmp, build, StoreSystemBuildType.MANUAL);
                } else {
                    LOGGER.log(Level.SEVERE, "No system for the key {0} found!", key);
                    throw new Exception("No system for the key " + key + " found!");
                }
            } else {
                LOGGER.log(Level.SEVERE, "The build object is null!");
                throw new Exception("The build object is null!");
            }
        } else {
            LOGGER.log(Level.SEVERE, "The key is null!");
            throw new Exception("The key is null!");
        }
    }

    /**
     * Process the mail notification.
     *
     * @param mail the mail.
     */
    public void process(Mail mail) {
        mailService.sendEmail(mail);
    }

    /**
     * Process the system.
     *
     * @param system the system.
     */
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
                    process(tmp, build, StoreSystemBuildType.TIMER);
                } else {
                    LOGGER.log(Level.WARNING, "Could not get the build for the system {0}", system.getGuid());
                }
            }
        }
    }

    /**
     * Attached or update the system with the build.
     *
     * @param tmp the system.
     * @param build the build.
     */
    private void process(StoreSystem tmp, StoreBuild build, StoreSystemBuildType type) {
        StoreBuildCriteria buildCriteria = new StoreBuildCriteria();
        buildCriteria.setDate(build.getDate());
        buildCriteria.setApplication(tmp.getApplication().getGuid());
        StoreBuild buildOld = buildService.getBuild(buildCriteria);

        boolean createSystemBuild = false;

        // check the build
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

        // the build was deployed on the system
        if (createSystemBuild) {
            StoreSystemBuild sb = new StoreSystemBuild();
            sb.setBuild(buildOld);
            sb.setSystem(tmp);
            sb.setType(type);
            sb.setDate(new Date());
            systemBuildService.saveSystemBuild(sb);

            // notification
            Set<String> users = userService.getUsersEmailsForSystem(tmp.getGuid());
            List<Mail> mails = createBuildDeployedMails(users, tmp, build);
            send(mails);
        }
    }

    /**
     * Creates the build deployed mails.
     *
     * @param emails the set of addresses.
     * @param system the system.
     * @param build the build.
     * @return the list of mails.
     */
    private List<Mail> createBuildDeployedMails(Set<String> emails, StoreSystem system, StoreBuild build) {
        List<Mail> result = null;
        if (emails != null) {
            result = new ArrayList<>();
            for (String email : emails) {
                Mail mail = new Mail();
                mail.getTo().add(email);
                mail.setTemplate(MAIL_BUILD_DEPLOYED_TEMPLATE);
                mail.getParameters().put(StoreSystem.class.getSimpleName(), system);
                mail.getParameters().put(StoreBuild.class.getSimpleName(), build);
            }
        }
        return result;
    }

    /**
     * Send the notification asynchrony.
     *
     * @param version the version.
     */
    private void send(List<? extends Serializable> items) {
        if (items != null && !items.isEmpty()) {
            Connection connection = null;
            try {
                connection = jmsConnectionFactory.createConnection();
                javax.jms.Session session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
                MessageProducer publisher = session.createProducer(queue);
                connection.start();
                for (Serializable item : items) {
                    ObjectMessage message = session.createObjectMessage(item);
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
    }

}
