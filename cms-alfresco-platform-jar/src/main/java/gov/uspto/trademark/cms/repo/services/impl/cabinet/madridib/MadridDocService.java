package gov.uspto.trademark.cms.repo.services.impl.cabinet.madridib;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.helpers.MadridNodeCreator;
import gov.uspto.trademark.cms.repo.model.cabinet.madridib.Madrid;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.nodelocator.MadridNodeLocator;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This service will implement the Madrid document type functionality.
 * 
 * @author stank
 */
@Component("MadridDocService")
public class MadridDocService extends AbstractMadridCommonService {

    private final CmsNodeLocator madridNodeLocator;

    /**
     * Instantiates a new summary doc service.
     * 
     * @param madridNodeCreator
     *            the case node creator
     * @param madridNodeLocator
     *            the case node locator
     */
    @Autowired
    public MadridDocService(MadridNodeCreator madridNodeCreator, MadridNodeLocator madridNodeLocator) {
        super(madridNodeCreator, madridNodeLocator);
        this.madridNodeLocator = madridNodeLocator;
    }

    @Override
    public TmngAlfResponse create(final String id, final String fileName, final ContentItem content,
            Map<String, Serializable> properties) {
        return createDocument(id, fileName, content, TradeMarkModel.MADRID_QNAME, properties, Madrid.class);
    }

    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber) {
        return retrieveContent(madridNodeLocator, TradeMarkModel.MADRID_QNAME, id, fileName, versionNumber);
    }

    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return retrieveContent(madridNodeLocator, TradeMarkModel.MADRID_QNAME, id, fileName, versionNumber, dataFilter);
    }

    @Override
    public TmngAlfResponse update(final String id, final String fileName, final ContentItem content,
            Map<String, Serializable> properties) {
        return null;
    }

    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber) {
        return new byte[0];
    }

    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return new byte[0];
    }

    @Override
    public TmngAlfResponse updateMetadata(String id, String fileName, Map<String, Serializable> properties) {
        return null;

    }
}
