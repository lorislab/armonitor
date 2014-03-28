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
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.web.rs.model.DashboardApplication;
import org.lorislab.armonitor.web.rs.model.DashboardProject;

/**
 * The dashboard project mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class DashboardProjectMapper implements MapperService<StoreProject, DashboardProject> {

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardProject map(StoreProject data, Set<String> profiles) {
        DashboardProject result = new DashboardProject();
        result.guid = data.getGuid();
        result.name = data.getName();
        result.index = data.getIndex();
        result.applications = Mapper.map(data.getApplications(), DashboardApplication.class, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreProject update(StoreProject entity, DashboardProject data, Set<String> profiles) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreProject create(DashboardProject data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardProject create(Set<String> profiles) {
        StoreProject role = new StoreProject();
        return map(role, profiles);
    }

}
