package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.content.transform.ContentTransformer;
import org.alfresco.repo.content.transform.UnsupportedTransformationException;
import org.alfresco.repo.policy.BehaviourFilter;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.TransformationOptions;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;

/**
 * Created by stank on 4/29/2016.
 */
@Component("multimediaService")
public class MultimediaService {

    private static final Logger log = LoggerFactory.getLogger(MultimediaService.class);

    /** The service registry. */
    @Autowired
    @Qualifier(value = "ServiceRegistry")
    protected ServiceRegistry serviceRegistry;
    
    @Autowired
    @Qualifier("TransactionService")    
    protected TransactionService transactionService;    

    @Autowired
    @Qualifier(value = "policyBehaviourFilter")
    private BehaviourFilter behaviourFilter;

    /** The cms node locator. */
    @Autowired
    @Qualifier(value = "caseNodeLocator")
    CmsNodeLocator cmsNodeLocator;

    @Autowired
    @Qualifier(value = "caseNodeCreator")
    CmsNodeCreator cmsNodeCreator;

    /**
     * Instantiates a new web script helper.
     */
    private MultimediaService() {

    }

    /**
     * 
     * Check if the parent folder is either the multimedia folder or the serial
     * number folder. The multimedia folder is a sub-folder of the serial number
     * folder.
     * 
     * @return boolean the parent folder is either a multimedia folder or the
     *         serial number folder.
     */
    private boolean validateParentFolder(String mediaMimeType, NodeRef serialNumberFolderNodeRef, boolean isPolicyMakingChanges) {
        QName qnameOfCaseFolder = serviceRegistry.getNodeService().getType(serialNumberFolderNodeRef);
        QName staticQNameOfCaseFolder = TradeMarkModel.CASE_FOLDER_QNAME;
        boolean isChangeInCaseFolder = staticQNameOfCaseFolder.isMatch(qnameOfCaseFolder);
        boolean caseFolderORMultimediaFolder = false;

        if (!isPolicyMakingChanges && isChangeInCaseFolder) {
            caseFolderORMultimediaFolder = true;
        }

        String incomingFolderName = (String) serviceRegistry.getNodeService().getProperty(serialNumberFolderNodeRef,
                ContentModel.PROP_NAME);
        if (StringUtils.equalsIgnoreCase(TMConstants.MULTIMEDIA_FOLDER_NAME, incomingFolderName)) {
            caseFolderORMultimediaFolder = true;
        }

        boolean parentCascadeTriggerSuppress = true;
        if (isChangeInCaseFolder && isPolicyMakingChanges && (StringUtils.equalsIgnoreCase(TMConstants.VIDEO_MP4, mediaMimeType)
                || StringUtils.equalsIgnoreCase(TMConstants.AUDIO_MPEG, mediaMimeType))) {
            parentCascadeTriggerSuppress = false;
        }
        log.trace("Completed validating incoming mime type: " + mediaMimeType + " folder type? " + caseFolderORMultimediaFolder
                + "; change in case?" + isChangeInCaseFolder + "; policy change? " + isPolicyMakingChanges + "; parent casecade? "
                + parentCascadeTriggerSuppress + "; returning: "
                + (caseFolderORMultimediaFolder && parentCascadeTriggerSuppress));
        return caseFolderORMultimediaFolder && parentCascadeTriggerSuppress;
    }

