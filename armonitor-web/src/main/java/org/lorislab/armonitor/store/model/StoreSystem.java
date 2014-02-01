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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.lorislab.jel.jpa.model.Persistent;

/**
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_SYSTEM")
public class StoreSystem extends Persistent {

    private static final long serialVersionUID = -4290539931465740615L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_APP")
    private StoreApplication application;

    @Column(name = "C_NAME")
    private String name;

    @Column(name = "C_ENABLED")
    private boolean enabled;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "system")
    private StoreAgent agent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "system")
    private Set<StoreSystemBuild> builds;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "ARM_SYS_ROLE",
            joinColumns = {
                @JoinColumn(name = "C_SYSTEM")},
            inverseJoinColumns = {
                @JoinColumn(name = "C_ROLE")})
    private Set<StoreRole> roles;

    @Column(name = "C_TIMER")
    private boolean timer;

    @Column(name = "C_KEY")
    private String key;

    public Set<StoreRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<StoreRole> roles) {
        this.roles = roles;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isTimer() {
        return timer;
    }

    public void setTimer(boolean timer) {
        this.timer = timer;
    }

    public Set<StoreSystemBuild> getBuilds() {
        return builds;
    }

    public void setBuilds(Set<StoreSystemBuild> builds) {
        this.builds = builds;
    }

    public StoreAgent getAgent() {
        return agent;
    }

    public void setAgent(StoreAgent agent) {
        this.agent = agent;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public StoreApplication getApplication() {
        return application;
    }

    public void setApplication(StoreApplication application) {
        this.application = application;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
