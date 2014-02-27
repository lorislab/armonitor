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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.bts.model.BtsCriteria;
import org.lorislab.armonitor.bts.model.BtsIssue;
import org.lorislab.armonitor.bts.model.BtsResult;
import org.lorislab.armonitor.bts.service.BtsService;
import org.lorislab.armonitor.comparator.BuildScmLogsComparator;
import org.lorislab.armonitor.comparator.ChangeComparator;
import org.lorislab.armonitor.model.BuildScmLogs;
import org.lorislab.armonitor.model.Change;
import org.lorislab.armonitor.model.ChangeReport;
import org.lorislab.armonitor.model.ScmLink;
import org.lorislab.armonitor.model.ScmLogBuild;
import org.lorislab.armonitor.scm.model.ScmCriteria;
import org.lorislab.armonitor.scm.model.ScmLog;
import org.lorislab.armonitor.scm.model.ScmResult;
import org.lorislab.armonitor.scm.service.ScmService;
import org.lorislab.armonitor.store.criteria.StoreApplicationCriteria;
import org.lorislab.armonitor.store.criteria.StoreBuildCriteria;
import org.lorislab.armonitor.store.criteria.StoreProjectCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemBuildCriteria;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.ejb.StoreBuildServiceBean;
import org.lorislab.armonitor.store.ejb.StoreProjectServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemBuildServiceBean;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreBTSystem;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.store.model.StoreSCMSystem;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.web.rs.util.LinkUtil;

/**
 * The report service.
 * 
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ReportServiceBean {
    
    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ReportServiceBean.class.getName());
  
    /**
     * The build service.
     */
    @EJB
    private StoreBuildServiceBean buildService;    
    /**
     * The system build service.
     */
    @EJB
    private StoreSystemBuildServiceBean systemBuildService;    
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
     * Creates the change report for the system build.
     *
     * @param guid the GUID of the system build.
     * @return the change report.
     * @throws Exception if the method fails.
     */
    public ChangeReport createChangeReportForSystem(String guid) throws Exception {
                
        StoreSystemBuildCriteria ssbc = new StoreSystemBuildCriteria();
        ssbc.setGuid(guid);
        ssbc.setFetchBuild(true);
        ssbc.setFetchSystem(true);

        StoreSystemBuild sb = systemBuildService.getSystemBuild(ssbc);
        if (sb == null) {
            throw new Exception("No system build found with id: " + guid);
        }                
        StoreBuild build = sb.getBuild();        
        ChangeReport result = createChangeReportForBuild(build.getGuid());
        
        result.setSystemBuild(sb);
        return result;
    }
    
    /**
     * Creates the change report for the system build.
     *
     * @param guid the GUID of the build.
     * @return the change report.
     * @throws Exception if the method fails.
     */
    public ChangeReport createChangeReportForBuild(String guid) throws Exception {                   
         ChangeReport result = new ChangeReport(guid);
         
         StoreBuild build = buildService.getBuild(guid);
        if (build == null) {
            throw new Exception("No build found!");
        }
        result.setBuild(build);
        
        // load the application
        StoreApplicationCriteria sac = new StoreApplicationCriteria();
        sac.setBuild(guid);
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
        bc.setType(bts.getType());
        bc.setVersion(build.getMavenVersion());
        bc.setProject(project.getBtsId());

        BtsResult btsResult = BtsService.getIssues(bc);
        List<BtsIssue> issues = btsResult.getIssues();
        for (BtsIssue issue : issues) {
            Change change = new Change();
            change.setId(issue.getId());
            change.setIssue(issue);            
            change.setLink(LinkUtil.createLink(bts.getLink(), issue, project));
            changes.put(change.getId(), change);
        }

        // get changes from the SCM
        StoreSCMSystem scm = app.getScm();
        if (scm == null) {
            throw new Exception("Missing the SCM configuration");
        }

        // create the server link
        ScmLink scmLink = new ScmLink();
        scmLink.setScmBranches(LinkUtil.createLink(app.getScmBranches(), scm, build));
        scmLink.setScmTags(LinkUtil.createLink(app.getScmTags(), scm, build));
        scmLink.setScmTrunk(LinkUtil.createLink(app.getScmTrunk(), scm, build));
        scmLink.setScmRepo(app.getScmRepo());
        
        // load all builds
        StoreBuildCriteria sbc = new StoreBuildCriteria();
        sbc.setApplication(app.getGuid());
        sbc.setMavenVersion(build.getMavenVersion());
        List<StoreBuild> builds = buildService.getBuilds(sbc);

        // get the SCM log
        ScmCriteria criteria = new ScmCriteria();
        criteria.setType(scm.getType());
        criteria.setServer(scmLink.getScmBranches());
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
            String key = BtsService.getIdPattern(bts.getType(), project.getBtsId());
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

                String link = LinkUtil.createLink(scm.getLink(), scmLog, scmLink);
                
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
                    change.getChanges().add(new ScmLogBuild(buildForLog, scmLog, link));

                    // add change to the current build
                    if (build.equals(buildForLog)) {

                        Change bch = buildChanges.get(issue);
                        if (bch == null) {
                            bch = new Change();
                            bch.setId(issue);
                            bch.setIssue(change.getIssue());                            
                            buildChanges.put(issue, bch);
                        }
                        bch.getChanges().add(new ScmLogBuild(buildForLog, scmLog, link));
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
                            change.setLink(LinkUtil.createLink(bts.getLink(), issue, project));
                        }
                        change = buildChanges.get(error);
                        if (change != null) {
                            change.setIssue(issue);
                            change.setLink(LinkUtil.createLink(bts.getLink(), issue, project));
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
}
