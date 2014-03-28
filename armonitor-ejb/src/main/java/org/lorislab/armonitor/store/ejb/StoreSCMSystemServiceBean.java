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

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.lorislab.armonitor.store.criteria.StoreSCMSystemCriteria;
import org.lorislab.armonitor.store.model.StoreApplication_;
import org.lorislab.armonitor.store.model.StoreProject_;
import org.lorislab.armonitor.store.model.StoreSCMSystem;
import org.lorislab.armonitor.store.model.StoreSCMSystem_;
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
        return this.delete(guid);
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
     * Gets the SCM systems by criteria.
     *
     * @param criteria the criteria.
     * @return the corresponding SCM systems.
     */    
    public StoreSCMSystem getSCMSystem(StoreSCMSystemCriteria criteria) {
        List<StoreSCMSystem> tmp = getSCMSystems(criteria);
        if (tmp != null && !tmp.isEmpty()) {
            return tmp.get(0);
        }
        return null;
    }
    
    /**
     * Gets list of all SCM systems.
     *
     * @return list of all SCM systems.
     */
    public List<StoreSCMSystem> getSCMSystems() {
        return getSCMSystems(new StoreSCMSystemCriteria());
    }

    /**
     * Gets the list of SCM systems by criteria.
     *
     * @param criteria the criteria.
     * @return the corresponding list of SCM systems.
     */
    public List<StoreSCMSystem> getSCMSystems(StoreSCMSystemCriteria criteria) {
        List<StoreSCMSystem> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreSCMSystem> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreSCMSystem> root = cq.from(StoreSCMSystem.class);

        if (criteria.isFetchApplication()) {
            root.fetch(StoreSCMSystem_.applications, JoinType.LEFT);
        }

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getGuid() != null) {
            predicates.add(cb.equal(root.get(StoreProject_.guid), criteria.getGuid()));
        }

        if (criteria.getApplication() != null) {
            predicates.add(cb.in(root.join(StoreSCMSystem_.applications).get(StoreApplication_.guid)).value(criteria.getApplication()));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        try {
            TypedQuery<StoreSCMSystem> typeQuery = getBaseEAO().createTypedQuery(cq);
            result = typeQuery.getResultList();
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }
}
