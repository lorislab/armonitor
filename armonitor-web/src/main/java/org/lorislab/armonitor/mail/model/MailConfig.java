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

import java.util.Locale;

/**
 * The mail configuration.
 *
 * @author Andrej Petras
 */
public class MailConfig {

    /**
     * The server resource URL.
     */
    private String url = "http://localhost:8080/armonitor/rs/mail/public/resource";
    /**
     * Disabled or enabled the send email functionality.
     */
    private boolean enabled = false;
    /**
     * The default email locale.
     */
    private Locale locale = Locale.ENGLISH;
    /**
     * The from email.
     */
    private String from = "armonitor@localhost";
    /**
     * The content type.
     */
    private String contentType = "text/html;charset=\"UTF-8\"";
    /**
     * The content char-set.
     */
    private String contentCharset = "UTF-8";
    /**
     * The transfer encoding.
     */
    private String transferEncoding = "quoted-printable";

    /**
     * Gets the URL.
     * 
     * @return the URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL.
     * 
     * @param url the URL to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the enabled.
     * 
     * @return the enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled.
     * 
     * @param enabled the enabled to set.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets the locale.
     * 
     * @return the locale.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Sets the locale.
     * 
     * @param locale the locale to set.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Gets the from.
     * 
     * @return the from.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the from.
     * 
     * @param from the from to set.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Gets the content type.
     * 
     * @return the contentType.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type.
     * 
     * @param contentType the contentType to set.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Gets the content char-set.
     * 
     * @return the contentCharset.
     */
    public String getContentCharset() {
        return contentCharset;
    }

    /**
     * Sets the content char-set.
     * 
     * @param contentCharset the contentCharset to set
     */
    public void setContentCharset(String contentCharset) {
        this.contentCharset = contentCharset;
    }

    /**
     * Gets the transfer encoding.
     * 
     * @return the transferEncoding.
     */
    public String getTransferEncoding() {
        return transferEncoding;
    }

    /**
     * Sets the transfer encoding.
     * 
     * @param transferEncoding the transferEncoding to set.
     */
    public void setTransferEncoding(String transferEncoding) {
        this.transferEncoding = transferEncoding;
    }
    
    
}
