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
package org.lorislab.armonitor.agent.model;

import java.util.Map;
import org.lorislab.armonitor.arm.model.Arm;

/**
 * The release model.
 *
 * @author Andrej Petras
 */
public class SearchResultItem {

    /**
     * The service.
     */
    private String service;

    /**
     * The application release monitor descriptor
     */
    private Arm arm;

    /**
     * The manifest
     */
    private Map<String, String> manifest;

    /**
     * Gets the service name.
     *
     * @return the service name.
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the service.
     *
     * @param service the service name.
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * Gets the ARM model.
     *
     * @return the ARM model.
     */
    public Arm getArm() {
        return arm;
    }

    /**
     * The ARM model.
     *
     * @param arm the ARM model.
     */
    public void setArm(Arm arm) {
        this.arm = arm;
    }

    /**
     * Gets the manifest.
     *
     * @return the manifest.
     */
    public Map<String, String> getManifest() {
        return manifest;
    }

    /**
     * Sets the manifest.
     *
     * @param manifest the manifest.
     */
    public void setManifest(Map<String, String> manifest) {
        this.manifest = manifest;
    }

}
