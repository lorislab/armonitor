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

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Column(name = "C_PASSWORD1")
    private char[] password1;
    /**
     * The second password.
     */
    @Column(name = "C_PASSWORD2")
    private char[] password2;
    /**
     * The user GUID.
     */
    @Column(name = "C_USER")
    private String userGuid;

    /**
     * Gets the main password.
     *
     * @return the main password.
     */
    public char[] getPassword1() {
        return password1;
    }

    /**
     * Sets the main password.
     *
     * @param password1 the main password.
     */
    public void setPassword1(char[] password1) {
        this.password1 = password1;
    }

    /**
     * Gets the second password.
     *
     * @return the second password.
     */
    public char[] getPassword2() {
        return password2;
    }

    /**
     * Sets the second password.
     *
     * @param password2 the second password.
     */
    public void setPassword2(char[] password2) {
        this.password2 = password2;
    }

    /**
     * Gets the user GUID.
     *
     * @return the user GUID.
     */
    public String getUserGuid() {
        return userGuid;
    }

    /**
     * Sets the user GUID.
     *
     * @param userGuid the user GUID.
     */
    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }
}
