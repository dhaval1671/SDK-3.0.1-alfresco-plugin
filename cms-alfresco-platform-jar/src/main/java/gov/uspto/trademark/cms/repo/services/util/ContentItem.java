package gov.uspto.trademark.cms.repo.services.util;

import java.io.InputStream;

import org.springframework.extensions.webscripts.servlet.FormData.FormField;

/**
 * Created by bgummadi on 8/28/2015.
 */
public class ContentItem {

    /** The input stream. */
    private InputStream inputStream;

    /** The form field. */
    private FormField formField;

    /**
     * Instantiates a new content item.
     */
    private ContentItem() {
    }

    /**
     * Instantiates a new content item.
     *
     * @param inputStream
     *            the input stream
     */
    private ContentItem(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Instantiates a new content item.
     *
     * @param formField
     *            the form field
     */
    private ContentItem(FormField formField) {
        this.formField = formField;
    }

    /**
     * Gets the single instance of ContentItem.
     *
     * @param inputStream
     *            the input stream
     * @return single instance of ContentItem
     */
    public static ContentItem getInstance(InputStream inputStream) {
        return new ContentItem(inputStream);
    }

    /**
     * Gets the single instance of ContentItem.
     *
     * @param formField
     *            the form field
     * @return single instance of ContentItem
     */
    public static ContentItem getInstance(FormField formField) {
        return new ContentItem(formField);
    }

    /**
     * Gets the input stream.
     *
     * @return the input stream
     */
    public InputStream getInputStream() {
        if (this.formField != null) {
            return this.formField.getInputStream();
        }
        return this.inputStream;
    }
}
