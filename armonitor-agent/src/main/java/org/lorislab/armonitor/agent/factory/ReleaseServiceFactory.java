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
package org.lorislab.armonitor.agent.factory;

import org.lorislab.armonitor.agent.service.ReleaseService;
import org.lorislab.armonitor.agent.service.ReleaseServiceImpl;

/**
 * The release service factory.
 *
 * @author Andrej Petras
 */
public final class ReleaseServiceFactory {

    /**
     * The default constructor
     */
    private ReleaseServiceFactory() {
        // empty constructor
    }

    /**
     * Creates the release service.
     *
     * @return the release service.
     */
    public static ReleaseService createService() {
        return new ReleaseServiceImpl();
    }
}
