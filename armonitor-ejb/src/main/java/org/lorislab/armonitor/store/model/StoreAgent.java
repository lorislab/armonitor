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
import org.lorislab.armonitor.store.model.enums.StoreAgentType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The agent.
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_AGENT")
public class StoreAgent extends Persistent {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -1986215389250537156L;

    /**
     * The name.
     */
    @Column(name = "C_NAME")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "agent")
    private Set<StoreSystem> systems;

    @Column(name = "C_URL")
    private String url;

    @Column(name = "C_TYPE")
    @Enumerated(EnumType.STRING)
    private StoreAgentType type;

    @Column(name = "C_AUTH")
    private boolean authentication;

    @Column(name = "C_USER")
    private String user;

    @Column(name = "C_PASSWORD")
    private char[] password;

    /**
     * Gets the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    public Set<StoreSystem> getSystems() {
        return systems;
    }

    public void setSystems(Set<StoreSystem> systems) {
        this.systems = systems;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public StoreAgentType getType() {
        return type;
    }

    public void setType(StoreAgentType type) {
        this.type = type;
    }

}
