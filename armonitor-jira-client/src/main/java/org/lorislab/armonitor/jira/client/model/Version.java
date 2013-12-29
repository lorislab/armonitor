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
 * The JIRA version.
 * 
 * @author Andrej Petras
 */
public class Version {
    
    public String self;
    
    public String id;
    
    public String description;
    
    public String name;
    
    public boolean archived;
    
    public boolean released;
    
    public String projectId;
    
    public boolean overdue;
    
    public String releaseDate;
    
    public String userReleaseDate;
    
    public String startDate;
    
    public String userStartDate;
}
