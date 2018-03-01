package gov.uspto.trademark.cms.repo.webscripts.beans;

import gov.uspto.trademark.cms.repo.model.AbstractBaseType;

/**
 * Created by bgummadi on 9/10/2014.
 */
public class EfileDocumentMetadataResponse extends EfileResponse {

    /** The metadata. */
    private AbstractBaseType metadata;

    public AbstractBaseType getMetadata() {
        return metadata;
    }

    public void setMetadata(AbstractBaseType metadata) {
        this.metadata = metadata;
    }

    public String getDocumentType() {
        return this.getMetadata().getDocumentType();
    }

}
