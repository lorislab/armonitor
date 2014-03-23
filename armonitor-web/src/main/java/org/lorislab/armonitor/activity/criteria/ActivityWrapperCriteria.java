/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lorislab.armonitor.activity.criteria;

import org.lorislab.jel.base.criteria.AbstractSearchCriteria;

/**
 *
 * @author Andrej Petras
 */
public class ActivityWrapperCriteria extends AbstractSearchCriteria {
    
    private static final long serialVersionUID = -156558770692659920L;
    
    private String guid;
    
    private String build;
    
    private boolean fetchProject;
    
    private boolean fetchApplication;
    
    private boolean sortList;

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean isFetchApplication() {
        return fetchApplication;
    }

    public void setFetchApplication(boolean fetchApplication) {
        this.fetchApplication = fetchApplication;
    }

    public boolean isFetchProject() {
        return fetchProject;
    }

    public void setFetchProject(boolean fetchProject) {
        this.fetchProject = fetchProject;
    }

    public boolean isSortList() {
        return sortList;
    }

    public void setSortList(boolean sortList) {
        this.sortList = sortList;
    }
    
    @Override
    public void reset() {
        guid = null;
        build = null;
        fetchApplication = false;
        fetchProject = false;
        sortList = false;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty(guid, build);
    }
    
}
