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
public class StoreAgentCriteria extends AbstractSearchCriteria {
    
    private static final long serialVersionUID = 8700313965472589998L;

    private String system;

    private Boolean timer;

    public Boolean isTimer() {
        return timer;
    }

    public void setTimer(Boolean timer) {
        this.timer = timer;
    }
        
    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
    
    @Override
    public void reset() {
        system = null;
        timer = null;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty(system, timer);
    }
    
}
