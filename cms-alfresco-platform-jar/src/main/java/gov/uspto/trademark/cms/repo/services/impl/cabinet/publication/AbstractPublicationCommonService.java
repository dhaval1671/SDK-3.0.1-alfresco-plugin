package gov.uspto.trademark.cms.repo.services.impl.cabinet.publication;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.namespace.QName;

import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.webscripts.beans.PublicationResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * Created by bgummadi on 8/7/2015.
 */
public abstract class AbstractPublicationCommonService extends AbstractPublicationBaseCommonService {

    /**
     * Instantiates a new abstract common service.
     *
     * @param cmsNodeCreator
     *            the case node creator
     * @param cmsNodeLocator
     *            the cms node locator
     */
    public AbstractPublicationCommonService(CmsNodeCreator cmsNodeCreator, CmsNodeLocator cmsNodeLocator) {
        super(cmsNodeCreator, cmsNodeLocator);
    }

    /**
     * Used to set Id and other mandatory parameters during creation process
     *
     * @param repositoryProperties
     * @param id
     */
    @Override
    protected void setMandatoryProperties(Map<QName, Serializable> repositoryProperties, String id) {
        // Empty implementation
    }

    /**
     * Used to buildResponse after creation
     *
     * @param id
     * @param type
     * @param fileName
     * @param versionNumber
     *            @return
     */
    @Override
    protected <T extends AbstractBaseType> TmngAlfResponse buildResponse(String id, String type, String fileName,
            String versionNumber) {
        PublicationResponse postResponse = new PublicationResponse();
        postResponse.setDocumentId(id, type, fileName);
        postResponse.setId(id);
        return postResponse;
    }
}
