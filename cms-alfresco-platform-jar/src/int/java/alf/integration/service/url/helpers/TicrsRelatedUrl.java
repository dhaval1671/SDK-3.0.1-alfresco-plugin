package alf.integration.service.url.helpers;

import org.apache.commons.lang.StringUtils;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.dtos.UrlInputDto;

/**
 * The Class TmngAlfrescoOtherUrl.
 */
public class TicrsRelatedUrl extends CentralBase {

    
    
    public static String ticrsRetrieveCaseCountUrl(UrlInputDto ui) {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestCase + ui.getSerialNumber() + "/" + "metadata";
        return ALFRESCO_URL + WEBSCRIPT_EXT;
    }
    
    public static String ticrsRetrieveCaseFileNames(UrlInputDto ui) {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestCase + "ticrs/filenames/" + ui.getSerialNumber();
        return ALFRESCO_URL + WEBSCRIPT_EXT;
    }    
    
    /**  
     * @Title: ticrsAdminDeleteUrl  
     * @Description:   
     * @param urlInput
     * @return  
     * @return String   
     * @throws  
     */ 
    public static String ticrsAdminDeleteUrl(UrlInputDto ui) {
        String url = null;
        if(StringUtils.isNotBlank(ui.getDocType())){
            url = ALFRESCO_URL + CentralBase.urlPrefixCmsRestV1AdminCase + ui.getSerialNumber() + "/" + ui.getFileName() + "?" + "docType=" + ui.getDocType();
        }else{
            url = ALFRESCO_URL + CentralBase.urlPrefixCmsRestV1AdminCase + ui.getSerialNumber() + "/" + ui.getFileName();
        }
        return url;
    }

    public static String retrieveContentTicrsDocumentTifJpegUrl(UrlInputDto ui) {
        ///cms/rest/v1/case/{id}/{docType}/{fileName}/attachment?version={version-number}
        String url = null;
        if(StringUtils.isNotBlank(ui.getVersion())){
            url = getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/" + ui.getFileName() + "/attachment?version="+ ui.getVersion();
        }else{
            url = getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/" + ui.getFileName() + "/attachment";
        }
        return url;        
    }      

}
