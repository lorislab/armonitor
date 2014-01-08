/*
 * Copyright 2013 lorislab.org.
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
package org.lorislab.armonitor.config.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.lorislab.armonitor.config.model.Config;
import org.lorislab.armonitor.config.model.Config_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConfigServiceBean extends AbstractEntityServiceBean<Config> {

    private static final long serialVersionUID = 3429620108065122167L;

    public List<Config> getAllConfig() {
        return this.getAll();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Config saveConfig(Config config) {
        return this.save(config);
    }

    public Config getConfigByClass(String clazz) {
        Config result = null;

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<Config> cq = getBaseEAO().createCriteriaQuery();
        Root<Config> root = cq.from(Config.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get(Config_.clazz), clazz));

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        TypedQuery<Config> query = getBaseEAO().createTypedQuery(cq);
        List<Config> g = query.getResultList();
        if (!g.isEmpty()) {
            result = g.get(0);
        }

        return result;
    }
}
