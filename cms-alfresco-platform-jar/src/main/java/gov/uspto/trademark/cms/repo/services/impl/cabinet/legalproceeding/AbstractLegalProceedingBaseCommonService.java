package gov.uspto.trademark.cms.repo.services.impl.cabinet.legalproceeding;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.LegalProceedingNodeCreator;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.nodelocator.LegalProceedingNodeLocator;
import gov.uspto.trademark.cms.repo.services.DocumentCreateListener;
import gov.uspto.trademark.cms.repo.services.impl.AbstractCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.LegalProceedingResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * The Class PublicationCommonService.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk} 11/30/2016.
 */
public abstract class AbstractLegalProceedingBaseCommonService extends AbstractCommonService {

    @Autowired
    @Qualifier("TransactionService")    
    protected TransactionService transactionService;
    
    /**
     * Instantiates a new publication common service.
     *
     * @param caseNodeCreator
     *            the case node creator
     * @param cmsNodeLocator
     *            the cms node locator
     */

    public AbstractLegalProceedingBaseCommonService(LegalProceedingNodeCreator legalProceedingNodeCreator,
            LegalProceedingNodeLocator legalProceedingNodeLocator) {
        super(legalProceedingNodeCreator, legalProceedingNodeLocator);
    }

    /**
     * This method creates the document for the given id, file name and type
     * with inputstream content and properties map.
     *
     * @param <T>
     *            the generic type
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param type
     *            the type
     * @param properties
     *            the properties
     * @param docTypeClass
     *            the doc type class
     * @return the tmng alf response
     */
    @Override
    public <T extends AbstractBaseType> TmngAlfResponse createDocument(final String id, final String fileName,
            final ContentItem content, final QName type, final Map<String, Serializable> properties, Class<T> docTypeClass) {
        return this.createDocument(id, fileName, content, type, properties, docTypeClass, new DocumentCreateListener() {

            @Override
            public void listen() {

            }
        });
    }

    /**
     * This method creates the document for the given id, file name and type
     * with inputstream content and properties map.
     *
     * @param <T>
     *            the generic type
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param type
     *            the type
     * @param properties
     *            the properties
     * @param docTypeClass
     *            the doc type class
     * @param docCreateListener
     *            the doc create listener
     * @return the tmng alf response
     */
    @Override
    public <T extends AbstractBaseType> TmngAlfResponse createDocument(final String id, final String fileName,
            final ContentItem content, final QName type, final Map<String, Serializable> properties, Class<T> docTypeClass,
            final DocumentCreateListener docCreateListener) {
        T docType = JacksonHelper.generateDTOFrIncomingClientJson(properties, docTypeClass);
        final Map<QName, Serializable> repoMap = WebScriptHelper.generateAlfRepoPropsFrDTO(docType);

        // Id could be different for different consumers. For case related it is
        // serial number for efile it is TrademarkId
        // This will be set by the concrete implementations
        this.setMandatoryProperties(repoMap, id);

        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionHelper.RetryingTransactionCallback<NodeRef> callback = new RetryingTransactionHelper.RetryingTransactionCallback<NodeRef>() {
            @Override
            public NodeRef execute() throws Throwable {
                NodeRef parentFolder = cmsNodeCreator.createNode(id);
                repoMap.put(ContentModel.PROP_NAME, fileName);
                repoMap.put(TradeMarkModel.PROP_DOCUMENT_NAME, fileName);
                QName lclQname = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, fileName);

                ChildAssociationRef newNodeAssoc = serviceRegistry.getNodeService().createNode(parentFolder,
                        ContentModel.ASSOC_CONTAINS, lclQname, type, repoMap);
                NodeRef resultNode = newNodeAssoc.getChildRef();
                ContentWriter writer = serviceRegistry.getContentService().getWriter(resultNode, ContentModel.PROP_CONTENT, true);
                writer.putContent(content.getInputStream());
                ContentData cd = (ContentData) serviceRegistry.getNodeService().getProperty(resultNode,
                        ContentModel.PROP_CONTENT);
                String mimeType = serviceRegistry.getMimetypeService().guessMimetype(fileName, writer.getReader());
                ContentData newCD = ContentData.setMimetype(cd, mimeType);
                serviceRegistry.getNodeService().setProperty(resultNode, ContentModel.PROP_CONTENT, newCD);

                docCreateListener.listen();

                return resultNode;
            }
        };

        txnHelper.doInTransaction(callback, false, true);
        LegalProceedingResponse postResponse = new LegalProceedingResponse();
        postResponse.setDocumentId(id, type.getLocalName(), fileName);
        postResponse.setId(id);
        return postResponse;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#create(java.lang.
     * String, java.lang.String, byte[], java.util.Map)
     */
    @Override
    public TmngAlfResponse create(final String id, final String fileName, final ContentItem content,
            Map<String, Serializable> properties) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#update(java.lang.
     * String, java.lang.String, byte[], java.util.Map)
     */
    @Override
    public TmngAlfResponse update(final String id, final String fileName, final ContentItem content,
            Map<String, Serializable> properties) {
        throw new UnsupportedOperationException();
    }

}