    private void convertMultimediaFile(final NodeRef serialNumberFolderNodeRef, final NodeRef incomingMediaTypeNodeRef, final String oldMediaFileName, final String newMediaFileName, final String oldMediaNewGuessMimeType, final String newMimeType) {
        Map<QName, Serializable> targetRepoMap = serviceRegistry.getNodeService().getProperties(incomingMediaTypeNodeRef);
        targetRepoMap.remove(ContentModel.PROP_NODE_UUID);
        targetRepoMap.remove(ContentModel.PROP_NODE_DBID);
        targetRepoMap.remove(ContentModel.PROP_CONTENT);
        targetRepoMap.remove(ContentModel.PROP_NAME);
        targetRepoMap.put(ContentModel.PROP_NAME, newMediaFileName);
        QName lclQname = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, newMediaFileName);
        QName type = serviceRegistry.getNodeService().getType(incomingMediaTypeNodeRef);
        behaviourFilter.disableBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME);
        ChildAssociationRef newNodeAssoc = serviceRegistry.getNodeService().createNode(serialNumberFolderNodeRef, ContentModel.ASSOC_CONTAINS, lclQname, type, targetRepoMap);
        NodeRef targetFileNodeRef = newNodeAssoc.getChildRef();
        ContentData cd = (ContentData) serviceRegistry.getNodeService().getProperty(targetFileNodeRef, ContentModel.PROP_CONTENT);
        ContentData newCD = ContentData.setMimetype(cd, TMConstants.VIDEO_MP4);
        if ((null != newMimeType) && (StringUtils.startsWith(newMimeType, TMConstants.AUDIO)) ) {
            newCD = ContentData.setMimetype(cd, TMConstants.AUDIO_MPEG);
        }
        serviceRegistry.getNodeService().setProperty(targetFileNodeRef, ContentModel.PROP_CONTENT, newCD);         
        log.debug("Convert file: " + oldMediaFileName);
        ContentReader incomingMediaTypeFileReader = serviceRegistry.getContentService().getReader(incomingMediaTypeNodeRef, ContentModel.PROP_CONTENT);
        ContentWriter mpeg4Writer = serviceRegistry.getContentService().getWriter(targetFileNodeRef, ContentModel.PROP_CONTENT, true);
        ContentTransformer incomingMediaTypeToMpeg4Transformer = getAppropriateTransformerForCreateEvent(oldMediaNewGuessMimeType);
    
        TransformationOptions options = new TransformationOptions();
        Map<String, Object> optionsMap = new HashMap<String, Object>();
        optionsMap.put(TransformationOptions.OPT_SOURCE_NODEREF, incomingMediaTypeNodeRef);
        optionsMap.put(TransformationOptions.OPT_SOURCE_CONTENT_PROPERTY, ContentModel.PROP_CONTENT);
        optionsMap.put(TransformationOptions.OPT_TARGET_NODEREF, targetFileNodeRef);
        optionsMap.put(TransformationOptions.OPT_TARGET_CONTENT_PROPERTY, ContentModel.PROP_CONTENT);
        optionsMap.put(TransformationOptions.OPT_INCLUDE_EMBEDDED, Boolean.FALSE);
        options.set(optionsMap);
        try {
            incomingMediaTypeToMpeg4Transformer.transform(incomingMediaTypeFileReader, mpeg4Writer, options);
            serviceRegistry.getNodeService().createAssociation(incomingMediaTypeNodeRef, targetFileNodeRef,TradeMarkModel.ASSOC_CONVERTED_MPEG4);
            serviceRegistry.getNodeService().createAssociation(targetFileNodeRef, incomingMediaTypeNodeRef,TradeMarkModel.ASSOC_ORIGINAL_SOURCE_MEDIA);
            behaviourFilter.enableBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME);
        } catch (ContentIOException e) {
            log.error("Unable to convert the multi-media file: " + oldMediaFileName, e);
            behaviourFilter.disableBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME);
            serviceRegistry.getCopyService().copy(incomingMediaTypeNodeRef, targetFileNodeRef);
        }
    }
    
    private void convertMultimediaFileForUpdate(final NodeRef caseFolderRef, final NodeRef incomingMediaTypeNodeRef, final NodeRef targetFileNodeRef, final String oldMediaFileName, final String oldMediaNewGuessMimeType) {
        log.debug("convertMultimediaFileForUpdate file: " + oldMediaFileName);
        ContentReader incomingMediaTypeFileReader = serviceRegistry.getContentService().getReader(incomingMediaTypeNodeRef, ContentModel.PROP_CONTENT);
        ContentWriter mpeg4Writer = serviceRegistry.getContentService().getWriter(targetFileNodeRef, ContentModel.PROP_CONTENT, true);
        ContentTransformer incomingMediaTypeToMpeg4Transformer = getAppropriateTransformerForCreateEvent(oldMediaNewGuessMimeType);
        TransformationOptions options = new TransformationOptions();
        Map<String, Object> optionsMap = new HashMap<String, Object>();
        optionsMap.put(TransformationOptions.OPT_SOURCE_NODEREF, incomingMediaTypeNodeRef);
        optionsMap.put(TransformationOptions.OPT_SOURCE_CONTENT_PROPERTY, ContentModel.PROP_CONTENT);
        optionsMap.put(TransformationOptions.OPT_TARGET_NODEREF, targetFileNodeRef);
        optionsMap.put(TransformationOptions.OPT_TARGET_CONTENT_PROPERTY, ContentModel.PROP_CONTENT);
        optionsMap.put(TransformationOptions.OPT_INCLUDE_EMBEDDED, Boolean.FALSE);
        options.set(optionsMap);
        try {
            incomingMediaTypeToMpeg4Transformer.transform(incomingMediaTypeFileReader, mpeg4Writer, options);
            //copy metadata with certain properties removed.
            Map<QName, Serializable> repoMap = serviceRegistry.getNodeService().getProperties(incomingMediaTypeNodeRef);
            repoMap.remove(ContentModel.PROP_NODE_UUID);
            repoMap.remove(ContentModel.PROP_NODE_DBID);
            repoMap.remove(ContentModel.PROP_CONTENT);
            repoMap.remove(ContentModel.PROP_NAME);
            repoMap.remove(ContentModel.PROP_VERSION_LABEL);
            serviceRegistry.getNodeService().addProperties(targetFileNodeRef, repoMap);
            behaviourFilter.enableBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME);
        } catch (ContentIOException | UnsupportedTransformationException e) {
            log.error("Update Operation: Unable to convert the multi-media file: " + oldMediaFileName, e);
            behaviourFilter.disableBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME);
            NodeRef targetNodeRefInsideSerialCaseFolder = cmsNodeLocator.locateNodeRef(caseFolderRef, oldMediaFileName);
            serviceRegistry.getCopyService().copy(incomingMediaTypeNodeRef, targetNodeRefInsideSerialCaseFolder);   
        }
    }    

    
    
    public boolean isMultimediaContentChangeInsideCaseOrMulFolder(String mediaMimeType, NodeRef serialNumberFolderNodeRef,
            QName qname, boolean isPolicyMakingChanges) {
        boolean returningResult = false;
        log.debug("Checking if the incoming mime type: " + mediaMimeType + " is to be converted...");
        boolean incomingMimeTypeIsVideoType = StringUtils.startsWith(mediaMimeType, TMConstants.VIDEO)
                || StringUtils.startsWith(mediaMimeType, TMConstants.AUDIO);

        if (!incomingMimeTypeIsVideoType) {
            return returningResult;
        }

        boolean isEvidence = TradeMarkModel.EVIDENCE_QNAME.isMatch(qname);
        boolean isSpecimen = TradeMarkModel.SPECIMEN_QNAME.isMatch(qname);
        boolean isMark = TradeMarkModel.MARK_QNAME.isMatch(qname);
        boolean isLegacy = TradeMarkModel.LEGACY_QNAME.isMatch(qname);

        boolean isVideoFileFormatSupportedByDocType = isEvidence || isSpecimen || isMark || isLegacy;

        log.trace("Checking if the incoming mime type: " + mediaMimeType + " is to supported doc type: "
                + isVideoFileFormatSupportedByDocType);
        if (!isVideoFileFormatSupportedByDocType) {
            return returningResult;
        }

        return validateParentFolder(mediaMimeType, serialNumberFolderNodeRef, isPolicyMakingChanges);
    }

    public NodeRef getMultimediaFolder(NodeRef currentFolderNodeRef, String incomingFolderName) {
        QName qnameOfCaseFolder = serviceRegistry.getNodeService().getType(currentFolderNodeRef);
        QName staticQNameOfCaseFolder = TradeMarkModel.CASE_FOLDER_QNAME;
        NodeRef multimediaFolderNodeRef = currentFolderNodeRef;

        if (staticQNameOfCaseFolder.isMatch(qnameOfCaseFolder)) {
            log.trace("Check if we need to create: " + TMConstants.MULTIMEDIA_FOLDER_NAME + " folder");
            multimediaFolderNodeRef = cmsNodeLocator.locateNodeRef(currentFolderNodeRef, TMConstants.MULTIMEDIA_FOLDER_NAME);
            if (multimediaFolderNodeRef == null) {
                log.debug("Create: " + TMConstants.MULTIMEDIA_FOLDER_NAME + " folder for loading original multimedia contents");
                multimediaFolderNodeRef = cmsNodeCreator.createFolderNode(currentFolderNodeRef,
                        TMConstants.MULTIMEDIA_FOLDER_NAME);
            }
            NodeRef returningNodeRefLcl = cmsNodeLocator.locateNodeRef(currentFolderNodeRef, TMConstants.MULTIMEDIA_FOLDER_NAME,
                    incomingFolderName);
            if (returningNodeRefLcl == null) {
                Map<QName, Serializable> targetRepoMap = serviceRegistry.getNodeService().getProperties(currentFolderNodeRef);
                targetRepoMap.remove(ContentModel.PROP_NODE_UUID);
                targetRepoMap.remove(ContentModel.PROP_NODE_DBID);
                targetRepoMap.remove(ContentModel.PROP_CONTENT);
                QName lclQname = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, incomingFolderName);
                QName type = serviceRegistry.getNodeService().getType(currentFolderNodeRef);
                ChildAssociationRef newNodeAssoc = serviceRegistry.getNodeService().createNode(multimediaFolderNodeRef,
                        ContentModel.ASSOC_CONTAINS, lclQname, type, targetRepoMap);
                multimediaFolderNodeRef = newNodeAssoc.getChildRef();
            } else {
                multimediaFolderNodeRef = returningNodeRefLcl;
            }
        }
        return multimediaFolderNodeRef;
    }

    public boolean isMediaTransformable(String mediaMimeType) {
        boolean isMediaTransformable = false;
        ContentTransformer transformer = null;
        transformer = figureTransformerStatus(mediaMimeType);        
        if(null != transformer){
            log.trace("We have a valid audio/video transformer for media mimeType = " + mediaMimeType);
            isMediaTransformable = true;
        }else{
            log.trace("audio/video transformer NOT found for media mimeType = " + mediaMimeType);
            isMediaTransformable = false;
        }
        return isMediaTransformable;
    }

    private ContentTransformer figureTransformerStatus(String mediaMimeType) {
        ContentTransformer transformer = null;
        if (StringUtils.startsWith(mediaMimeType, TMConstants.VIDEO)) {
            transformer = serviceRegistry.getContentService().getTransformer(mediaMimeType, MimetypeMap.MIMETYPE_VIDEO_MP4);
        } else if (StringUtils.startsWith(mediaMimeType, TMConstants.AUDIO)) {
            transformer = serviceRegistry.getContentService().getTransformer(mediaMimeType,  MimetypeMap.MIMETYPE_AUDIO_MP4);
            if(null == transformer){
                transformer = serviceRegistry.getContentService().getTransformer(mediaMimeType,  TMConstants.AUDIO_MPEG);
            }
        }
        
        boolean isMp4TransformerNameValid = false;
        boolean isMp3TransformerNameValid = false;       
        if(null != transformer){
            String transformerName = transformer.getName();
            isMp4TransformerNameValid = StringUtils.equalsIgnoreCase("transformer.ffmpeg.mp4", transformerName);
            isMp3TransformerNameValid = StringUtils.equalsIgnoreCase("transformer.ffmpeg.mp3", transformerName);            
            if(isMp3TransformerNameValid || isMp4TransformerNameValid){
                return transformer;
            }else{
                transformer = null;
            }
        }
        return transformer;
    }

    /**
     * @param mediaMimeType The type of media to be converted @Title:
     * getAppropriateTransformer @Description: @return @return
     * ContentTransformer @throws
     */
    public ContentTransformer getAppropriateTransformerForCreateEvent(String mediaMimeType) {
        ContentTransformer transformer = null;
        transformer = figureTransformerStatus(mediaMimeType);
        if (null == transformer) {
            throw new TmngCmsException.TmngContentTransformerNotFoundException(
                    "Transformer for converting media type " + mediaMimeType + " NOT found.", null);
        }
        return transformer;
    }

    /**
     * 
     * @param childAssocRef
     *            where should the file be copied?
     * @param oldMediaFileName
     *            Old multimedia file name
     * @param oldMediaNewGuessMimeType
     *            old multimedia file mime type
     * @param newMediaFileName
     *            New multimedia file name
     * @param newMimeType
     *            New multimedia file type
     * @param updateCurrentEntry
     *            Should the existing content be updated? (new file is always
     *            created)
     */
    public void processMediaConversion(final ChildAssociationRef childAssocRef, final String oldMediaFileName,
            final String oldMediaNewGuessMimeType, final String newMediaFileName, final String newMimeType,
            boolean updateCurrentEntry) {

        log.trace("Transform Policy Processing: Convert the media file " + oldMediaFileName + " of type: "
                + oldMediaNewGuessMimeType + " to " + newMediaFileName + " of type: " + newMimeType + "; update? "
                + updateCurrentEntry);

        final NodeRef incomingMediaTypeNodeRef = childAssocRef.getChildRef();

        ChildAssociationRef childAssocRefMultimedia = serviceRegistry.getNodeService().getPrimaryParent(incomingMediaTypeNodeRef);
        NodeRef multimediaFolderNodeRef = childAssocRefMultimedia.getParentRef();
        ChildAssociationRef childAssocRefSerialNumber = serviceRegistry.getNodeService().getPrimaryParent(multimediaFolderNodeRef);
        final NodeRef serialNumberFolderNodeRef = childAssocRefSerialNumber.getParentRef();

        if (!isMediaTransformable(oldMediaNewGuessMimeType)) {
            if (updateCurrentEntry){
                copyMultimediaAsIsWithNodeUpdate(serialNumberFolderNodeRef, incomingMediaTypeNodeRef, oldMediaFileName, oldMediaNewGuessMimeType);
            }else{
                copyMultimediaAsIsWithNodeCreate(serialNumberFolderNodeRef, incomingMediaTypeNodeRef, oldMediaFileName, oldMediaNewGuessMimeType);    
            }
            return;
        }
        if (updateCurrentEntry) {
            log.debug("Update multimedia file: " + newMediaFileName);
            final NodeRef targetFileNodeRef = cmsNodeLocator.locateNodeRef(serialNumberFolderNodeRef, oldMediaFileName);
            RetryingTransactionHelper txnHelperOne = transactionService.getRetryingTransactionHelper();
            RetryingTransactionHelper.RetryingTransactionCallback<Void> convertMultimediaFile = new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {
                @Override
                public Void execute() throws Throwable {
                    AuthenticationUtil.runAs(new RunAsWork<Void>() {
                        @Override
                        public Void doWork() throws Exception {
                            // Transforming a Video to MPEG4
                            try {
                                behaviourFilter.disableBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME);
                                convertMultimediaFileForUpdate(serialNumberFolderNodeRef, incomingMediaTypeNodeRef, targetFileNodeRef,oldMediaFileName, oldMediaNewGuessMimeType);
                            } catch (Exception e) {
                                log.debug("MultimediaService.updatingMultimediaFile(.....) failed: ", e);
                                behaviourFilter.disableBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME);
                                NodeRef targetNodeRefInsideSerialCaseFolder = cmsNodeLocator.locateNodeRef(serialNumberFolderNodeRef, oldMediaFileName);
                                serviceRegistry.getCopyService().copy(incomingMediaTypeNodeRef, targetNodeRefInsideSerialCaseFolder);                                 
                            }                        
                            return null;
                        }
                    }, AuthenticationUtil.SYSTEM_USER_NAME);                    
                    return null;
                }
            };
            txnHelperOne.doInTransaction(convertMultimediaFile, false, true);            
            return;
        } else {
            /*
             * Convert the file from the multimedia folder..
             */
        	RetryingTransactionHelper txnHelperTwo = transactionService.getRetryingTransactionHelper();
            RetryingTransactionHelper.RetryingTransactionCallback<Void> convertMultimediaFile = new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {
                @Override
                public Void execute() throws Throwable {
                    AuthenticationUtil.runAs(new RunAsWork<Void>() {
                        @Override
                        public Void doWork() throws Exception {
                            // Transforming a Video to MPEG4
                            try {
                                behaviourFilter.disableBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME);
                                convertMultimediaFile(serialNumberFolderNodeRef, incomingMediaTypeNodeRef, oldMediaFileName, newMediaFileName ,oldMediaNewGuessMimeType, newMimeType);
                            } catch (Exception e) {
                                log.debug("MultimediaService.convertMultimediaFile(.....) failed: ", e);
                            }                            
                            return null;
                        }
                    }, AuthenticationUtil.SYSTEM_USER_NAME);
                    return null;
                }
            };
            txnHelperTwo.doInTransaction(convertMultimediaFile, false, true);
        }
    }

	private void copyMultimediaAsIsWithNodeUpdate(final NodeRef serialNumberFolderNodeRef, final NodeRef incomingMediaTypeNodeRef, final String oldMediaFileName, final String oldMediaNewGuessMimeType) {
        log.warn("This is the not a transformable mime type: " + oldMediaNewGuessMimeType + "; file = " + oldMediaFileName + ", Copying as-is");
        RetryingTransactionHelper txnHelperSix = transactionService.getRetryingTransactionHelper();
        RetryingTransactionHelper.RetryingTransactionCallback<Void> copyOriginalMultimediaFile = new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {
            @Override
            public Void execute() throws Throwable {
                AuthenticationUtil.runAs(new RunAsWork<Void>() {
                    @Override
                    public Void doWork() throws Exception {
                        try {
                            behaviourFilter.disableBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME);
                            NodeRef targetNodeRefInsideSerialCaseFolder = cmsNodeLocator.locateNodeRef(serialNumberFolderNodeRef, oldMediaFileName);
                            serviceRegistry.getCopyService().copy(incomingMediaTypeNodeRef, targetNodeRefInsideSerialCaseFolder);
                        } catch (Exception e) {
                            log.debug("MultimediaService.copyMultimediaAsIs(....) for update(POST) request failed: ", e);
                        }
                        return null;
                    }
                }, AuthenticationUtil.SYSTEM_USER_NAME);
                return null;
            }
        };
      txnHelperSix.doInTransaction(copyOriginalMultimediaFile, false, true);
    }

    private void copyMultimediaAsIsWithNodeCreate(final NodeRef serialNumberFolderNodeRef, final NodeRef incomingMediaTypeNodeRef, final String oldMediaFileName, final String oldMediaNewGuessMimeType) {
        log.warn("This is the not a transformable mime type: " + oldMediaNewGuessMimeType + "; file = " + oldMediaFileName + ", Copying as-is");
        RetryingTransactionHelper txnHelperFive = transactionService.getRetryingTransactionHelper();
        RetryingTransactionHelper.RetryingTransactionCallback<Void> copyOriginalMultimediaFile = new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {
            @Override
            public Void execute() throws Throwable {
				AuthenticationUtil.runAs(new RunAsWork<Void>() {
					@Override
					public Void doWork() throws Exception {
		                try {
		                    behaviourFilter.disableBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME);
		                    Map<QName, Serializable> targetRepoMap = serviceRegistry.getNodeService().getProperties(incomingMediaTypeNodeRef);
		                    targetRepoMap.remove(ContentModel.PROP_NODE_UUID);
		                    targetRepoMap.remove(ContentModel.PROP_NODE_DBID);
		                    targetRepoMap.remove(ContentModel.PROP_CONTENT);
		                    targetRepoMap.remove(ContentModel.PROP_VERSION_LABEL);
		                    targetRepoMap.remove(ContentModel.PROP_NAME);        
		                    targetRepoMap.put(ContentModel.PROP_NAME, oldMediaFileName);
		                    QName lclQname = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, oldMediaFileName);
		                    QName type = serviceRegistry.getNodeService().getType(incomingMediaTypeNodeRef);
		                    behaviourFilter.disableBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME);
		                    ChildAssociationRef newNodeAssoc = serviceRegistry.getNodeService().createNode(serialNumberFolderNodeRef, ContentModel.ASSOC_CONTAINS, lclQname, type, targetRepoMap);
		                    NodeRef targetNodeRefInsideSerialCaseFolder = newNodeAssoc.getChildRef();
		                    serviceRegistry.getCopyService().copy(incomingMediaTypeNodeRef, targetNodeRefInsideSerialCaseFolder);
		                } catch (Exception e) {
		                    log.debug("MultimediaService.copyMultimediaAsIs(....) for create(PUT) request failed: ", e);
		                }
						return null;
					}
				}, AuthenticationUtil.SYSTEM_USER_NAME);
                return null;
            }
        };
      txnHelperFive.doInTransaction(copyOriginalMultimediaFile, false, true);
	}

    public boolean sameMulFileNameDoesExistsInCaseFolder(NodeRef caseFolderRef, QName type, String fileNameInCaseFolder) {
        boolean sameMulFileNameDoesExistsInCaseFolder = false;
        NodeRef targetNodeRefInsideSerialCaseFolder = cmsNodeLocator.locateNodeRef(caseFolderRef, fileNameInCaseFolder);
        if(null != targetNodeRefInsideSerialCaseFolder){
            sameMulFileNameDoesExistsInCaseFolder = true;
            String caseFolderName = (String) serviceRegistry.getNodeService().getProperty(caseFolderRef, ContentModel.PROP_NAME);
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.CONFLICT, "Mark '" + fileNameInCaseFolder + "' already exists inside " + caseFolderName);            
        }else{
            sameMulFileNameDoesExistsInCaseFolder = false;
        }
        return sameMulFileNameDoesExistsInCaseFolder;
    }

    public boolean isUserTryingMarkMutationIntoVideoType(NodeRef nodeRefOfFileInsideCaseFolder, String newIncomingFileMimeType) {
        boolean isUserTryingMarkMutationIntoVideoType = false;
        String fileName = (String) serviceRegistry.getNodeService().getProperty(nodeRefOfFileInsideCaseFolder, ContentModel.PROP_NAME);
        ContentData cd = (ContentData) serviceRegistry.getNodeService().getProperty(nodeRefOfFileInsideCaseFolder, ContentModel.PROP_CONTENT);
        String oldMimeType = cd.getMimetype();        
        boolean incomingMimeTypeIsVideoType = StringUtils.startsWith(newIncomingFileMimeType, TMConstants.VIDEO);
        boolean incomingMimeTypeIsAudioType = StringUtils.startsWith(newIncomingFileMimeType, TMConstants.AUDIO);
        boolean oldMarkMimeTypeIsAnythingOthertThanAudioVideoType = !doesItMatchAudioOrVideoStartingString(oldMimeType);
        boolean newMarkMimeTypeIsAnythingOthertThanAudioVideoType = !doesItMatchAudioOrVideoStartingString(newIncomingFileMimeType);
        boolean oldMarkMimeTypeIsOfAudioVideoType = !(oldMarkMimeTypeIsAnythingOthertThanAudioVideoType);
        log.debug("isUserTryingMarkMutation() :: " + isUserTryingMarkMutationIntoVideoType + ", oldMimeType --> " + oldMimeType + " :: "  + "newIncomingFileMimeType --> " + newIncomingFileMimeType);
        if(oldMarkMimeTypeIsAnythingOthertThanAudioVideoType && (incomingMimeTypeIsVideoType || incomingMimeTypeIsAudioType) ){
            isUserTryingMarkMutationIntoVideoType = true;
            //DE27653
            throw new TmngCmsException.CMSRuntimeException(HttpStatus.BAD_REQUEST, fileName + " Trademark mutation from " + oldMimeType + " to " + newIncomingFileMimeType + " is NOT allowed.");
        } else if(oldMarkMimeTypeIsOfAudioVideoType && newMarkMimeTypeIsAnythingOthertThanAudioVideoType ){
            isUserTryingMarkMutationIntoVideoType = true;
            // DE28199
            throw new TmngCmsException.CMSRuntimeException(HttpStatus.BAD_REQUEST, fileName + " Trademark mutation from " + oldMimeType + " to " + newIncomingFileMimeType + " is NOT allowed.");
        }else{
            isUserTryingMarkMutationIntoVideoType = false;
        }
        return isUserTryingMarkMutationIntoVideoType;
    }
    
    private boolean doesItMatchAudioOrVideoStartingString(String incomingMimeType) {
        boolean doesItMatchAudioOrVideoStartingString = false;
        String audio_video_start_matching_regex = "(AUDIO|VIDEO).*";
        Pattern pattern_audio_video_start_matching_regex = Pattern.compile(audio_video_start_matching_regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher_audio_video_starting_string= pattern_audio_video_start_matching_regex.matcher(incomingMimeType);
        doesItMatchAudioOrVideoStartingString = matcher_audio_video_starting_string.matches();
        return doesItMatchAudioOrVideoStartingString;
    }    
}