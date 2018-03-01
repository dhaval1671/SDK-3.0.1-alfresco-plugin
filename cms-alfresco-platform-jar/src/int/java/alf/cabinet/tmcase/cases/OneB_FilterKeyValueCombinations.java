package alf.cabinet.tmcase.cases;


/**
 * Created by stank on 01/12/2016
 */
public enum OneB_FilterKeyValueCombinations {

    USECASE_001        (null,                         null,                          "public",              200,          03),
    USECASE_002        (null,                         "",                            "public",              200,          03),
    USECASE_003        (null,                         "yyy",                         "public",              200,          03),
    USECASE_004        (null,                         "E",                           "public",              200,          03),
    USECASE_005        ("",                           null,                          "public",              200,          03),
    USECASE_009        ("xxx",                        null,                          "public",              200,          03),
    USECASE_013        ("sourceMedia",                null,                          "public",              200,          03),
    USECASE_017        ("docCode",                    null,                          "public",              200,          03),
    USECASE_021        (null,                         "_ABSENT_",                    "public",              200,          03),
    USECASE_022        ("",                           "_ABSENT_",                    "public",              200,          03),
    USECASE_023        ("xxx",                        "_ABSENT_",                    "public",              200,          03),
    USECASE_006        ("",                           "",                            "public",              200,          00),
    USECASE_007        ("",                           "yyy",                         "public",              200,          00),
    USECASE_008        ("",                           "E",                           "public",              200,          00),    
    USECASE_010        ("xxx",                        "",                            "public",              200,          00),
    USECASE_011        ("xxx",                        "yyy",                         "public",              200,          00),
    USECASE_012        ("xxx",                        "E",                           "public",              200,          00),
    USECASE_014        ("sourceMedia",                "",                            "public",              200,          00),
    USECASE_015        ("sourceMedia",                "yyy",                         "public",              200,          00),
    USECASE_018        ("docCode",                    "",                            "public",              200,          00),
    USECASE_019        ("docCode",                    "E",                           "public",              200,          00),
    USECASE_024        ("sourceMedia",                "_ABSENT_",                    "public",              200,          00),    
    USECASE_016        ("sourceMedia",                "electronic",                  "public",              200,          03),
    USECASE_020        ("docCode",                    "LGCY",                        "public",              200,          01),
    USECASE_025        ("docCode",                    "_ABSENT_",                    "public",              200,          02);    

    public static final String FILTER_KEY = "filterKey";    
    public static final String FILTER_VALUE = "filterValue";
    public static final String ACCESS_LEVEL = "accessLevel";
    
    private String filterKey;
    private String filterValue;
    private String accessLevel;
    private int responseCode;
    private int documentCount;
    
    

    /**
     * Instantiates a new trade mark document types.
     *
     * @param filterKey
     *            the type name
     * @param isCaseDocument
     *            the is case document
     * @param typeClass
     *            the type class
     */
    OneB_FilterKeyValueCombinations(String filterKey, String filterValue, String accessLevel, int responseCode, int documentCount) {
        this.filterKey = filterKey;
        this.filterValue = filterValue;
        this.accessLevel = accessLevel;
        this.responseCode = responseCode;
        this.documentCount = documentCount;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getDocumentCount() {
        return documentCount;
    }


}
