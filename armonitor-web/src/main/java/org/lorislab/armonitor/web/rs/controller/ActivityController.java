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
package org.lorislab.armonitor.web.rs.controller;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.lorislab.armonitor.web.rs.ejb.ActivityServiceBean;
import org.lorislab.armonitor.web.rs.model.Activity;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The activity session controller.
 *
 * @author Andrej Petras
 */
@Named
@SessionScoped
@CdiServiceMethod
public class ActivityController implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 8249649300401551656L;
    /**
     * The build GUID.
     */
    private String guid;
    /**
     * The activity.
     */
    private Activity activity;

    /**
     * The activity (now)
     */
    private Activity now;
    
    /**
     * The activity service.
     */
    @EJB
    private ActivityServiceBean service;

    /**
     * Gets the activity.
     *
     * @param guid the build GUID.
     * @return the activity.
     */
    public Activity getActivity(String guid) {
        if (this.guid == null || !this.guid.equals(guid)) {
            reloadActivity(guid);
        }
        return activity;
    }
    
    /**
     * Gets the activity (now).
     *
     * @param guid the build GUID.
     * @return the activity.
     */    
    public Activity getActivityNow(String guid) {
        if (this.guid == null || this.now == null || !this.guid.equals(guid)) {
            reloadActivityNow(guid);
        }
        return now;
    }

    /**
     * Reloads the activity (now).
     *
     * @param guid the build GUID.
     * @return the activity.
     */
    public Activity reloadActivityNow(String guid) {
        now = service.getActivityNowForBuild(guid);
        this.guid = guid;
        return now;
    }  
    
    /**
     * Reloads the activity.
     *
     * @param guid the build GUID.
     * @return the activity.
     */
    public Activity reloadActivity(String guid) {
        now = null;
        activity = service.getActivityForBuild(guid);        
        this.guid = guid;
        return activity;
    }   
}
