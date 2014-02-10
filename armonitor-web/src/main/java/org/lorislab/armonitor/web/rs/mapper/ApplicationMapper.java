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
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.web.rs.model.Application;

/**
 * The application mapper.
 *
 * @author Andrej Petras
 */
public class ApplicationMapper implements MapperService<StoreApplication, Application> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Application map(StoreApplication data, String profile) {
        Application result = new Application();
        result.guid = data.getGuid();
        result.name = data.getName();
        result.scmTags = data.getScmTags();
        result.scmTrunk = data.getScmTrunk();
        result.scmBranches = data.getScmBranches();
        result.enabled = data.isEnabled();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreApplication update(StoreApplication entity, Application data, String profile) {
        entity.setName(data.name);
        entity.setEnabled(data.enabled);
        entity.setScmTrunk(data.scmTrunk);
        entity.setScmBranches(data.scmBranches);
        entity.setScmTags(data.scmTags);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreApplication create(Application data, String profile) {
        StoreApplication result = new StoreApplication();
        result.setGuid(data.guid);
        result = update(result, data, profile);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Application create(String profile) {
        StoreApplication tmp = new StoreApplication();
        return map(tmp, profile);
    }

}
