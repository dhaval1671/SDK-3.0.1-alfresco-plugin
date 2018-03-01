package alf.integration.service.url.helpers.healthstatus;

import alf.integration.service.all.base.CentralBase;

/**
 * The Class TmngAlfrescoCreateUrl.
 */
public class AlfrescoHealthStatusUrl extends CentralBase {

    /**
     *
     * @param ui the ui
     * @return the string
     */
    public static String alfrescoHello() {
        return ALFRESCO_URL + "/health/hello";
    }    

    public static String alfrescoHealthStatus() {
        return ALFRESCO_URL + "/health/status";
    }      
    
}