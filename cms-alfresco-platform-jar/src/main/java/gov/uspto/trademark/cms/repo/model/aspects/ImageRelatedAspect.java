package gov.uspto.trademark.cms.repo.model.aspects;

/**
 * Image related aspect representation in the repository
 *
 * Created by stank on Aug/22/2014.
 */
public class ImageRelatedAspect extends AbstractBaseAspect {

    /** The image height. */
    private String imageHeight;

    /** The image width. */
    private String imageWidth;

    /** The image resolution x. */
    private String imageResolutionX;

    /** The image resolution y. */
    private String imageResolutionY;

    /** The image color depth. */
    private String imageColorDepth;

    /** The image compression type. */
    private String imageCompressionType;

    /**
     * Gets the image height.
     *
     * @return the image height
     */
    public String getImageHeight() {
        return imageHeight;
    }

    /**
     * Sets the image height.
     *
     * @param imageHeight
     *            the new image height
     */
    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    /**
     * Gets the image width.
     *
     * @return the image width
     */
    public String getImageWidth() {
        return imageWidth;
    }

    /**
     * Sets the image width.
     *
     * @param imageWidth
     *            the new image width
     */
    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    /**
     * Gets the image resolution x.
     *
     * @return the image resolution x
     */
    public String getImageResolutionX() {
        return imageResolutionX;
    }

    /**
     * Sets the image resolution x.
     *
     * @param imageResolutionX
     *            the new image resolution x
     */
    public void setImageResolutionX(String imageResolutionX) {
        this.imageResolutionX = imageResolutionX;
    }

    /**
     * Gets the image resolution y.
     *
     * @return the image resolution y
     */
    public String getImageResolutionY() {
        return imageResolutionY;
    }

    /**
     * Sets the image resolution y.
     *
     * @param imageResolutionY
     *            the new image resolution y
     */
    public void setImageResolutionY(String imageResolutionY) {
        this.imageResolutionY = imageResolutionY;
    }

    /**
     * Gets the image color depth.
     *
     * @return the image color depth
     */
    public String getImageColorDepth() {
        return imageColorDepth;
    }

    /**
     * Sets the image color depth.
     *
     * @param imageColorDepth
     *            the new image color depth
     */
    public void setImageColorDepth(String imageColorDepth) {
        this.imageColorDepth = imageColorDepth;
    }

    /**
     * Gets the image compression type.
     *
     * @return the image compression type
     */
    public String getImageCompressionType() {
        return imageCompressionType;
    }

    /**
     * Sets the image compression type.
     *
     * @param imageCompressionType
     *            the new image compression type
     */
    public void setImageCompressionType(String imageCompressionType) {
        this.imageCompressionType = imageCompressionType;
    }

}
