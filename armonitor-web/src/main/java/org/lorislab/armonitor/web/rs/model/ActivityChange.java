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

package org.lorislab.armonitor.web.rs.model;

import java.util.List;

/**
 * The activity change.
 * 
 * @author Andrej Petras
 */
public class ActivityChange {
    /**
     * The ID.
     */
    public String id;
    /**
     * The error flag.
     */
    public boolean error;
    /**
     * The not issue flag.
     */
    public boolean not;
    /**
     * The link.
     */
    public String link;
    /**
     * The assignee.
     */
    public String assignee;
    /**
     * The summary.
     */
    public String summary;
    /**
     * The resolution.
     */
    public String resolution;    
    /**
     * The type.
     */
    public String type;
    /**
     * The parent.
     */
    public String parent;
    /**
     * The list of changes.
     */
    public List<ActivityChangeLog> changes;
}
