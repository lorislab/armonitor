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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The user password model.
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_PASSWORD")
public class StorePassword extends Persistent {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -4996176584623934881L;

    /**
     * The main password.
     */
    @Column(name = "C_PASSWORD")
    private char[] password;
    /**
     * The user GUID.
     */
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "C_USER")
    private StoreUser user;

    /**
     * Gets the user.
     *
     * @return the user.
     */
    public StoreUser getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user
     */
    public void setUser(StoreUser user) {
        this.user = user;
    }

    /**
     * Gets the main password.
     *
     * @return the main password.
     */
    public char[] getPassword() {
        return password;
    }

    /**
     * Sets the main password.
     *
     * @param password the main password.
     */
    public void setPassword(char[] password) {
        this.password = password;
    }

}
