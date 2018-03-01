package gov.uspto.trademark.cms.repo.helpers;

import gov.uspto.trademark.cms.repo.services.BehaviorImageMarkService;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.services.EvidenceService;
import gov.uspto.trademark.cms.repo.services.VersionService;

/**
 * Created by bgummadi on 5/9/2014.
 */
public class TMServiceRegistry {

    /** The case service. */
    private CaseService caseService;

    /** The evidence service. */
    private EvidenceService evidenceService;

    /** The image mark service. */
    private BehaviorImageMarkService imageMarkService;

    /** The version service. */
    private VersionService versionService;

    /**
     * Gets the case service.
     *
     * @return the case service
     */
    public CaseService getCaseService() {
        return caseService;
    }

    /**
     * Sets the case service.
     *
     * @param caseService
     *            the new case service
     */
    public void setCaseService(CaseService caseService) {
        this.caseService = caseService;
    }

    /**
     * Gets the evidence service.
     *
     * @return the evidence service
     */
    public EvidenceService getEvidenceService() {
        return evidenceService;
    }

    /**
     * Sets the evidence service.
     *
     * @param evidenceService
     *            the new evidence service
     */
    public void setEvidenceService(EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    /**
     * Gets the trade mark service.
     *
     * @return the trade mark service
     */
    public BehaviorImageMarkService getTradeMarkService() {
        return imageMarkService;
    }

    /**
     * Sets the trade mark service.
     *
     * @param imageMarkService
     *            the new trade mark service
     */
    public void setTradeMarkService(BehaviorImageMarkService imageMarkService) {
        this.imageMarkService = imageMarkService;
    }

    /**
     * Gets the version service.
     *
     * @return the version service
     */
    public VersionService getVersionService() {
        return versionService;
    }

    /**
     * Sets the version service.
     *
     * @param versionService
     *            the new version service
     */
    public void setVersionService(VersionService versionService) {
        this.versionService = versionService;
    }
}
