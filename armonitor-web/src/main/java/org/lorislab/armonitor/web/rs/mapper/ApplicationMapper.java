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
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.web.rs.model.Application;

/**
 * The application mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class ApplicationMapper implements MapperService<StoreApplication, Application> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Application map(StoreApplication data, Set<String> profiles) {
        Application result = new Application();
        result.guid = data.getGuid();
        result.name = data.getName();
        result.scmTags = data.getScmTags();
        result.scmTrunk = data.getScmTrunk();
        result.scmBranches = data.getScmBranches();
        result.enabled = data.isEnabled();
        result.repoLink = data.getRepoLink();
        result.type = data.getType();
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
    public StoreApplication update(StoreApplication entity, Application data, Set<String> profiles) {
        entity.setName(data.name);
        entity.setEnabled(data.enabled);
        entity.setScmTrunk(data.scmTrunk);
        entity.setScmBranches(data.scmBranches);
        entity.setScmTags(data.scmTags);
        entity.setRepoLink(data.repoLink);
        entity.setType(data.type);   
        entity.setIndex(data.index);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreApplication create(Application data, Set<String> profiles) {
        StoreApplication result = new StoreApplication();
        result.setGuid(data.guid);
        result = update(result, data, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Application create(Set<String> profiles) {
        StoreApplication tmp = new StoreApplication();
        return map(tmp, profiles);
    }

}
