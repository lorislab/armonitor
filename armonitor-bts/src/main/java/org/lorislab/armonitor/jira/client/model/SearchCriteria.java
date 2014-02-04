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
 *
 * @author Andrej Petras
 */
public class SearchCriteria {
    
    private String jql;
    
    private int startAt = 0;
    
    private int maxResults = 50;
    
    private boolean validateQuery = true;
    
    private List<String> fields;
    
    private String expand;

    /**
     * @return the jql
     */
    public String getJql() {
        return jql;
    }

    /**
     * @param jql the jql to set
     */
    public void setJql(String jql) {
        this.jql = jql;
    }

    /**
     * @return the startAt
     */
    public int getStartAt() {
        return startAt;
    }

    /**
     * @param startAt the startAt to set
     */
    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    /**
     * @return the maxResults
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * @param maxResults the maxResults to set
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * @return the validateQuery
     */
    public boolean isValidateQuery() {
        return validateQuery;
    }

    /**
     * @param validateQuery the validateQuery to set
     */
    public void setValidateQuery(boolean validateQuery) {
        this.validateQuery = validateQuery;
    }

    /**
     * @return the fields
     */
    public List<String> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    /**
     * @return the expand
     */
    public String getExpand() {
        return expand;
    }

    /**
     * @param expand the expand to set
     */
    public void setExpand(String expand) {
        this.expand = expand;
    }
 
    
    
}
