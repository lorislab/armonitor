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
import org.lorislab.armonitor.store.model.StoreUser;
import org.lorislab.armonitor.web.rs.model.User;

/**
 * The user mapper.
 *
 * @author Andrej Petras
 */
public class UserMapper implements MapperService<StoreUser, User> {

    /**
     * {@inheritDoc}
     */
    @Override
    public User map(StoreUser data, Set<String> profiles) {
        User result = new User();
        result.guid = data.getGuid();
        result.email = data.getEmail();
        result.login = data.isLogin();
        result.name = data.getName();
        result.n = data.isNew();
        result.enabled = data.isEnabled();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreUser update(StoreUser entity, User data, Set<String> profiles) {
        entity.setEmail(data.email);
        entity.setLogin(data.login);
        entity.setName(data.name);
        entity.setEnabled(data.enabled);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreUser create(User data, Set<String> profiles) {
        StoreUser result = new StoreUser();
        result.setGuid(data.guid);
        result = update(result, data, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(Set<String> profiles) {
        return map(new StoreUser(), profiles);
    }

}
