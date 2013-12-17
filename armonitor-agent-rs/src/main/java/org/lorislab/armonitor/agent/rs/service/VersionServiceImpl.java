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

import java.util.Map;
import java.util.jar.Attributes;
import org.lorislab.armonitor.agent.factory.ReleaseServiceFactory;
import org.lorislab.armonitor.agent.model.Release;
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
    public Version getAgentVersion() throws Exception {
        Version result = new Version();
        ReleaseService service = ReleaseServiceFactory.createService();
        if (service != null) {
            Release release = service.getAgentRelease();
            result = createVersion(release);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Version getAppVersion() throws Exception {
        Version result = new Version();
        ReleaseService service = ReleaseServiceFactory.createService();
        if (service != null) {
            Release release = service.getApplicationRelease();
            result = createVersion(release);
        }
        return result;
    }

    /**
     * Creates the version from the release model.
     *
     * @param release the release model.
     * @return the version model.
     */
    private static Version createVersion(Release release) {
        Version result = new Version();
        if (release.getManifest() != null) {
            Attributes mainAttribs = release.getManifest().getMainAttributes();
            if (mainAttribs != null) {
                for (Map.Entry<Object, Object> entry : mainAttribs.entrySet()) {
                    result.manifest.put(entry.getKey().toString(), entry.getValue().toString());
                }
            }
        }       
        return result;
    }
}
