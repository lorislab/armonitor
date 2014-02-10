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

import java.util.Date;
import java.util.Map;

/**
 * The build.
 *
 * @author Andrej Petras
 */
public class Build {

    /**
     * The GUID.
     */
    public String guid;
    /**
     * The application.
     */
    public String application;
    /**
     * The agent.
     */
    public String agent;
    /**
     * The UID.
     */
    public String uid;
    /**
     * The version.
     */
    public Integer ver;
    /**
     * The date.
     */
    public Date date;
    /**
     * The service.
     */
    public String service;
    /**
     * The MAVEN group id.
     */
    public String groupdId;
    /**
     * The MAVEN artifact id.
     */
    public String artifactId;
    /**
     * The MAVEN version.
     */
    public String mavenVersion;
    /**
     * The SCM.
     */
    public String scm;
    /**
     * The build.
     */
    public String build;
    /**
     * The manifest parameters.
     */
    public Map<String, String> manifest;
    /**
     * The other parameters.
     */
    public Map<String, String> other;

}
