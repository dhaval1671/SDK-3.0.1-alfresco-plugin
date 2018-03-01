package alf.integration.service.dtos;

import javax.servlet.http.HttpServletRequest;

import org.alfresco.service.namespace.QName;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.servlet.WebScriptServletRuntime;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkLegalProceedingTypes;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;

/**
 * The Class UrlInputDto.
 */
public class LegalProceedingUrlInputDto {

    /** The url. */
    private String url;

    /** The uri. */
    private String uri;

    /** The http method type. */
    private RequestMethod httpMethodType;

    /** The serial number. */
    private String proceedingNumber;

    /** The doc type. */
    protected String docType;

    /** The file name. */
    private String fileName;

    /** The fourth template parameter. */
    private String fourthTemplateParameter;

    /** The version. */
    private String version;
    
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
    public LegalProceedingUrlInputDto() {
    }

    /**
     * Instantiates a new url input dto.
     *
     * @param request
     *            the request
     */
    public LegalProceedingUrlInputDto(WebScriptRequest request) {
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
    public LegalProceedingUrlInputDto(RequestMethod httpMethodType, String uri) {
        this.httpMethodType = httpMethodType;
        this.uri = uri;
        String newUri = uri.replace(TMConstants.REST_PREFIX_URL, "");
        String[] strArray = newUri.split("/");
        if (strArray.length == TMConstants.THREE) {
            // If length is == 3 then user is trying to either create or update
            // or retrieve content.
            this.proceedingNumber = strArray[TMConstants.ZERO];
            this.docType = strArray[TMConstants.ONE];
            this.fileName = strArray[TMConstants.TWO];
        } else if (strArray.length == TMConstants.FOUR) {
            // If template parameters are 4 and 4 value is metadata and method
            // type is get then user trying to retrieve metadata.
            this.proceedingNumber = strArray[TMConstants.ZERO];
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
    public LegalProceedingUrlInputDto(String docType) {
        this.docType = docType;
        this.qname = TradeMarkLegalProceedingTypes.getLegalProceedingQName(docType);
        this.cmsClass = TradeMarkLegalProceedingTypes.getLegalProceedingModelClassType(docType);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public RequestMethod getHttpMethodType() {
        return httpMethodType;
    }

    public void setHttpMethodType(RequestMethod httpMethodType) {
        this.httpMethodType = httpMethodType;
    }

    public String getProceedingNumber() {
        return proceedingNumber;
    }

    public void setProceedingNumber(String proceedingNumber) {
        this.proceedingNumber = proceedingNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFourthTemplateParameter() {
        return fourthTemplateParameter;
    }

    public void setFourthTemplateParameter(String fourthTemplateParameter) {
        this.fourthTemplateParameter = fourthTemplateParameter;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getRestore() {
        return restore;
    }

    public void setRestore(String restore) {
        this.restore = restore;
    }

    public String getRendition() {
        return rendition;
    }

    public void setRendition(String rendition) {
        this.rendition = rendition;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public QName getQname() {
        return qname;
    }

    public void setQname(QName qname) {
        this.qname = qname;
    }

    public Class<? extends AbstractBaseType> getCmsClass() {
        return cmsClass;
    }

    public void setCmsClass(Class<? extends AbstractBaseType> cmsClass) {
        this.cmsClass = cmsClass;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
 
}
