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
import org.lorislab.armonitor.store.model.StoreSCMSystem;
import org.lorislab.armonitor.web.rs.model.SCMSystem;

/**
 * The SCM system mapper.
 *
 * @author Andrej Petras
 */
public class SCMSystemMapper implements MapperService<StoreSCMSystem, SCMSystem> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SCMSystem map(StoreSCMSystem data, Set<String> profiles) {
        SCMSystem result = new SCMSystem();
        result.guid = data.getGuid();
        result.auth = data.isAuth();
        result.server = data.getServer();
        result.user = data.getUser();
        result.readTimeout = data.getReadTimeout();
        result.connectionTimeout = data.getConnectionTimeout();
        result.type = data.getType();       
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSCMSystem update(StoreSCMSystem entity, SCMSystem data, Set<String> profiles) {
        entity.setAuth(data.auth);
        entity.setServer(data.server);
        entity.setUser(data.user);
        entity.setConnectionTimeout(data.connectionTimeout);
        entity.setReadTimeout(data.readTimeout);
        entity.setType(data.type);       
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSCMSystem create(SCMSystem data, Set<String> profiles) {
        StoreSCMSystem result = new StoreSCMSystem();
        result.setGuid(data.guid);
        result = update(result, data, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SCMSystem create(Set<String> profiles) {
        StoreSCMSystem role = new StoreSCMSystem();
        return map(role, profiles);
    }

}
