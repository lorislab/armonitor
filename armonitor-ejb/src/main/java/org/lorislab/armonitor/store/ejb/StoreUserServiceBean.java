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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import javax.persistence.criteria.Subquery;
import org.lorislab.armonitor.store.criteria.StoreUserCriteria;
import org.lorislab.armonitor.store.model.StoreRole;
import org.lorislab.armonitor.store.model.StoreRole_;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.store.model.StoreSystem_;
import org.lorislab.armonitor.store.model.StoreUser;
import org.lorislab.armonitor.store.model.StoreUser_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 * The user service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreUserServiceBean extends AbstractEntityServiceBean<StoreUser> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Deletes the user.
     *
     * @param guid the GUID.
     * @return <code>true</code> if the user was deleted.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteUser(String guid) {
        return this.delete(guid);
    }
    
    public StoreUser getUser(String guid) {
        return this.getById(guid);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreUser saveUser(StoreUser user) {
        return this.save(user);
    }

    public Set<StoreRole> getRoles(String guid) {
        StoreUserCriteria criteria = new StoreUserCriteria();
        criteria.setGuid(guid);
        criteria.setFetchRoles(true);
        StoreUser user = getUser(criteria);
        if (user != null) {
            return user.getRoles();
        }
        return null;
    }

    public StoreUser getUser(StoreUserCriteria criteria) {
        StoreUser result = null;
        List<StoreUser> tmp = getUsers(criteria);
        if (tmp != null && !tmp.isEmpty()) {
            result = tmp.get(0);
        }
        return result;
    }

    public List<StoreUser> getUsers() {
        return getUsers(new StoreUserCriteria());
    }

    public Set<StoreUser> getUsersEmailsForSystem(String system) {
        Set<StoreUser> result = new HashSet<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreUser> cq = getBaseEAO().createCriteriaQuery(StoreUser.class);
        Root<StoreUser> root = cq.from(StoreUser.class);

        Subquery<String> sq = cq.subquery(String.class);
        Root<StoreSystem> project = sq.from(StoreSystem.class);
        sq.select(project.join(StoreSystem_.roles).get(StoreRole_.guid)).where(cb.equal(project.get(StoreSystem_.guid), system));

        cq.where(cb.in(root.join(StoreUser_.roles).get(StoreRole_.guid)).value(sq));

        try {
            TypedQuery<StoreUser> typeQuery = getBaseEAO().createQuery(cq);
            List<StoreUser> tmp = typeQuery.getResultList();
            if (tmp != null) {
                result.addAll(tmp);
            }
        } catch (NoResultException ex) {
            // do nothing
        }

        return result;
    }

    public List<StoreUser> getUsers(StoreUserCriteria criteria) {
        List<StoreUser> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreUser> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreUser> root = cq.from(StoreUser.class);

        if (criteria.isFetchRoles()) {
            root.fetch(StoreUser_.roles, JoinType.LEFT);
        }

        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getName() != null) {
            predicates.add(cb.equal(root.get(StoreUser_.name), criteria.getName()));
        }

        if (criteria.getEmail() != null) {
            predicates.add(cb.equal(root.get(StoreUser_.email), criteria.getEmail()));
        }
        
        if (criteria.getGuid() != null) {
            predicates.add(cb.equal(root.get(StoreUser_.guid), criteria.getGuid()));
        }

        if (criteria.getSystem() != null) {
            Subquery<String> sq = cq.subquery(String.class);
            Root<StoreSystem> project = sq.from(StoreSystem.class);
            sq.select(project.join(StoreSystem_.roles).get(StoreRole_.guid)).where(cb.equal(project.get(StoreSystem_.guid), criteria.getSystem()));
            sq.distinct(true);
            predicates.add(cb.in(root.join(StoreUser_.roles).get(StoreRole_.guid)).value(sq));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        try {
            TypedQuery<StoreUser> typeQuery = getBaseEAO().createTypedQuery(cq);
            result = typeQuery.getResultList();
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }
}
