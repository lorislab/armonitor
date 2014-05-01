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
import org.lorislab.armonitor.web.rs.model.DashboardApplicationSystem;
import org.lorislab.armonitor.web.rs.model.DeploySystem;

/**
 * The deploy system from dashboard mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class DeploySystemDashboardMapper implements MapperService<DashboardApplicationSystem, DeploySystem> {

    /**
     * {@inheritDoc}
     */       
    @Override
    public DeploySystem map(DashboardApplicationSystem data, Set<String> profiles) {
        DeploySystem system = new DeploySystem();
        system.guid = data.guid;
        system.clazz = data.clazz;
        system.domain = data.domain;
        system.name = data.name;
        system.application = data.name;
        system.project = data.name;
        return system;
    }

    /**
     * {@inheritDoc}
     */       
    @Override
    public DashboardApplicationSystem update(DashboardApplicationSystem entity, DeploySystem data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */       
    @Override
    public DashboardApplicationSystem create(DeploySystem data, Set<String> profiles) {
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
