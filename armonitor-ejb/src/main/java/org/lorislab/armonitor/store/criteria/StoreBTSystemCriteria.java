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

import org.lorislab.jel.base.criteria.AbstractSearchCriteria;

/**
 * The bug tracking system criteria.
 * 
 * @author Andrej Petras
 */
public class StoreBTSystemCriteria extends AbstractSearchCriteria {
    
    private static final long serialVersionUID = -3731346552383959214L;

    private String guid;
    
    private String project;
    
    private boolean fetchProject;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public boolean isFetchProject() {
        return fetchProject;
    }

    public void setFetchProject(boolean fetchProject) {
        this.fetchProject = fetchProject;
    }
        
    @Override
    public void reset() {
        guid = null;
        project = null;
        fetchProject = false;        
    }

    @Override
    public boolean isEmpty() {
        return isEmpty(guid, project);
    }
    
}
