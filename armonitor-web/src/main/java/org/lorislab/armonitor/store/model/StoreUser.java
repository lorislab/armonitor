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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The user model.
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_USER")
public class StoreUser extends Persistent {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -4010121895506227069L;
    /**
     * The user name.
     */
    @Column(name = "C_NAME")
    private String name;
    /**
     * The user email.
     */
    @Column(name = "C_EMAIL")
    private String email;

    /**
     * The user login flag.
     */
    @Column(name = "C_LOGIN")
    private boolean login;

    /**
     * The set of roles.
     */
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "ARM_USER_ROLE",
            joinColumns = {
                @JoinColumn(name = "C_USER")},
            inverseJoinColumns = {
                @JoinColumn(name = "C_ROLE")})
    private Set<StoreRole> roles;

    /**
     * Gets the login flag.
     *
     * @return the login flag.
     */
    public boolean isLogin() {
        return login;
    }

    /**
     * Sets the login flag.
     *
     * @param login the login flag.
     */
    public void setLogin(boolean login) {
        this.login = login;
    }

    /**
     * Gets the roles.
     *
     * @return the user roles.
     */
    public Set<StoreRole> getRoles() {
        return roles;
    }

    /**
     * Sets the user roles.
     *
     * @param roles the user roles.
     */
    public void setRoles(Set<StoreRole> roles) {
        this.roles = roles;
    }

    /**
     * Gets the user name.
     *
     * @return the user name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user name.
     *
     * @param name the user name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user email.
     *
     * @return the user email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user email.
     *
     * @param email the user email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
