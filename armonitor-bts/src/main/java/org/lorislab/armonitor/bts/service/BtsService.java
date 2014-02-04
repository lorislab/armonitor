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
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lorislab.armonitor.bts.model.BtsCriteria;
import org.lorislab.armonitor.bts.model.BtsIssue;

/**
 *
 * @author Andrej Petras
 */
public final class BtsService {
    
    private static final Logger LOGGER = Logger.getLogger(BtsService.class.getName());
    
    private static final Map<String, BtsServiceClient> CLIENTS = new HashMap<>();
    
    static {
        ServiceLoader<BtsServiceClient> services = ServiceLoader.load(BtsServiceClient.class);
        if (services != null) {
            Iterator<BtsServiceClient> iter = services.iterator();
            while (iter.hasNext()) {
                BtsServiceClient service = iter.next();
                LOGGER.log(Level.FINE,"Add BTS service {0}", service.getClass().getName());
                CLIENTS.put(service.getType(), service);
            }
        }        
    }
    
    private BtsService() {
        // empty constructor
    }
    
    public static List<BtsIssue> getIssues(BtsCriteria criteria) throws Exception {
        if (criteria == null) {
            throw new Exception("Missing bug tracking search criteria!");
        }
        
        // check type
        BtsServiceClient client = getClient(criteria.getType());                
        return client.getIssues(criteria);
    }
    
    public static String getIdPattern(String type, String id) throws Exception {
        BtsServiceClient client = getClient(type);  
        return client.getIdPattern(id);
    }
    
    public static BtsServiceClient getClient(String type) throws Exception {
        BtsServiceClient client = CLIENTS.get(type);
        if (client == null) {
            throw new Exception("The type " + type + " of the bug tracking is not supported.");
        }
        return client;
    }
    
}
