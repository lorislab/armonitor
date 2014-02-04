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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.ejb.StoreSCMSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreSCMSystem;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;
import org.lorislab.armonitor.web.rs.model.SCMSystem;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SCMSystemServiceBean {

    @EJB
    private StoreSCMSystemServiceBean service;
    
    public void changePassword(String guid, ChangePasswordRequest reqeust) {
        StoreSCMSystem tmp = service.getSCMSystem(guid);
        if (tmp != null) {
            char[] password = tmp.getPassword();
            if (password == null || Arrays.equals(password,reqeust.old.toCharArray())) {
                tmp.setPassword(reqeust.p1.toCharArray());
                service.saveSCMSystem(tmp);
            }
        }
    }    
    public List<SCMSystem> get() {
        List<StoreSCMSystem> tmp = service.getSCMSystems();
        return Mapper.map(tmp, SCMSystem.class);
    }

    public SCMSystem create() {
        return Mapper.create(StoreSCMSystem.class, SCMSystem.class);
    }

    public SCMSystem get(String guid) {
        StoreSCMSystem tmp = service.getSCMSystem(guid);
        return Mapper.map(tmp, SCMSystem.class);
    }

    public SCMSystem save(SCMSystem sys) {
        SCMSystem result;
        StoreSCMSystem tmp = service.getSCMSystem(sys.guid);
        if (tmp != null) {
            tmp = Mapper.update(tmp, sys);
        } else {
            tmp = Mapper.create(sys, StoreSCMSystem.class);
        }
        tmp = service.saveSCMSystem(tmp);
        result = Mapper.map(tmp, SCMSystem.class);
        return result;
    }
}
