package gov.uspto.trademark.cms.repo.model;

import java.util.Date;

/**
 * Created by bgummadi on 6/2/2014.
 */
public abstract class AbstractBaseType {

    /** The creation time. */
    private Date creationTime;

    /** The modification time. */
    private Date modificationTime;

    /** The content. */
    private String content;

    /** The mime type. */
    private String mimeType;

    /** The document size. */
    private String documentSize;

    /** The version. */
    private String version;

    /**
     * Gets the document type.
     *
     * @return the document type
     */
    abstract public String getDocumentType();

    /**
     * Gets the content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content
     *            the new content
     */
    public void setContent(String content) {
        this.content = content;
        String[] contentArray = content.split("\\|");
        for (String pair : contentArray) {
            String[] pairArray = pair.split("=");
            if ("size".equals(pairArray[0])) {
                this.documentSize = pairArray[1];
            } else if ("mimetype".equals(pairArray[0])) {
                this.mimeType = pairArray[1];
            }
        }
    }

    /**
     * Gets the creation time.
     *
     * @return the creation time
     */
    public Date getCreationTime() {
        if (null != creationTime) {
            return (Date) creationTime.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the creation time.
     *
     * @param creationTime
     *            the new creation time
     */
    public void setCreationTime(Date creationTime) {
        if (null != creationTime) {
            this.creationTime = (Date) creationTime.clone();
        } else {
            this.creationTime = null;
        }
    }

    /**
     * Gets the modification time.
     *
     * @return the modification time
     */
    public Date getModificationTime() {
        if (null != modificationTime) {
            return (Date) modificationTime.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the modification time.
     *
     * @param modificationTime
     *            the new modification time
     */
    public void setModificationTime(Date modificationTime) {
        if (null != modificationTime) {
            this.modificationTime = (Date) modificationTime.clone();
        } else {
            this.modificationTime = null;
        }
    }

    /**
     * Gets the mime type.
     *
     * @return the mime type
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the mime type.
     *
     * @param mimeType
     *            the new mime type
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Gets the document size.
     *
     * @return the document size
     */
    public String getDocumentSize() {
        return documentSize;
    }

    /**
     * Sets the document size.
     *
     * @param documentSize
     *            the new document size
     */
    public void setDocumentSize(String documentSize) {
        this.documentSize = documentSize;
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
}