package gov.uspto.trademark.cms.repo.nodelocator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.model.Repository;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.version.Version;
import org.alfresco.service.cmr.version.VersionDoesNotExistException;
import org.alfresco.service.cmr.version.VersionHistory;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.PathResolver;

/**
 * This abstract class implements the funcitonality to locate the nodes based on
 * various parameters.
 * 
 * @author vnondapaka
 *
 */
@Component
public abstract class AbstractNodeLocator implements CmsNodeLocator {

    private static final Logger log = LoggerFactory.getLogger(AbstractNodeLocator.class);

    /** The service registry. */
    @Autowired
    @Qualifier(value = "ServiceRegistry")
    protected ServiceRegistry serviceRegistry;

    /** The repository helper. */
    @Autowired
    @Qualifier(value = "repositoryHelper")
    protected Repository repositoryHelper;

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNodeRef
     * (org.alfresco.service.cmr.repository.NodeRef,
     * org.alfresco.service.namespace.QName, java.lang.String[])
     */
    @Override
    public NodeRef locateNodeRef(NodeRef nodeRef, QName docType, String... paths) {
        NodeRef resultNode = null;
        try {
            List<String> pathElements = Arrays.asList(paths);
            QName nodeDocType = serviceRegistry.getFileFolderService().resolveNamePath(nodeRef, pathElements).getType();
            if (nodeDocType.isMatch(docType) || nodeDocType.isMatch(ApplicationModel.TYPE_FILELINK)) {
                resultNode = locateNodeRef(nodeRef, paths);
                if (resultNode != null && nodeDocType.isMatch(ApplicationModel.TYPE_FILELINK)
                        && !serviceRegistry.getFileFolderService().getFileInfo(resultNode).getType().isMatch(docType)) {
                    throw new TmngCmsException.FileCheckFailedException(HttpStatus.FORBIDDEN,
                            "Service provided cannot be used on different document types.");
                }
            } else {
                throw new TmngCmsException.FileCheckFailedException(HttpStatus.FORBIDDEN,
                        "Service provided cannot be used on different document types.");
            }
        } catch (FileNotFoundException e) {
            throw new TmngCmsException.DocumentDoesNotExistException("Requested File \"" + Arrays.asList(paths).toString() + "\" Not Found", e);
        }
        return resultNode;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNodeRef
     * (org.alfresco.service.cmr.repository.NodeRef, java.lang.String,
     * org.alfresco.service.namespace.QName, java.lang.String[])
     */
    @Override
    public NodeRef locateNodeRef(NodeRef nodeRef, String version, QName docType, String... paths) {
        NodeRef docNode = null;
        try {
            docNode = locateNodeRef(nodeRef, docType, paths);
            if (version != null && docNode != null) {
                VersionHistory versionHistory = serviceRegistry.getVersionService().getVersionHistory(docNode);
                docNode = processForVersionNode(version, docNode, versionHistory);
            } else if (version == null) {
                docNode = locateNodeRef(nodeRef, docType, paths);
            }
        } catch (VersionDoesNotExistException e) {
            docNode = null;
        }
        return docNode;

    }

    private NodeRef processForVersionNode(String version, NodeRef docNode, VersionHistory versionHistory) {
        Version versionNode;
        if (versionHistory != null) {
            versionNode = versionHistory.getVersion(version);
            if (versionNode != null) {
                return versionNode.getFrozenStateNodeRef();
            }
        } else {
            return null;
        }
        return docNode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNodeRef
     * (org.alfresco.service.cmr.repository.NodeRef, java.lang.String[])
     */
    @Override
    public NodeRef locateNodeRef(NodeRef nodeRef, String... paths) {
        NodeRef resultNode = null;
        try {
            List<String> pathElements = Arrays.asList(paths);
            FileInfo fileInfo = serviceRegistry.getFileFolderService().resolveNamePath(nodeRef, pathElements);
            if (fileInfo.getType().isMatch(ApplicationModel.TYPE_FILELINK)) {
                resultNode = serviceRegistry.getFileFolderService().resolveNamePath(nodeRef, pathElements).getLinkNodeRef();
            } else {
                resultNode = serviceRegistry.getFileFolderService().resolveNamePath(nodeRef, pathElements).getNodeRef();
            }
        } catch (FileNotFoundException e) {
            Path primaryPath = serviceRegistry.getNodeService().getPath(nodeRef);
            String result = PathResolver.getNamePath(serviceRegistry.getNodeService(), primaryPath, null, TMConstants.FORWARD_SLASH, null);
            String fullPath = result + TMConstants.FORWARD_SLASH + Arrays.deepToString(paths);
            if (log.isDebugEnabled()) {
                log.debug("Requested File '" + fullPath + "' Not Found");
            }
        }
        return resultNode;
    }
    
    public NodeRef locateNodeRefWithException(NodeRef nodeRef, String... paths) {
        NodeRef resultNode = null;
        try {
            List<String> pathElements = Arrays.asList(paths);
            FileInfo fileInfo = serviceRegistry.getFileFolderService().resolveNamePath(nodeRef, pathElements);
            if (fileInfo.getType().isMatch(ApplicationModel.TYPE_FILELINK)) {
                resultNode = serviceRegistry.getFileFolderService().resolveNamePath(nodeRef, pathElements).getLinkNodeRef();
            } else {
                resultNode = serviceRegistry.getFileFolderService().resolveNamePath(nodeRef, pathElements).getNodeRef();
            }
        } catch (FileNotFoundException e) {
            Path primaryPath = serviceRegistry.getNodeService().getPath(nodeRef);
            String result = PathResolver.getNamePath(serviceRegistry.getNodeService(), primaryPath, null, TMConstants.FORWARD_SLASH, null);
            String fullPath = result + TMConstants.FORWARD_SLASH + StringUtils.join(paths, TMConstants.FORWARD_SLASH);
            if (log.isDebugEnabled()) {
                log.debug("Requested File '" + fullPath + "' Not Found");
            }
            throw new SerialNumberNotFoundException(fullPath + " File or Folder doesn't exist", e);
        }
        return resultNode;
    }    

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNode(java
     * .lang.String, java.lang.String, org.alfresco.service.namespace.QName)
     */
    @Override
    public NodeRef locateNode(String id, String fileName, QName docType) {
        NodeRef nodeRef = locateNode(id);
        if (nodeRef != null) {
            return locateNodeRef(nodeRef, docType, fileName);
        } else {
            return nodeRef;
        }
    }

    public NodeRef retrieveMarkOfRecordsFileName(String caseId) {
        Set<QName> qnameSet = new HashSet<QName>();
        qnameSet.add(TradeMarkModel.MARK_QNAME);
        NodeRef caseFolderNodeRef = locateNode(caseId);
        List<ChildAssociationRef> childRefList = serviceRegistry.getNodeService().getChildAssocs(caseFolderNodeRef, qnameSet);
        NodeRef fileNameLcl = null;
        int numOfMrkFound = childRefList.size();
        if (numOfMrkFound == TMConstants.ONE) {
            NodeRef nodeRefMOR = childRefList.get(TMConstants.ZERO).getChildRef();
            fileNameLcl = nodeRefMOR;
        } else if (numOfMrkFound > TMConstants.ONE) {
            for (ChildAssociationRef car : childRefList) {
                NodeRef nr = car.getChildRef();
                String fileName = (String) serviceRegistry.getNodeService().getProperty(nr, ContentModel.PROP_NAME);
                if (fileName.toUpperCase().startsWith("MRK_")) {
                    fileNameLcl = nr;
                    break;
                }
            }
            if (fileNameLcl == null) {
                for (ChildAssociationRef car : childRefList) {
                    NodeRef nr = car.getChildRef();
                    String fileName = (String) serviceRegistry.getNodeService().getProperty(nr, ContentModel.PROP_NAME);
                    if (fileName.toUpperCase().startsWith("USPTO-IMAGE-MARK")) {
                        fileNameLcl = nr;
                        break;
                    }
                }
            }
        }
        if (fileNameLcl == null) {
            log.debug("No marks found inside {}", caseId);
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, "No Mark found inside " + caseId);
        }
        return fileNameLcl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNodeRef
     * (java.lang.String[])
     */
    @Override
    public NodeRef locateNodeRef(String... paths) {
        List<String> pathElements = Arrays.asList(paths);
        NodeRef nodeRef = null;
        try {
            nodeRef = serviceRegistry.getFileFolderService().resolveNamePath(repositoryHelper.getCompanyHome(), pathElements)
                    .getNodeRef();
        } catch (FileNotFoundException e) {
            throw new SerialNumberNotFoundException(paths[paths.length - 1] + " File or Folder doesn't exist", e);
        }
        return nodeRef;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNodeRef
     * (org.alfresco.service.namespace.QName, java.lang.String[])
     */
    @Override
    public NodeRef locateNodeRef(QName docType, String... paths) {
        List<String> pathElements = Arrays.asList(paths);
        NodeRef nodeRef = null;
        try {
            FileInfo fileInfo = serviceRegistry.getFileFolderService().resolveNamePath(repositoryHelper.getCompanyHome(),
                    pathElements);
            QName nodeDocType = fileInfo.getType();
            if (nodeDocType.isMatch(docType)) {
                nodeRef = fileInfo.getNodeRef();
            } else {
                throw new TmngCmsException.FileCheckFailedException(HttpStatus.FORBIDDEN,
                        "Service provided cannot be used on different document types.");
            }
        } catch (FileNotFoundException e) {
            throw new SerialNumberNotFoundException(paths[paths.length - 1] + " File or Folder doesn't exist", e);
        }
        return nodeRef;
    }
}