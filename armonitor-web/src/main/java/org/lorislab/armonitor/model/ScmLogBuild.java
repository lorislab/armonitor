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
import org.lorislab.armonitor.scm.model.ScmLog;
import org.lorislab.armonitor.store.model.StoreBuild;

/**
 * The build change.
 *
 * @author Andrej Petras
 */
public class ScmLogBuild implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -8746843678636587498L;
    /**
     * The build.
     */
    private final StoreBuild build;
    /**
     * The list of SCM logs.
     */
    private final ScmLog scmLog;

    /**
     * The default constructor.
     *
     * @param build the build.
     * @param scmLog the SCM log.
     */
    public ScmLogBuild(StoreBuild build, ScmLog scmLog) {
        this.build = build;
        this.scmLog = scmLog;
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
     * Gets the SCM log.
     *
     * @return the SCM log.
     */
    public ScmLog getScmLog() {
        return scmLog;
    }
}
