package alf.integration.service.url.helpers.tmcase;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.ResponseBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.UrlHelper;

/**
 * The Class TmngAlfrescoUpdateUrl.
 */
public class CaseUpdateUrl extends CentralBase {

    /**
     * Update generic mark url.
     *
     * @param ui the ui
     * @return the string
     */
    public static String genericUpdateContentUrl(UrlInputDto ui) {
        return getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/" + ui.getFileName();
    }  
    
    public static String genericUpdateMetadataUrl(UrlInputDto ui) {
        return getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/" + ui.getFileName() + "/" + "metadata";
    }     

    /**
     * Gets the update response post.
     *
     * @param ui
     *            the ui
     * @return the update response post
     */
    public static String getUpdateResponsePost(UrlInputDto ui) {
        String alfUrl = UrlHelper.pointToAlfrescoUrlInsteadOfCmsUrl(ALFRESCO_URL, CODE_LAYER_ON_IP_ADDRESS, BACKEND_ALFRESCO_HOST);
        return alfUrl + CentralBase.urlPrefixCmsRestV1Case + ui.getSerialNumber() + ResponseBaseTest.URL_MIDFIX
                + ui.getFileName();
    }

    /**
     * Update evidence url.
     *
     * @param ui
     *            the ui
     * @return the string
     */
    public static String updateEvidenceMetadataUrl(UrlInputDto ui) {
        return getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/"
                + ui.getFileName() + "/metadata";
    }

    public static String updateSpecimenUrl(UrlInputDto ui) {
        return getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/"
                + ui.getFileName();
    }    
    
    /**
     * Instantiates a new tmng alfresco update url.
     */
    public CaseUpdateUrl() {
        super();
    }

}