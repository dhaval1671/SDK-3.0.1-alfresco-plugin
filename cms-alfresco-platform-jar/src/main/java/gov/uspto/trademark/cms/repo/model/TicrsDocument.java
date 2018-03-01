package gov.uspto.trademark.cms.repo.model;

import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Document;

/**
 * Created by stank on May/03/2016
 */
public class TicrsDocument extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "ticrsDocument";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return TicrsDocument.TYPE;
    }

}
