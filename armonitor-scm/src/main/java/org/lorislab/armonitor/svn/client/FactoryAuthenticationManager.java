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

import org.lorislab.armonitor.scm.model.ScmCriteria;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;

/**
 * The factory authentication manager.
 *
 * @author Andrej Petras
 */
public final class FactoryAuthenticationManager {

    /**
     * The default constructor.
     */
    private FactoryAuthenticationManager() {
        // empty constructor
    }

    /**
     * Creates the subversion custom authentication manager.
     *
     * @param criteria the criteria.
     * @return the subversion custom authentication manager.
     */
    public static SvnCustomAuthenticationManager create(ScmCriteria criteria) {
        return create(criteria.getUser(), criteria.getPassword(), criteria.getConnectionTimeout(), criteria.getReadTimeout());
    }

    /**
     * Creates the subversion custom authentication manager.
     *
     * @param userName the user name.
     * @param password the password.
     * @param connectionTimeout the connection timeout.
     * @param readTimeout the read timeout.
     * @return the subversion custom authentication manager.
     */
    public static SvnCustomAuthenticationManager create(String userName, char[] password, Integer connectionTimeout, Integer readTimeout) {
        DefaultSVNOptions options = new DefaultSVNOptions(null, true);
        boolean store = options.isAuthStorageEnabled();
        return new SvnCustomAuthenticationManager(store, userName, password, connectionTimeout, readTimeout);
    }

}
