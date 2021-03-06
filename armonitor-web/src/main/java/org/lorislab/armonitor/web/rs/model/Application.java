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
package org.lorislab.armonitor.web.rs.model;

/**
 * The application.
 *
 * @author Andrej Petras
 */
public class Application {

    /**
     * The GUID.
     */
    public String guid;
    /**
     * The name.
     */
    public String name;
    /**
     * The SCM trunk.
     */
    public String scmTrunk;
    /**
     * The SCM branches.
     */
    public String scmBranches;
    /**
     * The SCM tags.
     */
    public String scmTags;
    /**
     * The SCM repository.
     */
    public String scmRepo;
    /**
     * The SCM type.
     */
    public String scmType;
    /**
     * The enabled flag.
     */
    public boolean enabled;
    /**
     * The repository link.
     */
    public String repoLink;
    /**
     * The type.
     */
    public String type;
    /**
     * The new flag
     */
    public boolean n;
    /**
     * The key.
     */
    public String key;
    /**
     * The version.
     */
    public Integer v;
    /**
     * The index.
     */
    public Integer index;      
}
