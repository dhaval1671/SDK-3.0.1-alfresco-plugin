package alf.integration.service.url.helpers.tmcase;

import org.apache.commons.lang.StringUtils;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ResponseBaseTest;
import alf.integration.service.dtos.UrlInputDto;

/**
 * The Class TmngAlfrescoRetrieveContentUrl.
 */
public class CaseRetrieveContentUrl extends CentralBase {

    public static String retrieveContentGenericUrl(UrlInputDto ui) {
        StringBuffer sb = new StringBuffer(getUrlPrefix());
        sb.append(ui.getSerialNumber());
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
    
    public static String retrieveContentAsAttachmentUrl(UrlInputDto ui) {
        StringBuffer sb = new StringBuffer(getUrlPrefix());
        sb.append(ui.getSerialNumber());
        sb.append("/");
        sb.append(ui.getDocType());
        sb.append("/");
        sb.append(ui.getFileName());
        sb.append("/attachment");
        if(StringUtils.isNotBlank(ui.getVersion())){
            sb.append("?");
            sb.append("version");
            sb.append("=");
            sb.append(ui.getVersion());            
        }
        return sb.toString();
    }    
    
    /**
     * Instantiates a new tmng alfresco retrieve content url.
     */
    public CaseRetrieveContentUrl() {
        super();
    }

    /**
     * Returns the retreive generic mark content url based on the given
     * parameters.
     *
     * @param ui the ui
     * @return the string
     */
    public static String retrieveGenericMarkContentUrl(UrlInputDto ui) {
        if (StringUtils.isBlank(ui.getVersion())) {
            return retrieveContentGenericUrl(ui);
        } else {
            return getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/"
                    + ui.getFileName() + MarkBaseTest.URL_VERSION_PARAM + ui.getVersion();
        }

    }
    
    /**
     * Retrieve response content.
     *
     * @param ui
     *            the ui
     * @return the string
     */
    public static String retrieveResponseContent(UrlInputDto ui) {
        return retrieveContentGenericUrl(ui);
    }

    /**
     * Gets the redacted retrieve content.
     *
     * @param ui
     *            the ui
     * @return the redacted retrieve content
     */
    public static String getRedactedRetrieveContent(UrlInputDto ui) {
        return getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/"
                + ui.getFileName() + ResponseBaseTest.URL_POSTFIX_REDACTION;
    }

    /**
     * Gets the redacted retrieve content flavour original.
     *
     * @param ui
     *            the ui
     * @return the redacted retrieve content flavour original
     */
    public static String getRedactedRetrieveContentFlavourOriginal(UrlInputDto ui) {
        //return getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/"+ ui.getFileName() + ResponseBaseTest.URL_POSTFIX_ORIGINAL;
        StringBuffer sb = new StringBuffer(getUrlPrefix());
        sb.append(ui.getSerialNumber());
        sb.append("/");
        sb.append(ui.getDocType());
        sb.append("/");
        sb.append(ui.getFileName());
        sb.append(ResponseBaseTest.URL_POSTFIX_ORIGINAL);
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

}