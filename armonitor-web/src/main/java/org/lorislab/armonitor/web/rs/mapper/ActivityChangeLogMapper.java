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
import org.lorislab.armonitor.model.ScmLogBuild;
import org.lorislab.armonitor.scm.model.ScmLog;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.web.rs.model.ActivityChangeLog;

/**
 *
 * @author Andrej Petras
 */
public class ActivityChangeLogMapper implements MapperService<ScmLogBuild, ActivityChangeLog> {

    @Override
    public ActivityChangeLog map(ScmLogBuild data, Set<String> profiles) {
        ActivityChangeLog result = new ActivityChangeLog();
        StoreBuild build = data.getBuild();        
        result.link = data.getLink();
        if (build != null) {
            result.build = build.getGuid();
            result.version = build.getMavenVersion();
            result.rc = build.getBuild();            
        }
        ScmLog log = data.getScmLog();
        if (log != null) {
            result.id = log.getId();
            result.user = log.getUser();
            result.message = log.getMessage();
            result.date = log.getDate();            
        }                
        return result;
    }

    @Override
    public ScmLogBuild update(ScmLogBuild entity, ActivityChangeLog data, Set<String> profiles) {
        return entity;
    }

    @Override
    public ScmLogBuild create(ActivityChangeLog data, Set<String> profiles) {
        return null;
    }

    @Override
    public ActivityChangeLog create(Set<String> profiles) {
        return null;
    }
    
}
