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

package org.lorislab.armonitor.store.criteria;

import java.util.Date;
import org.lorislab.jel.base.criteria.AbstractSearchCriteria;

/**
 *
 * @author Andrej Petras
 */
public class StoreBuildCriteria extends AbstractSearchCriteria {
    
    private static final long serialVersionUID = 8885713194174673375L;

    private String guid;
    
    private String application;

    private String agent;
    
    private Date date;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
    
    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
        
    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
        
    @Override
    public void reset() {
        application = null;
        date = null;
        agent = null;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty(application, date, agent);
    }
    
}
