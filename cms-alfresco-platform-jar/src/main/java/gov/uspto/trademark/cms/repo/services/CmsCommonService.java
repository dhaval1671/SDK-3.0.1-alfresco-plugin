package gov.uspto.trademark.cms.repo.services;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.namespace.QName;

import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * Service to provide base operations on Alfresco.
 *
 * @author bgummadi
 */
public interface CmsCommonService {

    /**
     * This method is used to create the document. This will be invoked from the
     * CreateWebScript and has to be implemented in the doctype specific service
     * class.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param properties
     *            the properties
     * @return NodeRef - created NodeRef
     */
    TmngAlfResponse create(final String id, final String fileName, final ContentItem content,
            final Map<String, Serializable> properties);

    /**
     * This method is used to update the document. This will be invoked from the
     * UpdateWebScript and has to be implemented in the doc type specific
     * service.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param properties
     *            the properties
     * @return updated noderef
     */
    TmngAlfResponse update(final String id, final String fileName, final ContentItem content,
            final Map<String, Serializable> properties);

    /**
     * This method deletes the given document.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @return the tmng alf response
     */
    TmngAlfResponse delete(final String id, final String fileName);

    /**
     * This method is used to delete the given alfresco document based on id and
     * file name.
     *
     * @param <T>
     *            the generic type
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param type
     *            the type
     * @return the tmng alf response
     */
    <T extends AbstractBaseType> TmngAlfResponse hardDeleteDocument(String id, String fileName, QName type);

    /**
     * This method is used to update the document metadata . This will be
     * invoked from the UpdateWebScript and has to be implemented in the doc
     * type specific service.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param properties
     *            the properties
     * @return updated noderef
     */
    TmngAlfResponse updateMetadata(final String id, final String fileName, final Map<String, Serializable> properties);

    /**
     * This method is used to create the document for the given document type.
     *
     * @param <T>
     *            the generic type
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param type
     *            the type
     * @param properties
     *            the properties
     * @param docTypeClass
     *            the doc type class
     * @return created noderef
     */
    <T extends AbstractBaseType> TmngAlfResponse createDocument(String id, String fileName, ContentItem content, QName type,
            Map<String, Serializable> properties, Class<T> docTypeClass);

    /**
     * Creates a document if it doesn't exist. Replaces if the file exists
     *
     * @param id
     * @param fileName
     * @param content
     * @param type
     * @param properties
     * @param docTypeClass
     * @param <T>
     * @return
     */
    <T extends AbstractBaseType> TmngAlfResponse createOrUpdateDocument(String id, String fileName, ContentItem content,
            QName type, Map<String, Serializable> properties, Class<T> docTypeClass);

    /**
     * This method is used to create the document and perform the post create
     * operations implemented by listener.
     *
     * @param <T>
     *            the generic type
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param type
     *            the type
     * @param properties
     *            the properties
     * @param docTypeClass
     *            the doc type class
     * @param docCreateListener
     *            the doc create listener
     * @return the tmng alf response
     */
    <T extends AbstractBaseType> TmngAlfResponse createDocument(final String id, final String fileName, final ContentItem content,
            final QName type, final Map<String, Serializable> properties, Class<T> docTypeClass,
            final DocumentCreateListener docCreateListener);

    /**
     * This method is used to create the document and perform the post create
     * operations implemented by listener.
     *
     * @param <T>
     *            the generic type
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param type
     *            the type
     * @param properties
     *            the properties
     * @param docTypeClass
     *            the doc type class
     * @param docCreateListener
     *            the doc create listener
     * @return the tmng alf response
     */
    <T extends AbstractBaseType> TmngAlfResponse createDocument(final String id, final String fileName, final ContentItem content,
            final QName type, final Map<String, Serializable> properties, Class<T> docTypeClass,
            final DocumentCreateListener docCreateListener, CmsNodeCreator cmsNodeCreator);

    /**
     * This method is used to update the document for the given document type.
     *
     * @param <T>
     *            the generic type
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param type
     *            the type
     * @param properties
     *            the properties
     * @param docTypeClass
     *            the doc type class
     * @return updated noderef
     */
    <T extends AbstractBaseType> TmngAlfResponse updateDocument(String id, String fileName, ContentItem content, QName type,
            Map<String, Serializable> properties, Class<T> docTypeClass);

    /**
     * This method is used to retrieve the content for a document. This will be
     * invoked from RetrieveContentWebScript and will be implemented in doc type
     * specific service class.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param versionNumber
     *            the version number
     * @return content reader
     */
    ContentReader retrieveContent(String id, String fileName, String versionNumber);

    ContentReader retrieveContent(String id, String fileName, String versionNumber, CmsDataFilter dataFilter);

    /**
     * This method is used to retrieve the content for the given serial number
     * and file Name. This uses the given node locator and QName.
     *
     * @param cmsNodeLocator
     *            the cms node locator
     * @param docQName
     *            the doc q name
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param versionNumber
     *            the version number
     * @return content reader
     */
    ContentReader retrieveContent(CmsNodeLocator cmsNodeLocator, QName docQName, String id, String fileName,
            String versionNumber);

    /**
     * @Title: retrieveContent @Description: @param cmsNodeLocator @param
     * docQName @param serialNumber @param fileName @param versionNumber @param
     * dataFilter @return @return ContentReader @throws
     */
    ContentReader retrieveContent(CmsNodeLocator cmsNodeLocator, QName docQName, String serialNumber, String fileName,
            String versionNumber, CmsDataFilter dataFilter);

    /**
     * This method is used to retrieve the meta data for the given serial number
     * and file name.
     *
     * @param cmsNodeLocator
     *            the cms node locator
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param versionNumber
     *            the version number
     * @param docQName
     *            the doc q name
     * @param docTypeClass
     *            the doc type class
     * @return metadata byte array
     */
    byte[] retrieveMetadata(CmsNodeLocator cmsNodeLocator, String id, String fileName, String versionNumber, QName docQName,
            Class<? extends AbstractBaseType> docTypeClass);

    /**
     * This method is used to retrieve the metadata for the given serial number
     * and file name.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param versionNumber
     *            the version number
     * @return metadata json
     */
    byte[] retrieveMetadata(String id, String fileName, String versionNumber);

    byte[] retrieveMetadata(String id, String fileName, String versionNumber, CmsDataFilter dataFilter);

    /**
     * @Title: retrieveMetadata @Description: @param cmsNodeLocator @param
     * serialNumber @param fileName @param versionNumber @param docQName @param
     * docTypeClass @param dataFilter @return @return byte [] @throws
     */
    byte[] retrieveMetadata(CmsNodeLocator cmsNodeLocator, String serialNumber, String fileName, String versionNumber,
            QName docQName, Class<? extends AbstractBaseType> docTypeClass, CmsDataFilter dataFilter);

    /**
     * This method is used to retrieve the version for a document and id.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param docQName
     *            the doc q name
     * @return the list<? extends tmng alf response>
     */
    List<? extends TmngAlfResponse> retrieveVersionsList(String id, String fileName, QName docQName, CmsDataFilter dataFilter);

    /**
     * @param strDocType @Title: ticrsAdmindelete @Description: @param
     * serialNumber @param fileName @return @return TmngAlfResponse @throws
     */
    TmngAlfResponse ticrsAdminDelete(String serialNumber, String fileName, String strDocType);

}