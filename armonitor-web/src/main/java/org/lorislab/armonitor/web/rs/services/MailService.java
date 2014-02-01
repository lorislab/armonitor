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

package org.lorislab.armonitor.web.rs.services;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.config.ejb.ConfigurationServiceBean;
import org.lorislab.armonitor.mail.ejb.MailServiceBean;
import org.lorislab.armonitor.mail.model.MailConfig;
import org.lorislab.armonitor.mail.model.MailTemplateResource;

/**
 * The mail rest-service.
 * 
 * @author Andrej Petras
 */
@Path("mail")
public class MailService {
    
    @EJB
    private ConfigurationServiceBean configService;
    
    @EJB
    private MailServiceBean mailService;
    
    /**
     * Gets the mail template image (resource).
     *
     * @param template the template name.
     * @param name the resource name.
     *
     * @return the resource content.
     */
    @GET
    @Path("public/{template}/{name}")
    @Produces({"image/jpeg", "image/png", "image/gif"})
    public byte[] getMailResource(@PathParam("template") String template, @PathParam("name") String name) {
        byte[] result = null;
        MailTemplateResource resource = mailService.loadMailTemplateResource(template, name);
        if (resource != null) {
            result = resource.getContent();
        }
        return result;
    }
    
    @Path("cf")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MailConfig getConfig() {
        return configService.getConfiguration(MailConfig.class);
    }    
    
    @Path("cf")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public MailConfig setConfig(MailConfig jiraConfig) {
        return configService.setConfiguration(jiraConfig);
    }    
}
