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
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.web.rs.model.SystemBuild;

/**
 * The system build mapper.
 * 
 * @author Andrej Petras
 */
public class SystemBuildMapper implements MapperService<StoreSystemBuild, SystemBuild> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SystemBuild map(StoreSystemBuild data, Set<String> profiles) {
        SystemBuild result = new SystemBuild();
        result.guid = data.getGuid();
        result.date = data.getDate();      
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystemBuild update(StoreSystemBuild entity, SystemBuild data, Set<String> profiles) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSystemBuild create(SystemBuild data, Set<String> profiles) {
        StoreSystemBuild result = new StoreSystemBuild();
        result.setGuid(data.guid);
        result = update(result, data, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SystemBuild create(Set<String> profiles) {
        StoreSystemBuild tmp = new StoreSystemBuild();
        return map(tmp, profiles);
    }
}
