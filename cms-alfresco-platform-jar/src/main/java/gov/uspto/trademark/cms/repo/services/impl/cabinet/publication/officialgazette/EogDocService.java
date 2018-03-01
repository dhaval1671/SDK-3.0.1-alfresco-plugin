package gov.uspto.trademark.cms.repo.services.impl.cabinet.publication.officialgazette;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.officialgazette.Eog;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.publication.AbstractPublicationCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This service will implement the note document type functionality.
 * 
 * @author vnondapaka
 *
 */
@Component("EogDocService")
public class EogDocService extends AbstractPublicationCommonService {

    /** The eog node locator. */
    private final CmsNodeLocator eogNodeLocator;

    /**
     * Instantiates a new eog doc service.
     *
     * @param eogNodeCreator
     *            the eog node creator
     * @param eogNodeLocator
     *            the eog node locator
     */
    @Autowired
    public EogDocService(CmsNodeCreator eogNodeCreator, CmsNodeLocator eogNodeLocator) {
        super(eogNodeCreator, eogNodeLocator);
        this.eogNodeLocator = eogNodeLocator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveContent
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber) {
        return retrieveContent(eogNodeLocator, TradeMarkModel.EOG_QNAME, id, fileName, versionNumber);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveContent(
     * java.lang.String, java.lang.String, java.lang.String,
     * gov.uspto.trademark.cms.repo.filters.CmsDataFilter)
     */
    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber) {
        return retrieveMetadata(eogNodeLocator, id, fileName, versionNumber, TradeMarkModel.EOG_QNAME, Eog.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata(
     * java.lang.String, java.lang.String, java.lang.String,
     * gov.uspto.trademark.cms.repo.filters.CmsDataFilter)
     */
    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#updateMetadata(
     * java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    public TmngAlfResponse updateMetadata(String id, String fileName, Map<String, Serializable> properties) {
        return updateDocument(id, fileName, TradeMarkModel.EOG_QNAME, properties, Eog.class, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.impl.PublicationCommonService#
     * create(java.lang.String, java.lang.String, java.io.InputStream,
     * java.util.Map)
     */
    @Override
    public TmngAlfResponse create(String id, String fileName, ContentItem content, Map<String, Serializable> properties) {
        return createDocument(id, fileName, content, TradeMarkModel.EOG_QNAME, properties, Eog.class);

    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.impl.PublicationCommonService#
     * update(java.lang.String, java.lang.String, java.io.InputStream,
     * java.util.Map)
     */
    @Override
    public TmngAlfResponse update(String id, String fileName, ContentItem content, Map<String, Serializable> properties) {
        return updateDocument(id, fileName, TradeMarkModel.EOG_QNAME, properties, Eog.class, content);
    }
}
