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

package org.lorislab.armonitor.web.rs.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.store.criteria.StoreBuildCriteria;
import org.lorislab.armonitor.store.ejb.StoreBuildServiceBean;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.web.rs.model.Build;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NEVER)
public class BuildServiceBean {
    
    @EJB
    private StoreBuildServiceBean service;
    
    public List<Build> get(boolean parameters) {
        StoreBuildCriteria criteria = new StoreBuildCriteria();
        criteria.setFetchParameters(parameters);
        List<StoreBuild> tmp = service.getBuilds(criteria);
        return map(tmp);
    }
    
    public List<Build> getForApp(String guid, boolean parameters) {
        StoreBuildCriteria criteria = new StoreBuildCriteria();
        criteria.setFetchParameters(parameters);
        criteria.setApplication(guid);
        List<StoreBuild> tmp = service.getBuilds(criteria);
        return map(tmp);
    }
    
    private List<Build> map(List<StoreBuild> tmp) {
        List<Build> result = null;
        if (tmp != null) {
            result = new ArrayList<>();
            for (StoreBuild item : tmp) {
                Build build = map(item);
                if (build != null) {
                    result.add(build);
                }
            }
        }
        return result;
    }
    
    private Build map(StoreBuild tmp) {
        Build result = null;
        if (tmp != null) {
            result = new Build();
            result.guid = tmp.getGuid();
            
        }
        return result;
    }
    
}
