package gov.uspto.trademark.cms.repo.constants;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.namespace.QName;

import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Brief;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Decision;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Exhibit;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Motion;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Order;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Pleading;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Undesignated;

/**
 * Created by stank on Nov/22/2016.
 */
public enum TradeMarkLegalProceedingTypes {

    /** The type legal-proceeding. */
    TYPE_PLEADING(Pleading.TYPE, true, Pleading.class), TYPE_MOTION(Motion.TYPE, true, Motion.class), TYPE_EXHIBIT(Exhibit.TYPE, true, Exhibit.class), TYPE_BRIEF(Brief.TYPE, true, Brief.class), TYPE_ORDER(Order.TYPE, true, Order.class), TYPE_DECISION(Decision.TYPE, true, Decision.class), TYPE_UNDESIGNATED(Undesignated.TYPE, true, Undesignated.class),

    /** The type legal-proceeding top level folder. */
    TYPE_LEGAL_PROCEEDING_FOLDER(TradeMarkModel.LEGAL_PROCEEDING_FOLDER_NAME, false);

    /** The Constant supportedCaseTypes. */
    private static final Map<String, TradeMarkLegalProceedingTypes> SUPPORTED_LEGAL_PROCEEDING_TYPES = new HashMap<String, TradeMarkLegalProceedingTypes>();

    /** The Constant supportedCaseQNames. */
    private static final Map<String, QName> SUPPORTED_LEGAL_PROCEEDING_QNAMES = new HashMap<String, QName>();

    static {
        for (TradeMarkLegalProceedingTypes tmType : EnumSet.allOf(TradeMarkLegalProceedingTypes.class)) {
            SUPPORTED_LEGAL_PROCEEDING_TYPES.put(tmType.getAlfrescoTypeName(), tmType);
            if (tmType.isLegalProceedingDocument) {
                SUPPORTED_LEGAL_PROCEEDING_QNAMES.put(tmType.getAlfrescoTypeName(), createQName(tmType.getAlfrescoTypeName()));
            }
        }
    }

    /** The alfresco type name. */
    private String alfrescoTypeName;

    /** The is case document. */
    private boolean isLegalProceedingDocument;

    /** The type class. */
    private Class<? extends AbstractBaseType> typeClass;

    /**
     * Instantiates a new trade mark document types.
     *
     * @param typeName
     *            the type name
     * @param isPublicationDocument
     *            the is case document
     */
    TradeMarkLegalProceedingTypes(String typeName, boolean isPublicationDocument) {
        this(typeName, isPublicationDocument, null);
    }

    /**
     * Instantiates a new trade mark document types.
     *
     * @param typeName
     *            the type name
     * @param isPublicationDocument
     *            the is case document
     * @param typeClass
     *            the type class
     */
    TradeMarkLegalProceedingTypes(String typeName, boolean isPublicationDocument, Class<? extends AbstractBaseType> typeClass) {
        this.alfrescoTypeName = typeName;
        this.isLegalProceedingDocument = isPublicationDocument;
        this.typeClass = typeClass;
    }

    /**
     * Creates the q name.
     *
     * @param tmType
     *            the tm type
     * @return the q name
     */
    private static QName createQName(String tmType) {
        return QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI, tmType);
    }

    /**
     * Gets the trade mark type.
     *
     * @param typeName
     *            the type name
     * @return the trade mark type
     */
    public static TradeMarkLegalProceedingTypes getLegalProceedingType(String typeName) {
        return SUPPORTED_LEGAL_PROCEEDING_TYPES.get(typeName);
    }

    /**
     * Gets the cms model class type.
     *
     * @param typeName
     *            the type name
     * @return the cms model class type
     */
    public static Class<? extends AbstractBaseType> getLegalProceedingModelClassType(String typeName) {
        return SUPPORTED_LEGAL_PROCEEDING_TYPES.get(typeName).typeClass;
    }

    /**
     * Gets the trade mark q name.
     *
     * @param typeName
     *            the type name
     * @return the trade mark q name
     */
    public static QName getLegalProceedingQName(String typeName) {
        return SUPPORTED_LEGAL_PROCEEDING_QNAMES.get(typeName);
    }

    /**
     * Gets the supported case q names.
     *
     * @return the supported case q names
     */
    public static Set<QName> getSupportedLegalProceedingQNames() {
        return new HashSet<QName>(SUPPORTED_LEGAL_PROCEEDING_QNAMES.values());
    }

    /**
     * Gets the alfresco type name.
     *
     * @return the alfresco type name
     */
    public String getAlfrescoTypeName() {
        return this.alfrescoTypeName;
    }

    /**
     * Checks if is case document.
     *
     * @return true, if is case document
     */
    public boolean isLegalProceedingDocument() {
        return isLegalProceedingDocument;
    }

    /**
     * Gets the type class.
     *
     * @return the type class
     */
    public Class<? extends AbstractBaseType> getTypeClass() {
        return typeClass;
    }

}
