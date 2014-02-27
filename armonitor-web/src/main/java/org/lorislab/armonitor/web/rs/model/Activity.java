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

package org.lorislab.armonitor.web.rs.model;

import java.util.List;

/**
 * The activity model.
 * 
 * @author Andrej Petras
 */
public class Activity {
    /**
     * The GUID.
     */
    public String guid;
    /**
     * The build.
     */
    public Build build;
    /**
     * The application.
     */
    public String app;
    /**
     * The application name.
     */
    public String appName;
    /**
     * The project.
     */
    public String project;
    /**
     * The project name.
     */
    public String projectName;
    /**
     * The list of all changes.
     */
    public List<ActivityChange> changes;
    /**
     * The list of current build changes.
     */
    public List<ActivityChange> buildChanges;
}
