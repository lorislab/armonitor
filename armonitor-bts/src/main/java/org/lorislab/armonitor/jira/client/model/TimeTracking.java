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
 * The time tracking.
 *
 * @author Andrej Petras
 */
public class TimeTracking {

    /**
     * The original estimate.
     */
    private String originalEstimate;
    /**
     * The remaining estimate.
     */
    private String remainingEstimate;
    /**
     * The time spent.
     */
    private String timeSpent;
    /**
     * The original estimate in seconds.
     */
    private int originalEstimateSeconds;
    /**
     * The remaining estimate in seconds.
     */
    private int remainingEstimateSeconds;
    /**
     * The time spent in seconds.
     */
    private int timeSpentSeconds;

    /**
     * Gets the original estimate.
     *
     * @return the original estimate.
     */
    public String getOriginalEstimate() {
        return originalEstimate;
    }

    /**
     * Sets the original estimate.
     *
     * @param originalEstimate the original estimate.
     */
    public void setOriginalEstimate(String originalEstimate) {
        this.originalEstimate = originalEstimate;
    }

    /**
     * Gets the remaining estimate.
     *
     * @return the remaining estimate.
     */
    public String getRemainingEstimate() {
        return remainingEstimate;
    }

    /**
     * Sets the remaining estimate.
     *
     * @param remainingEstimate the remaining estimate.
     */
    public void setRemainingEstimate(String remainingEstimate) {
        this.remainingEstimate = remainingEstimate;
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
     * Gets the original estimate in seconds.
     *
     * @return the original estimate in seconds.
     */
    public int getOriginalEstimateSeconds() {
        return originalEstimateSeconds;
    }

    /**
     * Sets the original estimate in seconds.
     *
     * @param originalEstimateSeconds the original estimate in seconds.
     */
    public void setOriginalEstimateSeconds(int originalEstimateSeconds) {
        this.originalEstimateSeconds = originalEstimateSeconds;
    }

    /**
     * Gets the remaining estimate in seconds.
     *
     * @return the remaining estimate in seconds.
     */
    public int getRemainingEstimateSeconds() {
        return remainingEstimateSeconds;
    }

    /**
     * Sets the remaining estimate in seconds.
     *
     * @param remainingEstimateSeconds the remaining estimate in seconds.
     */
    public void setRemainingEstimateSeconds(int remainingEstimateSeconds) {
        this.remainingEstimateSeconds = remainingEstimateSeconds;
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
