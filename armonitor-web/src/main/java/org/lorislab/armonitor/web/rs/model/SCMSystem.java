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

/**
 * The SCM system.
 *
 * @author Andrej Petras
 */
public class SCMSystem {

    /**
     * The GUID.
     */
    public String guid;
    /**
     * The user.
     */
    public String user;
    /**
     * The authentication flag.
     */
    public boolean auth;
    /**
     * The server.
     */
    public String server;
    /**
     * The SCM system type.
     */
    public String type;
    /**
     * The read timeout.
     */
    public Integer readTimeout;
    /**
     * The connection timeout.
     */
    public Integer connectionTimeout;
    
    /**
     * The new flag
     */
    public boolean n;
}
