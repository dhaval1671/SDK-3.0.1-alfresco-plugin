package gov.uspto.trademark.cms.repo.services;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;

import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This interface provides all the redact functionalities.
 *
 * @author vnondapaka
 */
public interface CmsRedactionService {

    /**
     * This method is used to redact the given document.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param docType
     *            the doc type
     * @param properties
     *            the properties
     * @return TmngAlfResponse with documentId and Version
     */
    TmngAlfResponse redactDocument(String id, String fileName, ContentItem content, String docType,
            Map<String, Serializable> properties);

    /**
     * This method is used to retrieve the redacted content for the given
     * document and ID.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param docType
     *            the doc type
     * @return redacted content
     */
    ContentReader retrieveRedactionContent(String id, String fileName, String docType);

    /**
     * This method is used to restor the document to the original version. This
     * operation usually performed when a document has multiple redactions and
     * user wantes to revert the current version back to the original.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param docType
     *            the doc type
     * @return TmngAlfResponse with documentId and Version
     */
    TmngAlfResponse restoreToOriginalVersion(String id, String fileName, String docType);

    /**
     * This method is used to retrieve the original content when a document has
     * both original and redact versions.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param docType
     *            the doc type
     * @param accessLevelFilter
     * @return original content
     */
    ContentReader retrieveOriginalContent(String id, String fileName, String docType, CmsDataFilter dataFilter);

    /**
     * This method is used to retrieve the metadata of the original version of
     * the document.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param docType
     *            the doc type
     * @param accessLevelFilter
     * @return byte array
     */
    byte[] retrieveOriginalMetadata(String id, String fileName, String docType, CmsDataFilter dataFilter);

    /**
     * This method is used to retrieve the metadata of the redacted version of
     * the document.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param docType
     *            the doc type
     * @return metadata byte array
     */
    byte[] retrieveRedactionMetadata(String id, String fileName, String docType);
}