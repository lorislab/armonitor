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

import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import org.lorislab.armonitor.agent.model.Release;
import org.lorislab.armonitor.agent.model.Request;
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
    public Release getAgentRelease(Request request) {
        Release result = new Release();

        // load arm model
        if (request.isArm()) {
            Arm arm = ArmLoader.loadArmFromJar(ReleaseServiceImpl.class);
            result.setArm(arm);
        }
        
        // load manifest 
        if (request.isManifest()) {       
            Manifest manifest = ManifestLoader.loadManifestFromJar(ReleaseServiceImpl.class);
            if (manifest != null) {
                Attributes mainAttribs = manifest.getMainAttributes();
                if (mainAttribs != null) {
                    for (Map.Entry<Object, Object> entry : mainAttribs.entrySet()) {
                        result.getManifest().put(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Release getApplicationRelease(Request request) {
        Release result = new Release();

        // load arm model
        if (request.isArm()) {
            Arm arm = ArmLoader.loadArmFrom(ReleaseServiceImpl.class);
            result.setArm(arm);
        }
        
        // load manifest 
        if (request.isManifest()) {       
            Manifest manifest = ManifestLoader.loadManifestFrom(ReleaseServiceImpl.class);
            if (manifest != null) {
                Attributes mainAttribs = manifest.getMainAttributes();
                if (mainAttribs != null) {
                    for (Map.Entry<Object, Object> entry : mainAttribs.entrySet()) {
                        result.getManifest().put(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }
        }
        
        return result;
    }

}
