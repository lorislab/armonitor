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

package org.lorislab.armonitor.store.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.store.model.StoreBTSystem;
import org.lorislab.armonitor.store.model.StoreSCMSystem;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreSCMSystemServiceBean extends AbstractEntityServiceBean<StoreSCMSystem> {
    
    private static final long serialVersionUID = -1543513660078529228L;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreSCMSystem saveSCMSystem(StoreSCMSystem system) {
        return this.save(system);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteSCMSystem(String guid) {
        return this.delete(this);
    }
    
    public StoreSCMSystem getSCMSystem(String guid) {
        return getById(guid);
    }
    
    public List<StoreSCMSystem> getSCMSystems() {
        return getAll();
    }
}
