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
import org.lorislab.armonitor.store.model.StoreSCMSystem;
import org.lorislab.armonitor.store.model.enums.StoreSCMSystemType;
import org.lorislab.armonitor.web.rs.model.SCMSystem;
import org.lorislab.armonitor.web.rs.model.SCMSystemType;

/**
 *
 * @author Andrej Petras
 */
public class SCMSystemMapper implements MapperService<StoreSCMSystem, SCMSystem> {

    @Override
    public SCMSystem map(StoreSCMSystem data, String profile) {
        SCMSystem result = new SCMSystem();
        result.guid = data.getGuid();
        result.auth = data.isAuth();
        result.server = data.getServer();
        result.user = data.getUser();
        result.type = null;
        if (data.getType() != null) {
            result.type = SCMSystemType.valueOf(data.getType().name());
        }
        return result;
    }

    @Override
    public StoreSCMSystem update(StoreSCMSystem entity, SCMSystem data, String profile) {
        entity.setAuth(data.auth);
        entity.setServer(data.server);
        entity.setUser(data.user);
        entity.setType(null);
        if (data.type != null) {
            entity.setType(StoreSCMSystemType.valueOf(data.type.name()));
        }
        return entity;
    }

    @Override
    public StoreSCMSystem create(SCMSystem data, String profile) {
        StoreSCMSystem result = new StoreSCMSystem();
        result.setGuid(data.guid);
        result = update(result, data, profile);
        return result;
    }

    @Override
    public SCMSystem create(String profile) {
        StoreSCMSystem role = new StoreSCMSystem();
        return map(role, profile);
    }
    
}
