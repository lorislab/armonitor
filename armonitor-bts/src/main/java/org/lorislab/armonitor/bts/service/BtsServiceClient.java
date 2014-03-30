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

import org.lorislab.armonitor.bts.model.BtsCriteria;
import org.lorislab.armonitor.bts.model.BtsResult;

/**
 * The bug tracking client service.
 *
 * @author Andrej Petras
 */
public interface BtsServiceClient {

    /**
     * Gets the id search pattern.
     *
     * @param id the id.
     * @return the search pattern.
     */
    public String getIdPattern(String id);

    /**
     * Gets the client service type.
     *
     * @return the client service type.
     */
    public String getType();

    /**
     * Gets the client name.
     *
     * @return the client name.
     */
    public String getName();

    /**
     * Gets the list of issues.
     *
     * @param criteria the criteria.
     * @return the bug tracking search result.
     * @throws Exception if the method fails.
     */
    public BtsResult getIssues(BtsCriteria criteria) throws Exception;
    
    /**
     * Test the connection.
     *
     * @param criteria the criteria.
     * @throws Exception if the method fails.
     */
    public void testConnection(BtsCriteria criteria) throws Exception;    
}
