package gov.uspto.trademark.cms.repo.model.aspects;

/**
 * Case aspect representation in the repository
 *
 * Created by bgummadi on 6/3/2014.
 */
public class CaseAspect extends AbstractBaseAspect {

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
