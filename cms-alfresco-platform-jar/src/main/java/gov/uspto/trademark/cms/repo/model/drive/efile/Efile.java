package gov.uspto.trademark.cms.repo.model.drive.efile;

import org.codehaus.jackson.annotate.JsonUnwrapped;

import gov.uspto.trademark.cms.repo.model.TradeMarkContent;
import gov.uspto.trademark.cms.repo.model.aspects.EfileAspect;

/**
 * Created by stank on Sep/25/2014.
 */
public class Efile extends TradeMarkContent {

    /** The Constant TYPE. */
    public static final String TYPE = "e-file";

    /** The id. */
    private String id;

    /** The efile aspect. */
    @JsonUnwrapped
    private EfileAspect efileAspect = new EfileAspect();

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the efile aspect.
     *
     * @return the efile aspect
     */
    public EfileAspect getEfileAspect() {
        return efileAspect;
    }

    /**
     * Sets the efile aspect.
     *
     * @param efileAspect
     *            the new efile aspect
     */
    public void setEfileAspect(EfileAspect efileAspect) {
        this.efileAspect = efileAspect;
    }

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Efile.TYPE;
    }

}
