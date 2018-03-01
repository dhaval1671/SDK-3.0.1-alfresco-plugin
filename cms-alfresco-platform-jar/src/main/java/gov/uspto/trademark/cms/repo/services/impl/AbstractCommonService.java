package gov.uspto.trademark.cms.repo.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.model.RenditionModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.version.Version;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.AccessLevel;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.helpers.DataDictionaryHelper;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.nodedelete.DocumentSoftDelete;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsCommonService;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.DocumentCreateListener;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype.MultimediaService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.EvidencePostResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.PostResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This class implements the functionality related to documents. Each document
 * type specific service will extend this class and implements the methods.
 *
 */
public abstract class AbstractCommonService implements CmsCommonService {
	private static final Logger log = LoggerFactory.getLogger(AbstractCommonService.class);

	/** The Constant PUBLIC. */
	private static final String PUBLIC = "public";

	/** The service registry. */
	@Autowired
	@Qualifier(value = "ServiceRegistry")
	protected ServiceRegistry serviceRegistry;

	@Autowired
	@Qualifier("TransactionService")
	protected TransactionService transactionService;

	/** The base document delete. */
	@Autowired
	@Qualifier(value = "DocumentSoftDelete")
	protected DocumentSoftDelete baseDocumentDelete;

	/** The cms node locator. */
	protected CmsNodeLocator cmsNodeLocator;

	/** The cms node creator. */
	protected CmsNodeCreator cmsNodeCreator;

	@Autowired
	@Qualifier(value = "multimediaService")
	protected MultimediaService multimediaService;

