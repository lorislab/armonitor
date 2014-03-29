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
package org.lorislab.armonitor.process.ejb;

import java.util.logging.Level;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.mail.ejb.MailServiceBean;
import org.lorislab.armonitor.mail.model.Mail;
import org.lorislab.armonitor.process.resources.ErrorKeys;
import org.lorislab.jel.ejb.exception.ServiceException;

/**
 * The test service.
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class TestServiceBean {

    /**
     * The test email template.
     */
    private static final String MAIL_TEST_TEMPLATE = "test";
    
    
    /**
     * The mail service.
     */
    @EJB
    private MailServiceBean mailService;

    /**
     * Sends the test email to the mail address.
     *
     * @param email the email address.
     * @throws ServiceException if the method fails.
     */
    public void sendTestEmail(String email) throws ServiceException {
        if (email == null || email.isEmpty()) {
            throw new ServiceException(ErrorKeys.ERROR_EMAIL_IS_NULL);
        }
        try {
            Mail mail = new Mail();
            mail.getTo().add(email);
            mail.setTemplate(MAIL_TEST_TEMPLATE);                
            mailService.sendEmail(mail);
        } catch (Exception ex) {
            throw new ServiceException(ErrorKeys.ERROR_SEND_EMAIL, ex, email);
        }
    }
}
