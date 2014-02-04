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

package org.lorislab.armonitor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrej Petras
 */
public class ChangeReport implements Serializable {
    
    private static final long serialVersionUID = -8226350181208816998L;
    
    private final List<Change> other = new ArrayList<>();
    
    private final List<Change> changes = new ArrayList<>();

    private final List<Change> errors = new ArrayList<>();

    public List<Change> getOther() {
        return other;
    }
    
    public List<Change> getErrors() {
        return errors;
    }
   
    public List<Change> getChanges() {
        return changes;
    }
        
}
