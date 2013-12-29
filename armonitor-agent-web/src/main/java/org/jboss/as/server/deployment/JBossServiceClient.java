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

package org.jboss.as.server.deployment;

import java.net.MalformedURLException;
import java.util.List;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.as.server.deployment.module.ResourceRoot;
import org.jboss.msc.service.ServiceContainer;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.lorislab.armonitor.arm.model.Arm;
import org.lorislab.armonitor.arm.util.ArmLoader;

/**
 *
 * @author Andrej Petras
 */
public class JBossServiceClient {
    
    public static void test() {
        ServiceContainer container = CurrentServiceContainer.getServiceContainer();
        List<ServiceName> names = container.getServiceNames();
        for (ServiceName name : names) {

            if (Services.JBOSS_DEPLOYMENT_UNIT.getSimpleName().equals(name.getParent().getSimpleName())) {
                System.out.println(name.getSimpleName());
                System.out.println(name.getCanonicalName());
                
                ServiceController<?> serviceController = container.getService(name);
                AbstractDeploymentUnitService service = (AbstractDeploymentUnitService) serviceController.getService();
                
                
                DeploymentUnit unit = service.getValue();                   
                ResourceRoot root = unit.getAttachment(Attachments.DEPLOYMENT_ROOT);
                Manifest manifest = root.getAttachment(Attachments.MANIFEST);
                try {
                    Arm arm2 = ArmLoader.loadArm(root.getRoot().asFileURL());
//                    System.out.println(ReflectionUtil.toString(arm2));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(JBossServiceClient.class.getName()).log(Level.SEVERE, null, ex);
                }                
                System.out.println(root.getRootName());
                
            }
        }
        
    }

}
