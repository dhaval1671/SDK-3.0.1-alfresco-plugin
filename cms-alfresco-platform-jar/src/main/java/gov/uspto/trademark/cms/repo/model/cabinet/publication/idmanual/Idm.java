package gov.uspto.trademark.cms.repo.model.cabinet.publication.idmanual;

import gov.uspto.trademark.cms.repo.model.TradeMarkContent;

/**
 * This object is used for IDM document type.
 * 
 * @author stank
 *
 */
public class Idm extends TradeMarkContent {

    /** The Constant TYPE. */
    public static final String TYPE = "id-manual";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Idm.TYPE;
    }

}
