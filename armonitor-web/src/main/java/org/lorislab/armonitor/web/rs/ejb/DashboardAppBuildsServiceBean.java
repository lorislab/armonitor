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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.criteria.StoreApplicationCriteria;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.web.rs.model.DashboardAppBuilds;

/**
 * The dashboard application service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DashboardAppBuildsServiceBean {

    /**
     * The store application service.
     */
    @EJB
    private StoreApplicationServiceBean service;

    /**
     * Gets the dashboard application.
     *
     * @param guid the application GUID.
     * @return the dashboard application.
     */
    public DashboardAppBuilds getApplication(String guid) {

        // search system
        StoreApplicationCriteria criteria = new StoreApplicationCriteria();
        criteria.setGuid(guid);
        criteria.setFetchProject(true);
        criteria.setFetchBuilds(true);
        
        StoreApplication sys = service.getApplication(criteria);
        DashboardAppBuilds result = Mapper.map(sys, DashboardAppBuilds.class);
        return result;
    }

}
