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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.lorislab.armonitor.config.model.Attribute;
import org.lorislab.armonitor.config.model.Config;

/**
 * The configuration service.
 *
 * @author Andrej Petras
 */
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConfigurationServiceBean {

    private static final Logger LOGGER = Logger.getLogger(ConfigurationServiceBean.class.getName());

    private static final ResteasyProviderFactory RPF = ResteasyProviderFactory.getInstance();
    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON_TYPE;

    /**
     * The configuration model service.
     */
    @EJB
    private ConfigServiceBean service;

    /**
     * The cache.
     */
    private Map<Class, Object> cache = new HashMap<>();

    /**
     * After start method.
     */
    @PostConstruct
    public void start() {
        reload();
    }

    /**
     * Reloads all configuration models.
     */
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

    /**
     * Creates the configuration object from the model.
     *
     * @param config the configuration model.
     * @return the corresponding configuration object.
     */
    private Object loadConfiguration(Config config) {
        Object result = createInstance(config.getClazz());
        if (result != null) {
            if (config.getAttributes() != null) {

                for (Attribute attribute : config.getAttributes().values()) {
                    setAttributeToObject(result, attribute);
                }
            }
        }
        return result;
    }

    /**
     * Saves the configuration object.
     *
     * @param <T> the type.
     * @param data the object.
     * @return the saved configuration object.
     */
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

            for (Field field : fields) {

                Attribute attr = attributes.get(field.getName());
                if (attr == null) {
                    attr = createAttribute(data, field);
                    attributes.put(field.getName(), attr);
                } else {
                    updateAttribute(data, attr, field);
                }
            }
        }

        service.saveConfig(config);
        return data;
    }

    /**
     * Updates the configuration object.
     *
     * @param <T> the configuration object type.
     * @param data the configuration object.
     * @return the saved configuration object.
     */
    public <T> T setConfiguration(T data) {
        T result = null;
        if (data != null) {
            Class clazz = data.getClass();
            result = (T) saveConfiguration(data);
            cache.put(clazz, result);
        }
        return result;
    }

    /**
     * Gets the configuration object by class.
     *
     * @param <T> the class type.
     * @param clazz the class.
     * @return the corresponding configuration object.
     */
    public <T> T getConfiguration(Class<T> clazz) {
        T result = (T) cache.get(clazz);
        if (result == null) {
            result = (T) createInstance(clazz.getName());
            result = setConfiguration(result);
        }
        return result;
    }

    /**
     * Updates the attribute in the configuration model by configuration object
     * field.
     *
     * @param mapper the mapper.
     * @param object the object.
     * @param attribute the attribute.
     * @param field the field.
     */
    private void updateAttribute(Object object, Attribute attribute, Field field) {
        if (attribute != null) {
            try {
                boolean accessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    Object value = field.get(object);
                    String tmp = writeValue(value, field.getType());
                    attribute.setValue(tmp);
                } finally {
                    field.setAccessible(accessible);
                }
            } catch (Exception ex) {
                Logger.getLogger(ConfigurationServiceBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Creates the attribute for the configuration object field.
     *
     * @param mapper the mapper.
     * @param object the object.
     * @param field the field.
     * @return the corresponding attribute.
     */
    private Attribute createAttribute(Object object, Field field) {
        Attribute result = new Attribute();
        try {
            result.setName(field.getName());
            boolean accessible = field.isAccessible();
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                String tmp = writeValue(value, field.getType());
                result.setValue(tmp);
            } finally {
                field.setAccessible(accessible);
            }
        } catch (Exception ex) {
            Logger.getLogger(ConfigurationServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Sets the attribute to the configuration object.
     *
     * @param <T> the configuration object type.
     * @param mapper the mapper.
     * @param object the configuration object.
     * @param attribute the attribute.
     */
    private <T> void setAttributeToObject(T object, Attribute attribute) {
        try {
            String tmp = attribute.getValue();

            Class clazz = object.getClass();
            Field field = clazz.getDeclaredField(attribute.getName());
            Object value = readValue(tmp, field.getType());

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

    /**
     * Creates object instance by class name.
     *
     * @param name the class name.
     * @return the corresponding object instance.
     */
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

    private static <T> T readValue(String value, Class<T> clazz) {
        T result = null;
        InputStream stream = null;
        try {
            LOGGER.log(Level.FINEST, "Read the value from:{0}, type:{1}", new Object[]{value, clazz});
            MessageBodyReader<T> mr = RPF.getMessageBodyReader(clazz, clazz, null, MEDIA_TYPE);
            stream = new ByteArrayInputStream(value.getBytes("UTF-8"));
            result = mr.readFrom(clazz, clazz, null, MEDIA_TYPE, null, stream);
            LOGGER.log(Level.FINEST, "Result:{0}", result);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error reading the value from string " + value, ex);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "Error closing the output stream", ex);
                }
            }
        }
        return result;

    }

    private static String writeValue(Object value, Class clazz) {
        String result = null;
        ByteArrayOutputStream os = null;
        try {
            LOGGER.log(Level.FINEST, "Write the value from:{0}, type:{1}", new Object[]{value, clazz});
            MessageBodyWriter mw = RPF.getMessageBodyWriter(clazz, clazz, null, MEDIA_TYPE);
            os = new ByteArrayOutputStream();
            mw.writeTo(value, clazz, clazz, null, MEDIA_TYPE, null, os);
            result = new String(os.toByteArray(), "UTF-8");
            LOGGER.log(Level.FINEST, "Result:{0}", result);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error writing the value to string " + value, ex);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "Error closing the output stream", ex);
                }
            }
        }
        return result;
    }
}
