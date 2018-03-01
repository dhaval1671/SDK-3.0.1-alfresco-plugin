package gov.uspto.trademark.cms.repo.services.impl.cabinet.madridib;

import java.io.Serializable;
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

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.DataDictionaryHelper;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.DocumentCreateListener;
import gov.uspto.trademark.cms.repo.services.impl.AbstractCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.MadridResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * The Class PublicationCommonService.
 */
public abstract class AbstractMadridBaseCommonService extends AbstractCommonService {

    /**
     * Instantiates a new publication common service.
     *
     * @param caseNodeCreator
     *            the case node creator
     * @param cmsNodeLocator
     *            the cms node locator
     */
    public AbstractMadridBaseCommonService(CmsNodeCreator caseNodeCreator, CmsNodeLocator cmsNodeLocator) {
        super(caseNodeCreator, cmsNodeLocator);
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
        MadridResponse postResponse = new MadridResponse();
        postResponse.setDocumentId(id, type.getLocalName(), fileName);
        postResponse.setId(id);
        return postResponse;
    }

    /**
     * This method updates the document with content based on given id, file
     * name and document type.
     *
     * @param <T>
     *            the generic type
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param type
     *            the type
     * @param properties
     *            the properties
     * @param docTypeClass
     *            the doc type class
     * @param content
     *            the content
     * @return the tmng alf response
     */
    public <T extends AbstractBaseType> TmngAlfResponse updateDocument(final String id, final String fileName, final QName type,
            final Map<String, Serializable> properties, Class<T> docTypeClass, final ContentItem content) {

        MadridResponse MadridResponse = null;
        T docType = JacksonHelper.generateDTOFrIncomingClientJson(properties, docTypeClass);
        final Map<QName, Serializable> repoMap = WebScriptHelper.generateAlfRepoPropsFrDTO(docType);

        // Id could be different for different consumers. For case related it is
        // serial number for efile it is TrademarkId
        // This will be set by the concrete implementations
        this.setMandatoryProperties(repoMap, id);

        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<NodeRef> callback = new RetryingTransactionCallback<NodeRef>() {
            @Override
            public NodeRef execute() throws Throwable {
                NodeRef documentNodeRef = cmsNodeLocator.locateNode(id, fileName, type);
                if (documentNodeRef != null) {
                    repoMap.put(ContentModel.PROP_NAME, fileName);
                    repoMap.put(DataDictionaryHelper.getTradeMarkPropertyQName(TradeMarkModel.DOCUMENT_NAME), fileName);
                    serviceRegistry.getNodeService().addProperties(documentNodeRef, repoMap);
                    if (content != null) {
                        ContentWriter writer = serviceRegistry.getContentService().getWriter(documentNodeRef,
                                ContentModel.PROP_CONTENT, true);
                        writer.putContent(content.getInputStream());
                        ContentData cd = (ContentData) serviceRegistry.getNodeService().getProperty(documentNodeRef,
                                ContentModel.PROP_CONTENT);
                        String mimeType = serviceRegistry.getMimetypeService().guessMimetype(fileName, writer.getReader());
                        ContentData newCD = ContentData.setMimetype(cd, mimeType);
                        serviceRegistry.getNodeService().setProperty(documentNodeRef, ContentModel.PROP_CONTENT, newCD);
                    }
                }
                return documentNodeRef;
            }
        };
        final NodeRef nodeRef = txnHelper.doInTransaction(callback, false, true);
        if (nodeRef != null) {
            // Construct and Send Response
            MadridResponse = new MadridResponse();
            MadridResponse.setDocumentId(id, type.getLocalName(), fileName);
            MadridResponse.setId(id);
        }
        return MadridResponse;
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
