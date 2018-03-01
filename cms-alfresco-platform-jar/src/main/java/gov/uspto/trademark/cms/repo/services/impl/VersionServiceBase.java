package gov.uspto.trademark.cms.repo.services.impl;

import java.util.Collection;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.version.Version;

import gov.uspto.trademark.cms.repo.services.VersionService;

/**
 * Created by bgummadi on 5/2/2014.
 */
public class VersionServiceBase extends AbstractBaseService implements VersionService {

    /**
     * Returns the nodeRef of a version for the given source nodeRef.
     *
     * @param sourceRef
     *            the source ref
     * @param versionNumber
     *            the version number
     * @return the versioned node ref
     */
    @Override
    public Version getVersionedNodeRef(NodeRef sourceRef, String versionNumber) {
        return this.serviceRegistry.getVersionService().getVersionHistory(sourceRef).getVersion(versionNumber);
    }

    /**
     * Return a list of versions for a given nodeRef.
     *
     * @param sourceRef
     *            the source ref
     * @return the versions
     */
    @Override
    public Collection<Version> getVersions(NodeRef sourceRef) {
        return this.serviceRegistry.getVersionService().getVersionHistory(sourceRef).getAllVersions();
    }
}
