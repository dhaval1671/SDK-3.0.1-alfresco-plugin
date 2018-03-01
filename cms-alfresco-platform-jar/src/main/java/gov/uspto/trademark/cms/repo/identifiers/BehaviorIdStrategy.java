package gov.uspto.trademark.cms.repo.identifiers;

import org.springframework.stereotype.Component;

/**
 * Created by bgummadi on 8/10/2015.
 */
@Component
public interface BehaviorIdStrategy {

    /**
     * Concrete ids will implement how the id is split into parts which will be
     * used to create folders under their respective locations
     * 
     * @return
     */
    String[] split(String id);

    /**
     * Validates the id format
     *
     * @param id
     * @return
     */
    boolean isValid(String id);

}
