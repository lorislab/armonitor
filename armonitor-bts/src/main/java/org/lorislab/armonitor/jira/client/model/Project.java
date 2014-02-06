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

import java.util.Map;

/**
 * The project.
 *
 * @author Andrej Petras
 */
public class Project {

    /**
     * The self.
     */
    private String self;
    /**
     * The id.
     */
    private String id;
    /**
     * The key.
     */
    private String key;
    /**
     * The name.
     */
    private String name;
    /**
     * The project category.
     */
    private ProjectCategory projectCategory;
    /**
     * The map of avatar URL's.
     */
    private Map<String, String> avatarUrls;

    /**
     * Gets the project category.
     *
     * @return the project category.
     */
    public ProjectCategory getProjectCategory() {
        return projectCategory;
    }

    /**
     * Sets the project category.
     *
     * @param projectCategory the project category.
     */
    public void setProjectCategory(ProjectCategory projectCategory) {
        this.projectCategory = projectCategory;
    }

    /**
     * Gets the avatar URL's.
     *
     * @return the avatar URL's.
     */
    public Map<String, String> getAvatarUrls() {
        return avatarUrls;
    }

    /**
     * Sets the avatar URL's.
     *
     * @param avatarUrls the avatar URL's.
     */
    public void setAvatarUrls(Map<String, String> avatarUrls) {
        this.avatarUrls = avatarUrls;
    }

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
     * Gets the id.
     *
     * @param id the id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the key.
     *
     * @return the key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key the key to set.
     */
    public void setKey(String key) {
        this.key = key;
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

}
