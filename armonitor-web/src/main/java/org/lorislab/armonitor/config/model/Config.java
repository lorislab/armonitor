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

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import org.lorislab.jel.jpa.model.Persistent;

/**
 *
 * @author Andrej Petras
 */
@Entity(name = "ARM_CONFIG")
public class Config extends Persistent {
    
    private static final long serialVersionUID = 6269308858836846322L;

    @Column(name = "C_CLASS")
    private String clazz;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapKeyColumn(name="C_NAME")   
    @JoinColumn(name = "C_CONFIG")
    private Map<String, Attribute> attributes = new HashMap<>();

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

    
    public void setAttributes(Map<String, Attribute> attributes) {
        this.attributes = attributes;
    }
        
}
