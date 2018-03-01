package gov.uspto.trademark.cms.repo.webscripts.beans;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import gov.uspto.trademark.cms.repo.TmngCmsException;

/**
 * The Class EfileDocumentId.
 */
public class EfileDocumentId {

    /** The Constant FOUR. */
    private static final int FOUR = 4;

    /** The Constant THREE. */
    private static final int THREE = 3;

    /** The Constant TWO. */
    private static final int TWO = 2;

    /** The Constant ONE. */
    private static final int ONE = 1;

    /** The Constant ZERO. */
    private static final int ZERO = 0;

    /** Separator in documentId *. */
    public static final String ID_PATH_SEPARATOR = "/";

    /**
     * This could be of value drive etc.
     */
    private String drive;

    /**
     * This value tell if it is efile etc.
     */
    private String efileType;

    /**
     * This is trademarkId for efile type content.
     */
    private String trademarkId;

    /**
     * this value will be efile name.
     */
    private String efileName;

    /**
     * Instantiates a new efile document id.
     *
     * @param drive
     *            the drive
     * @param efileType
     *            the efile type
     * @param trademarkId
     *            the trademark id
     */
    public EfileDocumentId(String drive, String efileType, String trademarkId) {
        this(drive, efileType, trademarkId, null);
    }

    /**
     * Instantiates a new efile document id.
     *
     * @param drive
     *            the drive
     * @param efileType
     *            the efile type
     * @param trademarkId
     *            the trademark id
     * @param efileName
     *            the efile name
     */
    public EfileDocumentId(String drive, String efileType, String trademarkId, String efileName) {
        this.drive = drive;
        this.efileType = efileType;
        this.trademarkId = trademarkId;
        this.efileName = efileName;
    }

    /**
     * Instantiates a new efile document id.
     */
    public EfileDocumentId() {
        super();
    }

    /**
     * Constructs a DocumentId object from the given String.
     *
     * @param documentId
     *            the document id
     * @return the efile document id
     */
    public static EfileDocumentId createEfileDocId(String documentId) {
        String[] pathElements = split(documentId);
        EfileDocumentId documentIdObj = null;
        if (pathElements.length == EfileDocumentId.FOUR) {
            documentIdObj = new EfileDocumentId();
            documentIdObj.setDrive(pathElements[EfileDocumentId.ZERO]);
            documentIdObj.setEfileType(pathElements[EfileDocumentId.ONE]);
            documentIdObj.setTrademarkId(pathElements[EfileDocumentId.TWO]);
            documentIdObj.setEfileName(pathElements[EfileDocumentId.THREE]);
        } else {
            throw new TmngCmsException.CorruptedDocIdException(HttpStatus.BAD_REQUEST,
                    "Efile DocId '" + documentId + "' is corrupted, It doesn't have usual pre-defined components to it.");
        }
        return documentIdObj;
    }

    /**
     * Splits the documentId into a string array Removes any trailing slashes.
     *
     * @param efileId
     *            the efile id
     * @return the string[]
     */
    public static String[] split(String efileId) {
        efileId = StringUtils.trimLeadingCharacter(efileId, '/');
        return efileId.split(ID_PATH_SEPARATOR);
    }

    /**
     * Gets the efile id.
     *
     * @return the efile id
     */
    public String getEfileId() {
        StringBuilder builder = new StringBuilder("/");
        builder.append(getDrive());
        builder.append(ID_PATH_SEPARATOR);
        builder.append(getEfileType());
        builder.append(ID_PATH_SEPARATOR);
        builder.append(getTrademarkId());
        builder.append(ID_PATH_SEPARATOR);
        builder.append(getEfileName());
        return builder.toString();
    }

    /**
     * Gets the drive.
     *
     * @return the drive
     */
    public String getDrive() {
        return drive;
    }

    /**
     * Sets the drive.
     *
     * @param drive
     *            the new drive
     */
    public void setDrive(String drive) {
        this.drive = drive;
    }

    /**
     * Gets the efile type.
     *
     * @return the efile type
     */
    public String getEfileType() {
        return efileType;
    }

    /**
     * Sets the efile type.
     *
     * @param efileType
     *            the new efile type
     */
    public void setEfileType(String efileType) {
        this.efileType = efileType;
    }

    /**
     * Gets the trademark id.
     *
     * @return the trademark id
     */
    public String getTrademarkId() {
        return trademarkId;
    }

    /**
     * Sets the trademark id.
     *
     * @param trademarkId
     *            the new trademark id
     */
    public void setTrademarkId(String trademarkId) {
        this.trademarkId = trademarkId;
    }

    /**
     * Gets the efile name.
     *
     * @return the efile name
     */
    public String getEfileName() {
        return efileName;
    }

    /**
     * Sets the efile name.
     *
     * @param efileName
     *            the new efile name
     */
    public void setEfileName(String efileName) {
        this.efileName = efileName;
    }

}