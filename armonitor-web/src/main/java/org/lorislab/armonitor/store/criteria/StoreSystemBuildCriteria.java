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
    /**
     * The system build GUID.
     */
    private String guid;
    /**
     * The system.
     */
    private String system;

    /**
     * The build.
     */
    private String build;
    /**
     * The maximal date flag.
     */
    private boolean maxDate;
    /**
     * The set of systems.
     */
    private Set<String> systems;
    /**
     * The fetch system flag.
     */
    private boolean fetchSystem;
    /**
     * The fetch build flag.
     */
    private boolean fetchBuild;
    /**
     * The fetch build parameters flag.
     */
    private boolean fetchBuildParam;
    /**
     * The fetch system application flag.
     */
    private boolean fetchSystemApplication;

    public boolean isFetchSystemApplication() {
        return fetchSystemApplication;
    }

    public void setFetchSystemApplication(boolean fetchSystemApplication) {
        this.fetchSystemApplication = fetchSystemApplication;
    }

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
        fetchSystemApplication = false;
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
