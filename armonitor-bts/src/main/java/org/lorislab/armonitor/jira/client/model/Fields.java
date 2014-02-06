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
import javax.xml.bind.annotation.XmlElement;

/**
 * The fields.
 *
 * @author Andrej Petras
 */
public class Fields {

    /**
     * The priority.
     */
    private Priority priority;
    /**
     * The time spent.
     */
    private String timespent;
    /**
     * The issue type.
     */
    private IssueType issuetype;
    /**
     * The reporter.
     */
    private User reporter;
    /**
     * The list of task.
     */
    @XmlElement(name = "sub-tasks")
    private List<Task> subTasks;
    /**
     * The time tracking.
     */
    private TimeTracking timetracking;
    /**
     * The project.
     */
    private Project project;
    /**
     * The updated.
     */
    private String updated;
    /**
     * The created.
     */
    private String created;
    /**
     * The description.
     */
    private String description;
    /**
     * The issue links.
     */
    private List<Task> issuelinks;
    /**
     * The list of attachments.
     */
    private List<Attachment> attachment;
    /**
     * The watcher.
     */
    private Watcher watcher;
    /**
     * The list of comments.
     */
    private List<Comment> comment;
    /**
     * The list of work log.
     */
    private List<WorkLog> worklog;
    /**
     * The status.
     */
    private Status status;
    /**
     * The summary.
     */
    private String summary;
    /**
     * The environment.
     */
    private String environment;
    /**
     * The aggregate progress.
     */
    private Progress aggregateProgress;
    /**
     * The list of components.
     */
    private List<Component> components;
    /**
     * The resolution.
     */
    private Resolution resolution;
    /**
     * The list of fix versions.
     */
    private List<Version> fixVersions;
    /**
     * The security.
     */
    private Security security;
    /**
     * The resolution date.
     */
    private String resolutiondate;
    /**
     * The watches.
     */
    private Watcher watches;
    /**
     * The assignee.
     */
    private User assignee;
    /**
     * The list of versions.
     */
    private List<Version> versions;
    /**
     * The progress.
     */
    private Progress progress;

    /**
     * Gets the progress.
     *
     * @return the progress.
     */
    public Progress getProgress() {
        return progress;
    }

    /**
     * Sets the progress.
     *
     * @param progress the progress.
     */
    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    /**
     * Gets the list of sub tasks.
     *
     * @return the list of sub tasks.
     */
    public List<Task> getSubTasks() {
        return subTasks;
    }

    /**
     * Sets the list of sub tasks.
     *
     * @param subTasks the list of sub tasks.
     */
    public void setSubTasks(List<Task> subTasks) {
        this.subTasks = subTasks;
    }

    /**
     * Gets the time tracking.
     *
     * @return the time tracking.
     */
    public TimeTracking getTimetracking() {
        return timetracking;
    }

    /**
     * Sets the time tracking.
     *
     * @param timetracking the time tracking.
     */
    public void setTimetracking(TimeTracking timetracking) {
        this.timetracking = timetracking;
    }

    /**
     * Gets the project.
     *
     * @return the project.
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project.
     *
     * @param project the project.
     */
    public void setProject(Project project) {
        this.project = project;
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
     * Gets the list of issue links.
     *
     * @return the list of issue links.
     */
    public List<Task> getIssuelinks() {
        return issuelinks;
    }

    /**
     * Sets the list of issue links.
     *
     * @param issuelinks the list of issue links.
     */
    public void setIssuelinks(List<Task> issuelinks) {
        this.issuelinks = issuelinks;
    }

    /**
     * Gets the list of attachments.
     *
     * @return the list of attachments.
     */
    public List<Attachment> getAttachment() {
        return attachment;
    }

    /**
     * Sets the list of attachments.
     *
     * @param attachment the list of attachments.
     */
    public void setAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
    }

    /**
     * Gets the watcher.
     *
     * @return the watcher.
     */
    public Watcher getWatcher() {
        return watcher;
    }

    /**
     * Sets the watcher.
     *
     * @param watcher the watcher.
     */
    public void setWatcher(Watcher watcher) {
        this.watcher = watcher;
    }

    /**
     * Gets the list of comments.
     *
     * @return the list of comments.
     */
    public List<Comment> getComment() {
        return comment;
    }

