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

package org.lorislab.armonitor.store.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.lorislab.armonitor.store.model.enums.StoreBTSystemType;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The bug tracking system.
 * 
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_BTS")
public class StoreBTSystem extends Persistent {
    
    private static final long serialVersionUID = -4923603154378426272L;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "bts")   
    private Set<StoreProject> projects;
    
    @Column(name = "C_USER")
    private String user;
    
    @Column(name = "C_PASWORD")
    private char[] password;
    
    @Column(name = "C_AUTH")
    private boolean auth;
    
    @Column(name = "C_SERVER")
    private String server;
    
    @Column(name = "C_LINK")
    private String link;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "C_TYPE")
    private StoreBTSystemType type;

    /**
     * @return the projects
     */
    public Set<StoreProject> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(Set<StoreProject> projects) {
        this.projects = projects;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public char[] getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(char[] password) {
        this.password = password;
    }

    /**
     * @return the auth
     */
    public boolean isAuth() {
        return auth;
    }

    /**
     * @param auth the auth to set
     */
    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the btsType
     */
    public StoreBTSystemType getType() {
        return type;
    }

    /**
     * @param btsType the btsType to set
     */
    public void setType(StoreBTSystemType type) {
        this.type = type;
    }
        
}
