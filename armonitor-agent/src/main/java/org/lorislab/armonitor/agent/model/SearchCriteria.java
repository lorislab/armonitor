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

/**
 * The request model.
 *
 * @author Andrej Petras
 */
public class SearchCriteria {

    /**
     * The manifest.
     */
    private boolean manifest = false;

    /**
     * The service name.
     */
    private String service = null;

    /**
     * Gets the service.
     *
     * @return the service.
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the service.
     *
     * @param service the service.
     */
    public void setService(String service) {
        this.service = service;
    }   

    /**
     * Gets the manifest.
     *
     * @return the manifest.
     */
    public boolean isManifest() {
        return manifest;
    }

    /**
     * Sets the manifest.
     *
     * @param manifest the manifest.
     */
    public void setManifest(boolean manifest) {
        this.manifest = manifest;
    }

}
