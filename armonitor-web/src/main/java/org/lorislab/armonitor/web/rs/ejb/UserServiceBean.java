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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.criteria.StoreUserCriteria;
import org.lorislab.armonitor.store.ejb.StorePasswordServiceBean;
import org.lorislab.armonitor.store.ejb.StoreRoleServiceBean;
import org.lorislab.armonitor.store.ejb.StoreUserServiceBean;
import org.lorislab.armonitor.store.model.StorePassword;
import org.lorislab.armonitor.store.model.StoreRole;
import org.lorislab.armonitor.store.model.StoreUser;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;
import org.lorislab.armonitor.web.rs.model.Role;
import org.lorislab.armonitor.web.rs.model.User;
import org.lorislab.jel.ejb.exception.ServiceException;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserServiceBean {

    @EJB
    private StoreUserServiceBean service;

    @EJB
    private StoreRoleServiceBean roleService;

    @EJB
    private StorePasswordServiceBean passwordService;

    public Map<String, Role> getRoles(String guid) {
        StoreUserCriteria criteria = new StoreUserCriteria();
        criteria.setGuid(guid);
        criteria.setFetchRoles(true);
        StoreUser user = service.getUser(criteria);
        if (user != null) {
            return Mapper.convert(user.getRoles(), Role.class);
        }
        return null;
    }

    public void addRole(String guid, String role) {
        StoreUserCriteria criteria = new StoreUserCriteria();
        criteria.setGuid(guid);
        criteria.setFetchRoles(true);
        StoreUser user = service.getUser(criteria);
        if (user != null) {
            StoreRole tmp = roleService.getRole(role);
            if (tmp != null) {
                user.getRoles().add(tmp);
                service.saveUser(user);
            }
        }
    }

    public void removeRole(String guid, String role) {
        StoreUserCriteria criteria = new StoreUserCriteria();
        criteria.setGuid(guid);
        criteria.setFetchRoles(true);
        StoreUser user = service.getUser(criteria);
        if (user != null) {
            StoreRole tmp = roleService.getRole(role);
            if (tmp != null) {
                user.getRoles().remove(tmp);
                service.saveUser(user);
            }
        }
    }

    public List<User> get() {
        List<StoreUser> tmp = service.getUsers();
        return Mapper.map(tmp, User.class);
    }

    public User create() {
        return Mapper.create(StoreUser.class, User.class);
    }

    public User get(String guid) {
        StoreUser tmp = service.getUser(guid);
        return Mapper.map(tmp, User.class);
    }

    public User save(User user) {
        StoreUser tmp = service.getUser(user.guid);
        if (tmp != null) {
            tmp = Mapper.update(tmp, user);
        } else {
            tmp = Mapper.create(user, StoreUser.class);
        }
        tmp = service.saveUser(tmp);
        User result = Mapper.map(tmp, User.class);
        return result;
    }

    public void changePassword(String guid, ChangePasswordRequest reqeust) throws Exception {
        StoreUser user = service.getUser(guid);
        if (user != null) {
            StorePassword tmp = passwordService.getPasswordForUser(guid);
            if (tmp != null) {
                char[] password = tmp.getPassword();
                if (password == null || Arrays.equals(password, reqeust.old.toCharArray())) {
                    tmp.setPassword(reqeust.p1.toCharArray());
                    passwordService.savePassword(tmp);
                } else {
                    throw new Exception("Wrong password");
                }
            } else {
                tmp = new StorePassword();
                tmp.setUser(user);
                tmp.setPassword(reqeust.p1.toCharArray());                
                passwordService.savePassword(tmp);
            }            
        }
    }

    public void delete(String guid) throws ServiceException {
        service.deleteUser(guid);
    }     

    public void resetPassword(String guid, ChangePasswordRequest reqeust) {
        StoreUser user = service.getUser(guid);
        if (user != null) {
            StorePassword tmp = passwordService.getPasswordForUser(guid);
            if (tmp != null) {
                tmp.setPassword(reqeust.p1.toCharArray());
                passwordService.savePassword(tmp);
            } else {
                tmp = new StorePassword();
                tmp.setUser(user);
                tmp.setPassword(reqeust.p1.toCharArray());                
                passwordService.savePassword(tmp);
            }            
        }
    }
}
