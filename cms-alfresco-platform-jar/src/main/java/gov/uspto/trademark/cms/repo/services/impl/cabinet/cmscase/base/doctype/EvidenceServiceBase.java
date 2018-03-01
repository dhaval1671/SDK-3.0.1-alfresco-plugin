package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.DuplicateChildNodeNameException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.RedactionLevel;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.DataDictionaryHelper;
import gov.uspto.trademark.cms.repo.helpers.PathResolver;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.EvidenceService;
import gov.uspto.trademark.cms.repo.services.impl.AbstractBaseService;
import gov.uspto.trademark.cms.repo.webscripts.beans.EvidencePostResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.PostResponse;
import gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest;

/**
 * Created by bgummadi on 5/9/2014.
 * 
 * @author stank
 */
@Component("evidenceServiceBase")
public class EvidenceServiceBase extends AbstractBaseService implements EvidenceService {

    /** The Constant DELETED_FOLDER_NAME. */
    private static final String DELETED_FOLDER_NAME = "deleted";

    /** The Constant PUBLIC. */
    private static final String PUBLIC = "public";

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(EvidenceServiceBase.class);

    /** The cms node locator. */
    @Autowired
    @Qualifier(value = "caseNodeLocator")
    CmsNodeLocator cmsNodeLocator;

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.EvidenceService#
     * bulkMetadataUpdateEvidences(java.lang.String,
     * org.alfresco.service.cmr.repository.NodeRef, java.util.Map)
     */
    @Override
    public List<EvidencePostResponse> bulkMetadataUpdateEvidences(final String serialNumber, final NodeRef caseFolderNodeRef,
            final Map<NodeRef, CopyEvidenceRequest> sourceData) {
        List<EvidencePostResponse> newNodesList = new ArrayList<EvidencePostResponse>();
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<Void> callback = new RetryingTransactionCallback<Void>() {
            @Override
            public Void execute() throws Throwable {
                for (final Map.Entry<NodeRef, CopyEvidenceRequest> entry : sourceData.entrySet()) {
                    FileInfo fileInfo = serviceRegistry.getFileFolderService().getFileInfo((entry.getKey()));
                    final Map<QName, Serializable> properties = fileInfo.getProperties();
                    Map<QName, Serializable> updatedProperties = null;
                    Evidence evi = entry.getValue().getMetadata();
                    if (evi != null) {
                        updatedProperties = WebScriptHelper.generateAlfRepoPropsFrDTO(evi);
                    } else {
                        updatedProperties = new HashMap<QName, Serializable>();
                    }
                    /*
                     * While updating metadata from existing nodes it is
                     * updating uuid and db-id which is throwing
                     * "The node UUID cannot be changed."
                     */
                    properties.remove(ContentModel.PROP_NODE_UUID);
                    properties.remove(ContentModel.PROP_NODE_DBID);
                    properties.putAll(updatedProperties);
                    NodeRef eviNodeRef = entry.getKey();
                    boolean hasAspect = (serviceRegistry.getNodeService().hasAspect(eviNodeRef, TradeMarkModel.ASPECT_REDACTED));
                    if (hasAspect) {
                        // check if redaction level user is NOT passing as NONE.
                        String redactionLevel = (String) properties.get(TradeMarkModel.REDACTION_LEVEL_QNAME);
                        if (RedactionLevel.NONE.equals(RedactionLevel.getRedactionLevel(redactionLevel))) {
                            throw new TmngCmsException.CMSRuntimeException(
                                    " 'redactionLevel' NONE is not allowed, document already redacted.");
                        }
                    }
                    for (Entry<QName, Serializable> entryTwo : properties.entrySet()) {
                        serviceRegistry.getNodeService().setProperty(eviNodeRef, entryTwo.getKey(), entryTwo.getValue());
                    }

                }
                return null;
            }
        };
        txnHelper.doInTransaction(callback, false, true);

        for (Map.Entry<NodeRef, CopyEvidenceRequest> entry : sourceData.entrySet()) {
            FileInfo fileInfo = serviceRegistry.getFileFolderService().getFileInfo((entry.getKey()));
            final NodeRef nr = entry.getKey();
            /* Construct response */
            EvidencePostResponse response = new EvidencePostResponse();
            response.setDocumentId(serialNumber, Evidence.TYPE, fileInfo.getName());
            response.setSerialNumber(serialNumber);
            RetryingTransactionHelper txnHelperTwo = transactionService.getRetryingTransactionHelper();
            RetryingTransactionCallback<String> callbackTwo = new RetryingTransactionCallback<String>() {
                @Override
                public String execute() throws Throwable {
                    return (String) serviceRegistry.getNodeService().getProperty(nr, ContentModel.PROP_VERSION_LABEL);
                }
            };
            String verNo = txnHelperTwo.doInTransaction(callbackTwo, true, true);
            response.setVersion(verNo);
            newNodesList.add(response);
        }
        return newNodesList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.EvidenceService#copyEvidences(java
     * .lang.String, org.alfresco.service.cmr.repository.NodeRef, java.util.Map)
     */
    @Override
    public List<EvidencePostResponse> copyEvidences(final String serialNumber, final NodeRef caseFolderNodeRef,
            final Map<NodeRef, CopyEvidenceRequest> sourceData) {
        List<EvidencePostResponse> newNodesList = new ArrayList<EvidencePostResponse>();
        for (Map.Entry<NodeRef, CopyEvidenceRequest> entry : sourceData.entrySet()) {
            FileInfo fileInfo = serviceRegistry.getFileFolderService().getFileInfo((entry.getKey()));
            try {
                Map<QName, Serializable> properties = fileInfo.getProperties();
                Map<QName, Serializable> updatedProperties = null;

                Evidence evi = entry.getValue().getMetadata();
                if (evi != null) {
                    updatedProperties = WebScriptHelper.generateAlfRepoPropsFrDTO(evi);
                } else {
                    updatedProperties = new HashMap<QName, Serializable>();
                }

                /* Update mandatory properties and given properties */
                updatedProperties.put(ContentModel.PROP_NAME, fileInfo.getName());
                updatedProperties.put(DataDictionaryHelper.getTradeMarkPropertyQName(TradeMarkModel.DOCUMENT_NAME),
                        fileInfo.getName());
                updatedProperties.put(TradeMarkModel.SERIAL_NUMBER_QNAME, serialNumber);

                /*
                 * While copying properties from existing nodes it is copying
                 * uuid and db-id which is throwing node exists exception.
                 */
                properties.remove(ContentModel.PROP_NODE_UUID);
                properties.remove(ContentModel.PROP_NODE_DBID);
                
                String documentId = org.springframework.util.StringUtils.trimLeadingCharacter(entry.getValue().getDocumentId(), '/');
                processIncomingPath(serialNumber, caseFolderNodeRef, entry, fileInfo, properties, updatedProperties, documentId);

                /* Construct response */
                EvidencePostResponse response = new EvidencePostResponse();
                response.setDocumentId(serialNumber, Evidence.TYPE, fileInfo.getName());
                response.setSerialNumber(serialNumber);
                response.setVersion("1.0");
                newNodesList.add(response);
            } catch (DuplicateChildNodeNameException e) {
                throw new TmngCmsException.CMSRuntimeException(
                        "Document already exists at target folder for the given 'documentId' " + entry.getValue().getDocumentId(), e);
            }
        }
        return newNodesList;
    }

