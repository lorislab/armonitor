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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * The mapper.
 * 
 * @author Andrej Petras
 */
public final class Mapper {

    private static final Map<Class, Map<Class, MapperService>> MAPPER = new HashMap<>();

    private Mapper() {
        ServiceLoader<MapperService> services = ServiceLoader.load(MapperService.class);
        if (services != null) {
            Iterator<MapperService> iter = services.iterator();
            while (iter.hasNext()) {
                add(iter.next());
            }
        }
    }

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

    public static <T, E> List<T> map(List<E> data, Class<T> clazz) {
        return map(data, clazz, null);
    }
    
    public static <T, E> List<T> map(List<E> data, Class<T> clazz, String profile) {
        List<T> result = null;
        if (data != null) {
            result = new ArrayList<>();
            if (!data.isEmpty()) {
                MapperService<E, T> mapper = MAPPER.get(data.get(0).getClass()).get(clazz);;
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

    public static <T, E> T map(E data, Class<T> clazz) {
        return map(data, clazz, null);
    }
    
    public static <T, E> T map(E data, Class<T> clazz, String profile) {
        T result = null;
        if (data != null) {
            MapperService<E, T> mapper = MAPPER.get(data.getClass()).get(clazz);
            result = map(data, mapper, profile);
        }
        return result;
    }

    public static <T, E> T map(E data, MapperService<E, T> mapper) {
        return map(data, mapper, null);
    }
    
    public static <T, E> T map(E data, MapperService<E, T> mapper, String profile) {
        T result = null;
        if (mapper != null) {
            result = mapper.map(data, profile);
        }
        return result;
    }

    public static <T, E> E update(E entity, T data) {
        return update(entity, data, null);
    }
    
    public static <T, E> E update(E entity, T data, String profile) {
        if (entity != null && data != null) {
            MapperService<E, T> mapper = MAPPER.get(data.getClass()).get(data.getClass());
            if (mapper != null) {
                entity = mapper.update(entity, data, profile);
            }
        }
        return entity;
    }

    public static <T, E> E create(T data, Class<E> clazz) {
        return create(data, clazz, null);
    }
    
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

    public static <T, E> T map(Class<E> entity, Class<T> data) {
        return map(entity, data, null);
    }
    
    public static <T, E> T map(Class<E> entity, Class<T> data, String profile) {
        T result = null;
        if (entity != null && data != null) {
            MapperService<E, T> mapper = MAPPER.get(entity).get(data);
            if (mapper != null) {
                result = mapper.map(profile);
            }
        }
        return result;
    }
}
