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
package org.lorislab.armonitor.agent.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.as.server.deployment.AbstractDeploymentUnitService;
import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.JBossServiceClient;
import org.jboss.as.server.deployment.Services;
import org.jboss.as.server.deployment.module.ResourceRoot;
import org.jboss.msc.service.ServiceContainer;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.lorislab.armonitor.agent.model.SearchCriteria;
import org.lorislab.armonitor.agent.model.SearchResultItem;
import org.lorislab.armonitor.agent.service.ReleaseService;
import org.lorislab.armonitor.arm.model.Arm;
import org.lorislab.armonitor.arm.util.ArmLoader;
import org.lorislab.armonitor.manifest.util.ManifestLoader;

/**
 *
 * @author Andrej Petras
 */
public class ReleaseServiceImpl implements ReleaseService {

    @Override
    public SearchResultItem getAgentRelease(SearchCriteria criteria) {
        SearchResultItem result = new SearchResultItem();

        // load arm model
        if (criteria.isArm()) {
            Arm arm = ArmLoader.loadArmFrom(ReleaseServiceImpl.class);
            result.setArm(arm);
        }

        // load manifest 
        if (criteria.isManifest()) {
            Manifest manifest = ManifestLoader.loadManifestFrom(ReleaseServiceImpl.class);
            if (manifest != null) {
                Attributes mainAttribs = manifest.getMainAttributes();
                if (mainAttribs != null) {
                    for (Map.Entry<Object, Object> entry : mainAttribs.entrySet()) {
                        result.getManifest().put(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }
        }

        return result;
    }

    @Override
    public SearchResultItem getApplicationRelease(SearchCriteria criteria) {
        if (criteria != null && criteria.getService() != null && !criteria.getService().isEmpty()) {
            ServiceName name = Services.JBOSS_DEPLOYMENT_UNIT.append(criteria.getService());
            return createSearchResultItem(criteria, name);
        }
        return null;
    }

    private static SearchResultItem createSearchResultItem(SearchCriteria criteria, ServiceName name) {
        SearchResultItem result = new SearchResultItem();

        ServiceContainer container = CurrentServiceContainer.getServiceContainer();
        ServiceController<?> serviceController = container.getService(name);
        AbstractDeploymentUnitService service = (AbstractDeploymentUnitService) serviceController.getService();
        DeploymentUnit unit = service.getValue();

        result.setService(name.getSimpleName());
        
        ResourceRoot root = unit.getAttachment(Attachments.DEPLOYMENT_ROOT);

        if (criteria.isArm()) {
            try {
                Arm arm = ArmLoader.loadArm(root.getRoot().asFileURL());
                result.setArm(arm);
            } catch (MalformedURLException ex) {
                Logger.getLogger(JBossServiceClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (criteria.isManifest()) {
            Manifest manifest = root.getAttachment(Attachments.MANIFEST);
            if (manifest != null) {
                Attributes mainAttribs = manifest.getMainAttributes();
                if (mainAttribs != null) {
                    for (Map.Entry<Object, Object> entry : mainAttribs.entrySet()) {
                        result.getManifest().put(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<SearchResultItem> getAllReleases(SearchCriteria criteria) {
        List<SearchResultItem> result = new ArrayList<>();
        ServiceContainer container = CurrentServiceContainer.getServiceContainer();
        List<ServiceName> names = container.getServiceNames();
        for (ServiceName name : names) {
            if (Services.JBOSS_DEPLOYMENT_UNIT.getSimpleName().equals(name.getParent().getSimpleName())) {
                SearchResultItem item = createSearchResultItem(criteria, name);
                if (item != null) {
                    result.add(item);
                }
            }
        }
        return result;
    }

}
