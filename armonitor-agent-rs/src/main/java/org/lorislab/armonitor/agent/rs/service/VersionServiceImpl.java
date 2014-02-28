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
package org.lorislab.armonitor.agent.rs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.lorislab.armonitor.agent.factory.ReleaseServiceFactory;
import org.lorislab.armonitor.agent.model.SearchResultItem;
import org.lorislab.armonitor.agent.model.SearchCriteria;
import org.lorislab.armonitor.agent.rs.mapper.ObjectMapper;
import static org.lorislab.armonitor.agent.rs.mapper.ObjectMapper.update;
import org.lorislab.armonitor.agent.rs.model.Request;
import org.lorislab.armonitor.agent.rs.model.Version;
import org.lorislab.armonitor.agent.service.ReleaseService;

/**
 * The version service implementation.
 *
 * @author Andrej Petras
 */
public class VersionServiceImpl implements VersionService {

    /**
     * {@inheritDoc}
     */
    @Override
    public Version getAgentVersion(boolean manifest) throws Exception {
        Version result = new Version();
        result.uid = UUID.randomUUID().toString();
        
        ReleaseService service = ReleaseServiceFactory.createService();
        if (service != null) {
            SearchResultItem release = service.getAgentRelease(manifest);
            if (release != null) {
                result = ObjectMapper.update(result, release);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Version> getVersion(Request request) throws Exception {
        List<Version> result = new ArrayList<>();        
        ReleaseService service = ReleaseServiceFactory.createService();
        if (service != null) {
            SearchCriteria criteria = ObjectMapper.createCriteria(request);
            List<SearchResultItem> releases = service.getRelease(criteria);
            if (releases != null) {
                for (SearchResultItem item : releases) {
                    Version tmp = ObjectMapper.createVersion(request, item);
                    if (tmp != null) {
                        result.add(tmp);
                    }
                }
            }
        }
        return result;
    }

}
