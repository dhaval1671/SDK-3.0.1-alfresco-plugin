package gov.uspto.trademark.cms.repo.webscripts.cases.search;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The Class Metadata.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ Metadata.DOCUMENT_NAME, Metadata.DOCUMENT_ALIAS, Metadata.DOC_CODE, Metadata.LEGACY_CATEGORY,
        Metadata.ACCESS_LEVEL, Metadata.MAIL_DATE, Metadata.MIME_TYPE })
public class Metadata {

    /** The Constant MIME_TYPE. */
    static final String MIME_TYPE = "mimeType";

    /** The Constant MAIL_DATE. */
    static final String MAIL_DATE = "mailDate";

    /** The Constant ACCESS_LEVEL. */
    static final String ACCESS_LEVEL = "accessLevel";

    /** The Constant LEGACY_CATEGORY. */
    static final String LEGACY_CATEGORY = "legacyCategory";

    /** The Constant DOC_CODE. */
    static final String DOC_CODE = "docCode";

    /** The Constant DOCUMENT_ALIAS. */
    static final String DOCUMENT_ALIAS = "documentAlias";

    /** The Constant DOCUMENT_NAME. */
    static final String DOCUMENT_NAME = "documentName";

    /** The Constant DOCUMENT_TYPE. */
    static final String DOCUMENT_TYPE = "documentType";

    /** The document type. */
    @JsonProperty(DOCUMENT_TYPE)
    private String documentType;

    /** The document name. */
    @JsonProperty(DOCUMENT_NAME)
    private String documentName;

    /** The document alias. */
    @JsonProperty(DOCUMENT_ALIAS)
    private String documentAlias;

    /** The doc code. */
    @JsonProperty(DOC_CODE)
    private String docCode;

    /** The legacy category. */
    @JsonProperty(LEGACY_CATEGORY)
    private String legacyCategory;

    /** The access level. */
    @JsonProperty(ACCESS_LEVEL)
    private String accessLevel;

    /** The mail date. */
    @JsonProperty(MAIL_DATE)
    private Date mailDate;

    /** The mime type. */
    @JsonProperty(MIME_TYPE)
    private String mimeType;

    /**
     * Gets the document type.
     *
     * @return the document type
     */
    @JsonProperty(DOCUMENT_TYPE)
    public String getDocumentType() {
        return documentType;
    }

    /**
     * Sets the document type.
     *
     * @param documentType
     *            the new document type
     */
    @JsonProperty(DOCUMENT_TYPE)
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * Gets the document name.
     *
     * @return The documentName
     */
    @JsonProperty(DOCUMENT_NAME)
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Sets the document name.
     *
     * @param documentName
     *            The documentName
     */
    @JsonProperty(DOCUMENT_NAME)
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /**
     * Gets the document alias.
     *
     * @return The documentAlias
     */
    @JsonProperty(DOCUMENT_ALIAS)
    public String getDocumentAlias() {
        return documentAlias;
    }

    /**
     * Sets the document alias.
     *
     * @param documentAlias
     *            The documentAlias
     */
    @JsonProperty(DOCUMENT_ALIAS)
    public void setDocumentAlias(String documentAlias) {
        this.documentAlias = documentAlias;
    }

    /**
     * Gets the doc code.
     *
     * @return The docCode
     */
    @JsonProperty(DOC_CODE)
    public String getDocCode() {
        return docCode;
    }

    /**
     * Sets the doc code.
     *
     * @param docCode
     *            The docCode
     */
    @JsonProperty(DOC_CODE)
    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    /**
     * Gets the legacy category.
     *
     * @return The legacyCategory
     */
    @JsonProperty(LEGACY_CATEGORY)
    public String getLegacyCategory() {
        return legacyCategory;
    }

    /**
     * Sets the legacy category.
     *
     * @param legacyCategory
     *            The legacyCategory
     */
    @JsonProperty(LEGACY_CATEGORY)
    public void setLegacyCategory(String legacyCategory) {
        this.legacyCategory = legacyCategory;
    }

    /**
     * Gets the access level.
     *
     * @return The accessLevel
     */
    @JsonProperty(ACCESS_LEVEL)
    public String getAccessLevel() {
        return accessLevel;
    }

    /**
     * Sets the access level.
     *
     * @param accessLevel
     *            The accessLevel
     */
    @JsonProperty(ACCESS_LEVEL)
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    /**
     * Gets the mail date.
     *
     * @return The mailDate
     */
    @JsonProperty(MAIL_DATE)
    public Date getMailDate() {
        if (null != mailDate) {
            return (Date) this.mailDate.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the mail date.
     *
     * @param mailDate
     *            The mailDate
     */
    @JsonProperty(MAIL_DATE)
    public void setMailDate(Date mailDate) {
        if (null != mailDate) {
            this.mailDate = (Date) mailDate.clone();
        } else {
            this.mailDate = null;
        }
    }

    /**
     * Gets the mime type.
     *
     * @return The mimeType
     */
    @JsonProperty(MIME_TYPE)
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the mime type.
     *
     * @param mimeType
     *            The mimeType
     */
    @JsonProperty(MIME_TYPE)
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

}