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

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The ARM model.
 * 
 * @author Andrej Petras
 */
public class Arm implements Serializable {
    
    private static final long serialVersionUID = 661979832180196561L;
    
    private Date date;
    
    private String groupdId;
    
    private String artifactId;
    
    private String version;
    
    private String release;
    
    private String scm;
    
    private String build;
    
    private Map<String,String> other = new HashMap<>();   

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    /**
     * @return the groupdId
     */
    public String getGroupdId() {
        return groupdId;
    }

    /**
     * @param groupdId the groupdId to set
     */
    public void setGroupdId(String groupdId) {
        this.groupdId = groupdId;
    }

    /**
     * @return the artifactId
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * @param artifactId the artifactId to set
     */
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the release
     */
    public String getRelease() {
        return release;
    }

    /**
     * @param release the release to set
     */
    public void setRelease(String release) {
        this.release = release;
    }

    /**
     * @return the scm
     */
    public String getScm() {
        return scm;
    }

    /**
     * @param scm the scm to set
     */
    public void setScm(String scm) {
        this.scm = scm;
    }

    /**
     * @return the build
     */
    public String getBuild() {
        return build;
    }

    /**
     * @param build the build to set
     */
    public void setBuild(String build) {
        this.build = build;
    }

    /**
     * @return the other
     */
    public Map<String,String> getOther() {
        return other;
    }

    /**
     * @param other the other to set
     */
    public void setOther(Map<String,String> other) {
        this.other = other;
    }
    
    
}
