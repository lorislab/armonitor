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
import org.lorislab.armonitor.activity.wrapper.ActivityChangeWrapper;
import org.lorislab.armonitor.bts.model.BtsIssue;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreActivityChange;
import org.lorislab.armonitor.store.model.enums.ActivityChangeError;
import org.lorislab.armonitor.web.rs.model.ActivityChange;
import org.lorislab.armonitor.web.rs.model.ActivityChangeLog;

/**
 * The activity change mapper.
 * 
 * @author Andrej Petras
 */
public class ActivityChangeMapper implements MapperService<ActivityChangeWrapper, ActivityChange> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityChange map(ActivityChangeWrapper data, Set<String> profiles) {
        ActivityChange result = new ActivityChange();
        result.id = data.getKey();
        result.link = data.getLink();
        result.error = data.isError();
        if (data.isError()) {
            result.not = (ActivityChangeError.WRONG_KEY == data.getChange().getError());
        }
        
        StoreActivityChange change = data.getChange();
        result.assignee = change.getUser();
        result.resolution = change.getStatus();
        result.summary = change.getDescription();
        result.type = change.getType();
        result.parent = change.getParent();
        result.changes = Mapper.map(data.getLogs(), ActivityChangeLog.class, profiles);
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityChangeWrapper update(ActivityChangeWrapper entity, ActivityChange data, Set<String> profiles) {
        return entity;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityChangeWrapper create(ActivityChange data, Set<String> profiles) {
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityChange create(Set<String> profiles) {
        return null;
    }
    
}
