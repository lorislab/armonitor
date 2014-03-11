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
import java.security.Principal;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.lorislab.armonitor.web.rs.ejb.UserServiceBean;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;
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
        
    @EJB
    private UserServiceBean service;
    
    public User getUser() {
        return user;
    }
    
    public User setUser(User user) {
        this.user = user;
        return this.user;
    }
    
    public User saveUser(User data) {
        if (data != null && data.guid != null && user != null) {
            if (data.guid.equals(user.guid)) {
                user = service.save(data);
            }           
        }
        return user;
    }

    public void changePassword(ChangePasswordRequest reqeust) throws Exception {
        if (user != null) {
            service.changePassword(user.guid, reqeust);
        }
    }
}
