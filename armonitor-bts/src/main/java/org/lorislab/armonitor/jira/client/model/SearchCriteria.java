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
 * The search criteria.
 *
 * @author Andrej Petras
 */
public class SearchCriteria {

    /**
     * The JIRA a JQL query string.
     */
    private String jql;
    /**
     * The index of the first issue to return (0-based)
     */
    private int startAt = 0;
    /**
     * The maximum number of issues to return (defaults to 50). The maximum
     * allowable value is dictated by the JIRA property
     * 'jira.search.views.default.max'. If you specify a value that is higher
     * than this number, your search results will be truncated.
     */
    private int maxResults = 200;
    /**
     * Whether to validate the JQL query
     */
    private boolean validateQuery = true;
    /**
     * The list of fields to return for each issue. By default, all navigable
     * fields are returned.
     */
    private List<String> fields;
    /**
     * A comma-separated list of the parameters to expand.
     */
    private String expand;

    /**
     * Gets the JQL.
     *
     * @return the JQL.
     */
    public String getJql() {
        return jql;
    }

    /**
     * Sets the JQL.
     *
     * @param jql the JQL.
     */
    public void setJql(String jql) {
        this.jql = jql;
    }

    /**
     * Gets the start at index.
     *
     * @return the start at index.
     */
    public int getStartAt() {
        return startAt;
    }

    /**
     * Sets the start at index.
     *
     * @param startAt the start at index.
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
     * Gets the maximum results.
     *
     * @param maxResults the maximum results.
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * Gets the validate query flag.
     *
     * @return the validate query flag.
     */
    public boolean isValidateQuery() {
        return validateQuery;
    }

    /**
     * Sets the validate query flag.
     *
     * @param validateQuery the validate query flag.
     */
    public void setValidateQuery(boolean validateQuery) {
        this.validateQuery = validateQuery;
    }

    /**
     * Gets the list of fields.
     *
     * @return the list of fields.
     */
    public List<String> getFields() {
        return fields;
    }

    /**
     * Sets the list of fields.
     *
     * @param fields the list of fields.
     */
    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    /**
     * Gets the expand parameters.
     *
     * @return the expand parameters.
     */
    public String getExpand() {
        return expand;
    }

    /**
     * Sets the expand parameters.
     *
     * @param expand the expand parameters.
     */
    public void setExpand(String expand) {
        this.expand = expand;
    }

}
