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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrej Petras
 */
public final class MappingObject {

    private MappingObject() {
        // empty constructor
    }

    private static Map<String, Field> getFields(Class clazz, MappingStrategy strategy) {
        Map<String, Field> result = new HashMap<>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (strategy == null || strategy.isField(field.getName())) {
                if (!Modifier.isFinal(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
                    result.put(field.getName(), field);
                }
            }
        }
        if (clazz.getSuperclass() != null && !clazz.equals(Object.class)) {
            Map<String, Field> tmp = getFields(clazz.getSuperclass(), strategy);
            result.putAll(tmp);
        }

        return result;
    }

    private static void map(Field out, Object output, Field in, Object input) {
        if (out != null && in != null) {
            boolean sec1 = out.isAccessible();
            boolean sec2 = in.isAccessible();
            out.setAccessible(true);
            in.setAccessible(true);
            try {
                out.set(output, in.get(input));

            } catch (Exception ex) {
                Logger.getLogger(MappingObject.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                out.setAccessible(sec1);
                in.setAccessible(sec2);
            }
        }
    }

    public static final <T> T map(Object input, T output) {
        return map(input, output, null);
    }
    
    public static final <T> T mapWitExcludes(Object input, T output, String... excludes) {
        return map(input, output, new MappingStrategy(excludes));        
    }
    
    public static final <T> T map(Object input, T output, MappingStrategy strategy) {
        if (input != null && output != null) {
            Map<String, Field> in = getFields(input.getClass(), strategy);
            Map<String, Field> out = getFields(output.getClass(), strategy);
            for (Entry<String, Field> entry : in.entrySet()) {
                map(out.get(entry.getKey()), output, entry.getValue(), input);
            }
        }
        return output;
    }

}
