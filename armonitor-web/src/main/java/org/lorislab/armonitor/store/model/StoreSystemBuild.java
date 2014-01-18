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

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Proxy;
import org.lorislab.jel.jpa.model.Persistent;

/**
 *
 * @author Andrej Petras
 */
@Proxy(lazy=false)
@Entity
@Table(name = "ARM_SYSTEM_BUILD")
public class StoreSystemBuild extends Persistent {
    
    private static final long serialVersionUID = -5357640264532302086L;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "C_SYSTEM")
    private StoreSystem system;
      
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)    
    @JoinColumn(name = "C_BUILD")
    private StoreBuild build;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_DATE")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
        
    public StoreSystem getSystem() {
        return system;
    }

    public void setSystem(StoreSystem system) {
        this.system = system;
    }

    public StoreBuild getBuild() {
        return build;
    }

    public void setBuild(StoreBuild build) {
        this.build = build;
    }
     
}
