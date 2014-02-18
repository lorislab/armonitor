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
package org.lorislab.armonitor.web.rs.mapper;

import java.util.HashMap;
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreBuildParameter;
import org.lorislab.armonitor.store.model.enums.StoreBuildParameterType;
import org.lorislab.armonitor.web.rs.model.Build;

/**
 * The build mapper.
 *
 * @author Andrej Petras
 */
public class BuildMapper implements MapperService<StoreBuild, Build> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Build map(StoreBuild data, String profile) {
        Build result = new Build();
        result.guid = data.getGuid();
        result.agent = data.getAgent();
        result.artifactId = data.getArtifactId();
        result.build = data.getBuild();
        result.date = data.getDate();
        result.groupdId = data.getGroupdId();
        result.mavenVersion = data.getMavenVersion();
        result.scm = data.getScm();
        result.service = data.getService();
        result.uid = data.getUid();
        result.ver = data.getVer();
        
        
        if (profile != null && profile.equals("dashboard")) {
            if (data.getParameters() != null) {
                result.manifest = new HashMap<>();
                result.other = new HashMap<>();                
                for (StoreBuildParameter param : data.getParameters()) {
                    if (param.getType() == StoreBuildParameterType.MANIFEST) {
                        result.manifest.put(param.getName(), param.getValue());
                    } else {
                        result.other.put(param.getName(), param.getValue());
                    }
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreBuild update(StoreBuild entity, Build data, String profile) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreBuild create(Build data, String profile) {
        StoreBuild result = new StoreBuild();
        result.setGuid(data.guid);
        result = update(result, data, profile);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Build create(String profile) {
        StoreBuild tmp = new StoreBuild();
        return map(tmp, profile);
    }

}
