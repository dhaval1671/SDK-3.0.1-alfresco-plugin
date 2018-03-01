package alf.integration.service.url.helpers.tmcase;

import org.apache.commons.lang.StringUtils;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.dtos.LegalProceedingUrlInputDto;

/**
 * The Class TmngAlfrescoCreateUrl.
 */
public class LegalProceedingUrl extends CentralBase {

    /**
     * Instantiates a new tmng alfresco create url.
     */
    public LegalProceedingUrl() {
        super();
    }

    /**
     * Return generic create url.
     *
     * @param ui
     *            the ui
     * @return the string
     */
    public static String returnGenericCreateUrl(LegalProceedingUrlInputDto ui) {
        return getLegalProceedingUrlPrefix()  + ui.getProceedingNumber() + "/" + ui.getDocType() + "/" + ui.getFileName();
    }    
    
    public static String retrieveContentGenericUrl(LegalProceedingUrlInputDto ui) {
        StringBuffer sb = new StringBuffer(getLegalProceedingUrlPrefix());
        sb.append(ui.getProceedingNumber());
        sb.append("/");
        sb.append(ui.getDocType());
        sb.append("/");
        sb.append(ui.getFileName());
        if(StringUtils.isNotBlank(ui.getVersion())){
            sb.append("?");
            sb.append("version");
            sb.append("=");
            sb.append(ui.getVersion());            
        }
        if(StringUtils.isNotBlank(ui.getAccessLevel())){
            if(StringUtils.isNotBlank(ui.getVersion())){
                sb.append("&");
            }else{
                sb.append("?");
            }
            sb.append("accessLevel");
            sb.append("=");
            sb.append(ui.getAccessLevel());
        }
        return sb.toString();
    }

    private static String getLegalProceedingUrlPrefix() {
        return ALFRESCO_URL + CentralBase.urlPrefixLegalProceeding;
    }

    public static String retrieveDocumentMetadata(LegalProceedingUrlInputDto ui) {
        StringBuffer sb = new StringBuffer(getLegalProceedingUrlPrefix());
        sb.append(ui.getProceedingNumber());
        sb.append("/");
        sb.append(ui.getDocType());
        sb.append("/");
        sb.append(ui.getFileName());
        sb.append(CaseRetrieveMetadataUrl.URL_POSTFIX);
        if(StringUtils.isNotBlank(ui.getVersion())){
            sb.append("?");
            sb.append("version");
            sb.append("=");
            sb.append(ui.getVersion());            
        }
        if(StringUtils.isNotBlank(ui.getAccessLevel())){
            if(StringUtils.isNotBlank(ui.getVersion())){
                sb.append("&");
            }else{
                sb.append("?");
            }
            sb.append("accessLevel");
            sb.append("=");
            sb.append(ui.getAccessLevel());
        }
        return sb.toString();
    }
    
    public static String retrieveAllProceedingNumberDocMetadata(LegalProceedingUrlInputDto ui) {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixLegalProceeding + ui.getProceedingNumber() + CaseOtherUrl.URL_MIDFIX + CaseOtherUrl.URL_POSTFIX;
        return ALFRESCO_URL + WEBSCRIPT_EXT;
    }    

}