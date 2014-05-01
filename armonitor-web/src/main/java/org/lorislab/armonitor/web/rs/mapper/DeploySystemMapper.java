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
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.DeploySystem;

/**
 * The deploy system mapper.
 * 
 * @author Andrej Petras
 */
@MetaInfServices
public class DeploySystemMapper implements MapperService<StoreApplication, DeploySystem> {

    /**
     * {@inheritDoc}
     */       
    @Override
    public DeploySystem map(StoreApplication data, Set<String> profiles) {
        DeploySystem result = new DeploySystem();
        result.application = data.getName();
        result.project = data.getProject().getName();        
        StoreSystem sys = data.getSystems().iterator().next();
        result.guid = sys.getGuid();
        result.domain = sys.getDomain();
        result.clazz = sys.getClassification();
        result.name = sys.getName();
        return result;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public StoreApplication update(StoreApplication entity, DeploySystem data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public StoreApplication create(DeploySystem data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public DeploySystem create(Set<String> profiles) {
        return null;
    }
    
}
