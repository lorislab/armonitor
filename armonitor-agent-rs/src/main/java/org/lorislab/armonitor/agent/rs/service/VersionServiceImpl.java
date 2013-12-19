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

import java.util.Date;
import java.util.UUID;

import org.lorislab.armonitor.agent.factory.ReleaseServiceFactory;
import org.lorislab.armonitor.agent.model.Release;
import org.lorislab.armonitor.agent.model.Request;
import org.lorislab.armonitor.agent.rs.model.Version;
import org.lorislab.armonitor.agent.service.ReleaseService;
import org.lorislab.armonitor.arm.model.Arm;

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
    public Version getAgentVersion(String manifest) throws Exception {
        Version result = new Version();
        ReleaseService service = ReleaseServiceFactory.createService();
        if (service != null) {
            Request request = new Request();
            request.setManifest(manifest != null && !manifest.isEmpty());
            
            Release release = service.getAgentRelease(request);
            result = createVersion(release);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Version getAppVersion(String manifest) throws Exception {
        Version result = new Version();
        ReleaseService service = ReleaseServiceFactory.createService();
        if (service != null) {
            Request request = new Request();
            request.setManifest(manifest != null && !manifest.isEmpty());                    
            
            Release release = service.getApplicationRelease(request);
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
        result.date = new Date();
        result.uid = UUID.randomUUID().toString();
        
        Arm arm = release.getArm();
        if (arm != null) {
            // add maven attributes
            result.groupdId = arm.getGroupdId();
            result.artifactId = arm.getArtifactId();
            result.version = arm.getVersion();

            // add release attribtues
            result.release = arm.getRelease();
            result.scm = arm.getScm();
            result.build = arm.getBuild();

            // add ARM other attributes
            result.other = arm.getOther();
        }
        
        // add manifest
        result.manifest = release.getManifest();
        
        return result;
    }
}
