package gov.uspto.trademark.cms.repo.model.aspects;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * The Class ApplicationDates.
 */
public class ApplicationDates extends AbstractBaseAspect {

    /** The creation time. */
    @JsonIgnore
    private Date creationTime;

    /** The mail date. */
    private Date mailDate;

    /** The scan date. */
    private Date scanDate;

    /** The load date. */
    private Date loadDate;

    /**
     * Gets the mail date.
     *
     * @return the mail date
     */
    public Date getMailDate() {
        if (null != mailDate) {
            return (Date) mailDate.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the mail date.
     *
     * @param mailDate
     *            the new mail date
     */
    public void setMailDate(Date mailDate) {
        if (null != mailDate) {
            this.mailDate = (Date) mailDate.clone();
        } else {
            this.mailDate = null;
        }
    }

    /**
     * Gets the scan date.
     *
     * @return the scan date
     */
    public Date getScanDate() {
        if (null != scanDate) {
            return (Date) scanDate.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the scan date.
     *
     * @param scanDate
     *            the new scan date
     */
    public void setScanDate(Date scanDate) {
        if (null != scanDate) {
            this.scanDate = (Date) scanDate.clone();
        } else {
            this.scanDate = null;
        }
    }

    /**
     * Gets the load date.
     *
     * @return the load date
     */
    public Date getLoadDate() {
        if (null != loadDate) {
            return (Date) loadDate.clone();
        } else {
            return (null != this.creationTime) ? (Date) this.creationTime.clone() : null;
        }
    }

    /**
     * Sets the load date.
     *
     * @param loadDate
     *            the new load date
     */
    public void setLoadDate(Date loadDate) {
        if (null != loadDate) {
            this.loadDate = (Date) loadDate.clone();
        } else {
            this.loadDate = null;
        }
    }

    /**
     * Sets the creation time.
     *
     * @param creationDate
     *            the new creation time
     */
    public void setCreationTime(Date creationDate) {
        if (null != creationDate) {
            this.creationTime = (Date) creationDate.clone();
        } else {
            this.creationTime = null;
        }
    }

}
