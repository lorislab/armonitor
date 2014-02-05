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
 * The user criteria.
 *
 * @author Andrej Petras
 */
public class StoreUserCriteria extends AbstractSearchCriteria {

    /**
     * The timeout configuration: second.
     */
    private static final long serialVersionUID = -3133444873708479295L;

    private String name;

    private String guid;

    private boolean fetchRoles;

    private String system;

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        name = null;
        guid = null;
        system = null;
        fetchRoles = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return isEmpty(name, guid, system);
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFetchRoles() {
        return fetchRoles;
    }

    public void setFetchRoles(boolean fetchRoles) {
        this.fetchRoles = fetchRoles;
    }

}
