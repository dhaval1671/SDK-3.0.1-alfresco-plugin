package gov.uspto.trademark.cms.repo.constants;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.namespace.QName;

import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Application;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Attachment;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Document;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Legacy;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Note;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Notice;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.OfficeAction;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Receipt;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.RegistrationCertificate;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Response;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Signature;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Specimen;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Summary;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.TeasPdf;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.UpdatedRegistrationCertificate;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Withdrawal;
import gov.uspto.trademark.cms.repo.model.drive.efile.Efile;

/**
 * Created by bgummadi on 4/1/2014.
 */
public enum TradeMarkDocumentTypes {

    /** The type document. */
    TYPE_DOCUMENT(TradeMarkModel.TYPE_DOCUMENT, true, Document.class),

    /** The type mark. */
    TYPE_MARK(MarkDoc.TYPE, true, MarkDoc.class),

    /** The type evidence. */
    TYPE_EVIDENCE(Evidence.TYPE, true, Evidence.class),

    /** The type office action. */
    TYPE_OFFICE_ACTION(OfficeAction.TYPE, true, OfficeAction.class),

    /** The type note. */
    TYPE_NOTE(Note.TYPE, true, Note.class),

    /** The type legacy. */
    TYPE_LEGACY(Legacy.TYPE, true, Legacy.class),

    /** The type notice. */
    TYPE_NOTICE(Notice.TYPE, true, Notice.class),

    /** The type summary. */
    TYPE_SUMMARY(Summary.TYPE, true, Summary.class),

    /** The type response. */
    TYPE_RESPONSE(Response.TYPE, true, Response.class),

    /** The type receipt. */
    TYPE_RECEIPT(Receipt.TYPE, true, Receipt.class),

    /** Supplemental Attachment type. */
    TYPE_ATTACHMENT(Attachment.TYPE, true, Attachment.class),

    /** The type signature. */
    TYPE_SIGNATURE(Signature.TYPE, true, Signature.class),

    /** The type specimen. */
    TYPE_SPECIMEN(Specimen.TYPE, true, Specimen.class),

    /** The type withdrawal. */
    TYPE_WITHDRAWAL(Withdrawal.TYPE, true, Withdrawal.class),

    /** The type application. */
    TYPE_APPLICATION(Application.TYPE, true, Application.class),

    /** The type registration-certificate. */
    TYPE_REGISTRATION_CERTIFICATE(RegistrationCertificate.TYPE, true, RegistrationCertificate.class),

    /** The type updated-registration-certificate. */
    TYPE_UPDATED_REGISTRATION_CERTIFICATE(UpdatedRegistrationCertificate.TYPE, true, UpdatedRegistrationCertificate.class),

    /** The type teaspdf. */
    TYPE_TEAS_PDF(TeasPdf.TYPE, true, TeasPdf.class),

    /** The type efile. */
    TYPE_EFILE(Efile.TYPE, false, Efile.class),

    /** The type cabinet. */
    TYPE_CABINET(TradeMarkModel.TYPE_CABINET, false),

    /** The type folder. */
    TYPE_FOLDER(TradeMarkModel.TYPE_FOLDER, false),

    /** The type case folder. */
    TYPE_CASE_FOLDER(TradeMarkModel.TYPE_CASE_FOLDER, false);

    /** The Constant supportedCaseTypes. */
    private static final Map<String, TradeMarkDocumentTypes> SUPPORTED_CASE_TYPES = new HashMap<String, TradeMarkDocumentTypes>();

    /** The Constant supportedCaseQNames. */
    private static final Map<String, QName> SUPPORTED_CASE_QNAMES = new HashMap<String, QName>();

    static {
        for (TradeMarkDocumentTypes tmType : EnumSet.allOf(TradeMarkDocumentTypes.class)) {
            SUPPORTED_CASE_TYPES.put(tmType.getAlfrescoTypeName(), tmType);
            if (tmType.isCaseDocument()) {
                SUPPORTED_CASE_QNAMES.put(tmType.getAlfrescoTypeName(), createQName(tmType.getAlfrescoTypeName()));
            }
        }
    }

    /** The alfresco type name. */
    private String alfrescoTypeName;

    /** The is case document. */
    private boolean isCaseDocument;

    /** The type class. */
    private Class<? extends AbstractBaseType> typeClass;

    /**
     * Instantiates a new trade mark document types.
     *
     * @param typeName
     *            the type name
     * @param isCaseDocument
     *            the is case document
     */
    TradeMarkDocumentTypes(String typeName, boolean isCaseDocument) {
        this(typeName, isCaseDocument, null);
    }

    /**
     * Instantiates a new trade mark document types.
     *
     * @param typeName
     *            the type name
     * @param isCaseDocument
     *            the is case document
     * @param typeClass
     *            the type class
     */
    TradeMarkDocumentTypes(String typeName, boolean isCaseDocument, Class<? extends AbstractBaseType> typeClass) {
        this.alfrescoTypeName = typeName;
        this.isCaseDocument = isCaseDocument;
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
    public static TradeMarkDocumentTypes getTradeMarkType(String typeName) {
        return SUPPORTED_CASE_TYPES.get(typeName);
    }

    /**
     * Gets the cms model class type.
     *
     * @param typeName
     *            the type name
     * @return the cms model class type
     */
    public static Class<? extends AbstractBaseType> getCmsModelClassType(String typeName) {
        return SUPPORTED_CASE_TYPES.get(typeName).getTypeClass();
    }

    /**
     * Gets the trade mark q name.
     *
     * @param typeName
     *            the type name
     * @return the trade mark q name
     */
    public static QName getTradeMarkQName(String typeName) {
        return SUPPORTED_CASE_QNAMES.get(typeName);
    }

    /**
     * Gets the supported case q names.
     *
     * @return the supported case q names
     */
    public static Set<QName> getSupportedCaseQNames() {
        return new HashSet<QName>(SUPPORTED_CASE_QNAMES.values());
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
    public boolean isCaseDocument() {
        return isCaseDocument;
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
