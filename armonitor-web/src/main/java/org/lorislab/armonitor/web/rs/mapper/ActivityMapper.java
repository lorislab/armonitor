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
import org.lorislab.armonitor.activity.wrapper.ActivityWrapper;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.web.rs.model.Activity;
import org.lorislab.armonitor.web.rs.model.ActivityChange;
import org.lorislab.armonitor.web.rs.model.Build;
import org.lorislab.armonitor.util.LinkUtil;

/**
 * The activity mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class ActivityMapper implements MapperService<ActivityWrapper, Activity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Activity map(ActivityWrapper data, Set<String> profiles) {
        Activity result = new Activity();
        result.guid = data.getActivity().getGuid();
        result.types = data.getTypes();
        result.changes = Mapper.map(data.getChanges(), ActivityChange.class, profiles);                
        result.buildChanges = Mapper.map(data.getBuildChanges(), ActivityChange.class, profiles);        
        result.build = Mapper.map(data.getBuild(), Build.class, profiles);
        result.date = data.getActivity().getDate();
        // add the application
        StoreApplication app = data.getApplication();
        if (app != null) {
            result.app = app.getGuid();
            result.appName = app.getName();            
            if (result.build != null) {
                result.build.link = LinkUtil.createLink(app.getRepoLink(), data.getBuild());
            }
        }
        // add the project
        StoreProject p = data.getProject();
        if (p != null) {
            result.project = p.getGuid();
            result.projectName = p.getName();
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityWrapper update(ActivityWrapper entity, Activity data, Set<String> profiles) {
        return entity;
}

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityWrapper create(Activity data, Set<String> profiles) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Activity create(Set<String> profiles) {
        return null;
    }

}
