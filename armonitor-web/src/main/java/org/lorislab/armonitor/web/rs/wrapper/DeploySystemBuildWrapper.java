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
package org.lorislab.armonitor.web.rs.wrapper;

import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreSystemBuild;

/**
 * The deploy system build wrapper.
 *
 * @author Andrej Petras
 */
public class DeploySystemBuildWrapper {

    /**
     * The the current system build.
     */
    private StoreSystemBuild systemBuild;
    /**
     * The application.
     */
    private StoreApplication application;

    /**
     * Gets the application.
     *
     * @return the application.
     */
    public StoreApplication getApplication() {
        return application;
    }

    /**
     * Sets the application.
     *
     * @param application the application.
     */
    public void setApplication(StoreApplication application) {
        this.application = application;
    }

    /**
     * Gets the current system build.
     *
     * @return the current system build.
     */
    public StoreSystemBuild getSystemBuild() {
        return systemBuild;
    }

    /**
     * Sets the current system build.
     *
     * @param systemBuild the current system build.
     */
    public void setSystemBuild(StoreSystemBuild systemBuild) {
        this.systemBuild = systemBuild;
    }

}
