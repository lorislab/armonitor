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
 * The system build criteria.
 *
 * @author Andrej Petras
 */
public class StoreSystemBuildCriteria extends AbstractSearchCriteria {

    /**
     * The timeout configuration: second.
     */
    private static final long serialVersionUID = 1180646542774715225L;

    private String guid;

    private String system;

    private String build;

    private boolean maxDate;
    
    private Set<String> systems;
    
    private boolean fetchSystem;

    private boolean fetchBuild;

    private boolean fetchBuildParam;

    public boolean isFetchBuildParam() {
        return fetchBuildParam;
    }

    public void setFetchBuildParam(boolean fetchBuildParam) {
        this.fetchBuildParam = fetchBuildParam;
    }
        
    public boolean isMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Boolean maxDate) {
        this.maxDate = maxDate;
    }

    public Set<String> getSystems() {
        return systems;
    }

    public void setSystems(Set<String> systems) {
        this.systems = systems;
    }
        
    public boolean isFetchBuild() {
        return fetchBuild;
    }

    public void setFetchBuild(boolean fetchBuild) {
        this.fetchBuild = fetchBuild;
    }

    public boolean isFetchSystem() {
        return fetchSystem;
    }

    public void setFetchSystem(boolean fetchSystem) {
        this.fetchSystem = fetchSystem;
    }

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        system = null;
        build = null;
        maxDate = false;
        systems = null;
        fetchSystem = false;
        fetchBuild = false;
        fetchBuildParam = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        if (maxDate) {
            return false;
        }
        return isEmpty(system, build, systems);
    }

}
