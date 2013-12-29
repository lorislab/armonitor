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
package org.lorislab.armonitor.config.rs.service;

import javax.ejb.EJB;
import org.lorislab.armonitor.config.rs.model.JiraConfig;
import org.lorislab.armonitor.config.ejb.ConfigurationServiceLocal;

/**
 *
 * @author Andrej Petras
 */
public class ConfigurationServiceImpl implements ConfigurationService {

    @EJB
    private ConfigurationServiceLocal service;

    @Override
    public JiraConfig getJiraConfig() {
        return service.getConfiguration(JiraConfig.class);
    }

    @Override
    public JiraConfig setJiraConfig(JiraConfig jiraConfig) {
        return service.setConfiguration(jiraConfig);
    }

}
