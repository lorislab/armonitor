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

package org.lorislab.armonitor.config.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import org.lorislab.jel.jpa.model.Persistent;

/**
 *
 * @author Andrej Petras
 */
@Entity(name = "ARM_CONFIG_ATTR")
public class Attribute extends Persistent {
    
    private static final long serialVersionUID = 8218977162091953419L;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
