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
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;

/**
 *
 * @author Andrej Petras
 */
public class ApplicationSystemMapper implements MapperService<StoreSystem, ApplicationSystem> {

    @Override
    public ApplicationSystem map(StoreSystem data, String profile) {
        ApplicationSystem result = new ApplicationSystem();
        result.guid = data.getGuid();
        result.name = data.getName();
        result.enabled = data.isEnabled();
        result.timer = data.isTimer();
        return result;
    }

    @Override
    public StoreSystem update(StoreSystem entity, ApplicationSystem data, String profile) {
        entity.setName(data.name);
        entity.setEnabled(data.enabled);
        entity.setTimer(data.timer);
        return entity;
    }

    @Override
    public StoreSystem create(ApplicationSystem data, String profile) {
        StoreSystem result = new StoreSystem();
        result.setGuid(data.guid);
        result = update(result, data, profile);
        return result;
    }

    @Override
    public ApplicationSystem create(String profile) {
        StoreSystem tmp = new StoreSystem();
        return map(tmp, profile);
    }

}
