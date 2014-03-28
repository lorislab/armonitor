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
import org.lorislab.armonitor.activity.wrapper.ActivityChangeLogWrapper;
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreActivityLog;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.web.rs.model.ActivityChangeLog;

/**
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class ActivityChangeLogMapper implements MapperService<ActivityChangeLogWrapper, ActivityChangeLog> {

    @Override
    public ActivityChangeLog map(ActivityChangeLogWrapper data, Set<String> profiles) {
        ActivityChangeLog result = new ActivityChangeLog();
        result.link = data.getLink();

        // add the log
        StoreActivityLog log = data.getLog();
        result.id = log.getRevision();
        result.user = log.getUser();
        result.message = log.getMessage();
        result.date = log.getDate();

        // add the build
        StoreBuild build = log.getBuild();
        if (build != null) {
            result.build = build.getGuid();
            result.version = build.getMavenVersion();
            result.rc = build.getBuild();
        }
        return result;
    }

    @Override
    public ActivityChangeLogWrapper update(ActivityChangeLogWrapper entity, ActivityChangeLog data, Set<String> profiles) {
        return entity;
    }

    @Override
    public ActivityChangeLogWrapper create(ActivityChangeLog data, Set<String> profiles) {
        return null;
    }

    @Override
    public ActivityChangeLog create(Set<String> profiles) {
        return null;
    }

}
