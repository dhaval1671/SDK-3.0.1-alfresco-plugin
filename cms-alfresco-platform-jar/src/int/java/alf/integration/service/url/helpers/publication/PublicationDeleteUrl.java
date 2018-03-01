package alf.integration.service.url.helpers.publication;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.dtos.IdmUrlInputDto;

/**
 * The Class PublicationDeleteUrl.
 */
public class PublicationDeleteUrl extends CentralBase {



    /**
     * Creates the delete idm url.
     *
     * @param ui
     *          the ui
     * @return the string
     */
    public static String deleteIdmanualUrl(IdmUrlInputDto ui) {
        return ALFRESCO_URL + CentralBase.urlPrefixCmsRestPublication + ui.getSerialNumber() + "/" + ui.getDocType() + "/"
                + ui.getFileName();
    }



}