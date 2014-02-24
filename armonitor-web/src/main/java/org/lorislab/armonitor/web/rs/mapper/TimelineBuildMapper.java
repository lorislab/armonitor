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
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.web.rs.model.TimelineBuild;

/**
 * The time line build mapper.
 * 
 * @author Andrej Petras
 */
public class TimelineBuildMapper implements MapperService<StoreBuild, TimelineBuild> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TimelineBuild map(StoreBuild data, String profile) {
        TimelineBuild result = new TimelineBuild();
        result.guid = data.getGuid();
        result.content = data.getBuild();
        result.start = data.getDate();
        result.group = data.getMavenVersion();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreBuild update(StoreBuild entity, TimelineBuild data, String profile) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreBuild create(TimelineBuild data, String profile) {
        StoreBuild result = new StoreBuild();
        result.setGuid(data.guid);
        result = update(result, data, profile);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TimelineBuild create(String profile) {
        StoreBuild tmp = new StoreBuild();
        return map(tmp, profile);
    }

}
