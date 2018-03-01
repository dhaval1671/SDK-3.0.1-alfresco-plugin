package alf.integration.service.url.helpers.publication;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.dtos.EogUrlInputDto;
import alf.integration.service.dtos.IdmUrlInputDto;

/**
 * The Class TmngAlfrescoCreateUrl.
 */
public class PublicationCreateUrl extends CentralBase {

    /**
     * Creates the eog url.
     *
     * @param ui
     *            the ui
     * @return the string
     */
    public static String createEogUrl(EogUrlInputDto ui) {
        //PUT http://localhost:8080/alfresco/s/cms/rest/publication/20150704/eog/firstEOG.pdf
        return ALFRESCO_URL + CentralBase.urlPrefixCmsRestPublication + ui.getSerialNumber() + "/" + ui.getDocType() + "/" + ui.getFileName();
    }

    /**
     * Creates the idm url.
     *
     * @param ui
     *          the ui
     * @return the string
     */
    public static String createIdmanualUrl(IdmUrlInputDto ui) {
        return ALFRESCO_URL + CentralBase.urlPrefixCmsRestPublication + ui.getSerialNumber() + "/" + ui.getDocType() + "/" + ui.getFileName();
    }



}