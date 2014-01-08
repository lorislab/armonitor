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

import java.util.ArrayList;
import java.util.List;
import org.lorislab.armonitor.jira.client.model.Issue;
import org.lorislab.armonitor.jira.client.model.SearchCriteria;
import org.lorislab.armonitor.jira.client.model.SearchResult;
import org.lorislab.armonitor.jira.client.services.ProjectClient;
import org.lorislab.armonitor.jira.client.services.SearchClient;

/**
 *
 * @author Andrej Petras
 */
public class TestJIRAClient {
    
    
    public static void main(String ... arg) throws Exception {
        

        JIRAClient jira = new JIRAClient("https://jiraprod/rest/api/2", "andrej_petras", "ixinka20");
        ProjectClient client = jira.createProjectClient();
        SearchClient search = jira.createSearchClient();
        
        SearchCriteria criteria = new SearchCriteria();
        criteria.setJql("project = FOTEC and  fixVersion = \"3.5.5 GA\"");
        List<String> fields = new ArrayList<>();
        fields.add("status");
        fields.add("summary");        
        criteria.setFields(fields);
        SearchResult result = search.search(criteria);
        for (Issue i : result.getIssues()) {
            System.out.println(i.getKey() + "\t" + i.getFields().getStatus().getName() + "\t\t" + i.getFields().getSummary());
        }
    }
}
