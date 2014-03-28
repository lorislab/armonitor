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
import javax.persistence.Table;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The system.
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_SYSTEM")
public class StoreSystem extends Persistent {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -4290539931465740615L;

    /**
     * The application.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_APP")
    private StoreApplication application;

    /**
     * The name.
     */
    @Column(name = "C_NAME")
    private String name;

    /**
     * THe enable flag.
     */
    @Column(name = "C_ENABLED")
    private boolean enabled;

    /**
     * The agent.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_AGENT")
    private StoreAgent agent;

    /**
     * The system domain.
     */
    @Column(name = "C_DOMAIN")
    private String domain;

    /**
     * The system link.
     */
    @Column(name = "C_LINK")
    private String link;

    /**
     * The set of store system builds.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "system")
    private Set<StoreSystemBuild> builds;

    /**
     * The set of roles.
     */
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "ARM_SYS_ROLE",
            joinColumns = {
                @JoinColumn(name = "C_SYSTEM")},
            inverseJoinColumns = {
                @JoinColumn(name = "C_ROLE")})
    private Set<StoreRole> roles;

    /**
     * The timer flag.
     */
    @Column(name = "C_TIMER")
    private boolean timer;

    /**
     * The key for manual deployment.
     */
    @Column(name = "C_KEY")
    private String key;

    /**
     * The notification flag.
     */
    @Column(name = "C_NOTIFY")
    private boolean notification;

    /**
     * The service name.
     */
    @Column(name = "C_SERVICE")
    private String service;

    /**
     * The system classification.
     */
    @Column(name = "C_CLASS")
    private String classification;

    /**
     * The index.
     */
    @Column(name = "C_INDEX")
    private Integer index;

    /**
     * Gets the index.
     *
     * @return the index.
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Sets the index.
     *
     * @param index the index.
     */
    public void setIndex(Integer index) {
        this.index = index;
    }
    
    /**
     * Gets the system classification.
     *
     * @return the system classification.
     */
    public String getClassification() {
        return classification;
    }

    /**
     * Sets the system classification.
     *
     * @param classification the system classification.
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }

    /**
     * Gets the domain.
     *
     * @return the domain.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the domain.
     *
     * @param domain the domain.
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * Gets the link.
     *
     * @return the link.
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the link.
     *
     * @param link the link.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets the service name.
     *
     * @return the service name.
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the service name.
     *
     * @param service the service name.
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * Gets the notification flag.
     *
     * @return the notification flag.
     */
    public boolean isNotification() {
        return notification;
    }

    /**
     * Sets the notification flag.
     *
     * @param notification the notification flag.
     */
    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    /**
     * Gets the set of roles.
     *
     * @return the set of roles.
     */
    public Set<StoreRole> getRoles() {
        return roles;
    }

    /**
     * Sets the set of roles.
     *
     * @param roles the set of roles.
     */
    public void setRoles(Set<StoreRole> roles) {
        this.roles = roles;
    }

    /**
     * Gets the key for manual deployment.
     *
     * @return the key for manual deployment.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key for manual deployment.
     *
     * @param key the key for manual deployment.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the timer flag.
     *
     * @return the timer flag.
     */
    public boolean isTimer() {
        return timer;
    }

    /**
     * Sets the timer flag.
     *
     * @param timer the timer flag.
     */
    public void setTimer(boolean timer) {
        this.timer = timer;
    }

    /**
     * Gets the set of system builds.
     *
     * @return the set of system builds.
     */
    public Set<StoreSystemBuild> getBuilds() {
        return builds;
    }

    /**
     * Sets the set of system builds.
     *
     * @param builds the set of system builds.
     */
    public void setBuilds(Set<StoreSystemBuild> builds) {
        this.builds = builds;
    }

    /**
     * Gets the agent.
     *
     * @return the agent.
     */
    public StoreAgent getAgent() {
        return agent;
    }

    /**
     * Sets the agent.
     *
     * @param agent the agent.
     */
    public void setAgent(StoreAgent agent) {
        this.agent = agent;
    }

    /**
     * Sets the enabled flag.
     *
     * @param enabled the enabled flag.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets the enabled flag.
     *
     * @return the enabled flag.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Gets the application.
     *
     * @return the application.
     */
    public StoreApplication getApplication() {
        return application;
    }

    /**
     * Sets the application.
     *
     * @param application the application.
     */
    public void setApplication(StoreApplication application) {
        this.application = application;
    }

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

}
