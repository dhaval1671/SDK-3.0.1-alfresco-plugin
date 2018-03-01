package gov.uspto.trademark.cms.repo.services.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.model.Repository;
import org.alfresco.service.ServiceException;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileExistsException;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.DataDictionaryHelper;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.services.BaseService;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.services.VersionService;

/**
 * The Class AbstractBaseService.
 *
 * @author bgummadi
 */
public abstract class AbstractBaseService implements BaseService {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(AbstractBaseService.class);

    /** The service registry. */
    protected ServiceRegistry serviceRegistry;
    
    @Autowired
    @Qualifier("TransactionService")    
    protected TransactionService transactionService;    

    /** The repository helper. */
    protected Repository repositoryHelper;

    /** The case service. */
    protected CaseService caseService;

    /** The version service. */
    private VersionService versionService;

    /**
     * Sets the case service.
     *
     * @param caseService
     *            the new case service
     */
    public void setCaseService(CaseService caseService) {
        this.caseService = caseService;
    }

    /**
     * Gets the folder node ref.
     *
     * @param paths
     *            the paths
     * @return the folder node ref
     */
    protected NodeRef getFolderNodeRef(String... paths) {
        List<String> pathElements = Arrays.asList(paths);
        NodeRef nodeRef = null;
        try {
            nodeRef = serviceRegistry.getFileFolderService().resolveNamePath(repositoryHelper.getCompanyHome(), pathElements)
                    .getNodeRef();
        } catch (FileNotFoundException fnfe) {
            throw new TmngCmsException.SerialNumberNotFoundException(paths[paths.length - 1] + " File or Folder doesn't exist",
                    fnfe);
        }
        return nodeRef;
    }

    /**
     * Gets the folder node ref.
     *
     * @param rootNodeRef
     *            the root node ref
     * @param paths
     *            the paths
     * @return the folder node ref
     */
    protected NodeRef getFolderNodeRef(NodeRef rootNodeRef, String... paths) {
        return getFolderNodeRef(null, rootNodeRef, paths);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.BaseService#getType(org.alfresco
     * .service.cmr.repository.NodeRef)
     */
    @Override
    public QName getType(NodeRef nodeRef) {
        return this.serviceRegistry.getNodeService().getType(nodeRef);
    }

    /**
     * Gets the folder node ref.
     *
     * @param documentType
     *            the document type
     * @param rootNodeRef
     *            the root node ref
     * @param paths
     *            the paths
     * @return the folder node ref
     */
    public NodeRef getFolderNodeRef(QName documentType, NodeRef rootNodeRef, String... paths) {
        List<String> pathElements = Arrays.asList(paths);
        NodeRef nodeRef = null;
        try {

            if (documentType != null) {
                QName docType = serviceRegistry.getFileFolderService().resolveNamePath(rootNodeRef, pathElements).getType();
                if (docType.isMatch(documentType)) {
                    nodeRef = serviceRegistry.getFileFolderService().resolveNamePath(rootNodeRef, pathElements).getNodeRef();
                } else {
                    throw new TmngCmsException.FileCheckFailedException(HttpStatus.FORBIDDEN,
                            "Service provided cannot be used on different document types.");
                }
            } else {
                nodeRef = serviceRegistry.getFileFolderService().resolveNamePath(rootNodeRef, pathElements).getNodeRef();
            }
        } catch (FileNotFoundException e) {
            if (log.isDebugEnabled()) {
                log.debug("Entity does NOT exist \"" + pathElements.toString() + "\"", e);
            }
        }
        return nodeRef;
    }

    /**
     * Creates the folder.
     *
     * @param parentNodeRef
     *            the parent node ref
     * @param folderName
     *            the folder name
     * @return the node ref
     */
    protected NodeRef createFolder(NodeRef parentNodeRef, String folderName) {
        return createFolder(parentNodeRef, folderName, ContentModel.TYPE_FOLDER);
    }

    /**
     * Creates the folder.
     *
     * @param parentNodeRef
     *            the parent node ref
     * @param folderName
     *            the folder name
     * @param folderType
     *            the folder type
     * @return the node ref
     */
    protected NodeRef createFolder(NodeRef parentNodeRef, String folderName, QName folderType) {
        NodeRef nodeRef = null;
        try {
            // Check if node exists
            nodeRef = getFolderNodeRef(null, parentNodeRef, folderName);
            boolean isContentNodeRef = false;
            if (null != nodeRef) {
                boolean isFolder = serviceRegistry.getFileFolderService().getFileInfo(nodeRef).isFolder();
                isContentNodeRef = !isFolder;
            }
            // Create if node doesn't exist
            if (nodeRef == null || isContentNodeRef) {
                FileInfo fileInfo = this.serviceRegistry.getFileFolderService().create(parentNodeRef, folderName, folderType);
                nodeRef = fileInfo.getNodeRef();
            }
        } catch (FileExistsException fileExistsException) {
            if (log.isDebugEnabled()) {
                log.debug("File Exists Exception: " + nodeRef, fileExistsException);
            }
            nodeRef = getFolderNodeRef(null, parentNodeRef, folderName);
        } catch (Exception e) {
            /* Ignore folder exists */
            if (log.isInfoEnabled()) {
                log.info(e.getMessage());
            }
            if (log.isDebugEnabled()) {
                log.debug("Unable to create folder, may already exist: " + nodeRef, e);
            }
        }

        return nodeRef;
    }

