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
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.model.ChangeReport;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.web.rs.model.Activity;
import org.lorislab.armonitor.web.rs.model.ActivityChange;
import org.lorislab.armonitor.web.rs.model.Build;
import org.lorislab.armonitor.web.rs.util.LinkUtil;

/**
 * The activity mapper.
 *
 * @author Andrej Petras
 */
public class ActivityMapper implements MapperService<ChangeReport, Activity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Activity map(ChangeReport data, Set<String> profiles) {
        Activity result = new Activity();
        result.guid = data.getGuid();
        result.changes = Mapper.map(data.getChanges(), ActivityChange.class, profiles);
        result.buildChanges = Mapper.map(data.getBuildChanges(), ActivityChange.class, profiles);        
        result.build = Mapper.map(data.getBuild(), Build.class, profiles);
        
        StoreApplication app = data.getApplication();
        if (app != null) {
            result.app = app.getGuid();
            result.appName = app.getName();            
            if (result.build != null) {
                result.build.link = LinkUtil.createLink(app.getRepoLink(), data.getBuild());
            }
        }
        
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
    public ChangeReport update(ChangeReport entity, Activity data, Set<String> profiles) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChangeReport create(Activity data, Set<String> profiles) {
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
