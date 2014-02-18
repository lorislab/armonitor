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
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;
import org.lorislab.armonitor.web.rs.model.DashboardApplicationSystem;

/**
 * The dashboard application system mapper.
 *
 * @author Andrej Petras
 */
public class DashboardApplicationSystemMapper implements MapperService<StoreSystem, DashboardApplicationSystem> {

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardApplicationSystem map(StoreSystem data, String profile) {
        DashboardApplicationSystem result = new DashboardApplicationSystem();
        result.system = Mapper.map(data, ApplicationSystem.class, profile);
        if (result.system != null) {
            result.guid = result.system.guid;
        }
        StoreApplication app = data.getApplication();
        if (app != null) {
            result.application = app.getGuid();
            if (app.getProject() != null) {
                result.project = app.getProject().getGuid();
            }
        }
        result.systemBuild = null;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystem update(StoreSystem entity, DashboardApplicationSystem data, String profile) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystem create(DashboardApplicationSystem data, String profile) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardApplicationSystem create(String profile) {
        StoreSystem role = new StoreSystem();
        return map(role, profile);
    }

}
