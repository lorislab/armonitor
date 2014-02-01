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
import org.lorislab.armonitor.store.model.StoreRole;
import org.lorislab.armonitor.web.rs.model.Role;

/**
 *
 * @author Andrej Petras
 */
public class RoleMapper implements MapperService<StoreRole, Role> {

    @Override
    public Role map(StoreRole data, String profile) {
        Role result = new Role();
        result.guid = data.getGuid();
        result.name = data.getName();
        return result;
    }

    @Override
    public StoreRole update(StoreRole entity, Role data, String profile) {
        entity.setName(data.name);
        return entity;
    }

    @Override
    public StoreRole create(Role data, String profile) {
        StoreRole result = new StoreRole();
        result.setGuid(data.guid);
        result = update(result, data, profile);
        return result;
    }

    @Override
    public Role create(String profile) {
        StoreRole role = new StoreRole();
        return map(role, profile);
    }
    
}
