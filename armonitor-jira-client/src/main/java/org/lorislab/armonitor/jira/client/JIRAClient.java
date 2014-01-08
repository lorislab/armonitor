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
package org.lorislab.armonitor.jira.client;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.lorislab.armonitor.jira.client.services.ProjectClient;
import org.lorislab.armonitor.jira.client.services.SearchClient;


/**
 * The JIRA client.
 *
 * @author Andrej Petras
 */
public class JIRAClient {

    private static final String HTTPS = "https";
    
    private final String server;
    
    private final ClientExecutor executor;
    
    public JIRAClient(String server, String username, String password) throws Exception {
        this.server = server;
        
        HttpClient httpClient = new DefaultHttpClient();
        if (server.startsWith(HTTPS)) {
            SSLSocketFactory sslSocketFactory = new SSLSocketFactory(new TrustSelfSignedStrategy(), SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme(HTTPS, 443, sslSocketFactory));
        }
        this.executor = new JiraApacheHttpClient4Executor(username, password, httpClient);          
    }
    
    public SearchClient createSearchClient() {
        SearchClient client = ProxyFactory.create(SearchClient.class, server, executor);
        return client;        
    }
    
    public ProjectClient createProjectClient() {
        ProjectClient client = ProxyFactory.create(ProjectClient.class, server, executor);
        return client;
    }

}
