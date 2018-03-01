package gov.uspto.trademark.cms.repo.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Document;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.webscripts.beans.CaseDocumentMetadataResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.DocumentId;

/**
 * Implementation for case service which handles Cases.
 *
 * @author bgummadi
 */
@Component("caseServiceBase")
public class CaseServiceBase extends AbstractBaseService implements CaseService {

    /** The Constant SIX. */
    private static final int SIX = 6;

    /** The Constant THREE. */
    private static final int THREE = 3;

    /** The Constant ZERO. */
    private static final int ZERO = 0;
    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(CaseServiceBase.class);

    @Autowired
    private CmsNodeLocator caseNodeLocator;

    /**
     * Create case folder tree structure to host case documents.
     *
     * @param serialNumber
     *            the serial number
     * @return the node ref
     */
    @Override
    public NodeRef createCaseFolderTree(String serialNumber) {
        NodeRef parentNodeRef = getFolderNodeRef(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.CASE_FOLDER_NAME);
        parentNodeRef = createFolder(parentNodeRef, serialNumber.substring(CaseServiceBase.ZERO, CaseServiceBase.THREE));
        parentNodeRef = createFolder(parentNodeRef, serialNumber.substring(CaseServiceBase.THREE, CaseServiceBase.SIX));
        parentNodeRef = createFolder(parentNodeRef, serialNumber, TradeMarkModel.CASE_FOLDER_QNAME);
        return parentNodeRef;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.services.CaseService#getCaseFolderNodeRef(java
     * .lang.String)
     */
    @Override
    public NodeRef getCaseFolderNodeRef(String serialNumber) throws TmngCmsException.SerialNumberNotFoundException {
        NodeRef nodeRef = null;
        try {
            nodeRef = getFolderNodeRef(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.CASE_FOLDER_NAME,
                    serialNumber.substring(CaseServiceBase.ZERO, CaseServiceBase.THREE),
                    serialNumber.substring(CaseServiceBase.THREE, CaseServiceBase.SIX), serialNumber);
        } catch (StringIndexOutOfBoundsException sioobe) {
            if (log.isInfoEnabled()) {
                log.info(sioobe.getMessage(), sioobe);
            }
            throw new TmngCmsException.CaseSerialNumberFormatException(HttpStatus.BAD_REQUEST,
                    "Verify Case Serial Number format for: " + serialNumber, sioobe);
        }
        // Navigate to the case folder using a path resolver to find the NodeRef
        return nodeRef;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.services.CaseService#getCaseFolderNodeRef(java
     * .lang.String, boolean)
     */
    @Override
    public NodeRef getCaseFolderNodeRef(String serialNumber, boolean createIfNotFound)
            throws TmngCmsException.SerialNumberNotFoundException {
        try {
            return getCaseFolderNodeRef(serialNumber);
        } catch (TmngCmsException.SerialNumberNotFoundException e) {
            if (log.isInfoEnabled()) {
                log.info(e.getMessage(), e);
            }
            if (createIfNotFound) {
                return createCaseFolderTree(serialNumber);
            } else {
                throw e;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.services.CaseService#getCaseFolderProperties(
     * java.lang.String)
     */
    @Override
    public Map<QName, Serializable> getCaseFolderProperties(String serialNumber) {
        NodeRef caseFolderNodeRef = getCaseFolderNodeRef(serialNumber);
        return serviceRegistry.getNodeService().getProperties(caseFolderNodeRef);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.services.CaseService#getCaseFolderProperties(
     * org.alfresco.service.cmr.repository.NodeRef)
     */
    @Override
    public Map<QName, Serializable> getCaseFolderProperties(NodeRef caseFolderNodeRef) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.services.CaseService#getChildNodeRefs(java.lang
     * .String, org.alfresco.service.namespace.QName)
     */
    @Override
    public List<ChildAssociationRef> getChildNodeRefs(String serialNumber, QName qname) {
        Set<QName> qnameSet = new HashSet<QName>();
        qnameSet.add(qname);
        return getChildNodeRefs(serialNumber, qnameSet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.services.CaseService#getChildNodeRefs(java.lang
     * .String, java.util.Set)
     */
    @Override
    public List<ChildAssociationRef> getChildNodeRefs(String serialNumber, Set<QName> qnameSet) {
        NodeRef caseFolderNodeRef = getCaseFolderNodeRef(serialNumber);
        return serviceRegistry.getNodeService().getChildAssocs(caseFolderNodeRef, qnameSet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.CaseService#
     * getAllDocumentsProperties (java.lang.String)
     */
    @Override
    public List<CaseDocumentMetadataResponse> getAllDocumentsProperties(String serialNumber, CmsDataFilter dataFilter) {

        List<CaseDocumentMetadataResponse> documentMetadataList = new ArrayList<CaseDocumentMetadataResponse>();
        // get the serial number folder noderef
        NodeRef caseFolderNodeRef = caseNodeLocator.locateNode(serialNumber);
        Set<QName> qnameSet = TradeMarkDocumentTypes.getSupportedCaseQNames();
        qnameSet.add(ApplicationModel.TYPE_FILELINK);
        List<ChildAssociationRef> childAssociations = serviceRegistry.getNodeService().getChildAssocs(caseFolderNodeRef,
                qnameSet);

        for (ChildAssociationRef childAssociationRef : childAssociations) {
            NodeRef childNodeRef = childAssociationRef.getChildRef();
            QName qName = this.serviceRegistry.getNodeService().getType(childAssociationRef.getChildRef());
            boolean blnFileLink = false;
            if (qName.isMatch(ApplicationModel.TYPE_FILELINK)) {
                childNodeRef = serviceRegistry.getFileFolderService().getFileInfo(childAssociationRef.getChildRef())
                        .getLinkNodeRef();
                qName = this.serviceRegistry.getNodeService().getType(childNodeRef);
                blnFileLink = true;
            }
            TradeMarkDocumentTypes tmCaseDocumentType = TradeMarkDocumentTypes.getTradeMarkType(qName.getLocalName());
            CaseDocumentMetadataResponse caseDocMetadataResp = new CaseDocumentMetadataResponse();
            caseDocMetadataResp.setSerialNumber(serialNumber);
            caseDocMetadataResp.setDocumentType(tmCaseDocumentType.getAlfrescoTypeName());

            Map<QName, Serializable> repositoryProperties = this.serviceRegistry.getNodeService().getProperties(childNodeRef);
            Map<String, Serializable> convertedMap = WebScriptHelper.stringifyMap(repositoryProperties);
            if (dataFilter != null && dataFilter.filter(convertedMap)) {
                continue;
            }

            AbstractBaseType baseType = JacksonHelper.generateDTOFrAlfrescoRepoProps(convertedMap,
                    tmCaseDocumentType.getTypeClass());

            if (blnFileLink) {
                Document document = (Document) baseType;
                document.getCaseAspect().setSerialNumber(serialNumber);
                caseDocMetadataResp.setMetadata(document);
            } else {
                caseDocMetadataResp.setMetadata(baseType);
            }
            caseDocMetadataResp.setVersion(caseDocMetadataResp.getMetadata().getVersion());
            caseDocMetadataResp.setDocumentId(
                    new DocumentId(TradeMarkModel.CASE_FOLDER_NAME, serialNumber, tmCaseDocumentType.getAlfrescoTypeName(),
                            ((Document) caseDocMetadataResp.getMetadata()).getDocumentName()).getId());
            documentMetadataList.add(caseDocMetadataResp);
        }
        return documentMetadataList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.CaseService#
     * getAllMarkDocumentNodeRefs (java.lang.String)
     */
    @Override
    public List<ChildAssociationRef> getAllLatestMarkDocumentNodeRefs(String serialNumber) {
        Set<QName> qnameSet = new HashSet<QName>();
        qnameSet.add(TradeMarkDocumentTypes.getTradeMarkQName(MarkDoc.TYPE));
        return getChildNodeRefs(serialNumber, qnameSet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.CaseService#
     * getCabinetCaseFolderNodeRef ()
     */
    @Override
    public NodeRef getCabinetCaseFolderNodeRef() {
        return getFolderNodeRef(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.CASE_FOLDER_NAME);
    }

    @Override
    public List<String> retrieveTicrsCaseFileNames(String caseId) {
        NodeRef caseFolderNodeRef = caseNodeLocator.locateNode(caseId);
        List<String> fileNameList = new ArrayList<String>();
        Set<QName> qnameSet = TradeMarkDocumentTypes.getSupportedCaseQNames();
        List<ChildAssociationRef> childAssociations = serviceRegistry.getNodeService().getChildAssocs(caseFolderNodeRef,
                qnameSet);
        for (ChildAssociationRef childAssociationRef : childAssociations) {
            NodeRef childNodeRef = childAssociationRef.getChildRef();
            String migrSource = (String) serviceRegistry.getNodeService().getProperty(childNodeRef,
                    TradeMarkModel.PROP_MIGRATION_SOURCE);
            QName qName = serviceRegistry.getNodeService().getType(childNodeRef);
            if (migrSource != null && migrSource.equals(TradeMarkModel.TICRS_SOURCE) && qName != null
                    && !(qName.getLocalName()).equals(TradeMarkModel.TYPE_TICRS_DOCUMENT)) {
                String fileName = (String) this.serviceRegistry.getNodeService().getProperty(childNodeRef,
                        ContentModel.PROP_NAME);
                fileNameList.add(fileName);
            }
        }
        return fileNameList;
    }

}