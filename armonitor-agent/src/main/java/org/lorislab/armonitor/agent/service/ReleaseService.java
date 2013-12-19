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
package org.lorislab.armonitor.agent.service;

import org.lorislab.armonitor.agent.model.Release;
import org.lorislab.armonitor.agent.model.Request;

/**
 * The release service.
 *
 * @author Andrej Petras
 */
public interface ReleaseService {

    /**
     * Gets the release information of the agent.
     *
     * @param request the request.
     * 
     * @return the release information of the agent.
     */
    Release getAgentRelease(Request request);

    /**
     * Gets the release information of the application.
     *
     * @param request the request.
     * 
     * @return the release information of the application.
     */
    Release getApplicationRelease(Request request);
}