    /**
     * Uploads a given node with the given properties.
     *
     * @param type
     *            the type
     * @param serialNumber
     *            the serial number
     * @param fileName
     *            the file name
     * @param inputStream
     *            the input stream
     * @param properties
     *            the properties
     * @return the node ref
     */
    public NodeRef upload(QName type, String serialNumber, String fileName, InputStream inputStream,
            Map<QName, Serializable> properties) {
        NodeRef parentFolder = caseService.getCaseFolderNodeRef(serialNumber, true);
        return this.upload(type, serialNumber, parentFolder, fileName, inputStream, properties);
    }

    /**
     * Creates a new node with the given information.
     *
     * @param type
     *            the type
     * @param serialNumber
     *            the serial number
     * @param parentFolder
     *            the parent folder
     * @param fileName
     *            the file name
     * @param inputStream
     *            the input stream
     * @param properties
     *            the properties
     * @return the node ref
     */
    public NodeRef upload(QName type, String serialNumber, NodeRef parentFolder, String fileName, InputStream inputStream,
            Map<QName, Serializable> properties) {
        properties.put(ContentModel.PROP_NAME, fileName);
        properties.put(DataDictionaryHelper.getTradeMarkPropertyQName(TradeMarkModel.DOCUMENT_NAME), fileName);
        QName lclQname = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, fileName);
        ChildAssociationRef children = serviceRegistry.getNodeService().createNode(parentFolder, ContentModel.ASSOC_CONTAINS,
                lclQname, type, properties);
        NodeRef node = children.getChildRef();
        ContentWriter writer = serviceRegistry.getContentService().getWriter(node, ContentModel.PROP_CONTENT, true);
        writer.setMimetype(serviceRegistry.getMimetypeService().guessMimetype(fileName));
        writer.putContent(inputStream);
        return node;
    }

    /**
     * Gets the associations.
     *
     * @param source
     *            the source
     * @param type
     *            the type
     * @return the associations
     */
    public List<NodeRef> getAssociations(final NodeRef source, final QName type) {
        NodeService ns = serviceRegistry.getNodeService();
        List<NodeRef> nodes = null;
        try {
            if (!ns.exists(source)) {
                throw new IllegalArgumentException("Source node does not exist: " + source);
            }
            List<AssociationRef> assocs = ns.getTargetAssocs(source, type);
            if (assocs != null) {
                nodes = new LinkedList<NodeRef>();
                for (AssociationRef assoc : assocs) {
                    nodes.add(assoc.getTargetRef());
                }
            }
        } catch (Exception e) {

            if (log.isInfoEnabled()) {
                log.info(e.getMessage(), e);
            }

            String msg = "Could not find associations:\n" + "Source Node: " + source + "\n" + "Type: " + type + "\n";
            if (StringUtils.isNotBlank(e.getMessage())) {
                msg += "Caused by: " + e.getMessage();
            }
            throw new ServiceException(msg, e);
        }
        return nodes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.BaseService#getNodeProperties(org
     * .alfresco.service.cmr.repository.NodeRef)
     */
    @Override
    public Map<QName, Serializable> getNodeProperties(NodeRef nodeRef) {
        return serviceRegistry.getNodeService().getProperties(nodeRef);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.BaseService#getContentReader(org
     * .alfresco.service.cmr.repository.NodeRef,
     * org.alfresco.service.namespace.QName)
     */
    @Override
    public ContentReader getContentReader(NodeRef nodeRef, QName qname) {
        return this.serviceRegistry.getContentService().getReader(nodeRef, qname);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.BaseService#getProperty(org.
     * alfresco .service.cmr.repository.NodeRef,
     * org.alfresco.service.namespace.QName)
     */
    @Override
    public Serializable getProperty(NodeRef nodeRef, QName qname) {
        return this.serviceRegistry.getNodeService().getProperty(nodeRef, qname);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.BaseService#setServiceRegistry(
     * org.alfresco.service.ServiceRegistry)
     */
    @Override
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.BaseService#setRepositoryHelper
     * (org.alfresco.repo.model.Repository)
     */
    @Override
    public void setRepositoryHelper(Repository repositoryHelper) {
        this.repositoryHelper = repositoryHelper;
    }

    /**
     * Gets the version service.
     *
     * @return the version service
     */
    public VersionService getVersionService() {
        return versionService;
    }

    /**
     * Sets the version service.
     *
     * @param versionService
     *            the new version service
     */
    public void setVersionService(VersionService versionService) {
        this.versionService = versionService;
    }

    /**
     * Returns true if NodeRef has given aspect.
     *
     * @param nodeRef
     *            the node ref
     * @param aspect
     *            the aspect
     * @return true, if successful
     */
    @Override
    public boolean hasAspect(NodeRef nodeRef, QName aspect) {
        return this.serviceRegistry.getNodeService().hasAspect(nodeRef, aspect);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.BaseService#fileExists(java.util
     * .List, java.lang.String[])
     */
    @Override
    public NodeRef fileExists(List<QName> documentTypeList, String... path) {
        NodeRef returningNodeRef = null;
        NodeRef nr = fileExists(path);
        if (nr != null) {
            if (!(documentTypeList.isEmpty())) {
                QName docType = serviceRegistry.getNodeService().getType(nr);
                if (checkIfDocTypePresentInAllowedList(docType, documentTypeList)) {
                    returningNodeRef = nr;
                } else {
                    throw new TmngCmsException.FileCheckFailedException(HttpStatus.FORBIDDEN,
                            "Service provided cannot be used on different document types.");
                }
            } else {
                throw new ServiceException("Parameter: QName documentType cannot be null.");
            }
        }

        return returningNodeRef;
    }

    /**
     * Check if doc type present in allowed list.
     *
     * @param docType
     *            the doc type
     * @param documentTypeList
     *            the document type list
     * @return true, if successful
     */
    private boolean checkIfDocTypePresentInAllowedList(QName docType, List<QName> documentTypeList) {
        boolean returnStatus = false;
        for (QName qn : documentTypeList) {
            if (docType.isMatch(qn)) {
                returnStatus = true;
                break;
            }
        }
        return returnStatus;
    }

    /**
     * Checks if the given path exists under company home.
     *
     * @param path
     *            the path
     * @return NodeRef if it exists
     */
    public NodeRef fileExists(String... path) {
        log.debug("checking if " + Arrays.toString(path) + " exits.");
        try {
            return this.serviceRegistry.getFileFolderService()
                    .resolveNamePath(repositoryHelper.getCompanyHome(), Arrays.asList(path)).getNodeRef();
        } catch (FileNotFoundException e) {
            if (log.isInfoEnabled()) {
                log.info("Entity does NOT exist \"" + Arrays.toString(path) + "\"");
            }
            if (log.isDebugEnabled()) {
                log.debug("Entity does NOT exist \"" + Arrays.toString(path) + "\"", e);
            }
            return null;
        }
    }

    /**
     * Checks if the given path exists under a library.
     *
     * @param path
     *            the path
     * @return NodeRef if it exists
     */
    @Override
    public NodeRef libraryFileExists(String... path) {
        try {
            return this.serviceRegistry.getFileFolderService()
                    .resolveNamePath(repositoryHelper.getCompanyHome(), Arrays.asList(path)).getNodeRef();
        } catch (FileNotFoundException e) {
            if (log.isInfoEnabled()) {
                log.info("entity does NOT exist \"" + Arrays.toString(path) + "\"");
            }
            if (log.isDebugEnabled()) {
                log.debug("Entity does NOT exist \"" + Arrays.toString(path) + "\"", e);
            }
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.BaseService#getClientTypeJsonBytes
     * (org.alfresco.service.cmr.repository.NodeRef, java.lang.Class)
     */
    @Override
    public <T extends AbstractBaseType> byte[] getClientTypeJsonBytes(NodeRef nodeRef, Class<T> target) {
        byte[] b;
        T t = generateOutGoingDTO(nodeRef, target);
        b = JacksonHelper.generateClientJsonFrDTO(t);
        return b;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.BaseService#generateOutGoingDTO
     * (org.alfresco.service.cmr.repository.NodeRef, java.lang.Class)
     */
    @Override
    public <T extends AbstractBaseType> T generateOutGoingDTO(NodeRef nodeRef, Class<T> target) {
        Map<QName, Serializable> repositoryProperties = this.serviceRegistry.getNodeService().getProperties(nodeRef);
        Map<String, Serializable> convertedMap = WebScriptHelper.stringifyMap(repositoryProperties);
        T t = null;
        t = JacksonHelper.generateDTOFrAlfrescoRepoProps(convertedMap, target);
        return t;
    }

    /**
     * Update document metadata.
     *
     * @param properties
     *            the properties
     * @param nodeRef
     *            the node ref
     */
    protected void updateDocumentMetadata(final Map<QName, Serializable> properties, final NodeRef nodeRef) {
        for (Entry<QName, Serializable> entry : properties.entrySet()) {
            serviceRegistry.getNodeService().setProperty(nodeRef, entry.getKey(), entry.getValue());
        }
    }

    /**
     * Delete node ref.
     *
     * @param nodeRef
     *            the node ref
     */
    protected void deleteNodeRef(NodeRef nodeRef) {
        serviceRegistry.getNodeService().addAspect(nodeRef, ContentModel.ASPECT_TEMPORARY, null);
        serviceRegistry.getNodeService().deleteNode(nodeRef);
    }

    /**
     * Gets the parent folder node ref.
     *
     * @param nodeRef
     *            the node ref
     * @return the parent folder node ref
     */
    protected NodeRef getParentFolderNodeRef(NodeRef nodeRef) {
        ChildAssociationRef car = serviceRegistry.getNodeService().getPrimaryParent(nodeRef);
        return car.getParentRef();
    }

}
