package gov.uspto.trademark.cms.repo.identifiers;

import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TMConstants;

/**
 * Created by bgummadi on 8/17/2015.
 */
@Component("documentId")
public class DocumentId implements BehaviorIdStrategy {

    /**
     * Concrete ids will implement how the id is split into parts which will be
     * used to create folders under their respective locations
     *
     * @param id
     * @return
     */
    @Override
    public String[] split(String id) {
        return (null == id) ? new String[] {} : id.split(TMConstants.FORWARD_SLASH);
    }

    /**
     * Validates the id format
     *
     * @param id
     * @return
     */
    @Override
    public boolean isValid(String id) {
        return true;
    }
}
