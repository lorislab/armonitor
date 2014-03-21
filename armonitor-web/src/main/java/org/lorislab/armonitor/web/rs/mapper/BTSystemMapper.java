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
import org.lorislab.armonitor.store.model.StoreBTSystem;
import org.lorislab.armonitor.web.rs.model.BTSystem;

/**
 * The bug tracking system mapper.
 *
 * @author Andrej Petras
 */
public class BTSystemMapper implements MapperService<StoreBTSystem, BTSystem> {

    /**
     * {@inheritDoc}
     */
    @Override
    public BTSystem map(StoreBTSystem data, Set<String> profiles) {
        BTSystem result = new BTSystem();
        result.guid = data.getGuid();
        result.auth = data.isAuth();
        result.link = data.getLink();
        result.server = data.getServer();
        result.user = data.getUser();
        result.type = data.getType();
        result.n = data.isNew();
        result.name = data.getName();
        result.v = data.getVersion();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreBTSystem update(StoreBTSystem entity, BTSystem data, Set<String> profiles) {
        entity.setAuth(data.auth);
        entity.setLink(data.link);
        entity.setServer(data.server);
        entity.setUser(data.user);
        entity.setType(data.type);
        entity.setName(data.name);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreBTSystem create(BTSystem data, Set<String> profiles) {
        StoreBTSystem result = new StoreBTSystem();
        result.setGuid(data.guid);
        result = update(result, data, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BTSystem create(Set<String> profiles) {
        StoreBTSystem role = new StoreBTSystem();
        return map(role, profiles);
    }

}
