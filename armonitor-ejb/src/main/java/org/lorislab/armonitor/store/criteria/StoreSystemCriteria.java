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
 * The system criteria.
 *
 * @author Andrej Petras
 */
public class StoreSystemCriteria extends AbstractSearchCriteria {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -3270039293034055465L;

    private String guid;

    private Boolean enabled;

    private Set<String> applications;

    private Boolean timer;

    private String key;

    private boolean fetchAgent;

    private boolean fetchApplication;

    private boolean fetchRoles;

    /**
     * The fetch application project flag.
     */
    private boolean fetchApplicationProject;

    /**
     * Gets the fetch application project flag.
     *
     * @return the fetch application project flag.
     */
    public boolean isFetchApplicationProject() {
        return fetchApplicationProject;
    }

    /**
     * Sets the fetch application project flag.
     *
     * @param fetchApplicationProject the fetch application project flag.
     */
    public void setFetchApplicationProject(boolean fetchApplicationProject) {
        this.fetchApplicationProject = fetchApplicationProject;
    }

    public boolean isFetchRoles() {
        return fetchRoles;
    }

    public void setFetchRoles(boolean fetchRoles) {
        this.fetchRoles = fetchRoles;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isFetchApplication() {
        return fetchApplication;
    }

    public void setFetchApplication(boolean fetchApplication) {
        this.fetchApplication = fetchApplication;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean isFetchAgent() {
        return fetchAgent;
    }

    public void setFetchAgent(boolean fetchAgent) {
        this.fetchAgent = fetchAgent;
    }

    public Boolean isTimer() {
        return timer;
    }

    public void setTimer(Boolean timer) {
        this.timer = timer;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<String> getApplications() {
        return applications;
    }

    public void setApplications(Set<String> applications) {
        this.applications = applications;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        applications = null;
        enabled = null;
        timer = null;
        fetchAgent = false;
        fetchApplication = false;
        key = null;
        fetchRoles = false;
        fetchApplicationProject = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return isEmpty(enabled, timer, applications, key);
    }
}
