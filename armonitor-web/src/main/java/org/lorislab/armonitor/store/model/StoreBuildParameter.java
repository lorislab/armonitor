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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.lorislab.jel.jpa.model.Persistent;

/**
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_BUILD_PARAM")
public class StoreBuildParameter extends Persistent {
    
    private static final long serialVersionUID = -5508967118564416749L;
    
    @Column(name = "C_TYPE")
    @Enumerated(EnumType.STRING)
    private StoreBuildParameterType type;
    
    @Column(name = "C_NAME")
    private String name;
    
    @Column(name = "C_VALUE")
    private String value;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StoreBuildParameterType getType() {
        return type;
    }

    public void setType(StoreBuildParameterType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }        
    
}
