package alf.integration.service.url.helpers.madrid;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.dtos.MadridUrlInputDto;

/**
 * The Class TmngAlfrescoCreateUrl.
 */
public class MadridRetrieveContentUrl extends CentralBase {


    /**
     * Retrieve eog content url.
     *
     * @param ui the ui
     * @return the string
     */
    public static String retrieveMadridContentUrl(MadridUrlInputDto ui) {
        return ALFRESCO_URL + CentralBase.urlPrefixCmsRestMadridIB + ui.getSerialNumber() + "/" + ui.getDocType() + "/"
                + ui.getFileName();
    }    

    
}