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
package org.lorislab.maven.armonitor.plugins;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.maven.artifact.manager.WagonManager;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.wagon.authentication.AuthenticationInfo;
import org.lorislab.armonitor.agent.rs.mapper.ObjectMapper;
import org.lorislab.armonitor.agent.rs.model.Version;
import org.lorislab.armonitor.arm.model.Arm;
import org.lorislab.armonitor.arm.util.ArmLoader;
import org.lorislab.armonitor.client.MonitorClient;
import org.lorislab.armonitor.manifest.util.ManifestLoader;

/**
 * The deploy goal.
 * 
 * @author Andrej Petras
 */
@Mojo(name = "deploy",
        defaultPhase = LifecyclePhase.DEPLOY,
        requiresProject = true,
        threadSafe = true)
@Execute(goal = "deploy", phase = LifecyclePhase.PREPARE_PACKAGE)
public class DeployMojo extends AbstractMonitorMojo {

    /**
     * The deploy key parameter.
     */
    @Parameter
    private String deployKey;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute(URL url, String user, String password, boolean authetication, Version version) throws Exception {
        MonitorClient.deploy(url, user, password, authetication, deployKey, version);
    }
}
