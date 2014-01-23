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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The mail template.
 *
 * @author Andrej Petras
 */
public class MailTemplate {

    /**
     * The name.
     */
    private String name;
    /**
     * The subject.
     */
    private String subject;
    /**
     * The content.
     */
    private String content;
    /**
     * The locale of the template.
     */
    private Locale locale;
    /**
     * The list of template resources.
     */
    private List<MailTemplateResource> resources = new ArrayList<>();

    /**
     * Sets the mail template resource.
     *
     * @param resources the mail template resource.
     */
    public void setResources(List<MailTemplateResource> resources) {
        this.resources = resources;
    }

    /**
     * Gets the mail template resource.
     *
     * @return the mail template resource.
     */
    public List<MailTemplateResource> getResources() {
        return resources;
    }

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
     * Gets the subject.
     *
     * @return the subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param subject the subject.
     */
    public void setSubject(String subject) {
        this.subject = subject;
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
     * @param locale the locale.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
