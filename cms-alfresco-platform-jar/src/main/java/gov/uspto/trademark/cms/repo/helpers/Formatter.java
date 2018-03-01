package gov.uspto.trademark.cms.repo.helpers;

/**
 * General Purpose interface for formatting some object as a String.
 *
 * @author jwolf2
 * @param <T>
 *            the generic type
 */
public interface Formatter<T> {

    /**
     * Return the object formatted as a String. Null behavior unspecified.
     *
     * @param object
     *            the object
     * @return the string
     */
    String format(T object);

}