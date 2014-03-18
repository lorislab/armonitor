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
package org.lorislab.armonitor.web.rs.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.lorislab.armonitor.web.rs.model.Message;
import org.lorislab.armonitor.web.rs.model.MessageInfo;
import org.lorislab.armonitor.web.rs.model.MessageType;
import org.lorislab.jel.base.resources.ResourceManager;
import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;

/**
 * The message controller
 *
 * @author Andrej Petras
 */
@Named
@SessionScoped
@CdiServiceMethod
public class MessageController implements Serializable {

    private static final long serialVersionUID = 2983125711919761564L;

    private final Locale locale = Locale.ENGLISH;
    
    private final MessageInfo info = new MessageInfo();

    private final Map<String, Message> messages = new HashMap<>();

    public Collection<Message> getMessages() {
        return messages.values();
    }
    
    public void deleteMessage(String id) {
        if (id != null) {
            messages.remove(id);
        }
    }
            
    public void addMsg(Enum key ) {
        addMessage(key, null); 
    }
    
    public void addMsgRef(Enum key, Serializable ref ) {       
        addMessage(key, ref); 
    }
    
    public void addMsgArg(Enum key, Serializable... arguments ) {        
        addMessage(key, null, arguments);        
    }

    public void addMessage(Enum key, Serializable ref, Serializable... arguments ) {
        Message rm = new Message();
        rm.id = UUID.randomUUID().toString();
        rm.date = new Date();
        rm.key = key;
        rm.params = arguments;
        rm.ref = ref;
        rm.type = MessageType.INFO;
        rm.message = ResourceManager.getMessage(rm.key, this.locale, rm.params);        
        this.messages.put(rm.id, rm);        
        info.status = MessageType.INFO;
        info.msg = rm;
        info.size = this.messages.size();
    }
    
    public MessageInfo getInfo() {
        return info;
    }

    public MessageInfo close() {
        info.msg = null;
        return info;
    }

    public MessageInfo trash() {
        this.messages.clear();
        info.msg = null;
        info.size = messages.size();
        info.status = null;
        return info;
    }

    public MessageInfo trash(String id) {
        messages.remove(id);
        info.size = messages.size();
        return info;
    }
}
