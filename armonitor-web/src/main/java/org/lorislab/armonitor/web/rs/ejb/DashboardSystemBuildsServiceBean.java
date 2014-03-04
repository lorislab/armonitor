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

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.criteria.StoreSystemBuildCriteria;
import org.lorislab.armonitor.store.criteria.StoreSystemCriteria;
import org.lorislab.armonitor.store.ejb.StoreSystemBuildServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.web.rs.model.DashboardSystemBuilds;
import org.lorislab.armonitor.web.rs.model.TimelineBuild;

/**
 * The dashboard system service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DashboardSystemBuildsServiceBean {

    /**
     * The store system service.
     */
    @EJB
    private StoreSystemServiceBean service;
    /**
     * The store system build service.
     */
    @EJB
    private StoreSystemBuildServiceBean sysBuildService;

    /**
     * Gets the dashboard system.
     *
     * @param guid the system GUID.
     * @return the dashboard system.
     */
    public DashboardSystemBuilds getSystem(String guid) {

        // search system
        StoreSystemCriteria criteria = new StoreSystemCriteria();
        criteria.setGuid(guid);
        criteria.setFetchApplication(true);
        criteria.setFetchApplicationProject(true);
        StoreSystem sys = service.getSystem(criteria);
        DashboardSystemBuilds result = Mapper.map(sys, DashboardSystemBuilds.class);

        if (result != null) {

            // search system builds
            StoreSystemBuildCriteria sbc = new StoreSystemBuildCriteria();
            sbc.setSystem(guid);
            sbc.setFetchBuild(true);

            List<StoreSystemBuild> builds = sysBuildService.getSystemBuilds(sbc);
            result.builds = Mapper.map(builds, TimelineBuild.class);
        }
        return result;
    }

}
