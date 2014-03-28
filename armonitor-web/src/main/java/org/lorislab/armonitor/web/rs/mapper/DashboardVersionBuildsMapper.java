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
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.web.rs.model.DashboardVersionBuilds;
import org.lorislab.armonitor.web.rs.model.TimelineBuild;

/**
 * The dashboard version builds mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class DashboardVersionBuildsMapper implements MapperService<StoreApplication, DashboardVersionBuilds> {

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardVersionBuilds map(StoreApplication data, Set<String> profiles) {
        DashboardVersionBuilds result = new DashboardVersionBuilds();
        result.guid = data.getGuid();
        result.name = data.getName();
        StoreProject project = data.getProject();
        if (project != null) {
            result.project = project.getGuid();
            result.projectName = project.getName();
        }
        result.builds = Mapper.map(data.getBuilds(), TimelineBuild.class, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreApplication update(StoreApplication entity, DashboardVersionBuilds data, Set<String> profiles) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreApplication create(DashboardVersionBuilds data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardVersionBuilds create(Set<String> profiles) {
        return null;
    }

}
