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

package org.lorislab.armonitor.log.service;

import java.util.ArrayList;
import java.util.List;
import org.lorislab.armonitor.log.model.MailLogParameter;
import org.lorislab.armonitor.log.model.MailTemplateResourceLogParameter;
import org.lorislab.armonitor.log.model.MessageLogParameter;
import org.lorislab.jel.ejb.log.parameters.PersistentLogParameter;
import org.lorislab.jel.ejb.log.parameters.WrapperLogParameter;
import org.lorislab.jel.log.config.LogServiceConfiguration;
import org.lorislab.jel.log.context.ContextLogger;
import org.lorislab.jel.log.context.impl.DefaultContextLogger;
import org.lorislab.jel.log.parameters.ClassLogParameter;
import org.lorislab.jel.log.parameters.InstanceOfLogParameter;
import org.lorislab.jel.log.parameters.LogParameter;
import org.lorislab.jel.log.parameters.impl.BasicLogParamater;
import org.lorislab.jel.log.parameters.impl.CollectionLogParameter;
import org.lorislab.jel.log.parameters.impl.DefaultReflectionLogParameter;
import org.lorislab.jel.log.parameters.impl.EnumLogParamater;
import org.lorislab.jel.log.parameters.impl.MapLogParameter;

/**
 *
 * @author Andrej Petras
 */
public class DefaultLogServiceConfigurationImpl implements LogServiceConfiguration {

    private static final ContextLogger CONTEXT_LOG = new DefaultContextLogger();
    
    private static final LogParameter DEFAULT_PARAMETER = new DefaultReflectionLogParameter();
    
    private static final List<ClassLogParameter> CLASS_PARAM = new ArrayList<>();
    
    private static final List<InstanceOfLogParameter> INSTANCE_PARAM = new ArrayList<>();
    
    static {
        CLASS_PARAM.add(new BasicLogParamater());  
        
        INSTANCE_PARAM.add(new MapLogParameter());
        INSTANCE_PARAM.add(new EnumLogParamater());
        INSTANCE_PARAM.add(new CollectionLogParameter());
        INSTANCE_PARAM.add(new PersistentLogParameter());
        INSTANCE_PARAM.add(new WrapperLogParameter());
        INSTANCE_PARAM.add(new MessageLogParameter());
        
        INSTANCE_PARAM.add(new MailLogParameter());
        INSTANCE_PARAM.add(new MailTemplateResourceLogParameter());        
    }
    
    @Override
    public List<ClassLogParameter> getClassLogParameters() {
        return CLASS_PARAM;
    }

    @Override
    public List<InstanceOfLogParameter> getInstanceOfLogParameters() {
        return INSTANCE_PARAM;
    }

    @Override
    public LogParameter getDefaultLogParameter() {
        return DEFAULT_PARAMETER;
    }

    @Override
    public ContextLogger getContextLogger() {
        return CONTEXT_LOG;
    }
    
}
