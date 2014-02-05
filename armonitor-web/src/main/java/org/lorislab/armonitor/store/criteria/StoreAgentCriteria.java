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
 * The agent criteria.
 *
 * @author Andrej Petras
 */
public class StoreAgentCriteria extends AbstractSearchCriteria {

    /**
     * The timeout configuration: second.
     */
    private static final long serialVersionUID = 8700313965472589998L;

    /**
     * The system.
     */
    private String system;

    /**
     * The GUID.
     */
    private String guid;

    /**
     * The fetch system flag.
     */
    private boolean fetchSystem;

    /**
     * Gets the fetch system flag.
     *
     * @return the fetch system flag.
     */
    public boolean isFetchSystem() {
        return fetchSystem;
    }

    /**
     * Sets the fetch system flag.
     *
     * @param fetchSystem the fetch system flag.
     */
    public void setFetchSystem(boolean fetchSystem) {
        this.fetchSystem = fetchSystem;
    }

    /**
     * Gets the GUID.
     *
     * @return the GUID.
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Sets the GUID.
     *
     * @param guid the GUID.
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * Gets the system.
     *
     * @return the system.
     */
    public String getSystem() {
        return system;
    }

    /**
     * Sets the system.
     *
     * @param system the system.
     */
    public void setSystem(String system) {
        this.system = system;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        system = null;
        guid = null;
        fetchSystem = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return isEmpty(system, guid);
    }

}
