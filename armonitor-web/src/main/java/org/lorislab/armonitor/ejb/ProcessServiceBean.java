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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import org.lorislab.armonitor.bts.model.BtsCriteria;
import org.lorislab.armonitor.bts.model.BtsIssue;
import org.lorislab.armonitor.bts.model.BtsResult;
import org.lorislab.armonitor.bts.service.BtsService;
import org.lorislab.armonitor.mail.ejb.MailServiceBean;
import org.lorislab.armonitor.mail.model.Mail;
import org.lorislab.armonitor.model.ScmLogBuild;
import org.lorislab.armonitor.model.BuildScmLogs;
import org.lorislab.armonitor.comparator.BuildScmLogsComparator;
import org.lorislab.armonitor.model.Change;
import org.lorislab.armonitor.comparator.ChangeComparator;
import org.lorislab.armonitor.model.ChangeReport;
import org.lorislab.armonitor.scm.model.ScmCriteria;
import org.lorislab.armonitor.scm.model.ScmLog;
import org.lorislab.armonitor.scm.model.ScmResult;
import org.lorislab.armonitor.scm.service.ScmService;
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
                StoreBuild build = agentClientService.getAppBuild(system.getAgent(), system.getService());
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

        StoreSystemBuild sb = new StoreSystemBuild();
        sb.setBuild(buildOld);
        sb.setSystem(tmp);
        sb.setType(type);
        sb.setDate(new Date());
        sb = systemBuildService.saveSystemBuild(sb);

        send(sb.getGuid());
    }

    public void sendReportAsync(String guid) {
        send(guid);
    }

    /**
     * Creates and send the report to the users.
     *
     * @param guid the system build GUID.
     */
    public void sendReport(String guid) {
        try {
            // create change report
            ChangeReport changeReport = createChangeReport(guid);            
            // notification
            send(changeReport);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Sends the report to the users.
     *
     * @param report the change report.
     */
    public void sendReport(ChangeReport report) {
        try {
            // the core data for the report
            StoreSystemBuild sb = report.getSystemBuild();
            StoreSystem tmp = sb.getSystem();
            StoreBuild build = sb.getBuild();
            StoreProject project = report.getProject();
            StoreApplication app = report.getApplication();

            // notification
            if (tmp.isNotification()) {            
                Set<String> users = userService.getUsersEmailsForSystem(tmp.getGuid());
                List<Mail> mails = createBuildDeployedMails(users, tmp, build, project, report, app, sb);
                send(mails);
            } else {
                LOGGER.log(Level.WARNING, "The notification for the system {0} is disabled!", tmp.getName());
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Creates the change report for the system build.
     *
     * @param guid the GUID of the system build.
     * @return the change report.
     * @throws Exception if the method fails.
     */
    public ChangeReport createChangeReport(String guid) throws Exception {
        ChangeReport result = new ChangeReport(guid);

        StoreSystemBuildCriteria ssbc = new StoreSystemBuildCriteria();
        ssbc.setGuid(guid);
        ssbc.setFetchBuild(true);
        ssbc.setFetchSystem(true);

        StoreSystemBuild sb = systemBuildService.getSystemBuild(ssbc);
        if (sb == null) {
            throw new Exception("No system build found with id: " + guid);
        }
        StoreSystem tmp = sb.getSystem();
        StoreBuild build = sb.getBuild();

        result.setSystemBuild(sb);

        // load the application
        StoreApplicationCriteria sac = new StoreApplicationCriteria();
        sac.setSystem(tmp.getGuid());
        sac.setFetchSCM(true);
        StoreApplication app = appService.getApplication(sac);
        if (app == null) {
            throw new Exception("Missing the application for the system!");
        }
        result.setApplication(app);

        // load the project
        StoreProjectCriteria pc = new StoreProjectCriteria();
        pc.setApplication(app.getGuid());
        pc.setFetchBTS(true);
        StoreProject project = projectService.getProject(pc);
        if (project == null) {
            throw new Exception("Missing project for the application and system!");
        }
        result.setProject(project);

        StoreBTSystem bts = project.getBts();
        if (bts == null) {
            throw new Exception("Missing the bug tracking configuration");
        }

        Map<String, Change> changes = new HashMap<>();
        Map<String, Change> buildChanges = new HashMap<>();
        Set<String> errors = new HashSet<>();

        // update changes from the bug tracking system
        BtsCriteria bc = new BtsCriteria();
        bc.setServer(bts.getServer());
        bc.setUser(bts.getUser());
        bc.setPassword(bts.getPassword());
        bc.setAuth(bts.isAuth());
        bc.setType(bts.getType().name());
        bc.setVersion(build.getMavenVersion());
        bc.setProject(project.getBtsId());

        BtsResult btsResult = BtsService.getIssues(bc);
        List<BtsIssue> issues = btsResult.getIssues();
        for (BtsIssue issue : issues) {
            Change change = new Change();
            change.setId(issue.getId());
            change.setIssue(issue);
            changes.put(change.getId(), change);
        }

        // get changes from the SCM
        StoreSCMSystem scm = app.getScm();
        if (scm == null) {
            throw new Exception("Missing the SCM configuration");
        }

        // create the server link
        String server = scm.getServer() + app.getScmBranches();
        server = server.replaceAll(VERSION_REGEX, build.getMavenVersion());

        // load all builds
        StoreBuildCriteria sbc = new StoreBuildCriteria();
        sbc.setApplication(app.getGuid());
        sbc.setMavenVersion(build.getMavenVersion());
        List<StoreBuild> builds = buildService.getBuilds(sbc);

        // get the SCM log
        ScmCriteria criteria = new ScmCriteria();
        criteria.setType(scm.getType().name());
        criteria.setServer(server);
        criteria.setAuth(scm.isAuth());
        criteria.setUser(scm.getUser());
        criteria.setPassword(scm.getPassword());
        criteria.setReadTimeout(scm.getReadTimeout());
        criteria.setConnectionTimeout(scm.getConnectionTimeout());
        ScmResult scmResult = ScmService.getLog(criteria);

        List<BuildScmLogs> buildScmLogs = new ArrayList<>();
        for (StoreBuild item : builds) {
            String id = item.getScm();
            if (id != null && !id.isEmpty()) {
                ScmLog log = scmResult.getScmLog(id);
                if (log != null) {
                    buildScmLogs.add(new BuildScmLogs(item, log.getDate()));
                } else {
                    LOGGER.log(Level.WARNING, "No SCM log found for the [{0}] id. Ignore the build {1}", new Object[]{id, item.getGuid()});
                }
            }
        }
        //add next release
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        buildScmLogs.add(new BuildScmLogs(null, cal.getTime()));
        // sort the build log by date
        Collections.sort(buildScmLogs, BuildScmLogsComparator.INSTANCE);

        List<ScmLog> scmLogs = scmResult.getScmLogs();

        if (scmLogs != null) {

            // create issue id pattern
            String key = BtsService.getIdPattern(bts.getType().name(), project.getBtsId());
            Pattern searchPattern = Pattern.compile(key);

            // search issues in the SCM log.
            for (ScmLog scmLog : scmLogs) {
                StoreBuild buildForLog = null;
                if (!buildScmLogs.isEmpty()) {
                    Iterator<BuildScmLogs> iter = buildScmLogs.iterator();
                    BuildScmLogs bsl = iter.next();
                    while (scmLog.getDate().after(bsl.getDate())) {
                        bsl = iter.next();
                    }
                    buildForLog = bsl.getBuild();
                }

                LOGGER.log(Level.FINEST, "REV: {0} {1} {2}", new Object[]{scmLog.getId(), scmLog.getDate(), scmLog.getMessage()});
                Matcher matcher = searchPattern.matcher(scmLog.getMessage());
                while (matcher.find()) {
                    String issue = matcher.group();
                    Change change = changes.get(issue);
                    if (change == null) {
                        change = new Change();
                        change.setError(true);
                        change.setId(issue);
                        errors.add(issue);
                        changes.put(issue, change);
                    }

                    // add SCM log to the change
                    change.getChanges().add(new ScmLogBuild(buildForLog, scmLog));
                    
                    // add change to the current build
                    if (build.equals(buildForLog)) {
                        
                        Change bch = buildChanges.get(issue);
                        if (bch == null) {
                            bch = new Change();
                            bch.setId(issue);
                            bch.setIssue(change.getIssue());
                            buildChanges.put(issue, bch);
                        }
                        bch.getChanges().add(new ScmLogBuild(buildForLog, scmLog));
                    }
                }
            }
        }

        // get the wrong commits from the BTS
        if (!errors.isEmpty()) {
            bc.setVersion(null);
            bc.setProject(null);
            for (String error : errors) {
                bc.setId(error);
                try {
                    BtsResult btsTmp = BtsService.getIssues(bc);
                    if (!btsTmp.isEmpty()) {
                        BtsIssue issue = btsTmp.getIssues().get(0);
                        Change change = changes.get(error);
                        if (change != null) {
                            change.setIssue(issue);
                        }
                    }
                } catch (Exception ex) {
                    LOGGER.log(Level.WARNING, "{0} is not valid issue for this project", error);
                    LOGGER.log(Level.FINEST, "Error get the BTS issue", ex);
                }
            }
        }

        // create change report
        result.getChanges().addAll(changes.values());
        Collections.sort(result.getChanges(), ChangeComparator.INSTANCE);

        result.getBuildChanges().addAll(buildChanges.values());
        Collections.sort(result.getBuildChanges(), ChangeComparator.INSTANCE);

        return result;
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
     * @param item the serialisable items.
     */
    private void send(Serializable item) {
        send(Arrays.asList(item));
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
