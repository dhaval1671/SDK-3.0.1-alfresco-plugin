package gov.uspto.trademark.cms.repo.services.impl.cabinet.legalproceeding;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.helpers.LegalProceedingNodeCreator;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Exhibit;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.nodelocator.LegalProceedingNodeLocator;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This service will implement the exhibit(legal-proceeding) type functionality.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk} 11/30/2016.
 *
 */
@Component("ExhibitDocService")
public class ExhibitDocService extends AbstractLegalProceedingCommonService {

    private final CmsNodeLocator legalProceedingNodeLocator;

    /**
     * Instantiates a new notice doc service.
     *
     * @param legalProceedingNodeCreator
     *            the case node creator
     * @param legalProceedingNodeLocator
     *            the case node locator
     */
    @Autowired
    public ExhibitDocService(LegalProceedingNodeCreator legalProceedingNodeCreator,
            LegalProceedingNodeLocator legalProceedingNodeLocator) {
        super(legalProceedingNodeCreator, legalProceedingNodeLocator);
        this.legalProceedingNodeLocator = legalProceedingNodeLocator;
    }

    @Override
    public TmngAlfResponse create(final String id, final String fileName, final ContentItem content,
            Map<String, Serializable> properties) {
        return createDocument(id, fileName, content, TradeMarkModel.EXHIBIT_QNAME, properties, Exhibit.class);
    }

    @Override
    public TmngAlfResponse update(final String id, final String fileName, final ContentItem content,
            Map<String, Serializable> properties) {
        return null;
    }

    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber) {
        return retrieveContent(legalProceedingNodeLocator, TradeMarkModel.EXHIBIT_QNAME, id, fileName, versionNumber);
    }

    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return retrieveContent(legalProceedingNodeLocator, TradeMarkModel.EXHIBIT_QNAME, id, fileName, versionNumber, dataFilter);
    }

    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber) {
        return retrieveMetadata(legalProceedingNodeLocator, id, fileName, versionNumber, TradeMarkModel.EXHIBIT_QNAME,
                Exhibit.class);
    }

    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return retrieveMetadata(legalProceedingNodeLocator, id, fileName, versionNumber, TradeMarkModel.EXHIBIT_QNAME,
                Exhibit.class, dataFilter);
    }

    @Override
    public TmngAlfResponse updateMetadata(String id, String fileName, Map<String, Serializable> properties) {
        return null;
    }

}
