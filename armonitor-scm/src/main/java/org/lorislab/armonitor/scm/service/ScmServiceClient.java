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

import java.util.List;
import org.lorislab.armonitor.scm.model.ScmCriteria;
import org.lorislab.armonitor.scm.model.ScmLog;

/**
 * The SCM service client.
 *
 * @author Andrej Petras
 */
public interface ScmServiceClient {

    /**
     * Gets the type.
     *
     * @return the type.
     */
    public String getType();

    /**
     * Gets the log.
     *
     * @param criteria the criteria.
     * @return the list of log items.
     * @throws Exception if the method fails.
     */
    public List<ScmLog> getLog(ScmCriteria criteria) throws Exception;
}
