package gov.uspto.trademark.cms.repo.services.impl.drive.efile;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.namespace.QName;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.impl.AbstractCommonService;
import gov.uspto.trademark.cms.repo.webscripts.beans.EfileResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * Created by bgummadi on 8/7/2015.
 */
public abstract class AbstractEfileCommonService extends AbstractCommonService {

    /**
     * Instantiates a new abstract common service.
     *
     * @param cmsNodeCreator
     *            the case node creator
     * @param cmsNodeLocator
     *            the cms node locator
     */
    public AbstractEfileCommonService(CmsNodeCreator cmsNodeCreator, CmsNodeLocator cmsNodeLocator) {
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
        repositoryProperties.put(TradeMarkModel.TRADEMARK_ID_QNAME, id);
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
        EfileResponse postResponse = new EfileResponse();
        postResponse.setDocumentId(id, type, fileName);
        postResponse.setVersion(versionNumber);
        postResponse.setId(id);
        return postResponse;
    }
}
