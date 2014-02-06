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
package org.lorislab.armonitor.bts.model;

import java.io.Serializable;
import java.util.Set;

/**
 * The bug tracking criteria.
 *
 * @author Andrej Petras
 */
public class BtsCriteria implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 7340657695893586927L;
    /**
     * The type.
     */
    private String type;
    /**
     * The authentication flag.
     */
    private boolean auth;
    /**
     * The server URL.
     */
    private String server;
    /**
     * The user name.
     */
    private String user;
    /**
     * The password.
     */
    private char[] password;
    /**
     * The version.
     */
    private String version;
    /**
     * The project.
     */
    private String project;
    /**
     * The set of ID's.
     */
    private Set<String> ids;

    /**
     * The id.
     */
    private String id;

    /**
     * Gets the id.
     *
     * @return the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the set of id's.
     *
     * @return the set of id's.
     */
    public Set<String> getIds() {
        return ids;
    }

    /**
     * Sets the set of id's.
     *
     * @param ids the set of id's.
     */
    public void setIds(Set<String> ids) {
        this.ids = ids;
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
     * Gets the authentication flag.
     *
     * @return the authentication flag.
     */
    public boolean isAuth() {
        return auth;
    }

    /**
     * Sets the authentication flag.
     *
     * @param auth the authentication flag.
     */
    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    /**
     * Gets the server.
     *
     * @return the server.
     */
    public String getServer() {
        return server;
    }

    /**
     * Sets the server.
     *
     * @param server the server.
     */
    public void setServer(String server) {
        this.server = server;
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
     * Gets the password.
     *
     * @return the password.
     */
    public char[] getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password.
     */
    public void setPassword(char[] password) {
        this.password = password;
    }

    /**
     * Gets the version.
     *
     * @return the version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version the version.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets the project.
     *
     * @return the project.
     */
    public String getProject() {
        return project;
    }

    /**
     * Sets the project.
     *
     * @param project the project.
     */
    public void setProject(String project) {
        this.project = project;
    }

}
