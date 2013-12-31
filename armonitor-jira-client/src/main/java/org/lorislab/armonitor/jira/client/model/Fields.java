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
 *
 * @author Andrej Petras
 */
public class Fields {
    
    private Priority priority;
    
    private String timespent;
    
    private IssueType issuetype;
    
    private User reporter;
    
    @XmlElement(name = "sub-tasks")
    private List<Task> subTasks;
    
    private TimeTracking timetracking;
    
    private Project project;
    
    private String updated;
    
    private String created;
    
    private String description;
    
    private List<Task> issuelinks;
    
    private List<Attachment> attachment;
    
    private Watcher watcher;
    
    private List<Comment> comment;
    
    private List<WorkLog> worklog;
    
    private Status status;
    
    private String summary;

    private String environment;
    
    private Progress aggregateProgress;
    
    private List<Component> components;
    
    private Resolution resolution;
    
    private List<Version> fixVersions;
    
    private Security security;
    
    private String resolutiondate;
    
    private Watcher watches;
    
    private User assignee;
    
    private List<Version> versions;
        
    private Progress progress;

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }
        
    /**
     * @return the subTasks
     */
    public List<Task> getSubTasks() {
        return subTasks;
    }

    /**
     * @param subTasks the subTasks to set
     */
    public void setSubTasks(List<Task> subTasks) {
        this.subTasks = subTasks;
    }

    /**
     * @return the timetracking
     */
    public TimeTracking getTimetracking() {
        return timetracking;
    }

    /**
     * @param timetracking the timetracking to set
     */
    public void setTimetracking(TimeTracking timetracking) {
        this.timetracking = timetracking;
    }

    /**
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * @return the updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the issuelinks
     */
    public List<Task> getIssuelinks() {
        return issuelinks;
    }

    /**
     * @param issuelinks the issuelinks to set
     */
    public void setIssuelinks(List<Task> issuelinks) {
        this.issuelinks = issuelinks;
    }

    /**
     * @return the attachment
     */
    public List<Attachment> getAttachment() {
        return attachment;
    }

    /**
     * @param attachment the attachment to set
     */
    public void setAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
    }

    /**
     * @return the watcher
     */
    public Watcher getWatcher() {
        return watcher;
    }

    /**
     * @param watcher the watcher to set
     */
    public void setWatcher(Watcher watcher) {
        this.watcher = watcher;
    }

    /**
     * @return the comment
     */
    public List<Comment> getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    /**
     * @return the worklog
     */
    public List<WorkLog> getWorklog() {
        return worklog;
    }

    /**
     * @param worklog the worklog to set
     */
    public void setWorklog(List<WorkLog> worklog) {
        this.worklog = worklog;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * @return the timespent
     */
    public String getTimespent() {
        return timespent;
    }

    /**
     * @param timespent the timespent to set
     */
    public void setTimespent(String timespent) {
        this.timespent = timespent;
    }

    /**
     * @return the issuetype
     */
    public IssueType getIssuetype() {
        return issuetype;
    }

    /**
     * @param issuetype the issuetype to set
     */
    public void setIssuetype(IssueType issuetype) {
        this.issuetype = issuetype;
    }

    /**
     * @return the reporter
     */
    public User getReporter() {
        return reporter;
    }

    /**
     * @param reporter the reporter to set
     */
    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    /**
     * @return the created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return the environment
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * @param environment the environment to set
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * @return the aggregateProgress
     */
    public Progress getAggregateProgress() {
        return aggregateProgress;
    }

    /**
     * @param aggregateProgress the aggregateProgress to set
     */
    public void setAggregateProgress(Progress aggregateProgress) {
        this.aggregateProgress = aggregateProgress;
    }

    /**
     * @return the components
     */
    public List<Component> getComponents() {
        return components;
    }

    /**
     * @param components the components to set
     */
    public void setComponents(List<Component> components) {
        this.components = components;
    }

    /**
     * @return the resolution
     */
    public Resolution getResolution() {
        return resolution;
    }

    /**
     * @param resolution the resolution to set
     */
    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    /**
     * @return the fixVersions
     */
    public List<Version> getFixVersions() {
        return fixVersions;
    }

    /**
     * @param fixVersions the fixVersions to set
     */
    public void setFixVersions(List<Version> fixVersions) {
        this.fixVersions = fixVersions;
    }

    /**
     * @return the security
     */
    public Security getSecurity() {
        return security;
    }

    /**
     * @param security the security to set
     */
    public void setSecurity(Security security) {
        this.security = security;
    }

    /**
     * @return the resolutiondate
     */
    public String getResolutiondate() {
        return resolutiondate;
    }

    /**
     * @param resolutiondate the resolutiondate to set
     */
    public void setResolutiondate(String resolutiondate) {
        this.resolutiondate = resolutiondate;
    }

    /**
     * @return the watches
     */
    public Watcher getWatches() {
        return watches;
    }

    /**
     * @param watches the watches to set
     */
    public void setWatches(Watcher watches) {
        this.watches = watches;
    }

    /**
     * @return the assignee
     */
    public User getAssignee() {
        return assignee;
    }

    /**
     * @param assignee the assignee to set
     */
    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    /**
     * @return the versions
     */
    public List<Version> getVersions() {
        return versions;
    }

    /**
     * @param versions the versions to set
     */
    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }
    
    
}
