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
package org.lorislab.armonitor.scm.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lorislab.armonitor.scm.model.ScmCriteria;
import org.lorislab.armonitor.scm.model.ScmResult;

/**
 * The SCM service.
 *
 * @author Andrej Petras
 */
public final class ScmService {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ScmService.class.getName());
    /**
     * The cache of the service clients.
     */
    private static final Map<String, ScmServiceClient> CLIENTS = new HashMap<>();

    /**
     * Loads the service clients.
     */
    static {
        ServiceLoader<ScmServiceClient> services = ServiceLoader.load(ScmServiceClient.class);
        if (services != null) {
            Iterator<ScmServiceClient> iter = services.iterator();
            while (iter.hasNext()) {
                ScmServiceClient service = iter.next();
                LOGGER.log(Level.FINE, "Add SCM service {0}", service.getClass().getName());
                CLIENTS.put(service.getType(), service);
            }
        }
    }

    /**
     * The default constructor.
     */
    private ScmService() {
        // empty constructor
    }

    /**
     * Gets the list of issues.
     *
     * @param criteria the criteria.
     * @return the list of logs.
     * @throws Exception if the method fails.
     */
    public static ScmResult getLog(ScmCriteria criteria) throws Exception {
        if (criteria == null) {
            throw new Exception("Missing bug tracking search criteria!");
        }

        // check client
        ScmServiceClient client = getClient(criteria.getType());
        return client.getLog(criteria);
    }  
    
    /**
     * Gets the client by type.
     *
     * @param type the type.
     * @return the service client.
     * @throws Exception if the method fails.
     */
    public static ScmServiceClient getClient(String type) throws Exception {
        ScmServiceClient client = CLIENTS.get(type);
        if (client == null) {
            throw new Exception("The type " + type + " of the scm service is not supported.");
        }
        return client;
    }
}
