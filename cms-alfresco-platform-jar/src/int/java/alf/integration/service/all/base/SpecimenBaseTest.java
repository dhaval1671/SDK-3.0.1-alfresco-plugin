package alf.integration.service.all.base;

/**
 * The Class SpecimenBaseTest.
 */
public class SpecimenBaseTest extends CentralBase{

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/specimen/";
    
    /** The Constant VALUE_SPECIMEN_FILE_NAME. */
    public static final String VALUE_SPECIMEN_FILE_NAME = "Specimen_1_Version1.0.docx";
    
    /** The Constant INVALID_FILE_NAME. */
    protected static final String INVALID_FILE_NAME = "jnkSpecimen.pdf";

    /** The Constant VALUE_SPECIMEN_METADATA. */
    public static final String VALUE_SPECIMEN_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateSpecimenIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateSpecimenIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_SPECIMEN_ATTACHMENT. */
    public static final String CONTENT_SPECIMEN_ATTACHMENT = "src//test//resources//specimen//specimen.pdf";

}
