package gov.uspto.trademark.cms.repo.services.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.repo.version.VersionBaseModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.version.Version;
import org.alfresco.service.cmr.version.VersionHistory;
import org.alfresco.service.cmr.version.VersionType;
import org.alfresco.service.transaction.TransactionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.RedactionRestoreOriginalException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.helpers.CaseNodeCreator;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsRedactionService;
import gov.uspto.trademark.cms.repo.services.DocumentServiceFactory;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.PostResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This class implements all the document redaction functionalities.
 * 
 * @author vnondapaka
 *
 */
@Component("documentRedactionService")
public class DocumentRedactionService implements CmsRedactionService {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(DocumentRedactionService.class);

    /** The Constant LOG_TEXT. */
    private static final String LOG_TEXT = "ID {}, FileName {}, docType {}";

    /** The Constant FLOAT_ONE_POINT_ZERO. */
    private static final float FLOAT_ONE_POINT_ZERO = 1.0f;

    /** The Constant TWO. */
    private static final int TWO = 2;

    /** The service registry. */
    @Autowired
    @Qualifier(value = "ServiceRegistry")
    protected ServiceRegistry serviceRegistry;
    
    @Autowired
    @Qualifier("TransactionService")    
    protected TransactionService transactionService;    

    /** The cms node locator. */
    @Autowired
    @Qualifier(value = "caseNodeLocator")
    CmsNodeLocator cmsNodeLocator;

    /** The case node creator. */
    @Autowired
    @Qualifier(value = "caseNodeCreator")
    CaseNodeCreator caseNodeCreator;

    /** The document service map. */
    @Resource(name = "documentServiceMap")
    private Map<String, String> documentServiceMap;

