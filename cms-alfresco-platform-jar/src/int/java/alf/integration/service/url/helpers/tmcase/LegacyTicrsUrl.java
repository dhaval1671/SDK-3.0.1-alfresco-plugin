package alf.integration.service.url.helpers.tmcase;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.dtos.LegacyTicrsDto;

/**
 * The Class TmngAlfrescoOtherUrl.
 */
public class LegacyTicrsUrl extends CentralBase {

    /**
     * GET http://{hostName}/alfresco/s
     *  /cms/rest/case/ticrs/{docType}/{versionFolder}/{fileName}
     * @param ltd
     * @return
     */
    public static String retrieveLegacyTicrsContentUrl(LegacyTicrsDto ltd) {
        return ALFRESCO_URL + "/cms/rest/case/ticrs/" + ltd.getDocType() + "/"+ ltd.getVersionFolder() + "/"+ ltd.getFileName();
    }    

}
