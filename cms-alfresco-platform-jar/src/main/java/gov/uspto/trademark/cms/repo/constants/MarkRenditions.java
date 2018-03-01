package gov.uspto.trademark.cms.repo.constants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.repo.rendition.executer.AbstractRenderingEngine;
import org.alfresco.repo.rendition.executer.ImageRenderingEngine;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;

/**
 * The Enum MarkRenditions.
 */
public enum MarkRenditions {

    /** The MAR k_ tin y_80 x65_ png. */
    MARK_TINY_80X65("tiny-png", TMConstants.EIGHTY, TMConstants.SIXTY_FIVE, true, TMConstants.IMAGE_PNG),

    /** The MAR k_ smal l_100 x100. */
    MARK_SMALL_100X100("small-png", TMConstants.HUNDRED, TMConstants.HUNDRED, true, TMConstants.IMAGE_PNG, true),

    /** The MAR k_ thumbnai l_160 x130_ png. */
    MARK_THUMBNAIL_160X130("thumbnail-png", TMConstants.HUNDRED_SIXTY, TMConstants.HUNDRED_THIRTY, true, TMConstants.IMAGE_PNG),

    /** The MAR k_ larg e_320 x260_ png. */
    MARK_LARGE_320X260("large-png", TMConstants.THREE_HUNDRED_TWENTY, TMConstants.TWO_HUNDRED_SIXTY, true, TMConstants.IMAGE_PNG),

    /** The image to png. */
    IMAGE_TO_PNG("image-png", null, null, null, TMConstants.IMAGE_PNG);

    /** The rendition name. */
    private final String renditionName;

    /** The width. */
    private final Integer width;

    /** The height. */
    private final Integer height;

    /** The maintain aspect ratio. */
    private final Boolean maintainAspectRatio;

    /** The mime type. */
    private final String mimeType;

    /** The resize to thumbnail. */
    private final Boolean resizeToThumbnail;

    /**
     * Instantiates a new mark renditions.
     *
     * @param renditionName
     *            the rendition name
     * @param width
     *            the width
     * @param height
     *            the height
     * @param maintainAspectRatio
     *            the maintain aspect ratio
     * @param mimeType
     *            the mime type
     */
    MarkRenditions(String renditionName, Integer width, Integer height, Boolean maintainAspectRatio, String mimeType) {
        this(renditionName, width, height, maintainAspectRatio, mimeType, null);
    }

    /**
     * Instantiates a new mark renditions.
     *
     * @param renditionName
     *            the rendition name
     * @param width
     *            the width
     * @param height
     *            the height
     * @param maintainAspectRatio
     *            the maintain aspect ratio
     * @param mimeType
     *            the mime type
     * @param resizeToThumbnail
     *            the resize to thumbnail
     */
    MarkRenditions(String renditionName, Integer width, Integer height, Boolean maintainAspectRatio, String mimeType,
            Boolean resizeToThumbnail) {
        this.renditionName = renditionName;
        this.width = width;
        this.height = height;
        this.maintainAspectRatio = maintainAspectRatio;
        this.mimeType = mimeType;
        this.resizeToThumbnail = resizeToThumbnail;
    }

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    public Map<String, Serializable> getProperties() {
        Map<String, Serializable> properties = new HashMap<String, Serializable>();
        if (this.height != null) {
            properties.put(ImageRenderingEngine.PARAM_RESIZE_WIDTH, this.height);
        }
        if (this.width != null) {
            properties.put(ImageRenderingEngine.PARAM_RESIZE_HEIGHT, this.width);
        }
        if (this.maintainAspectRatio != null) {
            properties.put(ImageRenderingEngine.PARAM_MAINTAIN_ASPECT_RATIO, this.maintainAspectRatio);
        }
        if (this.mimeType != null) {
            properties.put(AbstractRenderingEngine.PARAM_MIME_TYPE, this.mimeType);
        }
        if (this.resizeToThumbnail != null) {
            properties.put(ImageRenderingEngine.PARAM_RESIZE_TO_THUMBNAIL, this.resizeToThumbnail);
        }
        return properties;
    }

    /**
     * Gets the rendition name.
     *
     * @return the rendition name
     */
    public String getRenditionName() {
        return this.renditionName;
    }

    /**
     * From rendition name.
     *
     * @param renditionName
     *            the rendition name
     * @return the mark renditions
     */
    public static MarkRenditions fromRenditionName(String renditionName) {
        if (StringUtils.isNotBlank(renditionName)) {
            for (MarkRenditions mr : MarkRenditions.values()) {
                if (renditionName.equalsIgnoreCase(mr.renditionName)) {
                    return mr;
                }
            }
        }
        throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, "Rendition not supported : " + renditionName);
    }
}
