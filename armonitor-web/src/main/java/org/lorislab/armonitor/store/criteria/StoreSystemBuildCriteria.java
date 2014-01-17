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
 *
 * @author Andrej Petras
 */
public class StoreSystemBuildCriteria extends AbstractSearchCriteria {
    
    private static final long serialVersionUID = 1180646542774715225L;

    private String guid;
    
    private String system;
    
    private String build;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
       
    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
        
    @Override
    public void reset() {
        system = null;
        build = null;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty(system, build);
    }
    
}
