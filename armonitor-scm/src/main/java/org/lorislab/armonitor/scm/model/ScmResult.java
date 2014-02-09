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
package org.lorislab.armonitor.scm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The SCM search result.
 *
 * @author Andrej Petras
 */
public class ScmResult implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 2706786230774245834L;
    /**
     * The list of SCM logs.
     */
    private final List<ScmLog> logs = new ArrayList<>();
    /**
     * The map of SCM logs.
     */
    private final Map<String, ScmLog> map = new HashMap<>();

    /**
     * Adds the SCM log to the result.
     *
     * @param log the SCM log.
     */
    public void addScmLog(ScmLog log) {
        if (!map.containsKey(log.getId())) {
            logs.add(log);
            map.put(log.getId(), log);
        }
    }

    /**
     * Gets the list of SCM logs.
     *
     * @return the list of SCM logs.
     */
    public List<ScmLog> getScmLogs() {
        return logs;
    }

    /**
     * Gets the SCM log by id.
     *
     * @param id the SCM log id.
     * @return the SCM log or <code>null</code>.
     */
    public ScmLog getScmLog(String id) {
        return map.get(id);
    }

    /**
     * The size of the result list.
     *
     * @return the size of the result list.
     */
    public int size() {
        return logs.size();
    }
    

    /**
     * Returns <code>true</code> if the list of logs is empty.
     *
     * @return <code>true</code> if the list of logs is empty.
     */
    public boolean isEmpty() {
        return logs.isEmpty();
    }    
}
