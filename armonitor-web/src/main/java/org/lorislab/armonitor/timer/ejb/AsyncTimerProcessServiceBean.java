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

package org.lorislab.armonitor.timer.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.lorislab.armonitor.store.model.StoreSystem;

/**
 *
 * @author Andrej Petras
 */
@MessageDriven(name = "AsyncTimerProcessServiceBean",
activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/timerProcess"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class AsyncTimerProcessServiceBean implements MessageListener {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(AsyncTimerProcessServiceBean.class.getName());
    
    @EJB
    private TimerProcessServiceBean processService;
    
    @Override
    public void onMessage(Message message) {
        try {
            if (message != null) {
                if (message instanceof ObjectMessage) {
                    ObjectMessage msg = (ObjectMessage) message;
                    Object object = msg.getObject();
                    if (object != null) {
                        if (object instanceof StoreSystem) {
                            StoreSystem system = (StoreSystem) object;
                            processService.process(system);
                        } else {
                            LOGGER.log(Level.SEVERE, "Message content object not supported format!");
                        }
                    } else {
                        LOGGER.log(Level.SEVERE, "Missing object content!");
                    }
                } else {
                    LOGGER.log(Level.SEVERE, "Not supported asynchronne message!");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error by processing the user application message.", ex);
        }
    }
    
}
