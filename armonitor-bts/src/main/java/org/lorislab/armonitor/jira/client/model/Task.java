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

/**
 * The task.
 *
 * @author Andrej Petras
 */
public class Task {

    /**
     * The id.
     */
    private String id;
    /**
     * The type.
     */
    private Type type;
    /**
     * The link outward issue.
     */
    private LinkIssue outwardIssue;
    /**
     * The link inward issue.
     */
    private LinkIssue inwardIssue;

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

    /**
     * Gets the type.
     *
     * @return the type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the type.
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Gets the outward issue.
     *
     * @return the outward issue.
     */
    public LinkIssue getOutwardIssue() {
        return outwardIssue;
    }

    /**
     * Sets the outward issue.
     *
     * @param outwardIssue the outward issue.
     */
    public void setOutwardIssue(LinkIssue outwardIssue) {
        this.outwardIssue = outwardIssue;
    }

    /**
     * Gets the inward issue.
     *
     * @return the inward issue.
     */
    public LinkIssue getInwardIssue() {
        return inwardIssue;
    }

    /**
     * Sets the inward issue.
     *
     * @param inwardIssue the inward issue.
     */
    public void setInwardIssue(LinkIssue inwardIssue) {
        this.inwardIssue = inwardIssue;
    }

}
