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
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.web.rs.model.Build;
import org.lorislab.armonitor.web.rs.model.DeploySystem;
import org.lorislab.armonitor.web.rs.model.DeploySystemBuilds;

/**
 * The deploy system builds mapper.
 * 
 * @author Andrej Petras
 */
@MetaInfServices
public class DeploySystemBuildsMapper implements MapperService<StoreApplication, DeploySystemBuilds> {

    /**
     * {@inheritDoc}
     */    
    @Override
    public DeploySystemBuilds map(StoreApplication data, Set<String> profiles) {
        DeploySystemBuilds result = new DeploySystemBuilds();
        result.system = Mapper.map(data, DeploySystem.class, profiles);
        result.guid = result.system.guid;
        result.builds = Mapper.map(data.getBuilds(), Build.class, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public StoreApplication update(StoreApplication entity, DeploySystemBuilds data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public StoreApplication create(DeploySystemBuilds data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public DeploySystemBuilds create(Set<String> profiles) {
        return null;
    }
    
}
