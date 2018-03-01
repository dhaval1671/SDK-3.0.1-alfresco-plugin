package gov.uspto.trademark.cms.repo.services;

/**
 * This is a factory class to get the metadata builder based on cms document
 * type.
 *
 * @author vnondapaka
 */
public interface MadridServiceFactory {

    /**
     * This method returns appropriate document metadata builder class based on
     * cms document type.
     *
     * @param docType
     *            the doc type
     * @return the publication service
     */
    CmsCommonService getMadridService(String docType);
}
