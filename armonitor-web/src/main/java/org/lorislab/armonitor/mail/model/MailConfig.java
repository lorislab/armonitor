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
    public String url = "http://localhost:8080/armonitor/rs/mail/public/resource";
    /**
     * Disabled or enabled the send email functionality.
     */
    public boolean enabled = false;
    /**
     * The default email locale.
     */
    public Locale locale = Locale.ENGLISH;
    /**
     * The from email.
     */
    public String from = "armonitor@localhost";
    /**
     * The content type.
     */
    public String contentType = "text/html;charset=\"UTF-8\"";
    /**
     * The content char-set.
     */
    public String contentCharset = "UTF-8";
    /**
     * The transfer encoding.
     */
    public String transferEncoding = "quoted-printable";
}
