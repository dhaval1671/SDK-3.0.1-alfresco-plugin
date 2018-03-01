package gov.uspto.trademark.cms.repo.webscripts.beans;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;

/**
 * The Class DocumentId.
 */
public class DocumentId {

    /** The Constant ONE. */
    private static final int ONE = 1;

    /** The Constant FOUR. */
    private static final int FOUR = 4;

    /** The Constant ZERO. */
    private static final int ZERO = 0;

    /** The Constant THREE. */
    private static final int THREE = 3;

    /** The Constant TWO. */
    private static final int TWO = 2;

    /** Filename for Image mark *. */
    public static final String IMAGE_FILE_NAME = "image";

    /** Filename for Multimedia mark *. */
    public static final String MULTIMEDIA_FILE_NAME = "multimedia";

    /** Separator in documentId *. */
    public static final String ID_PATH_SEPARATOR = "/";

    /**
     * This could be of value case, publication, petition etc.
     */
    private String cabinetType;

    /**
     * This is serialNumber for Mark type content and Date for publication type.
     */
    private String documentId;

    /**
     * This value tell if it is Mark/evidence etc.
     */
    private String modelType;

    /**
     * this value will be image for Mark Images and Multimedia for Mark
     * multimedia or file name for publication.
     */
    private String fileName;

    /**
     * Instantiates a new document id.
     *
     * @param cabinetType
     *            the cabinet type
     * @param documentId
     *            the document id
     * @param modelType
     *            the model type
     * @param fileName
     *            the file name
     */
    public DocumentId(String cabinetType, String documentId, String modelType, String fileName) {
        this.cabinetType = cabinetType;
        this.documentId = documentId;
        this.modelType = modelType;
        this.fileName = fileName;
    }

    /**
     * Instantiates a new document id.
     */
    public DocumentId() {
        super();
    }

    /**
     * Gets the cabinet type.
     *
     * @return the cabinet type
     */
    public String getCabinetType() {
        return cabinetType;
    }

    /**
     * Sets the cabinet type.
     *
     * @param cabinetType
     *            the new cabinet type
     */
    public void setCabinetType(String cabinetType) {
        this.cabinetType = cabinetType;
    }

    /**
     * Gets the document id.
     *
     * @return the document id
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Sets the document id.
     *
     * @param documentId
     *            the new document id
     */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * Gets the model type.
     *
     * @return the model type
     */
    public String getModelType() {
        return modelType;
    }

    /**
     * Sets the model type.
     *
     * @param modelType
     *            the new model type
     */
    public void setModelType(String modelType) {
        this.modelType = modelType;
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
     * Constructs a DocumentId object from the given String.
     *
     * @param documentId
     *            the document id
     * @return the document id
     */
    public static DocumentId createDocumentId(String documentId) {
        String[] pathElements = split(documentId);
        DocumentId documentIdObj = null;
        if (pathElements.length == DocumentId.FOUR && (StringUtils.isNotBlank(pathElements[DocumentId.ZERO])
                && StringUtils.isNotBlank(pathElements[1]) && StringUtils.isNotBlank(pathElements[DocumentId.TWO])
                && StringUtils.isNotBlank(pathElements[DocumentId.THREE]))) {
            documentIdObj = new DocumentId();
            documentIdObj.setCabinetType(pathElements[DocumentId.ZERO]);
            documentIdObj.setDocumentId(pathElements[DocumentId.ONE]);
            documentIdObj.setModelType(pathElements[DocumentId.TWO]);
            documentIdObj.setFileName(pathElements[DocumentId.THREE]);
        } else {
            throw new TmngCmsException.CorruptedDocIdException(HttpStatus.BAD_REQUEST,
                    "DocId '" + documentId + "' is corrupted.");
        }
        return documentIdObj;
    }

    /**
     * Splits the documentId into a string array Removes any trailing slashes.
     *
     * @param documentId
     *            the document id
     * @return the string[]
     */
    public static String[] split(String documentId) {
        documentId = org.springframework.util.StringUtils.trimLeadingCharacter(documentId, '/');
        return documentId.split(ID_PATH_SEPARATOR);
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {

        String fileName = getFileName();
        StringBuilder builder = new StringBuilder("/");
        builder.append(getCabinetType());
        builder.append(ID_PATH_SEPARATOR);
        builder.append(getDocumentId());
        builder.append(ID_PATH_SEPARATOR);
        builder.append(getModelType());
        builder.append(ID_PATH_SEPARATOR);
        builder.append(fileName);

        return builder.toString();
    }

}