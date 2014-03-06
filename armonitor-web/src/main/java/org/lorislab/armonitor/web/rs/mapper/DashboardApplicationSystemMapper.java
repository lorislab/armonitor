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
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreSystem;
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
    public DashboardApplicationSystem map(StoreSystem data, Set<String> profiles) {
        DashboardApplicationSystem result = new DashboardApplicationSystem();
        result.guid = data.getGuid();
        result.name = data.getName();
        result.link = data.getLink();
        result.domain = data.getDomain();
        result.clazz = data.getClassification();
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
    public StoreSystem update(StoreSystem entity, DashboardApplicationSystem data, Set<String> profiles) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystem create(DashboardApplicationSystem data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardApplicationSystem create(Set<String> profiles) {
        StoreSystem role = new StoreSystem();
        return map(role, profiles);
    }

}
