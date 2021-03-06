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
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.DashboardSystemBuilds;

/**
 * The dashboard system mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class DashboardSystemBuildsMapper implements MapperService<StoreSystem, DashboardSystemBuilds> {

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardSystemBuilds map(StoreSystem data, Set<String> profiles) {
        DashboardSystemBuilds result = new DashboardSystemBuilds();
        result.guid = data.getGuid();
        result.name = data.getName();
        StoreApplication app = data.getApplication();
        if (app != null) {
            result.app = app.getGuid();
            result.appName = app.getName();

            StoreProject project = app.getProject();
            if (project != null) {
                result.project = project.getGuid();
                result.projectName = project.getName();
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystem update(StoreSystem entity, DashboardSystemBuilds data, Set<String> profiles) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystem create(DashboardSystemBuilds data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardSystemBuilds create(Set<String> profiles) {
        return null;
    }

}
