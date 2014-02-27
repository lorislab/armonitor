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
import org.lorislab.armonitor.store.criteria.StoreBuildCriteria;
import org.lorislab.armonitor.store.ejb.StoreBuildServiceBean;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.web.rs.model.Build;
import org.lorislab.armonitor.web.rs.model.BuildCriteria;
import org.lorislab.armonitor.web.rs.model.TimelineBuild;
import org.lorislab.armonitor.web.rs.util.LinkUtil;

/**
 * The build service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BuildServiceBean {

    /**
     * The store build service.
     */
    @EJB
    private StoreBuildServiceBean service;

    /**
     * Gets the list of builds by the criteria.
     *
     * @param criteria the criteria.
     * @return the list of builds corresponding to the criteria.
     */
    public List<Build> getBuilds(BuildCriteria criteria) {
        StoreBuildCriteria sc = Mapper.create(criteria, StoreBuildCriteria.class);
        List<StoreBuild> tmp = service.getBuilds(sc);
        String profile = null;
        if (criteria.params) {
            profile = "params";
        }
        return Mapper.map(tmp, Build.class, profile);
    }

    /**
     * Gets the list of builds by the criteria.
     *
     * @param criteria the criteria.
     * @return the list of builds corresponding to the criteria.
     */
    public List<TimelineBuild> getDashboardBuilds(BuildCriteria criteria) {
        StoreBuildCriteria sc = Mapper.create(criteria, StoreBuildCriteria.class);
        List<StoreBuild> tmp = service.getBuilds(sc);
        return Mapper.map(tmp, TimelineBuild.class);
    }

    /**
     * Gets the build.
     *
     * @param guid the GUID.
     * @return the build.
     */
    public Build getBuild(String guid) {
        StoreBuildCriteria bc = new StoreBuildCriteria();
        bc.setGuid(guid);
        bc.setFetchParameters(true);
        bc.setFetchApplication(true);
        StoreBuild tmp = service.getBuild(bc);
        return Mapper.map(tmp, Build.class, "link", "params");
    }

    /**
     * Gets the link for the build.
     * @param guid the build GUID.
     * @return the link corresponding to the build.
     */
    public String getLink(String guid) {
        String result = null;
        StoreBuildCriteria bc = new StoreBuildCriteria();
        bc.setGuid(guid);
        bc.setFetchApplication(true);
        StoreBuild tmp = service.getBuild(bc);
        if (tmp != null && tmp.getApplication() != null) {
            Build build = Mapper.map(tmp, Build.class);
            result = LinkUtil.createLink(tmp.getApplication().getRepoLink(), build);
        }
        return result;
    }
   
}
