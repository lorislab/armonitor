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

/**
 * The SCM link.
 *
 * @author Andrej Petras
 */
public class ScmLink {

    /**
     * The SCM trunk.
     */
    private String scmTrunk;
    /**
     * The SCM tags.
     */
    private String scmTags;
    /**
     * The SCM branches.
     */
    private String scmBranches;
    /**
     * The SCM repository.
     */
    private String scmRepo;

    /**
     * Gets the SCM repository.
     *
     * @return the SCM repository.
     */
    public String getScmRepo() {
        return scmRepo;
    }

    /**
     * Sets the SCM repository.
     *
     * @param scmRepo the SCM repository.
     */
    public void setScmRepo(String scmRepo) {
        this.scmRepo = scmRepo;
    }

    /**
     * Gets the SCM branches.
     *
     * @return the SCM branches.
     */
    public String getScmBranches() {
        return scmBranches;
    }

    /**
     * Sets the SCM branches.
     *
     * @param scmBranches the SCM branches.
     */
    public void setScmBranches(String scmBranches) {
        this.scmBranches = scmBranches;
    }

    /**
     * Gets the SCM tags.
     *
     * @return the SCM tags.
     */
    public String getScmTags() {
        return scmTags;
    }

    /**
     * Sets the SCM tags.
     *
     * @param scmTags the SCM tags.
     */
    public void setScmTags(String scmTags) {
        this.scmTags = scmTags;
    }

    /**
     * Gets the SCM trunk.
     *
     * @return the SCM trunk.
     */
    public String getScmTrunk() {
        return scmTrunk;
    }

    /**
     * Sets the SCM trunk.
     *
     * @param scmTrunk the SCM trunk.
     */
    public void setScmTrunk(String scmTrunk) {
        this.scmTrunk = scmTrunk;
    }

}
