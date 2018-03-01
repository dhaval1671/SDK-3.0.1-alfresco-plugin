package gov.uspto.trademark.cms.repo.identifiers;

import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;

/**
 * Created by bgummadi on 8/10/2015.
 */
@Component("proceedingNumberId")
public class ProceedingNumberId implements BehaviorIdStrategy {

    /**
     * 
     */
    private static final String INVALID_GLOBAL_ID = "Invalid Proceeding Number.";
    public static final String ID_SEPARATOR = "";

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

    @Override
    public boolean isValid(String proceedingNumber) {
        if (proceedingNumber == null || "".equals(proceedingNumber.trim())) {
            throw new TmngCmsException.InvalidGlobalIdException(
                    "Proceeding Number Blank or null. " + ProceedingNumberId.INVALID_GLOBAL_ID);
        }
        return true;
    }

}
