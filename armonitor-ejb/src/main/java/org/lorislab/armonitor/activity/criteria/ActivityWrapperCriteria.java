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

package org.lorislab.armonitor.activity.criteria;

import org.lorislab.jel.base.criteria.AbstractSearchCriteria;

/**
 *
 * @author Andrej Petras
 */
public class ActivityWrapperCriteria extends AbstractSearchCriteria {
    
    private static final long serialVersionUID = -156558770692659920L;
    
    private String guid;
    
    private String build;
    
    private boolean sortList;

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean isSortList() {
        return sortList;
    }

    public void setSortList(boolean sortList) {
        this.sortList = sortList;
    }
    
    @Override
    public void reset() {
        guid = null;
        build = null;
        sortList = false;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty(guid, build);
    }
    
}
