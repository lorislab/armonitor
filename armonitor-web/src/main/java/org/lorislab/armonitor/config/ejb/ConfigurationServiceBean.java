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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.codehaus.jackson.map.ObjectMapper;
import org.lorislab.armonitor.config.model.Attribute;
import org.lorislab.armonitor.config.model.Config;

/**
 *
 * @author Andrej Petras
 */
@Singleton
@Local(ConfigurationServiceLocal.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConfigurationServiceBean implements ConfigurationServiceLocal {

    @EJB
    private ConfigServiceLocal service;

    private Map<Class, Object> cache = new HashMap<>();

    @PostConstruct
    @Override
    public void start() {
        reload();
    }

    @Override
    public void reload() {
        cache = new HashMap<>();
        List<Config> configs = service.getAllConfig();
        if (configs != null) {
            for (Config config : configs) {
                Object tmp = loadConfiguration(config);
                cache.put(tmp.getClass(), tmp);
            }
        }
    }

    private Object loadConfiguration(Config config) {
        Object result = createInstance(config.getClazz());
        if (result != null) {
            if (config.getAttributes() != null) {
                ObjectMapper mapper = new ObjectMapper();
                for (Attribute attribute : config.getAttributes().values()) {
                    setAttributeToObject(mapper, result, attribute);
                }
            }
        }
        return result;
    }

    private <T> T saveConfiguration(T data) {
        Class clazz = data.getClass();
        Config config = service.getConfigByClass(clazz.getName());
        if (config == null) {
            config = new Config();
            config.setClazz(clazz.getName());
        }

        Map<String, Attribute> attributes = config.getAttributes();

        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {

            ObjectMapper mapper = new ObjectMapper();

            for (Field field : fields) {

                Attribute attr = attributes.get(field.getName());
                if (attr == null) {
                    attr = createAttribute(mapper, data, field);
                    attr.setConfig(config);
                    attributes.put(field.getName(), attr);
                } else {
                    updateAttribute(mapper, data, attr, field);
                }
            }
        }

        Config tmp = service.saveConfig(config);

        return data;
    }

    @Override
    public <T> T setConfiguration(T data) {
        T result = null;
        if (data != null) {
            Class clazz = data.getClass();
            result = (T) saveConfiguration(data);
            cache.put(clazz, result);
        }
        return result;
    }

    @Override
    public <T> T getConfiguration(Class<T> clazz) {
        T result = (T) cache.get(clazz);
        if (result == null) {
            result = (T) createInstance(clazz.getName());
            result = setConfiguration(result);
        }
        return result;
    }

    private void updateAttribute(ObjectMapper mapper, Object object, Attribute attribute, Field field) {
        if (attribute != null) {
            try {
                boolean accessible = field.isAccessible();
                try {
                    Object value = field.get(object);
                    field.setAccessible(true);
                    String tmp = mapper.writeValueAsString(value);
                    attribute.setValue(tmp);
                } finally {
                    field.setAccessible(accessible);
                }
            } catch (Exception ex) {
                Logger.getLogger(ConfigurationServiceBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Attribute createAttribute(ObjectMapper mapper, Object object, Field field) {
        Attribute result = new Attribute();
        try {
            result.setName(field.getName());
            boolean accessible = field.isAccessible();
            try {
                Object value = field.get(object);
                field.setAccessible(true);
                String tmp = mapper.writeValueAsString(value);
                result.setValue(tmp);
            } finally {
                field.setAccessible(accessible);
            }
        } catch (Exception ex) {
            Logger.getLogger(ConfigurationServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private <T> void setAttributeToObject(ObjectMapper mapper, T object, Attribute attribute) {
        try {
            String tmp = attribute.getValue();

            Class clazz = object.getClass();
            Field field = clazz.getDeclaredField(attribute.getName());
            Object value = mapper.readValue(tmp, field.getType());

            boolean accessible = field.isAccessible();
            try {
                field.setAccessible(true);
                field.set(object, value);
            } finally {
                field.setAccessible(accessible);
            }
        } catch (Exception ex) {
            Logger.getLogger(ConfigurationServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Object createInstance(String name) {
        Object result = null;
        try {
            Class clazz = Class.forName(name);
            result = clazz.newInstance();
        } catch (Exception ex) {
            Logger.getLogger(ConfigurationServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
