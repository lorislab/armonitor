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
import org.lorislab.armonitor.store.model.StorePassword;
import org.lorislab.armonitor.store.model.StorePassword_;
import org.lorislab.armonitor.store.model.StoreUser_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 * The password service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StorePasswordServiceBean extends AbstractEntityServiceBean<StorePassword> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 1L;

    public StorePassword getPassword(String guid) {
        return this.getById(guid);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StorePassword savePassword(StorePassword password) {
        return this.save(password);
    }

    public StorePassword getPasswordForUser(String guid) {
        StorePassword result = null;

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<StorePassword> cq = getBaseEAO().createCriteriaQuery();
        Root<StorePassword> root = cq.from(StorePassword.class);
        
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.join(StorePassword_.user).get(StoreUser_.guid), guid));

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        try {
            TypedQuery<StorePassword> typeQuery = getBaseEAO().createTypedQuery(cq);
            List<StorePassword> tmp = typeQuery.getResultList();
            if (tmp != null && !tmp.isEmpty()) {
                result = tmp.get(0);
            }
        } catch (NoResultException ex) {
            // do nothing
        }
        return result;
    }
}
