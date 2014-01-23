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

package org.lorislab.armonitor.store.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.lorislab.jel.jpa.model.Persistent;

/**
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_APP")
public class StoreApplication extends Persistent {
    
    private static final long serialVersionUID = -6964092704804204045L;
    
    @Column(name = "C_PROJECT")
    private String project;
    
    @Column(name = "C_NAME")
    private String name;
    
    @Column(name = "C_SCM")
    private String scm;    

    @Column(name = "C_ENABLED")
    private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "application")
    private Set<StoreBuild> builds;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "application")   
    private Set<StoreSystem> systems;

    public Set<StoreSystem> getSystems() {
        return systems;
    }

    public void setSystems(Set<StoreSystem> systems) {
        this.systems = systems;
    }
        
    public Set<StoreBuild> getBuilds() {
        return builds;
    }

    public void setBuilds(Set<StoreBuild> builds) {
        this.builds = builds;
    }
        
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
    
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
    
}
