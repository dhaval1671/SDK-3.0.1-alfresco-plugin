package gov.uspto.trademark.cms.repo.helpers;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.Path;
import org.springframework.util.StringUtils;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.webscripts.beans.DocumentId;
import gov.uspto.trademark.cms.repo.webscripts.beans.EfileDocumentId;

/**
 * Created by bgummadi on 9/15/2014.
 */
public class PathResolver {

    /** The Constant FOLDER_PATH. */
    public static final String FOLDER_PATH = "folder-path";

    /** The Constant FILE_PATH. */
    public static final String FILE_PATH = "file-path";

    /** The Constant EVIDENCE_LIBRARY_FILE_PATH_PREFIX. */
    public static final String EVIDENCE_LIBRARY_FILE_PATH_PREFIX = "libraries/evidences/content/" + FILE_PATH + "/";

    /** The Constant EVIDENCE_LIBRARY_FOLDER_PATH_PREFIX. */
    public static final String EVIDENCE_LIBRARY_FOLDER_PATH_PREFIX = "libraries/evidences/content/" + FOLDER_PATH + "/";

    /** The Constant TWO_A. */
    private static final String TWO_A = "2a";

    /** WebCapture path */
    private static final String WEBCAPTURE = "WebCapture";

    /** The Constant TWO_A_DOCUMENT_ID_PART. */
    private static final String TWO_A_DOCUMENT_ID_PART = EVIDENCE_LIBRARY_FILE_PATH_PREFIX + TWO_A;

    /**
     * Instantiates a new path resolver.
     */
    private PathResolver() {
        super();
    }

    /**
     * Gets the 2 a evidence root path.
     *
     * @return the 2 a evidence root path
     */
    public static String get2AEvidenceRootPath() {
        return TradeMarkModel.DOCUMENT_LIBRARY_FOLDER_NAME + "/" + TradeMarkModel.EVIDENCE_BANK_FOLDER_NAME + "/"
                + TradeMarkModel.TWOAEVIDENCE_LIBRARY_FOLDER_NAME;
    }

    /**
     * Gets the 2 a evidence root path.
     *
     * @return the 2 a evidence root path
     */
    public static String getWebCaptureEvidenceRootPath() {
        return TradeMarkModel.DOCUMENT_LIBRARY_FOLDER_NAME + TMConstants.FORWARD_SLASH + WEBCAPTURE;
    }

    /**
     * Resolve2 a document id to path.
     *
     * @param documentId
     *            the document id
     * @return the string
     */
    public static String resolve2ADocumentIdToPath(String documentId) {
        documentId = StringUtils.trimLeadingCharacter(documentId, '/');
        return StringUtils.replace(documentId.toLowerCase(), TWO_A_DOCUMENT_ID_PART.toLowerCase(), get2AEvidenceRootPath());
    }

    /**
     * Resolve2 a document id to path.
     *
     * @param documentId
     *            the document id
     * @return the string
     */
    public static String resolveWebCaptureDocumentIdToPath(String documentId) {
        documentId = StringUtils.trimLeadingCharacter(documentId, '/');
        return StringUtils.replace(documentId.toLowerCase(), WEBCAPTURE.toLowerCase() + TMConstants.FORWARD_SLASH, getWebCaptureEvidenceRootPath() + TMConstants.FORWARD_SLASH);
    }

    /**
     * Resolve case document id to path.
     *
     * @param documentId
     *            the document id
     * @return the string
     */
    public static String resolveCaseDocumentIdToPath(String documentId) {
        documentId = StringUtils.trimLeadingCharacter(documentId, '/');
        DocumentId documentIdObj = DocumentId.createDocumentId(documentId);
        StringBuilder pathBuilder = new StringBuilder(TradeMarkModel.CABINET_FOLDER_NAME).append(DocumentId.ID_PATH_SEPARATOR)
                .append(TradeMarkModel.CASE_FOLDER_NAME).append(DocumentId.ID_PATH_SEPARATOR)
                .append(documentIdObj.getDocumentId().substring(TMConstants.ZERO, TMConstants.THREE))
                .append(DocumentId.ID_PATH_SEPARATOR)
                .append(documentIdObj.getDocumentId().substring(TMConstants.THREE, TMConstants.SIX))
                .append(DocumentId.ID_PATH_SEPARATOR).append(documentIdObj.getDocumentId()).append(DocumentId.ID_PATH_SEPARATOR)
                .append(documentIdObj.getFileName());
        return pathBuilder.toString();
    }

