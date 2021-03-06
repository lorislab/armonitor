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

/**
 * The project name mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class ProjectNameMapper implements MapperService<StoreProject, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String map(StoreProject data, Set<String> profiles) {
        return data.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreProject update(StoreProject entity, String data, Set<String> profiles) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreProject create(String data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String create(Set<String> profiles) {
        return null;
    }

}
