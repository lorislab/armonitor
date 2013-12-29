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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.lorislab.armonitor.scm.client.ScmClient;
import org.lorislab.armonitor.scm.model.ScmLog;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 *
 * @author Andrej Petras
 */
public class SvnClient implements ScmClient {

    @Override
    public List<ScmLog> getLog(String server, String user, String password) throws Exception {

        List<ScmLog> result = new ArrayList<>();
        
        long startRevision = 0;
        long endRevision = -1; //HEAD (the latest) revision

        SVNRepository repository = null;

        repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(server));
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(user, password);
        repository.setAuthenticationManager(authManager);

        Collection logEntries = null;
        logEntries = repository.log(new String[]{""}, null, startRevision, endRevision, true, true);

        if (logEntries != null) {
            SVNLogEntry entry;
            Iterator iter = logEntries.iterator();
            while (iter.hasNext()) {
                entry = (SVNLogEntry) iter.next();
                
                ScmLog log = new ScmLog();
                log.setId("" + entry.getRevision());
                log.setUser(entry.getAuthor());
                log.setMessage(entry.getMessage());
                log.setDate(entry.getDate());
                
                result.add(log);
                
            }
        }
        
        return result;
    }
}
