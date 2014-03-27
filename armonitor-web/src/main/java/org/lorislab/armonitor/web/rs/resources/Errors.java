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

package org.lorislab.armonitor.web.rs.resources;

import org.lorislab.jel.base.resources.annotations.ResourceKey;

/**
 * The error keys.
 * 
 * @author Andrej Petras
 */
@ResourceKey
public enum Errors {
    
    WRONG_USER_OR_PASSWORD,
    
    SCM_DELETE_ERROR,
    
    DASHBOARD_LOAD_ERROR,
    
    DASHBOARD_UPDATE_SYSTEM_BUILD_ERROR;
}
