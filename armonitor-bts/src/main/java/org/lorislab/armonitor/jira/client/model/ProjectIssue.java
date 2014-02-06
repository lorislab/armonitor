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
 * The project issue.
 *
 * @author Andrej Petras
 */
public class ProjectIssue {

    /**
     * The self.
     */
    private String self;
    /**
     * The id.
     */
    private String id;
    /**
     * The name.
     */
    private String name;
    /**
     * The subtask flag.
     */
    private boolean subtask;
    /**
     * The list of statuses.
     */
    private List<Status> statuses;

    /**
     * Gets the self.
     *
     * @return the self.
     */
    public String getSelf() {
        return self;
    }

    /**
     * Sets the self.
     *
     * @param self the self to set.
     */
    public void setSelf(String self) {
        this.self = self;
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
     * @param id the id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the subtask flag.
     *
     * @return the subtask flag.
     */
    public boolean isSubtask() {
        return subtask;
    }

    /**
     * Sets the subtask flag.
     *
     * @param subtask the subtask to set.
     */
    public void setSubtask(boolean subtask) {
        this.subtask = subtask;
    }

    /**
     * Gets the list of statuses.
     *
     * @return the list of statuses.
     */
    public List<Status> getStatuses() {
        return statuses;
    }

    /**
     * Sets the list of statuses.
     *
     * @param statuses the list of statuses.
     */
    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

}
