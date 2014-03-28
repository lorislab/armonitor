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
import org.lorislab.armonitor.store.criteria.StoreBuildCriteria;
import org.lorislab.armonitor.web.rs.model.BuildCriteria;

/**
 * The build criteria mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class BuildCriteriaMapper implements MapperService<StoreBuildCriteria, BuildCriteria> {

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildCriteria map(StoreBuildCriteria data, Set<String> profiles) {
        BuildCriteria result = new BuildCriteria();
        result.application = data.getApplication();
        result.params = data.isFetchParameters();
        result.mavenVersion = data.getMavenVersion();
        result.orderByDate = data.getOrderByDate();
        return result;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public StoreBuildCriteria update(StoreBuildCriteria entity, BuildCriteria data, Set<String> profiles) {
        entity.setApplication(data.application);
        entity.setFetchParameters(data.params);
        entity.setMavenVersion(data.mavenVersion);
        entity.setOrderByDate(data.orderByDate);
        return entity;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public StoreBuildCriteria create(BuildCriteria data, Set<String> profiles) {
        StoreBuildCriteria result = new StoreBuildCriteria();
        result = update(result, data, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public BuildCriteria create(Set<String> profiles) {
        StoreBuildCriteria tmp = new StoreBuildCriteria();
        return map(tmp, profiles);
    }

}
