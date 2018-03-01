package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.namespace.RegexQNamePattern;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.FileCheckFailedException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.AccessLevel;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.PathResolver;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.OfficeAction;
import gov.uspto.trademark.cms.repo.nodedelete.BehaviorDocumentDelete;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.DocumentCreateListener;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.AbstractCaseCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.DocumentId;
import gov.uspto.trademark.cms.repo.webscripts.beans.EvidencePostResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.PostResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;
import gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest;

/**
 * This service will implement the OfficeAction document type functionality.
 * 
 * @author vnondapaka
 *
 */
@Component("OfficeActionDocService")
public class OfficeActionDocService extends AbstractCaseCommonService {

    /** The Constant PATH_SEPARATOR. */
    private static final String PATH_SEPARATOR = "/";

    /** The Constant EVIDENCE_LIST. */
    private static final String EVIDENCE_LIST = "evidenceList";

    @Autowired
    @Qualifier(value = "DocumentSoftDelete")
    protected BehaviorDocumentDelete behaviorDocumentDelete;

    /**
     * Instantiates a new office action doc service.
     *
     * @param caseNodeCreator
     *            the case node creator
     * @param caseNodeLocator
     *            the case node locator
     */
    @Autowired
    public OfficeActionDocService(CmsNodeCreator caseNodeCreator, CmsNodeLocator caseNodeLocator) {
        super(caseNodeCreator, caseNodeLocator);
    }

    /*
     * This method is used to create the officeAction Document and also attach
     * the evidences provided in the request.
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#create(java.lang
     * .String, java.lang.String, byte[], java.util.Map)
     */
    @Override
    public TmngAlfResponse create(final String id, final String fileName, final ContentItem content,
            Map<String, Serializable> properties) {
        final CopyEvidenceRequest[] evidenceAttachments = getAssociatedEvidencesFromMetadata(id, properties);
        properties.remove(EVIDENCE_LIST);
        return createDocument(id, fileName, content, TradeMarkModel.OFFICE_ACTION_QNAME, properties, OfficeAction.class,
                new DocumentCreateListener() {
                    @Override
                    public void listen() {
                        if (evidenceAttachments != null) {
                            NodeRef officeActionNodeRef = cmsNodeLocator.locateNode(id, fileName,
                                    TradeMarkModel.OFFICE_ACTION_QNAME);
                            for (CopyEvidenceRequest eviRequest : evidenceAttachments) {
                                String[] repositoryPath = PathResolver.resolveDocumentIdToPath(eviRequest.getDocumentId())
                                        .split(PATH_SEPARATOR);
                                NodeRef evidenceNodeRef = cmsNodeLocator.locateNodeRef(TradeMarkModel.EVIDENCE_QNAME,
                                        repositoryPath);
                                serviceRegistry.getNodeService().createAssociation(officeActionNodeRef, evidenceNodeRef,
                                        TradeMarkModel.ASSOC_RELATED_EVIDENCE);
                                serviceRegistry.getNodeService().createAssociation(evidenceNodeRef, officeActionNodeRef,
                                        TradeMarkModel.ASSOC_RELATED_OFFICE_ACTION);
                                processAssociatedEvidence(eviRequest, evidenceNodeRef);
                            }
                        }
                    }

                    private void processAssociatedEvidence(CopyEvidenceRequest eviRequest, NodeRef evidenceNodeRef) {
                        if (eviRequest.getMetadata() != null) {
                            Map<QName, Serializable> metadata = WebScriptHelper
                                    .generateAlfRepoPropsFrDTO(eviRequest.getMetadata());
                            if (metadata.get(TradeMarkModel.PROP_GROUP_NAME) != null) {
                                serviceRegistry.getNodeService().setProperty(evidenceNodeRef, TradeMarkModel.PROP_GROUP_NAME,
                                        metadata.get(TradeMarkModel.PROP_GROUP_NAME));
                            }
                        }
                        // Evidences attached to an office-action will always be
                        // public from this point
                        serviceRegistry.getNodeService().setProperty(evidenceNodeRef, TradeMarkModel.ACCESS_LEVEL_QNAME,
                                AccessLevel.PUBLIC.getFieldAccessLevel());
                    }
                });
    }

    /*
     * This method is used to update the office Action Document
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#update(java.lang
     * .String, java.lang.String, byte[], java.util.Map)
     */
    @Override
    public TmngAlfResponse update(final String id, final String fileName, final ContentItem content,
            Map<String, Serializable> properties) {
        return updateDocument(id, fileName, content, TradeMarkModel.OFFICE_ACTION_QNAME, properties, OfficeAction.class);
    }

