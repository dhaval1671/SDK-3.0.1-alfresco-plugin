package gov.uspto.trademark.cms.repo.identifiers;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;

/**
 * Created by bgummadi on 8/10/2015.
 */
@Component("globalId")
public class GlobalId implements BehaviorIdStrategy {

    /**
     * 
     */
    private static final String INVALID_GLOBAL_ID = "Invalid GlobalId";
    public static final String ID_SEPARATOR = ":";

    /**
     * Concrete ids will implement how the id is split into parts which will be
     * used to create folders under their respective locations
     *
     * @param id
     * @return
     */
    @Override
    public String[] split(String id) {
        this.isValid(id);
        return id.split(ID_SEPARATOR);
    }

    /**
     * Validates the id format
     *
     * @param globalId
     * @return
     */
    @Override
    public boolean isValid(String globalId) {
        if (globalId == null || "".equals(globalId.trim())) {
            throw new TmngCmsException.InvalidGlobalIdException(GlobalId.INVALID_GLOBAL_ID);
        }

        String[] idPart = globalId.split(ID_SEPARATOR, -1);
        if (idPart.length != TMConstants.THREE) {
            throw new TmngCmsException.InvalidGlobalIdException(GlobalId.INVALID_GLOBAL_ID);
        }

        if (!NumberUtils.isNumber(idPart[TMConstants.ONE])) {
            throw new TmngCmsException.InvalidGlobalIdException(GlobalId.INVALID_GLOBAL_ID);
        }

        if (!NumberUtils.isNumber(idPart[TMConstants.TWO])) {
            throw new TmngCmsException.InvalidGlobalIdException(GlobalId.INVALID_GLOBAL_ID);
        }

        return true;
    }

}
