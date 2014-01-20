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

/**
 * The mapper service interface.
 *
 * @author Andrej Petras
 *
 * @param <E> the entity.
 * @param <T> the rest POJO model.
 */
public interface MapperService<E, T> {

    /**
     * Maps the entity to the rest model.
     *
     * @param profile the map profile.
     * @param data the entity object.
     * @return the rest model.
     */
    public T map(E data, String profile);

    /**
     * Updates the entity.
     *
     * @param profile the map profile.
     * @param entity the entity.
     * @param data the rest model.
     * @return the updated entity.
     */
    public E update(E entity, T data, String profile);

    /**
     * Creates new entity from rest model.
     *
     * @param profile the map profile.
     * @param data the rest model.
     * @return the created entity.
     */
    public E create(T data, String profile);

    /**
     * Map new created entity to the rest model.
     *
     * @param profile the map profile.
     * @return the new rest model.
     */
    public T create(String profile);
}
