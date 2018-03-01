package gov.uspto.trademark.cms.repo.model.aspects;

import java.util.Date;

/**
 * PCM Related aspect representation in the repository
 *
 * Created by stank on 7/10/2015.
 */
public class PcmRelatedAspect extends AbstractBaseAspect {

    /** The pcm absn. */
    private String pcmABSN;

    /** The pcm afn. */
    private String pcmAFN;

    /** The pcm note num. */
    private String pcmNoteNum;

    /** The pcm seq num. */
    private String pcmSeqNum;

    /** The pcm replace by seq num. */
    private String pcmReplaceBySeqNum;

    /** The pcm file suffix. */
    private String pcmFileSuffix;

    /** The pcm media type. */
    private String pcmMediaType;

    /** The pcm original file name. */
    private String pcmOriginalFileName;

    /** The pcm rsn. */
    private String pcmRsn;

    /** The pcm file size. */
    private String pcmFileSize;

    /** The pcm create date time. */
    private Date pcmCreateDateTime;

    /** The pcm update date time. */
    private Date pcmUpdateDateTime;

    /** The pcm last modified date. */
    private Date pcmLastModifiedDate;

    /** The pcm oracle apply time. */
    private Date pcmOracleApplyTime;

    /**
     * Gets the pcm absn.
     *
     * @return the pcm absn
     */
    public String getPcmABSN() {
        return pcmABSN;
    }

    /**
     * Sets the pcm absn.
     *
     * @param pcmABSN
     *            the new pcm absn
     */
    public void setPcmABSN(String pcmABSN) {
        this.pcmABSN = pcmABSN;
    }

    /**
     * Gets the pcm afn.
     *
     * @return the pcm afn
     */
    public String getPcmAFN() {
        return pcmAFN;
    }

    /**
     * Sets the pcm afn.
     *
     * @param pcmAFN
     *            the new pcm afn
     */
    public void setPcmAFN(String pcmAFN) {
        this.pcmAFN = pcmAFN;
    }

    /**
     * Gets the pcm note num.
     *
     * @return the pcm note num
     */
    public String getPcmNoteNum() {
        return pcmNoteNum;
    }

    /**
     * Sets the pcm note num.
     *
     * @param pcmNoteNum
     *            the new pcm note num
     */
    public void setPcmNoteNum(String pcmNoteNum) {
        this.pcmNoteNum = pcmNoteNum;
    }

    /**
     * Gets the pcm seq num.
     *
     * @return the pcm seq num
     */
    public String getPcmSeqNum() {
        return pcmSeqNum;
    }

    /**
     * Sets the pcm seq num.
     *
     * @param pcmSeqNum
     *            the new pcm seq num
     */
    public void setPcmSeqNum(String pcmSeqNum) {
        this.pcmSeqNum = pcmSeqNum;
    }

    /**
     * Gets the pcm replace by seq num.
     *
     * @return the pcm replace by seq num
     */
    public String getPcmReplaceBySeqNum() {
        return pcmReplaceBySeqNum;
    }

    /**
     * Sets the pcm replace by seq num.
     *
     * @param pcmReplaceBySeqNum
     *            the new pcm replace by seq num
     */
    public void setPcmReplaceBySeqNum(String pcmReplaceBySeqNum) {
        this.pcmReplaceBySeqNum = pcmReplaceBySeqNum;
    }

    /**
     * Gets the pcm file suffix.
     *
     * @return the pcm file suffix
     */
    public String getPcmFileSuffix() {
        return pcmFileSuffix;
    }

    /**
     * Sets the pcm file suffix.
     *
     * @param pcmFileSuffix
     *            the new pcm file suffix
     */
    public void setPcmFileSuffix(String pcmFileSuffix) {
        this.pcmFileSuffix = pcmFileSuffix;
    }

    /**
     * Gets the pcm media type.
     *
     * @return the pcm media type
     */
    public String getPcmMediaType() {
        return pcmMediaType;
    }

