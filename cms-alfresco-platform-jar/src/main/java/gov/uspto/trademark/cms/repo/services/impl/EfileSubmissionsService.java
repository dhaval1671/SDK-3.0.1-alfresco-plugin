package gov.uspto.trademark.cms.repo.services.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.nodedelete.BehaviorDocumentDelete;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.DocumentCreateListener;
import gov.uspto.trademark.cms.repo.services.DocumentServiceFactory;
import gov.uspto.trademark.cms.repo.services.SubmissionsService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.PostResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.SubmissionResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;
import gov.uspto.trademark.cms.repo.webscripts.efile.vo.SubmissionJson;

/**
 * This class is used to submit the files from e-file to submissions / case
 * folder.
 * 
 * @author vnondapaka
 *
 */
@Component("submissionsService")
public class EfileSubmissionsService implements SubmissionsService {

    /** The case service. */
    @Autowired
    protected CaseService caseService;

    @Autowired
    CmsNodeCreator caseNodeCreator;

    @Autowired
    CmsNodeCreator submissionNodeCreator;

    @Autowired
    CmsNodeLocator submissionNodeLocator;

    @Autowired
    @Qualifier(value = "eFileNodeLocator")
    CmsNodeLocator efileNodeLocator;

    @Autowired
    CmsNodeLocator caseNodeLocator;

    @Autowired
    @Qualifier("DocumentHardDelete")
    BehaviorDocumentDelete documentHardDelete;

    @Resource(name = "documentServiceMap")
    private Map<String, String> documentServiceMap;

    @Autowired
    @Qualifier(value = "ServiceRegistry")
    protected ServiceRegistry serviceRegistry;
    
    @Autowired
    @Qualifier("TransactionService")    
    protected TransactionService transactionService;    

    /** The document service factory. */
    @Autowired
    @Qualifier(value = "documentServiceFactory")
    private DocumentServiceFactory documentServiceFactory;

    @Override
    public List<TmngAlfResponse> submitEfile(final SubmissionJson[] submissionJsonArray) {
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionHelper.RetryingTransactionCallback<List<TmngAlfResponse>> callback = new RetryingTransactionHelper.RetryingTransactionCallback<List<TmngAlfResponse>>() {
            @Override
            public List<TmngAlfResponse> execute() throws Throwable {
                List<TmngAlfResponse> submissionResponse = new ArrayList<TmngAlfResponse>();
                // Run in a transaction
                for (SubmissionJson submissionJson : submissionJsonArray) {
                    // check if file exists in temp space
                    String[] documentIdParts = submissionJson.getDocumentId().split("/");
                    String globalId = documentIdParts[TMConstants.THREE];
                    String fileName = documentIdParts[TMConstants.FOUR];
                    NodeRef eFileNodeRef = efileNodeLocator.locateNode(globalId, fileName, TradeMarkModel.EFILE_QNAME);
                    if (eFileNodeRef == null) {
                        throw new TmngCmsException.PathNotFoundException("Invalid eFile " + submissionJson.getDocumentId());
                    }
                    QName typeQName = TradeMarkDocumentTypes.getTradeMarkQName(submissionJson.getDocumentType());
                    byte[] metadata = JacksonHelper.generateClientJsonFrDTO(submissionJson.getMetadata());
                    Map<String, Serializable> metadataProperties = WebScriptHelper.parseJson(metadata);
                    InputStream inputStream = serviceRegistry.getContentService()
                            .getReader(eFileNodeRef, ContentModel.PROP_CONTENT).getContentInputStream();
                    TmngAlfResponse submissionResp = create(globalId, fileName, inputStream, metadataProperties, submissionJson);
                    if (submissionJson.getSerialNumbers() == null || submissionJson.getSerialNumbers().isEmpty()) {
                        submissionResponse.add(submissionResp);
                    }
                    // Copy to respective serial numbers
                    NodeRef submissionNodeRef = submissionNodeLocator.locateNode(globalId, fileName, typeQName);
                    for (String serialNumber : submissionJson.getSerialNumbers()) {
                        TmngAlfResponse postResponse = createFileLink(serialNumber, fileName, submissionNodeRef,
                                submissionJson.getDocumentType());
                        submissionResponse.add(postResponse);
                    }
                    // Delete the file at this point
                    documentHardDelete.delete(eFileNodeRef, Boolean.TRUE);
                }
                return submissionResponse;

            }
        };
        return txnHelper.doInTransaction(callback, false, true);
    }

    /**
     * This method is used to create links under case folders from the original
     * file under submissions folder.
     * 
     * @param serialNumber
     * @param fileName
     * @param submissionNodeRef
     * @param docType
     * @return
     */
    private TmngAlfResponse createFileLink(String serialNumber, String fileName, NodeRef submissionNodeRef, String docType) {
        NodeRef caseFolderNodeRef = caseNodeCreator.createNode(serialNumber);
        Map<QName, Serializable> props = new HashMap<QName, Serializable>(TMConstants.TWO, 1.0f);
        props.put(ContentModel.PROP_NAME, fileName);
        props.put(ContentModel.PROP_LINK_DESTINATION, submissionNodeRef);
        QName lclQname = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, fileName);

        ChildAssociationRef childRef = serviceRegistry.getNodeService().createNode(caseFolderNodeRef, ContentModel.ASSOC_CONTAINS,
                lclQname, ApplicationModel.TYPE_FILELINK, props);

        // apply the titled aspect - title and description
        Map<QName, Serializable> titledProps = new HashMap<QName, Serializable>(TMConstants.TWO, 1.0f);
        titledProps.put(ContentModel.PROP_TITLE, fileName);
        titledProps.put(ContentModel.PROP_DESCRIPTION, fileName);
        serviceRegistry.getNodeService().addAspect(childRef.getChildRef(), ContentModel.ASPECT_TITLED, titledProps);

        PostResponse postResponse = new PostResponse();
        postResponse.setDocumentId(serialNumber, docType, fileName);
        postResponse.setVersion(
                (String) serviceRegistry.getNodeService().getProperty(submissionNodeRef, ContentModel.PROP_VERSION_LABEL));
        postResponse.setSerialNumber(serialNumber);
        return postResponse;

    }

    /**
     * This method is used to create the document from e-file
     * 
     * @param id
     * @param fileName
     * @param content
     * @param properties
     * @param submissionJson
     * @return
     */
    private TmngAlfResponse create(String id, String fileName, InputStream content, Map<String, Serializable> properties,
            SubmissionJson submissionJson) {
        String documentType = submissionJson.getDocumentType();
        QName typeQName = TradeMarkDocumentTypes.getTradeMarkQName(documentType);
        Class<? extends AbstractBaseType> t = TradeMarkDocumentTypes.getTradeMarkType(documentType).getTypeClass();
        DocumentCreateListener listener = new DocumentCreateListener() {
            @Override
            public void listen() {
            }
        };
        TmngAlfResponse submissionResp = documentServiceFactory.getDocumentService(documentServiceMap.get(documentType))
                .createDocument(id, fileName, ContentItem.getInstance(content), typeQName, properties, t, listener,
                        submissionNodeCreator);

        SubmissionResponse postResponse = new SubmissionResponse();
        postResponse.setDocumentId(id, submissionJson.getDocumentType(), fileName);
        postResponse.setVersion(((PostResponse) submissionResp).getVersion());
        postResponse.setId(id);
        return postResponse;

    }

}
