package gov.uspto.trademark.cms.repo.constants;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.namespace.QName;

import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.model.cabinet.madridib.Madrid;

/**
 * Created by stank on Jun/6/2015.
 */
public enum TradeMarkMadridTypes {

    /** The type eog. */
    TYPE_MADRID(Madrid.TYPE, true, Madrid.class),

    /** The type eog top level folder. */
    TYPE_MADRID_FOLDER(TradeMarkModel.MADRIDIB_FOLDER_NAME, false);

    /** The Constant supportedCaseTypes. */
    private static final Map<String, TradeMarkMadridTypes> SUPPORTED_MADRID_TYPES = new HashMap<String, TradeMarkMadridTypes>();

    /** The Constant supportedCaseQNames. */
    private static final Map<String, QName> SUPPORTED_MADRID_QNAMES = new HashMap<String, QName>();

    static {
        for (TradeMarkMadridTypes tmType : EnumSet.allOf(TradeMarkMadridTypes.class)) {
            SUPPORTED_MADRID_TYPES.put(tmType.getAlfrescoTypeName(), tmType);
            if (tmType.isMadridDocument) {
                SUPPORTED_MADRID_QNAMES.put(tmType.getAlfrescoTypeName(), createQName(tmType.getAlfrescoTypeName()));
            }
        }
    }

    /** The alfresco type name. */
    private String alfrescoTypeName;

    /** The is case document. */
    private boolean isMadridDocument;

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
    TradeMarkMadridTypes(String typeName, boolean isPublicationDocument) {
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
    TradeMarkMadridTypes(String typeName, boolean isPublicationDocument, Class<? extends AbstractBaseType> typeClass) {
        this.alfrescoTypeName = typeName;
        this.isMadridDocument = isPublicationDocument;
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
    public static TradeMarkMadridTypes getMadridType(String typeName) {
        return SUPPORTED_MADRID_TYPES.get(typeName);
    }

    /**
     * Gets the cms model class type.
     *
     * @param typeName
     *            the type name
     * @return the cms model class type
     */
    public static Class<? extends AbstractBaseType> getMadridModelClassType(String typeName) {
        return SUPPORTED_MADRID_TYPES.get(typeName).typeClass;
    }

    /**
     * Gets the trade mark q name.
     *
     * @param typeName
     *            the type name
     * @return the trade mark q name
     */
    public static QName getMadridQName(String typeName) {
        return SUPPORTED_MADRID_QNAMES.get(typeName);
    }

    /**
     * Gets the supported case q names.
     *
     * @return the supported case q names
     */
    public static Set<QName> getSupportedMadridQNames() {
        return new HashSet<QName>(SUPPORTED_MADRID_QNAMES.values());
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
    public boolean isMadridDocument() {
        return isMadridDocument;
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
