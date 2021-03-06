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
import org.kohsuke.MetaInfServices;
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
 * The default log service configuration.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class DefaultLogServiceConfigurationImpl implements LogServiceConfiguration {

    /**
     * The default log parameter.
     */
    private static final LogParameter DEFAULT_PARAMETER = new DefaultReflectionLogParameter();

    /**
     * The list of class log parameters.
     */
    private static final List<ClassLogParameter> CLASS_PARAM = new ArrayList<>();

    /**
     * The list of instance log parameters.
     */
    private static final List<InstanceOfLogParameter> INSTANCE_PARAM = new ArrayList<>();

    /**
     * Set up the default configuration.
     */
    static {
        CLASS_PARAM.add(new BasicLogParamater());                           
        INSTANCE_PARAM.add(new MapLogParameter());
        INSTANCE_PARAM.add(new EnumLogParamater());
        INSTANCE_PARAM.add(new CollectionLogParameter());
        INSTANCE_PARAM.add(new PersistentLogParameter());
        INSTANCE_PARAM.add(new WrapperLogParameter());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClassLogParameter> getClassLogParameters() {
        return CLASS_PARAM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InstanceOfLogParameter> getInstanceOfLogParameters() {
        return INSTANCE_PARAM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogParameter getDefaultLogParameter() {
        return DEFAULT_PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContextLogger getContextLogger() {
        return null;
    }

}
