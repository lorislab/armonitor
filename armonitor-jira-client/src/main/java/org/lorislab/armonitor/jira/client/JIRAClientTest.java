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

import java.util.List;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.resteasy.client.ProxyFactory;
import org.lorislab.armonitor.jira.client.model.Project;
import org.lorislab.armonitor.jira.client.model.Version;
import org.lorislab.armonitor.jira.client.services.ProjectClient;


/**
 * The JIRA client.
 *
 * @author Andrej Petras
 */
public class JIRAClientTest {

    private static String server = "https://jiraprod/rest/api/2";
    private static String username = "andrej_petras";
    private static String password = "ixinka20";
    
    public static void main(String... a) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        SSLSocketFactory sslSocketFactory = new SSLSocketFactory(new TrustSelfSignedStrategy(), SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, sslSocketFactory));

        ProjectClient client = ProxyFactory.create(ProjectClient.class, server, new JiraApacheHttpClient4Executor(username, password, httpClient));
        List<Project> projects = client.getProjects();
        if (projects != null) {
            for (Project p : projects) {
//                System.out.println(p.getId() + "K: " + p.getKey() + "N: " + p.getName());

                System.out.println("##############" + p.getName() + "#################");
                List<Version> versions = client.getVersions(p.getId());
                for (Version v : versions) {
                    if (!v.released) {
                        System.out.println(v.name);
                    }
                }
                System.out.println("###############################");
            }
        }

    }
}
