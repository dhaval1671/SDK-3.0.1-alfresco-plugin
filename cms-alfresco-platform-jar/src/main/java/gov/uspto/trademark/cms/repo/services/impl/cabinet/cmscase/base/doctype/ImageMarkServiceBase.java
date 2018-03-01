package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.Serializable;
import java.util.HashMap;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.rendition.RenditionDefinitionImpl;
import org.alfresco.repo.rendition.executer.ImageRenderingEngine;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.cmr.rendition.RenditionDefinition;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.MarkRenditions;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.BehaviorImageMarkService;

/**
 * The Class ImageMarkServiceBase.
 *
 * @author bgummadi
 */
public class ImageMarkServiceBase extends MarkServiceBase implements BehaviorImageMarkService {

    private static final String UNABLE_TO_CREATE_RENDITION = "Unable to create rendition";

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(ImageMarkServiceBase.class);

    /** Supported renditions **/

    // Why not map in context file ????

    /** The cms node locator. */
    @Autowired
    @Qualifier(value = "caseNodeLocator")
    CmsNodeLocator cmsNodeLocator;

    /**
     * Returns metadata for a version of the mark.
     *
     * @param serialNumber
     *            the serial number
     * @param versionNumber
     *            the version number
     * @return the mark reader
     */
    @Override
    public ContentReader getMarkReader(String serialNumber, String versionNumber) {
        NodeRef nodeRef = getVersionNodeRef(serialNumber, versionNumber, TradeMarkModel.MARK_QNAME);
        return getContentReader(nodeRef, ContentModel.PROP_CONTENT);
    }

    /**
     * Checks if renditions can be supported for a given mimeType
     *
     * @param mimeType
     * @return
     */
    @Override
    public boolean isRenditionSupported(String mimeType) {
        return mimeType.startsWith("image/");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.services.TradeMarkService#getMarkRenditionReader
     * (org.alfresco.service.cmr.repository.NodeRef, java.lang.String)
     */
    @Override
    public ContentReader getMarkRenditionReader(NodeRef nodeRef, String renditionName) {
        ContentData cd = (ContentData) serviceRegistry.getNodeService().getProperty(nodeRef, ContentModel.PROP_CONTENT);
        String mimeType = cd.getMimetype();

        boolean isRenditionSupported = isRenditionSupported(mimeType);
        if (!isRenditionSupported) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                    mimeType + " Mark mime type is NOT supported for Rendition, only image mime types are supported");
        }

        QName renditionQName = QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI, renditionName);
        ChildAssociationRef renditionNodeRef = this.serviceRegistry.getRenditionService().getRenditionByName(nodeRef,
                renditionQName);
        if (null == renditionNodeRef) {
            log.debug("Rendition not found {} for {}", renditionName, nodeRef);
            renditionNodeRef = createRendition(nodeRef, MarkRenditions.fromRenditionName(renditionName));
        }
        return getContentReader(renditionNodeRef.getChildRef(), ContentModel.PROP_CONTENT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.services.TradeMarkService#getMarkName(org.
     * alfresco .service.cmr.repository.NodeRef)
     */
    @Override
    public String getMarkName(NodeRef nodeRef) {
        return (String) getProperty(nodeRef, ContentModel.PROP_NAME);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.services.TradeMarkService#createRendition(org
     * .alfresco.service.cmr.repository.NodeRef,
     * gov.uspto.trademark.cms.constants.MarkRenditions)
     */
    @Override
    public ChildAssociationRef createRendition(final NodeRef sourceNode, final MarkRenditions renditionName) {
        if (log.isDebugEnabled()) {
            log.debug("Creating rendition");
            log.debug("MarkRenditions {}" + renditionName.getProperties().toString());
        }
        ContentData cd = (ContentData) serviceRegistry.getNodeService().getProperty(sourceNode, ContentModel.PROP_CONTENT);
        String mimeType = cd.getMimetype();

        boolean isRenditionSupported = isRenditionSupported(mimeType);
        if (!isRenditionSupported) {
            throw new TmngCmsException.CMSRuntimeException(UNABLE_TO_CREATE_RENDITION);
        }
        ChildAssociationRef renditionAssocTwo = null;
        try {
            RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
            RetryingTransactionHelper.RetryingTransactionCallback<ChildAssociationRef> callback = new RetryingTransactionHelper.RetryingTransactionCallback<ChildAssociationRef>() {
                @Override
                public ChildAssociationRef execute() throws Throwable {
                    StopWatch sw = new StopWatch();
                    sw.start();
                    String fileName = (String) serviceRegistry.getNodeService().getProperty(sourceNode, ContentModel.PROP_NAME);
                    QName renditionQName = QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI,renditionName.getRenditionName());
                    RenditionDefinition renditionDef = serviceRegistry.getRenditionService().createRenditionDefinition(renditionQName, ImageRenderingEngine.NAME);
                    renditionDef.setParameterValues(renditionName.getProperties());
                    renditionDef.setParameterValue(RenditionDefinitionImpl.RENDITION_DEFINITION_NAME, renditionQName);
                    ChildAssociationRef car = serviceRegistry.getRenditionService().render(sourceNode, renditionDef); 
                    sw.stop();
                    long duration = sw.getTime();
                    if(log.isInfoEnabled()){
                        log.info("It took " + duration + " ms, to create rendition \'" + renditionName.getRenditionName() + "\' for \'" + fileName + "\'");
                    }
                    return car;
                }
            };
            renditionAssocTwo = txnHelper.doInTransaction(callback, false, true);

            if (null == renditionAssocTwo) {
                throw new TmngCmsException.CMSRuntimeException(UNABLE_TO_CREATE_RENDITION);
            } else {
                log.debug("Rendition created {}", renditionAssocTwo.getChildRef());
            }
        } catch (AlfrescoRuntimeException are) {
            serviceRegistry.getNodeService().addAspect(sourceNode, TradeMarkModel.NO_RENDITION_MARK,
                    new HashMap<QName, Serializable>());
            if (log.isDebugEnabled()) {
                log.debug(UNABLE_TO_CREATE_RENDITION, are);
            }
            throw new TmngCmsException.CMSRuntimeException(UNABLE_TO_CREATE_RENDITION, are);
        }
        return renditionAssocTwo;
    }

}