/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lorislab.armonitor.activity.ejb;

import java.util.Collections;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.activity.comparator.ActivityChangeLogWrapperComparator;
import org.lorislab.armonitor.activity.comparator.ActivityChangeWrapperComparator;
import org.lorislab.armonitor.activity.criteria.ActivityWrapperCriteria;
import org.lorislab.armonitor.activity.model.ActivityType;
import org.lorislab.armonitor.activity.wrapper.ActivityChangeLogWrapper;
import org.lorislab.armonitor.activity.wrapper.ActivityChangeWrapper;
import org.lorislab.armonitor.activity.wrapper.ActivityWrapper;
import org.lorislab.armonitor.store.criteria.StoreActivityCriteria;
import org.lorislab.armonitor.store.criteria.StoreApplicationCriteria;
import org.lorislab.armonitor.store.ejb.StoreActivityServiceBean;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.model.StoreActivity;
import org.lorislab.armonitor.store.model.StoreActivityChange;
import org.lorislab.armonitor.store.model.StoreActivityLog;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreBTSystem;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.store.model.StoreSCMSystem;
import org.lorislab.armonitor.web.rs.util.LinkUtil;

/**
 * The activity wrapper service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ActivityWrapperServiceBean {

    @EJB
    private StoreActivityServiceBean activityService;

    @EJB
    private StoreApplicationServiceBean appService;

    public ActivityWrapper create(ActivityWrapperCriteria criteria) {

        // create wrapper and load the activity                        
        StoreActivityCriteria sac = new StoreActivityCriteria();
        sac.setBuild(criteria.getBuild());
        sac.setGuid(criteria.getGuid());
        sac.setFetchBuild(true);
        sac.setFetchChange(true);
        sac.setFetchChangeLog(true);
        sac.setFetchChangeLogBuild(true);
        StoreActivity activity = activityService.getActivity(sac);
        
        return create(activity, criteria);
    }
    
    public ActivityWrapper create(StoreActivity activity, ActivityWrapperCriteria criteria) {        
        ActivityWrapper result = new ActivityWrapper(activity);
                
        StoreBuild build = activity.getBuild();        
        criteria.setBuild(build.getGuid());
        
        // load application and project
        StoreBTSystem bts = null;
        StoreSCMSystem scm = null;
        StoreProject project = null;
        StoreApplication app = null;

        // fetch the application and project
        StoreApplicationCriteria sapc = new StoreApplicationCriteria();
        sapc.setBuild(criteria.getBuild());
        sapc.setFetchSCM(true);
        sapc.setFetchProject(true);
        sapc.setFetchProjectBts(true);

        app = appService.getApplication(sapc);
        result.setApplication(app);

        if (app != null) {
            scm = app.getScm();
            project = app.getProject();
            result.setProject(project);
            bts = project.getBts();
        }

        // temporary 
        boolean btsLink = bts != null && project != null;
        boolean scmLink = scm != null && app != null;

        // create the wrapper list
        if (activity.getChanges()
                != null) {
            for (StoreActivityChange change : activity.getChanges()) {
                // add to the types
                String type = change.getType();
                if (type == null) {
                    type = ActivityType.ERROR;
                }
                result.getTypes().add(type);

                // the build change
                ActivityChangeWrapper bacw = null;
                // create the change                
                ActivityChangeWrapper acw = new ActivityChangeWrapper(change);
                if (btsLink) {
                    String link = LinkUtil.createLink(bts.getLink(), change, project);
                    acw.setLink(link);
                }
                result.getChanges().add(acw);

                // check the change logs
                if (change.getLogs() != null) {
                    for (StoreActivityLog log : change.getLogs()) {

                        ActivityChangeLogWrapper aclw = new ActivityChangeLogWrapper(log);
                        if (scmLink) {
                            String link = LinkUtil.createLink(scm.getLink(), log, app);
                            aclw.setLink(link);
                        }
                        acw.getLogs().add(aclw);

                        // check the build change.
                        if (build.equals(log.getBuild())) {
                            if (bacw == null) {
                                bacw = new ActivityChangeWrapper(change);
                                bacw.setLink(acw.getLink());
                            }
                            bacw.getLogs().add(aclw);
                        }
                    }
                }

                if (criteria.isSortList()) {
                    Collections.sort(acw.getLogs(), ActivityChangeLogWrapperComparator.INSTANCE);
                }

                // add the build change to the wrapper
                if (bacw != null) {
                    result.getBuildChanges().add(bacw);
                    if (criteria.isSortList()) {
                        Collections.sort(bacw.getLogs(), ActivityChangeLogWrapperComparator.INSTANCE);
                    }
                }
            }

            //FIXME: sort order the list
            if (criteria.isSortList()) {
                Collections.sort(result.getChanges(), ActivityChangeWrapperComparator.INSTANCE);
                Collections.sort(result.getBuildChanges(), ActivityChangeWrapperComparator.INSTANCE);
            }
        }
        return result;
    }

}
