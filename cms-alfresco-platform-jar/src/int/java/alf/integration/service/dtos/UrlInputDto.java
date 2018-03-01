package alf.integration.service.dtos;

import javax.servlet.http.HttpServletRequest;

import org.alfresco.service.namespace.QName;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.servlet.WebScriptServletRuntime;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;

/**
 * The Class UrlInputDto.
 */
public class UrlInputDto {

    /** The url. */
    private String url;

    /** The uri. */
    private String uri;

    /** The http method type. */
    private RequestMethod httpMethodType;

    /** The serial number. */
    private String serialNumber;

    /** The doc type. */
    protected String docType;

    /** The doc sub type. */
    protected String docSubType;

    /** The file name. */
    private String fileName;

    /** The fourth template parameter. */
    private String fourthTemplateParameter;

    private String filterKey;
    private String filterValue;
    
    /** The version. */
    private String version;
    
    /** The access level. */
    private String accessLevel;    

    /** The flavour. */
    private String flavour;

    /** The restore. */
    private String restore;

    /** The qname. */
    protected QName qname;

    /** The cms class. */
    protected Class<? extends AbstractBaseType> cmsClass;

    /** The rendition. */
    private String rendition;

    /**
     * Instantiates a new url input dto.
     */
    public UrlInputDto() {
    }

    /**
     * Instantiates a new url input dto.
     *
     * @param request
     *            the request
     */
    public UrlInputDto(WebScriptRequest request) {
        HttpServletRequest hsr = WebScriptServletRuntime.getHttpServletRequest(request);
        this.uri = hsr.getRequestURI();
        this.httpMethodType = RequestMethod.valueOf(hsr.getMethod());
    }

    /**
     * Instantiates a new url input dto.
     *
     * @param httpMethodType
     *            the http method type
     * @param uri
     *            the uri
     */
    public UrlInputDto(RequestMethod httpMethodType, String uri) {
        this.httpMethodType = httpMethodType;
        this.uri = uri;
        String newUri = uri.replace("/cms/rest/case/", "");
        String[] strArray = newUri.split("/");
        if (strArray.length == TMConstants.THREE) {
            // If length is == 3 then user is trying to either create or update
            // or retrieve content.
            this.serialNumber = strArray[TMConstants.ZERO];
            this.docType = strArray[TMConstants.ONE];
            this.fileName = strArray[TMConstants.TWO];
        } else if (strArray.length == TMConstants.FOUR) {
            // If template parameters are 4 and 4 value is metadata and method
            // type is get then user trying to retrieve metadata.
            this.serialNumber = strArray[TMConstants.ZERO];
            this.docType = strArray[TMConstants.ONE];
            this.fileName = strArray[TMConstants.TWO];
            this.fourthTemplateParameter = strArray[TMConstants.THREE];
        }
    }

    /**
     * Instantiates a new url input dto.
     *
     * @param docType
     *            the doc type
     */
    public UrlInputDto(String docType) {
        this.docType = docType;
        this.qname = TradeMarkDocumentTypes.getTradeMarkQName(docType);
        this.cmsClass = TradeMarkDocumentTypes.getCmsModelClassType(docType);
    }

    /**
     * Instantiates a new url input dto.
     *
     * @param docType
     *            the doc type
     * @param docSubType
     *            the doc sub type
     */
    public UrlInputDto(String docType, String docSubType) {
        this.docType = docType;
        this.docSubType = docSubType;
        this.qname = TradeMarkDocumentTypes.getTradeMarkQName(docType);
        this.cmsClass = TradeMarkDocumentTypes.getCmsModelClassType(docSubType);
    }

    /**
     * Gets the serial number.
     *
     * @return the serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the serial number.
     *
     * @param serialNumber
     *            the new serial number
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Gets the doc type.
     *
     * @return the doc type
     */
    public String getDocType() {
        return docType;
    }

    /**
     * Sets the doc type.
     *
     * @param docType
     *            the new doc type
     */
    public void setDocType(String docType) {
        this.docType = docType;
    }

    /**
     * Gets the file name.
     *
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name.
     *
     * @param fileName
     *            the new file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the new version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets the flavour.
     *
     * @return the flavour
     */
    public String getFlavour() {
        return flavour;
    }

    /**
     * Sets the flavour.
     *
     * @param flavour
     *            the new flavour
     */
    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    /**
     * Gets the restore.
     *
     * @return the restore
     */
    public String getRestore() {
        return restore;
    }

    /**
     * Sets the restore.
     *
     * @param restore
     *            the new restore
     */
    public void setRestore(String restore) {
        this.restore = restore;
    }

    /**
     * Gets the type qname.
     *
     * @return the type qname
     */
    public QName getTypeQname() {
        return qname;
    }

    /**
     * Gets the cms class.
     *
     * @return the cms class
     */
    public Class<? extends AbstractBaseType> getCmsClass() {
        return cmsClass;
    }

    /**
     * Gets the doc sub type.
     *
     * @return the doc sub type
     */
    public String getDocSubType() {
        return docSubType;
    }

    /**
     * Sets the doc sub type.
     *
     * @param docSubType
     *            the new doc sub type
     */
    public void setDocSubType(String docSubType) {
        this.docSubType = docSubType;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     *
     * @param url
     *            the new url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the uri.
     *
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the uri.
     *
     * @param uri
     *            the new uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Gets the http method type.
     *
     * @return the http method type
     */
    public RequestMethod getHttpMethodType() {
        return httpMethodType;
    }

    /**
     * Sets the http method type.
     *
     * @param httpMethodType
     *            the new http method type
     */
    public void setHttpMethodType(RequestMethod httpMethodType) {
        this.httpMethodType = httpMethodType;
    }

    /**
     * Gets the fourth template parameter.
     *
     * @return the fourth template parameter
     */
    public String getFourthTemplateParameter() {
        return fourthTemplateParameter;
    }

    /**
     * Sets the fourth template parameter.
     *
     * @param fourthTemplateParameter
     *            the new fourth template parameter
     */
    public void setFourthTemplateParameter(String fourthTemplateParameter) {
        this.fourthTemplateParameter = fourthTemplateParameter;
    }

    /**
     * Gets the rendition.
     *
     * @return the rendition
     */
    public String getRendition() {
        return rendition;
    }

    /**
     * Sets the rendition.
     *
     * @param rendition
     *            the new rendition
     */
    public void setRendition(String rendition) {
        this.rendition = rendition;
    }
    
    /**
     * Gets the access level.
     *
     * @return the access level
     */
    public String getAccessLevel() {
        return accessLevel;
    }

    /**
     * Sets the access level.
     *
     * @param accessLevel the new access level
     */
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }
    
    

}
