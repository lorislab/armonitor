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
package org.lorislab.armonitor.web.rs.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.spi.interception.MessageBodyWriterContext;
import org.jboss.resteasy.spi.interception.MessageBodyWriterInterceptor;
import org.lorislab.armonitor.web.rs.controller.MessageController;
import org.lorislab.armonitor.web.rs.model.MessageInfo;

/**
 * The message interceptor.
 * 
 * @author Andrej Petras
 */
@Provider
@ServerInterceptor
public class MessageInterceptor implements MessageBodyWriterInterceptor {

    /**
     * The message controller.
     */
    @Inject
    private MessageController controller;

    /**
     * {@inheritDoc}
     * 
     * Attach the message information to the header.
     */
    @Override
    public void write(MessageBodyWriterContext context) throws IOException, WebApplicationException {              
        ResteasyProviderFactory rpf = ResteasyProviderFactory.getInstance();
        MessageBodyWriter mw = rpf.getMessageBodyWriter(MessageInfo.class, null, null, context.getMediaType());        
        MessageInfo info = controller.getInfo();
        ByteArrayOutputStream os = new ByteArrayOutputStream();        
        mw.writeTo(info, null, null, null, context.getMediaType(), null, os);
        String out = new String(os.toByteArray(),"UTF-8");        
        MultivaluedMap<String, Object> headers = context.getHeaders();          
        headers.add(MessageInfo.class.getSimpleName(), out);
        context.proceed();
    }
}
