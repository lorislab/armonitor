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
package org.lorislab.armonitor.mail.template.ejb;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mail.template.model.MailTemplate;
import org.lorislab.armonitor.mail.template.model.MailTemplateResource;
import org.lorislab.armonitor.mail.template.util.MailTemplateBuilder;
import org.lorislab.jel.base.util.FileUtil;

/**
 * The mail template service bean.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class MailTemplateServiceBean {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(MailTemplateServiceBean.class.getName());
    /**
     * The template content file.
     */
    private static final String TEMPLATE_CONTENT_FILE = "email.content";
    /**
     * The template subject file.
     */
    private static final String TEMPLATE_SUBJECT_FILE = "email.subject";

    /**
     * The email configuration file.
     */
    private static final String CONF_EMAIL = "email.properties";

    /**
     * Loads the mail template by name.
     *
     * @param name the template name.
     * @param locale the locale.
     * @return the mail template.
     */
    public MailTemplate loadMailTemplateByName(String name, Locale locale) {

        String subject = null;
        String content = null;

        try {
            // load the email configuration: content for the locale.
            ResourceBundle bundle = ResourceBundle.getBundle(CONF_EMAIL, locale, this.getClass().getClassLoader());
            String contentFile = bundle.getString(TEMPLATE_CONTENT_FILE);
            String subjectFile = bundle.getString(TEMPLATE_SUBJECT_FILE);
            // load the content of the template
            subject = FileUtil.readFileAsString(MailTemplateBuilder.getFilePathFromTemplate(name, contentFile), this.getClass().getClassLoader());
            content = FileUtil.readFileAsString(MailTemplateBuilder.getFilePathFromTemplate(name, subjectFile), this.getClass().getClassLoader());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error reading the templates", ex);
        }
        // create template
        MailTemplate template = new MailTemplate();
        template.setName(name);
        template.setSubject(subject);
        template.setContent(content);
        return template;
    }

    /**
     * Load the mail template resource.
     *
     * @param template the template name.
     * @param name the resource name.
     * @return the mail template resource.
     */
    public MailTemplateResource loadMailTemplateResource(String template, String name) {

        byte[] data = null;
        try {
            // load the file
            data = FileUtil.readFileAsByteArray(MailTemplateBuilder.getFilePathFromTemplate(template, name), this.getClass().getClassLoader());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error reading the template " + template + " resource " + name, ex);
        }
        // create template resource
        MailTemplateResource result = new MailTemplateResource();
        result.setName(name);
        result.setContent(data);
        return result;
    }

}