    /**
     * Sets the list of comments.
     *
     * @param comment the list of comments.
     */
    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    /**
     * Gets the list of work logs.
     *
     * @return the list of work logs.
     */
    public List<WorkLog> getWorklog() {
        return worklog;
    }

    /**
     * Sets the list of work logs.
     *
     * @param worklog the list of work logs.
     */
    public void setWorklog(List<WorkLog> worklog) {
        this.worklog = worklog;
    }

    /**
     * Gets the status.
     *
     * @return the status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the status.
     */
    public void setStatus(Status status) {
        this.status = status;
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
     * @param summary the summary.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Gets the priority.
     *
     * @return the priority.
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the priority.
     *
     * @param priority the priority.
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Gets the time spent.
     *
     * @return the time spent.
     */
    public String getTimespent() {
        return timespent;
    }

    /**
     * Sets the time spent.
     *
     * @param timespent the time spent.
     */
    public void setTimespent(String timespent) {
        this.timespent = timespent;
    }

    /**
     * Gets the issue type.
     *
     * @return the issue type.
     */
    public IssueType getIssuetype() {
        return issuetype;
    }

    /**
     * Sets the issue type.
     *
     * @param issuetype the issue type.
     */
    public void setIssuetype(IssueType issuetype) {
        this.issuetype = issuetype;
    }

    /**
     * Gets the reporter.
     *
     * @return the reporter.
     */
    public User getReporter() {
        return reporter;
    }

    /**
     * Sets the reporter.
     *
     * @param reporter the reporter.
     */
    public void setReporter(User reporter) {
        this.reporter = reporter;
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
     * @param created the created.
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * Gets the environment.
     *
     * @return the environment.
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Sets the environment.
     *
     * @param environment the environment.
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * Gets the aggregate progress.
     *
     * @return the aggregate progress.
     */
    public Progress getAggregateProgress() {
        return aggregateProgress;
    }

    /**
     * Sets the aggregate progress.
     *
     * @param aggregateProgress the aggregate progress.
     */
    public void setAggregateProgress(Progress aggregateProgress) {
        this.aggregateProgress = aggregateProgress;
    }

    /**
     * Gets the list of components.
     *
     * @return the list of components.
     */
    public List<Component> getComponents() {
        return components;
    }

    /**
     * Sets the list of components.
     *
     * @param components the list of components.
     */
    public void setComponents(List<Component> components) {
        this.components = components;
    }

    /**
     * Gets the resolution.
     *
     * @return the resolution.
     */
    public Resolution getResolution() {
        return resolution;
    }

    /**
     * Sets the resolution.
     *
     * @param resolution the resolution.
     */
    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    /**
     * Gets the list of fix versions.
     *
     * @return the list of fix versions.
     */
    public List<Version> getFixVersions() {
        return fixVersions;
    }

    /**
     * Sets the list of fix versions.
     *
     * @param fixVersions the list of fix versions.
     */
    public void setFixVersions(List<Version> fixVersions) {
        this.fixVersions = fixVersions;
    }

    /**
     * Gets the security.
     *
     * @return the security.
     */
    public Security getSecurity() {
        return security;
    }

    /**
     * Sets the security.
     *
     * @param security the security.
     */
    public void setSecurity(Security security) {
        this.security = security;
    }

    /**
     * Gets the resolution date.
     *
     * @return the resolution date.
     */
    public String getResolutiondate() {
        return resolutiondate;
    }

    /**
     * Sets the resolution date.
     *
     * @param resolutiondate the resolution date.
     */
    public void setResolutiondate(String resolutiondate) {
        this.resolutiondate = resolutiondate;
    }

    /**
     * Gets the watches.
     *
     * @return the watches.
     */
    public Watcher getWatches() {
        return watches;
    }

    /**
     * Sets the watches.
     *
     * @param watches the watches.
     */
    public void setWatches(Watcher watches) {
        this.watches = watches;
    }

    /**
     * Gets the assignee.
     *
     * @return the assignee.
     */
    public User getAssignee() {
        return assignee;
    }

    /**
     * Sets the assignee.
     *
     * @param assignee the assignee.
     */
    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    /**
     * Gets the list of versions.
     *
     * @return the list of versions.
     */
    public List<Version> getVersions() {
        return versions;
    }

    /**
     * Sets the list of versions.
     *
     * @param versions the list of versions.
     */
    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

}
