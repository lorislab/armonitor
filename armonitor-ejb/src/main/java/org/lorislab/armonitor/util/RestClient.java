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
package org.lorislab.armonitor.util;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;

/**
 * The default rest client.
 *
 * @author Andrej Petras
 */
public class RestClient {

    /**
     * The HTTPS constant.
     */
    private static final String HTTPS = "https";

    /**
     * Gets the rest-service client.
     *
     * @param <T> the rest-service client implementation.
     * @param clazz the rest-service class.
     * @param url the server URL.
     * @param username the username.
     * @param password the password.
     * @param auth the authentication flag.
     * @exception Exception if the method fails.
     *
     * @return the the rest-service client instance.
     */
    public static <T> T getClient(final Class<T> clazz, String url, boolean auth, String username, char[] password) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        if (url.startsWith(HTTPS)) {
            SSLSocketFactory sslSocketFactory = new SSLSocketFactory(new TrustSelfSignedStrategy(), SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme(HTTPS, 443, sslSocketFactory));
        }

        if (auth) {
            BasicCredentialsProvider provider = new BasicCredentialsProvider();
            Credentials credentials = new UsernamePasswordCredentials(username, new String(password));
            provider.setCredentials(AuthScope.ANY, credentials);
            httpClient.setCredentialsProvider(provider);
        }
        return ProxyFactory.create(clazz, url, new ApacheHttpClient4Executor(httpClient));
    }

}
