package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.util.Collection;
import java.util.List;

import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.version.Version;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.services.MarkService;
import gov.uspto.trademark.cms.repo.services.impl.AbstractBaseService;

/**
 * The Class MarkServiceBase.
 *
 * @author stank
 */
public class MarkServiceBase extends AbstractBaseService implements MarkService {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(MarkServiceBase.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.MarkService#getTradeMarkNodeRef
     * (java.lang.String, java.lang.String,
     * org.alfresco.service.namespace.QName,
     * org.alfresco.service.namespace.QName)
     */
    @Override
    public NodeRef getTradeMarkNodeRef(String serialNumber, String versionNumber, QName documentType) {
        NodeRef nodeRef = null;
        NodeRef latestVersionMarknodeRef = null;
        if (StringUtils.isNotBlank(versionNumber)) {
            nodeRef = getVersionNodeRef(serialNumber, versionNumber, documentType);
        } else {
            latestVersionMarknodeRef = getTradeMarkNodeRef(serialNumber, documentType);
            nodeRef = latestVersionMarknodeRef;
        }
        return nodeRef;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.MarkService#getTradeMarkNodeRef
     * (java.lang.String, org.alfresco.service.namespace.QName,
     * org.alfresco.service.namespace.QName)
     */
    @Override
    public NodeRef getTradeMarkNodeRef(String serialNumber, QName documentType) {
        List<ChildAssociationRef> childRefList = caseService.getChildNodeRefs(serialNumber, documentType);
        NodeRef markNR = null;
        for (ChildAssociationRef car : childRefList) {
            NodeRef nr = car.getChildRef();
            if (serviceRegistry.getNodeService().getType(nr).isMatch(documentType)) {
                markNR = nr;
                break;
            }
        }
        if (markNR == null) {
            log.debug("No marks found for {}", serialNumber);
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, "No Mark found inside " + serialNumber);
        }
        return markNR;
    }

    /**
     * Returns the NodeRef of a particular version number for a given serial
     * number.
     *
     * @param serialNumber
     *            the serial number
     * @param versionNumber
     *            the version number
     * @param documentType
     *            the document type
     * @return the version node ref
     */
    protected NodeRef getVersionNodeRef(String serialNumber, String versionNumber, QName documentType) {
        Collection<Version> allVersions = getVersionHistory(serialNumber, documentType);
        NodeRef nodeRef = null;
        for (Version version : allVersions) {
            if (versionNumber.equals(version.getVersionProperty("versionLabel"))) {
                nodeRef = version.getFrozenStateNodeRef();
                return nodeRef;
            }
        }
        return nodeRef;
    }

    /**
     * Returns version history for a given serialNumber.
     *
     * @param serialNumber
     *            the serial number
     * @param documentType
     *            the document type
     * @return the version history
     */
    protected Collection<Version> getVersionHistory(String serialNumber, QName documentType) {
        NodeRef markNodeRef = getTradeMarkNodeRef(serialNumber, documentType);
        return this.serviceRegistry.getVersionService().getVersionHistory(markNodeRef).getAllVersions();
    }

}