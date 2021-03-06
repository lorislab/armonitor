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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.lorislab.armonitor.store.criteria.StoreApplicationCriteria;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreApplication_;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreBuild_;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.store.model.StoreProject_;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.store.model.StoreSystem_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 * The application service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreApplicationServiceBean extends AbstractEntityServiceBean<StoreApplication> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -6510343941028532559L;

    /**
     * Deletes the application.
     *
     * @param guid the GUID.
     * @return <code>true</code> if the application was deleted.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteApplication(String guid) {
        return this.delete(guid);
    }

    /**
     * Saves the application.
     *
     * @param application the application.
     * @return the saved application.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreApplication saveApplication(StoreApplication application) {
        StoreApplication tmp = this.save(application);
        return getApplication(tmp.getGuid());
    }

    /**
     * Gets the application by GUID.
     *
     * @param guid the application GUID.
     * @return the corresponding application.
     */
    public StoreApplication getApplication(String guid) {
        StoreApplicationCriteria criteria = new StoreApplicationCriteria();
        criteria.setGuid(guid);
        return this.getById(guid);
    }

    /**
     * Gets the application by application criteria.
     *
     * @param criteria the application criteria.
     * @return the corresponding application.
     */
    public StoreApplication getApplication(StoreApplicationCriteria criteria) {
        StoreApplication result = null;
        List<StoreApplication> tmp = getApplications(criteria);
        if (tmp != null && !tmp.isEmpty()) {
            result = tmp.get(0);
        }
        return result;
    }

    /**
     * Gets the list of all applications.
     *
     * @return the list of all applications.
     */
    public List<StoreApplication> getApplications() {
        StoreApplicationCriteria criteria = new StoreApplicationCriteria();
        return getApplications(criteria);
    }

    /**
     * Gets the application object for the deployment the build on the system.
     *
     * @param system the system GUID.
     * @param build the build GUID.
     * 
     * @return the corresponding application.
     */
    public StoreApplication getApplicationForDeployment(String system, String build) {
        StoreApplication result = null;

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreApplication> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreApplication> root = cq.from(StoreApplication.class);

        cq.distinct(true);

        List<Predicate> predicates = new ArrayList<>();
        root.fetch(StoreApplication_.project, JoinType.LEFT);
        
        Join<StoreApplication, StoreBuild> fb = (Join<StoreApplication, StoreBuild>)  root.fetch(StoreApplication_.builds, JoinType.LEFT);
        predicates.add(cb.equal(fb.get(StoreBuild_.guid), build));                

        Join<StoreApplication, StoreSystem> fs = (Join<StoreApplication, StoreSystem>) root.fetch(StoreApplication_.systems, JoinType.LEFT);
        predicates.add(cb.equal(fs.get(StoreSystem_.guid), system));

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        try {
            TypedQuery<StoreApplication> typeQuery = getBaseEAO().createTypedQuery(cq);
            List<StoreApplication> tmp = typeQuery.getResultList();
            if (tmp != null && !tmp.isEmpty()) {
                result = tmp.get(0);
            }
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }
    
    /**
     * Gets the application object for the deployment list.
     *
     * @param system the system GUID.
     * @return the corresponding application.
     */
    public StoreApplication getApplicationForDeployment(String system) {
        StoreApplication result = null;

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreApplication> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreApplication> root = cq.from(StoreApplication.class);

        cq.distinct(true);

        List<Predicate> predicates = new ArrayList<>();

        root.fetch(StoreApplication_.builds, JoinType.LEFT);
        root.fetch(StoreApplication_.project, JoinType.LEFT);

        Join<StoreApplication, StoreSystem> fs = (Join<StoreApplication, StoreSystem>) root.fetch(StoreApplication_.systems, JoinType.LEFT);
        predicates.add(cb.equal(fs.get(StoreSystem_.guid), system));

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        try {
            TypedQuery<StoreApplication> typeQuery = getBaseEAO().createTypedQuery(cq);
            List<StoreApplication> tmp = typeQuery.getResultList();
            if (tmp != null && !tmp.isEmpty()) {
                result = tmp.get(0);
            }
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }

    /**
     * Gets the applications by application criteria.
     *
     * @param criteria the application criteria.
     * @return the corresponding list of application.
     */
    public List<StoreApplication> getApplications(StoreApplicationCriteria criteria) {
        List<StoreApplication> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreApplication> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreApplication> root = cq.from(StoreApplication.class);

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.isFetchSCM()) {
            root.fetch(StoreApplication_.scm, JoinType.LEFT);
        }

        if (criteria.isFetchProject()) {
            Fetch<StoreApplication, StoreProject> pf = root.fetch(StoreApplication_.project, JoinType.LEFT);

            if (criteria.isFetchProjectBts()) {
                pf.fetch(StoreProject_.bts, JoinType.LEFT);
            }
        }

        if (criteria.isFetchSystem()) {
            root.fetch(StoreApplication_.systems, JoinType.LEFT);
        }

        if (criteria.isFetchBuilds()) {
            Fetch<StoreApplication, StoreBuild> fb = root.fetch(StoreApplication_.builds, JoinType.LEFT);

            if (criteria.getFetchBuildsVersion() != null) {
                Join<StoreApplication, StoreBuild> jb = (Join<StoreApplication, StoreBuild>) fb;
                predicates.add(cb.equal(jb.get(StoreBuild_.mavenVersion), criteria.getFetchBuildsVersion()));
            }
        }

        if (criteria.getGuid() != null) {
            predicates.add(cb.equal(root.get(StoreApplication_.guid), criteria.getGuid()));
        }

        if (criteria.getKey() != null) {
            predicates.add(cb.equal(root.get(StoreApplication_.key), criteria.getKey()));
        }

        if (criteria.getBuild() != null) {
            predicates.add(cb.in(root.join(StoreApplication_.builds).get(StoreBuild_.guid)).value(criteria.getBuild()));
        }

        if (criteria.isEnabled() != null) {
            predicates.add(cb.equal(root.get(StoreApplication_.enabled), criteria.isEnabled()));
        }

        if (criteria.getProjects() != null && !criteria.getProjects().isEmpty()) {
            predicates.add(root.get(StoreApplication_.project).in(criteria.getProjects()));
        }

        if (criteria.getSystem() != null) {
            predicates.add(cb.in(root.join(StoreApplication_.systems).get(StoreSystem_.guid)).value(criteria.getSystem()));
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
