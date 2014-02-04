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
import org.lorislab.armonitor.store.ejb.StoreBTSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreBTSystem;
import org.lorislab.armonitor.web.rs.model.BTSystem;
import org.lorislab.armonitor.web.rs.model.ChangePasswordRequest;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BTSystemServiceBean {

    @EJB
    private StoreBTSystemServiceBean service;

    public void changePassword(String guid, ChangePasswordRequest reqeust) {
        StoreBTSystem tmp = service.getBTSystem(guid);
        if (tmp != null) {
            char[] password = tmp.getPassword();
            if (password == null || Arrays.equals(password,reqeust.old.toCharArray())) {
                tmp.setPassword(reqeust.p1.toCharArray());
                service.saveBTSystem(tmp);
            }
        }
    }
    
    public List<BTSystem> get() {
        List<StoreBTSystem> tmp = service.getBTSystems();
        return Mapper.map(tmp, BTSystem.class);
    }

    public BTSystem create() {
        return Mapper.create(StoreBTSystem.class, BTSystem.class);
    }

    public BTSystem get(String guid) {
        StoreBTSystem tmp = service.getBTSystem(guid);
        return Mapper.map(tmp, BTSystem.class);
    }

    public BTSystem save(BTSystem sys) {
        BTSystem result;
        StoreBTSystem tmp = service.getBTSystem(sys.guid);
        if (tmp != null) {
            tmp = Mapper.update(tmp, sys);
        } else {
            tmp = Mapper.create(sys, StoreBTSystem.class);
        }
        tmp = service.saveBTSystem(tmp);
        result = Mapper.map(tmp, BTSystem.class);
        return result;
    }
}
