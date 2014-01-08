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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.lorislab.armonitor.store.criteria.StoreSystemBuildCriteria;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.store.model.StoreSystemBuild_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreSystemBuildServiceBean extends AbstractEntityServiceBean<StoreSystemBuild> {
    
    private static final long serialVersionUID = 3658813042535292506L;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreSystemBuild saveSystemBuild(StoreSystemBuild data) {
        return this.save(data);
    }
    
    public StoreSystemBuild getSystemBuild(String guid) {
        return this.getById(guid);
    }
    
    public List<StoreSystemBuild> getSystemBuilds() {
        return this.getAll();
    }

    public List<StoreSystemBuild> getSystemBuilds(StoreSystemBuildCriteria criteria) {
        List<StoreSystemBuild> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreSystemBuild> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreSystemBuild> root = cq.from(StoreSystemBuild.class);

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.isActive() != null) {
            // TODO:
        }
        
        if (criteria.getSystem() != null) {
            predicates.add(cb.equal(root.get(StoreSystemBuild_.system), criteria.getSystem()));
        }

        if (criteria.getBuild() != null) {
            predicates.add(cb.equal(root.get(StoreSystemBuild_.build), criteria.getBuild()));
        }
        
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        try {
            TypedQuery<StoreSystemBuild> typeQuery = getBaseEAO().createTypedQuery(cq);
            result = typeQuery.getResultList();
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }         
}
