package alf.integration.service.all.base;

/**
 * The Enum ParameterValues.
 */
public enum EogParameterValues {



    /** The VALU e_ seria l_77777777_ number. */
    DATE_20150609 ("20150609"),
    
    /** The DAT e_20150631. */
    DATE_20150631 ("20150631"), /*invalid date*/
    /** The DAT e_ folde r_ doe s_ no t_ exis t_20150632. */
 DATE_FOLDER_DOES_NOT_EXIST_20150632 ("20150632")
    

    ;

    /** The parameter values. */
    private final String parameterValues;       

    /**
     * Instantiates a new parameter values.
     *
     * @param s the s
     */
    private EogParameterValues(String s) {
        parameterValues = s;
    }

    /**
     * Equals name.
     *
     * @param otherName the other name
     * @return true, if successful
     */
    public boolean equalsName(String otherName){
        return (otherName == null)? false:parameterValues.equals(otherName);
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    public String toString(){
       return parameterValues;
    }

}