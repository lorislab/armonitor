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
import org.lorislab.armonitor.web.rs.model.DashboardVersionBuilds;

/**
 * The dashboard version builds service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DashboardVersionBuildsServiceBean {

    /**
     * The store application service.
     */
    @EJB
    private StoreApplicationServiceBean service;
    
    /**
     * Gets the version builds.
     *
     * @param app the application GUID.
     * @param version the version.
     * @return the version builds.
     */
    public DashboardVersionBuilds getVersion(String app, String version) {
        StoreApplicationCriteria sac = new StoreApplicationCriteria();
        sac.setGuid(app);
        sac.setFetchProject(true);  
        sac.setFetchBuilds(true);
        sac.setFetchBuildsVersion(version);
        StoreApplication tmp = service.getApplication(sac);        
        return Mapper.map(tmp, DashboardVersionBuilds.class);        
    }

}
