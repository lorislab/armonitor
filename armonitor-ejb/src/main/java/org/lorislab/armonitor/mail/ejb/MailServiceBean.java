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
     * Load the mail template resource.
     *
     * @param template the template name.
     * @param name the resource name.
     * @param locale the locale.
     * @return the mail template resource.
     */
    public MailTemplateResource loadMailTemplateResource(String template, String name, Locale locale) {

        byte[] data = null;
        try {
            // load the file
            //FIXME: load only from class path
            String file = MailUtil.getFilePathFromTemplate(template, name, locale);
            data = FileUtil.readFileAsByteArray(file, this.getClass().getClassLoader());
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

        if (config.isEnabled()) {
            // set email locale
            email.setLocale(config.getLocale());
            // set email from
            email.setFrom(config.getFrom());
            // sets the attributes
            email.setContentType(config.getContentType());
            email.setContentCharset(config.getContentCharset());
            email.setTransferEncoding(config.getTransferEncoding());
            // add special parameter
            email.getParameters().put(MailConfig.class.getSimpleName(), config);
            email.getParameters().put(Mail.class.getSimpleName(), email);
            // send email
            sendMail(email);
        } else {
            LOGGER.log(Level.FINE, "The send email is disabled!");
        }
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


            String subject = MailUtil.getMailSubject(email.getTemplate(), email.getLocale(), email.getParameters());
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
            String content = MailUtil.getMailContent(email.getTemplate(), email.getLocale(), email.getParameters());
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
