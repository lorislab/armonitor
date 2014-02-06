/*
 * Copyright 2013 lorislab.org.
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
package org.lorislab.armonitor.jira.client.model;

import java.util.List;

/**
 * The search result.
 *
 * @author Andrej Petras
 */
public class SearchResult {

    /**
     * The expand.
     */
    private String expand;
    /**
     * The start at.
     */
    private int startAt;
    /**
     * The maximum results.
     */
    private int maxResults;
    /**
     * The total.
     */
    private int total;
    /**
     * The list of issues.
     */
    private List<Issue> issues;

    /**
     * Gets the expand.
     *
     * @return the expand.
     */
    public String getExpand() {
        return expand;
    }

    /**
     * Sets the expand.
     *
     * @param expand the expand to set.
     */
    public void setExpand(String expand) {
        this.expand = expand;
    }

    /**
     * Gets the start at.
     *
     * @return the start at.
     */
    public int getStartAt() {
        return startAt;
    }

    /**
     * Sets the start at.
     *
     * @param startAt the start at.
     */
    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    /**
     * Gets the maximum results.
     *
     * @return the maximum results.
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * Sets the maximum results.
     *
     * @param maxResults the maximum results.
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * Gets the total.
     *
     * @return the total.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets the total.
     *
     * @param total the total to set.
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Gets the list of issues.
     *
     * @return the list of issues.
     */
    public List<Issue> getIssues() {
        return issues;
    }

    /**
     * Sets the list of issues.
     *
     * @param issues the list of issues.
     */
    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

}
