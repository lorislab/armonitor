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

package org.lorislab.armonitor.activity.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.lorislab.armonitor.store.model.StoreActivity;
import org.lorislab.armonitor.store.model.StoreApplication;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreProject;

/**
 * The activity model.
 * 
 * @author Andrej Petras
 */
public class ActivityWrapper implements Serializable {
    
    private static final long serialVersionUID = 4577724099379832522L;
    
    private final StoreActivity activity;
    
    private StoreApplication application;
    
    private StoreProject project;
    
    /**
     * The list of all changes.
     */
    private final List<ActivityChangeWrapper> changes = new ArrayList<>();
    /**
     * The list of current build changes.
     */
    private final List<ActivityChangeWrapper> buildChanges= new ArrayList<>();
    /**
     * The activity change types.
     */
    private final Set<String> types = new HashSet<>();

    public ActivityWrapper(StoreActivity activity) {
        this.activity = activity;
    }
    
    public void setApplication(StoreApplication application) {
        this.application = application;
    }

    public StoreApplication getApplication() {
        return application;
    }

    public void setProject(StoreProject project) {
        this.project = project;
    }

    public StoreProject getProject() {
        return project;
    }
    
    public StoreActivity getActivity() {
        return activity;
    }
    
    public String getGuid() {
        return activity.getGuid();
    }
    
    /**
     * @return the changes
     */
    public List<ActivityChangeWrapper> getChanges() {
        return changes;
    }

    /**
     * @return the buildChanges
     */
    public List<ActivityChangeWrapper> getBuildChanges() {
        return buildChanges;
    }

    /**
     * @return the types
     */
    public Set<String> getTypes() {
        return types;
    }

    public StoreBuild getBuild() {
        return activity.getBuild();
    }
    
    public boolean hasBuildChanges() {
        return !buildChanges.isEmpty();
    }
    
    public boolean hasChanges() {
        return !changes.isEmpty();
    }    
}
