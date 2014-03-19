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
import org.lorislab.armonitor.store.criteria.StoreBTSystemCriteria;
import org.lorislab.armonitor.store.model.StoreBTSystem;
import org.lorislab.armonitor.store.model.StoreBTSystem_;
import org.lorislab.armonitor.store.model.StoreProject_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 * The bug tracking system service.
 * 
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreBTSystemServiceBean extends AbstractEntityServiceBean<StoreBTSystem> {
    /**
     * The UID for this class.
     */    
    private static final long serialVersionUID = -1543513660078529228L;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreBTSystem saveBTSystem(StoreBTSystem system) {
        return this.save(system);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteBTSystem(String guid) {
        return this.delete(guid);
    }
    
    public StoreBTSystem getBTSystem(String guid) {
        return getById(guid);
    }
    
    public StoreBTSystem getBTSystem(StoreBTSystemCriteria criteria) {
        List<StoreBTSystem> tmp = getBTSystems(criteria);
        if (tmp != null && !tmp.isEmpty()) {
            return tmp.get(0);
        }
        return null;
    }
    
    public List<StoreBTSystem> getBTSystems() {
        return getBTSystems(new StoreBTSystemCriteria());
    }
    
    /**
     * Gets the list of bug tracking systems by criteria.
     *
     * @param criteria the criteria.
     * @return the corresponding list of bug tracking systems.
     */
    public List<StoreBTSystem> getBTSystems(StoreBTSystemCriteria criteria) {
        List<StoreBTSystem> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreBTSystem> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreBTSystem> root = cq.from(StoreBTSystem.class);

        if (criteria.isFetchProject()) {
            root.fetch(StoreBTSystem_.projects, JoinType.LEFT);
        }

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getGuid() != null) {
            predicates.add(cb.equal(root.get(StoreBTSystem_.guid), criteria.getGuid()));
        }

        if (criteria.getProject() != null) {
            predicates.add(cb.in(root.join(StoreBTSystem_.projects).get(StoreProject_.guid)).value(criteria.getProject()));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        try {
            TypedQuery<StoreBTSystem> typeQuery = getBaseEAO().createTypedQuery(cq);
            result = typeQuery.getResultList();
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }    
}
