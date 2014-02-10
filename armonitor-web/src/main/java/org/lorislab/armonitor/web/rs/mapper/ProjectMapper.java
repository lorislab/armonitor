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

import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.web.rs.model.Project;

/**
 * The project mapper.
 *
 * @author Andrej Petras
 */
public class ProjectMapper implements MapperService<StoreProject, Project> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Project map(StoreProject data, String profile) {
        Project result = new Project();
        result.guid = data.getGuid();
        result.name = data.getName();
        result.btsId = data.getBtsId();
        result.enabled = data.isEnabled();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreProject update(StoreProject entity, Project data, String profile) {
        entity.setName(data.name);
        entity.setBtsId(data.btsId);
        entity.setEnabled(data.enabled);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreProject create(Project data, String profile) {
        StoreProject result = new StoreProject();
        result.setGuid(data.guid);
        result = update(result, data, profile);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project create(String profile) {
        StoreProject role = new StoreProject();
        return map(role, profile);
    }

}
