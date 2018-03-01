package alf.integration.service.base;
/**
 * {@code ExtractorException} is thrown when there is a problem in the application. 
 * @author Sanjay Tank
 * @version 1.0
 */
public class PropertiesLoadException extends RuntimeException {
    /**
     * Increment when there are externally visible changes.
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 0L;

    /**
     * Throw a new extractor exception.
     * @param message a description of the problem
     * @param cause the exception that triggered this one.
     */
    public PropertiesLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new properties load exception.
     *
     * @param message the message
     */
    public PropertiesLoadException(String message) {
        super(message);
    }

}
