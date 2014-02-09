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
package org.lorislab.armonitor.comparator;

import java.util.Comparator;
import org.lorislab.armonitor.model.Change;

/**
 * Change comparator.
 *
 * @author Andrej Petras
 */
public class ChangeComparator implements Comparator<Change> {

    /**
     * The instance.
     */
    public static final ChangeComparator INSTANCE = new ChangeComparator();

    /**
     * Compare two changes.
     *
     * @param o1 the first change.
     * @param o2 the second change.
     * @return compare the error flags, compare the changes list, compare the
     * id.
     */
    @Override
    public int compare(Change o1, Change o2) {
        if (o1.isError() && !o2.isError()) {
            return -1;
        }
        if (o2.isError() && !o1.isError()) {
            return 1;
        }
        if (o1.getChanges().isEmpty() && !o2.getChanges().isEmpty()) {
            return 1;
        }
        if (o2.getChanges().isEmpty() && !o1.getChanges().isEmpty()) {
            return -1;
        }
        return o1.getId().compareTo(o2.getId());
    }
}
