package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Document;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.AbstractCaseCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.EogTemplateResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This service will implement the note document type functionality.
 * 
 * @author vnondapaka
 *
 */
@Component("eogTemplateDocService")
public class EogTemplateDocService extends AbstractCaseCommonService {

    private final CmsNodeLocator eogTemplateNodeLocator;

    @Autowired
    public EogTemplateDocService(CmsNodeCreator eogTemplateNodeCreator, CmsNodeLocator eogTemplateNodeLocator) {
        super(eogTemplateNodeCreator, eogTemplateNodeLocator);
        this.eogTemplateNodeLocator = eogTemplateNodeLocator;
    }

    @Override
    public TmngAlfResponse create(String id, String fileName, ContentItem content, Map<String, Serializable> properties) {

        return createDocument(id, fileName, content, TradeMarkModel.DOCUMENT_QNAME, properties, Document.class);

    }

    @Override
    public TmngAlfResponse update(String id, String fileName, ContentItem content, Map<String, Serializable> properties) {
        return null;
    }

    @Override
    public TmngAlfResponse updateMetadata(String id, String fileName, Map<String, Serializable> properties) {
        return null;
    }

    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber) {
        return retrieveContent(eogTemplateNodeLocator, TradeMarkModel.DOCUMENT_QNAME, id, fileName, versionNumber);
    }

    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return null;
    }

    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber) {
        return ArrayUtils.EMPTY_BYTE_ARRAY;
    }

    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return ArrayUtils.EMPTY_BYTE_ARRAY;
    }

    /**
     * Used to buildResponse after creation
     *
     * @param id
     * @param type
     * @param fileName
     * @param versionNumber
     * @return
     */
    @Override
    protected TmngAlfResponse buildResponse(String id, String type, String fileName, String versionNumber) {
        EogTemplateResponse postResponse = new EogTemplateResponse();
        postResponse.setDocumentId(id, type, fileName);
        postResponse.setVersion(versionNumber);
        postResponse.setId(id);
        return postResponse;
    }

}
