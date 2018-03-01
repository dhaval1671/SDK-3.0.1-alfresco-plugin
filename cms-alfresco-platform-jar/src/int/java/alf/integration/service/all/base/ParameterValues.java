package alf.integration.service.all.base;

/**
 * The Enum ParameterValues.
 */
public enum ParameterValues {

    /** The VALU e_ efil e_7777_ trademarkid. */
    VALUE_EFILE_GID_ABC_777_123_TRADEMARKID("abc:777:123"),

    /** The VALU e_ efil e_7778_ trademarkid. */
    VALUE_EFILE_MJK_777_008_TRADEMARKID("mjk:777:008"),

    /** The VALU e_ efil e_7779_ trademarkid. */
    VALUE_EFILE_UPR_777_009_TRADEMARKID("upr:777:009"),

    /** The VALU e_ efil e_7780_ trademarkid. */
    VALUE_EFILE_GID_DEF_778_456_TRADEMARKID("def:778:456"),

    /** The VALU e_ efil e_7781_ trademarkid. */
    VALUE_EFILE_GEH_778_001_TRADEMARKID("geh:778:001"),
    
    VALUE_SERIAL_77777771_NUMBER("77777771"), 
    VALUE_SERIAL_77777772_NUMBER("77777772"), 
    VALUE_SERIAL_77777773_NUMBER("77777773"), 
    VALUE_SERIAL_77777774_NUMBER("77777774"), 
    VALUE_SERIAL_77777775_NUMBER("77777775"), 
    VALUE_SERIAL_77777776_NUMBER("77777776"),    

    /** The VALU e_ seria l_77777777_ number. */
    VALUE_SERIAL_77777777_NUMBER("77777777"),

    /** The VALU e_ seria l_77777778_ number. */
    VALUE_SERIAL_77777778_NUMBER("77777778"),

    /** The VALU e_ seria l_77777779_ number. */
    VALUE_SERIAL_77777779_NUMBER("77777779"),

    /** The VALU e_ seria l_77777780_ number. */
    VALUE_SERIAL_77777780_NUMBER("77777780"),

    /** The VALU e_ seria l_77777781_ number. */
    VALUE_SERIAL_77777781_NUMBER("77777781"),

    /** The VALU e_ seria l_77777782_ number. */
    VALUE_SERIAL_77777782_NUMBER("77777782"),

    /** The VALU e_ seria l_77777783_ number. */
    VALUE_SERIAL_77777783_NUMBER("77777783"),

    VALUE_SERIAL_77777784_NUMBER("77777784"), 
    VALUE_SERIAL_77777785_NUMBER("77777785"),
    VALUE_SERIAL_77777786_NUMBER("77777786"),
    //START: MOR testing
    VALUE_SERIAL_77777787_NUMBER("77777787"),
    VALUE_SERIAL_77777788_NUMBER("77777788"),
    VALUE_SERIAL_77777789_NUMBER("77777789"),
    VALUE_SERIAL_77777790_NUMBER("77777790"),
    VALUE_SERIAL_77777791_NUMBER("77777791"),
    //END: MOR testing
    
    /** The VALU e_ seria l_77777801_ number. */
    VALUE_SERIAL_77777801_NUMBER("77777801"),

    /** The VALU e_ seria l_77777802_ number. */
    VALUE_SERIAL_77777802_NUMBER("77777802"),

    /** The VALU e_ seria l_77777803_ number. */
    VALUE_SERIAL_77777803_NUMBER("77777803"),

    /** The VALU e_ seria l_77777804_ number. */
    VALUE_SERIAL_77777804_NUMBER("77777804"),

    VALUE_SERIAL_77777805_TICRS_ADMIN_DELETE("77777805"), 
    VALUE_SERIAL_77777806_TICRS("77777806"), 
    VALUE_SERIAL_77777807_TICRS("77777807"),

    /** The VALU e_ invali d_ seria l_ numbe r_ forma t1. */
    VALUE_INVALID_SERIAL_NUMBER_FORMAT1("abc"),

    /** The VALU e_ invali d_ seria l_ numbe r_ forma t2. */
    VALUE_INVALID_SERIAL_NUMBER_FORMAT2("1234"),

    /** The VALU e_ invali d_ seria l_ numbe r_ forma t3. */
    VALUE_INVALID_SERIAL_NUMBER_FORMAT3("5656a45"),

    /** The VALU e_ invali d_ seria l_ numbe r_ forma t4. */
    VALUE_INVALID_SERIAL_NUMBER_FORMAT4("7777777"),

    /** The VALU e_ invali d_ seria l_ numbe r_ forma t5. */
    VALUE_INVALID_SERIAL_NUMBER_FORMAT5("77777777@"),

    /** The VALU e_ invali d_ seria l_ numbe r_ forma t6. */
    VALUE_INVALID_SERIAL_NUMBER_FORMAT6("o000000$"),

    /** The VALU e_ invali d_ seria l_ numbe r_ forma t7. */
    VALUE_INVALID_SERIAL_NUMBER_FORMAT7("oooooo"),

    /** The VALU e_ invali d_ seria l_ numbe r_ forma t8. */
    VALUE_INVALID_SERIAL_NUMBER_FORMAT8(","),

    /** The VALU e_ invali d_ seria l_ numbe r_ forma t9. */
    VALUE_INVALID_SERIAL_NUMBER_FORMAT9("77,77,777"),

    /** The VALU e_ vali d_ seria l_ numbe r_ forma t1. */
    VALUE_VALID_SERIAL_NUMBER_FORMAT1("00000000,00000001"),

    /** The VALU e_ vali d_ seria l_ numbe r_ forma t2. */
    VALUE_VALID_SERIAL_NUMBER_FORMAT2("98765432,"),

    /** The VALU e_ vali d_ seria l_ numbe r_ forma t3. */
    VALUE_VALID_SERIAL_NUMBER_FORMAT3("98765432,98765431,"),
    
    VALUE_SERIAL_88888888_NUMBER("88888888"), 

    ;

    /** The parameter values. */
    private final String parameterValues;

    /**
     * Instantiates a new parameter values.
     *
     * @param s
     *            the s
     */
    private ParameterValues(String s) {
        parameterValues = s;
    }

    /**
     * Equals name.
     *
     * @param otherName
     *            the other name
     * @return true, if successful
     */
    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : parameterValues.equals(otherName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    public String toString() {
        return parameterValues;
    }

}