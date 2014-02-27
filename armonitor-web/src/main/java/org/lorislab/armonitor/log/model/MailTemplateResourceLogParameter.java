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

package org.lorislab.armonitor.log.model;

import java.util.ArrayList;
import java.util.List;
import org.lorislab.armonitor.mail.model.MailTemplateResource;
import org.lorislab.jel.log.parameters.ClassLogParameter;

/**
 * The mail template resource parameter.
 * 
 * @author Andrej Petras
 */
public class MailTemplateResourceLogParameter implements ClassLogParameter {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Class<?>> getClasses() {
        List<Class<?>> result = new ArrayList<>();
        result.add(MailTemplateResource.class);
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
        MailTemplateResource res = (MailTemplateResource) parameter;
        return parameter.getClass().getSimpleName() + ":" + res.getName();
    }
}
