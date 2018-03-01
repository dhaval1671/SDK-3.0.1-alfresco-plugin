package alf.integration.service.url.helpers.publication;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.dtos.EogUrlInputDto;
import alf.integration.service.dtos.IdmUrlInputDto;

/**
 * The Class TmngAlfrescoCreateUrl.
 */
public class PublicationRetrieveContentUrl extends CentralBase {


    /**
     * Retrieve eog content url.
     *
     * @param ui the ui
     * @return the string
     */
    public static String retrieveEogContentUrl(EogUrlInputDto ui) {
        return ALFRESCO_URL + CentralBase.urlPrefixCmsRestPublication + ui.getSerialNumber() + "/" + ui.getDocType() + "/"
                + ui.getFileName();
    }    

    
    /**
     * Retrieve idm content url.
     *
     * @param ui the ui
     * @return the string
     */
    public static String retrieveIdmContentUrl(IdmUrlInputDto ui) {
        return ALFRESCO_URL + CentralBase.urlPrefixCmsRestPublication + ui.getSerialNumber() + "/" + ui.getDocType() + "/"
                + ui.getFileName();
    }    


}