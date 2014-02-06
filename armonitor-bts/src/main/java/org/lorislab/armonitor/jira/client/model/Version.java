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
 * The JIRA version.
 *
 * @author Andrej Petras
 */
public class Version {

    /**
     * The self.
     */
    private String self;
    /**
     * The id.
     */
    private String id;
    /**
     * The description.
     */
    private String description;
    /**
     * The name.
     */
    private String name;
    /**
     * The archived flag.
     */
    private boolean archived;
    /**
     * The released flag.
     */
    private boolean released;
    /**
     * The project ID.
     */
    private String projectId;
    /**
     * The overdue flag.
     */
    private boolean overdue;
    /**
     * The release date.
     */
    private String releaseDate;
    /**
     * The user release date.
     */
    private String userReleaseDate;
    /**
     * The start date.
     */
    private String startDate;
    /**
     * The user start date.
     */
    private String userStartDate;

    /**
     * Gets the self link.
     *
     * @return the self link.
     */
    public String getSelf() {
        return self;
    }

    /**
     * Sets the self link.
     *
     * @param self the self link.
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
     * @param id the id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the description.
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the archived flag.
     *
     * @return the archived flag.
     */
    public boolean isArchived() {
        return archived;
    }

    /**
     * Sets the archived flag.
     *
     * @param archived the archived flag.
     */
    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    /**
     * Gets the released flag.
     *
     * @return the archived flag.
     */
    public boolean isReleased() {
        return released;
    }

    /**
     * Sets the archived flag.
     *
     * @param released the archived flag.
     */
    public void setReleased(boolean released) {
        this.released = released;
    }

    /**
     * Gets the project id.
     *
     * @return the project id.
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     *
     * @param projectId the project id.
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the overdue flag.
     *
     * @return the overdue flag.
     */
    public boolean isOverdue() {
        return overdue;
    }

    /**
     * Sets the overdue flag.
     *
     * @param overdue the overdue flag.
     */
    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    /**
     * Gets the release date.
     *
     * @return the release date.
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date.
     *
     * @param releaseDate the release date.
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets the user release date.
     *
     * @return the user release date.
     */
    public String getUserReleaseDate() {
        return userReleaseDate;
    }

    /**
     * Sets the user release date.
     *
     * @param userReleaseDate the user release date.
     */
    public void setUserReleaseDate(String userReleaseDate) {
        this.userReleaseDate = userReleaseDate;
    }

    /**
     * Gets the start date.
     *
     * @return the start date.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the start date.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the user start date.
     *
     * @return the user start date.
     */
    public String getUserStartDate() {
        return userStartDate;
    }

    /**
     * Sets the user start date.
     *
     * @param userStartDate the user start date.
     */
    public void setUserStartDate(String userStartDate) {
        this.userStartDate = userStartDate;
    }

}
