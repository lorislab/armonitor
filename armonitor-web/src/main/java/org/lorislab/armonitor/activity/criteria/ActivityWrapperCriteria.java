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
        sortList = false;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty(guid, build);
    }
    
}
