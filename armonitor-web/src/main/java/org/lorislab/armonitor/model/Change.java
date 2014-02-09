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
package org.lorislab.armonitor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.lorislab.armonitor.bts.model.BtsIssue;

/**
 * The report change.
 *
 * @author Andrej Petras
 */
public class Change implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -1744381414449867439L;
    /**
     * The id.
     */
    private String id;
    /**
     * The issue.
     */
    private BtsIssue issue;

    /**
     * The error flag.
     */
    private boolean error;

    /**
     * The list of changes.
     */
    private final List<ScmLogBuild> changes = new ArrayList<>();

    /**
     * Gets the error flag.
     *
     * @return the error flag.
     */
    public boolean isError() {
        return error;
    }

    /**
     * Sets the error flag.
     *
     * @param error the error flag.
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Gets the list of changes.
     *
     * @return the list of changes.
     */
    public List<ScmLogBuild> getChanges() {
        return changes;
    }

    /**
     * Gets the issue.
     *
     * @return the issue.
     */
    public BtsIssue getIssue() {
        return issue;
    }

    /**
     * Sets the issue.
     *
     * @param issue the issue.
     */
    public void setIssue(BtsIssue issue) {
        this.issue = issue;
    }

    /**
     * Gets the id.
     *
     * @return the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id.
     */
    public void setId(String id) {
        this.id = id;
    }

}
