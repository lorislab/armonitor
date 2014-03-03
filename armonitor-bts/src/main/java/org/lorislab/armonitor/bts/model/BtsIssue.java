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

/**
 * The bug tracking issue.
 *
 * @author Andrej Petras
 */
public class BtsIssue implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -628009213493313128L;
    /**
     * The ID.
     */
    private String id;
    /**
     * The summary.
     */
    private String summary;
    /**
     * The assignee.
     */
    private String assignee;
    /**
     * The resolution status.
     */
    private String resolution;

    /**
     * The type.
     */
    private String type;

    /**
     * The parent.
     */
    private String parent;

    /**
     * Gets the ID.
     *
     * @return the ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID.
     *
     * @param id the id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the parent.
     *
     * @return the parent.
     */
    public String getParent() {
        return parent;
    }

    /**
     * Sets the parent.
     *
     * @param parent the parent.
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * Gets the type.
     *
     * @return the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the summary.
     *
     * @return the summary.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the summary.
     *
     * @param summary the summary to set.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Gets the assignee.
     *
     * @return the assignee.
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * Sets the assignee.
     *
     * @param assignee the assignee to set.
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * Gets the resolution status.
     *
     * @return the resolution status.
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * Sets the resolution status.
     *
     * @param resolution the resolution to set.
     */
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

}
