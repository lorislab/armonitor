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
import org.lorislab.armonitor.store.criteria.StoreSystemCriteria;
import org.lorislab.armonitor.store.model.StoreApplication_;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.store.model.StoreSystem_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 * The system service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreSystemServiceBean extends AbstractEntityServiceBean<StoreSystem> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -9106271336827485594L;

    /**
     * Saves the system.
     *
     * @param system the system.
     * @return the saved system.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreSystem saveSystem(StoreSystem system) {
        return this.save(system);
    }

    /**
     * Gets the system by GUID.
     *
     * @param guid the GUID.
     * @return the corresponding system.
     */
    public StoreSystem getSystem(String guid) {
        StoreSystemCriteria criteria = new StoreSystemCriteria();
        criteria.setGuid(guid);
        return getSystem(criteria);
    }

    /**
     * Gets the system by criteria.
     *
     * @param criteria the criteria.
     * @return the corresponding system.
     */
    public StoreSystem getSystem(StoreSystemCriteria criteria) {
        StoreSystem result = null;
        List<StoreSystem> tmp = getSystems(criteria);
        if (tmp != null && !tmp.isEmpty()) {
            result = tmp.get(0);
        }
        return result;
    }

    /**
     * Gets the list of all systems.
     *
     * @return the list of all systems.
     */
    public List<StoreSystem> getSystems() {
        return getSystems(new StoreSystemCriteria());
    }

    /**
     * Gets the list of systems by criteria.
     *
     * @param criteria the criteria.
     * @return the list of systems corresponding to the criteria.
     */
    public List<StoreSystem> getSystems(StoreSystemCriteria criteria) {
        List<StoreSystem> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreSystem> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreSystem> root = cq.from(StoreSystem.class);

        if (criteria.isFetchAgent()) {
            root.fetch(StoreSystem_.agent, JoinType.LEFT);
        }

        if (criteria.isFetchApplication()) {
            root.fetch(StoreSystem_.application, JoinType.LEFT);
        }

        if (criteria.isFetchRoles()) {
            root.fetch(StoreSystem_.roles, JoinType.LEFT);
        }

        List<Predicate> predicates = new ArrayList<>();
        if (criteria.isEnabled() != null) {
            predicates.add(cb.equal(root.get(StoreSystem_.enabled), criteria.isEnabled()));
        }

        if (criteria.getGuid() != null) {
            predicates.add(cb.equal(root.get(StoreSystem_.guid), criteria.getGuid()));
        }

        if (criteria.getKey() != null) {
            predicates.add(cb.equal(root.get(StoreSystem_.key), criteria.getKey()));
        }
        
        if (criteria.isTimer() != null) {
            predicates.add(cb.equal(root.get(StoreSystem_.timer), criteria.isTimer()));
        }

        if (criteria.getApplications() != null && !criteria.getApplications().isEmpty()) {
            predicates.add(root.get(StoreSystem_.application).get(StoreApplication_.guid).in(criteria.getApplications()));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        try {
            TypedQuery<StoreSystem> typeQuery = getBaseEAO().createTypedQuery(cq);
            result = typeQuery.getResultList();
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }
}
