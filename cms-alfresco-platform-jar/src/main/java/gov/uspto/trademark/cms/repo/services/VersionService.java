package gov.uspto.trademark.cms.repo.services;

import java.util.Collection;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.version.Version;

/**
 * Created by bgummadi on 5/2/2014.
 */
public interface VersionService {

    /**
     * Returns the nodeRef of a version for the given source nodeRef.
     *
     * @param source
     *            the source
     * @param versionNumber
     *            the version number
     * @return the versioned node ref
     */
    Version getVersionedNodeRef(NodeRef source, String versionNumber);

    /**
     * Return a list of versions for a given nodeRef.
     *
     * @param sourceRef
     *            the source ref
     * @return the versions
     */
    Collection<Version> getVersions(NodeRef sourceRef);

}