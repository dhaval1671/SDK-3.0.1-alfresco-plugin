package gov.uspto.trademark.cms.repo;

import org.springframework.http.HttpStatus;

/**
 * Created by bgummadi on 6/16/2014.
 */
public class TmngCmsException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5158903790768302682L;

    /**
     * The Class PathNotFoundException.
     */
    public static class PathNotFoundException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -1002582482538004849L;

        /**
         * Instantiates a new path not found exception.
         *
         * @param statusText
         *            the status text
         */
        public PathNotFoundException(String statusText) {
            super(statusText);
        }
    }

    /**
     * The Class EvidenceFileDoesNotExistsException.
     */
    public static class EvidenceFileDoesNotExistsException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -9099378831263062230L;

        /**
         * Instantiates a new evidence file does not exists exception.
         *
         * @param statusText
         *            the status text
         */
        public EvidenceFileDoesNotExistsException(String statusText) {
            super(statusText);
        }
    }

    /**
     * The Class CaseSerialNumberFormatException.
     */
    public static class CaseSerialNumberFormatException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 3478758530393524171L;

        /**
         * Instantiates a new case serial number format exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         * @param throwable
         *            the throwable
         */
        public CaseSerialNumberFormatException(HttpStatus statusCode, String statusText, Throwable throwable) {
            super(statusCode, statusText, throwable);
        }
    }

    /**
     * The Class UpdateEvidenceContentFailedException.
     */
    public static class UpdateEvidenceContentFailedException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 243220769512925515L;

        /**
         * Instantiates a new update evidence content failed exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         */
        public UpdateEvidenceContentFailedException(HttpStatus statusCode, String statusText) {
            super(statusCode, statusText);
        }
    }

    /**
     * The Class UpdateEvidenceMetadataFailedException.
     */
    public static class UpdateEvidenceMetadataFailedException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 6752858341053886790L;

        /**
         * Instantiates a new update evidence metadata failed exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         */
        public UpdateEvidenceMetadataFailedException(HttpStatus statusCode, String statusText) {
            super(statusCode, statusText);
        }
    }

    /**
     * The Class UpdateMarkContentFailedException.
     */
    public static class UpdateMarkContentFailedException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 3034347415914955864L;

        /**
         * Instantiates a new update mark content failed exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         */
        public UpdateMarkContentFailedException(HttpStatus statusCode, String statusText) {
            super(statusCode, statusText);
        }
    }

    /**
     * The Class UpdateMarkFailedException.
     */
    public static class UpdateMarkFailedException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1715356247842240533L;

        /**
         * Instantiates a new update mark failed exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         */
        public UpdateMarkFailedException(HttpStatus statusCode, String statusText) {
            super(statusCode, statusText);
        }
    }

    /**
     * The Class UpdateMarkMetadataFailedException.
     */
    public static class UpdateMarkMetadataFailedException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -2601294426911114726L;

        /**
         * Instantiates a new update mark metadata failed exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         */
        public UpdateMarkMetadataFailedException(HttpStatus statusCode, String statusText) {
            super(statusCode, statusText);
        }
    }

    /**
     * The Class UpdateRedactedResponseFailedException.
     */
    public static class UpdateRedactedResponseFailedException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -2601294426911114726L;

        /**
         * Instantiates a new update redacted response failed exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         */
        public UpdateRedactedResponseFailedException(HttpStatus statusCode, String statusText) {
            super(statusCode, statusText);
        }
    }

    /**
     * The Class RedactionRestoreOriginalException.
     */
    public static class RedactionRestoreOriginalException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 7864740209190981193L;

        /**
         * Instantiates a restore original failed exception.
         *
         * @param errorMsg
         *            the error msg
         */
        public RedactionRestoreOriginalException(String errorMsg) {
            super(errorMsg);
        }
    }

    /**
     * The Class FileCheckFailedException.
     */
    public static class FileCheckFailedException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 138825500795723263L;

        /**
         * Instantiates a new file check failed exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         */
        public FileCheckFailedException(HttpStatus statusCode, String statusText) {
            super(statusCode, statusText);
        }
    }

    /**
     * The Class SerialNumberNotFoundException.
     */
    public static class SerialNumberNotFoundException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -4777734514730754731L;

        /**
         * Instantiates a new serial number not found exception.
         *
         * @param statusText
         *            the status text
         * @param throwable
         *            the throwable
         */
        public SerialNumberNotFoundException(String statusText, Throwable throwable) {
            super(HttpStatus.NOT_FOUND, statusText, throwable);
        }

    }

    /**
     * The Class FileDoesNotExistsException.
     */
    public static class DocumentDoesNotExistException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 6665583345417199428L;

        /**
         * Instantiates a new evidence file does not exists exception.
         *
         * @param errorMessage
         *            the error message
         */
        public DocumentDoesNotExistException(String errorMessage) {
            super(errorMessage);
        }

        /**
         * Instantiates a new document does not exist exception.
         *
         * @param errorMessage
         *            the error message
         * @param e
         *            the e
         */
        public DocumentDoesNotExistException(String errorMessage, Exception e) {
            super(errorMessage, e);
        }

        /**
         * Instantiates a new document does not exist exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         */
        public DocumentDoesNotExistException(HttpStatus statusCode, String statusText) {
            super(statusCode, statusText);
        }

        /**
         * Instantiates a Document Does Not Exist Exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         * @param throwable
         *            the throwable
         */
        public DocumentDoesNotExistException(HttpStatus statusCode, String statusText, Throwable throwable) {
            super(statusCode, statusText, throwable);
        }
    }

    /**
     * The Class PropertyNotFoundException.
     */
    public static class PropertyNotFoundException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 7492976007688662042L;

        /**
         * Instantiates a new property not found exception.
         *
         * @param errorMessage
         *            the error message
         */
        public PropertyNotFoundException(String errorMessage) {
            super(errorMessage);
        }

        /**
         * Instantiates a new property not found exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         * @param throwable
         *            the throwable
         */
        public PropertyNotFoundException(HttpStatus statusCode, String statusText, Throwable throwable) {
            super(statusCode, statusText, throwable);
        }
    }

    /**
     * The Class CorruptedDocIdException.
     */
    public static class CorruptedDocIdException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -321229481296389739L;

        /**
         * Instantiates a new corrupted doc id exception.
         *
         * @param errorMessage
         *            the error message
         */
        public CorruptedDocIdException(String errorMessage) {
            super(errorMessage);
        }

        /**
         * Instantiates a new corrupted doc id exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         */
        public CorruptedDocIdException(HttpStatus statusCode, String statusText) {
            super(statusCode, statusText);
        }
    }

    /**
     * The Class CMSRuntimeException.
     */
    public static class CMSRuntimeException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -8203580783690662679L;

        /**
         * Instantiates a new CMS runtime exception.
         *
         * @param message
         *            the message
         * @param cause
         *            the cause
         */
        public CMSRuntimeException(String message, Exception cause) {
            super(message, cause);
        }

        /**
         * Instantiates a new CMS runtime exception.
         *
         * @param httpStatus
         *            the http status
         * @param message
         *            the message
         * @param cause
         *            the cause
         */
        public CMSRuntimeException(HttpStatus httpStatus, String message, Exception cause) {
            super(httpStatus, message, cause);
        }

        /**
         * Instantiates a new CMS runtime exception.
         *
         * @param httpStatus
         *            the http status
         * @param message
         *            the message
         */
        public CMSRuntimeException(HttpStatus httpStatus, String message) {
            super(httpStatus, message);
        }

        /**
         * Instantiates a new CMS runtime exception.
         *
         * @param message
         *            the message
         */
        public CMSRuntimeException(String message) {
            super(message);
        }
    }

    /**
     * The Class EvidenceFileDoesNotExistsException.
     */
    public static class AccessLevelRuleViolationException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -8876093293277370123L;

        /**
         * Instantiates a new evidence file does not exists exception.
         *
         * @param statusText
         *            the status text
         */
        public AccessLevelRuleViolationException(String statusText) {
            super(statusText);
        }

        /**
         * Instantiates a new access level rule violation exception.
         *
         * @param statusCode
         *            the status code
         * @param statusText
         *            the status text
         */
        public AccessLevelRuleViolationException(HttpStatus statusCode, String statusText) {
            super(statusCode, statusText);
        }
    }

    /**
     * The Class EvidenceFileDoesNotExistsException.
     */
    public static class InvalidGlobalIdException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 8073312251114248434L;

        /**
         * Instantiates a new evidence file does not exists exception.
         *
         * @param statusText
         *            the status text
         */
        public InvalidGlobalIdException(String statusText) {
            super(statusText);
        }
    }

    public static class CMSWebScriptException extends BaseRuntimeException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 8073312251114248434L;

        /**
         * Instantiates a new evidence file does not exists exception.
         *
         * @param statusText
         *            the status text
         */
        public CMSWebScriptException(HttpStatus statusCode, String statusText) {
            super(statusCode, statusText);
        }

        public CMSWebScriptException(HttpStatus statusCode, String statusText, Throwable throwable) {
            super(statusCode, statusText, throwable);
        }
    }

    public static class TmngContentTransformerNotFoundException extends BaseRuntimeException {
        private static final long serialVersionUID = 4743947491735936059L;

        public TmngContentTransformerNotFoundException(String statusText, Throwable throwable) {
            super(HttpStatus.INTERNAL_SERVER_ERROR, statusText, throwable);
        }
    }

}
