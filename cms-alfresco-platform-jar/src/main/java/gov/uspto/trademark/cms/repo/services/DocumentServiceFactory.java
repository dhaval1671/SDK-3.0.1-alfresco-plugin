package gov.uspto.trademark.cms.repo.services;

/**
 * This is a factory class to get the metadata builder based on cms document
 * type.
 *
 * @author vnondapaka
 */
public interface DocumentServiceFactory {

    /**
     * This method returns appropriate document metadata builder class based on
     * cms document type.
     *
     * @param docType
     *            the doc type
     * @return the document service
     */
    CmsCommonService getDocumentService(String docType);
}
