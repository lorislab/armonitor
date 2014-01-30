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
package org.lorislab.armonitor.mail.model;

import java.io.Serializable;

/**
 * The email attachment.
 *
 * @author Andrej Petras
 */
public class MailAttachment implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -1280878661047592250L;
    /**
     * The content.
     */
    private byte[] content;
    /**
     * The name.
     */
    private String name;
    /**
     * The content type.
     */
    private String contentType;

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

    /**
     * Gets the name.
     *
     * @return the name
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
     * Gets the content type.
     *
     * @return he content type.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets he content type.
     *
     * @param contentType he content type.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
