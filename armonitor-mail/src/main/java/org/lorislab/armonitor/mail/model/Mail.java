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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * The email.
 * 
 * @author Andrej Petras
 */
public class Mail implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -8683230089360406899L;
    /**
     * The from address.
     */
    private String from;
    /**
     * The email GUID.
     */
    private final String guid = UUID.randomUUID().toString();
    ;
    /**
     * The email template.
     */
    private String template;
    /**
     * The email to address.
     */
    private Set<String> to = new HashSet<>();
    /**
     * The BCC addresses.
     */
    private Set<String> bcc = new HashSet<>();
    /**
     * The CC addresses.
     */
    private Set<String> cc = new HashSet<>();
    /**
     * The list of email attachments.
     */
    private List<MailAttachment> attachments = new ArrayList<>();
    /**
     * The email locale.
     */
    private Locale locale = Locale.ENGLISH;
    /**
     * The list of parameters for the template.
     */
    private Map<String, Object> parameters = new HashMap<>();
    /**
     * The email content char-set.
     */
    private String contentCharset;
    /**
     * The email transfer encoding.
     */
    private String transferEncoding;
    /**
     * The email content type.
     */
    private String contentType;

    /**
     * Gets the content char-set.
     *
     * @return the content char-set.
     */
    public String getContentCharset() {
        return contentCharset;
    }

    /**
     * Sets the content char-set.
     *
     * @param contentCharset the content char-set.
     */
    public void setContentCharset(String contentCharset) {
        this.contentCharset = contentCharset;
    }

    /**
     * Gets the transfer encoding.
     *
     * @return the transfer encoding.
     */
    public String getTransferEncoding() {
        return transferEncoding;
    }

    /**
     * Sets the transfer encoding.
     *
     * @param transferEncoding the transfer encoding.
     */
    public void setTransferEncoding(String transferEncoding) {
        this.transferEncoding = transferEncoding;
    }

    /**
     * Gets the content type.
     *
     * @return the content type.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type.
     *
     * @param contentType the content type.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Gets the from address.
     *
     * @return the from address.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the from address.
     *
     * @param from the from address.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Gets the email GUID.
     *
     * @return the email GUID.
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Gets the set of email to addresses.
     *
     * @return the set of email to addresses.
     */
    public Set<String> getTo() {
        return to;
    }

    /**
     * Gets the list of email attachments.
     *
     * @return the list of email attachments.
     */
    public List<MailAttachment> getAttachments() {
        return attachments;
    }

    /**
     * Sets the list of email attachments.
     *
     * @param attachments the list of email attachments.
     */
    public void setAttachments(List<MailAttachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * Sets the set of email addresses.
     *
     * @param to the set of email addresses.
     */
    public void setTo(Set<String> to) {
        this.to = to;
    }

    /**
     * Gets the set of BBC email addresses.
     *
     * @return the set of BBC email addresses.
     */
    public Set<String> getBcc() {
        return bcc;
    }

    /**
     * Sets the set of BBC email addresses.
     *
     * @param bcc the set of BBC email addresses.
     */
    public void setBcc(Set<String> bcc) {
        this.bcc = bcc;
    }

    /**
     * Gets the set of CC email addresses.
     *
     * @return the set of CC email addresses.
     */
    public Set<String> getCc() {
        return cc;
    }

    /**
     * Sets the set of CC email addresses.
     *
     * @param cc the set of CC email addresses.
     */
    public void setCc(Set<String> cc) {
        this.cc = cc;
    }

    /**
     * Gets the email locale.
     *
     * @return the email locale.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Sets the email locale.
     *
     * @param locale the email locale.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Gets the email template.
     *
     * @return the email template.
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Sets the email template.
     *
     * @param template the email template.
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Gets the template parameters.
     *
     * @return the template parameters.
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * Sets the template parameters.
     *
     * @param parameters the template parameters.
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

}
