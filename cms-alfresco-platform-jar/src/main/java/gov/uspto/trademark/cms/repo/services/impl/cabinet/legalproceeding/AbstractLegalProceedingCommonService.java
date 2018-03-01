package gov.uspto.trademark.cms.repo.services.impl.cabinet.legalproceeding;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.namespace.QName;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.LegalProceedingNodeCreator;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.nodelocator.LegalProceedingNodeLocator;
import gov.uspto.trademark.cms.repo.webscripts.beans.PublicationResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk} on Jul/7/2016.
 */
public abstract class AbstractLegalProceedingCommonService extends AbstractLegalProceedingBaseCommonService {

    /**
     * Instantiates a new abstract common service.
     *
     * @param cmsNodeCreator
     *            the case node creator
     * @param cmsNodeLocator
     *            the cms node locator
     */
    public AbstractLegalProceedingCommonService(LegalProceedingNodeCreator legalProceedingNodeCreator,
            LegalProceedingNodeLocator legalProceedingNodeLocator) {
        super(legalProceedingNodeCreator, legalProceedingNodeLocator);
    }

    /**
     * Used to set Id and other mandatory parameters during creation process
     *
     * @param repositoryProperties
     * @param id
     */
    @Override
    protected void setMandatoryProperties(Map<QName, Serializable> repositoryProperties, String id) {
        repositoryProperties.put(TradeMarkModel.PROCEEDING_NUMBER_QNAME, id);
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
