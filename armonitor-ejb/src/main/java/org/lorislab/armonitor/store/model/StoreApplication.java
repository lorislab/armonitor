/*
 * Copyright 2013 lorislab.org.
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
package org.lorislab.armonitor.store.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The application.
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_APP")
public class StoreApplication extends Persistent {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -6964092704804204045L;

    /**
     * The project.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_PROJECT")
    private StoreProject project;

    /**
     * The repository link.
     */
    @Column(name = "C_REPO_LINK")
    private String repoLink;

    /**
     * The SCM.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_SCM")
    private StoreSCMSystem scm;

    /**
     * The name.
     */
    @Column(name = "C_NAME")
    private String name;

    /**
     * The key for manual deployment.
     */
    @Column(name = "C_KEY")
    private String key;

    /**
     * The SCM trunk.
     */
    @Column(name = "C_SCM_TRUNK")
    private String scmTrunk;

    /**
     * The SCM tags.
     */
    @Column(name = "C_SCM_TAG")
    private String scmTags;

    /**
     * The SCM branches.
     */
    @Column(name = "C_SCM_BRANCHES")
    private String scmBranches;

    /**
     * The SCM repository.
     */
    @Column(name = "C_SCM_REPO")
    private String scmRepo;

    /**
     * The enabled flag.
     */
    @Column(name = "C_ENABLED")
    private boolean enabled;

    /**
     * The type.
     */
    @Column(name = "C_TYPE")
    private String type;

    /**
     * The application builds.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "application")
    private Set<StoreBuild> builds;

    /**
     * Set of application systems.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "application")
    private Set<StoreSystem> systems;

    /**
     * The index.
     */
    @Column(name = "C_INDEX")
    private Integer index;

    /**
     * Gets the index.
     *
     * @return the index.
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Sets the index.
     *
     * @param index the index.
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * Gets the key for manual deployment.
     *
     * @return the key for manual deployment.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key for manual deployment.
     *
     * @param key the key for manual deployment.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the type.
     *
     * @return the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the type.
     */
    public void setType(String type) {
        this.type = type;
    }

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
     * Gets the repository link.
     *
     * @return the repository link.
     */
    public String getRepoLink() {
        return repoLink;
    }

    /**
     * Sets the repository link.
     *
     * @param repoLink the repository link.
     */
    public void setRepoLink(String repoLink) {
        this.repoLink = repoLink;
    }

    public String getScmBranches() {
        return scmBranches;
    }

    public void setScmBranches(String scmBranches) {
        this.scmBranches = scmBranches;
    }

    public String getScmTags() {
        return scmTags;
    }

    public void setScmTags(String scmTags) {
        this.scmTags = scmTags;
    }

    public String getScmTrunk() {
        return scmTrunk;
    }

    public void setScmTrunk(String scmTrunk) {
        this.scmTrunk = scmTrunk;
    }

    public Set<StoreSystem> getSystems() {
        return systems;
    }

    public void setSystems(Set<StoreSystem> systems) {
        this.systems = systems;
    }

    public Set<StoreBuild> getBuilds() {
        return builds;
    }

    public void setBuilds(Set<StoreBuild> builds) {
        this.builds = builds;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public StoreProject getProject() {
        return project;
    }

    public void setProject(StoreProject project) {
        this.project = project;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the scm
     */
    public StoreSCMSystem getScm() {
        return scm;
    }

    /**
     * @param scm the scm to set
     */
    public void setScm(StoreSCMSystem scm) {
        this.scm = scm;
    }

}
