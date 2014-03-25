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

import org.lorislab.armonitor.activity.ejb.ActivityProcessServiceBean;
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
import org.lorislab.armonitor.activity.criteria.ActivityWrapperCriteria;
import org.lorislab.armonitor.activity.ejb.ActivityWrapperServiceBean;
import org.lorislab.armonitor.activity.wrapper.ActivityWrapper;
import org.lorislab.armonitor.mail.ejb.MailServiceBean;
import org.lorislab.armonitor.mail.model.Mail;
import org.lorislab.armonitor.process.resources.ErrorKeys;
import org.lorislab.armonitor.store.criteria.StoreApplicationCriteria;
import org.lorislab.armonitor.store.criteria.StoreBuildCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemBuildCriteria;
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
    private static final String MAIL_BUILD_DEPLOYED_TEMPLATE = "deploy";
    
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
     * The activity wrapper service.
     */
    @EJB
    private ActivityWrapperServiceBean activityWrapperService;
    
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
        StoreApplication app = appService.getApplication(criteria);
        if (app == null) {
            throw new ServiceException(ErrorKeys.NO_APPLICATION_FOR_KEY_FOUND, key, key);
        }
        install(app, build);               
    }
   
    private StoreBuild install(final StoreApplication app, final StoreBuild build) throws ServiceException {      
        // create new build and save it
        StoreBuild buildNew = null;
        try {
            build.setApplication(app);
            build.setInstall(new Date());
            buildNew = buildService.saveBuild(build);
        } catch (Exception ex) {            
            throw new ServiceException(ErrorKeys.BUILD_ALREADY_INSTALLED, ex, app.getName(), build.getMavenVersion(), build.getBuild());
        }        
        
        // create activity for the build.
        try {
            StoreActivity activity = activityProcessService.createActivity(app, buildNew);
            activityService.saveActivity(activity);
        } catch (Exception ex) {
            throw new ServiceException(ErrorKeys.ERROR_CREATE_ACTIVITY_FOR_BUILD, ex, app.getName(), build.getMavenVersion(), build.getBuild());
        }       
        
        return buildNew;
    }

    /**
     * Deploy the store build.
     *
     * @param key the key.
     * @param build the store build.
     * @throws ServiceException if the method fails.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)    
    public void deploy(final String key, final StoreBuild build) throws ServiceException {
        // check the system by key
        StoreSystemCriteria criteria = new StoreSystemCriteria();
        criteria.setKey(key);
        criteria.setFetchApplication(true);
        StoreSystem system = systemService.getSystem(criteria);       
        if (system == null) {
            throw new ServiceException(ErrorKeys.NO_SYSTEM_FOR_KEY_FOUND, key, key);
        }        
        deploy(system, system.getApplication(), build, StoreSystemBuildType.MANUAL);
    }

    /**
     * Deploy the store build.
     *
     * @param system the store system.
     * @param build the store build.
     * @param type the type of deployment.
     * @param application the application.
     * @throws ServiceException if the method fails.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)    
    public void deploy(final StoreSystem system, final StoreApplication application, final StoreBuild build, final StoreSystemBuildType type) throws ServiceException {

        // check the build
        StoreBuildCriteria buildCriteria = new StoreBuildCriteria();
        buildCriteria.setDate(build.getDate());
        buildCriteria.setKey(build.getKey()); 
        buildCriteria.setApplication(application.getGuid());
        StoreBuild buildOld = buildService.getBuild(buildCriteria);

        // if the build does not exist install it first
        if (buildOld == null) {
            buildOld = install(application, build);
        }

        // deploy the build on the system
        StoreSystemBuild sysBuild = new StoreSystemBuild();
        sysBuild.setBuild(buildOld);
        sysBuild.setSystem(system);
        sysBuild.setType(type);
        sysBuild.setDate(new Date());
        sysBuild = systemBuildService.saveSystemBuild(sysBuild);
        
        // send notification
        notification(buildOld.getGuid(), system, sysBuild);       
    }

    public void sendNotificationForSystem(String guid) throws ServiceException {
        // load the system build, build and system
        StoreSystemBuildCriteria criteria = new StoreSystemBuildCriteria();
        criteria.setMaxDate(Boolean.TRUE);
        criteria.setSystem(guid);
        criteria.setFetchSystem(true);
        criteria.setFetchBuild(true);
        StoreSystemBuild systemBuild = systemBuildService.getSystemBuild(criteria);
        if (systemBuild == null) {
            throw new ServiceException(ErrorKeys.NO_SYSTEM_BUILD_FOR_SYSTEM_FOUND, guid);
        }
        // send notification
        notification(systemBuild.getBuild().getGuid(), systemBuild.getSystem(), systemBuild);
    }
    
    public void sendNotificationForSystemBuild(String guid) throws ServiceException {
        // load the system build, build and system
        StoreSystemBuildCriteria criteria = new StoreSystemBuildCriteria();
        criteria.setGuid(guid);
        criteria.setFetchSystem(true);
        criteria.setFetchBuild(true);
        StoreSystemBuild systemBuild = systemBuildService.getSystemBuild(criteria);
        if (systemBuild == null) {
            throw new ServiceException(ErrorKeys.NO_SYSTEM_BUILD_FOUND, guid);
        }
        // send notification
        notification(systemBuild.getBuild().getGuid(), systemBuild.getSystem(), systemBuild);
    }
    
    /**
     * Creates the notification and send mails.
     * @param build the build.
     * @param system the system.
     * @param activity the activity.
     */
    private void notification(String build, StoreSystem system, StoreSystemBuild sysBuild) throws ServiceException {
        
        // check the notification
        if (!system.isNotification()) {
            LOGGER.log(Level.WARNING, "The notification for the system {0} is disabled!", system.getName());
            return;
        }
        
        ActivityWrapperCriteria wc = new ActivityWrapperCriteria();
        wc.setSortList(true);
        wc.setBuild(build);
            
        ActivityWrapper wrapper = activityWrapperService.create(wc);
        if (wrapper == null) {
            LOGGER.log(Level.SEVERE, "No activity for the build found!");
            return;
//            throw new ServiceException(ErrorKeys.NO_ACTIVITY_FOUND_FOR_BUILD, build);
        }        
                
        // load the users
        Set<StoreUser> users = userService.getUsersEmailsForSystem(system.getGuid());
        if (users == null || users.isEmpty()) {
            LOGGER.log(Level.WARNING, "No user found for the notification system {0}.", system.getName());
            return;
        }
        
        // create mails
        List<Mail> mails = createBuildDeployedMails(users, wrapper.getBuild(), system, wrapper, wrapper.getApplication(), wrapper.getProject(), sysBuild);
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
