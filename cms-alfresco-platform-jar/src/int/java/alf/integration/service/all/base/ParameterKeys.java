package alf.integration.service.all.base;

/**
 * The Enum ParameterKeys.
 */
public enum ParameterKeys {

    /** The url. */
    URL ("url"),
    
    /** The serial number. */
    SERIAL_NUMBER ("sn"),
    
    /** The id. */
    ID ("id"),
    
    /** The file name. */
    FILE_NAME ("fileName"),
    
    /** The metadata. */
    METADATA ("metadata"),
    
    /** The version. */
    VERSION ("version"),
    
    /** The content. */
    CONTENT ("content"),
    
    /** The content attachement. */
    CONTENT_ATTACHEMENT ("contentAttachment")
    ;

    /** The parameter keys. */
    private final String parameterKeys;       

    /**
     * Instantiates a new parameter keys.
     *
     * @param s the s
     */
    private ParameterKeys(String s) {
        parameterKeys = s;
    }

    /**
     * Equals name.
     *
     * @param otherName the other name
     * @return true, if successful
     */
    public boolean equalsName(String otherName){
        return (otherName == null)? false:parameterKeys.equals(otherName);
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    public String toString(){
       return parameterKeys;
    }

}