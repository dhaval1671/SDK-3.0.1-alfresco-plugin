package gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding;

import org.codehaus.jackson.annotate.JsonUnwrapped;

import gov.uspto.trademark.cms.repo.model.TradeMarkContent;
import gov.uspto.trademark.cms.repo.model.aspects.ACLAspect;
import gov.uspto.trademark.cms.repo.model.aspects.BusinessVersionAspect;
import gov.uspto.trademark.cms.repo.model.aspects.MigratedAspect;

/**
 * Legal-Proceeding Type representation in the repository
 * <p/>
 * Created by stank on Nov/16/2016.
 */

public class LegalProceeding extends TradeMarkContent {

    private String proceedingNumber;
    private String proceedingType;
    private String entryDate;
    private String identifier;

    /** The Constant TYPE. */
    public static final String TYPE = "legal-proceeding";

    /** The a cl aspect. */
    @JsonUnwrapped
    private ACLAspect aCLAspect = new ACLAspect();

    /** The migrated aspect. */
    @JsonUnwrapped
    private MigratedAspect migratedAspect = new MigratedAspect();

    /** The business version aspect. */
    @JsonUnwrapped
    private BusinessVersionAspect businessVersionAspect = new BusinessVersionAspect();

    public String getProceedingNumber() {
        return proceedingNumber;
    }

    public void setProceedingNumber(String proceedingNumber) {
        this.proceedingNumber = proceedingNumber;
    }

    public String getProceedingType() {
        return proceedingType;
    }

    public void setProceedingType(String proceedingType) {
        this.proceedingType = proceedingType;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public BusinessVersionAspect getBusinessVersionAspect() {
        return businessVersionAspect;
    }

    public void setBusinessVersionAspect(BusinessVersionAspect businessVersionAspect) {
        this.businessVersionAspect = businessVersionAspect;
    }

    /**
     * Gets the a cl aspect.
     *
     * @return the a cl aspect
     */
    public ACLAspect getaCLAspect() {
        return aCLAspect;
    }

    /**
     * Sets the a cl aspect.
     *
     * @param aCLAspect
     *            the new a cl aspect
     */
    public void setaCLAspect(ACLAspect aCLAspect) {
        this.aCLAspect = aCLAspect;
    }

    /**
     * Gets the migrated aspect.
     *
     * @return the migrated aspect
     */
    public MigratedAspect getMigratedAspect() {
        return migratedAspect;
    }

    /**
     * Sets the migrated aspect.
     *
     * @param migratedAspect
     *            the new migrated aspect
     */
    public void setMigratedAspect(MigratedAspect migratedAspect) {
        this.migratedAspect = migratedAspect;
    }

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return LegalProceeding.TYPE;
    }

}
