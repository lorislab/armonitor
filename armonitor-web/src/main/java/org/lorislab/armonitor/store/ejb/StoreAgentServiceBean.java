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
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.lorislab.armonitor.store.criteria.StoreAgentCriteria;
import org.lorislab.armonitor.store.model.StoreAgent;
import org.lorislab.armonitor.store.model.StoreAgent_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreAgentServiceBean extends AbstractEntityServiceBean<StoreAgent> {
    
    private static final long serialVersionUID = -6750263259636685498L;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreAgent saveAgent(StoreAgent agent) {
        return this.save(agent);
    }
    
    public StoreAgent getAgent(String guid) {
        return this.getById(guid);
    }
    
    public List<StoreAgent> getAgents() {
        return this.getAll();
    }

    public List<StoreAgent> getAgents(StoreAgentCriteria criteria) {
        List<StoreAgent> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreAgent> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreAgent> root = cq.from(StoreAgent.class);

        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getSystem() != null) {
            predicates.add(root.get(StoreAgent_.system).in(criteria.getSystem()));
        }

        if (criteria.isTimer() != null) {
            predicates.add(cb.equal(root.get(StoreAgent_.timer),criteria.isTimer()));
        }
        
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        try {
            TypedQuery<StoreAgent> typeQuery = getBaseEAO().createTypedQuery(cq);
            result = typeQuery.getResultList();
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }      
}
