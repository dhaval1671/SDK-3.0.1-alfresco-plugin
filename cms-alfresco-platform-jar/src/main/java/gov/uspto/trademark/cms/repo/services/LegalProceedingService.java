package gov.uspto.trademark.cms.repo.services;

import java.util.List;

import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.webscripts.beans.LegalProceedingDocumentMetadataResponse;

/**
 * Services to handle cases. Each case has a folder under which all case related
 * documents are stored. Each case has unique case number as an identifier.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
@Component
public interface LegalProceedingService {

    /**
     * Returns properties for all the documents under the case folder for a
     * given serial number.
     *
     * @param serialNumber
     *            the serial number
     * @return the all document properties
     */
    List<LegalProceedingDocumentMetadataResponse> getAllDocumentsProperties(String serialNumber, CmsDataFilter dataFilter);
    
    List<LegalProceedingDocumentMetadataResponse> retrieveMetadataFromMultipleProceedingNumbers(String[] proceedingNumbers, CmsDataFilter dataFilter);
    
    

}