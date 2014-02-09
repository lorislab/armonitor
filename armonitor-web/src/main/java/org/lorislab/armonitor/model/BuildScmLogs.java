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
import java.util.Date;
import org.lorislab.armonitor.store.model.StoreBuild;

/**
 * The build change.
 *
 * @author Andrej Petras
 */
public class BuildScmLogs implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 6687971736480059630L;

    /**
     * The store build.
     */
    private final StoreBuild build;

    /**
     * The date.
     */
    private final Date date;

    /**
     * The default constructor.
     *
     * @param build the build.
     * @param date the date.
     */
    public BuildScmLogs(StoreBuild build, Date date) {
        this.build = build;
        this.date = date;
    }

    /**
     * Gets the build.
     *
     * @return the build.
     */
    public StoreBuild getBuild() {
        return build;
    }

    /**
     * Gets the date.
     *
     * @return the date.
     */
    public Date getDate() {
        return date;
    }

}
