package gov.uspto.trademark.cms.repo.services;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.model.Repository;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import gov.uspto.trademark.cms.repo.model.AbstractBaseType;

/**
 * Service to provide base operations on Alfresco.
 *
 * @author bgummadi
 */
public interface BaseService {

    /**
     * Sets the service registry to access Alfresco services.
     *
     * @param serviceRegistry
     *            the new service registry
     */
    void setServiceRegistry(ServiceRegistry serviceRegistry);

    /**
     * Sets RepositoryHelper which provides helper methods to communicate with
     * Alfresco.
     *
     * @param repositoryHelper
     *            the new repository helper
     */
    void setRepositoryHelper(Repository repositoryHelper);

    /**
     * Returns a property value for a given NodeRef.
     *
     * @param nodeRef
     *            the node ref
     * @param qname
     *            the qname
     * @return the property
     */
    Serializable getProperty(NodeRef nodeRef, QName qname);

    /**
     * Returns all properties for a given NodeRef.
     *
     * @param nodeRef
     *            the node ref
     * @return the node properties
     */
    Map<QName, Serializable> getNodeProperties(NodeRef nodeRef);

    /**
     * Returns a reader to read the content.
     *
     * @param nodeRef
     *            the node ref
     * @param qname
     *            the qname
     * @return the content reader
     */
    ContentReader getContentReader(NodeRef nodeRef, QName qname);

    /**
     * Returns a byte array representation of a type with all the properties for
     * a given NodeRef.
     *
     * @param <T>
     *            the generic type
     * @param nodeRef
     *            the node ref
     * @param target
     *            the target
     * @return the client type json bytes
     */
    <T extends AbstractBaseType> byte[] getClientTypeJsonBytes(NodeRef nodeRef, Class<T> target);

    /**
     * Returns a type with all the properties for a given NodeRef.
     *
     * @param <T>
     *            the generic type
     * @param nodeRef
     *            the node ref
     * @param target
     *            the target
     * @return the t
     */
    <T extends AbstractBaseType> T generateOutGoingDTO(NodeRef nodeRef, Class<T> target);

    /**
     * Returns the QName for a given nodeRef.
     *
     * @param nodeRef
     *            the node ref
     * @return the type
     */
    QName getType(NodeRef nodeRef);

    /**
     * Checks if given NodeRef has the given aspect.
     *
     * @param nodeRef
     *            the node ref
     * @param aspect
     *            the aspect
     * @return true, if successful
     */
    boolean hasAspect(NodeRef nodeRef, QName aspect);

    /**
     * Checks if the given path exists under company home.
     *
     * @param documentTypeList
     *            the document type list
     * @param path
     *            the path
     * @return true if it exists
     */
    NodeRef fileExists(List<QName> documentTypeList, String... path);

    /**
     * Checks if the given path exists under library.
     *
     * @param path
     *            the path
     * @return true if it exists
     */
    NodeRef libraryFileExists(String... path);

}