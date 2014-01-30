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
package org.lorislab.armonitor.mail.ejb;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.lorislab.armonitor.config.ejb.ConfigurationServiceBean;
import org.lorislab.armonitor.mail.model.Mail;
import org.lorislab.armonitor.mail.model.MailAttachment;
import org.lorislab.armonitor.mail.model.MailConfig;
import org.lorislab.armonitor.mail.model.MailTemplate;
import org.lorislab.armonitor.mail.model.MailTemplateResource;
import org.lorislab.armonitor.mail.util.MailUtil;
import org.lorislab.jel.base.util.FileUtil;

/**
 * The mail service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class MailServiceBean {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(MailServiceBean.class.getName());

    /**
     * The mail session.
     */
    @Resource(lookup = "java:jboss/mail/ArMonitorMailServer")
    private Session mailSession;

    /**
     * The configuration service.
     */
    @EJB
    private ConfigurationServiceBean configService;

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
            ResourceBundle bundle = ResourceBundle.getBundle(MailUtil.CONF_EMAIL, locale, this.getClass().getClassLoader());
            String contentFile = bundle.getString(MailUtil.TEMPLATE_CONTENT_FILE);
            String subjectFile = bundle.getString(MailUtil.TEMPLATE_SUBJECT_FILE);
            // load the content of the template
            subject = FileUtil.readFileAsString(MailUtil.getFilePathFromTemplate(name, contentFile), this.getClass().getClassLoader());
            content = FileUtil.readFileAsString(MailUtil.getFilePathFromTemplate(name, subjectFile), this.getClass().getClassLoader());
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
            data = FileUtil.readFileAsByteArray(MailUtil.getFilePathFromTemplate(template, name), this.getClass().getClassLoader());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error reading the template " + template + " resource " + name, ex);
        }
        // create template resource
        MailTemplateResource result = new MailTemplateResource();
        result.setName(name);
        result.setContent(data);
        return result;
    }

    /**
     * Sends the email.
     *
     * @param email the email object.
     */
    public void sendEmail(Mail email) {
        MailConfig config = configService.getConfiguration(MailConfig.class);
        // set email locale
        email.setLocale(config.locale);
        // set email from
        email.setFrom(config.from);
        // sets the attributes
        email.setContentType(config.contentType);
        email.setContentCharset(config.contentCharset);
        email.setTransferEncoding(config.transferEncoding);
        // send email
        sendMail(email);
    }

    /**
     * Sends the email.
     *
     * @param email the email object.
     */
    private void sendMail(Mail email) {
        try {

            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(MailUtil.createAddress(email.getFrom()));
            message.setRecipients(Message.RecipientType.TO, MailUtil.createAddresses(email.getTo()));

            MailTemplate template = loadMailTemplateByName(email.getTemplate(), email.getLocale());

            String subject = MailUtil.getEmailContent(template.getSubject(), email.getParameters());
            message.setSubject(subject);
            message.setSentDate(new Date());

            // BCC addresses
            if (!email.getBcc().isEmpty()) {
                message.setRecipients(Message.RecipientType.BCC, MailUtil.createAddresses(email.getBcc()));
            }

            // CC addresses
            if (!email.getCc().isEmpty()) {
                message.setRecipients(Message.RecipientType.CC, MailUtil.createAddresses(email.getCc()));
            }

            // Email content
            MimeBodyPart messagePart = new MimeBodyPart();
            String content = MailUtil.getEmailContent(template.getContent(), email.getParameters());
            messagePart.setText(content, email.getContentCharset());

            // Email header
            messagePart.setHeader(MailUtil.CONTENT_TRANSFER_ENCODING, email.getTransferEncoding());
            messagePart.setHeader(MailUtil.CONTENT_TYPE, email.getContentType());

            // create message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);

            List<MailAttachment> attachments = email.getAttachments();
            if (attachments != null && !attachments.isEmpty()) {
                for (MailAttachment attachment : attachments) {

                    MimeBodyPart attachmentPart = new MimeBodyPart();

                    byte[] fileContent = attachment.getContent();
                    attachmentPart.setContent(fileContent, attachment.getContentType());

                    attachmentPart.setFileName(attachment.getName());
                    multipart.addBodyPart(attachmentPart);
                }
            }
            message.setContent(multipart);

            Transport.send(message);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error by sending the email " + email.getGuid(), ex);
        }
    }
}
