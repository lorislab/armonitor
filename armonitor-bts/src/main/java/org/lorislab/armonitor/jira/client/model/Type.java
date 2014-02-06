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
 * The type.
 *
 * @author Andrej Petras
 */
public class Type {

    /**
     * The id.
     */
    private String id;
    /**
     * The name.
     */
    private String name;
    /**
     * The inward.
     */
    private String inward;
    /**
     * The outward.
     */
    private String outward;

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
     * Gets the inward.
     *
     * @return the inward.
     */
    public String getInward() {
        return inward;
    }

    /**
     * Sets the inward.
     *
     * @param inward the inward.
     */
    public void setInward(String inward) {
        this.inward = inward;
    }

    /**
     * Sets the outward.
     *
     * @return the outward.
     */
    public String getOutward() {
        return outward;
    }

    /**
     * Sets the outward.
     *
     * @param outward the outward.
     */
    public void setOutward(String outward) {
        this.outward = outward;
    }

}
