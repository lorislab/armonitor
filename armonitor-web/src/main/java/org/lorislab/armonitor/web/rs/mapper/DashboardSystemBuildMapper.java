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
package org.lorislab.armonitor.web.rs.mapper;

import java.util.Set;
import org.kohsuke.MetaInfServices;
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.web.rs.model.DashboardSystemBuild;
import org.lorislab.armonitor.util.LinkUtil;

/**
 * he dashboard system build mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class DashboardSystemBuildMapper implements MapperService<StoreSystemBuild, DashboardSystemBuild> {

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardSystemBuild map(StoreSystemBuild data, Set<String> profiles) {
        DashboardSystemBuild result = new DashboardSystemBuild();
        result.guid = data.getGuid();
        result.date = data.getDate();
        StoreBuild build = data.getBuild();
        if (build != null) {
            result.build = build.getGuid();
            result.rc = build.getBuild();
            result.version = build.getMavenVersion();
            result.scm = build.getScm();
        }
        StoreSystem sys = data.getSystem();
        if (sys != null) {
            result.system = sys.getGuid();
            
            if (sys.getApplication() != null) {
                String link = sys.getApplication().getRepoLink();
                result.link = LinkUtil.createLink(link, build);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystemBuild update(StoreSystemBuild entity, DashboardSystemBuild data, Set<String> profiles) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystemBuild create(DashboardSystemBuild data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardSystemBuild create(Set<String> profiles) {
        StoreSystemBuild role = new StoreSystemBuild();
        return map(role, profiles);
    }
}