    private void processIncomingPath(final String serialNumber, final NodeRef caseFolderNodeRef,
            Map.Entry<NodeRef, CopyEvidenceRequest> entry, FileInfo fileInfo, Map<QName, Serializable> properties,
            Map<QName, Serializable> updatedProperties, String documentId) {
        if (PathResolver.isCasePath(documentId)) {

            /* Check if the source is of type evidence */
            QName sourceQName = serviceRegistry.getNodeService().getType(entry.getKey());
            if (!sourceQName.getLocalName().equalsIgnoreCase(TradeMarkModel.EVIDENCE_QNAME.getLocalName())) {
                throw new TmngCmsException.CMSRuntimeException(entry.getValue().getDocumentId() + " is not an Evidence file");
            }

            /* Copy evidence file from one case to another */
            QName localQName = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, fileInfo.getName());
            QName nodeTypeOfNewNodeRef =      serviceRegistry.getNodeService().getType(fileInfo.getNodeRef());
            final NodeRef newEvidenceNode = serviceRegistry.getNodeService().createNode(caseFolderNodeRef, ContentModel.ASSOC_CONTAINS, localQName, nodeTypeOfNewNodeRef).getChildRef();            

            /* Copy new properties to original properties */
            properties.putAll(updatedProperties);

            /* Update Node with new properties */
            serviceRegistry.getNodeService().setProperties(newEvidenceNode, properties);
            
        } else {

            properties.remove(TradeMarkModel.PROP_EVIDENCE_BANK_TWO_A_LIB_SOURCE);
            updatedProperties.remove(TradeMarkModel.PROP_EVIDENCE_BANK_TWO_A_LIB_SOURCE);

            Date d = Calendar.getInstance().getTime();
            updatedProperties.put(ContentModel.PROP_CREATED, d);
            updatedProperties.put(ContentModel.PROP_MODIFIED, d);

            /* Override new properties */
            properties.putAll(updatedProperties);

            /* Create new evidence file with given properties */
            this.upload(TradeMarkModel.EVIDENCE_QNAME, serialNumber, caseFolderNodeRef, fileInfo.getName(),
                    serviceRegistry.getFileFolderService().getReader(entry.getKey()).getContentInputStream(), properties);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.EvidenceService#deleteEvidences
     * (java.lang.String, java.util.List)
     */
    @Override
    public List<PostResponse> deleteEvidences(String serialNumber, List<NodeRef> deleteFileList)
            throws TmngCmsException.AccessLevelRuleViolationException {

        List<PostResponse> mvrList = new ArrayList<PostResponse>();
        NodeService ns = serviceRegistry.getNodeService();
        for (NodeRef eviNodeRef : deleteFileList) {

            Map<QName, Serializable> properties = ns.getProperties(eviNodeRef);
            String fn = (String) properties.get(ContentModel.PROP_NAME);

            // Check if it is of the type evidence.
            QName qn = ns.getType(eviNodeRef);
            if (!(TradeMarkModel.EVIDENCE_QNAME.isMatch(qn))) {
                throw new TmngCmsException.AccessLevelRuleViolationException(HttpStatus.FORBIDDEN,
                        "Can't delete file " + fn + " as it is NOT of the type evidence.");
            }

            // Check if this NodeRefs are associated with any OfficeAction, if
            // yes then throw exception.
            List<AssociationRef> associationRefList = ns.getTargetAssocs(eviNodeRef, TradeMarkModel.ASSOC_RELATED_OFFICE_ACTION);
            for (AssociationRef ar : associationRefList) {
                NodeRef nf = ar.getTargetRef();
                String officeActionName = (String) ns.getProperty(nf, ContentModel.PROP_NAME);
                log.debug(fn + " is associated with Office Action " + officeActionName);
                throw new TmngCmsException.AccessLevelRuleViolationException(HttpStatus.FORBIDDEN,
                        "Can't delete evidence " + fn + " as it is associated with Office Action " + officeActionName);
            }

            // Check if this NodeRefs have accessLevel as public then don't
            // delete, throw exception.
            String accessLevel = (String) ns.getProperty(eviNodeRef, TradeMarkModel.ACCESS_LEVEL_QNAME);
            if (StringUtils.equalsIgnoreCase(PUBLIC, accessLevel)) {
                log.debug("accessLevel on evidence file " + fn + " is set to " + accessLevel + " and can't be deleted.");
                throw new TmngCmsException.AccessLevelRuleViolationException(HttpStatus.FORBIDDEN,
                        "Can't delete evidence " + fn + " as its accessLevel is set to " + accessLevel);
            }

            // check if delete folder exists in the serial number, if NOT create
            // a delete folder.
            NodeRef caseFolderNodeRef = caseService.getCaseFolderNodeRef(serialNumber);
            NodeRef deleteFolderNodeRef = createFolder(caseFolderNodeRef, DELETED_FOLDER_NAME);

            String baseName = FilenameUtils.getBaseName(fn);
            String ext = FilenameUtils.getExtension(fn);
            if (StringUtils.isNotBlank(ext)) {
                ext = "'." + ext + "'";
            }
            String partName = new SimpleDateFormat("'_'yyyyMMddhhmmss_SSS" + ext).format(new Date());
            String newFileName = baseName + partName;
            // Append time stamp to NodeRef name.
            serviceRegistry.getNodeService().setProperty(eviNodeRef, ContentModel.PROP_NAME, newFileName);

            // move the evidence file to delete folder.
            ns.moveNode(eviNodeRef, deleteFolderNodeRef, ContentModel.ASSOC_CONTAINS, null);
            cookPostResponse(serialNumber, mvrList, properties, fn);
        }

        return mvrList;
    }

    /**
     * Cook post response.
     *
     * @param serialNumber
     *            the serial number
     * @param mvrList
     *            the mvr list
     * @param properties
     *            the properties
     * @param fn
     *            the fn
     */
    private void cookPostResponse(String serialNumber, List<PostResponse> mvrList, Map<QName, Serializable> properties,
            String fn) {
        PostResponse epr = new PostResponse();
        epr.setDocumentId(serialNumber, Evidence.TYPE, fn);
        epr.setSerialNumber(serialNumber);

        epr.setVersion((String) properties.get(ContentModel.PROP_VERSION_LABEL));
        mvrList.add(epr);
    }

}
