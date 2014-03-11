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

package org.lorislab.armonitor.web.rs.ejb;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SecurityServiceBean {
    
    private static final Logger LOGGER = Logger.getLogger(SecurityServiceBean.class.getName());
        
    @Resource
    private EJBContext context;
    
    @EJB
    private StoreUserServiceBean service; 
    
    public User getUser(LoginRequest request) {
        User result = null;                        
        StoreUserCriteria criteria = new StoreUserCriteria();
        criteria.setEmail(request.email);
        StoreUser tmp = service.getUser(criteria);
        if (tmp != null) {
           result = Mapper.map(tmp, User.class);
        } else {
            LOGGER.log(Level.SEVERE, "Not valid user for email {0}", request.email);
        }
        return result;
    }
    
    public Map<String,Boolean> isUserInRole(Set<String> roles) {
        Map<String,Boolean> result = new HashMap<>();
        if (roles != null) {
            for (String role : roles) {
                boolean tmp = context.isCallerInRole(role);
                result.put(role, tmp);
            }
        }
        return result;
    }
}
