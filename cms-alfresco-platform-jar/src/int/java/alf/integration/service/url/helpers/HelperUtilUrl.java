package alf.integration.service.url.helpers;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.dtos.UrlInputDto;

/**
 * The Class TmngAlfrescoOtherUrl.
 */
public class HelperUtilUrl extends CentralBase {

    public static String bulkValidateTopLevelFolders(UrlInputDto ui) {
        String WEBSCRIPT_EXT = "/cms/validate/" + "bulkValidateTopLevelFolders" + "/"+ "a" + "/" + "b" + "/" + "c";
        return ALFRESCO_URL + WEBSCRIPT_EXT;
    }

}
