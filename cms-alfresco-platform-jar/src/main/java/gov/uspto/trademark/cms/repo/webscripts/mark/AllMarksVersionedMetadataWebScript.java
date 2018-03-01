package gov.uspto.trademark.cms.repo.webscripts.mark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.version.Version;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.DocumentId;

/**
 * Returns a list of all documents with complete metadata for a given serial
 * number
 * <p/>
 * Created by bgummadi on 9/5/2014.
 */
public class AllMarksVersionedMetadataWebScript extends AbstractCmsCommonWebScript {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(AllMarksVersionedMetadataWebScript.class);

    @Autowired
    @Qualifier(value = "caseServiceBase")
    CaseService caseService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.webscripts.BaseWebscript#executeAction(org
     * .springframework.extensions.webscripts.WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    protected void executeService(WebScriptRequest webScriptRequest, WebScriptResponse response) {
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        String serialNumber = urlParameters.get(SERIAL_NUMBER_PATH_PARAMETER);

        List<ChildAssociationRef> caseDocumentList = caseService.getAllLatestMarkDocumentNodeRefs(serialNumber);
        List<AllMarksVersionedMetadata> documentMetadataList = new ArrayList<AllMarksVersionedMetadata>();

        for (ChildAssociationRef childAssociationRef : caseDocumentList) {
            NodeRef lclNodeRef = childAssociationRef.getChildRef();
            Collection<Version> versionList = serviceRegistry.getVersionService().getVersionHistory(lclNodeRef).getAllVersions();
            List<AbstractBaseType> abt = new ArrayList<AbstractBaseType>();
            for (Version version : versionList) {
                QName qNameParent = caseService.getType(lclNodeRef);
                TradeMarkDocumentTypes tmCaseDocumentTypeParent = TradeMarkDocumentTypes
                        .getTradeMarkType(qNameParent.getLocalName());
                abt.add(caseService.generateOutGoingDTO(version.getFrozenStateNodeRef(),
                        tmCaseDocumentTypeParent.getTypeClass()));
            }

            String latestVersionLabel = (String) serviceRegistry.getNodeService().getProperty(lclNodeRef,
                    ContentModel.PROP_VERSION_LABEL);
            String documentName = (String) serviceRegistry.getNodeService().getProperty(lclNodeRef, ContentModel.PROP_NAME);
            QName qNameParent = caseService.getType(lclNodeRef);
            TradeMarkDocumentTypes tmCaseDocumentTypeParent = TradeMarkDocumentTypes.getTradeMarkType(qNameParent.getLocalName());
            documentMetadataList.add(prepareCaseDocumentMetadataResponse(serialNumber,
                    tmCaseDocumentTypeParent.getAlfrescoTypeName(), abt, latestVersionLabel, documentName));
        }

        response.setContentType(TMConstants.APPLICATION_JSON);
        try {
            response.getOutputStream().write(JacksonHelper.generateClientJsonFrDTO(documentMetadataList));
        } catch (IOException e) {
            if (log.isInfoEnabled()) {
                log.info(e.getMessage(), e);
            }
            log.error(e.getMessage(), e);
        }

    }

    /**
     * Prepare case document metadata response.
     *
     * @param <T>
     *            the generic type
     * @param serialNumber
     *            the serial number
     * @param documentType
     *            the document type
     * @return the case document metadata response
     */
    private <T extends AbstractBaseType> AllMarksVersionedMetadata prepareCaseDocumentMetadataResponse(String serialNumber,
            String documentType, List<AbstractBaseType> metadataList, String latestVersion, String documentName) {
        AllMarksVersionedMetadata cdmr = new AllMarksVersionedMetadata();
        cdmr.setSerialNumber(serialNumber);
        cdmr.setMetadata(metadataList);
        cdmr.setVersion(latestVersion);
        cdmr.setDocumentId(new DocumentId(TradeMarkModel.CASE_FOLDER_NAME, serialNumber, documentType, documentName).getId());
        return cdmr;
    }

}