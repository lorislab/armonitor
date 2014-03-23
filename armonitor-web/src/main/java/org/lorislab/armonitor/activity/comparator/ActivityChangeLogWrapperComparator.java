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
import org.lorislab.armonitor.activity.wrapper.ActivityChangeLogWrapper;
import org.lorislab.armonitor.store.model.StoreActivityLog;

/**
 * The activity logs comparator.
 *
 * @author Andrej Petras
 */
public class ActivityChangeLogWrapperComparator implements Comparator<ActivityChangeLogWrapper> {

    /**
     * The instance.
     */
    public static final ActivityChangeLogWrapperComparator INSTANCE = new ActivityChangeLogWrapperComparator();

    /**
     * Compare two logs by date.
     *
     * @param o1 the first build SCM log.
     * @param o2 the second build SCM log.
     * @return <code>-1</code> if the first log is before the second one.
     * <code>1</code> if the second log is before the first one.<code>0</code>
     * if the date of build logs are equals.
     */
    @Override
    public int compare(ActivityChangeLogWrapper o1, ActivityChangeLogWrapper o2) {
        StoreActivityLog b1 = o1.getLog();
        StoreActivityLog b2 = o2.getLog();
        if (b1 != null && b2 != null) {
            return b1.getDate().compareTo(b2.getDate());
        }

        if (b1 == null && b2 == null) {
            return 0;
        }

        if (b1 == null) {
            return 1;
        }

        return -1;
    }

}
