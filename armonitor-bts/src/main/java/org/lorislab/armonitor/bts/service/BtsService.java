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
package org.lorislab.armonitor.bts.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lorislab.armonitor.bts.model.BtsCriteria;
import org.lorislab.armonitor.bts.model.BtsResult;

/**
 * The bug tracking service.
 *
 * @author Andrej Petras
 */
public final class BtsService {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(BtsService.class.getName());
    /**
     * The cache of clients.
     */
    private static final Map<String, BtsServiceClient> CLIENTS = new HashMap<>();

    /**
     * Loads the clients.
     */
    static {
        ServiceLoader<BtsServiceClient> services = ServiceLoader.load(BtsServiceClient.class);
        if (services != null) {
            Iterator<BtsServiceClient> iter = services.iterator();
            while (iter.hasNext()) {
                BtsServiceClient service = iter.next();
                LOGGER.log(Level.FINE, "Add BTS service {0}", service.getClass().getName());
                CLIENTS.put(service.getType(), service);
            }
        }
    }

    /**
     * The default constructor.
     */
    private BtsService() {
        // empty constructor
    }

    /**
     * Gets the set of client service types.
     *
     * @return the set of client service types.
     */
    public static Set<String> getTypes() {
        return CLIENTS.keySet();
    }

    /**
     * Gets the list of issues.
     *
     * @param criteria the criteria.
     * @return the list of issues.
     * @throws Exception if the method fails.
     */
    public static BtsResult getIssues(BtsCriteria criteria) throws Exception {
        if (criteria == null) {
            throw new Exception("Missing bug tracking search criteria!");
        }

        // check type
        BtsServiceClient client = getClient(criteria.getType());
        return client.getIssues(criteria);
    }

    /**
     * Gets the pattern for ID.
     *
     * @param type the BTS type.
     * @param id the id.
     * @return the search pattern.
     * @throws Exception if the method fails.
     */
    public static String getIdPattern(String type, String id) throws Exception {
        BtsServiceClient client = getClient(type);
        return client.getIdPattern(id);
    }

    /**
     * Gets the client for the type.
     *
     * @param type the type.
     * @return the corresponding client.
     * @throws Exception if the clients does not exists.
     */
    public static BtsServiceClient getClient(String type) throws Exception {
        BtsServiceClient client = CLIENTS.get(type);
        if (client == null) {
            throw new Exception("The type " + type + " of the bug tracking is not supported.");
        }
        return client;
    }

}
