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
 * The issue.
 *
 * @author Andrej Petras
 */
public class Issue {

    /**
     * The list of expand attributes.
     */
    private String expand;
    /**
     * The id.
     */
    private String id;
    /**
     * The self.
     */
    private String self;
    /**
     * The key.
     */
    private String key;
    /**
     * The fields.
     */
    private Fields fields;

    /**
     * Gets the fields.
     *
     * @return the fields.
     */
    public Fields getFields() {
        return fields;
    }

    /**
     * Sets the fields.
     *
     * @param fields the fields.
     */
    public void setFields(Fields fields) {
        this.fields = fields;
    }

    /**
     * Gets the list of expand attributes.
     *
     * @return the list of expand attributes.
     */
    public String getExpand() {
        return expand;
    }

    /**
     * Sets the list of expand attributes.
     *
     * @param expand the list of expand attributes.
     */
    public void setExpand(String expand) {
        this.expand = expand;
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

}
