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
package org.lorislab.armonitor.jira.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lorislab.armonitor.bts.model.BtsCriteria;
import org.lorislab.armonitor.bts.model.BtsIssue;
import org.lorislab.armonitor.bts.service.BtsServiceClient;
import org.lorislab.jira.jaxrs.model.FieldNames;
import org.lorislab.jira.jaxrs.model.Fields;
import org.lorislab.jira.jaxrs.model.Issue;
import org.lorislab.jira.jaxrs.model.SearchCriteria;
import org.lorislab.jira.jaxrs.model.SearchResult;
import org.lorislab.jira.jaxrs.services.SearchClient;

/**
 * The bug tracking JIRA client service.
 *
 * @author Andrej Petras
 */
public class JiraBtsServiceClient implements BtsServiceClient {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(JiraBtsServiceClient.class.getName());
    /**
     * The default resolution status.
     */
    private static final String DEFAULT_RESOLUTION = "Unresolved";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "JIRA";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BtsIssue> getIssues(BtsCriteria criteria) throws Exception {
        List<BtsIssue> result = new ArrayList<>();

        boolean first = false;
        StringBuilder jql = new StringBuilder();

        // version
        if (criteria.getVersion() != null) {
            if (first) {
                jql.append(" and ");
            }
            jql.append("fixVersion = \"").append(criteria.getVersion()).append('"');
            first = true;
        }

        // project
        if (criteria.getProject() != null) {
            if (first) {
                jql.append(" and ");
            }
            jql.append("project = ").append(criteria.getProject());
            first = true;
        }

        // ids
        if (criteria.getIds() != null && !criteria.getIds().isEmpty()) {
            if (first) {
                jql.append(" and ");
            }
            jql.append("id in (");
            boolean ff = false;
            for (String id : criteria.getIds()) {
                if (ff) {
                    jql.append(',');
                }
                jql.append(id);
                ff = true;
            }
            jql.append(')');
        }

        // id
        if (criteria.getId() != null) {
            if (first) {
                jql.append(" and ");
            }
            jql.append("id = ");
            jql.append(criteria.getId());
        }

        LOGGER.log(Level.INFO, "Jira criteria: {0}", jql.toString());
        // create the client
        JIRAClient btsClient = new JIRAClient(criteria.getServer(), criteria.getUser(), criteria.getPassword(), criteria.isAuth());
        SearchClient search = btsClient.createSearchClient();

        SearchCriteria sc = new SearchCriteria();
        sc.setJql(jql.toString());
        sc.setFields(Arrays.asList(FieldNames.STATUS, FieldNames.SUMMARY, FieldNames.ASSIGNEE, FieldNames.RESOLUTION));
        sc.setMaxResults(100);

        // search
        SearchResult sr;
        do {
            sr = search.search(sc);

            List<BtsIssue> items = map(sr);
            result.addAll(items);

            sc.setStartAt(sc.getStartAt() + sr.getMaxResults());
        } while (sc.getStartAt() < sr.getTotal());

        return result;
    }

    /**
     * Map the search result to the issue model.
     *
     * @param sr the search result.
     * @return the list of issues.
     */
    private List<BtsIssue> map(SearchResult sr) {
        List<BtsIssue> result = new ArrayList<>();
        if (sr != null) {
            for (Issue issue : sr.getIssues()) {
                BtsIssue i = new BtsIssue();
                i.setId(issue.getKey());
                Fields fields = issue.getFields();
                if (fields.getAssignee() != null) {
                    i.setAssignee(fields.getAssignee().getDisplayName());
                }
                if (fields.getResolution() != null) {
                    i.setResolution(fields.getResolution().getName());
                } else {
                    i.setResolution(DEFAULT_RESOLUTION);
                }
                i.setSummary(fields.getSummary());
                result.add(i);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIdPattern(String id) {
        return id + "\\-\\d+";
    }

}
