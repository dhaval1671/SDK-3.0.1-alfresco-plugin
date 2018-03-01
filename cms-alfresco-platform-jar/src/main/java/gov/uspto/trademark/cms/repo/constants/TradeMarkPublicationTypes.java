package gov.uspto.trademark.cms.repo.constants;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.namespace.QName;

import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.idmanual.Idm;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.officialgazette.Eog;

/**
 * Created by stank on Jun/6/2015.
 */
public enum TradeMarkPublicationTypes {

    /** The type eog. */
    TYPE_EOG(Eog.TYPE, true, Eog.class),

    /** The type idmanual. */
    TYPE_IDM(Idm.TYPE, true, Idm.class),

    /** The type eog top level folder. */
    TYPE_EOG_FOLDER(TradeMarkModel.TYPE_EOG_FOLDER, false),

    /** The type idm top level folder. */
    TYPE_IDM_FOLDER(TradeMarkModel.TYPE_IDM_FOLDER, false);

    /** The Constant supportedCaseTypes. */
    private static final Map<String, TradeMarkPublicationTypes> SUPPORTED_PUBLICATION_TYPES = new HashMap<String, TradeMarkPublicationTypes>();

    /** The Constant supportedCaseQNames. */
    private static final Map<String, QName> SUPPORTED_PUBLICATION_QNAMES = new HashMap<String, QName>();

    static {
        for (TradeMarkPublicationTypes tmType : EnumSet.allOf(TradeMarkPublicationTypes.class)) {
            SUPPORTED_PUBLICATION_TYPES.put(tmType.getAlfrescoTypeName(), tmType);
            if (tmType.isPublicationDocument) {
                SUPPORTED_PUBLICATION_QNAMES.put(tmType.getAlfrescoTypeName(), createQName(tmType.getAlfrescoTypeName()));
            }
        }
    }

    /** The alfresco type name. */
    private String alfrescoTypeName;

    /** The is case document. */
    private boolean isPublicationDocument;

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
    TradeMarkPublicationTypes(String typeName, boolean isPublicationDocument) {
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
    TradeMarkPublicationTypes(String typeName, boolean isPublicationDocument, Class<? extends AbstractBaseType> typeClass) {
        this.alfrescoTypeName = typeName;
        this.isPublicationDocument = isPublicationDocument;
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
    public static TradeMarkPublicationTypes getPublicationType(String typeName) {
        return SUPPORTED_PUBLICATION_TYPES.get(typeName);
    }

    /**
     * Gets the cms model class type.
     *
     * @param typeName
     *            the type name
     * @return the cms model class type
     */
    public static Class<? extends AbstractBaseType> getPublicationModelClassType(String typeName) {
        return SUPPORTED_PUBLICATION_TYPES.get(typeName).typeClass;
    }

    /**
     * Gets the trade mark q name.
     *
     * @param typeName
     *            the type name
     * @return the trade mark q name
     */
    public static QName getPublicationQName(String typeName) {
        return SUPPORTED_PUBLICATION_QNAMES.get(typeName);
    }

    /**
     * Gets the supported case q names.
     *
     * @return the supported case q names
     */
    public static Set<QName> getSupportedPublicationQNames() {
        return new HashSet<QName>(SUPPORTED_PUBLICATION_QNAMES.values());
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
    public boolean isPublicationDocument() {
        return isPublicationDocument;
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
