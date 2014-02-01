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
 * The role.
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_ROLE")
public class StoreRole extends Persistent {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 5902150853124220591L;

    /**
     * The role name.
     */
    @Column(name = "C_NAME", unique = true, nullable = false)
    private String name;

    /**
     * Gets the role name.
     *
     * @return the role name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the role name.
     *
     * @param name the role name.
     */
    public void setName(String name) {
        this.name = name;
    }

}
