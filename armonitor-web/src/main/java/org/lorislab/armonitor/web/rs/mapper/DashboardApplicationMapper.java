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
import org.lorislab.armonitor.web.rs.model.DashboardApplication;
import org.lorislab.armonitor.web.rs.model.DashboardApplicationSystem;

/**
 * The dashboard application mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class DashboardApplicationMapper implements MapperService<StoreApplication, DashboardApplication> {

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardApplication map(StoreApplication data, Set<String> profiles) {
        DashboardApplication result = new DashboardApplication();
        result.guid = data.getGuid();
        result.name = data.getName();
        result.index = data.getIndex();
        if (data.getProject() != null) {
            result.project = data.getProject().getGuid();
        }
        result.systems = Mapper.map(data.getSystems(), DashboardApplicationSystem.class, profiles);       
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreApplication update(StoreApplication entity, DashboardApplication data, Set<String> profiles) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreApplication create(DashboardApplication data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DashboardApplication create(Set<String> profiles) {
        StoreApplication role = new StoreApplication();
        return map(role, profiles);
    }

}
