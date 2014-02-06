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
package org.lorislab.armonitor.scm.model;

import java.io.Serializable;
import java.util.List;

/**
 * The SCM criteria.
 *
 * @author Andrej Petras
 */
public class ScmCriteria implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 5576804571037265497L;
    /**
     * The type.
     */
    private String type;
    /**
     * The authentication flag.
     */
    private boolean auth;
    /**
     * The server.
     */
    private String server;
    /**
     * The user.
     */
    private String user;
    /**
     * The password.
     */
    private char[] password;
    /**
     * The start.
     */
    private String start;
    /**
     * The end.
     */
    private String end;
    /**
     * The list of paths.
     */
    private List<String> path;

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
     * Gets the start.
     *
     * @return the start.
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets the start.
     *
     * @param start the start.
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Gets the end.
     *
     * @return the end.
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the end.
     *
     * @param end the end.
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Gets the list of paths.
     *
     * @return the list of paths.
     */
    public List<String> getPath() {
        return path;
    }

    /**
     * Sets the list of paths.
     *
     * @param path the list of paths.
     */
    public void setPath(List<String> path) {
        this.path = path;
    }

}
