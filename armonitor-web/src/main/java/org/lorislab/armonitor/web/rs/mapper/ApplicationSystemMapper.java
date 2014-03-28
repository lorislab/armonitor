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
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;

/**
 * The application system mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class ApplicationSystemMapper implements MapperService<StoreSystem, ApplicationSystem> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationSystem map(StoreSystem data, Set<String> profiles) {
        ApplicationSystem result = new ApplicationSystem();
        result.guid = data.getGuid();
        result.name = data.getName();
        result.enabled = data.isEnabled();
        result.timer = data.isTimer();
        result.notification = data.isNotification();
        result.service = data.getService();
        result.domain = data.getDomain();
        result.link = data.getLink();
        result.clazz = data.getClassification();
        result.n = data.isNew();
        result.v = data.getVersion();
        result.key = data.getKey();
        result.index = data.getIndex();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystem update(StoreSystem entity, ApplicationSystem data, Set<String> profiles) {
        entity.setName(data.name);
        entity.setEnabled(data.enabled);
        entity.setTimer(data.timer);
        entity.setNotification(data.notification);
        entity.setService(data.service);
        entity.setLink(data.link);
        entity.setDomain(data.domain);
        entity.setClassification(data.clazz);
        entity.setIndex(data.index);        
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystem create(ApplicationSystem data, Set<String> profiles) {
        StoreSystem result = new StoreSystem();
        result.setGuid(data.guid);
        result = update(result, data, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationSystem create(Set<String> profiles) {
        StoreSystem tmp = new StoreSystem();
        return map(tmp, profiles);
    }

}
