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
package org.lorislab.armonitor.activity.wrapper;

import java.io.Serializable;
import java.util.Date;
import org.lorislab.armonitor.store.model.StoreActivityLog;

/**
 * The activity change log.
 * 
 * @author Andrej Petras
 */
public class ActivityChangeLogWrapper implements Serializable {
    
    private static final long serialVersionUID = 5349085743738031574L;
    
    private final StoreActivityLog log;

    /**
     * The link.
     */
    private String link;
    
    public ActivityChangeLogWrapper(StoreActivityLog log) {
        this.log = log;
    }
           
    public StoreActivityLog getLog() {
        return log;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
        
    public Date getDate() {
        return log.getDate();
    }
    
    public boolean isBuild() {
        return log.getBuild() != null;
    }
}
