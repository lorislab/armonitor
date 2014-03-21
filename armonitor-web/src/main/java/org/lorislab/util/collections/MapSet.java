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
package org.lorislab.util.collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The map of sets.
 *
 * @param <K> the key.
 * @param <V> the value.
 *
 * @author Andrej Petras
 */
public class MapSet<K, V> {

    private final Map<K, Set<V>> data = new HashMap<>();

    public Set<Entry<K, Set<V>>> entrySet() {
        return data.entrySet();
    }
    
    public Map<K, Set<V>> map() {
        return data;
    }
    
    public boolean contains(K key, V value) {
        Set<V> set = data.get(key);
        if (set != null) {
            return set.contains(value);
        }
        return false;
    }

    public boolean containsKey(K key) {
        return data.containsKey(key);
    }

    public void put(K key, V value) {
        Set<V> set = data.get(key);
        if (set == null) {
            set = new HashSet<>();
            data.put(key, set);
        }
        set.add(value);
    }

    public Set<V> getValues(K key) {
        return data.get(key);
    }

    public int size() {
        return data.size();
    }

    public int size(K key) {
        Set<V> set = data.get(key);
        if (set != null) {
            return set.size();
        }
        return 0;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public boolean isEmpty(K key) {
        Set<V> set = data.get(key);
        if (set != null) {
            return set.isEmpty();
        }
        return true;
    }

}
