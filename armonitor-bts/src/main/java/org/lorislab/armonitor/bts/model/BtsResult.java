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
package org.lorislab.armonitor.bts.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The bug tracking search result.
 *
 * @author Andrej Petras
 */
public class BtsResult implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The list of issues.
     */
    private final List<BtsIssue> issues = new ArrayList<>();
    /**
     * The cache.
     */
    private final Map<String, BtsIssue> cache = new HashMap<>();

    /**
     * Adds the issue to the result.
     *
     * @param issue the issue.
     */
    public void addIssue(BtsIssue issue) {
        if (!cache.containsKey(issue.getId())) {
            issues.add(issue);
            cache.put(issue.getId(), issue);
        }
    }

    /**
     * Gets the list of issues.
     *
     * @return the list of issues.
     */
    public List<BtsIssue> getIssues() {
        return issues;
    }

    /**
     * Gets the issue by id.
     *
     * @param id the issue id.
     * @return the issue.
     */
    public BtsIssue getIssue(String id) {
        return cache.get(id);
    }

    /**
     * Gets the size of the issues list.
     *
     * @return the size of the issues list.
     */
    public int size() {
        return issues.size();
    }

    /**
     * Returns <code>true</code> if the list of issues is empty.
     *
     * @return <code>true</code> if the list of issues is empty.
     */
    public boolean isEmpty() {
        return issues.isEmpty();
    }
}
