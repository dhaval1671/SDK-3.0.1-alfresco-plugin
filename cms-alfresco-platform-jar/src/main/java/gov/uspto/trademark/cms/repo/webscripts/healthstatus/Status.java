package gov.uspto.trademark.cms.repo.webscripts.healthstatus;

import gov.uspto.trademark.cms.repo.constants.TMConstants;

/**
 * The Enum status.
 */
public enum Status {

    /** GREEN: Indicates all components are alive and reachable */
    GREEN("GREEN"),

    /** YELLOW: Some components are alive and reachable. */
    YELLOW("YELLOW"),

    /** RED: None of the components are alive or reachable */
    RED("RED");

    /** The access level. */
    private final String status;

    /**
     * Instantiates a new status.
     *
     * @param status
     */
    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Status getOverallAlfrescoStatus(Status alfMountStatus, Status alfDBStatus) {
        Status finalStatus = Status.RED;
        if ((null != alfMountStatus) && (null != alfDBStatus)) {
            if ((alfMountStatus.compareTo(GREEN) == TMConstants.ZERO)) {
                if (alfDBStatus.compareTo(Status.GREEN) == TMConstants.ZERO) {
                    finalStatus = Status.GREEN;
                } else if (alfDBStatus.compareTo(Status.RED) == TMConstants.ZERO) {
                    finalStatus = Status.YELLOW;
                }
            } else if (null != alfMountStatus && (alfMountStatus.compareTo(RED) == TMConstants.ZERO)) {
                if (alfDBStatus.compareTo(Status.GREEN) == TMConstants.ZERO) {
                    finalStatus = Status.YELLOW;
                } else if (alfDBStatus.compareTo(Status.RED) == TMConstants.ZERO) {
                    finalStatus = Status.RED;
                }
            }
        }
        return finalStatus;
    }

}