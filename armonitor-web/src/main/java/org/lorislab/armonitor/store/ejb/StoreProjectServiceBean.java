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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.lorislab.armonitor.store.criteria.StoreProjectCriteria;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreApplication_;
import org.lorislab.armonitor.store.model.StoreProject;
import org.lorislab.armonitor.store.model.StoreProject_;
import org.lorislab.armonitor.store.model.StoreSystem;
import org.lorislab.armonitor.store.model.StoreSystem_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 * The project service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StoreProjectServiceBean extends AbstractEntityServiceBean<StoreProject> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -4937927663216469945L;

    /**
     * Saves the project.
     *
     * @param project the project.
     * @return the saved project.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StoreProject saveProject(StoreProject project) {
        return this.save(project);
    }

    /**
     * Gets the project by GUID.
     *
     * @param guid the GUID.
     * @return the project corresponding to the GUID.
     */
    public StoreProject getProject(String guid) {
        StoreProjectCriteria criteria = new StoreProjectCriteria();
        criteria.setGuid(guid);
        return getProject(criteria);
    }

    /**
     * Gets the project by criteria.
     *
     * @param criteria the criteria.
     * @return the project corresponding to the criteria.
     */
    public StoreProject getProject(StoreProjectCriteria criteria) {
        List<StoreProject> tmp = getProjects(criteria);
        if (tmp != null && !tmp.isEmpty()) {
            return tmp.get(0);
        }
        return null;
    }

    /**
     * Gets the list of all projects.
     *
     * @return the list of all projects.
     */
    public List<StoreProject> getProjects() {
        return getProjects(new StoreProjectCriteria());
    }

    /**
     * Gets the projects list for the dashboard.
     *
     * @return the projects list for the dashboard.
     */
    public List<StoreProject> getDashboardProjects() {
        List<StoreProject> result = new ArrayList<>();

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StoreProject> cq = getBaseEAO().createCriteriaQuery();
        Root<StoreProject> root = cq.from(StoreProject.class);

        cq.distinct(true);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get(StoreProject_.enabled), true));

        Join<StoreProject, StoreApplication> applications = (Join<StoreProject, StoreApplication>) root.fetch(StoreProject_.applications, JoinType.LEFT);
        predicates.add(cb.equal(applications.get(StoreApplication_.enabled), true));

        Join<StoreApplication, StoreSystem> systems = (Join<StoreApplication, StoreSystem>) applications.fetch(StoreApplication_.systems, JoinType.LEFT);
        predicates.add(cb.equal(systems.get(StoreSystem_.enabled), true));

        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        try {
            TypedQuery<StoreProject> typeQuery = getBaseEAO().createTypedQuery(cq);
            result = typeQuery.getResultList();
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }

    /**
     * Gets the list of projects by the criteria.
     *
     * @param criteria the criteria.
     * @return the list of projects corresponding to the criteria.
     */
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
