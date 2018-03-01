package alf.integration.service.url.helpers.tmcase;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.LegacyBaseTest;
import alf.integration.service.all.base.ResponseBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.UrlHelper;

/**
 * The Class TmngAlfrescoCreateUrl.
 */
public class CaseCreateUrl extends CentralBase {

    /**
     * Return generic create url.
     *
     * @param ui
     *            the ui
     * @return the string
     */
    public static String returnGenericCreateUrl(UrlInputDto ui) {
        return getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/" + ui.getFileName();
    }

    /**
     * Instantiates a new tmng alfresco create url.
     */
    public CaseCreateUrl() {
        super();
    }

    /**
     * Gets the creates the legacy url.
     *
     * @param ui
     *            the ui
     * @return the creates the legacy url
     */
    public static String getCreateLegacyUrl(UrlInputDto ui) {
        String alfUrl = UrlHelper
                .pointToAlfrescoUrlInsteadOfCmsUrl(ALFRESCO_URL, CODE_LAYER_ON_IP_ADDRESS, BACKEND_ALFRESCO_HOST);
        return alfUrl + CentralBase.urlPrefixCmsRestV1Case + ui.getSerialNumber() + LegacyBaseTest.URL_MIDFIX_CREATE
                + ui.getFileName();
    }

    /**
     * Gets the creates the response put.
     *
     * @param ui
     *            the ui
     * @return the creates the response put
     */
    public static String getCreateResponsePut(UrlInputDto ui) {
        String alfUrl = UrlHelper
                .pointToAlfrescoUrlInsteadOfCmsUrl(ALFRESCO_URL, CODE_LAYER_ON_IP_ADDRESS, BACKEND_ALFRESCO_HOST);
        return alfUrl + CentralBase.urlPrefixCmsRestV1Case + ui.getSerialNumber() + ResponseBaseTest.URL_MIDFIX
                + ui.getFileName();
    }

    /**
     * Efile create url.
     *
     * @param ui
     *            the ui
     * @return the string
     */
    public static String efileCreateUrl(UrlInputDto ui) {
        return ALFRESCO_URL + "/cms/rest/drive/e-file/" + ui.getSerialNumber() + "/" + ui.getFileName();
    }

    public static String getEogTemplateCreateUrl(UrlInputDto ui) {
        return ALFRESCO_URL + "/cms/rest/drive/eogtemplate/" + ui.getSerialNumber() + "/" + ui.getFileName();
    }

}