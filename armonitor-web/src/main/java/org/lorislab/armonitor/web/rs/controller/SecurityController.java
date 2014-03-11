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

package org.lorislab.armonitor.web.rs.controller;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.criteria.StoreUserCriteria;
import org.lorislab.armonitor.store.ejb.StoreUserServiceBean;
import org.lorislab.armonitor.store.model.StoreUser;
import org.lorislab.armonitor.web.rs.model.LoginRequest;
import org.lorislab.armonitor.web.rs.model.User;

/**
 *
 * @author Andrej Petras
 */
@Named
@SessionScoped
public class SecurityController implements Serializable {
    
    private static final long serialVersionUID = 3414127937239625590L;
    
    private static final Logger LOGGER = Logger.getLogger(SecurityController.class.getName());
        
    private User user;
    
    public User getUser() {
        return user;
    }
    
    public User setUser(User user) {
        this.user = user;
        return this.user;
    }
}
