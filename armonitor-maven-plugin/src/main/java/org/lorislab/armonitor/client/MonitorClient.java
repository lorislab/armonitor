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
package org.lorislab.armonitor.client;

import java.net.URL;
import org.lorislab.armonitor.agent.rs.model.Version;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.lorislab.armonitor.rs.model.Request;
import org.lorislab.armonitor.rs.model.Result;
import org.lorislab.armonitor.rs.model.Status;
import org.lorislab.armonitor.rs.service.MonitorService;

/**
 * The ARMONITOR client.
 *
 * @author Andrej Petras
 */
public final class MonitorClient {

    /**
     * The agent monitor URL.
     */
    private static final String APP_URL = "armonitor/rs";

    /**
     * The default constructor.
     */
    private MonitorClient() {
        // empty constructor
    }

    /**
     * Creates the monitor service.
     *
     * @param url the URL.
     * @param username the user name.
     * @param password the password.
     * @return the monitor service.
     */
    private static MonitorService createService(String url, String username, String password, boolean auth) {
        RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
        if (auth) {
            Credentials credentials = new UsernamePasswordCredentials(username, password);
            DefaultHttpClient httpClient = new DefaultHttpClient();
            BasicCredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(AuthScope.ANY, credentials);
            httpClient.setCredentialsProvider(provider);
            return ProxyFactory.create(MonitorService.class, url, new ApacheHttpClient4Executor(httpClient));
        }
        return ProxyFactory.create(MonitorService.class, url);
    }

    /**
     * Sends the version for the system <code>key</code> to the ARMONITOR
     * server.
     *
     * @param url the URL.
     * @param user the user name.
     * @param password the password.
     * @param auth the authentication flag.
     * @param key the system key.
     * @param version the version.
     * @throws java.lang.Exception if the method fails.
     */
    public static void send(URL url, String user, String password, boolean auth, String key, Version version) throws Exception {
        try {

            String tmp = url.toString();
            if (!tmp.endsWith("/")) {
                tmp = tmp + "/";
            }
            tmp = tmp + APP_URL;

            MonitorService service = createService(tmp, user, password, auth);
            Request request = new Request();
            request.version = version;
            request.key = key;
            Result result = service.buildRequest(request);
            if (result != null) {
                if (result.status == null || result.status.equals(Status.ERROR)) {
                    throw new Exception("Error process request: " + result.message);
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error sending the data to the server!", ex);
        }
    }

}
