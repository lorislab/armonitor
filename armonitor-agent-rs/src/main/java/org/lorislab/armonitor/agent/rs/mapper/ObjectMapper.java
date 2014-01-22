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
package org.lorislab.armonitor.agent.rs.mapper;

import java.util.UUID;
import org.lorislab.armonitor.agent.model.SearchResultItem;
import org.lorislab.armonitor.agent.rs.model.Request;
import org.lorislab.armonitor.agent.rs.model.Version;
import org.lorislab.armonitor.arm.model.Arm;
import org.lorislab.armonitor.agent.model.SearchCriteria;

/**
 * The object mapper.
 *
 * @author Andrej Petras
 */
public final class ObjectMapper {

    /**
     * The default constructor.
     */
    private ObjectMapper() {
        // empty constructor
    }

    /**
     * Creates the search criteria from the request.
     *
     * @param request the client request.
     * @return the search criteria.
     */
    public static SearchCriteria createCriteria(Request request) {
        SearchCriteria criteria = new SearchCriteria();
        criteria.setManifest(request.manifest);
        criteria.setService(request.service);
        return criteria;
    }

    /**
     * Creates the version.
     *
     * @return the version.
     */
    public static Version create() {
        Version result = new Version();
        result.uid = UUID.randomUUID().toString();
        return result;
    }

    /**
     * Update the version with the ARM model.
     *
     * @param version the version.
     * @param arm the arm model.
     * @return the version.
     */
    public static Version update(Version version, Arm arm) {
        if (arm != null && version != null) {
            version.date = arm.getDate();

            // add maven attributes
            version.groupdId = arm.getGroupId();
            version.artifactId = arm.getArtifactId();
            version.version = arm.getVersion();

            // add release attribtues
            version.release = arm.getRelease();
            version.scm = arm.getScm();
            version.build = arm.getBuild();

            // add ARM other attributes
            version.other = arm.getOther();
        }
        return version;
    }

    /**
     * Updates the version with the search result item.
     *
     * @param version the version.
     * @param release the search result item.
     * @return the version.
     */
    public static Version update(Version version, SearchResultItem release) {
        if (version != null && release != null) {
            version.service = release.getService();
            // add arm
            version = update(version, release.getArm());
            // add manifest
            version.manifest = release.getManifest();
        }
        return version;
    }

    /**
     * Creates the version from the release model.
     *
     * @param release the release model.
     * @param request the request.
     * @return the version model.
     */
    public static Version createVersion(Request request, SearchResultItem release) {
        Version result = create();
        if (request.uid != null) {
            result.uid = request.uid;
        }
        result = update(result, release);
        return result;
    }
}
