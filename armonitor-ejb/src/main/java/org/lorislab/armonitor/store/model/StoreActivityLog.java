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
package org.lorislab.armonitor.store.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The store activity log.
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "ARM_ACTIVITY_LOG")
public class StoreActivityLog extends Persistent {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 4282904270471113168L;

    /**
     * The activity change.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_CHANGE")
    private StoreActivityChange change;

    /**
     * The build.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "C_BUILD")
    private StoreBuild build;
    
    /**
     * The date.
     */
    @Column(name = "C_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * The user.
     */
    @Column(name = "C_USER")
    private String user;

    /**
     * The revision.
     */
    @Column(name = "C_REVISION")
    private String revision;

    /**
     * The message.
     */
    @Column(name = "C_MESSAGE")
    private String message;
    
    /**
     * Gets the date.
     *
     * @return the date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the date.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the user.
     *
     * @return the user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user the user.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets the revision.
     *
     * @return the revision.
     */
    public String getRevision() {
        return revision;
    }

    /**
     * Sets the revision.
     *
     * @param revision the revision.
     */
    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * Gets the message.
     *
     * @return the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the activity change.
     *
     * @return the activity change.
     */
    public StoreActivityChange getChange() {
        return change;
    }

    /**
     * Sets the activity change.
     *
     * @param change the activity change.
     */
    public void setChange(StoreActivityChange change) {
        this.change = change;
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
     * Sets the build.
     *
     * @param build the build.
     */
    public void setBuild(StoreBuild build) {
        this.build = build;
    }    
}
