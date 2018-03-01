package gov.uspto.trademark.cms.repo.constants;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bgummadi on 4/1/2014.
 */
public enum TradeMarkProperties {

    /** The document name. */
    DOCUMENT_NAME(TradeMarkModel.DOCUMENT_NAME),

    /** The created by user id. */
    CREATED_BY_USER_ID(TradeMarkModel.CREATED_BY_USER_ID),

    /** The modified by user id. */
    MODIFIED_BY_USER_ID(TradeMarkModel.MODIFIED_BY_USER_ID),

    /** The mail date. */
    MAIL_DATE(TradeMarkModel.MAIL_DATE),

    /** The scan date. */
    SCAN_DATE(TradeMarkModel.SCAN_DATE),

    /** The load date. */
    LOAD_DATE(TradeMarkModel.LOAD_DATE),

    /** The reason for publication. */
    REASON_FOR_PUBLICATION(TradeMarkModel.REASON_FOR_PUBLICATION),

    /** The facet identifier. */
    FACET_IDENTIFIER(TradeMarkModel.FACET_IDENTIFIER),

    /** The category sequence. */
    CATEGORY_SEQUENCE(TradeMarkModel.CATEGORY_SEQUENCE),

    /** The case related. */
    CASE_RELATED(TradeMarkModel.CASE_RELATED),

    /** The serial number. */
    SERIAL_NUMBER(TradeMarkModel.SERIAL_NUMBER),

    /** The issue date. */
    ISSUE_DATE(TradeMarkModel.ISSUE_DATE),

    /** The business document related. */
    BUSINESS_DOCUMENT_RELATED(TradeMarkModel.BUSINESS_DOCUMENT_RELATED),

    /** The business document type. */
    BUSINESS_DOCUMENT_TYPE(TradeMarkModel.BUSINESS_DOCUMENT_TYPE),

    /** The direction. */
    DIRECTION(TradeMarkModel.DIRECTION),

    /** The image related. */
    IMAGE_RELATED(TradeMarkModel.IMAGE_RELATED),

    /** The image height. */
    IMAGE_HEIGHT(TradeMarkModel.IMAGE_HEIGHT),

    /** The image width. */
    IMAGE_WIDTH(TradeMarkModel.IMAGE_WIDTH),

    /** The image resolution x. */
    IMAGE_RESOLUTION_X(TradeMarkModel.IMAGE_RESOLUTION_X),

    /** The image resolution y. */
    IMAGE_RESOLUTION_Y(TradeMarkModel.IMAGE_RESOLUTION_Y),

    /** The image color depth. */
    IMAGE_COLOR_DEPTH(TradeMarkModel.IMAGE_COLOR_DEPTH),

    /** The image compression type. */
    IMAGE_COMPRESSION_TYPE(TradeMarkModel.IMAGE_COMPRESSION_TYPE),

    /** The source media. */
    SOURCE_MEDIA(TradeMarkModel.SOURCE_MEDIA),

    /** The source medium. */
    SOURCE_MEDIUM(TradeMarkModel.SOURCE_MEDIUM),

    /** The business version related. */
    BUSINESS_VERSION_RELATED(TradeMarkModel.BUSINESS_VERSION_RELATED),

    /** The effective start date. */
    EFFECTIVE_START_DATE(TradeMarkModel.EFFECTIVE_START_DATE),

    /** The effective end date. */
    EFFECTIVE_END_DATE(TradeMarkModel.EFFECTIVE_END_DATE),

    /** The evidence related. */
    EVIDENCE_RELATED(TradeMarkModel.EVIDENCE_RELATED),

    /** The evidence capture date time. */
    EVIDENCE_CAPTURE_DATE_TIME(TradeMarkModel.EVIDENCE_CAPTURE_DATE_TIME),

    /** The evidence source url. */
    EVIDENCE_SOURCE_URL(TradeMarkModel.EVIDENCE_SOURCE_URL),

    /** The evidence source type. */
    EVIDENCE_SOURCE_TYPE(TradeMarkModel.EVIDENCE_SOURCE_TYPE),

    /** The multimedia related. */
    MULTIMEDIA_RELATED(TradeMarkModel.MULTIMEDIA_RELATED),

    /** The codec audio. */
    CODEC_AUDIO(TradeMarkModel.CODEC_AUDIO),

    /** The audio compression type. */
    AUDIO_COMPRESSION_TYPE(TradeMarkModel.AUDIO_COMPRESSION_TYPE),

    /** The codec video. */
    CODEC_VIDEO(TradeMarkModel.CODEC_VIDEO),

    /** The video compression type. */
    VIDEO_COMPRESSION_TYPE(TradeMarkModel.VIDEO_COMPRESSION_TYPE),

    /** The mm duration. */
    MM_DURATION(TradeMarkModel.MM_DURATION),

    /** The mm start time. */
    MM_START_TIME(TradeMarkModel.MM_START_TIME),

    /** The mm end time. */
    MM_END_TIME(TradeMarkModel.MM_END_TIME),

    /** The active. */
    ACTIVE(TradeMarkModel.ACTIVE),

    /** The supported. */
    SUPPORTED(TradeMarkModel.SUPPORTED),

    /** The converted. */
    CONVERTED(TradeMarkModel.CONVERTED),

    /** The cont cd. */
    CONT_CD(TradeMarkModel.CONT_CD),

    /** The migrated. */
    MIGRATED(TradeMarkModel.MIGRATED),

    /** The migration method. */
    MIGRATION_METHOD(TradeMarkModel.MIGRATION_METHOD),

    /** The migration source. */
    MIGRATION_SOURCE(TradeMarkModel.MIGRATION_SOURCE),

    /** The stylesheet. */
    STYLESHEET(TradeMarkModel.STYLESHEET),

    /** The attachments. */
    ATTACHMENTS(TradeMarkModel.ATTACHMENTS);

    /** The Constant lookup. */
    private static final Map<String, TradeMarkProperties> LOOKUP = new HashMap<String, TradeMarkProperties>();

    static {
        for (TradeMarkProperties tmType : EnumSet.allOf(TradeMarkProperties.class)) {
            LOOKUP.put(tmType.getPropertyName(), tmType);
        }
    }

    /** The property name. */
    private String propertyName;

    /**
     * Instantiates a new trade mark properties.
     *
     * @param propertyName
     *            the property name
     */
    TradeMarkProperties(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * Gets the property name.
     *
     * @return the property name
     */
    public String getPropertyName() {
        return this.propertyName;
    }

    /**
     * Gets the trade mark type.
     *
     * @param propertyName
     *            the property name
     * @return the trade mark type
     */
    public static TradeMarkProperties getTradeMarkType(String propertyName) {
        return LOOKUP.get(propertyName);
    }
}
