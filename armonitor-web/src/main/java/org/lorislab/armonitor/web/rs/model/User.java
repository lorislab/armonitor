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
 * The user.
 * 
 * @author Andrej Petras
 */
public class User {
   
    /**
     * The GUID.
     */
    public String guid;
    
    /**
     * The name.
     */
    public String name;

    /**
     * The email.
     */
    public String email;
    
    /**
     * The login flag.
     */
    public boolean login;  
    
    /**
     * The enabled flag.
     */
    public boolean enabled;
    
    /**
     * The new flag
     */
    public boolean n;
    /**
     * The version.
     */
    public Integer v;    
}
