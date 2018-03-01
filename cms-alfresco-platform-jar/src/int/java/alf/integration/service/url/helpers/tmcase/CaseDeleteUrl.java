package alf.integration.service.url.helpers.tmcase;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.dtos.UrlInputDto;

/**
 * The Class TmngAlfrescoCreateUrl.
 */
public class CaseDeleteUrl extends CentralBase {

    public static String genericDeleteUrl(UrlInputDto ui){
        return getUrlPrefix() + ui.getSerialNumber()  + "/" + ui.getDocType() + "/" + ui.getFileName();
    } 

}