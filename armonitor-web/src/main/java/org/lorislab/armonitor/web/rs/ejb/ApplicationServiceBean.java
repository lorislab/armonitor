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
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.web.rs.model.Application;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ApplicationServiceBean {
    
    @EJB
    private StoreApplicationServiceBean service;
    
    
    public Application create() throws Exception {
        StoreApplication tmp = new StoreApplication();
        return map(tmp);
    }
    
    public Application get(String guid) throws Exception {
        StoreApplication tmp = service.getApplication(guid);
        return map(tmp);
    }
    
    public List<Application> get() throws Exception {
        List<StoreApplication> tmp = service.getApplications();
        return map(tmp);
    }
    
    public Application save(Application app) throws Exception {
        Application result;
        StoreApplication tmp = service.getApplication(app.guid);
        if (tmp != null) {
            tmp = update(tmp, app);
        } else {
            tmp = new StoreApplication();
            tmp.setGuid(app.guid);
            tmp = update(tmp, app);
        }
        tmp = service.saveApplication(tmp);
        tmp = service.getApplication(tmp.getGuid());
        result = map(tmp);
        return result;
    }
    
    private List<Application> map(List<StoreApplication> tmp) {
        List<Application> result = null;
        if (tmp != null) {
            result = new ArrayList<>();
            for (StoreApplication item : tmp) {
                Application agent = map(item);
                if (agent != null) {
                    result.add(agent);
                }
            }
        }
        return result;
    }
    
    private StoreApplication update(StoreApplication tmp, Application app) {
        if (tmp != null) {
            tmp.setName(app.name);
        }
        return tmp;
    }
    
    private Application map(StoreApplication app) {
        Application result = null;
        if (app != null) {
            result = new Application();
            result.guid = app.getGuid();
            result.name = app.getName();
        }
        return result;
    }
}
