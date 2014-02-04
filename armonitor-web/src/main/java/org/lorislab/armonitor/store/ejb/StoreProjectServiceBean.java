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
import org.lorislab.armonitor.store.criteria.StoreProjectCriteria;
import org.lorislab.armonitor.store.model.StoreApplication_;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.store.model.StoreProject_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreProjectServiceBean extends AbstractEntityServiceBean<StoreProject> {

    private static final long serialVersionUID = -4937927663216469945L;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreProject saveProject(StoreProject project) {
        return this.save(project);
    }

    public StoreProject getProject(String guid) {
        StoreProjectCriteria criteria = new StoreProjectCriteria();
        criteria.setGuid(guid);
        return getProject(criteria);
    }

    public StoreProject getProject(StoreProjectCriteria criteria) {
       List<StoreProject> tmp = getProjects(criteria);
       if (tmp != null && !tmp.isEmpty()) {
           return tmp.get(0);
       }
       return  null;
    }

    public List<StoreProject> getProjects() {
        return getProjects(new StoreProjectCriteria());
    }

    public List<StoreProject> getProjects(StoreProjectCriteria criteria) {
        List<StoreProject> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreProject> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreProject> root = cq.from(StoreProject.class);

        if (criteria.isFetchApplication()) {
            root.fetch(StoreProject_.applications, JoinType.LEFT);
        }        
        
        if (criteria.isFetchBTS()) {
            root.fetch(StoreProject_.bts, JoinType.LEFT);
        } 
        
        List<Predicate> predicates = new ArrayList<>();
        if (criteria.isEnabled() != null) {
            predicates.add(cb.equal(root.get(StoreProject_.enabled), criteria.isEnabled()));
        }

        if (criteria.getGuid() != null) {
            predicates.add(cb.equal(root.get(StoreProject_.guid), criteria.getGuid()));
        }
        
        if (criteria.getApplication() != null) {
            predicates.add(cb.in(root.join(StoreProject_.applications).get(StoreApplication_.guid)).value(criteria.getApplication()));
        }
        
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        try {
            TypedQuery<StoreProject> typeQuery = getBaseEAO().createTypedQuery(cq);
            result = typeQuery.getResultList();
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }
}
