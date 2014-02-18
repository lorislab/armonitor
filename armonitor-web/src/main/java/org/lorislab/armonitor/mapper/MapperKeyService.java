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
 * The mapper key service.
 *
 * @author Andrej Petras
 */
public interface MapperKeyService<E> {

    /**
     * Gets the key from the object.
     *
     * @param data the object.
     * @param profile the mapper profile.
     * @return the corresponding key.
     */
    public String getKey(E data, String profile);

}
