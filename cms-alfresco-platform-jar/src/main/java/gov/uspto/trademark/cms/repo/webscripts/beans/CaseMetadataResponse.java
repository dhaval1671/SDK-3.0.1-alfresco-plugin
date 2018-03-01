package gov.uspto.trademark.cms.repo.webscripts.beans;

import java.util.Date;

/**
 * This Bean class is used to populate the metadata at case folder level.
 *
 * @author vnondapaka
 */
public class CaseMetadataResponse {

    /** The name. */
    private String name;

    /** The ticrs doc count. */
    private int ticrsDocCount;

    /** The last ticrs sync date. */
    private Date lastTICRSSyncDate;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the ticrs doc count.
     *
     * @return the ticrs doc count
     */
    public int getTicrsDocCount() {
        return ticrsDocCount;
    }

    /**
     * Sets the ticrs doc count.
     *
     * @param ticrsDocCount
     *            the new ticrs doc count
     */
    public void setTicrsDocCount(int ticrsDocCount) {
        this.ticrsDocCount = ticrsDocCount;
    }

    /**
     * Gets the last ticrs sync date.
     *
     * @return the last ticrs sync date
     */
    public Date getLastTICRSSyncDate() {
        if (null != lastTICRSSyncDate) {
            return (Date) lastTICRSSyncDate.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the last ticrs sync date.
     *
     * @param lastTICRSSyncDate
     *            the new last ticrs sync date
     */
    public void setLastTICRSSyncDate(Date lastTICRSSyncDate) {
        if (null != lastTICRSSyncDate) {
            this.lastTICRSSyncDate = (Date) lastTICRSSyncDate.clone();
        } else {
            this.lastTICRSSyncDate = null;
        }
    }

}
