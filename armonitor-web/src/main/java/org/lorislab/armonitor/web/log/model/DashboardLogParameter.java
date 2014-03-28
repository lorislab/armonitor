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

package org.lorislab.armonitor.web.log.model;

import java.util.ArrayList;
import java.util.List;
import org.kohsuke.MetaInfServices;
import org.lorislab.armonitor.web.rs.model.Dashboard;
import org.lorislab.jel.log.parameters.ClassLogParameter;

/**
 * The dashboard log parameter.
 * 
 * @author Andrej Petras
 */
@MetaInfServices
public class DashboardLogParameter implements ClassLogParameter {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Class<?>> getClasses() {
        List<Class<?>> result = new ArrayList<>();
        result.add(Dashboard.class);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isResult() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getObject(Object parameter) {   
        Dashboard tmp = (Dashboard) parameter;
        return parameter.getClass().getSimpleName() + "[date:" + tmp.date + ",size:" + tmp.size + "]";
    }
}