    /**
     * Sets the pcm media type.
     *
     * @param pcmMediaType
     *            the new pcm media type
     */
    public void setPcmMediaType(String pcmMediaType) {
        this.pcmMediaType = pcmMediaType;
    }

    /**
     * Gets the pcm original file name.
     *
     * @return the pcm original file name
     */
    public String getPcmOriginalFileName() {
        return pcmOriginalFileName;
    }

    /**
     * Sets the pcm original file name.
     *
     * @param pcmOriginalFileName
     *            the new pcm original file name
     */
    public void setPcmOriginalFileName(String pcmOriginalFileName) {
        this.pcmOriginalFileName = pcmOriginalFileName;
    }

    /**
     * Gets the pcm rsn.
     *
     * @return the pcm rsn
     */
    public String getPcmRsn() {
        return pcmRsn;
    }

    /**
     * Sets the pcm rsn.
     *
     * @param pcmRsn
     *            the new pcm rsn
     */
    public void setPcmRsn(String pcmRsn) {
        this.pcmRsn = pcmRsn;
    }

    /**
     * Gets the pcm file size.
     *
     * @return the pcm file size
     */
    public String getPcmFileSize() {
        return pcmFileSize;
    }

    /**
     * Sets the pcm file size.
     *
     * @param pcmFileSize
     *            the new pcm file size
     */
    public void setPcmFileSize(String pcmFileSize) {
        this.pcmFileSize = pcmFileSize;
    }

    /**
     * Gets the pcm create date time.
     *
     * @return the pcm create date time
     */
    public Date getPcmCreateDateTime() {
        if (pcmCreateDateTime != null) {
            return (Date) pcmCreateDateTime.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the pcm create date time.
     *
     * @param pcmCreateDateTime
     *            the new pcm create date time
     */
    public void setPcmCreateDateTime(Date pcmCreateDateTime) {
        if (pcmCreateDateTime != null) {
            this.pcmCreateDateTime = (Date) pcmCreateDateTime.clone();
        } else {
            this.pcmCreateDateTime = null;
        }
    }

    /**
     * Gets the pcm update date time.
     *
     * @return the pcm update date time
     */
    public Date getPcmUpdateDateTime() {
        if (pcmUpdateDateTime != null) {
            return (Date) pcmUpdateDateTime.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the pcm update date time.
     *
     * @param pcmUpdateDateTime
     *            the new pcm update date time
     */
    public void setPcmUpdateDateTime(Date pcmUpdateDateTime) {
        if (pcmUpdateDateTime != null) {
            this.pcmUpdateDateTime = (Date) pcmUpdateDateTime.clone();
        } else {
            this.pcmUpdateDateTime = null;
        }
    }

    /**
     * Gets the pcm last modified date.
     *
     * @return the pcm last modified date
     */
    public Date getPcmLastModifiedDate() {
        if (pcmLastModifiedDate != null) {
            return (Date) pcmLastModifiedDate.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the pcm last modified date.
     *
     * @param pcmLastModifiedDate
     *            the new pcm last modified date
     */
    public void setPcmLastModifiedDate(Date pcmLastModifiedDate) {
        if (pcmLastModifiedDate != null) {
            this.pcmLastModifiedDate = (Date) pcmLastModifiedDate.clone();
        } else {
            this.pcmLastModifiedDate = null;
        }
    }

    /**
     * Gets the pcm oracle apply time.
     *
     * @return the pcm oracle apply time
     */
    public Date getPcmOracleApplyTime() {
        if (pcmOracleApplyTime != null) {
            return (Date) pcmOracleApplyTime.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the pcm oracle apply time.
     *
     * @param pcmOracleApplyTime
     *            the new pcm oracle apply time
     */
    public void setPcmOracleApplyTime(Date pcmOracleApplyTime) {
        if (pcmOracleApplyTime != null) {
            this.pcmOracleApplyTime = (Date) pcmOracleApplyTime.clone();
        } else {
            this.pcmOracleApplyTime = null;
        }
    }

}