	/**
	 * Instantiates a new abstract common service.
	 *
	 * @param cmsNodeCreator
	 *            the case node creator
	 * @param cmsNodeLocator
	 *            the cms node locator
	 */
	public AbstractCommonService(CmsNodeCreator cmsNodeCreator, CmsNodeLocator cmsNodeLocator) {
		this.cmsNodeCreator = cmsNodeCreator;
		this.cmsNodeLocator = cmsNodeLocator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveContent
	 * (gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator,
	 * org.alfresco.service.namespace.QName, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ContentReader retrieveContent(CmsNodeLocator cmsNodeLocator, QName docQName, String serialNumber,
			String fileName, String versionNumber) {
		NodeRef nodeRef = cmsNodeLocator.locateNode(serialNumber);
		ContentReader contentReader = null;
		if (nodeRef != null) {
			NodeRef resultNodeRef = cmsNodeLocator.locateNodeRef(nodeRef, versionNumber, docQName, fileName);
			if (resultNodeRef != null) {
				contentReader = serviceRegistry.getContentService().getReader(resultNodeRef, ContentModel.PROP_CONTENT);
			}
		}
		return contentReader;
	}

	@Override
	public ContentReader retrieveContent(CmsNodeLocator cmsNodeLocator, QName docQName, String serialNumber,
			String fileName, String versionNumber, CmsDataFilter dataFilter) {
		NodeRef nodeRef = cmsNodeLocator.locateNode(serialNumber);
		ContentReader contentReader = null;
		if (nodeRef != null) {
			NodeRef resultNodeRef = cmsNodeLocator.locateNodeRef(nodeRef, versionNumber, docQName, fileName);
			if (resultNodeRef != null) {
				String accessLevel = (String) serviceRegistry.getNodeService().getProperty(resultNodeRef,
						TradeMarkModel.ACCESS_LEVEL_QNAME);
				if (dataFilter != null && dataFilter.filter(accessLevel)) {
					throw new TmngCmsException.AccessLevelRuleViolationException(HttpStatus.FORBIDDEN,
							"Server intercepted request for secured document.");
				}
				contentReader = serviceRegistry.getContentService().getReader(resultNodeRef, ContentModel.PROP_CONTENT);
			}
		}
		return contentReader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata
	 * (gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator,
	 * java.lang.String, java.lang.String, java.lang.String,
	 * org.alfresco.service.namespace.QName, java.lang.Class)
	 */
	@Override
	public byte[] retrieveMetadata(CmsNodeLocator cmsNodeLocator, String serialNumber, String fileName,
			String versionNumber, QName docQName, Class<? extends AbstractBaseType> docTypeClass) {
		NodeRef nodeRef = cmsNodeLocator.locateNode(serialNumber);
		byte[] result = null;
		if (nodeRef != null) {
			NodeRef resultNodeRef = cmsNodeLocator.locateNodeRef(nodeRef, versionNumber, docQName, fileName);
			if (resultNodeRef != null) {
				Map<QName, Serializable> repositoryProperties = this.serviceRegistry.getNodeService()
						.getProperties(resultNodeRef);
				Map<String, Serializable> convertedMap = WebScriptHelper.stringifyMap(repositoryProperties);
				if (convertedMap.get(TMConstants.SERIAL_NUMBER) != null) {
					convertedMap.put(TMConstants.SERIAL_NUMBER, serialNumber);
				}

				result = JacksonHelper.generateClientJsonFrDTO(
						JacksonHelper.generateDTOFrAlfrescoRepoProps(convertedMap, docTypeClass));
			}

		}
		return result;
	}

	@Override
	public byte[] retrieveMetadata(CmsNodeLocator cmsNodeLocator, String serialNumber, String fileName,
			String versionNumber, QName docQName, Class<? extends AbstractBaseType> docTypeClass,
			CmsDataFilter dataFilter) {
		NodeRef nodeRef = cmsNodeLocator.locateNode(serialNumber);
		byte[] result = null;
		if (nodeRef != null) {
			NodeRef resultNodeRef = cmsNodeLocator.locateNodeRef(nodeRef, versionNumber, docQName, fileName);
			if (resultNodeRef != null) {
				String accessLevel = (String) serviceRegistry.getNodeService().getProperty(resultNodeRef,
						TradeMarkModel.ACCESS_LEVEL_QNAME);
				if (dataFilter != null && dataFilter.filter(accessLevel)) {
					throw new TmngCmsException.AccessLevelRuleViolationException(HttpStatus.FORBIDDEN,
							"Server intercepted request for secured document.");
				}
				Map<QName, Serializable> repositoryProperties = this.serviceRegistry.getNodeService()
						.getProperties(resultNodeRef);
				Map<String, Serializable> convertedMap = WebScriptHelper.stringifyMap(repositoryProperties);
				if (convertedMap.get(TMConstants.SERIAL_NUMBER) != null) {
					convertedMap.put(TMConstants.SERIAL_NUMBER, serialNumber);
				}

				result = JacksonHelper.generateClientJsonFrDTO(
						JacksonHelper.generateDTOFrAlfrescoRepoProps(convertedMap, docTypeClass));
			}

		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata
	 * (gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator,
	 * java.lang.String, java.lang.String, java.lang.String,
	 * org.alfresco.service.namespace.QName, java.lang.Class)
	 */
	@Override
	public List<? extends TmngAlfResponse> retrieveVersionsList(String id, String fileName, QName docQName,
			CmsDataFilter dataFilter) {
		NodeRef nodeRef = cmsNodeLocator.locateNode(id, fileName, docQName);
		if (nodeRef == null) {
			throw new TmngCmsException.DocumentDoesNotExistException("File Does not exists");
		}
		Collection<Version> versionsList = serviceRegistry.getVersionService().getVersionHistory(nodeRef)
				.getAllVersions();

		List<EvidencePostResponse> responseList = new ArrayList<EvidencePostResponse>();
		EvidencePostResponse postResponse;
		for (Version version : versionsList) {
			String accessLevel = (String) serviceRegistry.getNodeService().getProperty(version.getFrozenStateNodeRef(),
					TradeMarkModel.ACCESS_LEVEL_QNAME);
			if (dataFilter != null && dataFilter.filter(accessLevel)) {
				continue;
			} else {
				postResponse = new EvidencePostResponse();
				postResponse.setDocumentId(id, docQName.getLocalName(), fileName);
				postResponse.setSerialNumber(id);
				postResponse.setVersion(version.getVersionLabel());
				postResponse.setAccessLevel(accessLevel);
				responseList.add(postResponse);
			}
		}
		return responseList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.uspto.trademark.cms.repo.services.CmsCommonService#createDocument
	 * (java.lang.String, java.lang.String, byte[],
	 * org.alfresco.service.namespace.QName, java.util.Map, java.lang.Class)
	 */
	@Override
	public <T extends AbstractBaseType> TmngAlfResponse createDocument(final String id, final String fileName,
			final ContentItem content, final QName type, final Map<String, Serializable> properties,
			Class<T> docTypeClass) {
		return this.createDocument(id, fileName, content, type, properties, docTypeClass, new DocumentCreateListener() {
			@Override
			public void listen() {
				// No implementation required at this time
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.uspto.trademark.cms.repo.services.CmsCommonService#createDocument
	 * (java.lang.String, java.lang.String, byte[],
	 * org.alfresco.service.namespace.QName, java.util.Map, java.lang.Class,
	 * gov.uspto.trademark.cms.repo.services.DocumentCreateListener)
	 */
	@Override
	public <T extends AbstractBaseType> TmngAlfResponse createDocument(final String id, final String fileName,
			final ContentItem content, final QName type, final Map<String, Serializable> properties,
			Class<T> docTypeClass, final DocumentCreateListener docCreateListener) {
		return this.createDocument(id, fileName, content, type, properties, docTypeClass, docCreateListener,
				cmsNodeCreator);
	}

	/*
	 * This method is used to create the document for the given content
	 * 
	 * @see
	 * gov.uspto.trademark.cms.repo.services.CmsCommonService#createDocument
	 * (java.lang.String, java.lang.String, byte[],
	 * org.alfresco.service.namespace.QName, java.util.Map, java.lang.Class,
	 * gov.uspto.trademark.cms.repo.services.DocumentCreateListener)
	 */
	@Override
	public synchronized <T extends AbstractBaseType> TmngAlfResponse createDocument(final String id,
			final String fileName, final ContentItem content, final QName type,
			final Map<String, Serializable> properties, Class<T> docTypeClass,
			final DocumentCreateListener docCreateListener, final CmsNodeCreator cmsNodeCreator) {

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
				NodeRef authBlockNodeRef = AuthenticationUtil.runAs(new RunAsWork<NodeRef>() {
					@Override
					public NodeRef doWork() throws Exception {
						NodeRef parentFolder;
						// Create case folder if it doesn't exist
						NodeRef caseFolder = cmsNodeCreator.createNode(id);
						// This is used to guess mimetype and also to update the
						// content
						// node
						ContentWriter tempWriter = serviceRegistry.getContentService().getTempWriter();
						tempWriter.putContent(content.getInputStream());
						// Guess mime type and if multimedia(is transformable)
						// create
						// the file under multimedia folder
						final String mimeType = serviceRegistry.getMimetypeService().guessMimetype(fileName,
								tempWriter.getReader());
						if (multimediaService.isMultimediaContentChangeInsideCaseOrMulFolder(mimeType, caseFolder, type,
								false)
								&& !(multimediaService.sameMulFileNameDoesExistsInCaseFolder(caseFolder, type,
										fileName))) {
							parentFolder = cmsNodeCreator.createFolderNode(caseFolder,
									TMConstants.MULTIMEDIA_FOLDER_NAME);
							log.debug("Processing multimedia file: " + fileName + " in the multimedia folder");
						} else {
							parentFolder = caseFolder;
						}
						repoMap.put(ContentModel.PROP_NAME, fileName);
						repoMap.put(TradeMarkModel.PROP_DOCUMENT_NAME, fileName);
						QName lclQname = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, fileName);
						ChildAssociationRef newNodeAssoc = serviceRegistry.getNodeService().createNode(parentFolder,
								ContentModel.ASSOC_CONTAINS, lclQname, type, repoMap);
						NodeRef resultNode = newNodeAssoc.getChildRef();
						ContentWriter writer = serviceRegistry.getContentService().getWriter(resultNode,
								ContentModel.PROP_CONTENT, true);
						writer.putContent(tempWriter.getReader());
						ContentData cd = (ContentData) serviceRegistry.getNodeService().getProperty(resultNode,
								ContentModel.PROP_CONTENT);
						ContentData newCD = ContentData.setMimetype(cd, mimeType);
						serviceRegistry.getNodeService().setProperty(resultNode, ContentModel.PROP_CONTENT, newCD);
						docCreateListener.listen();
						return resultNode;
					}
				}, AuthenticationUtil.SYSTEM_USER_NAME);
				return authBlockNodeRef;
			}
		};
		final NodeRef newlyCreatedNodeRef = txnHelper.doInTransaction(callback, false, true);

		RetryingTransactionCallback<String> callbackVersion = new RetryingTransactionCallback<String>() {
			@Override
			public String execute() throws Throwable {
				String newVersionNumber = AuthenticationUtil.runAs(new RunAsWork<String>() {
					@Override
					public String doWork() throws Exception {
						if (!serviceRegistry.getNodeService().exists(newlyCreatedNodeRef)) {
							throw new ObjectNotFoundException(newlyCreatedNodeRef,
									"Node not created as yet: " + newlyCreatedNodeRef.toString());
						}
						return (String) serviceRegistry.getNodeService().getProperty(newlyCreatedNodeRef,
								ContentModel.PROP_VERSION_LABEL);
					}
				}, AuthenticationUtil.SYSTEM_USER_NAME);
				return newVersionNumber;
			}
		};

		String versionNumber = txnHelper.doInTransaction(callbackVersion, true, true);
		return this.buildResponse(id, type.getLocalName(), fileName, versionNumber);
	}

	/**
	 * Creates a document if it doesn't exist. Replaces if the file exists A new
	 * version will be created if replaced
	 * 
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
	 * @param <T>
	 * @return
	 */
	@Override
	public <T extends AbstractBaseType> TmngAlfResponse createOrUpdateDocument(final String id, final String fileName,
			final ContentItem content, final QName type, final Map<String, Serializable> properties,
			Class<T> docTypeClass) {
		NodeRef nodeRef = null;
		try {
			nodeRef = this.cmsNodeLocator.locateNode(id, fileName, type);
		} catch (TmngCmsException.DocumentDoesNotExistException | TmngCmsException.SerialNumberNotFoundException e) {
			log.trace("No action required. The document is being created for the first time", e);
			// No action required. This means the document is being created for
			// the first time
		} 
		TmngAlfResponse response;
		if (nodeRef == null) {
			response = createDocument(id, fileName, content, type, properties, docTypeClass);
		} else {
			response = this.updateDocument(id, fileName, content, type, properties, docTypeClass);
		}
		return response;
	}

	/*
	 * This method is used to update the document with given content and
	 * metadata
	 * 
	 * @see
	 * gov.uspto.trademark.cms.repo.services.CmsCommonService#updateDocument
	 * (java.lang.String, java.lang.String, byte[],
	 * org.alfresco.service.namespace.QName, java.util.Map, java.lang.Class)
	 */
	@Override
	public <T extends AbstractBaseType> TmngAlfResponse updateDocument(final String id, final String fileName,
			final ContentItem content, final QName type, final Map<String, Serializable> properties,
			Class<T> docTypeClass) {
		PostResponse postResponse = null;
		T docType = JacksonHelper.generateDTOFrIncomingClientJson(properties, docTypeClass);
		final Map<QName, Serializable> repoMap = WebScriptHelper.generateAlfRepoPropsFrDTO(docType);
		repoMap.put(TradeMarkModel.SERIAL_NUMBER_QNAME, id);
		// check if incoming client json has accessLevel DE7380
		String accessLevel = (String) properties.get(AccessLevel.ACCESS_LEVEL_KEY);
		if (accessLevel == null) {
			repoMap.remove(TradeMarkModel.ACCESS_LEVEL_QNAME);
		}
		// remove versionLabel
		String versionAttrib = (String) properties.get(TradeMarkModel.VERSION);
		if (null != versionAttrib) {
			repoMap.remove(ContentModel.PROP_VERSION_LABEL);
		}

		RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
		RetryingTransactionCallback<NodeRef> callback = new RetryingTransactionCallback<NodeRef>() {
			@Override
			public NodeRef execute() throws Throwable {
				String mimeType = deriveContentMimeType(fileName, content);
				NodeRef documentNodeRef = locateSpecifiedDocument(id, fileName, content, type, mimeType);
				if (documentNodeRef != null) {
					if (serviceRegistry.getNodeService().hasAspect(documentNodeRef, TradeMarkModel.ASPECT_REDACTED)
							&& StringUtils.isEmpty((String) properties.get(TradeMarkModel.ORIGINAL_DOCUMENT_VERSION))) {
						throw new TmngCmsException.CMSRuntimeException("Update is not allowed on Redacted Document");
					}
					// if the doc is of Mark type then delete all its rendition.
					// [US70026, Sep-06-2017]
					// otherwise stale rendition will remain and new ones will
					// not be generated.
					boolean isMark = TradeMarkModel.MARK_QNAME.isMatch(type);
					if (isMark) {
						List<ChildAssociationRef> childRefListOfQNameRendition = serviceRegistry.getNodeService()
								.getChildAssocs(documentNodeRef);
						for (ChildAssociationRef car : childRefListOfQNameRendition) {
							if (RenditionModel.ASSOC_RENDITION.equals(car.getTypeQName())) {
								NodeRef nr = car.getChildRef();
								serviceRegistry.getNodeService().addAspect(nr, ContentModel.ASPECT_TEMPORARY, null);
								serviceRegistry.getNodeService().deleteNode(nr);
							}
						}
					}
					repoMap.put(DataDictionaryHelper.getTradeMarkPropertyQName(TradeMarkModel.DOCUMENT_NAME), fileName);
					serviceRegistry.getNodeService().addProperties(documentNodeRef, repoMap);
					writeContent(content, fileName, documentNodeRef);
				}
				return documentNodeRef;
			}
		};

		final NodeRef nodeRef = txnHelper.doInTransaction(callback, false, true);

		if (nodeRef != null) {
			// Construct and Send Response
			postResponse = new PostResponse();
			postResponse.setDocumentId(id, type.getLocalName(), fileName);
			RetryingTransactionCallback<String> callbackVersion = new RetryingTransactionCallback<String>() {
				@Override
				public String execute() throws Throwable {
					if (!serviceRegistry.getNodeService().exists(nodeRef)) {
						throw new ObjectNotFoundException(nodeRef, "Node not created as yet: " + nodeRef.toString());
					}
					return (String) serviceRegistry.getNodeService().getProperty(nodeRef,
							ContentModel.PROP_VERSION_LABEL);
				}
			};
			postResponse.setVersion(txnHelper.doInTransaction(callbackVersion, true, true));
			postResponse.setSerialNumber(id);
		}
		return postResponse;
	}

	/**
	 * Use the filename and the content to determine (guess) the mime type.
	 * 
	 * @param fileName
	 *            name of the file
	 * @param content
	 *            the content
	 * @return the derived mime type
	 */
	private String deriveContentMimeType(final String fileName, final ContentItem content) {
		String mimeType = null;
		if ((content != null) && (content.getInputStream() != null)) {
			ContentWriter tempWriter = serviceRegistry.getContentService().getTempWriter();
			tempWriter.putContent(content.getInputStream());
			ContentReader crLcl = tempWriter.getReader();
			String mimeTypeOfIncomingContentFrTempContentReader = crLcl.getMimetype();
			if(StringUtils.isBlank(mimeTypeOfIncomingContentFrTempContentReader)){
			    mimeType = serviceRegistry.getMimetypeService().getMimetypeIfNotMatches(tempWriter.getReader());
			}else{
			    mimeType = serviceRegistry.getMimetypeService().guessMimetype(fileName, tempWriter.getReader());
			}
		}
		return mimeType;
	}

	/**
	 * find the node reference object for the given document id, filename and
	 * content based on the mime type (possible multimedia file).
	 * 
	 * @param id
	 *            node id
	 * @param fileName
	 *            name of the file
	 * @param content
	 *            the content
	 * @param type
	 *            type of file
	 * @param mimeType
	 *            mime type of the file
	 * @return the node reference of the file (if found)
	 */
	private NodeRef locateSpecifiedDocument(final String id, final String fileName, final ContentItem content,
			final QName type, String mimeType) {
		NodeRef caseSerialNumberFolderNodeRef = cmsNodeLocator.locateNode(id);
		NodeRef fileNodeRef = cmsNodeLocator.locateNode(id, fileName, type);
		NodeRef documentNodeRef = fileNodeRef;
		if ((content != null) && (content.getInputStream() != null)) {
			NodeRef nodeRefOfFileInsideCaseFolder = fileNodeRef;
			String newIncomingFileMimeType = mimeType;
			if (!(multimediaService.isUserTryingMarkMutationIntoVideoType(nodeRefOfFileInsideCaseFolder,
					newIncomingFileMimeType))
					&& multimediaService.isMultimediaContentChangeInsideCaseOrMulFolder(mimeType,
							caseSerialNumberFolderNodeRef, type, false)) {
				documentNodeRef = cmsNodeLocator.locateNodeRef(caseSerialNumberFolderNodeRef,
						TMConstants.MULTIMEDIA_FOLDER_NAME, fileName);
				log.debug("Updating multimedia file: " + fileName + " in the multimedia folder.");
			}
		} else {
			documentNodeRef = fileNodeRef;
		}
		return documentNodeRef;
	}

	/**
	 * Write the content from the incoming stream and also set the mime type of
	 * the content (guessing the content or the filename).
	 * 
	 * @param content
	 *            the content
	 * @param fileName
	 *            name of the file
	 * @param documentNodeRef
	 *            the node reference to the file
	 */
	private void writeContent(final ContentItem content, final String fileName, NodeRef documentNodeRef) {
		if ((content != null) && (content.getInputStream() != null)) {
			ContentWriter tempWriter = serviceRegistry.getContentService().getTempWriter();
			tempWriter.putContent(content.getInputStream());
			String mimeType = serviceRegistry.getMimetypeService().guessMimetype(fileName, tempWriter.getReader());
			ContentWriter writer = serviceRegistry.getContentService().getWriter(documentNodeRef,
					ContentModel.PROP_CONTENT, true);
			writer.putContent(tempWriter.getReader());
			ContentData cd = (ContentData) serviceRegistry.getNodeService().getProperty(documentNodeRef,
					ContentModel.PROP_CONTENT);
			ContentData newCD = ContentData.setMimetype(cd, mimeType);
			serviceRegistry.getNodeService().setProperty(documentNodeRef, ContentModel.PROP_CONTENT, newCD);
		}
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.uspto.trademark.cms.repo.services.CmsCommonService#delete(java.lang
	 * .String, java.lang.String, org.alfresco.service.namespace.QName)
	 */
	@Override
	public TmngAlfResponse delete(final String id, final String fileName) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.uspto.trademark.cms.repo.services.CmsCommonService#ticrsAdminDelete
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public TmngAlfResponse ticrsAdminDelete(String id, String fileName, String strDocType) {
		PostResponse postResponse = null;
		NodeRef result = baseDocumentDelete.ticrsSoftDelete(id, fileName, strDocType);
		if (null != result) {
			QName qn = serviceRegistry.getFileFolderService().getFileInfo(result).getType();
			postResponse = new PostResponse();
			postResponse.setDocumentId(id, qn.getLocalName(), fileName);
		}
		return postResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.uspto.trademark.cms.repo.services.CmsCommonService#deleteDocument
	 * (java.lang.String, java.lang.String,
	 * org.alfresco.service.namespace.QName)
	 */
	@Override
	public <T extends AbstractBaseType> TmngAlfResponse hardDeleteDocument(final String id, final String fileName,
			final QName type) {
		PostResponse postResponse = null;

		RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
		RetryingTransactionCallback<Boolean> callback = new RetryingTransactionCallback<Boolean>() {
			@Override
			public Boolean execute() throws Throwable {
				NodeRef documentNodeRef = cmsNodeLocator.locateNode(id, fileName, type);
				if (documentNodeRef != null) {
					String accessLevel = (String) serviceRegistry.getNodeService().getProperty(documentNodeRef,
							TradeMarkModel.ACCESS_LEVEL_QNAME);
					if (accessLevel != null && accessLevel.equalsIgnoreCase(PUBLIC)) {
						throw new TmngCmsException.AccessLevelRuleViolationException(
								"Public documents cannot be deleted");
					}
					serviceRegistry.getNodeService().deleteNode(documentNodeRef);
					return Boolean.TRUE;
				}
				return Boolean.FALSE;
			}
		};
		final Boolean result = txnHelper.doInTransaction(callback, false, true);
		if (result) {
			// Construct and Send Response
			postResponse = new PostResponse();
			postResponse.setDocumentId(id, type.getLocalName(), fileName);
		}
		return postResponse;
	}

	/**
	 * Used to set Id and other mandatory parameters during creation process.
	 *
	 * @param repositoryProperties
	 *            the repository properties
	 * @param id
	 *            the id
	 */
	protected abstract void setMandatoryProperties(Map<QName, Serializable> repositoryProperties, String id);

	/**
	 * Used to buildResponse after creation.
	 *
	 * @param <T>
	 *            the generic type
	 * @param id
	 *            the id
	 * @param type
	 *            the type
	 * @param fileName
	 *            the file name
	 * @param versionNumber
	 *            the version number
	 * @return the tmng alf response
	 */
	protected abstract <T extends AbstractBaseType> TmngAlfResponse buildResponse(String id, String type,
			String fileName, String versionNumber);
}