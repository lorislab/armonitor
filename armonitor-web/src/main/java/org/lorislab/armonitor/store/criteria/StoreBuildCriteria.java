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

import java.util.Date;
import org.lorislab.jel.base.criteria.AbstractSearchCriteria;

/**
 * The build criteria.
 *
 * @author Andrej Petras
 */
public class StoreBuildCriteria extends AbstractSearchCriteria {

    /**
     * The timeout configuration: second.
     */
    private static final long serialVersionUID = 8885713194174673375L;
    /**
     * The GUID.
     */
    private String guid;
    /**
     * The application.
     */
    private String application;
    /**
     * The agent.
     */
    private String agent;
    /**
     * The date.
     */
    private Date date;
    /**
     * The fetch parameters flag.
     */
    private boolean fetchParameters;
    /**
     * The fetch applications flag.
     */
    private boolean fetchApplication;

    /**
     * The fetch application project flag.
     */
    private boolean fetchApplicationProject;

    /**
     * The MAVEN version.
     */
    private String mavenVersion;
    /**
     * The order by date flag.
     */
    private Boolean orderByDate;

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

    /**
     * Gets the order by date flag.
     *
     * @return the order by date flag.
     */
    public Boolean getOrderByDate() {
        return orderByDate;
    }

    /**
     * Sets the order by date flag.
     *
     * @param orderByDate the order by date flag.
     */
    public void setOrderByDate(Boolean orderByDate) {
        this.orderByDate = orderByDate;
    }

    /**
     * Gets the MAVEN version.
     *
     * @return the MAVEN version.
     */
    public String getMavenVersion() {
        return mavenVersion;
    }

    /**
     * Sets the MAVEN version.
     *
     * @param mavenVersion the MAVEN version.
     */
    public void setMavenVersion(String mavenVersion) {
        this.mavenVersion = mavenVersion;
    }

    /**
     * Gets the fetch applications flag.
     *
     * @return the fetch applications flag.
     */
    public boolean isFetchApplication() {
        return fetchApplication;
    }

    /**
     * Sets the fetch applications flag.
     *
     * @param fetchApplication the fetch applications flag.
     */
    public void setFetchApplication(boolean fetchApplication) {
        this.fetchApplication = fetchApplication;
    }

    /**
     * Gets the fetch parameters flag.
     *
     * @return the fetch parameters flag.
     */
    public boolean isFetchParameters() {
        return fetchParameters;
    }

    /**
     * Sets the fetch parameters flag.
     *
     * @param fetchParameters the fetch parameters flag.
     */
    public void setFetchParameters(boolean fetchParameters) {
        this.fetchParameters = fetchParameters;
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
     * Gets the agent.
     *
     * @return the agent.
     */
    public String getAgent() {
        return agent;
    }

    /**
     * Sets the agent.
     *
     * @param agent the agent.
     */
    public void setAgent(String agent) {
        this.agent = agent;
    }

    /**
     * Gets the date.
     *
     * @return the date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the date.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the application.
     *
     * @return the application.
     */
    public String getApplication() {
        return application;
    }

    /**
     * Sets the application.
     *
     * @param application the application.
     */
    public void setApplication(String application) {
        this.application = application;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        application = null;
        date = null;
        agent = null;
        mavenVersion = null;
        fetchParameters = false;
        fetchApplication = false;
        orderByDate = null;
        fetchApplicationProject = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return isEmpty(application, date, agent, mavenVersion);
    }

}
