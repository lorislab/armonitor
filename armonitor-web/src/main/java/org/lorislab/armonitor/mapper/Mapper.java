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
package org.lorislab.armonitor.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The mapper.
 *
 * @author Andrej Petras
 */
public final class Mapper {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(Mapper.class.getName());
    /**
     * The mapper services cache.
     */
    private static final Map<Class, Map<Class, MapperService>> MAPPER = new HashMap<>();
    /**
     * The mapper key services cache.
     */
    private static final Map<Class, MapperKeyService> MAPPER_KEY = new HashMap<>();

    /**
     * Loads the mapper services.
     */
    static {
        ServiceLoader<MapperService> services = ServiceLoader.load(MapperService.class);
        if (services != null) {
            Iterator<MapperService> iter = services.iterator();
            while (iter.hasNext()) {
                MapperService service = iter.next();
                LOGGER.log(Level.FINE, "Add mapper service {0}", service.getClass().getName());
                add(service);
            }
        }

        ServiceLoader<MapperKeyService> keyServices = ServiceLoader.load(MapperKeyService.class);
        if (keyServices != null) {
            Iterator<MapperKeyService> iter = keyServices.iterator();
            while (iter.hasNext()) {
                MapperKeyService service = iter.next();
                LOGGER.log(Level.FINE, "Add mapper key service {0}", service.getClass().getName());
                add(service);
            }
        }
    }

    /**
     * The default constructor.
     */
    private Mapper() {
        // empty default constructor.
    }

    /**
     * Adds the mapper key service.
     *
     * @param mapper the mapper key service.
     */
    private static void add(MapperKeyService mapper) {
        Type[] type = ((ParameterizedType) mapper.getClass().getGenericInterfaces()[0]).getActualTypeArguments();
        MAPPER_KEY.put((Class) type[0], mapper);
    }

    /**
     * Adds the mapper service to the cache.
     *
     * @param mapper the mapper service.
     */
    private static void add(MapperService mapper) {
        Type[] type = ((ParameterizedType) mapper.getClass().getGenericInterfaces()[0]).getActualTypeArguments();
        Class entity = (Class) type[0];
        Map<Class, MapperService> tmp = MAPPER.get(entity);
        if (tmp == null) {
            tmp = new HashMap<>();
            MAPPER.put(entity, tmp);
        }
        tmp.put((Class) type[1], mapper);
    }

    /**
     * Maps the set of the entities to the set of models.
     *
     * @param <T> the model type.
     * @param <E> the entity type.
     * @param data the set of entities.
     * @param clazz the model class.
     * @return the set of models.
     */
    public static <T, E> Set<T> map(Set<E> data, Class<T> clazz) {
        return map(data, clazz, null);
    }

