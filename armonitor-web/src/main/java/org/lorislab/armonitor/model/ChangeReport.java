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
 * The change report.
 *
 * @author Andrej Petras
 */
public class ChangeReport implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -8226350181208816998L;
    /**
     * The other changes.
     */
    private final List<Change> other = new ArrayList<>();
    /**
     * The current changes.
     */
    private final List<Change> changes = new ArrayList<>();
    /**
     * The error changes.
     */
    private final List<Change> errors = new ArrayList<>();

    /**
     * Gets the other changes.
     *
     * @return the other changes.
     */
    public List<Change> getOther() {
        return other;
    }

    /**
     * Gets the error changes.
     *
     * @return the error changes.
     */
    public List<Change> getErrors() {
        return errors;
    }

    /**
     * Gets the current changes.
     *
     * @return the current changes.
     */
    public List<Change> getChanges() {
        return changes;
    }

}
