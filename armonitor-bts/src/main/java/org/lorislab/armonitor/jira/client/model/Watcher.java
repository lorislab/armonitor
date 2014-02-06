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
 * The watcher.
 *
 * @author Andrej Petras
 */
public class Watcher {

    /**
     * The self.
     */
    private String self;
    /**
     * Is watching flag.
     */
    private boolean isWatching;
    /**
     * The watch count.
     */
    private int watchCount;
    /**
     * The list of watchers.
     */
    private List<User> watchers;

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
     * Gets is watching flag.
     *
     * @return is watching flag.
     */
    public boolean isIsWatching() {
        return isWatching;
    }

    /**
     * Sets is watching flag.
     *
     * @param isWatching is watching flag.
     */
    public void setIsWatching(boolean isWatching) {
        this.isWatching = isWatching;
    }

    /**
     * Gets the watch count.
     *
     * @return the watch count.
     */
    public int getWatchCount() {
        return watchCount;
    }

    /**
     * Sets the watch count.
     *
     * @param watchCount the watch count.
     */
    public void setWatchCount(int watchCount) {
        this.watchCount = watchCount;
    }

    /**
     * Gets the watchers list.
     *
     * @return the watchers list.
     */
    public List<User> getWatchers() {
        return watchers;
    }

    /**
     * Sets the watchers list.
     *
     * @param watchers the watchers list.
     */
    public void setWatchers(List<User> watchers) {
        this.watchers = watchers;
    }

}
