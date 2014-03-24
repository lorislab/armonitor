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

package org.lorislab.armonitor.activity.comparator;

import java.util.Comparator;
import org.lorislab.armonitor.activity.wrapper.ActivityChangeWrapper;

/**
 *
 * @author Andrej Petras
 */
public class ActivityChangeWrapperComparator implements Comparator<ActivityChangeWrapper> {

    /**
     * The instance.
     */
    public static final ActivityChangeWrapperComparator INSTANCE = new ActivityChangeWrapperComparator();
    
    @Override
    public int compare(ActivityChangeWrapper o1, ActivityChangeWrapper o2) {
        if (o1.isError() && !o2.isError()) {
            return -1;
        }
        if (o2.isError() && !o1.isError()) {
            return 1;
        }
        if (o1.isEmpty() && o2.isLogs()) {
            return 1;
        }
        if (o2.isEmpty() && o1.isLogs()) {
            return -1;
        }
        return o1.getKey().compareTo(o2.getKey());
    }
    
}
