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
package org.lorislab.armonitor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.store.model.StoreSystemBuild;

/**
 * The change report.
 *
 * @author Andrej Petras
 */
public class ChangeReport implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -8226350181208816998L;
    /**
     * The current changes.
     */
    private final List<Change> changes = new ArrayList<>();

    /**
     * The list of build changes.
     */
    private final List<Change> buildChanges = new ArrayList<>();
    ;

    /**
     * The system build.
     */
    private StoreSystemBuild systemBuild;

    /**
     * The application.
     */
    private StoreApplication application;

    /**
     * The project.
     */
    private StoreProject project;

    /**
     * The GUID.
     */
    private final String guid;

    /**
     * The default constructor.
     *
     * @param guid the GUID.
     */
    public ChangeReport(String guid) {
        this.guid = guid;
    }

    /**
     * Gets the GUID.
     *
     * @return the GUID.
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Gets the project.
     *
     * @return the project.
     */
    public StoreProject getProject() {
        return project;
    }

    /**
     * Sets the project.
     *
     * @param project the project.
     */
    public void setProject(StoreProject project) {
        this.project = project;
    }

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
     * Gets the system build.
     *
     * @return the system build.
     */
    public StoreSystemBuild getSystemBuild() {
        return systemBuild;
    }

    /**
     * Sets the system build.
     *
     * @param systemBuild the system build.
     */
    public void setSystemBuild(StoreSystemBuild systemBuild) {
        this.systemBuild = systemBuild;
    }

    /**
     * Gets the list of build changes.
     *
     * @return the list of build changes.
     */
    public List<Change> getBuildChanges() {
        return buildChanges;
    }

    /**
     * Gets the current changes.
     *
     * @return the current changes.
     */
    public List<Change> getChanges() {
        return changes;
    }

}
