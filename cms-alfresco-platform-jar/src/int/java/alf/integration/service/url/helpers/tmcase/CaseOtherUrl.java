package alf.integration.service.url.helpers.tmcase;

import org.apache.commons.lang.StringUtils;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ResponseBaseTest;
import alf.integration.service.all.base.WebcaptureBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.UrlHelper;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class TmngAlfrescoOtherUrl.
 */
public class CaseOtherUrl extends CentralBase {

    /** The Constant URL_POSTFIX. */
    public static final String URL_POSTFIX = "/metadata";

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/documents";

    /**
     * Creates the or update redact post.
     *
     * @param ui
     *            the ui
     * @return the string
     */
    public static String createOrUpdateRedactPost(UrlInputDto ui) {
        String alfUrl = UrlHelper.pointToAlfrescoUrlInsteadOfCmsUrl(ALFRESCO_URL, CODE_LAYER_ON_IP_ADDRESS, BACKEND_ALFRESCO_HOST);
        return alfUrl + CentralBase.urlPrefixCmsRestV1Case + ui.getSerialNumber() + "/" + ui.getDocType() + "/"
                + ui.getFileName() + ResponseBaseTest.URL_POSTFIX_REDACTION;
    }

    /**
     * Delete restore redacted to original.
     *
     * @param ui
     *            the ui
     * @return the string
     */
    public static String deleteRestoreRedactedToOriginal(UrlInputDto ui) {
        return getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/"
                + ui.getFileName() + ResponseBaseTest.URL_POSTFIX_REDACTION;
    }

    /**
     * Efile delete url.
     *
     * @param ui
     *            the ui
     * @return the string
     */
    public static String efileDeleteUrl(UrlInputDto ui) {
        return ALFRESCO_URL + "/cms/rest/drive/e-file/" + ui.getSerialNumber() + "/" + ui.getFileName();
    }

    /**
     * Efile copy multiple efiles to multiple serial numbers url.
     *
     * @param urlInput
     *            the url input
     * @return the string
     */
    public static String efileCopyMultipleEfilesToMultipleSerialNumbersUrl(UrlInputDto urlInput) {
        return ALFRESCO_URL + "/cms/rest/drive/e-file/submission";
    }

    public static String retrieveAllMarksVersionedMetadataUrl(UrlInputDto ui) {
        return ALFRESCO_URL + CentralBase.urlPrefixCmsRestCase + ui.getSerialNumber() + "/"+ TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName() + "s"+ "/versions/metadata";
    }    

    /**
     * Gets the retrieve image mark rendition url.
     *
     * @param ui
     *            the ui
     * @return the retrieve image mark rendition url
     */
    public static String getRetrieveImageMarkRenditionUrl(UrlInputDto ui) {
        String returnUrl = null;
        if (StringUtils.isNotBlank(ui.getVersion())) {
            returnUrl = CentralBase.urlPrefixCmsRestCase + ui.getSerialNumber() + "/"+ TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName() + "/" + ui.getFileName() + "/rendition"
                    + "/" + ui.getRendition() + MarkBaseTest.URL_VERSION_PARAM + ui.getVersion();
        } else {
            returnUrl = CentralBase.urlPrefixCmsRestCase + ui.getSerialNumber() + "/"+ TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName() + "/" + ui.getFileName() + "/rendition"
                    + "/" + ui.getRendition();
        }
        return ALFRESCO_URL + returnUrl;
    }

    // Case APIs
    /**
     * Retrieve case document metadata.
     *
     * @param ui
     *            the ui
     * @return the string
     */
    public static String retrieveCaseDocumentMetadata(UrlInputDto ui) {
        String webscriptExt = CentralBase.urlPrefixCmsRestCase + ui.getSerialNumber() + URL_MIDFIX + URL_POSTFIX;
        return ALFRESCO_URL + webscriptExt;
    }

    /**
     * Search at top level case folder.
     *
     * @param urlInput            the url input
     * @return String
     * @Title: searchAtTopLevelCaseFolder
     * @Description: 
     */
    public static String searchAtTopLevelCaseFolder(UrlInputDto urlInput) {
        return ALFRESCO_URL + "/cms/rest/cases/" + "search/content";
    }

    // Evidence APIs
    /**
     * Bulk metadata update to evidence type.
     *
     * @param ui            the ui
     * @return String
     * @Title: bulkMetadataUpdateToEvidenceType
     * @Description: 
     */
    public static String bulkMetadataUpdateToEvidenceType(UrlInputDto ui) {
        return ALFRESCO_URL + CentralBase.urlPrefixCmsRestCase + ui.getSerialNumber() + "/evidences";
    }

    /**
     * Copy evidence fr src to trgt url.
     *
     * @param ui the ui
     * @return String
     * @Title: copyEvidenceFrSrcToTrgtUrl
     * @Description: 
     */
    public static String copyEvidenceFrSrcToTrgtUrl(UrlInputDto ui) {
        return ALFRESCO_URL + CentralBase.urlPrefixCmsRestCase + ui.getSerialNumber() + "/evidences/" + "copy";
    }
    
    public static String postDeleteEvidenceUrl(UrlInputDto ui){
        return ALFRESCO_URL + CentralBase.urlPrefixCmsRestCase + ui.getSerialNumber() + "/evidences/clear";
    }    
    
    public static String deleteAttachmentUrl(UrlInputDto ui){
        return getUrlPrefix() + ui.getSerialNumber()  + "/" + ui.getDocType() + "/"
                + ui.getFileName();
    }
    
    public static String genericRetrieveVersionsUrl(UrlInputDto ui){
        return ALFRESCO_URL + CentralBase.urlPrefixCmsRestV1Case + ui.getSerialNumber() + "/" + ui.getDocType() + "/" + ui.getFileName() + "/versions?accessLevel=" + ui.getAccessLevel();
    }
    
    public static String getFullPath(String webcapture, String userid, String path) {
        return  "/" + webcapture + "/" + userid + "/" + path;
    }    
    
    public static String createWebcaptureUrl(String fullPath){
        return ALFRESCO_URL + WebcaptureBaseTest.EVI_BANK_CMS_REST_PREFIX + "/libraries"+ fullPath;
    }      
    
    public static String deleteWebcaptureUrl(String fullPath){
        return ALFRESCO_URL + WebcaptureBaseTest.EVI_BANK_CMS_REST_PREFIX + "/libraries"+ fullPath;
    } 
    
    public static String renameWebcaptureUrl(String fullPath){
        return ALFRESCO_URL + WebcaptureBaseTest.EVI_BANK_CMS_REST_PREFIX + "/libraries"+ fullPath;
    }     
    

}
