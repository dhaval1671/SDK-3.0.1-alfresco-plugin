package gov.uspto.trademark.cms.repo.model.cabinet.madridib;

import gov.uspto.trademark.cms.repo.model.TradeMarkContent;

/**
 * Created by stank on Jul/06/2016.
 */
public class Madrid extends TradeMarkContent {

    /** The Constant TYPE. */
    public static final String TYPE = "madrid";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Madrid.TYPE;
    }

}
