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
package org.lorislab.armonitor.mail.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import org.lorislab.armonitor.mail.model.Mail;

/**
 * The mail utility class.
 *
 * @author Andrej Petras
 */
public final class MailUtil {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(MailUtil.class.getName());
    /**
     * The content file prefix.
     */
    private static final String TEMPLATE_CONTENT = "content";
    /**
     * The subject file prefix.
     */
    private static final String TEMPLATE_SUBJECT = "subject";

    /**
     * The template directory.
     */
    private static final String TEMPLATE_DIR_NAME = "/templates/";
    /**
     * The path separator.
     */
    private static final String PATH_SEPARATOR = "/";

    /**
     * The free marker configuration.
     */
    private static final Configuration CFG = new Configuration();

    /**
     * Set up the template configuration.
     */
    static {
        CFG.setDefaultEncoding("UTF-8");

        // set up the template directory
        String templateDir = System.getProperty(MailUtil.class.getName());
        if (templateDir != null) {
            try {
                CFG.setDirectoryForTemplateLoading(new File(templateDir));
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Switch to default templates. Error open the mail template directory " + templateDir, ex);
                CFG.setClassForTemplateLoading(MailUtil.class, TEMPLATE_DIR_NAME);
            }
        } else {
            CFG.setClassForTemplateLoading(MailUtil.class, TEMPLATE_DIR_NAME);
        }
    }

    /**
     * The email object constant.
     */
    public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    /**
     * The email object constant.
     */
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * The email pattern.
     */
    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    /**
     * The pattern object.
     */
    private final static Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    /**
     * The default constructor.
     */
    private MailUtil() {
        // empty constructor
    }

    /**
     * Creates the email.
     *
     * @param locale the email locale.
     * @param from the email from address.
     * @param to the email to address.
     * @param template the email template.
     * @param contentCharset the content char-ser
     * @param transferEncoding the transfer encoding.
     * @param contentType the content type.
     * @param values the list of template values.
     * @return the created email.
     */
    public static Mail createEmail(Locale locale, String from, String to, String template, String contentCharset, String transferEncoding, String contentType, Object... values) {
        Mail result = new Mail();
        result.getTo().add(to);
        // set email locale
        result.setLocale(locale);
        // sets the attributes
        result.setTemplate(template);
        result.setContentType(contentType);
        result.setContentCharset(contentCharset);
        result.setTransferEncoding(transferEncoding);
        // add parameters
        result.setParameters(createTemplateValues(values));
        return result;
    }

    /**
     * Creates the template values.
     *
     * @param values the object.
     * @return the map of object for the template generator.
     */
    public static Map<String, Object> createTemplateValues(Object... values) {
        Map<String, Object> result = new HashMap<>();
        if (values != null) {
            for (Object item : values) {
                result.put(item.getClass().getSimpleName(), item);
            }
        }
        return result;
    }

    /**
     * Gets the file path from template.
     *
     * @param template
     * @param file
     * @return
     */
    public static final String getFilePathFromTemplate(String template, String file) {
        return TEMPLATE_DIR_NAME + template + PATH_SEPARATOR + file;
    }

    /**
     * Gets the mail content.
     *
     * @param template the mail template.
     * @param locale the mail locale.
     * @param parameters the mail parameters.
     * @return the mail content.
     * @throws Exception if the method fails.
     */
    public static String getMailContent(String template, Locale locale, Map<String, Object> parameters) throws Exception {
        return getContent(TEMPLATE_CONTENT, template, locale, parameters);
    }

    /**
     * Gets the mail subject.
     *
     * @param template the mail template.
     * @param locale the mail locale.
     * @param parameters the mail parameters.
     * @return the mail subject.
     * @throws Exception if the method fails.
     */
    public static String getMailSubject(String template, Locale locale, Map<String, Object> parameters) throws Exception {
        return getContent(TEMPLATE_SUBJECT, template, locale, parameters);
    }

    /**
     * Gets the email content.
     *
     * @param name the name of the content.
     * @param template the template.
     * @param parameters the list of parameters.
     * @return the email content.
     * @throws Exception if the method fails.
     */
    private static String getContent(String name, String template, Locale locale, Map<String, Object> parameters) throws Exception {

        String contentKey = name + "_" + locale.getLanguage();
        String key = template + "/" + contentKey + ".html";

        Template contentTmp = CFG.getTemplate(key);

        final StringWriter writer = new StringWriter();
        contentTmp.process(parameters, writer);
        String result = writer.toString();
        return result;
    }

    /**
     * Returns <code>true</code> if the email address has a valid format.
     *
     * @param email the email address.
     * @return <code>true</code> if the email address has a valid format.
     */
    public static boolean validate(String email) {
        return PATTERN.matcher(email).matches();
    }

    /**
     * Creates the list of addresses.
     *
     * @param addresses the set of addresses.
     * @return the list of addresses.
     * @throws Exception if the method fails.
     */
    public static Address[] createAddresses(Set<String> addresses) throws Exception {
        Address[] result = null;
        if (addresses != null && !addresses.isEmpty()) {
            List<Address> items = new ArrayList<>();
            for (String address : addresses) {
                Address item = createAddress(address);
                items.add(item);
            }

            if (!items.isEmpty()) {
                result = items.toArray(new Address[items.size()]);
            }
        }
        return result;
    }

    /**
     * Creates the email address object.
     *
     * @param address the address as a string.
     * @return the email address object.
     * @throws Exception if the method fails.
     */
    public static Address createAddress(String address) throws Exception {
        Address result = null;
        if (address != null) {
            result = new InternetAddress(address);
        }
        return result;
    }

}
