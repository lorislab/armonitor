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
import javax.persistence.Table;
import org.lorislab.jel.jpa.model.Persistent;

/**
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_AGENT")
public class StoreAgent extends Persistent {
    
    private static final long serialVersionUID = -1986215389250537156L;
    
    @Column(name = "C_SYSTEM")
    private String system;
    
    @Column(name = "C_URL")
    private String url;
    
    @Column(name = "C_TYPE")
    @Enumerated(EnumType.STRING)
    private StoreAgentType type;

    @Column(name = "C_AUTH")
    private boolean authentication;
    
    @Column(name = "C_USER")
    private String user;
    
    @Column(name = "C_PASSWORD")
    private String password;

    @Column(name = "C_SERVICE")
    private String service;

    @Column(name = "C_TIMER")
    private boolean timer;

    public boolean isTimer() {
        return timer;
    }

    public void setTimer(boolean timer) {
        this.timer = timer;
    }
        
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
        
    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
        
    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public StoreAgentType getType() {
        return type;
    }

    public void setType(StoreAgentType type) {
        this.type = type;
    }
        
}
