package gov.uspto.trademark.cms.repo.services;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.webscripts.beans.CaseDocumentMetadataResponse;

/**
 * Services to handle cases. Each case has a folder under which all case related
 * documents are stored. Each case has unique case number as an identifier.
 *
 * @author bgummadi
 */
public interface CaseService extends BaseService {

    /**
     * Create case folder tree structure to host case documents.
     *
     * @param serialNumber
     *            the serial number
     * @return the node ref
     */
    NodeRef createCaseFolderTree(String serialNumber);

    /**
     * Returns the case folder's NodeRef for a serial nuumber.
     *
     * @param serialNumber
     *            the serial number
     * @return the case folder node ref
     */
    NodeRef getCaseFolderNodeRef(String serialNumber);

    /**
     * Returns the case folder's NodeRef for a serial number. Creates case
     * folder if it doesn't exist
     *
     * @param serialNumber
     *            the serial number
     * @param createIfNotFound
     *            the create if not found
     * @return the case folder node ref
     */
    NodeRef getCaseFolderNodeRef(String serialNumber, boolean createIfNotFound);

    /**
     * Returns case folder properties.
     *
     * @param serialNumber
     *            the serial number
     * @return the case folder properties
     */
    Map<QName, Serializable> getCaseFolderProperties(String serialNumber);

    /**
     * Returns case folder properties.
     *
     * @param caseFolderNodeRef
     *            the case folder node ref
     * @return the case folder properties
     */
    Map<QName, Serializable> getCaseFolderProperties(NodeRef caseFolderNodeRef);

    /**
     * Returns a list of child NodeRefs that matches a Qname for a given serial
     * number.
     *
     * @param serialNumber
     *            the serial number
     * @param qname
     *            the qname
     * @return the child node refs
     */
    List<ChildAssociationRef> getChildNodeRefs(String serialNumber, QName qname);

    /**
     * Returns a list of child NodeRef's that matches a given set of QNames for
     * a given serial number.
     *
     * @param serialNumber
     *            the serial number
     * @param qnameSet
     *            the qname set
     * @return the child node refs
     */
    List<ChildAssociationRef> getChildNodeRefs(String serialNumber, Set<QName> qnameSet);

    /**
     * Returns properties for all the documents under the case folder for a
     * given serial number.
     *
     * @param serialNumber
     *            the serial number
     * @return the all document properties
     */
    List<CaseDocumentMetadataResponse> getAllDocumentsProperties(String serialNumber, CmsDataFilter dataFilter);

    /**
     * Returns NodeRef for Case folder.
     *
     * @return NodeRef
     */
    NodeRef getCabinetCaseFolderNodeRef();

    /**
     * @Title: getAllMarkDocumentNodeRefs @Description: @param
     * serialNumber @return @return List<ChildAssociationRef> @throws
     */
    List<ChildAssociationRef> getAllLatestMarkDocumentNodeRefs(String serialNumber);

    List<String> retrieveTicrsCaseFileNames(String caseId);

}