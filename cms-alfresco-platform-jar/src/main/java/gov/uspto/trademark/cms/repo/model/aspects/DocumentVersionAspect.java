package gov.uspto.trademark.cms.repo.model.aspects;

import java.util.Date;

/**
 * DocumentVersion aspect representation in the repository
 * <p/>
 * Created by bgummadi on 6/3/2014.
 */
public class DocumentVersionAspect extends AbstractBaseAspect {

    /** The document start date. */
    private Date documentStartDate;

    /** The document end date. */
    private Date documentEndDate;

    /** The new content indicator. */
    private Boolean newContentIndicator;

    /**
     * Gets the document start date.
     *
     * @return the document start date
     */
    public Date getDocumentStartDate() {
        if (null != documentStartDate) {
            return (Date) documentStartDate.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the document start date.
     *
     * @param documentStartDate
     *            the new document start date
     */
    public void setDocumentStartDate(Date documentStartDate) {
        if (null != documentStartDate) {
            this.documentStartDate = (Date) documentStartDate.clone();
        } else {
            this.documentStartDate = null;
        }
    }

    /**
     * Gets the document end date.
     *
     * @return the document end date
     */
    public Date getDocumentEndDate() {
        if (null != documentEndDate) {
            return (Date) documentEndDate.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the document end date.
     *
     * @param documentEndDate
     *            the new document end date
     */
    public void setDocumentEndDate(Date documentEndDate) {
        if (null != documentEndDate) {
            this.documentEndDate = (Date) documentEndDate.clone();
        } else {
            this.documentEndDate = null;
        }
    }

    /**
     * Gets the new content indicator.
     *
     * @return the new content indicator
     */
    public Boolean getNewContentIndicator() {
        return newContentIndicator;
    }

    /**
     * Sets the new content indicator.
     *
     * @param newContentIndicator
     *            the new new content indicator
     */
    public void setNewContentIndicator(Boolean newContentIndicator) {
        this.newContentIndicator = newContentIndicator;
    }
}
