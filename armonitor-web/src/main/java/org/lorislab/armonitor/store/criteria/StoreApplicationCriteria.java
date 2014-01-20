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
package org.lorislab.armonitor.store.criteria;

import java.util.Set;
import org.lorislab.jel.base.criteria.AbstractSearchCriteria;

/**
 *
 * @author Andrej Petras
 */
public class StoreApplicationCriteria extends AbstractSearchCriteria {
    
    private static final long serialVersionUID = -506625437892985522L;

    private String guid;
    
    private Boolean enabled;

    private Set<String> projects;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
    
    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<String> getProjects() {
        return projects;
    }

    public void setProjects(Set<String> projects) {
        this.projects = projects;
    }
    
    @Override
    public void reset() {
        projects = null;
        enabled = null;
        guid = null;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty(enabled, projects, guid);
    }

}
