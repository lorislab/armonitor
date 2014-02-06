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
 * The comment.
 *
 * @author Andrej Petras
 */
public class Comment {

    /**
     * The self link.
     */
    private String self;
    /**
     * The id.
     */
    private String id;
    /**
     * The author.
     */
    private User author;
    /**
     * The body.
     */
    private String body;
    /**
     * The update author.
     */
    private User updateAuthor;
    /**
     * The created.
     */
    private String created;
    /**
     * The updated.
     */
    private String updated;
    /**
     * The visibility.
     */
    private Visibility visibility;

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
     * @param id the id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the author.
     *
     * @return the author.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets the author.
     *
     * @param author the author.
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Gets the body.
     *
     * @return the body.
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body.
     *
     * @param body the body.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets the update author.
     *
     * @return the update author.
     */
    public User getUpdateAuthor() {
        return updateAuthor;
    }

    /**
     * Sets the update author.
     *
     * @param updateAuthor the update author.
     */
    public void setUpdateAuthor(User updateAuthor) {
        this.updateAuthor = updateAuthor;
    }

    /**
     * Gets the created.
     *
     * @return the created.
     */
    public String getCreated() {
        return created;
    }

    /**
     * Sets the created.
     *
     * @param created the created to set.
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * Gets the updated.
     *
     * @return the updated.
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * Sets the updated.
     *
     * @param updated the updated.
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     * Gets the visibility.
     *
     * @return the visibility.
     */
    public Visibility getVisibility() {
        return visibility;
    }

    /**
     * Sets the visibility.
     *
     * @param visibility the visibility.
     */
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

}
