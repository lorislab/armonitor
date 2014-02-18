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
import java.util.Date;
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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.lorislab.armonitor.store.criteria.StoreSystemBuildCriteria;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreBuild_;
import org.lorislab.armonitor.store.model.StoreSystemBuild;
import org.lorislab.armonitor.store.model.StoreSystemBuild_;
import org.lorislab.armonitor.store.model.StoreSystem_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 * The system build service.
 * 
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreSystemBuildServiceBean extends AbstractEntityServiceBean<StoreSystemBuild> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 3658813042535292506L;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreSystemBuild saveSystemBuild(StoreSystemBuild data) {
        return this.save(data);
    }

    public StoreSystemBuild getSystemBuild(String guid) {
        StoreSystemBuildCriteria criteria = new StoreSystemBuildCriteria();
        criteria.setGuid(guid);
        return this.getById(guid);
    }

    public StoreSystemBuild getSystemBuild(StoreSystemBuildCriteria criteria) {
        StoreSystemBuild result = null;
        List<StoreSystemBuild> tmp = getSystemBuilds(criteria);
        if (tmp != null && !tmp.isEmpty()) {
            result = tmp.get(0);
        }
        return result;
    }

    public List<StoreSystemBuild> getSystemBuilds() {
        return getSystemBuilds(new StoreSystemBuildCriteria());
    }

    public List<StoreSystemBuild> getSystemBuilds(StoreSystemBuildCriteria criteria) {
        List<StoreSystemBuild> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreSystemBuild> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreSystemBuild> root = cq.from(StoreSystemBuild.class);

        if (criteria.isFetchBuild()) {
            Fetch<StoreSystemBuild, StoreBuild> bf = root.fetch(StoreSystemBuild_.build, JoinType.LEFT);
            
            if (criteria.isFetchBuildParam()) {
                bf.fetch(StoreBuild_.parameters, JoinType.LEFT);
            }
        }

        if (criteria.isFetchSystem()) {
            root.fetch(StoreSystemBuild_.system, JoinType.LEFT);
        }

        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getGuid() != null) {            
            predicates.add(cb.equal(root.get(StoreSystemBuild_.guid), criteria.getGuid()));
        }

        if (criteria.isMaxDate()) {
            Subquery<Date> sq = cq.subquery(Date.class);
            Root<StoreSystemBuild> ssb = sq.from(StoreSystemBuild.class);
            sq.select(cb.greatest(ssb.get(StoreSystemBuild_.date)))
                    .where(
                            cb.equal(
                                    ssb.get(StoreSystemBuild_.system).get(StoreSystem_.guid),
                                    root.get(StoreSystemBuild_.system).get(StoreSystem_.guid)
                            )
                    );
            predicates.add(cb.equal(root.get(StoreSystemBuild_.date), sq));
        }
        
        if (criteria.getSystems() != null && !criteria.getSystems().isEmpty()) {
            predicates.add(root.get(StoreSystemBuild_.system).get(StoreSystem_.guid).in(criteria.getSystems()));
        }
        
        if (criteria.getSystem() != null) {
            predicates.add(cb.equal(root.get(StoreSystemBuild_.system).get(StoreSystem_.guid), criteria.getSystem()));
        }

        if (criteria.getBuild() != null) {
            predicates.add(cb.equal(root.get(StoreSystemBuild_.build).get(StoreBuild_.guid), criteria.getBuild()));
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
