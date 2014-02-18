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
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.web.rs.model.DashboardApplication;
import org.lorislab.armonitor.web.rs.model.DashboardProject;
import org.lorislab.armonitor.web.rs.model.Project;

/**
 * The dashboard project mapper.
 *
 * @author Andrej Petras
 */
public class DashboardProjectMapper implements MapperService<StoreProject, DashboardProject> {

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardProject map(StoreProject data, String profile) {
        DashboardProject result = new DashboardProject();
        result.project = Mapper.map(data, Project.class, profile);
        if (result.project != null) {
            result.guid = result.project.guid;
        }
        result.applications = Mapper.convert(data.getApplications(), DashboardApplication.class, profile);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreProject update(StoreProject entity, DashboardProject data, String profile) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreProject create(DashboardProject data, String profile) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardProject create(String profile) {
        StoreProject role = new StoreProject();
        return map(role, profile);
    }

}
