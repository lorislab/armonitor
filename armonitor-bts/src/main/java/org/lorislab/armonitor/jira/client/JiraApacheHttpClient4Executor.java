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

import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

/**
 * The JIRA apache HTTP client executor.
 *
 * @author Andrej Petras
 */
public class JiraApacheHttpClient4Executor extends ApacheHttpClient4Executor {

    /**
     * The user name.
     */
    private final String username;

    /**
     * The password.
     */
    private final char[] password;

    /**
     * The default constructor.
     *
     * @param username the user name.
     * @param password the password.
     */
    public JiraApacheHttpClient4Executor(String username, char[] password) {
        this.username = username;
        this.password = password;
    }

    /**
     * The default constructor.
     *
     * @param username the user name.
     * @param password the password.
     * @param httpClient the HTTP client.
     */
    public JiraApacheHttpClient4Executor(String username, char[] password, HttpClient httpClient) {
        super(httpClient);
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the credentials.
     *
     * @return the credentials.
     */
    private String encodeCredentials() {
        byte[] credentials = (this.username + ':' + new String(this.password)).getBytes();
        return new String(Base64.encodeBase64(credentials));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientResponse execute(ClientRequest request) throws Exception {
        request.header("Authorization", "Basic " + encodeCredentials());
        return super.execute(request);
    }
}
