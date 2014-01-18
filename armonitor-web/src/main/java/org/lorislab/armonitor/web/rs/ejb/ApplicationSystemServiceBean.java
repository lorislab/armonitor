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

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.web.rs.model.ApplicationSystem;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ApplicationSystemServiceBean {
   
    @EJB
    private StoreSystemServiceBean service;

    @EJB
    private StoreApplicationServiceBean appService;
    
    public List<ApplicationSystem> get() {
        List<StoreSystem> tmp = service.getSystems();
        return map(tmp);
    }

    public ApplicationSystem get(String uid) {
        StoreSystem tmp = service.getSystem(uid);
        return map(tmp);
    }

    public ApplicationSystem create() throws Exception {
        StoreSystem tmp = new StoreSystem();
        ApplicationSystem result = new ApplicationSystem();
        result.guid = tmp.getGuid();
        return result;
    }

    public ApplicationSystem save(ApplicationSystem system) throws Exception {
        ApplicationSystem result = null;
        if (system != null) {
            StoreSystem tmp = service.getSystem(system.guid);
            if (tmp != null) {
                tmp = update(tmp, system);
                tmp = service.saveSystem(tmp);
                tmp = service.getSystem(tmp.getGuid());
                result = map(tmp);
            } else {
                tmp = new StoreSystem();
                tmp.setGuid(system.guid);
                tmp = update(tmp, system);
                StoreApplication app = appService.getApplication(system.application);
                if (app != null) {
                    tmp.setApplication(app);
                    tmp = service.saveSystem(tmp);
                    tmp = service.getSystem(tmp.getGuid());
                    result = map(tmp);
                }
            }           
        }
        return result;
    }

    private List<ApplicationSystem> map(List<StoreSystem> tmp) {
        List<ApplicationSystem> result = null;
        if (tmp != null) {
            result = new ArrayList<>();
            for (StoreSystem item : tmp) {
                ApplicationSystem sys = map(item);
                if (sys != null) {
                    result.add(sys);
                }
            }
        }
        return result;
    }

    private ApplicationSystem map(StoreSystem tmp) {
        ApplicationSystem result = null;
        if (tmp != null) {
            result = new ApplicationSystem();
            result.guid = tmp.getGuid();
            result.name = tmp.getName();
            result.enabled = tmp.isEnabled();
            result.timer = tmp.isTimer();
            if (tmp.getApplication() != null) {
                result.application = tmp.getApplication().getGuid();
            }
        }
        return result;
    }

    private StoreSystem update(StoreSystem tmp, ApplicationSystem sys) {
        if (tmp != null && sys != null) {
            tmp.setName(sys.name);
            tmp.setEnabled(sys.enabled);
            tmp.setTimer(sys.timer);            
        }
        return tmp;
    }
    
}
