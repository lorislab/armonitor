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
import org.lorislab.armonitor.store.criteria.StoreApplicationCriteria;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreApplication_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreApplicationServiceBean extends AbstractEntityServiceBean<StoreApplication> {
    
    private static final long serialVersionUID = -6510343941028532559L;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreApplication saveApplication(StoreApplication application) {
        return this.save(application);
    }
    
    public StoreApplication getApplication(String guid) {
        return this.getById(guid);
    }
    
    public List<StoreApplication> getApplications() {
        return this.getAll();
    }

    public List<StoreApplication> getApplications(StoreApplicationCriteria criteria) {
        List<StoreApplication> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreApplication> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreApplication> root = cq.from(StoreApplication.class);

        List<Predicate> predicates = new ArrayList<>();
        if (criteria.isEnabled() != null) {
            predicates.add(cb.equal(root.get(StoreApplication_.enabled), criteria.isEnabled()));
        }

        if (criteria.getProjects() != null && !criteria.getProjects().isEmpty()) {
            predicates.add(root.get(StoreApplication_.project).in(criteria.getProjects()));
        }
        
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        try {
            TypedQuery<StoreApplication> typeQuery = getBaseEAO().createTypedQuery(cq);
            result = typeQuery.getResultList();
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }    
}