    /**
     * Maps the ser of the entities to the models.
     *
     * @param <T> the model type.
     * @param <E> the entity type.
     * @param data the set of entities.
     * @param clazz the model class.
     * @param profile the mapping profile.
     * @return the set of models.
     */
    public static <T, E> Set<T> map(Set<E> data, Class<T> clazz, String profile) {
        Set<T> result = null;
        if (data != null) {
            result = new HashSet<>();
            if (!data.isEmpty()) {
                MapperService<E, T> mapper = MAPPER.get(data.iterator().next().getClass()).get(clazz);
                for (E item : data) {
                    if (item != null) {
                        T tmp = map(item, mapper, profile);
                        if (tmp != null) {
                            result.add(tmp);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Converts the collection to the map.
     *
     * @param <T> the result type.
     * @param <E> the input type.
     * @param data the input collection.
     * @param clazz the result class.
     * @return the corresponding map.
     */
    public static <T, E> Map<String, T> convert(Collection<E> data, Class<T> clazz) {
        return convert(data, clazz, null);
    }

    /**
     * Converts the collection to the map.
     *
     * @param <T> the result type.
     * @param <E> the input type.
     * @param data the input collection.
     * @param clazz the result class.
     * @param profile the mapper profile.
     * @return the corresponding map.
     */
    public static <T, E> Map<String, T> convert(Collection<E> data, Class<T> clazz, String profile) {
        Map<String, T> result = null;
        if (data != null) {
            result = new HashMap<>();
            if (!data.isEmpty()) {
                Class e = data.iterator().next().getClass();
                MapperService<E, T> mapper = MAPPER.get(e).get(clazz);
                MapperKeyService<E> mapperKey = MAPPER_KEY.get(e);
                for (E item : data) {
                    if (item != null) {
                        T tmp = map(item, mapper, profile);
                        if (tmp != null) {
                            String key = mapperKey.getKey(item, profile);
                            result.put(key, tmp);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Maps the list of the entities to the list of models.
     *
     * @param <T> the model type.
     * @param <E> the entity type.
     * @param data the set of entities.
     * @param clazz the model class.
     * @return the list of models.
     */
    public static <T, E> List<T> map(List<E> data, Class<T> clazz) {
        return map(data, clazz, null);
    }

    /**
     * Maps the list of the entities to the list of models.
     *
     * @param <T> the model type.
     * @param <E> the entity type.
     * @param data the set of entities.
     * @param clazz the model class.
     * @param profile the mapping profile.
     * @return the list of models.
     */
    public static <T, E> List<T> map(List<E> data, Class<T> clazz, String profile) {
        List<T> result = null;
        if (data != null) {
            result = new ArrayList<>();
            if (!data.isEmpty()) {
                MapperService<E, T> mapper = MAPPER.get(data.get(0).getClass()).get(clazz);
                for (E item : data) {
                    if (item != null) {
                        T tmp = map(item, mapper, profile);
                        if (tmp != null) {
                            result.add(tmp);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Mapping the entity to the model.
     *
     * @param <T> the type of the model.
     * @param <E> the type of the entity.
     * @param data the entity.
     * @param clazz the class of the model.
     * @return the model.
     */
    public static <T, E> T map(E data, Class<T> clazz) {
        return map(data, clazz, null);
    }

    /**
     * Mapping the entity to the model.
     *
     * @param <T> the type of the model.
     * @param <E> the type of the entity.
     * @param data the entity.
     * @param clazz the class of the model.
     * @param profile the mapping profile.
     * @return the model.
     */
    public static <T, E> T map(E data, Class<T> clazz, String profile) {
        T result = null;
        if (data != null) {
            MapperService<E, T> mapper = MAPPER.get(data.getClass()).get(clazz);
            result = map(data, mapper, profile);
        }
        return result;
    }

    /**
     * Mapping the entity to the model.
     *
     * @param <T> the type of the model.
     * @param <E> the type of the entity.
     * @param data the entity.
     * @param mapper the mapper service.
     * @return the model.
     */
    public static <T, E> T map(E data, MapperService<E, T> mapper) {
        return map(data, mapper, null);
    }

    /**
     * Mapping the entity to the model.
     *
     * @param <T> the type of the model.
     * @param <E> the type of the entity.
     * @param data the entity.
     * @param mapper the mapper service.
     * @param profile the mapping profile.
     * @return the model.
     */
    public static <T, E> T map(E data, MapperService<E, T> mapper, String profile) {
        T result = null;
        if (mapper != null) {
            result = mapper.map(data, profile);
        }
        return result;
    }

    /**
     * Update the entity by model.
     *
     * @param <T> the type of the model.
     * @param <E> the type of the entity.
     * @param entity the entity.
     * @param data the model.
     * @return the entity.
     */
    public static <T, E> E update(E entity, T data) {
        return update(entity, data, null);
    }

    /**
     * Update the entity by model.
     *
     * @param <T> the type of the model.
     * @param <E> the type of the entity.
     * @param entity the entity.
     * @param data the model.
     * @param profile the mapping profile.
     * @return the entity.
     */
    public static <T, E> E update(E entity, T data, String profile) {
        if (entity != null && data != null) {
            MapperService<E, T> mapper = MAPPER.get(entity.getClass()).get(data.getClass());
            if (mapper != null) {
                entity = mapper.update(entity, data, profile);
            }
        }
        return entity;
    }

    /**
     * Create the entity from the model.
     *
     * @param <T> the type of the model.
     * @param <E> the type of the entity.
     * @param clazz the entity class.
     * @param data the model.
     * @return the entity.
     */
    public static <T, E> E create(T data, Class<E> clazz) {
        return create(data, clazz, null);
    }

    /**
     * Create the entity from the model.
     *
     * @param <T> the type of the model.
     * @param <E> the type of the entity.
     * @param clazz the entity class.
     * @param data the model.
     * @param profile the mapping profile.
     * @return the entity.
     */
    public static <T, E> E create(T data, Class<E> clazz, String profile) {
        E result = null;
        if (data != null && clazz != null) {
            MapperService<E, T> mapper = MAPPER.get(clazz).get(data.getClass());
            if (mapper != null) {
                result = mapper.create(data, profile);
            }
        }
        return result;
    }

    /**
     * Create the model from the entity.
     *
     * @param <T> the type of the model.
     * @param <E> the type of the entity.
     * @param data the model class.
     * @param entity the model.
     * @return the model.
     */
    public static <T, E> T create(Class<E> entity, Class<T> data) {
        return create(entity, data, null);
    }

    /**
     * Create the model from the entity.
     *
     * @param <T> the type of the model.
     * @param <E> the type of the entity.
     * @param data the model class.
     * @param entity the model.
     * @param profile the mapping profile.
     * @return the model.
     */
    public static <T, E> T create(Class<E> entity, Class<T> data, String profile) {
        T result = null;
        if (entity != null && data != null) {
            MapperService<E, T> mapper = MAPPER.get(entity).get(data);
            if (mapper != null) {
                result = mapper.create(profile);
            }
        }
        return result;
    }
}
