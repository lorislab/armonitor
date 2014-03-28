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
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.lorislab.armonitor.store.criteria.StoreBuildCriteria;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreBuild_;
import org.lorislab.armonitor.store.model.StoreApplication_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 * The build service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreBuildServiceBean extends AbstractEntityServiceBean<StoreBuild> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 8403472044970132117L;

    /**
     * Saves the store build.
     *
     * @param data the build.
     * @return the saved build.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreBuild saveBuild(StoreBuild data) {
        return this.save(data);
    }

    /**
     * Gets the build by GUID.
     *
     * @param guid the GUID.
     * @return the corresponding build.
     */
    public StoreBuild getBuild(String guid) {
        StoreBuildCriteria criteria = new StoreBuildCriteria();
        criteria.setGuid(guid);
        return getBuild(criteria);
    }

    /**
     * Gets the build by criteria.
     *
     * @param criteria the criteria.
     * @return the corresponding build.
     */
    public StoreBuild getBuild(StoreBuildCriteria criteria) {
        StoreBuild result = null;
        List<StoreBuild> tmp = getBuilds(criteria);
        if (tmp != null && !tmp.isEmpty()) {
            result = tmp.get(0);
        }
        return result;
    }

    /**
     * Gets all builds.
     *
     * @return the list of all builds.
     */
    public List<StoreBuild> getBuilds() {
        return getBuilds(new StoreBuildCriteria());
    }

    /**
     * Gets the builds by criteria.
     *
     * @param criteria the criteria.
     * @return the corresponding list of criteria.
     */
    public List<StoreBuild> getBuilds(StoreBuildCriteria criteria) {
        List<StoreBuild> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreBuild> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreBuild> root = cq.from(StoreBuild.class);

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.isFetchParameters()) {
            root.fetch(StoreBuild_.parameters, JoinType.LEFT);
        }

        if (criteria.isFetchApplication()) {
            Fetch<StoreBuild, StoreApplication> af = root.fetch(StoreBuild_.application, JoinType.LEFT);            
            
            if (criteria.isFetchApplicationProject()) {
                af.fetch(StoreApplication_.project, JoinType.LEFT);
            }
        }

        if (criteria.getMavenVersion() != null) {
            predicates.add(cb.equal(root.get(StoreBuild_.mavenVersion), criteria.getMavenVersion()));
        }

        if (criteria.getApplication() != null) {
            predicates.add(cb.equal(root.get(StoreBuild_.application).get(StoreApplication_.guid), criteria.getApplication()));
        }

        if (criteria.getGuid() != null) {
            predicates.add(cb.equal(root.get(StoreBuild_.guid), criteria.getGuid()));
        }

        if (criteria.getAgent() != null) {
            predicates.add(cb.equal(root.get(StoreBuild_.agent), criteria.getAgent()));
        }

        if (criteria.getDate() != null) {
            predicates.add(cb.equal(root.get(StoreBuild_.date), criteria.getDate()));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        List<Order> orders = new ArrayList<>();
        
        if (criteria.getOrderByDate() != null) {
            if (criteria.getOrderByDate()) {
                orders.add( cb.asc(root.get(StoreBuild_.date)));
            } else {
                orders.add( cb.desc(root.get(StoreBuild_.date)));
            }
        }
        
        if (!orders.isEmpty()) {
            cq.orderBy(orders);
        }
        
        try {
            TypedQuery<StoreBuild> typeQuery = getBaseEAO().createTypedQuery(cq);
            result = typeQuery.getResultList();
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }
}
