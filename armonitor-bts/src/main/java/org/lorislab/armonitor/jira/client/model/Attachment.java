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
package org.lorislab.armonitor.jira.client.model;

/**
 * The attachment.
 *
 * @author Andrej Petras
 */
public class Attachment {

    /**
     * The self.
     */
    private String self;
    /**
     * The file name.
     */
    private String filename;
    /**
     * The author.
     */
    private User author;
    /**
     * The created.
     */
    private String created;
    /**
     * The size.
     */
    private int size;
    /**
     * The mime type.
     */
    private String mimeType;
    /**
     * The content.
     */
    private String content;
    /**
     * The thumbnail.
     */
    private String thumbnail;

    /**
     * Gets the self.
     *
     * @return the self.
     */
    public String getSelf() {
        return self;
    }

    /**
     * Sets the self.
     *
     * @param self the self to set.
     */
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     * Gets the file name.
     *
     * @return the file name.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the file name.
     *
     * @param filename the filename to set.
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Gets the author.
     *
     * @return the author.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets the author.
     *
     * @param author the author to set.
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Gets the created.
     *
     * @return the created.
     */
    public String getCreated() {
        return created;
    }

    /**
     * Sets the created.
     *
     * @param created the created to set.
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * Gets the size.
     *
     * @return the size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size.
     *
     * @param size the size.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Gets the mime type.
     *
     * @return the mime type.
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the mime type.
     *
     * @param mimeType the mime type.
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Gets the content.
     *
     * @return the content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content the content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the thumbnail.
     *
     * @return the thumbnail.
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Sets the thumbnail.
     *
     * @param thumbnail the thumbnail to set.
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
