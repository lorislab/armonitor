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
import org.lorislab.armonitor.store.model.StoreSCMSystem;

/**
 * The source code system name mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class SCMSystemNameMapper implements MapperService<StoreSCMSystem, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String map(StoreSCMSystem data, Set<String> profiles) {
        return data.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSCMSystem update(StoreSCMSystem entity, String data, Set<String> profiles) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreSCMSystem create(String data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String create(Set<String> profiles) {
        return null;
    }

}
