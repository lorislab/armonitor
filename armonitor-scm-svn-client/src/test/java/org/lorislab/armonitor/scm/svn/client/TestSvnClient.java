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

package org.lorislab.armonitor.scm.svn.client;

import java.util.List;
import org.lorislab.armonitor.scm.model.ScmCriteria;
import org.lorislab.armonitor.scm.model.ScmLog;

/**
 *
 * @author Andrej Petras
 */
public class TestSvnClient {
    
    public static void main(String ... arg) throws Exception {
        SvnClient client = new SvnClient();
        
        ScmCriteria criteria = new ScmCriteria();
        criteria.setServer("https://subversion/svn/HighQMan/GiAG/FORel3/branches/branch-3.5/Version-3.5.5.GA");
        criteria.setUser("pet");
        criteria.setPassword("ixinka20");
        
        List<ScmLog> logs = client.getLog(criteria);
        
        for (ScmLog log : logs) {
            System.out.println(log.getId() + " " + log.getUser() + " " + log.getMessage());
        }
    }
    
}
