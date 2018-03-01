package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.servlet.FormData.FormField;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.nodedelete.DocumentHardDelete;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.WebcaptureService;
import gov.uspto.trademark.cms.repo.services.impl.AbstractBaseService;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.WebcaptureResponse;

/**
 * Created by stank on 5/3/2017.
 * @author stank
 */
@Component("WebcaptureImplService")
public class WebcaptureImplService extends AbstractBaseService implements WebcaptureService {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(WebcaptureImplService.class);

    @Autowired
    @Qualifier(value = "caseNodeLocator")
    private CmsNodeLocator cmsNodeLocator;

    @Autowired
    @Qualifier("DocumentHardDelete")
    private DocumentHardDelete documentHardDelete;    
    
    @Override    
    public NodeRef getDocumentLibraryFolderNodeRef(){
        return getFolderNodeRef(TradeMarkModel.DOCUMENT_LIBRARY_FOLDER_NAME);
    }
    
    @Override
    public NodeRef getWebcaptureFolderNodeRef() {
        return getFolderNodeRef(TradeMarkModel.DOCUMENT_LIBRARY_FOLDER_NAME, TradeMarkModel.WEBCAPTURE_FOLDER_NAME);
    }    
    
    @Override
    public TmngAlfResponse createWebcapture(final String userid, final String fileName, final FormField content) {
        final NodeRef documentLibraryNodeRef = getDocumentLibraryFolderNodeRef();
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<WebcaptureResponse> callback = new RetryingTransactionCallback<WebcaptureResponse>() {
            @Override
            public WebcaptureResponse execute() throws Throwable {
                NodeRef webcaptureFolderNodeRef = createFolder(documentLibraryNodeRef, TradeMarkModel.WEBCAPTURE_FOLDER_NAME);
                NodeRef userIdFolderNodeRef = createFolder(webcaptureFolderNodeRef, userid);
                Map<QName, Serializable> props = new HashMap<QName, Serializable>(TMConstants.TWO, 1.0f);
                props.put(ContentModel.PROP_NAME, fileName);
                QName lclQname = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, fileName);
                ChildAssociationRef childRef = serviceRegistry.getNodeService().createNode(userIdFolderNodeRef,ContentModel.ASSOC_CONTAINS, lclQname, ContentModel.TYPE_CONTENT, props);
                NodeRef newContentNodeRef = childRef.getChildRef();
                ContentWriter tempWriter = serviceRegistry.getContentService().getTempWriter();
                tempWriter.putContent(content.getInputStream());
                ContentWriter writer = serviceRegistry.getContentService().getWriter(newContentNodeRef, ContentModel.PROP_CONTENT,true);
                writer.putContent(tempWriter.getReader());
                
                String mimeType = serviceRegistry.getMimetypeService().guessMimetype(fileName, tempWriter.getReader());           
                ContentData cd = (ContentData) serviceRegistry.getNodeService().getProperty(newContentNodeRef, ContentModel.PROP_CONTENT);
                ContentData newCD = ContentData.setMimetype(cd, mimeType);
                serviceRegistry.getNodeService().setProperty(newContentNodeRef, ContentModel.PROP_CONTENT, newCD);                
                
                long fileSize = tempWriter.getSize();
                WebcaptureResponse wr = new WebcaptureResponse();
                wr.setSize(Long.toString(fileSize));
                return wr;
            }
        };
        return txnHelper.doInTransaction(callback, false, true);
    }

    @Override
    public WebcaptureResponse deleteWebcapture(String userid, String fileName) {
        WebcaptureResponse wr;
        NodeRef webcaptureNodeRef = getWebcaptureFolderNodeRef();
        String docId = TMConstants.FORWARD_SLASH + TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase() + TMConstants.FORWARD_SLASH + userid + TMConstants.FORWARD_SLASH + fileName;
        NodeRef documentNodeRef = cmsNodeLocator.locateNodeRef(webcaptureNodeRef, userid, fileName);
        if (null != documentNodeRef) {
            wr = new WebcaptureResponse();
            log.debug("### Hard Delete " + fileName + " ####");
            documentHardDelete.hardDeleteNoChecks(documentNodeRef, Boolean.TRUE);
            wr.setDocumentId(docId);
        }else{
            throw new TmngCmsException.DocumentDoesNotExistException("Requested File \"" + docId + "\" Not Found");
        }
        return wr;
    }

    @Override
    public WebcaptureResponse renameWebcapture(final String userid, final String oldFileName, final String newFileName) {
        
        final String oldDocId = TMConstants.FORWARD_SLASH + TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase() + TMConstants.FORWARD_SLASH + userid + TMConstants.FORWARD_SLASH + oldFileName;
        String newDocId = TMConstants.FORWARD_SLASH + TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase() + TMConstants.FORWARD_SLASH + userid + TMConstants.FORWARD_SLASH + newFileName;
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<NodeRef> callback = new RetryingTransactionCallback<NodeRef>() {
            @Override
            public NodeRef execute() throws Throwable {
                NodeRef webcaptureNodeRef = getWebcaptureFolderNodeRef();
                NodeRef documentNodeRef = cmsNodeLocator.locateNodeRef(webcaptureNodeRef, userid, oldFileName);
                
                if (documentNodeRef != null) {
                    if(oldFileName.equalsIgnoreCase(newFileName)){
                        throw new TmngCmsException.CMSWebScriptException(HttpStatus.CONFLICT, "'oldFileName' and 'newFileName' should be (ignore-case) different.");
                    }                    
                    
                    Map<QName, Serializable> repoMap = new HashMap<QName, Serializable>();
                    repoMap.put(ContentModel.PROP_NAME, newFileName);
                    serviceRegistry.getNodeService().addProperties(documentNodeRef, repoMap);
                }else{
                    throw new TmngCmsException.DocumentDoesNotExistException("Requested File \"" + oldDocId + "\" Not Found"); 
                }
                return documentNodeRef;
            }
        };

        NodeRef updateNodeRef = txnHelper.doInTransaction(callback, false, true);

        WebcaptureResponse wr = null;
        if(null != updateNodeRef){
            wr = new WebcaptureResponse();
            wr.setDocumentId(newDocId);
        }
        return wr;
    }

}
