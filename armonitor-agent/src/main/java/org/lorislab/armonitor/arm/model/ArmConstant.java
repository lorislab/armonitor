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

package org.lorislab.armonitor.arm.model;

/**
 * The ARM model constant.
 * 
 * @author Andrej Petras
 */
public interface ArmConstant {
   
    public static final String FILE_NAME = "arm.properties";
    
    public static final String DIR_LOCATION = "META-INF/armonitor";
    
    public static final String FILE_LOCATION = DIR_LOCATION + "/" + FILE_NAME;
        
    public static final String MAVEN_GROUP_ID = "Maven-GroupId";
    
    public static final String MAVEN_ARTIFACT_ID = "Maven-ArtifactId";
    
    public static final String MAVEN_VERSION = "Maven-Version";
    
    public static final String RELEASE_VERSION = "Release-Version";
    
    public static final String RELEASE_SCM = "Release-Scm";
    
    public static final String RELEASE_BUILD = "Release-Build";
    
    public static final String RELEASE_DATE = "Release-Date";
}
