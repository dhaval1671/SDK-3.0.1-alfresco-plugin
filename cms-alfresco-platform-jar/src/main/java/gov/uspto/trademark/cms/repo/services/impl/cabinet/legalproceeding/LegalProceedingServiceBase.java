package gov.uspto.trademark.cms.repo.services.impl.cabinet.legalproceeding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TradeMarkLegalProceedingTypes;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.LegalProceeding;
import gov.uspto.trademark.cms.repo.nodelocator.LegalProceedingNodeLocator;
import gov.uspto.trademark.cms.repo.services.LegalProceedingService;
import gov.uspto.trademark.cms.repo.webscripts.beans.DocumentId;
import gov.uspto.trademark.cms.repo.webscripts.beans.LegalProceedingDocumentMetadataResponse;

/**
 * Implementation for case service which handles Cases.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
@Component("legalProceedingServiceBase")
public class LegalProceedingServiceBase implements LegalProceedingService {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(LegalProceedingServiceBase.class);

    /** The legal-proceeding node locator. */
    @Autowired
    @Qualifier(value = "legalProceedingNodeLocator")
    private LegalProceedingNodeLocator legalProceedingNodeLocator;

    /** The service registry. */
    @Autowired
    @Qualifier(value = "ServiceRegistry")
    private ServiceRegistry serviceRegistry;

    @Override
    public List<LegalProceedingDocumentMetadataResponse> getAllDocumentsProperties(String proceedingNumber,
            CmsDataFilter dataFilter) {

        List<LegalProceedingDocumentMetadataResponse> documentMetadataList = new ArrayList<LegalProceedingDocumentMetadataResponse>();
        // get the serial number folder noderef
        NodeRef caseFolderNodeRef = legalProceedingNodeLocator.locateNode(proceedingNumber);
        Set<QName> qnameSet = TradeMarkLegalProceedingTypes.getSupportedLegalProceedingQNames();
        List<ChildAssociationRef> childAssociations = serviceRegistry.getNodeService().getChildAssocs(caseFolderNodeRef,
                qnameSet);
        for (ChildAssociationRef childAssociationRef : childAssociations) {
            NodeRef childNodeRef = childAssociationRef.getChildRef();
            Map<QName, Serializable> repositoryProperties = this.serviceRegistry.getNodeService().getProperties(childNodeRef);
            Map<String, Serializable> convertedMap = WebScriptHelper.stringifyMap(repositoryProperties);
            if (dataFilter != null && dataFilter.filter(convertedMap)) {
                continue;
            }
            QName qName = this.serviceRegistry.getNodeService().getType(childAssociationRef.getChildRef());
            TradeMarkLegalProceedingTypes tmLegalProceedingDocumentType = TradeMarkLegalProceedingTypes
                    .getLegalProceedingType(qName.getLocalName());
            LegalProceedingDocumentMetadataResponse lpDocMetadataResp = new LegalProceedingDocumentMetadataResponse();
            lpDocMetadataResp.setProceedingNumber(proceedingNumber);
            lpDocMetadataResp.setDocumentType(tmLegalProceedingDocumentType.getAlfrescoTypeName());
            AbstractBaseType baseType = JacksonHelper.generateDTOFrAlfrescoRepoProps(convertedMap,
                    tmLegalProceedingDocumentType.getTypeClass());
            lpDocMetadataResp.setMetadata(baseType);
            lpDocMetadataResp.setVersion(lpDocMetadataResp.getMetadata().getVersion());
            lpDocMetadataResp.setDocumentId(new DocumentId(TradeMarkModel.LEGAL_PROCEEDING_FOLDER_NAME, proceedingNumber,
                    tmLegalProceedingDocumentType.getAlfrescoTypeName(),
                    ((LegalProceeding) lpDocMetadataResp.getMetadata()).getDocumentName()).getId());
            documentMetadataList.add(lpDocMetadataResp);
        }
        return documentMetadataList;
    }

    @Override
    public List<LegalProceedingDocumentMetadataResponse> retrieveMetadataFromMultipleProceedingNumbers(String[] proceedingNumbers, CmsDataFilter dataFilter) {
        List<LegalProceedingDocumentMetadataResponse> tempList = new ArrayList<LegalProceedingDocumentMetadataResponse>();; 
        for(String proceedingNumber: proceedingNumbers){
            List<LegalProceedingDocumentMetadataResponse> lpdmr = null;
            try{
                lpdmr = getAllDocumentsProperties(proceedingNumber, dataFilter);
            }
            catch(SerialNumberNotFoundException e){
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage(), e);
                }
                continue;
            }
            if(null != lpdmr){
                tempList.addAll(lpdmr);
            }
        }
        return tempList;
    }

}