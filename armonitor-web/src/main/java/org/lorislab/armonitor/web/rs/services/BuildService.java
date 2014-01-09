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

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.lorislab.armonitor.store.criteria.StoreBuildCriteria;
import org.lorislab.armonitor.store.ejb.StoreBuildServiceBean;
import org.lorislab.armonitor.store.model.StoreBuild;

/**
 *
 * @author Andrej Petras
 */
@Path("build")
public class BuildService {
    
    @EJB
    private StoreBuildServiceBean service;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<StoreBuild> get() {
        return service.getBuilds();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public List<StoreBuild> get(StoreBuildCriteria criteria) {
        return service.getBuilds(criteria);
    }
}
