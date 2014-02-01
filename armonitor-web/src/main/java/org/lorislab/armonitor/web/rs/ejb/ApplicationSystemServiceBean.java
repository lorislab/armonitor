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
import java.util.Set;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.criteria.StoreSystemCriteria;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.ejb.StoreRoleServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreRole;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;
import org.lorislab.armonitor.web.rs.model.Role;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ApplicationSystemServiceBean {
   
    @EJB
    private StoreSystemServiceBean service;

    @EJB
    private StoreApplicationServiceBean appService;
    
    @EJB
    private StoreRoleServiceBean roleService;
        
    public Set<Role> getRoles(String guid) {
        StoreSystemCriteria criteria = new StoreSystemCriteria();
        criteria.setGuid(guid);
        criteria.setFetchApplication(false);
        criteria.setFetchRoles(true);
        StoreSystem sys = service.getSystem(criteria);
        if (sys != null) {
            return Mapper.map(sys.getRoles(), Role.class);
        }       
        return null;
    }
    
    public void addRole(String guid, String role) {
        StoreSystemCriteria criteria = new StoreSystemCriteria();
        criteria.setGuid(guid);
        criteria.setFetchApplication(false);
        criteria.setFetchRoles(true);
        StoreSystem sys = service.getSystem(criteria);
        if (sys != null) {
            StoreRole tmp = roleService.getRole(role);
            if (tmp != null) {
                sys.getRoles().add(tmp);
                service.saveSystem(sys);
            }
        }
    }

    public void removeRole(String guid, String role) {
        StoreSystemCriteria criteria = new StoreSystemCriteria();
        criteria.setGuid(guid);
        criteria.setFetchApplication(false);
        criteria.setFetchRoles(true);
        StoreSystem sys = service.getSystem(criteria);
        if (sys != null) {
            StoreRole tmp = roleService.getRole(role);
            if (tmp != null) {
                sys.getRoles().remove(tmp);
                service.saveSystem(sys);
            }
        }
    } 
    
    public List<ApplicationSystem> get() {
        List<StoreSystem> tmp = service.getSystems();
        return Mapper.map(tmp, ApplicationSystem.class);
    }

    public ApplicationSystem get(String uid) {
        StoreSystem tmp = service.getSystem(uid);
        return Mapper.map(tmp, ApplicationSystem.class);
    }

    public ApplicationSystem create() throws Exception {
        return Mapper.create(StoreSystem.class, ApplicationSystem.class);
    }

    public ApplicationSystem save(ApplicationSystem system) throws Exception {
        ApplicationSystem result = null;
        if (system != null) {
            StoreSystem tmp = service.getSystem(system.guid);
            if (tmp != null) {
                tmp = Mapper.update(tmp, system);
            } else {
                tmp = Mapper.create(system, StoreSystem.class);
                StoreApplication app = appService.getApplication(system.application);
                if (app != null) {
                    tmp.setApplication(app);                    
                } else {
                    throw new Exception("Missing application for the system!");
                }
            }              
            tmp = service.saveSystem(tmp);
            result = Mapper.map(tmp, ApplicationSystem.class);
        }
        return result;
    }

    public void deleteKey(String guid) throws Exception {
        StoreSystem tmp = service.getSystem(guid);
        if (tmp != null) {
            tmp.setKey(null);
            service.saveSystem(tmp);
        }
    }
    
    public String generatedKey(String guid) throws Exception {
        String result = null;
        StoreSystem tmp = service.getSystem(guid);
        if (tmp != null && !tmp.isTimer()) {
            tmp.setKey(UUID.randomUUID().toString());
            tmp = service.saveSystem(tmp);
            result = tmp.getKey();
        }        
        return result;
    }
}
