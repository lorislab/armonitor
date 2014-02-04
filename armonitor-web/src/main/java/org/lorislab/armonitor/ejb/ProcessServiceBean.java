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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jms.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.lorislab.armonitor.jira.client.JIRAClient;
import org.lorislab.armonitor.jira.client.model.FieldNames;
import org.lorislab.armonitor.jira.client.model.Fields;
import org.lorislab.armonitor.jira.client.model.Issue;
import org.lorislab.armonitor.jira.client.model.SearchCriteria;
import org.lorislab.armonitor.jira.client.model.SearchResult;
import org.lorislab.armonitor.jira.client.services.SearchClient;
import org.lorislab.armonitor.mail.ejb.MailServiceBean;
import org.lorislab.armonitor.mail.model.Mail;
import org.lorislab.armonitor.model.Change;
import org.lorislab.armonitor.model.ChangeReport;
import org.lorislab.armonitor.scm.client.ScmClient;
import org.lorislab.armonitor.scm.model.ScmCriteria;
import org.lorislab.armonitor.scm.model.ScmLog;
import org.lorislab.armonitor.scm.svn.client.SvnClient;
import org.lorislab.armonitor.store.criteria.StoreApplicationCriteria;
import org.lorislab.armonitor.store.criteria.StoreBuildCriteria;
import org.lorislab.armonitor.store.criteria.StoreProjectCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemBuildCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemCriteria;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.ejb.StoreBuildServiceBean;
import org.lorislab.armonitor.store.ejb.StoreProjectServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemBuildServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.ejb.StoreUserServiceBean;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreBTSystem;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.store.model.StoreSCMSystem;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.store.model.enums.StoreBTSystemType;
import org.lorislab.armonitor.store.model.enums.StoreSCMSystemType;
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
     * The version regular expression.
     */
    private static final String VERSION_REGEX = "\\$\\{version\\}";
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
     * The project service.
     */
    @EJB
    private StoreProjectServiceBean projectService;
    /**
     * The application service.
     */
    @EJB
    private StoreApplicationServiceBean appService;

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
                criteria.setFetchApplication(true);
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
        if (!createSystemBuild) {
            return;
        }

        try {
            StoreSystemBuild sb = new StoreSystemBuild();
            sb.setBuild(buildOld);
            sb.setSystem(tmp);
            sb.setType(type);
            sb.setDate(new Date());
            systemBuildService.saveSystemBuild(sb);

            // load the application
            StoreApplicationCriteria sac = new StoreApplicationCriteria();
            sac.setGuid(tmp.getApplication().getGuid());
            sac.setFetchSCM(true);
            StoreApplication app = appService.getApplication(sac);
            if (app == null) {
                throw new Exception("Missing the application for the system!");
            }

            // load the project
            StoreProjectCriteria pc = new StoreProjectCriteria();
            pc.setApplication(tmp.getApplication().getGuid());
            pc.setFetchBTS(true);
            StoreProject project = projectService.getProject(pc);
            if (project == null) {
                throw new Exception("Missing project for the application and system!");
            }
            StoreBTSystem bts = project.getBts();
            if (bts == null) {
                throw new Exception("Missing the bug tracking configuration");
            }

            Map<String, Change> changes = new HashMap<>();

            // get changes from the SCM
            StoreSCMSystem scm = app.getScm();
            if (scm == null) {
                throw new Exception("Missing the SCM configuration");
            }

            if (scm.getType() == StoreSCMSystemType.SUBVERSION) {
                ScmClient scmClient = new SvnClient();
                ScmCriteria criteria = new ScmCriteria();

                String server = scm.getServer() + app.getScmBranches();
                server = server.replaceAll(VERSION_REGEX, build.getMavenVersion());

                criteria.setServer(server);

                criteria.setAuth(scm.isAuth());
                criteria.setUser(scm.getUser());
                criteria.setPassword(scm.getPassword());
                List<ScmLog> scmLogs = scmClient.getLog(criteria);
                if (scmLogs != null) {

                    // create issue id pattern
                    String key = project.getBtsId();
                    if (bts.getType() == StoreBTSystemType.JIRA) {
                        key = key + "\\-\\d+";
                    }
                    Pattern searchPattern = Pattern.compile(key);

                    // search issues in the SCM log.
                    for (ScmLog scmLog : scmLogs) {

                        Matcher matcher = searchPattern.matcher(scmLog.getMessage());
                        while (matcher.find()) {
                            String issue = matcher.group();
                            Change ch = changes.get(issue);
                            if (ch == null) {
                                ch = new Change();
                                ch.setId(issue);
                                changes.put(issue, ch);
                            }
                            ch.getScmLogs().add(scmLog);
                        }
                    }
                }
            } else {
                throw new Exception("Not supported SCM provider");
            }

            // update changes from the bug tracking system
            if (!changes.isEmpty()) {
                if (bts.getType() == StoreBTSystemType.JIRA) {

                    // search issues in the current project and current version
                    Set<String> issues = changes.keySet();
                    StringBuilder jql = new StringBuilder();
                    jql.append("id in (");
                    boolean first = false;
                    for (String issue : issues) {
                        if (first) {
                            jql.append(',');
                        }
                        jql.append(issue);
                        first = true;
                    }
                    jql.append(") and fixVersion = \"");
                    jql.append(build.getMavenVersion());
                    jql.append("\" and project = ");
                    jql.append(project.getBtsId());

                    JIRAClient btsClient = new JIRAClient(bts.getServer(), bts.getUser(), bts.getPassword(), bts.isAuth());
                    SearchClient search = btsClient.createSearchClient();

                    SearchCriteria criteria = new SearchCriteria();
                    criteria.setJql(jql.toString());
                    criteria.setFields(Arrays.asList(FieldNames.STATUS, FieldNames.SUMMARY, FieldNames.ASSIGNEE, FieldNames.RESOLUTION));

                    SearchResult result = search.search(criteria);
                    if (result != null) {
                        for (Issue issue : result.getIssues()) {
                            Change change = changes.get(issue.getKey());
                            if (change != null) {
                                Fields fields = issue.getFields();
                                if (fields.getAssignee() != null) {
                                    change.setAssignee(fields.getAssignee().getDisplayName());
                                }
                                if (fields.getResolution() != null) {
                                    change.setResolution(fields.getResolution().getName());
                                } else {
                                    change.setResolution("Unresolved");
                                }
                                change.setSummary(fields.getSummary());
                            }
                        }
                    }
                } else {
                    throw new Exception("Not supported bug tracking system");
                }
            }

            // create change report
            ChangeReport changeReport = new ChangeReport();
            changeReport.getChanges().addAll(changes.values());

            // notification
            Set<String> users = userService.getUsersEmailsForSystem(tmp.getGuid());
            List<Mail> mails = createBuildDeployedMails(users, tmp, build, project, changeReport);
            send(mails);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error: {0}", ex.getMessage());
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
    private List<Mail> createBuildDeployedMails(Set<String> emails, Object... values) {
        List<Mail> result = null;
        if (emails != null) {
            result = new ArrayList<>();
            for (String email : emails) {
                Mail mail = new Mail();
                mail.getTo().add(email);
                mail.setTemplate(MAIL_BUILD_DEPLOYED_TEMPLATE);
                if (values != null) {
                    for (Object value : values) {
                        mail.getParameters().put(value.getClass().getSimpleName(), value);
                    }
                }
                result.add(mail);
            }
        }
        return result;
    }

    /**
     * Send the notification asynchrony.
     *
     * @param item the serialisable.
     */
    public void exec(Serializable item) {
        if (item != null) {
            Connection connection = null;
            try {
                connection = jmsConnectionFactory.createConnection();
                javax.jms.Session session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
                MessageProducer publisher = session.createProducer(queue);
                connection.start();
                ObjectMessage message = session.createObjectMessage(item);
                publisher.send(message);
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

    /**
     * Send the notification asynchrony.
     *
     * @param items the list of serialisable items.
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
