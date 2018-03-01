package gov.uspto.trademark.cms.repo.webscripts.beans;

/**
 * Created by bgummadi on 9/10/2014.
 */
public class CaseDocumentMetadataResponse extends BaseMetadataResponse {

    /** The serial number. */
    private String serialNumber;

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

}
