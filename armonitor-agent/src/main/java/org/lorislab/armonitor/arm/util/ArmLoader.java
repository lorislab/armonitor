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

package org.lorislab.armonitor.arm.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lorislab.armonitor.arm.model.Arm;
import org.lorislab.armonitor.arm.model.ArmConstant;

/**
 * The ARM model loader.
 * 
 * @author Andrej Petras
 */
public class ArmLoader {
      
    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ArmLoader.class.getName());

    /**
     * The WEB-INF folder name in the WAR package.
     */
    private static final String WEB_INF = "WEB-INF";

    /**
     * The EAR package path substring.
     */
    private static final String EAR_SUBSTRING = ".ear/";
    
    /**
     * The default constructor.
     */
    private ArmLoader() {
        // empty contrustor
    }
    
    public static Arm createArm(Properties properties) {
        Arm result = new Arm();
                
        // add maven properties
        result.setGroupdId((String) properties.remove(ArmConstant.MAVEN_GROUP_ID));        
        result.setArtifactId((String) properties.remove(ArmConstant.MAVEN_ARTIFACT_ID));        
        result.setVersion((String) properties.remove(ArmConstant.MAVEN_VERSION));
        
        // add release version
        result.setScm((String) properties.remove(ArmConstant.RELEASE_SCM));
        result.setBuild((String) properties.remove(ArmConstant.RELEASE_BUILD));
        result.setVersion((String) properties.remove(ArmConstant.RELEASE_VERSION));
        
        // add release date
        String tmp = (String) properties.remove(ArmConstant.RELEASE_DATE);
        Date date = new Date(Long.valueOf(tmp));
        result.setDate(date);
        
        // add other
        for (String name : properties.stringPropertyNames()) {            
            result.getOther().put(name, properties.getProperty(name));
        }
        
        return result;
    }
    
    public static Properties createProperties(Arm arm) {
        Properties result = new Properties();
        
        // add other
        result.putAll(arm.getOther());
        
        // add maven properties
        result.put(ArmConstant.MAVEN_GROUP_ID, arm.getGroupdId());
        result.put(ArmConstant.MAVEN_ARTIFACT_ID, arm.getArtifactId());
        result.put(ArmConstant.MAVEN_VERSION, arm.getVersion());
        
        // add release properties
        result.put(ArmConstant.RELEASE_SCM, arm.getScm());
        result.put(ArmConstant.RELEASE_VERSION, arm.getRelease());
        result.put(ArmConstant.RELEASE_BUILD, arm.getBuild());
        
        // add release properties
        Long tmp = null;
        Date date = arm.getDate();
        if (date != null) {
            tmp = date.getTime();
        }
        result.put(ArmConstant.RELEASE_DATE, tmp);
        
        return result;
    }
    
    /**
     * Loads the manifest from WAR.
     *
     * @param clazz the class.
     * @return the manifest.
     */
    public static Arm loadArmFrom(Class clazz) {
        Arm result = null;
        ProtectionDomain domain = clazz.getProtectionDomain();
        if (domain != null) {
            CodeSource codeSource = domain.getCodeSource();
            if (codeSource != null) {
                URL url = codeSource.getLocation();
                String urlString = codeSource.getLocation().toExternalForm();
                
                if (urlString.contains(EAR_SUBSTRING)) {
                    urlString = urlString.substring(0, urlString.lastIndexOf(EAR_SUBSTRING)) + EAR_SUBSTRING;
                } else if (urlString.contains(WEB_INF)) {
                    urlString = urlString.substring(0, urlString.lastIndexOf(WEB_INF));
                }
                
                try {
                    url = new URL(urlString);
                } catch (MalformedURLException e) {
                    LOGGER.log(Level.SEVERE, "Error creating url for String: " + urlString, e);
                }
                result = loadArm(url);
            }
        }
        return result;
    }

    /**
     * Load the manifest of the JAR file for the class.
     *
     * @param clazz the class
     * @return the manifest of the JAR file for the class.
     */
    public static Arm loadArmFromJar(Class clazz) {
        Arm result = null;
        ProtectionDomain domain = clazz.getProtectionDomain();
        if (domain != null) {
            CodeSource codeSource = domain.getCodeSource();
            if (codeSource != null) {
                result = loadArm(codeSource.getLocation());
            }
        }
        return result;
    }

    /**
     * Read manifest from the URL.
     *
     * @param url the manifest URL.
     * @return the corresponding manifest.
     */
    public static Arm loadArm(URL url) {
        Arm result = null;
        if (url != null) {
            InputStream stream = null;
            try {
                stream = urlToStream(new URL(url + "/" + ArmConstant.FILE_LOCATION));
                
                Properties properties = new Properties();
                properties.load(stream);
                
                result = createArm(properties);
                
            } catch (MalformedURLException e1) {
                LOGGER.log(Level.FINEST, e1.getMessage(), e1);
            } catch (IOException e) {
                LOGGER.log(Level.FINEST, e.getMessage(), e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "Error closing stream: " + e.getMessage(), e);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Creates the input stream to URL.
     *
     * @param url the URL.
     * @return the corresponding stream to the URL.
     * @throws IOException if the method fails.
     */
    private static InputStream urlToStream(URL url) throws IOException {
        if (url != null) {
            URLConnection connection = url.openConnection();
            try {
                connection.setUseCaches(false);
            } catch (IllegalArgumentException e) {
                LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
            return connection.getInputStream();
        } else {
            return null;
        }
    }    
}
