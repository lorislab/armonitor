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
import org.lorislab.armonitor.store.model.StoreBTSystem;
import org.lorislab.armonitor.store.model.enums.StoreBTSystemType;
import org.lorislab.armonitor.web.rs.model.BTSystem;
import org.lorislab.armonitor.web.rs.model.BTSystemType;

/**
 *
 * @author Andrej Petras
 */
public class BTSystemMapper implements MapperService<StoreBTSystem, BTSystem> {

    @Override
    public BTSystem map(StoreBTSystem data, String profile) {
        BTSystem result = new BTSystem();
        result.guid = data.getGuid();
        result.auth = data.isAuth();
        result.link = data.getLink();
        result.server = data.getServer();
        result.user = data.getUser();
        result.type = null;
        if (data.getType() != null) {
            result.type = BTSystemType.valueOf(data.getType().name());
        }
        return result;
    }

    @Override
    public StoreBTSystem update(StoreBTSystem entity, BTSystem data, String profile) {
        entity.setAuth(data.auth);
        entity.setLink(data.link);
        entity.setServer(data.server);
        entity.setUser(data.user);
        entity.setType(null);
        if (data.type != null) {
            entity.setType(StoreBTSystemType.valueOf(data.type.name()));
        }
        return entity;
    }

    @Override
    public StoreBTSystem create(BTSystem data, String profile) {
        StoreBTSystem result = new StoreBTSystem();
        result.setGuid(data.guid);
        result = update(result, data, profile);
        return result;
    }

    @Override
    public BTSystem create(String profile) {
        StoreBTSystem role = new StoreBTSystem();
        return map(role, profile);
    }
    
}
