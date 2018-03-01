package gov.uspto.trademark.cms.repo.webscripts.beans;

/**
 * Created by stank on 01/18/2017.
 */
public class LegalProceedingDocumentMetadataResponse extends BaseMetadataResponse {

    /** The serial number. */
    private String proceedingNumber;

    public String getProceedingNumber() {
        return proceedingNumber;
    }

    public void setProceedingNumber(String proceedingNumber) {
        this.proceedingNumber = proceedingNumber;
    }

}
