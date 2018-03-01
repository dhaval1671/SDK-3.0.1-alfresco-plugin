package gov.uspto.trademark.cms.repo.model.aspects.mixin;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This interface is used to to convert pcmrelated object to json structure with
 * proper transformations.
 * 
 * @author stank
 *
 */
public interface PcmRelatedAspectMixIn {

    /**
     * Gets the pcm absn.
     *
     * @return the pcm absn
     */
    @JsonProperty("pcm-ABSN")
    int getPcmABSN();

    /**
     * Gets the pcm afn.
     *
     * @return the pcm afn
     */
    @JsonProperty("pcm-AFN")
    int getPcmAFN();

    /**
     * Gets the pcm note num.
     *
     * @return the pcm note num
     */
    @JsonProperty("pcm-noteNum")
    int getPcmNoteNum();

    /**
     * Gets the pcm seq num.
     *
     * @return the pcm seq num
     */
    @JsonProperty("pcm-seqNum")
    int getPcmSeqNum();

    /**
     * Gets the pcm replace by seq num.
     *
     * @return the pcm replace by seq num
     */
    @JsonProperty("pcm-replaceBySeqNum")
    int getPcmReplaceBySeqNum();

    /**
     * Gets the pcm file suffix.
     *
     * @return the pcm file suffix
     */
    @JsonProperty("pcm-fileSuffix")
    String getPcmFileSuffix();

    /**
     * Gets the pcm media type.
     *
     * @return the pcm media type
     */
    @JsonProperty("pcm-mediaType")
    String getPcmMediaType();

    /**
     * Gets the pcm original file name.
     *
     * @return the pcm original file name
     */
    @JsonProperty("pcm-originalFileName")
    String getPcmOriginalFileName();

    /**
     * Gets the pcm rsn.
     *
     * @return the pcm rsn
     */
    @JsonProperty("pcm-rsn")
    float getPcmRsn();

    /**
     * Gets the pcm file size.
     *
     * @return the pcm file size
     */
    @JsonProperty("pcm-fileSize")
    long getPcmFileSize();

    /**
     * Gets the pcm create date time.
     *
     * @return the pcm create date time
     */
    @JsonProperty("pcm-createDateTime")
    Date getPcmCreateDateTime();

    /**
     * Gets the pcm update date time.
     *
     * @return the pcm update date time
     */
    @JsonProperty("pcm-updateDateTime")
    Date getPcmUpdateDateTime();

    /**
     * Gets the pcm last modified date.
     *
     * @return the pcm last modified date
     */
    @JsonProperty("pcm-lastModifiedDate")
    Date getPcmLastModifiedDate();

    /**
     * Gets the pcm oracle apply time.
     *
     * @return the pcm oracle apply time
     */
    @JsonProperty("pcm-oracleApplyTime")
    Date getPcmOracleApplyTime();

}