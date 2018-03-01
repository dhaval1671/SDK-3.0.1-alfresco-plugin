package gov.uspto.trademark.cms.repo.model;

/**
 * The Class TradeMarkContent.
 */
public class TradeMarkContent extends AbstractBaseType {

    /** The Constant TYPE. */
    public static final String TYPE = "trademarkContent";

    /** The document name. */
    private String documentName;

    /**
     * Gets the document name.
     *
     * @return the document name
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Sets the document name.
     *
     * @param documentName
     *            the new document name
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return TradeMarkContent.TYPE;
    }

}
