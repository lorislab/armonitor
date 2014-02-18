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

import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.web.rs.model.Build;
import org.lorislab.armonitor.web.rs.model.DashboardSystemBuild;
import org.lorislab.armonitor.web.rs.model.SystemBuild;

/**
 * he dashboard system build mapper.
 *
 * @author Andrej Petras
 */
public class DashboardSystemBuildMapper implements MapperService<StoreSystemBuild, DashboardSystemBuild> {

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardSystemBuild map(StoreSystemBuild data, String profile) {
        DashboardSystemBuild result = new DashboardSystemBuild();
        result.systemBuild = Mapper.map(data, SystemBuild.class, profile);
        result.build = Mapper.map(data.getBuild(), Build.class, profile);
        if (result.systemBuild != null) {
            result.guid = result.systemBuild.guid;
        }
        if (data.getSystem() != null) {
            result.system = data.getSystem().getGuid();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystemBuild update(StoreSystemBuild entity, DashboardSystemBuild data, String profile) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystemBuild create(DashboardSystemBuild data, String profile) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardSystemBuild create(String profile) {
        StoreSystemBuild role = new StoreSystemBuild();
        return map(role, profile);
    }
}
