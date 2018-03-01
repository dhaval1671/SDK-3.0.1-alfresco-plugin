package gov.uspto.trademark.cms.repo.model.cabinet.publication.officialgazette;

import gov.uspto.trademark.cms.repo.model.TradeMarkContent;

/**
 * This domain object is used for EoG document types.
 *
 * @author stank
 */
public class Eog extends TradeMarkContent {

    /** The Constant TYPE. */
    public static final String TYPE = "official-gazette";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Eog.TYPE;
    }

}
