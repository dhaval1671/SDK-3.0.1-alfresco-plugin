package alf.integration.service.url.helpers.madrid;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.dtos.MadridUrlInputDto;

/**
 * The Class TmngAlfrescoCreateUrl.
 */
public class MadridCreateUrl extends CentralBase {

    public static String createMadridUrl(MadridUrlInputDto ui) {
        //PUT http://localhost:8080/alfresco/s/cms/rest/publication/20150704/eog/firstEOG.pdf
        return ALFRESCO_URL + CentralBase.urlPrefixCmsRestMadridIB + ui.getSerialNumber() + "/" + ui.getDocType() + "/" + ui.getFileName();
    }


}