    /*
     * This method is used to retrieve the content of an office action document.
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveContent
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber) {
        return retrieveContent(cmsNodeLocator, TradeMarkModel.OFFICE_ACTION_QNAME, id, fileName, versionNumber);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveContent(
     * java.lang.String, java.lang.String, java.lang.String,
     * gov.uspto.trademark.cms.repo.filters.CmsDataFilter)
     */
    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return retrieveContent(cmsNodeLocator, TradeMarkModel.OFFICE_ACTION_QNAME, id, fileName, versionNumber, dataFilter);
    }

    /*
     * This method is used to retrieve the metadata of office action document.
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber) {
        byte[] metadata = retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber, TradeMarkModel.OFFICE_ACTION_QNAME,
                OfficeAction.class);
        if (metadata != null) {
            Map<String, Serializable> m = WebScriptHelper.parseJson(metadata);
            List<EvidencePostResponse> eviAssocFiles = getOfficeActionRelatedEvidence(
                    cmsNodeLocator.locateNode(id, fileName, TradeMarkModel.OFFICE_ACTION_QNAME));
            m.put(EVIDENCE_LIST, (Serializable) eviAssocFiles);
            return JacksonHelper.generateClientJsonFrDTO(m);
        } else {
            return metadata;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata(
     * java.lang.String, java.lang.String, java.lang.String,
     * gov.uspto.trademark.cms.repo.filters.CmsDataFilter)
     */
    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        byte[] metadata = retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber, TradeMarkModel.OFFICE_ACTION_QNAME,
                OfficeAction.class, dataFilter);
        if (metadata != null) {
            Map<String, Serializable> m = WebScriptHelper.parseJson(metadata);
            List<EvidencePostResponse> eviAssocFiles = getOfficeActionRelatedEvidence(
                    cmsNodeLocator.locateNode(id, fileName, TradeMarkModel.OFFICE_ACTION_QNAME));
            m.put(EVIDENCE_LIST, (Serializable) eviAssocFiles);
            return JacksonHelper.generateClientJsonFrDTO(m);
        } else {
            return metadata;
        }
    }

    /**
     * This method is used to retrieve the list of office action associated
     * evidences passed in this request. This method validates whether these
     * evidences exist in the system or not and also validates whether they
     * belong to the same case or not.
     *
     * @param id
     *            the id
     * @param properties
     *            the properties
     * @return the associated evidences from metadata
     */
    private CopyEvidenceRequest[] getAssociatedEvidencesFromMetadata(String id, Map<String, Serializable> properties) {
        CopyEvidenceRequest[] copyEvidenceRequests = null;
        try {
            @SuppressWarnings("unchecked")
            List<LinkedHashMap<String, String>> eviList = (List<LinkedHashMap<String, String>>) properties.get(EVIDENCE_LIST);
            if (eviList != null) {
                String jsonString = WebScriptHelper.toJsonAsString(eviList);
                copyEvidenceRequests = JacksonHelper.unMarshall(jsonString, CopyEvidenceRequest[].class);
                for (CopyEvidenceRequest copyEvidenceRequest : copyEvidenceRequests) {
                    String documentId = copyEvidenceRequest.getDocumentId();
                    processEvidenceMetadata(id, copyEvidenceRequest, documentId);
                }
            }
        } catch (IOException | FileCheckFailedException | SerialNumberNotFoundException e) {
            throw new TmngCmsException.CMSRuntimeException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } 
        return copyEvidenceRequests;
    }

    private void processEvidenceMetadata(String id, CopyEvidenceRequest copyEvidenceRequest, String documentId) {
        if (StringUtils.isNotBlank(documentId)) {
            String[] repositoryPath = PathResolver.resolveDocumentIdToPath(copyEvidenceRequest.getDocumentId())
                    .split(PATH_SEPARATOR);
            NodeRef nodeRef = cmsNodeLocator.locateNodeRef(TradeMarkModel.EVIDENCE_QNAME, repositoryPath);
            if (nodeRef == null) {
                throw new TmngCmsException.EvidenceFileDoesNotExistsException(
                        "Evidence Document " + copyEvidenceRequest.getDocumentId() + " does not exist");
            }
            String lclDocumentId = org.springframework.util.StringUtils.trimLeadingCharacter(documentId, '/');
            DocumentId documentIdObj = DocumentId.createDocumentId(lclDocumentId);
            if (!StringUtils.equals(id, documentIdObj.getDocumentId())) {
                throw new TmngCmsException.CMSRuntimeException(
                        "OfficeAction case number does not match incoming associated evidence case number");
            }
        } else {
            throw new TmngCmsException.CMSRuntimeException("documentId cannot be null or empty");
        }
    }

    /**
     * Gets the office action related evidence.
     *
     * @param nodeRef
     *            the node ref
     * @return the office action related evidence
     */
    private List<EvidencePostResponse> getOfficeActionRelatedEvidence(NodeRef nodeRef) {
        List<AssociationRef> assocs = serviceRegistry.getNodeService().getTargetAssocs(nodeRef,
                TradeMarkModel.ASSOC_RELATED_EVIDENCE);
        List<EvidencePostResponse> evidenceMetadataList = new LinkedList<>();
        Map<QName, Serializable> properties = null;
        EvidencePostResponse evidenceMetadata = null;
        NodeRef eviNodeRef = null;
        String serialNumber;
        for (AssociationRef associationRef : assocs) {
            eviNodeRef = associationRef.getTargetRef();
            if (eviNodeRef != null) {
                properties = serviceRegistry.getNodeService().getProperties(eviNodeRef);
                evidenceMetadata = new EvidencePostResponse();
                serialNumber = (String) properties.get(TradeMarkModel.SERIAL_NUMBER_QNAME);
                evidenceMetadata.setSerialNumber(serialNumber);
                evidenceMetadata.setDocumentId(serialNumber, serviceRegistry.getNodeService().getType(eviNodeRef).getLocalName(),
                        (String) properties.get(ContentModel.PROP_NAME));
                evidenceMetadata.setAccessLevel((String) properties.get(TradeMarkModel.ACCESS_LEVEL_QNAME));
                evidenceMetadata.setVersion((String) properties.get(ContentModel.PROP_VERSION_LABEL));
                evidenceMetadataList.add(evidenceMetadata);
            }
        }
        return evidenceMetadataList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#updateMetadata
     * (java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    public TmngAlfResponse updateMetadata(String id, String fileName, Map<String, Serializable> properties) {
        return updateDocument(id, fileName, null, TradeMarkModel.OFFICE_ACTION_QNAME, properties, OfficeAction.class);

    }

    @Override
    public TmngAlfResponse delete(String id, String fileName) {
        NodeRef offActnToBeDeletedNodeRef = cmsNodeLocator.locateNode(id, fileName, TradeMarkModel.OFFICE_ACTION_QNAME);
        String accessLevelLocal = (String) serviceRegistry.getNodeService().getProperty(offActnToBeDeletedNodeRef,
                TradeMarkModel.ACCESS_LEVEL_QNAME);
        PostResponse postResponse = null;
        if (accessLevelLocal != null && accessLevelLocal.equalsIgnoreCase(TMConstants.PUBLIC)) {
            throw new TmngCmsException.AccessLevelRuleViolationException(HttpStatus.FORBIDDEN,
                    "Public documents cannot be deleted");
        } else if (accessLevelLocal != null && accessLevelLocal.equalsIgnoreCase(TMConstants.INTERNAL)) {
            List<AssociationRef> associations = serviceRegistry.getNodeService().getTargetAssocs(offActnToBeDeletedNodeRef,
                    RegexQNamePattern.MATCH_ALL);
            for (AssociationRef assoc : associations) {
                // remove association BR-US14573
                removeAssociation(assoc.getSourceRef(), assoc.getTargetRef(), TradeMarkModel.ASSOC_RELATED_EVIDENCE);
                removeAssociation(assoc.getTargetRef(), assoc.getSourceRef(), TradeMarkModel.ASSOC_RELATED_OFFICE_ACTION);
                // make the evidence of type public. BR-US14573
                String accessLevel = (String) serviceRegistry.getNodeService().getProperty(assoc.getTargetRef(),
                        TradeMarkModel.ACCESS_LEVEL_QNAME);
                if (accessLevel != null && accessLevel.equalsIgnoreCase(TMConstants.PUBLIC)) {
                    serviceRegistry.getNodeService().setProperty(assoc.getTargetRef(), TradeMarkModel.ACCESS_LEVEL_QNAME,
                            AccessLevel.PUBLIC.getFieldAccessLevel());
                }
            }
            Boolean result = behaviorDocumentDelete.delete(offActnToBeDeletedNodeRef);
            if (result) {
                postResponse = new PostResponse();
                postResponse.setDocumentId(id, TradeMarkModel.OFFICE_ACTION_QNAME.getLocalName(), fileName);
            }
        } else {
            throw new TmngCmsException.AccessLevelRuleViolationException(HttpStatus.FORBIDDEN,
                    "document 'accessLevel' NOT recognized");
        }
        return postResponse;
    }

}