    /** The document service factory. */
    @Autowired
    @Qualifier(value = "documentServiceFactory")
    private DocumentServiceFactory documentServiceFactory;

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsRedactionService#redactDocument(
     * java.lang.String, java.lang.String, byte[], java.lang.String,
     * java.util.Map)
     */
    @Override
    public TmngAlfResponse redactDocument(String id, String fileName, ContentItem content, String docType,
            Map<String, Serializable> properties) {
        log.debug("RedactDocument Request for" + LOG_TEXT, new Object[] { id, fileName, docType });
        NodeRef originalNode = cmsNodeLocator.locateNode(id, fileName, TradeMarkDocumentTypes.getTradeMarkQName(docType));
        if (originalNode == null) {
            throw new DocumentDoesNotExistException("Serial Number or File does not exist");
        }
        String originalVersion = (String) serviceRegistry.getNodeService().getProperty(originalNode,
                TradeMarkModel.PROP_ORIGINAL_DOCUMENT_VERSION);
        if (StringUtils.isEmpty(originalVersion)) {
            originalVersion = (String) serviceRegistry.getNodeService().getProperty(originalNode,
                    ContentModel.PROP_VERSION_LABEL);
        }
        log.debug("Populating original Version >>" + originalVersion + ">>For >>" + id + "/" + fileName + "/" + docType);
        properties.put(TradeMarkModel.ORIGINAL_DOCUMENT_VERSION, originalVersion);

        return documentServiceFactory.getDocumentService(documentServiceMap.get(docType)).update(id, fileName, content,
                properties);

    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.CmsRedactionService#
     * restoreToOriginalVersion(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public TmngAlfResponse restoreToOriginalVersion(String id, String fileName, String docType) {
        log.debug("Restoring Original Version for " + LOG_TEXT, new Object[] { id, fileName, docType });
        PostResponse postResponse = null;
        final NodeRef documentNodeRef = cmsNodeLocator.locateNode(id, fileName,
                TradeMarkDocumentTypes.getTradeMarkQName(docType));
        if (documentNodeRef == null) {
            throw new SerialNumberNotFoundException(id + " File or Folder doesn't exist", null);
        }
        if (!serviceRegistry.getNodeService().hasAspect(documentNodeRef, TradeMarkModel.ASPECT_REDACTED)) {
            throw new RedactionRestoreOriginalException("Current Document is Original " + id);
        }
        final String originalVersionLabel = (String) serviceRegistry.getNodeService().getProperty(documentNodeRef,
                TradeMarkModel.PROP_ORIGINAL_DOCUMENT_VERSION);

        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<NodeRef> callback = new RetryingTransactionCallback<NodeRef>() {
            @Override
            public NodeRef execute() throws Throwable {
                VersionHistory versionHistory = serviceRegistry.getVersionService().getVersionHistory(documentNodeRef);
                Version originalVersion = versionHistory.getVersion(originalVersionLabel);
                serviceRegistry.getVersionService().revert(documentNodeRef, originalVersion, true);
                NodeRef workingCopyRef = serviceRegistry.getCheckOutCheckInService().checkout(documentNodeRef);
                Map<String, Serializable> props = new HashMap<String, Serializable>(DocumentRedactionService.TWO,
                        DocumentRedactionService.FLOAT_ONE_POINT_ZERO);
                props.put(VersionBaseModel.PROP_VERSION_TYPE, VersionType.MINOR);
                return serviceRegistry.getCheckOutCheckInService().checkin(workingCopyRef, props);
            }
        };
        final NodeRef nodeRef = txnHelper.doInTransaction(callback, false, true);
        if (nodeRef != null) {
            // Construct and Send Response
            postResponse = new PostResponse();
            postResponse.setDocumentId(id, docType, fileName);
            RetryingTransactionHelper txnHelperVersion = transactionService.getRetryingTransactionHelper();
            RetryingTransactionCallback<String> callbackVersion = new RetryingTransactionCallback<String>() {
                @Override
                public String execute() throws Throwable {
                    return (String) serviceRegistry.getNodeService().getProperty(nodeRef, ContentModel.PROP_VERSION_LABEL);
                }
            };
            postResponse.setVersion(txnHelperVersion.doInTransaction(callbackVersion, true, true));
            postResponse.setSerialNumber(id);
        }
        log.debug("Restoring Original Version Completed for ID {}, FileName {}, docType {}",
                new Object[] { id, fileName, docType });
        return postResponse;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.CmsRedactionService#
     * retrieveOriginalContent(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public ContentReader retrieveOriginalContent(String id, String fileName, String docType, CmsDataFilter dataFilter) {
        log.debug("Retrieve Original Content Request for" + LOG_TEXT, new Object[] { id, fileName, docType });
        String originalVersionNumber = getOriginalVersionNumber(id, fileName, docType);
        return documentServiceFactory.getDocumentService(documentServiceMap.get(docType)).retrieveContent(id, fileName,
                originalVersionNumber, dataFilter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.CmsRedactionService#
     * retrieveOriginalMetadata(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public byte[] retrieveOriginalMetadata(String id, String fileName, String docType, CmsDataFilter dataFilter) {
        log.debug("Retrieve Original Metadata Request for" + LOG_TEXT, new Object[] { id, fileName, docType });
        String originalVersionNumber = getOriginalVersionNumber(id, fileName, docType);
        return documentServiceFactory.getDocumentService(documentServiceMap.get(docType)).retrieveMetadata(id, fileName,
                originalVersionNumber, dataFilter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.CmsRedactionService#
     * retrieveRedactionMetadata(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public byte[] retrieveRedactionMetadata(String id, String fileName, String docType) {
        log.debug("Retrieve Redaction Metadata Request for" + LOG_TEXT, new Object[] { id, fileName, docType });
        byte[] metadata = null;
        if (isRedacted(id, fileName, docType)) {
            metadata = documentServiceFactory.getDocumentService(documentServiceMap.get(docType)).retrieveMetadata(id, fileName,
                    null);
        }
        return metadata;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.CmsRedactionService#
     * retrieveRedactionContent(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public ContentReader retrieveRedactionContent(String id, String fileName, String docType) {
        log.debug("Retrieve Redaction Content Request for" + LOG_TEXT, new Object[] { id, fileName, docType });
        ContentReader contentReader = null;
        if (isRedacted(id, fileName, docType)) {
            contentReader = documentServiceFactory.getDocumentService(documentServiceMap.get(docType)).retrieveContent(id,
                    fileName, null);
        }
        return contentReader;
    }

    /**
     * Gets the original version number.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param docType
     *            the doc type
     * @return the original version number
     */
    private String getOriginalVersionNumber(String id, String fileName, String docType) {
        NodeRef documentNodeRef = cmsNodeLocator.locateNode(id, fileName, TradeMarkDocumentTypes.getTradeMarkQName(docType));
        String originalVersion = null;
        if (documentNodeRef != null
                && serviceRegistry.getNodeService().hasAspect(documentNodeRef, TradeMarkModel.ASPECT_REDACTED)) {
            originalVersion = serviceRegistry.getNodeService()
                    .getProperty(documentNodeRef, TradeMarkModel.PROP_ORIGINAL_DOCUMENT_VERSION).toString();
        }

        return originalVersion;
    }

    /**
     * Checks if is redacted.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param docType
     *            the doc type
     * @return true, if is redacted
     */
    private boolean isRedacted(String id, String fileName, String docType) {
        NodeRef documentNodeRef = cmsNodeLocator.locateNode(id, fileName, TradeMarkDocumentTypes.getTradeMarkQName(docType));
        return (documentNodeRef != null
                && serviceRegistry.getNodeService().hasAspect(documentNodeRef, TradeMarkModel.ASPECT_REDACTED));
    }
}