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
import org.lorislab.armonitor.web.rs.model.Build;
import org.lorislab.armonitor.web.rs.model.DeploySystem;
import org.lorislab.armonitor.web.rs.model.DeploySystemBuild;
import org.lorislab.armonitor.web.rs.wrapper.DeploySystemBuildWrapper;

/**
 * The deploy system build mapper.
 * 
 * @author Andrej Petras
 */
@MetaInfServices
public class DeploySystemBuildMapper implements MapperService<DeploySystemBuildWrapper, DeploySystemBuild> {

    /**
     * {@inheritDoc}
     */    
    @Override
    public DeploySystemBuild map(DeploySystemBuildWrapper data, Set<String> profiles) {
        DeploySystemBuild result = new DeploySystemBuild();
        // map system
        result.system = Mapper.map(data.getApplication(), DeploySystem.class, profiles);
        result.systemGuid = result.system.guid;
        // map build
        result.build = Mapper.map(data.getApplication().getBuilds().iterator().next(), Build.class, profiles);
        result.buildGuid = result.build.guid;
        // map current system build
        result.systemBuild = Mapper.map(data.getSystemBuild().getBuild(), Build.class, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public DeploySystemBuildWrapper update(DeploySystemBuildWrapper entity, DeploySystemBuild data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public DeploySystemBuildWrapper create(DeploySystemBuild data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public DeploySystemBuild create(Set<String> profiles) {
        return null;
    }
    
}
