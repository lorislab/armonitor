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
package org.lorislab.armonitor.web.rs.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.activity.criteria.ActivityWrapperCriteria;
import org.lorislab.armonitor.activity.ejb.ActivityWrapperServiceBean;
import org.lorislab.armonitor.activity.wrapper.ActivityWrapper;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.web.rs.model.Activity;

/**
 * The activity service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ActivityServiceBean {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ActivityServiceBean.class.getName());
    /**
     * The activity service.
     */
    @EJB
    private ActivityWrapperServiceBean activityService;
      
    /**
     * Gets the activity for the build.
     *
     * @param build the build GUID.
     * @return the activity.
     */
    public Activity getActivityForBuild(String build) {
        try {
            ActivityWrapperCriteria criteria = new ActivityWrapperCriteria();
            criteria.setBuild(build);
            criteria.setSortList(true);
            ActivityWrapper wrapper = activityService.create(criteria);
            return Mapper.map(wrapper, Activity.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error create activity for the build {0}, Error: {1}", new Object[]{build, ex.getMessage()});
            LOGGER.log(Level.FINE, "Error create activity", ex);
        }
        return null;
    }
}
