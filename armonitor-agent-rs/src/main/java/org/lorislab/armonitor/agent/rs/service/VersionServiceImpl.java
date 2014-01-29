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

import org.lorislab.armonitor.agent.factory.ReleaseServiceFactory;
import org.lorislab.armonitor.agent.model.SearchResultItem;
import org.lorislab.armonitor.agent.model.SearchCriteria;
import org.lorislab.armonitor.agent.rs.mapper.ObjectMapper;
import org.lorislab.armonitor.agent.rs.model.Request;
import org.lorislab.armonitor.agent.rs.model.Version;
import org.lorislab.armonitor.agent.rs.service.VersionService;
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
    public Version getAgentVersion(Request request) throws Exception {
        Version result = new Version();
        result.uid = request.uid;
        
        ReleaseService service = ReleaseServiceFactory.createService();
        if (service != null) {
            SearchCriteria criteria = ObjectMapper.createCriteria(request);
            SearchResultItem release = service.getAgentRelease(criteria);
            if (release != null) {
                result = ObjectMapper.createVersion(request, release);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Version getAppVersion(Request request) throws Exception {
        Version result = new Version();
        result.uid = request.uid;
        
        ReleaseService service = ReleaseServiceFactory.createService();
        if (service != null) {
            SearchCriteria criteria = ObjectMapper.createCriteria(request);
            SearchResultItem release = service.getApplicationRelease(criteria);
            if (release != null) {
                result = ObjectMapper.createVersion(request, release);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Version> getAllVersion(Request request) throws Exception {
        List<Version> result = new ArrayList<>();
        ReleaseService service = ReleaseServiceFactory.createService();
        if (service != null) {
            SearchCriteria criteria = ObjectMapper.createCriteria(request);
            List<SearchResultItem> releases = service.getAllReleases(criteria);
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
