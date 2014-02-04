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

import org.lorislab.jel.base.criteria.AbstractSearchCriteria;


/**
 *
 * @author Andrej Petras
 */
public class StoreProjectCriteria extends AbstractSearchCriteria {
    
    private static final long serialVersionUID = 617467092691040328L;
    
    private String guid;
    
    private Boolean enabled;

    private String application;

    private boolean fetchApplication;

    private boolean fetchBTS;

    public boolean isFetchBTS() {
        return fetchBTS;
    }

    public void setFetchBTS(boolean fetchBTS) {
        this.fetchBTS = fetchBTS;
    }
        
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
    
    public boolean isFetchApplication() {
        return fetchApplication;
    }

    public void setFetchApplication(boolean fetchApplication) {
        this.fetchApplication = fetchApplication;
    }
        
    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
        
    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void reset() {
        enabled = null;
        application = null;
        fetchApplication = false;
        guid = null;
        fetchBTS = false;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty(enabled, application, guid);
    }
}
