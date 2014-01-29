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
 *
 * @author Andrej Petras
 */
@Mojo(name = "deploy",
        defaultPhase = LifecyclePhase.DEPLOY,
        requiresProject = true,
        threadSafe = true)
@Execute(goal = "deploy", phase = LifecyclePhase.PREPARE_PACKAGE)
public class DeployMojo extends AbstractMojo {

    private static final Map<String, String> MAP = new HashMap<>();

    static {
        MAP.put("ejb", "jar");
    }

    /**
     * The MAVEN project.
     */
    @Component
    protected MavenProject project;

    /**
     * The key parameter.
     */
    @Parameter
    private String key;

    /**
     * The URL parameter.
     */
    @Parameter
    private URL url;

    /**
     * The username parameter.
     */
    @Parameter
    private String username;

    /**
     * The password parameter.
     */
    @Parameter
    private String password;

    /**
     * The server parameter.
     */
    @Parameter
    private String server;

    /**
     * The MAVEN Wagon manager to use when obtaining server authentication
     * details.
     */
    @Component(role = WagonManager.class)
    private WagonManager wagonManager;

    /**
     * The path of the file to deploy.
     */
    @Parameter(defaultValue = "${project.build.directory}/${project.build.finalName}", required = true)
    private String deployFile;

    /**
     * The extension of the file to deploy.
     */
    @Parameter(defaultValue = "${project.packaging}", required = true)
    private String packing;

    /**
     * The default execution.
     *
     * @throws MojoExecutionException if the execution fails.
     * @throws MojoFailureException if the build process fails.
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        String user = username;
        String pass = password;

        if (server != null) {
            AuthenticationInfo info = wagonManager.getAuthenticationInfo(server);
            if (info == null) {
                throw new MojoExecutionException("Missing server " + server);
            }
            user = info.getUserName();
            pass = info.getPassword();
        }

        String extension = packing;
        if (MAP.containsKey(extension)) {
            extension = MAP.get(extension);
        }

        String tmp = deployFile + "." + extension;
        File file = new File(tmp);
        if (file.exists()) {

            Version version = null;
            try {
                URL fileUrl = file.toURI().toURL();
                Arm arm = ArmLoader.loadArmFromJar(file);
                Map<String, String> map = ManifestLoader.loadManifestToMap(fileUrl);

                if (arm != null && map != null) {
                    if (arm.getDate() != null) {
                        version = ObjectMapper.create();
                        ObjectMapper.update(version, arm);
                        version.manifest = map;
                    }
                }
            } catch (MalformedURLException ex) {
                throw new MojoExecutionException("Error create the version report", ex);
            }

            if (version != null) {
                try {
                    MonitorClient.send(url, user, pass, key, version);
                } catch (Exception ex) {
                    throw new MojoExecutionException("Error sending the version data to the server", ex);
                }
            } else {
                throw new MojoExecutionException("The version report could not be created! Please check your configuration.");
            }
        } else {
            throw new MojoExecutionException("The deploy file " + tmp + " is missing!");
        }
    }
}
