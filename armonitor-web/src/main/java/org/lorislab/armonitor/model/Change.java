/*
 * Copyright 2014 lorislab.org.
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

package org.lorislab.armonitor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.lorislab.armonitor.bts.model.BtsIssue;
import org.lorislab.armonitor.scm.model.ScmLog;

/**
 *
 * @author Andrej Petras
 */
public class Change implements Serializable {
    
    private static final long serialVersionUID = -1744381414449867439L;
    
    private String id;
    
    private BtsIssue issue;
    
    private final List<ScmLog> scmLogs = new ArrayList<>();
 
    public BtsIssue getIssue() {
        return issue;
    }

    public void setIssue(BtsIssue issue) {
        this.issue = issue;
    }   
        
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ScmLog> getScmLogs() {
        return scmLogs;
    }
    
    public boolean isError() {
        return issue == null;
    }
}
