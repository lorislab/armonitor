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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mail.ejb.MailServiceBean;
import org.lorislab.armonitor.mail.model.Mail;
import org.lorislab.armonitor.process.resources.ErrorKeys;
import org.lorislab.armonitor.store.criteria.StoreActivityCriteria;
import org.lorislab.armonitor.store.criteria.StoreApplicationCriteria;
import org.lorislab.armonitor.store.criteria.StoreBuildCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemCriteria;
import org.lorislab.armonitor.store.ejb.StoreActivityServiceBean;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.ejb.StoreBuildServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemBuildServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.ejb.StoreUserServiceBean;
import org.lorislab.armonitor.store.model.StoreActivity;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.store.model.StoreUser;
import org.lorislab.armonitor.store.model.enums.StoreSystemBuildType;
import org.lorislab.jel.ejb.exception.ServiceException;

/**
 * The process service.
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
     * The store system service.
     */
    @EJB
    private StoreSystemServiceBean systemService;

    /**
     * The store application service.
     */
    @EJB
    private StoreApplicationServiceBean appService;

    /**
     * The store build service.
     */
    @EJB
    private StoreBuildServiceBean buildService;

    /**
     * The activity process service.
     */
    @EJB
    private ActivityProcessServiceBean activityProcessService;

    /**
     * The store activity service.
     */
    @EJB
    private StoreActivityServiceBean activityService;

    /**
     * The store system build service.
     */
    @EJB
    private StoreSystemBuildServiceBean systemBuildService;

    /**
     * The store user service.
     */
    @EJB
    private StoreUserServiceBean userService;
    
    /**
     * The mail service.
     */
    @EJB
    private MailServiceBean mailService;
    
    /**
     * Install the store build.
     *
     * @param key the key.
     * @param build the store build.
     * @throws ServiceException if the method fails.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void install(final String key, final StoreBuild build) throws ServiceException {
        // check the application for to key
        StoreApplicationCriteria criteria = new StoreApplicationCriteria();
        criteria.setKey(key);
        criteria.setFetchProject(true);
        StoreApplication app = appService.getApplication(criteria);
        if (app == null) {
            throw new ServiceException(ErrorKeys.NO_APPLICATION_FOR_KEY_FOUND, key, key);
        }
        if (app.getProject() == null) {
            throw new ServiceException(ErrorKeys.NO_PROJECT_FOR_APPLICATION_FOUND, app.getGuid(), app.getName());
        }

        // instal the build
        StoreBuild buildNew = createBuild(build, app);
        createActivity(buildNew, app, app.getProject());
    }

    /**
     * Deploy the store build.
     *
     * @param sys the system with loaded application and project.
     * @param build the store build.
     * @param type the system build type.
     * @throws ServiceException if the method fails.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deploy(StoreSystem sys, StoreBuild build, StoreSystemBuildType type) throws ServiceException {
        if (sys.getApplication() == null) {
            throw new ServiceException(ErrorKeys.NO_APPLICATION_FOR_SYSTEM_FOUND, sys.getGuid(), sys.getName());
        }
        if (sys.getApplication().getProject() == null) {
            throw new ServiceException(ErrorKeys.NO_PROJECT_FOR_APPLICATION_FOUND, sys.getApplication().getGuid(), sys.getApplication().getName());
        }
        // deploy the build on the system
        deploy(build, type, sys, sys.getApplication(), sys.getApplication().getProject());
    }

    /**
     * Deploy the store build.
     *
     * @param key the key.
     * @param build the store build.
     * @throws ServiceException if the method fails.
     */
    public void deploy(String key, StoreBuild build) throws ServiceException {
        // check the system by key
        StoreSystemCriteria criteria = new StoreSystemCriteria();
        criteria.setKey(key);
        criteria.setFetchApplication(true);
        criteria.setFetchApplicationProject(true);
        StoreSystem sys = systemService.getSystem(criteria);       
        if (sys == null) {
            throw new ServiceException(ErrorKeys.NO_SYSTEM_FOR_KEY_FOUND, key, key);
        }        
        deploy(sys, build, StoreSystemBuildType.MANUAL);
    }

    /**
     * Deploy the store build.
     *
     * @param build the store build.
     * @throws ServiceException if the method fails.
     */
    private void deploy(final StoreBuild build, final StoreSystemBuildType type, final StoreSystem sys, StoreApplication app, StoreProject project) throws ServiceException {

        // check the build
        StoreBuildCriteria buildCriteria = new StoreBuildCriteria();
        buildCriteria.setDate(build.getDate());
        buildCriteria.setApplication(app.getGuid());
        StoreBuild buildOld = buildService.getBuild(buildCriteria);

        StoreActivity activity = null;
        // if the build does not exist install it first
        if (buildOld == null) {
            buildOld = createBuild(build, app);
            activity = createActivity(buildOld, app, project);
        }

        // deploy the build on the system
        StoreSystemBuild sysBuild = new StoreSystemBuild();
        sysBuild.setBuild(buildOld);
        sysBuild.setSystem(sys);
        sysBuild.setType(type);
        sysBuild.setDate(new Date());
        sysBuild = systemBuildService.saveSystemBuild(sysBuild);

        // if not created find already existing activity
        if (activity == null) {
            StoreActivityCriteria ac = new StoreActivityCriteria();
            ac.setBuild(buildOld.getGuid());
            ac.setFetchChange(true);
            ac.setFetchChangeLog(true);
            ac.setFetchChangeLogBuild(true);
            activity = activityService.getActivity(ac);
        }

        if (activity == null) {
            throw new ServiceException(ErrorKeys.NO_ACTIVITY_FOUND_FOR_BUILD, buildOld.getGuid());
        }
        
        // send notification
        notification(buildOld, sys, activity, app, project, sysBuild);
    }

    /**
     * Creates the notification and send mails.
     * @param build the build.
     * @param system the system.
     * @param activity the activity.
     */
    private void notification(StoreBuild build, StoreSystem system, StoreActivity activity, StoreApplication app, StoreProject project, StoreSystemBuild sysBuild) {
        
        // check the notification
        if (!system.isNotification()) {
            LOGGER.log(Level.WARNING, "The notification for the system {0} is disabled!", system.getName());
            return;
        }
        
        // load the users
        Set<StoreUser> users = userService.getUsersEmailsForSystem(system.getGuid());
        if (users == null || users.isEmpty()) {
            LOGGER.log(Level.WARNING, "No user found for the notification system {0}.", system.getName());
        }
        
        // create mails
        List<Mail> mails = createBuildDeployedMails(users, activity, build, system, app, project, sysBuild);
        if (mails != null) {
            for (Mail mail : mails) {
                try {
                    mailService.sendEmail(mail);
                } catch (Exception ex) {                    
                    LOGGER.log(Level.SEVERE, "Error sending the build notification mail to users: {0}", mail.getTo().toString());
                }
            }
        }
    }
    
    /**
     * Creates the activity for the build.
     *
     * @param build the build.
     * @param app the application.
     * @param project the project.
     * @return the activity.
     *
     * @throws ServiceException if the method fails.
     */
    private StoreActivity createActivity(final StoreBuild build, final StoreApplication app, final StoreProject project) throws ServiceException {
        StoreActivity result = null;
        // create activity and save it   
        try {
            StoreActivity activity = activityProcessService.createActivity(build, project.getGuid(), app.getGuid());
            result = activityService.saveActivity(activity);
        } catch (Exception ex) {
            throw new ServiceException(ErrorKeys.ERROR_CREATE_ACTIVITY_FOR_BUILD, build.getGuid(), app.getName(), build.getMavenVersion(), build.getBuild());
        }
        return result;
    }

    /**
     * Creates the store build.
     *
     * @param build the build.
     * @param app the application.
     * @return the activity.
     *
     * @throws ServiceException if the method fails.
     */
    private StoreBuild createBuild(final StoreBuild build, final StoreApplication app) throws ServiceException {
        // check the build
        StoreBuildCriteria buildCriteria = new StoreBuildCriteria();
        buildCriteria.setDate(build.getDate());
        buildCriteria.setApplication(app.getGuid());
        StoreBuild buildOld = buildService.getBuild(buildCriteria);
        if (buildOld != null) {
            throw new ServiceException(ErrorKeys.BUILD_ALREADY_INSTALLED, buildOld.getGuid());
        }

        // create new build and save it
        build.setApplication(app);
        build.setInstall(new Date());
        return buildService.saveBuild(build);
    }
    
    /**
     * Creates the build deployed mails.
     *
     * @param users the set of users.
     * @param system the system.
     * @param build the build.
     * @return the list of mails.
     */
    private List<Mail> createBuildDeployedMails(Set<StoreUser> users, Object... values) {
        List<Mail> result = null;
        if (users != null) {
            result = new ArrayList<>();
            for (StoreUser user : users) {
                Mail mail = new Mail();
                mail.getTo().add(user.getEmail());
                mail.setTemplate(MAIL_BUILD_DEPLOYED_TEMPLATE);
                // add the user to the parameters
                mail.getParameters().put(user.getClass().getSimpleName(), user);
                // add the list of values to the parameters
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
}
