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

package org.lorislab.armonitor.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Andrej Petras
 */
public class MappingStrategy implements Serializable {
    
    private static final long serialVersionUID = 2444033037267816885L;
    
    private Set<String> excludes = new HashSet<>();
    
    private Set<String> includes = null;

    public MappingStrategy(String ... excludes) {
        setExcludes(excludes);
    }
    
    public final boolean isField(String name) {
        if (includes == null) {
            return !excludes.contains(name);
        } else {
            return includes.contains(name);
        }
    }
    
    public final void setExcludes(String ... names) {
        excludes = new HashSet<>(Arrays.asList(names));
    }
    
    public final void setIncludes(String ... names) {
        includes = new HashSet<>(Arrays.asList(names));
    }

}
