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

package org.lorislab.armonitor.process.resources;

/**
 * The process error keys.
 * 
 * @author Andrej Petras
 */
public enum ErrorKeys {
    
    ERROR_CREATE_AGENT_CONNECTION,
    
    NO_AGENT_FOUND,
    
    ERROR_CREATE_BT_CONNECTION,
    
    NO_BT_SYSTEM_FOUND, 
    
    ERROR_CREATE_SCM_CONNECTION,
    
    NO_SCM_SYSTEM_FOUND,
    
    ERROR_SEND_EMAIL,
    
    ERROR_EMAIL_IS_NULL,
    
    NO_SYSTEM_BUILD_FOR_SYSTEM_FOUND, 
    
    NO_SYSTEM_BUILD_FOUND, 
    
    BUILD_ALREADY_INSTALLED,
    
    ERROR_CREATE_ACTIVITY_FOR_BUILD,
    
    NO_ACTIVITY_FOUND_FOR_BUILD,
    
    /**
     * The no build for the GUID and application not found.
     */
    NO_BUILD_FOR_GUID_AND_APPLICATION_FOUND,
    
    NO_APPLICATION_FOR_SYSTEM_FOUND,
    
    NO_SYSTEM_FOR_KEY_FOUND,
    
    NO_PROJECT_FOR_APPLICATION_FOUND,
    
    NO_APPLICATION_FOR_KEY_FOUND;
}
