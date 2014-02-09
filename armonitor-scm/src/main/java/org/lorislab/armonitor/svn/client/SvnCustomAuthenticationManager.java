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

import org.tmatesoft.svn.core.internal.wc.DefaultSVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;

/**
 * The custom authentication manager.
 * 
 * @author Andrej Petras
 */
public class SvnCustomAuthenticationManager extends DefaultSVNAuthenticationManager {
        
    /**
     * The connection timeout.
     */
    private final Integer connectionTimeout;
    /**
     * The read timeout.
     */
    private final Integer readTimeout;    
    /**
     * The default constructor.
     * @param storeAuth the store authentication.
     * @param userName the user name.
     * @param password the password.
     * @param connectionTimeout the connection timeout.
     * @param readTimeout the read timeout.
     */
    public SvnCustomAuthenticationManager(boolean storeAuth, String userName, char[] password, Integer connectionTimeout, Integer readTimeout) {
        super(null, storeAuth, userName, new String(password), null, null);
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }
       
    @Override
    public int getConnectTimeout(SVNRepository repository) {
        if (connectionTimeout != null) {
            return connectionTimeout;
        }
        return super.getConnectTimeout(repository);
    }

    @Override
    public int getReadTimeout(SVNRepository repository) {
        if (readTimeout != null) {
            return readTimeout;
        }
        return super.getReadTimeout(repository);
    }

}
