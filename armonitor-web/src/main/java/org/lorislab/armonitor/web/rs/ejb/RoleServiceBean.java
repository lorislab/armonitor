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

import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.ejb.StoreRoleServiceBean;
import org.lorislab.armonitor.store.model.StoreRole;
import org.lorislab.armonitor.web.rs.model.Role;
import org.lorislab.jel.ejb.exception.ServiceException;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class RoleServiceBean {
    
    @EJB
    private StoreRoleServiceBean service;
    
    public List<Role> get() {
       List<StoreRole> tmp = service.getRoles();
       return Mapper.map(tmp, Role.class);
    }
    
    public Role create() {
        return Mapper.create(StoreRole.class, Role.class);
    }
    
    public Role get(String guid) {
        StoreRole tmp = service.getRole(guid);
        return Mapper.map(tmp, Role.class);
    }
    
    public Role save(Role role) {
        Role result;
        StoreRole tmp = service.getRole(role.guid);
        if (tmp != null) {
            tmp = Mapper.update(tmp, role);
        } else {
            tmp = Mapper.create(role, StoreRole.class);
        }
        tmp = service.saveRole(tmp);
        result = Mapper.map(tmp, Role.class);
        return result;
    }   
    
    public void delete(String guid) throws ServiceException {
        service.deleteRole(guid);
    }     

    public Map<String, Role> map() {
        List<StoreRole> tmp = service.getRoles();
        return Mapper.convert(tmp, Role.class);
    }
}