    /**
     * Resolve efile document id to path.
     *
     * @param trademarkId
     *            the trademark id
     * @return the string
     */
    private static String resolveEfileDocumentIdToPath(String trademarkId) {
        trademarkId = StringUtils.trimLeadingCharacter(trademarkId, '/');
        EfileDocumentId documentIdObj = EfileDocumentId.createEfileDocId(trademarkId);
        StringBuilder pathBuilder = new StringBuilder(TradeMarkModel.EFILE_DRIVE).append(EfileDocumentId.ID_PATH_SEPARATOR)
                .append(TradeMarkModel.TYPE_EFILE_FOLDER).append(EfileDocumentId.ID_PATH_SEPARATOR)
                .append(documentIdObj.getTrademarkId()).append(EfileDocumentId.ID_PATH_SEPARATOR)
                .append(documentIdObj.getEfileName());
        return pathBuilder.toString();
    }

    /**
     * Resolve document id to path.
     *
     * @param documentId
     *            the document id
     * @return the string
     */
    public static String resolveDocumentIdToPath(String documentId) {
        documentId = StringUtils.trimLeadingCharacter(documentId, '/');
        if (isCasePath(documentId)) {
            return resolveCaseDocumentIdToPath(documentId);
        } else if (is2aPath(documentId)) {
            return resolve2ADocumentIdToPath(documentId);
        } else if (isEfilePath(documentId)) {
            return resolveEfileDocumentIdToPath(documentId);
        } else if (isWebCapturePath(documentId)) {
            return resolveWebCaptureDocumentIdToPath(documentId);
        }
        return documentId;
    }

    /**
     * Checks if is case path.
     *
     * @param path
     *            the path
     * @return true, if is case path
     */
    public static boolean isCasePath(String path) {
        return TradeMarkModel.CASE_FOLDER_NAME.equalsIgnoreCase(path.split("/")[0]);
    }

    /**
     * Checks if is 2a path.
     *
     * @param path
     *            the path
     * @return true, if is 2a path
     */
    public static boolean is2aPath(String path) {
        String subString = "/" + TWO_A + "/";
        return path.toLowerCase().contains(subString.toLowerCase());
    }

    /**
     * Checks if WebCapture path
     *
     * @param path
     * @return
     */
    public static boolean isWebCapturePath(String path) {
        return path.toLowerCase().contains(WEBCAPTURE.toLowerCase());
    }

    /**
     * Checks if is efile path.
     *
     * @param path
     *            the path
     * @return true, if is efile path
     */
    private static boolean isEfilePath(String path) {
        return TradeMarkModel.EFILE_DRIVE.equalsIgnoreCase(path.split("/")[0]);
    }
    
    /**
     * Resolve a Path by converting each element into its display NAME attribute
     * 
     * @param path       Path to convert
     * @param separator  Separator to user between path elements
     * @param prefix     To prepend to the path
     * 
     * @return Path converted using NAME attribute on each element
     */
    public static String getNamePath(NodeService nodeService, Path path, NodeRef rootNode, String separator, String prefix)
    {
       StringBuilder buf = new StringBuilder(128);
       
       // ignore root node check if not passed in
       boolean foundRoot = (rootNode == null);
       
       if (prefix != null)
       {
          buf.append(prefix);
       }
       
       // skip first element as it represents repo root '/'
       for (int i=1; i<path.size(); i++)
       {
          Path.Element element = path.get(i);
          String elementString = null;
          if (element instanceof Path.ChildAssocElement)
          {
             ChildAssociationRef elementRef = ((Path.ChildAssocElement)element).getRef();
             if (elementRef.getParentRef() != null)
             {
                // only append if we've found the root already
                if (foundRoot == true)
                {
                   Object nameProp = nodeService.getProperty(elementRef.getChildRef(), ContentModel.PROP_NAME);
                   if (nameProp != null)
                   {
                      elementString = nameProp.toString();
                   }
                   else
                   {
                      elementString = element.getElementString();
                   }
                }
                
                // either we've found root already or may have now
                // check after as we want to skip the root as it represents the CIFS share name
                foundRoot = (foundRoot || elementRef.getChildRef().equals(rootNode));
             }
          }
          else
          {
             elementString = element.getElementString();
          }
          
          if (elementString != null)
          {
             buf.append(separator);
             buf.append(elementString);
          }
       }
       
       return buf.toString();
    }    

}
