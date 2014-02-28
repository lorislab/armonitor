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
package org.lorislab.armonitor.agent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.lorislab.armonitor.agent.model.SearchResultItem;
import org.lorislab.armonitor.agent.model.SearchCriteria;
import org.lorislab.armonitor.arm.model.Arm;
import org.lorislab.armonitor.arm.util.ArmLoader;
import org.lorislab.armonitor.manifest.util.ManifestLoader;

/**
 * The release service implementation.
 *
 * @author Andrej Petras
 */
public class ReleaseServiceImpl implements ReleaseService {

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResultItem getAgentRelease(boolean manifest) {
        SearchResultItem result = new SearchResultItem();

        // load arm model
        Arm arm = ArmLoader.loadArmFromJar(ReleaseServiceImpl.class);
        result.setArm(arm);
        
        // load manifest 
        if (manifest) {    
            Map<String,String> tmp = ManifestLoader.loadManifestFromJarToMap(ReleaseServiceImpl.class); 
            result.setManifest(tmp);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SearchResultItem> getRelease(SearchCriteria request) {
        List<SearchResultItem> result = new ArrayList<>();

        SearchResultItem item = new SearchResultItem();
        result.add(item);
        
        // load arm model
        Arm arm = ArmLoader.loadArmFrom(ReleaseServiceImpl.class);
        item.setArm(arm);
        
        // load manifest 
        if (request.isManifest()) {
            Map<String,String> tmp = ManifestLoader.loadManifestFromToMap(ReleaseServiceImpl.class);            
            item.setManifest(tmp);
        }
        
        return result;
    }
}
