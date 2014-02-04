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
 *
 * @author Andrej Petras
 */
public class Task {
    
    private String id;
     
    private Type type;
    
    private LinkIssue outwardIssue;

    private LinkIssue inwardIssue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LinkIssue getOutwardIssue() {
        return outwardIssue;
    }

    public void setOutwardIssue(LinkIssue outwardIssue) {
        this.outwardIssue = outwardIssue;
    }

    public LinkIssue getInwardIssue() {
        return inwardIssue;
    }

    public void setInwardIssue(LinkIssue inwardIssue) {
        this.inwardIssue = inwardIssue;
    }
    
    
}
