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

import java.util.Iterator;
import java.util.ServiceLoader;
import org.lorislab.armonitor.agent.service.ReleaseService;

/**
 * The release service factory.
 *
 * @author Andrej Petras
 */
public final class ReleaseServiceFactory {

    /**
     * The instance.
     */
    private static ReleaseService INSTANCE = null;

    /**
     * The default constructor
     */
    private ReleaseServiceFactory() {
        // empty constructor
    }

    /**
     * Creates the service.
     *
     * @return the release service.
     */
    public static ReleaseService createService() {
        ServiceLoader<ReleaseService> tmp = ServiceLoader.load(ReleaseService.class);
        if (tmp != null) {
            Iterator<ReleaseService> iter = tmp.iterator();
            if (iter.hasNext()) {
                return iter.next();
            }
        }
        return null;
    }

    /**
     * Creates the release service.
     *
     * @return the release service.
     */
    public static ReleaseService getService() {
        if (INSTANCE == null) {
            INSTANCE = createService();
        }
        return INSTANCE;
    }
}
