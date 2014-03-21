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
 * The store activity criteria.
 *
 * @author Andrej Petras
 */
public class StoreActivityCriteria extends AbstractSearchCriteria {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -9017281933052385969L;

    /**
     * The activity GUID.
     */
    private String guid;

    /**
     * The build GUID.
     */
    private String build;

    /**
     * The fetch build flag.
     */
    private boolean fetchBuild;
    /**
     * The fetch change flag.
     */
    private boolean fetchChange;

    /**
     * The fetch change log flag.
     */
    private boolean fetchChangeLog;

    /**
     * The fetch change log build flag.
     */
    private boolean fetchChangeLogBuild;

    /**
     * Gets the fetch build flag.
     *
     * @return the fetch build flag.
     */
    public boolean isFetchBuild() {
        return fetchBuild;
    }

    /**
     * Sets the fetch build flag.
     *
     * @param fetchBuild the fetch build flag.
     */
    public void setFetchBuild(boolean fetchBuild) {
        this.fetchBuild = fetchBuild;
    }

    /**
     * Gets the fetch change build log flag.
     *
     * @return the fetch change build log flag.
     */
    public boolean isFetchChangeLogBuild() {
        return fetchChangeLogBuild;
    }

    /**
     * Sets the fetch change build log flag.
     *
     * @param fetchChangeLogBuild the fetch change build log flag.
     */
    public void setFetchChangeBuildLog(boolean fetchChangeLogBuild) {
        this.fetchChangeLogBuild = fetchChangeLogBuild;
    }

    /**
     * Gets the build.
     *
     * @return the build.
     */
    public String getBuild() {
        return build;
    }

    /**
     * Sets the build.
     *
     * @param build the build.
     */
    public void setBuild(String build) {
        this.build = build;
    }

    /**
     * Gets the fetch change flag.
     *
     * @return the fetch change flag.
     */
    public boolean isFetchChange() {
        return fetchChange;
    }

    /**
     * Sets the fetch change flag.
     *
     * @param fetchChange the fetch change flag.
     */
    public void setFetchChange(boolean fetchChange) {
        this.fetchChange = fetchChange;
    }

    /**
     * Gets the fetch change log flag.
     *
     * @return the fetch change log flag.
     */
    public boolean isFetchChangeLog() {
        return fetchChangeLog;
    }

    /**
     * Sets the fetch change log flag.
     *
     * @param fetchChangeLog the fetch change log flag.
     */
    public void setFetchChangeLog(boolean fetchChangeLog) {
        this.fetchChangeLog = fetchChangeLog;
    }

    /**
     * Gets the GUID
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
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        guid = null;
        build = null;
        fetchBuild = false;
        fetchChange = false;
        fetchChangeLog = false;
        fetchChangeLogBuild = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return isEmpty(guid, build);
    }

}
