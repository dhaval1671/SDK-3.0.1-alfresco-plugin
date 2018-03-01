package gov.uspto.trademark.cms.repo.identifiers;

import org.springframework.stereotype.Component;

/**
 * Created by bgummadi on 8/10/2015.
 */
@Component("serialNumberId")
public class SerialNumberId implements BehaviorIdStrategy {
    /**
     * Concrete ids will implement how the id is split into parts which will be
     * used to create folders under their respective locations
     *
     * @return
     */
    @Override
    public String[] split(String id) {
        return new String[0];
    }

    /**
     * Validates the id format
     *
     * @param id
     * @return
     */
    @Override
    public boolean isValid(String id) {
        return false;
    }
}
