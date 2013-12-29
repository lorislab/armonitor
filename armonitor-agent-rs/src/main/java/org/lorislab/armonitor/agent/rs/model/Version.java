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

package org.lorislab.armonitor.agent.rs.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The version model.
 * 
 * @author Andrej Petras
 */
public class Version {
    
    public String uid;
    
    public int ver = 1;
    
    public Date date;
    
    public String service;
    
    public String groupdId;
    
    public String artifactId;
    
    public String version;
    
    public String release;
    
    public String scm;
    
    public String build;
    
    public Map<String,String> other = new HashMap<>();   
    
    public Map<String,String> manifest = new HashMap<>();   
}
