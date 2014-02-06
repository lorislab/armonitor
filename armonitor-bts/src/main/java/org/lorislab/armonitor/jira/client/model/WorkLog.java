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
 * The work log.
 *
 * @author Andrej Petras
 */
public class WorkLog {

    /**
     * The id.
     */
    private String id;
    /**
     * The self.
     */
    private String self;
    /**
     * The author.
     */
    private User author;
    /**
     * The update author.
     */
    private User updateAuthor;
    /**
     * The comment.
     */
    private String comment;
    /**
     * The visibility.
     */
    private Visibility visibility;
    /**
     * The started.
     */
    private String started;
    /**
     * The time spent.
     */
    private String timeSpent;
    /**
     * The time spend seconds.
     */
    private int timeSpentSeconds;

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
     * @param self the self.
     */
    public void setSelf(String self) {
        this.self = self;
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
     * Gets the comment.
     *
     * @return the comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment.
     *
     * @param comment the comment.
     */
    public void setComment(String comment) {
        this.comment = comment;
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

    /**
     * Gets the started.
     *
     * @return the started.
     */
    public String getStarted() {
        return started;
    }

    /**
     * Sets the started.
     *
     * @param started the started.
     */
    public void setStarted(String started) {
        this.started = started;
    }

    /**
     * Gets the time spent.
     *
     * @return the time spent.
     */
    public String getTimeSpent() {
        return timeSpent;
    }

    /**
     * Sets the time spent.
     *
     * @param timeSpent the time spent.
     */
    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    /**
     * Gets the time spent in seconds.
     *
     * @return the time spent in seconds.
     */
    public int getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

    /**
     * Sets the time spent in seconds.
     *
     * @param timeSpentSeconds the time spent in seconds.
     */
    public void setTimeSpentSeconds(int timeSpentSeconds) {
        this.timeSpentSeconds = timeSpentSeconds;
    }

}
