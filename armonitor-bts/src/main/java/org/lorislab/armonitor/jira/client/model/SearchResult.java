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
public class SearchResult {
    
    private String expand;
    
    private int startAt;
    
    private int maxResults;
    
    private int total;
    
    private List<Issue> issues;

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
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the issues
     */
    public List<Issue> getIssues() {
        return issues;
    }

    /**
     * @param issues the issues to set
     */
    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
   
    
    
}
