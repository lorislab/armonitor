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
 * The application system.
 *
 * @author Andrej Petras
 */
public class ApplicationSystem {

    /**
     * The GUID.
     */
    public String guid;
    /**
     * The name.
     */
    public String name;
    /**
     * The enable flag.
     */
    public boolean enabled;
    /**
     * The timer flag.
     */
    public boolean timer;
    /**
     * The notification flag.
     */
    public boolean notification;
    /**
     * The service.
     */
    public String service;    
    /**
     * The domain.
     */
    public String domain;
    /**
     * The link.
     */
    public String link;
    /**
     * The classification.
     */
    public String clazz;
    /**
     * The new flag
     */
    public boolean n;    
}
