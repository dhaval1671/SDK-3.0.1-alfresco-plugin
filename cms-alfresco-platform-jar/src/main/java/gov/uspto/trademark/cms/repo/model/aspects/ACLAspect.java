package gov.uspto.trademark.cms.repo.model.aspects;

import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.AccessLevel;

/**
 * ACL aspect representation in the repository
 *
 * Created by bgummadi on 6/2/2014.
 */
public class ACLAspect extends AbstractBaseAspect {

    /** The access level. */
    private String accessLevel = "internal";

    /**
     * Gets the access level.
     *
     * @return the access level
     */
    public String getAccessLevel() {
        return accessLevel;
    }

    /**
     * Sets the access level.
     *
     * @param accessLevel
     *            the new access level
     */
    public void setAccessLevel(String accessLevel) {
        AccessLevel accessLevelEnum = AccessLevel.getAccessLevel(accessLevel);
        if (null == accessLevelEnum) {
            throw new TmngCmsException.AccessLevelRuleViolationException(HttpStatus.FORBIDDEN,
                    "'accessLevel' value NOT compliant to standard values");
        } else {
            this.accessLevel = accessLevel.toString();
        }
    }
}
