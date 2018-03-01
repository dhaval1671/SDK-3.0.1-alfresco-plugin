package alf.integration.service.dtos;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.extensions.webscripts.servlet.FormData.FormField;

import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class ServiceLayerDto.
 */
public class ServiceLayerDto extends UrlInputDto {

    /** The input stream. */
    private InputStream inputStream;

    /** The string key properties. */
    private Map<String, Serializable> stringKeyProperties;

    /** The qname key properties. */
    private Map<QName, Serializable> qnameKeyProperties;

    /** The form field. */
    private FormField formField;

    /** The web script request. */
    private WebScriptRequest webScriptRequest;

    /** The web script response. */
    private WebScriptResponse webScriptResponse;

    /** The newly created node ref. */
    private NodeRef newlyCreatedNodeRef;

    /** The document to be update node ref. */
    private NodeRef documentToBeUpdateNodeRef;

    /**
     * Instantiates a new service layer dto.
     *
     * @param docType
     *            the doc type
     */
    public ServiceLayerDto(String docType) {
        super.docType = docType;
        super.qname = TradeMarkDocumentTypes.getTradeMarkQName(docType);
        super.cmsClass = TradeMarkDocumentTypes.getCmsModelClassType(docType);
    }

    /**
     * Instantiates a new service layer dto.
     *
     * @param docType
     *            the doc type
     * @param docSubType
     *            the doc sub type
     */
    public ServiceLayerDto(String docType, String docSubType) {
        super.docType = docType;
        super.docSubType = docSubType;
        super.qname = TradeMarkDocumentTypes.getTradeMarkQName(docType);
        super.cmsClass = TradeMarkDocumentTypes.getCmsModelClassType(docType);
    }

    /**
     * Gets the input stream.
     *
     * @return the input stream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * Sets the input stream.
     *
     * @param inputStream
     *            the new input stream
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Gets the string key properties.
     *
     * @return the string key properties
     */
    public Map<String, Serializable> getStringKeyProperties() {
        return stringKeyProperties;
    }

    /**
     * Sets the string key properties.
     *
     * @param stringKeyProperties
     *            the string key properties
     */
    public void setStringKeyProperties(Map<String, Serializable> stringKeyProperties) {
        this.stringKeyProperties = stringKeyProperties;
    }

    /**
     * Gets the qname key properties.
     *
     * @return the qname key properties
     */
    public Map<QName, Serializable> getQnameKeyProperties() {
        return qnameKeyProperties;
    }

    /**
     * Sets the qname key properties.
     *
     * @param qnameKeyProperties
     *            the qname key properties
     */
    public void setQnameKeyProperties(Map<QName, Serializable> qnameKeyProperties) {
        this.qnameKeyProperties = qnameKeyProperties;
    }

    /**
     * Gets the form field.
     *
     * @return the form field
     */
    public FormField getFormField() {
        return formField;
    }

    /**
     * Sets the form field.
     *
     * @param formField
     *            the new form field
     */
    public void setFormField(FormField formField) {
        this.formField = formField;
    }

    /**
     * Gets the web script request.
     *
     * @return the web script request
     */
    public WebScriptRequest getWebScriptRequest() {
        return webScriptRequest;
    }

    /**
     * Sets the web script request.
     *
     * @param webScriptRequest
     *            the new web script request
     */
    public void setWebScriptRequest(WebScriptRequest webScriptRequest) {
        this.webScriptRequest = webScriptRequest;
    }

    /**
     * Gets the web script response.
     *
     * @return the web script response
     */
    public WebScriptResponse getWebScriptResponse() {
        return webScriptResponse;
    }

    /**
     * Sets the web script response.
     *
     * @param webScriptResponse
     *            the new web script response
     */
    public void setWebScriptResponse(WebScriptResponse webScriptResponse) {
        this.webScriptResponse = webScriptResponse;
    }

    /**
     * Gets the newly created node ref.
     *
     * @return the newly created node ref
     */
    public NodeRef getNewlyCreatedNodeRef() {
        return newlyCreatedNodeRef;
    }

    /**
     * Sets the newly created node ref.
     *
     * @param newlyCreatedNodeRef
     *            the new newly created node ref
     */
    public void setNewlyCreatedNodeRef(NodeRef newlyCreatedNodeRef) {
        this.newlyCreatedNodeRef = newlyCreatedNodeRef;
    }

    /**
     * Gets the document to be update node ref.
     *
     * @return the document to be update node ref
     */
    public NodeRef getDocumentToBeUpdateNodeRef() {
        return documentToBeUpdateNodeRef;
    }

    /**
     * Sets the document to be update node ref.
     *
     * @param documentToBeUpdateNodeRef
     *            the new document to be update node ref
     */
    public void setDocumentToBeUpdateNodeRef(NodeRef documentToBeUpdateNodeRef) {
        this.documentToBeUpdateNodeRef = documentToBeUpdateNodeRef;
    }

}
