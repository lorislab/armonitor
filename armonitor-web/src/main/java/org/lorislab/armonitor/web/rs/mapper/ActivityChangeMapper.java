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
import org.lorislab.armonitor.bts.model.BtsIssue;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.model.Change;
import org.lorislab.armonitor.web.rs.model.ActivityChange;
import org.lorislab.armonitor.web.rs.model.ActivityChangeLog;

/**
 *
 * @author Andrej Petras
 */
public class ActivityChangeMapper implements MapperService<Change, ActivityChange> {

    @Override
    public ActivityChange map(Change data, Set<String> profiles) {
        ActivityChange result = new ActivityChange();
        result.id = data.getId();
        result.error = data.isError();
        result.link = data.getLink();
        result.not = data.isNotIssue();
        BtsIssue i = data.getIssue();
        if (i != null) {
            result.assignee = i.getAssignee();
            result.resolution = i.getResolution();
            result.summary = i.getSummary();
        }
        result.changes = Mapper.map(data.getChanges(), ActivityChangeLog.class, profiles);
        return result;
    }

    @Override
    public Change update(Change entity, ActivityChange data, Set<String> profiles) {
        return entity;
    }

    @Override
    public Change create(ActivityChange data, Set<String> profiles) {
        return null;
    }

    @Override
    public ActivityChange create(Set<String> profiles) {
        return null;
    }
    
}
