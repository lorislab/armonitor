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
import org.lorislab.armonitor.store.model.enums.StoreSCMSystemType;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The SCM system.
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_SCM")
public class StoreSCMSystem extends Persistent {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -2589083311226920375L;

    /**
     * The set of the applications for this SCM system.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "scm")
    private Set<StoreApplication> applications;
    /**
     * The user name.
     */
    @Column(name = "C_USER")
    private String user;
    /**
     * The password.
     */
    @Column(name = "C_PASWORD")
    private char[] password;
    /**
     * The authentication flag.
     */
    @Column(name = "C_AUTH")
    private boolean auth;
    /**
     * The server.
     */
    @Column(name = "C_SERVER")
    private String server;
    /**
     * The type.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "C_TYPE")
    private StoreSCMSystemType type;

    /**
     * The connection timeout
     */
    @Column(name = "C_CONN_TIMEOUT")
    private Integer connectionTimeout;

    /**
     * The read timeout.
     */
    @Column(name = "C_READ_TIMEOUT")
    private Integer readTimeout;

    /**
     * Gets the connection timeout.
     *
     * @return the connection timeout.
     */
    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * Sets the connection timeout.
     *
     * @param connectionTimeout the connection timeout.
     */
    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * Gets the read timeout.
     *
     * @return the read timeout.
     */
    public Integer getReadTimeout() {
        return readTimeout;
    }

    /**
     * Sets the read timeout.
     *
     * @param readTimeout the read timeout.
     */
    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * Gets the set of applications.
     *
     * @return the set of applications.
     */
    public Set<StoreApplication> getApplications() {
        return applications;
    }

    /**
     * Sets the set of applications.
     *
     * @param applications the applications to set.
     */
    public void setApplications(Set<StoreApplication> applications) {
        this.applications = applications;
    }

    /**
     * Gets the user name.
     *
     * @return the user name.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user name.
     *
     * @param user the user to set.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets the password.
     *
     * @return the password.
     */
    public char[] getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set.
     */
    public void setPassword(char[] password) {
        this.password = password;
    }

    /**
     * Gets the authentication flag.
     *
     * @return the authentication flag.
     */
    public boolean isAuth() {
        return auth;
    }

    /**
     * Sets the authentication flag.
     *
     * @param auth the authentication flag.
     */
    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    /**
     * Gets the server.
     *
     * @return the server.
     */
    public String getServer() {
        return server;
    }

    /**
     * Sets the server.
     *
     * @param server the server to set.
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Gets the type.
     *
     * @return the type.
     */
    public StoreSCMSystemType getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the type to set.
     */
    public void setType(StoreSCMSystemType type) {
        this.type = type;
    }

}
