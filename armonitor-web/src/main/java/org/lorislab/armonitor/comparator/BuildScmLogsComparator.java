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
import org.lorislab.armonitor.model.BuildScmLogs;

/**
 * The build SCM logs comparator.
 *
 * @author Andrej Petras
 */
public class BuildScmLogsComparator implements Comparator<BuildScmLogs> {

    /**
     * The instance.
     */
    public static final BuildScmLogsComparator INSTANCE = new BuildScmLogsComparator();

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
    public int compare(BuildScmLogs o1, BuildScmLogs o2) {
        return o1.getDate().compareTo(o2.getDate());
    }

}
