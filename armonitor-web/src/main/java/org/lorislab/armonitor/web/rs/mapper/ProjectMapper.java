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
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.web.rs.model.Project;

/**
 * The project mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class ProjectMapper implements MapperService<StoreProject, Project> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Project map(StoreProject data, Set<String> profiles) {
        Project result = new Project();
        result.guid = data.getGuid();
        result.name = data.getName();
        result.btsId = data.getBtsId();
        result.enabled = data.isEnabled();
        result.n = data.isNew();
        result.v = data.getVersion();    
        result.index = data.getIndex();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreProject update(StoreProject entity, Project data, Set<String> profiles) {
        entity.setName(data.name);
        entity.setBtsId(data.btsId);
        entity.setEnabled(data.enabled);
        entity.setIndex(data.index);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreProject create(Project data, Set<String> profiles) {
        StoreProject result = new StoreProject();
        result.setGuid(data.guid);
        result = update(result, data, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project create(Set<String> profiles) {
        StoreProject role = new StoreProject();
        return map(role, profiles);
    }

}
