package gov.uspto.trademark.cms.repo.model.aspects;

import java.util.Date;

/**
 * BusinessVersion representation in the repository
 *
 * Created by bgummadi on 6/3/2014.
 */
public class BusinessVersionAspect extends AbstractBaseAspect {

    /** The effective start date. */
    private Date effectiveStartDate;

    /** The effective end date. */
    private Date effectiveEndDate;

    /**
     * Gets the effective start date.
     *
     * @return the effective start date
     */
    public Date getEffectiveStartDate() {
        if (null != effectiveStartDate) {
            return (Date) effectiveStartDate.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the effective start date.
     *
     * @param effectiveStartDate
     *            the new effective start date
     */
    public void setEffectiveStartDate(Date effectiveStartDate) {
        if (null != effectiveStartDate) {
            this.effectiveStartDate = (Date) effectiveStartDate.clone();
        } else {
            this.effectiveStartDate = null;
        }
    }

    /**
     * Gets the effective end date.
     *
     * @return the effective end date
     */
    public Date getEffectiveEndDate() {
        if (null != effectiveEndDate) {
            return (Date) effectiveEndDate.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the effective end date.
     *
     * @param effectiveEndDate
     *            the new effective end date
     */
    public void setEffectiveEndDate(Date effectiveEndDate) {
        if (null != effectiveEndDate) {
            this.effectiveEndDate = (Date) effectiveEndDate.clone();
        } else {
            this.effectiveEndDate = null;
        }
    }
}
