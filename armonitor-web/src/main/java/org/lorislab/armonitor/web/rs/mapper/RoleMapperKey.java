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
import org.lorislab.armonitor.mapper.MapperKeyService;
import org.lorislab.armonitor.store.model.StoreRole;

/**
 * The role mapper key.
 * 
 * @author Andrej Petras
 */
@MetaInfServices
public class RoleMapperKey implements MapperKeyService<StoreRole> {

    /**
     * {@inheritDoc}
     */    
    @Override
    public String getKey(StoreRole data, Set<String> profiles) {
        return data.getGuid();
    }
    
}
