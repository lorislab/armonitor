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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.lorislab.armonitor.store.criteria.StoreActivityCriteria;
import org.lorislab.armonitor.store.model.StoreActivity;
import org.lorislab.armonitor.store.model.StoreActivityChange;
import org.lorislab.armonitor.store.model.StoreActivityChange_;
import org.lorislab.armonitor.store.model.StoreActivityLog;
import org.lorislab.armonitor.store.model.StoreActivityLog_;
import org.lorislab.armonitor.store.model.StoreActivity_;
import org.lorislab.armonitor.store.model.StoreBuild_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 * The store activity service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreActivityServiceBean extends AbstractEntityServiceBean<StoreActivity> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -822365615541318435L;

    /**
     * Saves the activity.
     *
     * @param activity the activity.
     * @return the saved activity.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreActivity saveActivity(StoreActivity activity) {
        return this.save(activity);
    }

    /**
     * Gets the activity.
     *
     * @param guid the activity GUID.
     * @return the corresponding activity.
     */
    public StoreActivity getActivity(String guid) {
        StoreActivityCriteria criteria = new StoreActivityCriteria();
        criteria.setGuid(guid);
        return getActivity(criteria);
    }

    /**
     * Gets the activity.
     *
     * @param criteria the activity criteria.
     * @return the corresponding activity.
     */
    public StoreActivity getActivity(StoreActivityCriteria criteria) {
        StoreActivity result = null;
        List<StoreActivity> tmp = getActivities(criteria);
        if (tmp != null && !tmp.isEmpty()) {
            result = tmp.get(0);
        }
        return result;
    }

    /**
     * Gets the list of all activities.
     *
     * @return the list of all activities.
     */
    public List<StoreActivity> getActivities() {
        return getActivities(new StoreActivityCriteria());
    }

    /**
     * Get activities by activity criteria.
     *
     * @param criteria the activity criteria.
     * @return the corresponding activities.
     */
    public List<StoreActivity> getActivities(StoreActivityCriteria criteria) {
        List<StoreActivity> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreActivity> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreActivity> root = cq.from(StoreActivity.class);

        if (criteria.isFetchBuild()) {
            root.fetch(StoreActivity_.build, JoinType.LEFT);
        }
        
        if (criteria.isFetchChange()) {
            Fetch<StoreActivity, StoreActivityChange> chf = root.fetch(StoreActivity_.changes, JoinType.LEFT);

            if (criteria.isFetchChangeLog()) {
                Fetch<StoreActivityChange, StoreActivityLog> lf = chf.fetch(StoreActivityChange_.logs, JoinType.LEFT);
                
                if (criteria.isFetchChangeLogBuild()) {
                    lf.fetch(StoreActivityLog_.build, JoinType.LEFT);
                }                
            }
        }

        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getBuild()!= null) {
            predicates.add(cb.equal(root.join(StoreActivity_.build).get(StoreBuild_.guid), criteria.getBuild()));
        }

        if (criteria.getGuid() != null) {
            predicates.add(cb.equal(root.get(StoreActivity_.guid), criteria.getGuid()));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }
        try {
            TypedQuery<StoreActivity> typeQuery = getBaseEAO().createTypedQuery(cq);
            result = typeQuery.getResultList();
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }
}
