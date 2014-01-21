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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mapper.Mapper;
import org.lorislab.armonitor.store.ejb.StoreApplicationServiceBean;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.web.rs.model.Application;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ApplicationServiceBean {
    
    @EJB
    private StoreApplicationServiceBean service;
    
    
    public Application create() throws Exception {
        return Mapper.create(StoreApplication.class, Application.class);
    }
    
    public Application get(String guid) throws Exception {
        StoreApplication tmp = service.getApplication(guid);
        return Mapper.map(tmp, Application.class);
    }
    
    public List<Application> get() throws Exception {
        List<StoreApplication> tmp = service.getApplications();
        return Mapper.map(tmp, Application.class);
    }
    
    public Application save(Application app) throws Exception {
        Application result;
        StoreApplication tmp = service.getApplication(app.guid);
        if (tmp != null) {
            tmp = Mapper.update(tmp, app);
        } else {
            tmp = Mapper.create(tmp, StoreApplication.class);
        }
        tmp = service.saveApplication(tmp);
        result = Mapper.map(tmp, Application.class);
        return result;
    }

}
