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
import java.util.Set;
import org.kohsuke.MetaInfServices;
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreBuildParameter;
import org.lorislab.armonitor.store.model.enums.StoreBuildParameterType;
import org.lorislab.armonitor.web.rs.model.Build;
import org.lorislab.armonitor.util.LinkUtil;

/**
 * The build mapper.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class BuildMapper implements MapperService<StoreBuild, Build> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Build map(StoreBuild data, Set<String> profiles) {
        Build result = new Build();
        result.guid = data.getGuid();
        result.agent = data.getAgent();
        result.artifactId = data.getArtifactId();
        result.build = data.getBuild();
        result.date = data.getDate();
        result.groupId = data.getGroupId();
        result.mavenVersion = data.getMavenVersion();
        result.scm = data.getScm();
        result.service = data.getService();
        result.uid = data.getUid();
        result.ver = data.getVer();
        result.key = data.getKey();
        if (profiles.contains("link")) {
            if (data.getApplication() != null) {
                result.link = LinkUtil.createLink(data.getApplication().getRepoLink(), data);
            }
        }

        if (profiles.contains("params")) {
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
    public StoreBuild update(StoreBuild entity, Build data, Set<String> profiles) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreBuild create(Build data, Set<String> profiles) {
        StoreBuild result = new StoreBuild();
        result.setGuid(data.guid);
        result = update(result, data, profiles);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Build create(Set<String> profiles) {
        StoreBuild tmp = new StoreBuild();
        return map(tmp, profiles);
    }

}
