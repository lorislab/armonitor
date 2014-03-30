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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lorislab.armonitor.bts.model.BtsCriteria;
import org.lorislab.armonitor.bts.model.BtsIssue;
import org.lorislab.armonitor.bts.model.BtsResult;
import org.lorislab.armonitor.bts.service.BtsServiceClient;
import org.lorislab.jira.jaxrs.model.FieldNames;
import org.lorislab.jira.jaxrs.model.Fields;
import org.lorislab.jira.jaxrs.model.Issue;
import org.lorislab.jira.jaxrs.model.Permission;
import org.lorislab.jira.jaxrs.model.Permissions;
import org.lorislab.jira.jaxrs.model.SearchCriteria;
import org.lorislab.jira.jaxrs.model.SearchResult;
import org.lorislab.jira.jaxrs.model.User;
import org.lorislab.jira.jaxrs.services.MyPermissionsClient;
import org.lorislab.jira.jaxrs.services.MySelfClient;
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
     * The search fields.
     */
    private static final List<String> FIELDS = Arrays.asList(FieldNames.STATUS, FieldNames.SUMMARY, FieldNames.ASSIGNEE, FieldNames.RESOLUTION, FieldNames.PARENT, FieldNames.ISSUETYPE);

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
    public String getName() {
        return "Jira";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BtsResult getIssues(BtsCriteria criteria) throws Exception {
        BtsResult result = new BtsResult();

        try {
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
            sc.setFields(FIELDS);
            sc.setMaxResults(100);

            // search
            SearchResult sr;
            do {
                sr = search.search(sc);

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

                    if (fields.getParent() != null) {
                        i.setParent(fields.getParent().getKey());
                    }

                    if (fields.getIssuetype() != null) {
                        i.setType(fields.getIssuetype().getName());
                    }
                    result.addIssue(i);
                }

                sc.setStartAt(sc.getStartAt() + sr.getMaxResults());
            } while (sc.getStartAt() < sr.getTotal());

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error reading the issue information from JIRA. {0}", ex.getMessage());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void testConnection(BtsCriteria criteria) throws Exception {
        // create the client
        JIRAClient btsClient = new JIRAClient(criteria.getServer(), criteria.getUser(), criteria.getPassword(), criteria.isAuth());
        MySelfClient client = btsClient.createMySelfClient();
        User user = client.getCurrentlyLoggedUser();
        if (user == null) {
            throw new Exception("Wrong user for the connection!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void testProjectAccess(BtsCriteria criteria) throws Exception {

        // create the client
        JIRAClient btsClient = new JIRAClient(criteria.getServer(), criteria.getUser(), criteria.getPassword(), criteria.isAuth());
        MyPermissionsClient client = btsClient.createMyPermissionsClient();
        Permissions per = client.getPermissions(criteria.getProject(), null, null, null);

        boolean test = false;

        if (per != null && per.getPermissions() != null) {
            Map<String, Permission> tmp = per.getPermissions();
            test = tmp.containsKey("BROWSE");
        }

        if (!test) {
            throw new Exception("No browse access to the project " + criteria.getProject());
        }
    }
}
