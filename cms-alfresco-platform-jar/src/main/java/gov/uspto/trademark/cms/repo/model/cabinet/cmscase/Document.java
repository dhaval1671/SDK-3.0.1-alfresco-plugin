package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

import org.codehaus.jackson.annotate.JsonUnwrapped;

import gov.uspto.trademark.cms.repo.model.TradeMarkContent;
import gov.uspto.trademark.cms.repo.model.aspects.ACLAspect;
import gov.uspto.trademark.cms.repo.model.aspects.ApplicationDates;
import gov.uspto.trademark.cms.repo.model.aspects.ApplicationSource;
import gov.uspto.trademark.cms.repo.model.aspects.CaseAspect;
import gov.uspto.trademark.cms.repo.model.aspects.LegacyDocumentTypeAspect;
import gov.uspto.trademark.cms.repo.model.aspects.MigratedAspect;
import gov.uspto.trademark.cms.repo.model.aspects.RedactedDocumentAspect;

/**
 * Document Type representation in the repository
 * <p/>
 * Created by bgummadi on 6/3/2014.
 */

public class Document extends TradeMarkContent {

    /** The Constant TYPE. */
    public static final String TYPE = "document";

    /** The document alias. */
    private String documentAlias;

    /** The created by user id. */
    private String createdByUserId;

    /** The modified by user id. */
    private String modifiedByUserId;

    /** The redaction level. */
    private String redactionLevel;

    /** The a cl aspect. */
    @JsonUnwrapped
    private ACLAspect aCLAspect = new ACLAspect();

    /** The migrated aspect. */
    @JsonUnwrapped
    private MigratedAspect migratedAspect = new MigratedAspect();

    /** The case aspect. */
    @JsonUnwrapped
    private CaseAspect caseAspect = new CaseAspect();

    /** The application source. */
    @JsonUnwrapped
    private ApplicationSource applicationSource = new ApplicationSource();

    /** The application dates. */
    @JsonUnwrapped
    private ApplicationDates applicationDates = new ApplicationDates();

    /** The legacy document type. */
    @JsonUnwrapped
    private LegacyDocumentTypeAspect legacyDocumentType = new LegacyDocumentTypeAspect();

    /** The redacted document. */
    @JsonUnwrapped
    private RedactedDocumentAspect redactedDocument = new RedactedDocumentAspect();

    /**
     * Gets the redacted document.
     *
     * @return the redacted document
     */
    public RedactedDocumentAspect getRedactedDocument() {
        return redactedDocument;
    }

    /**
     * Sets the redacted document.
     *
     * @param redactedDocument
     *            the new redacted document
     */
    public void setRedactedDocument(RedactedDocumentAspect redactedDocument) {
        this.redactedDocument = redactedDocument;
    }

    /**
     * Gets the legacy document type.
     *
     * @return the legacy document type
     */
    public LegacyDocumentTypeAspect getLegacyDocumentType() {
        return legacyDocumentType;
    }

    /**
     * Sets the legacy document type.
     *
     * @param legacyDocumentType
     *            the new legacy document type
     */
    public void setLegacyDocumentType(LegacyDocumentTypeAspect legacyDocumentType) {
        this.legacyDocumentType = legacyDocumentType;
    }

    /**
     * Gets the document alias.
     *
     * @return the document alias
     */
    public String getDocumentAlias() {
        return documentAlias;
    }

    /**
     * Sets the document alias.
     *
     * @param documentAlias
     *            the new document alias
     */
    public void setDocumentAlias(String documentAlias) {
        this.documentAlias = documentAlias;
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
     * Gets the application source.
     *
     * @return the application source
     */
    public ApplicationSource getApplicationSource() {
        return applicationSource;
    }

    /**
     * Sets the application source.
     *
     * @param applicationSource
     *            the new application source
     */
    public void setApplicationSource(ApplicationSource applicationSource) {
        this.applicationSource = applicationSource;
    }

    /**
     * Gets the application dates.
     *
     * @return the application dates
     */
    public ApplicationDates getApplicationDates() {
        applicationDates.setCreationTime(this.getCreationTime());
        return applicationDates;
    }

    /**
     * Sets the application dates.
     *
     * @param applicationDates
     *            the new application dates
     */
    public void setApplicationDates(ApplicationDates applicationDates) {
        this.applicationDates = applicationDates;
    }

    /**
     * Gets the case aspect.
     *
     * @return the case aspect
     */
    public CaseAspect getCaseAspect() {
        return caseAspect;
    }

    /**
     * Sets the case aspect.
     *
     * @param caseAspect
     *            the new case aspect
     */
    public void setCaseAspect(CaseAspect caseAspect) {
        this.caseAspect = caseAspect;
    }

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Document.TYPE;
    }

    /**
     * Gets the created by user id.
     *
     * @return the created by user id
     */
    public String getCreatedByUserId() {
        return createdByUserId;
    }

    /**
     * Sets the created by user id.
     *
     * @param createdByUserId
     *            the new created by user id
     */
    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    /**
     * Gets the modified by user id.
     *
     * @return the modified by user id
     */
    public String getModifiedByUserId() {
        return modifiedByUserId;
    }

    /**
     * Sets the modified by user id.
     *
     * @param modifiedByUserId
     *            the new modified by user id
     */
    public void setModifiedByUserId(String modifiedByUserId) {
        this.modifiedByUserId = modifiedByUserId;
    }

    /**
     * Gets the redaction level.
     *
     * @return the redaction level
     */
    public String getRedactionLevel() {
        return redactionLevel;
    }

    /**
     * Sets the redaction level.
     *
     * @param redactionLevel
     *            the new redaction level
     */
    public void setRedactionLevel(String redactionLevel) {
        this.redactionLevel = redactionLevel;
    }

}
