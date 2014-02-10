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
import org.lorislab.armonitor.store.model.StoreSCMSystem;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 * The SCM system service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreSCMSystemServiceBean extends AbstractEntityServiceBean<StoreSCMSystem> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -1543513660078529228L;

    /**
     * Saves the SCM system.
     *
     * @param system the SCM system.
     * @return the saved SCM system.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreSCMSystem saveSCMSystem(StoreSCMSystem system) {
        return this.save(system);
    }

    /**
     * Deletes the SCM system.
     *
     * @param guid the GUID.
     * @return <code>true</code> if the system was deleted.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteSCMSystem(String guid) {
        return this.delete(this);
    }

    /**
     * Gets the SCM system by GUID.
     *
     * @param guid the GUID.
     * @return the corresponding SCM system.
     */
    public StoreSCMSystem getSCMSystem(String guid) {
        return getById(guid);
    }

    /**
     * Gets list of all SCM systems.
     *
     * @return list of all SCM systems.
     */
    public List<StoreSCMSystem> getSCMSystems() {
        return getAll();
    }
}
