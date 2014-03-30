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
package org.lorislab.armonitor.svn.client;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lorislab.armonitor.scm.model.ScmCriteria;
import org.lorislab.armonitor.scm.model.ScmLog;
import org.lorislab.armonitor.scm.model.ScmResult;
import org.lorislab.armonitor.scm.service.ScmServiceClient;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

/**
 * The subversion service client.
 *
 * @author Andrej Petras
 */
public class SvnScmServiceClient implements ScmServiceClient {

    /**
     * The logger for this class.
     */    
    private static final Logger LOGGER = Logger.getLogger(SvnScmServiceClient.class.getName());
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "SUBVERSION";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Subversion";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScmResult getLog(ScmCriteria criteria) throws Exception {

        ScmResult result = new ScmResult();

        long startRevision = 0;
        if (criteria.getStart() != null) {
            startRevision = Long.parseLong(criteria.getStart());
        }
        long endRevision = -1; //HEAD (the latest) revision
        if (criteria.getEnd() != null) {
            endRevision = Long.parseLong(criteria.getEnd());
        }

        SVNRepository repository = null;
        try {
            
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(criteria.getServer()));
            
            if (criteria.isAuth()) {
                repository.setAuthenticationManager(FactoryAuthenticationManager.create(criteria));
            }

            String[] path = new String[]{""};
            if (criteria.getPath() != null && !criteria.getPath().isEmpty()) {
                path = criteria.getPath().toArray(new String[criteria.getPath().size()]);
            }

            Collection logEntries = repository.log(path, null, startRevision, endRevision, true, true);

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

                    result.addScmLog(log);

                }
            }
        } finally {
            if (repository != null) {
                repository.closeSession();
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public void testConnection(ScmCriteria criteria) throws Exception {
        SVNRepository repository = null;
        try { 
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(criteria.getServer()));            
            
            if (criteria.isAuth()) {
                repository.setAuthenticationManager(FactoryAuthenticationManager.create(criteria));
            }
            
            repository.testConnection();
        } finally {
            if (repository != null) {
                repository.closeSession();
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */    
    @Override
    public void testRepository(ScmCriteria criteria) throws Exception {
        SVNRepository repository = null;
        try { 
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(criteria.getServer()));            
            
            if (criteria.isAuth()) {
                repository.setAuthenticationManager(FactoryAuthenticationManager.create(criteria));
            }
            
            long revision = -1;
            SVNDirEntry dir = repository.info(".", revision);
            if (dir == null) {
                throw new Exception("Error reading the repository information.");
            }
            revision = dir.getRevision();
            LOGGER.log(Level.INFO, "Revision {0} is the last in the repository {1}", new Object[]{revision, criteria.getServer()});
        } finally {
            if (repository != null) {
                repository.closeSession();
            }
        }
    }    
}
