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

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.armonitor.agent.ejb.AgentClientServiceBean;
import org.lorislab.armonitor.bts.model.BtsCriteria;
import org.lorislab.armonitor.bts.service.BtsService;
import org.lorislab.armonitor.mail.ejb.MailServiceBean;
import org.lorislab.armonitor.mail.model.Mail;
import org.lorislab.armonitor.process.resources.ErrorKeys;
import org.lorislab.armonitor.scm.model.ScmCriteria;
import org.lorislab.armonitor.scm.service.ScmService;
import org.lorislab.armonitor.store.ejb.StoreAgentServiceBean;
import org.lorislab.armonitor.store.ejb.StoreBTSystemServiceBean;
import org.lorislab.armonitor.store.ejb.StoreSCMSystemServiceBean;
import org.lorislab.armonitor.store.model.StoreAgent;
import org.lorislab.armonitor.store.model.StoreBTSystem;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreSCMSystem;
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
     * The store SCM system service.
     */
    @EJB
    private StoreSCMSystemServiceBean scmService;

    /**
     * The store bug tracking system service.
     */
    @EJB
    private StoreBTSystemServiceBean btsService;

    /**
     * The agent client service.
     */
    @EJB
    private AgentClientServiceBean agentClientService;

    /**
     * The store agent service.
     */
    @EJB
    private StoreAgentServiceBean agentService;

    /**
     * Gets the agent services list.
     *
     * @param guid the GUID of the agent.
     *
     * @return the build of the agent.
     * @throws ServiceException if the method fails.
     */
    public List<StoreBuild> getAgentServices(String guid) throws ServiceException {
        List<StoreBuild> result = null;
        StoreAgent agent = agentService.getAgent(guid);
        if (agent == null) {
            throw new ServiceException(ErrorKeys.NO_AGENT_FOUND, guid);
        }

        try {
            result = agentClientService.getBuilds(agent);
        } catch (Exception ex) {
            throw new ServiceException(ErrorKeys.ERROR_CREATE_AGENT_CONNECTION, guid, ex, agent.getUrl(), ex.getMessage());
        }
        return result;
    }
    
    /**
     * Tests the agent connection.
     *
     * @param guid the GUID of the agent.
     *
     * @return the build of the agent.
     * @throws ServiceException if the method fails.
     */
    public StoreBuild testAgent(String guid) throws ServiceException {
        StoreBuild result = null;
        StoreAgent agent = agentService.getAgent(guid);
        if (agent == null) {
            throw new ServiceException(ErrorKeys.NO_AGENT_FOUND, guid);
        }

        try {
            result = agentClientService.getAgentBuild(agent);
        } catch (Exception ex) {
            throw new ServiceException(ErrorKeys.ERROR_CREATE_AGENT_CONNECTION, guid, ex, agent.getUrl(), ex.getMessage());
        }
        return result;
    }

    /**
     * Tests the BTS system connection.
     *
     * @param guid the GUID of the BTS system.
     * @throws ServiceException if the method fails.
     */
    public void testBTS(String guid) throws ServiceException {
        StoreBTSystem bts = btsService.getBTSystem(guid);
        if (bts == null) {
            throw new ServiceException(ErrorKeys.NO_BT_SYSTEM_FOUND, guid);
        }

        try {
            BtsCriteria bc = new BtsCriteria();
            bc.setServer(bts.getServer());
            bc.setUser(bts.getUser());
            bc.setPassword(bts.getPassword());
            bc.setAuth(bts.isAuth());
            bc.setType(bts.getType());
            BtsService.testConnection(bc);
        } catch (Exception ex) {
            throw new ServiceException(ErrorKeys.ERROR_CREATE_BT_CONNECTION, guid, ex, bts.getServer(), ex.getMessage());
        }
    }

    /**
     * Tests the SCM system connection.
     *
     * @param guid the GUID of the SCM system.
     * @throws ServiceException if the method fails.
     */
    public void testSCM(String guid) throws ServiceException {
        StoreSCMSystem scm = scmService.getSCMSystem(guid);
        if (scm == null) {
            throw new ServiceException(ErrorKeys.NO_SCM_SYSTEM_FOUND, guid);
        }

        try {
            ScmCriteria criteria = new ScmCriteria();
            criteria.setType(scm.getType());
            criteria.setServer(scm.getServer());
            criteria.setAuth(scm.isAuth());
            criteria.setUser(scm.getUser());
            criteria.setPassword(scm.getPassword());
            criteria.setReadTimeout(scm.getReadTimeout());
            criteria.setConnectionTimeout(scm.getConnectionTimeout());
            ScmService.testConnection(criteria);
        } catch (Exception ex) {
            throw new ServiceException(ErrorKeys.ERROR_CREATE_SCM_CONNECTION, guid, ex, scm.getServer(), ex.getMessage());
        }
    }

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

    public void testBTSAccess(String guid, String project) throws ServiceException {
        StoreBTSystem bts = btsService.getBTSystem(guid);
        if (bts == null) {
            throw new ServiceException(ErrorKeys.NO_BT_SYSTEM_FOUND, guid);
        }

        try {
            BtsCriteria bc = new BtsCriteria();
            bc.setProject(project);
            bc.setServer(bts.getServer());
            bc.setUser(bts.getUser());
            bc.setPassword(bts.getPassword());
            bc.setAuth(bts.isAuth());
            bc.setType(bts.getType());
            BtsService.testProjectAccess(bc);
        } catch (Exception ex) {
            throw new ServiceException(ErrorKeys.ERROR_CREATE_BT_CONNECTION, guid, ex, bts.getServer(), ex.getMessage());
        }
    }
}
