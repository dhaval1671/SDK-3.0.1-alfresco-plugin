package alf.integration.service.url.helpers.tmcase;

import org.apache.commons.lang.StringUtils;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ResponseBaseTest;
import alf.integration.service.dtos.UrlInputDto;

/**
 * The Class TmngAlfrescoRetrieveMetadataUrl.
 */
public class CaseRetrieveMetadataUrl extends CentralBase {

    /** The Constant URL_POSTFIX. */
    public static final String URL_POSTFIX = "/metadata";      
    
    /**
     * This method retrieves the generic mark metadata url.
     *
     * @param ui the ui
     * @return the string
     */
    public static String retrieveGenericMarkMetadataUrl(UrlInputDto ui) {
        if (StringUtils.isBlank(ui.getVersion())) {
            return retrieveDocumentMetadata(ui);
        } else {
            return getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/"
                    + ui.getFileName() + CaseRetrieveMetadataUrl.URL_POSTFIX + MarkBaseTest.URL_VERSION_PARAM + ui.getVersion();
        }

    }
    public static String retrieveDocumentMetadata(UrlInputDto ui) {
        StringBuffer sb = new StringBuffer(getUrlPrefix());
        sb.append(ui.getSerialNumber());
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
    
    /**
     * Gets the redacted retrieve metadata.
     *
     * @param ui
     *            the ui
     * @return the redacted retrieve metadata
     */
    public static String getRedactedRetrieveMetadata(UrlInputDto ui) {
        return getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/"
                + ui.getFileName() + ResponseBaseTest.URL_POSTFIX_REDACTION + CaseRetrieveMetadataUrl.URL_POSTFIX;
    }

    /**
     * Gets the redacted retrieve metadata flavour original.
     *
     * @param ui
     *            the ui
     * @return the redacted retrieve metadata flavour original
     */
    public static String getRedactedRetrieveMetadataFlavourOriginal(UrlInputDto ui) {
        //return getUrlPrefix() + ui.getSerialNumber() + "/" + ui.getDocType() + "/" + ui.getFileName() + ResponseBaseTest.URL_POSTFIX_ORIGINAL + CaseRetrieveMetadataUrl.URL_POSTFIX;
        StringBuffer sb = new StringBuffer(getUrlPrefix());
        sb.append(ui.getSerialNumber());
        sb.append("/");
        sb.append(ui.getDocType());
        sb.append("/");
        sb.append(ui.getFileName());
        sb.append(ResponseBaseTest.URL_POSTFIX_ORIGINAL);
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

    /**
     * Instantiates a new tmng alfresco retrieve metadata url.
     */
    public CaseRetrieveMetadataUrl() {
        super();
    }


    
}