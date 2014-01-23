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
package org.lorislab.armonitor.mail.template.model;

import java.io.Serializable;

/**
 * The mail template resource.
 *
 * @author Andrej Petras
 */
public class MailTemplateResource implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -7752917947829372574L;

    /**
     * The name.
     */
    private String name;
    /**
     * The content.
     */
    private byte[] content;

    /**
     * Gets the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the content.
     *
     * @return the content.
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content the content.
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

}
