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
package org.lorislab.armonitor.agent.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.lorislab.armonitor.agent.rs.model.Version;
import org.lorislab.armonitor.mapper.MapperService;
import org.lorislab.armonitor.store.model.StoreBuild;
import org.lorislab.armonitor.store.model.StoreBuildParameter;
import org.lorislab.armonitor.store.model.enums.StoreBuildParameterType;

/**
 * The version mapper.
 *
 * @author Andrej Petras
 */
public class VersionMapper implements MapperService<Version, StoreBuild> {

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreBuild map(Version data, String profile) {
        StoreBuild result = new StoreBuild();
        result.setUid(data.uid);
        result.setBuild(data.build);
        result.setArtifactId(data.artifactId);
        result.setGroupdId(data.groupdId);
        result.setMavenVersion(data.version);
        result.setDate(data.date);
        result.setScm(data.scm);
        result.setService(data.service);
        result.setVer(data.ver);
        result.setParameters(new HashSet<StoreBuildParameter>());
        result.getParameters().addAll(createStoreBuildParameter(data.manifest, StoreBuildParameterType.MANIFEST));
        result.getParameters().addAll(createStoreBuildParameter(data.other, StoreBuildParameterType.OTHER));
        return result;
    }

    /**
     * Creates the list of store build parameters.
     *
     * @param params the map of parameters.
     * @param type the type of parameters.
     * @return the corresponding list of parameter.
     */
    private static List<StoreBuildParameter> createStoreBuildParameter(Map<String, String> params, StoreBuildParameterType type) {
        List<StoreBuildParameter> result = new ArrayList<>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                StoreBuildParameter param = new StoreBuildParameter();
                param.setType(type);
                param.setName(entry.getKey());
                param.setValue(entry.getValue());
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Version update(Version entity, StoreBuild data, String profile) {
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Version create(StoreBuild data, String profile) {
        return new Version();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreBuild create(String profile) {
        return new StoreBuild();
    }

}
