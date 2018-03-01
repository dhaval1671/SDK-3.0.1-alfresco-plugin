package alf.integration.service.url.helpers.madrid;

import org.apache.commons.lang.StringUtils;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.dtos.MadridUrlInputDto;

/**
 * The Class TmngAlfrescoRetrieveMetadataUrl.
 */
public class MadridRetrieveMetadataUrl extends CentralBase {

    /** The Constant URL_POSTFIX. */
    public static final String URL_POSTFIX = "/metadata";      
    
    public static String retrieveMadridMetadata(MadridUrlInputDto ui) {
        StringBuffer sb = new StringBuffer(getMadridUrlPrefix());
        sb.append(ui.getSerialNumber());
        sb.append("/");
        sb.append(ui.getDocType());
        sb.append("/");
        sb.append(ui.getFileName());
        sb.append(MadridRetrieveMetadataUrl.URL_POSTFIX);
        if(StringUtils.isNotBlank(ui.getVersion())){
            sb.append("?");
            sb.append("version");
            sb.append("=");
            sb.append(ui.getVersion());            
        }
        return sb.toString();
    }   
    
    /**  
     * @Title: getMadridUrlPrefix  
     * @Description:   
     * @return  
     * @return String   
     * @throws  
     */ 
    private static String getMadridUrlPrefix() {
        String urlPrefix =  ALFRESCO_URL +  urlPrefixCmsRestMadridIB; 
        return urlPrefix;
    }

    /**
     * Instantiates a new tmng alfresco retrieve metadata url.
     */
    public MadridRetrieveMetadataUrl() {
        super();
    }


    
}