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

import org.kohsuke.MetaInfServices;
import org.lorislab.armonitor.mail.model.Mail;
import org.lorislab.jel.log.parameters.InstanceOfLogParameter;

/**
 * The mail log parameter.
 * 
 * @author Andrej Petras
 */
@MetaInfServices
public class MailLogParameter implements InstanceOfLogParameter {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean instanceOfClasses(Object parameter) {
        return parameter instanceof Mail;
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
        Mail mail = (Mail) parameter;
        return parameter.getClass().getSimpleName() + ":" + mail.getGuid();
    }
    
}
