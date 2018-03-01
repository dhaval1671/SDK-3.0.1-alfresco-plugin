package gov.uspto.trademark.cms.repo.constants;

import org.alfresco.model.ContentModel;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;

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
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Brief;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Decision;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Exhibit;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Motion;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Order;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Pleading;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Undesignated;
import gov.uspto.trademark.cms.repo.model.cabinet.madridib.Madrid;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.idmanual.Idm;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.officialgazette.Eog;
import gov.uspto.trademark.cms.repo.model.drive.efile.Efile;

/**
 * The Interface TradeMarkModel.
 */
public interface TradeMarkModel extends ContentModel {

    // TradeMark model
    /** The TRADEMAR k_ mode l_1_0_ uri. */
    String TRADEMARK_MODEL_1_0_URI = "http://www.uspto.gov/cms/model/content/1.0";

    /** The TRADEMAR k_ mode l_1_0_ prefix. */
    String TRADEMARK_MODEL_1_0_PREFIX = "tm";

    /** The type trademark content. */
    QName TYPE_TRADEMARK_CONTENT = QName.createQName(TRADEMARK_MODEL_1_0_URI, "content");

    // Aspects
    /** The aspect business version. */
    QName ASPECT_BUSINESS_VERSION = QName.createQName(TRADEMARK_MODEL_1_0_URI, "businessVersion");

    /** The aspect no rendition. */
    QName NO_RENDITION_MARK = QName.createQName(TRADEMARK_MODEL_1_0_URI, "noRendition");

    // redaction aspect
    /** The aspect redacted. */
    QName ASPECT_REDACTED = QName.createQName(TRADEMARK_MODEL_1_0_URI, "redactedDocument");

    /** The aspect evidence bank. */
    QName ASPECT_EVIDENCE_BANK = QName.createQName(TRADEMARK_MODEL_1_0_URI, "evidenceBankAspect");

    // Migration Aspect
    /** The aspect migrated. */
    QName ASPECT_MIGRATED = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.MIGRATED);

    // Alfresco QNames
    /** The on content property update qname. */
    QName ON_CONTENT_PROPERTY_UPDATE_QNAME = QName.createQName(NamespaceService.ALFRESCO_URI, "onContentPropertyUpdate");

    // QNames
    /** The trademark folder qname. */
    QName TRADEMARK_FOLDER_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.TYPE_FOLDER);

    /** The mark qname. */
    QName MARK_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, MarkDoc.TYPE);

    /** The evidence qname. */
    QName EVIDENCE_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Evidence.TYPE);

    /** The office action qname. */
    QName OFFICE_ACTION_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, OfficeAction.TYPE);

    /** The note qname. */
    QName NOTE_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Note.TYPE);

    /** The notice qname. */
    QName NOTICE_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Notice.TYPE);

    /** The legacy qname. */
    QName LEGACY_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Legacy.TYPE);

    /** The summary qname. */
    QName SUMMARY_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Summary.TYPE);

    /** The response qname. */
    QName RESPONSE_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Response.TYPE);

    /** The receipt qname. */
    QName RECEIPT_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Receipt.TYPE);

    /** Supplemental Attachment QName. */
    QName ATTACHMENT_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Attachment.TYPE);

    /** The signature qname. */
    QName SIGNATURE_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Signature.TYPE);

    /** The specimen qname. */
    QName SPECIMEN_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Specimen.TYPE);

    /** The withdrawal qname. */
    QName WITHDRAWAL_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Withdrawal.TYPE);

    /** The application qname. */
    QName APPLICATION_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Application.TYPE);

    /** The registration-certificate qname **/
    QName REGISTRATION_CERTIFICATE_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, RegistrationCertificate.TYPE);

    /** The updated-registration-certificate qname **/
    QName UPDATED_REGISTRATION_CERTIFICATE_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI,
            UpdatedRegistrationCertificate.TYPE);

    QName MADRID_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Madrid.TYPE);

    /** The teas pdf qname **/
    QName TEAS_PDF_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TeasPdf.TYPE);

    /** The efile qname. */
    QName EFILE_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Efile.TYPE);

    /** The trademark id qname. */
    QName TRADEMARK_ID_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.TRADEMARK_ID);

    /** The document qname. */
    QName DOCUMENT_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Document.TYPE);

    QName TICRS_DOCUMENT_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.TYPE_TICRS_DOCUMENT);

    /** The case folder qname. */
    QName CASE_FOLDER_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.TYPE_CASE_FOLDER);

    QName PROCEEDING_NUMBER_FOLDER_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI,
            TradeMarkModel.TYPE_PROCEEDING_NUMBER_FOLDER);

    QName EFILE_FOLDER_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.TYPE_EFILE_FOLDER_TWO);

    /** The eog folder qname. */
    QName EOG_FOLDER_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.TYPE_EOG_FOLDER);

    /** The idmanual folder qname. */
    QName IDM_FOLDER_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.TYPE_IDM_FOLDER);

    /** The serial number qname. */
    QName SERIAL_NUMBER_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.SERIAL_NUMBER);

    QName PROCEEDING_NUMBER_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.PROCEEDING_NUMBER);

    /** The business document type qname. */
    QName BUSINESS_DOCUMENT_TYPE_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.BUSINESS_DOCUMENT_TYPE);

    /** The access level qname. */
    QName ACCESS_LEVEL_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.ACCESS_LEVEL);

    /** The redaction level qname. */
    QName REDACTION_LEVEL_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TradeMarkModel.REDACTION_LEVEL);

    /** Publication qnames *. */
    /** The eog qname. */
    QName EOG_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Eog.TYPE);
    /** The idmanual qname. */
    QName IDMANUAL_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Idm.TYPE);

    /** The legal-preceeding qnames **/
    QName PLEADING_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Pleading.TYPE);
    QName MOTION_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Motion.TYPE);
    QName EXHIBIT_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Exhibit.TYPE);
    QName BRIEF_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Brief.TYPE);
    QName ORDER_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Order.TYPE);
    QName DECISION_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Decision.TYPE);
    QName UNDESIGNATED_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, Undesignated.TYPE);

    // Association Names
    /** The assoc related evidence. */
    QName ASSOC_RELATED_EVIDENCE = QName.createQName(TRADEMARK_MODEL_1_0_URI, "relatedEvidence");

    /** The assoc related office action. */
    QName ASSOC_RELATED_OFFICE_ACTION = QName.createQName(TRADEMARK_MODEL_1_0_URI, "relatedOfficeAction");

    /** The assoc original to redacted. */
    QName ASSOC_ORIGINAL_TO_REDACTED = QName.createQName(TRADEMARK_MODEL_1_0_URI, "relatedRedactedDocument");

    /** The assoc redacted to original. */
    QName ASSOC_REDACTED_TO_ORIGINAL = QName.createQName(TRADEMARK_MODEL_1_0_URI, "relatedOriginalDocument");

    QName ASSOC_CONVERTED_MPEG4 = QName.createQName(TRADEMARK_MODEL_1_0_URI, "convertedMpeg4");
    QName ASSOC_ORIGINAL_SOURCE_MEDIA = QName.createQName(TRADEMARK_MODEL_1_0_URI, "originalSourceMedia");

    // FolderNames
    /** The cabinet folder name. */
    String CABINET_FOLDER_NAME = "cabinet";
    
    String XML_XSL_DTD_FOLDER_NAME = "xml-xsl-dtd";
    String EXPORTPATH_FOLDER_NAME = "exportpath";
    String DTD_XSL_FOLDER_NAME = "DTD_XSL";    

    /** The case folder name. */
    String CASE_FOLDER_NAME = "case";

    String MADRIDIB_FOLDER_NAME = "madridib";

    /** TICRS folder name */
    String TICRS_FOLDER_NAME = "TICRS";

    /** teaspdf folder name */
    String TEAS_PDF_FOLDER_NAME = "teaspdf";

    /** The submissions folder name. */
    String SUBMISSIONS_FOLDER_NAME = "submissions";

    /** The petition folder name. */
    String PETITION_FOLDER_NAME = "petition";

    /** The publication folder name. */
    String PUBLICATION_FOLDER_NAME = "publication";

    /** The EOG folder name. */
    String EOG_FOLDER_NAME = "Official Gazette";

    /** The IDM folder name. */
    String IDM_FOLDER_NAME = "ID Manual";

    /** The legal proceeding folder name. */
    String LEGAL_PROCEEDING_FOLDER_NAME = "legal-proceeding";

    /** The quality review. */
    String QUALITY_REVIEW = "quality-review";

    /** The document library folder name. */
    String DOCUMENT_LIBRARY_FOLDER_NAME = "Document_Library";

    /** The evidence bank folder name. */
    String EVIDENCE_BANK_FOLDER_NAME = "Evidence Bank";

    String WEBCAPTURE_FOLDER_NAME = "WebCapture";

    /** The twoaevidence library folder name. */
    String TWOAEVIDENCE_LIBRARY_FOLDER_NAME = "2A";

    /** The efile drive. */
    String EFILE_DRIVE = "drive";

    // TradeMark Document Types
    /** The type document. */
    String TYPE_DOCUMENT = "document";

    /** Ticrs document type */
    String TYPE_TICRS_DOCUMENT = "ticrsDocument";

    String TICRS_SOURCE = "TICRS";

    /** The type cabinet. */
    String TYPE_CABINET = "cabinet";
    
    /** The type efile folder. */
    String TYPE_EFILE_FOLDER = "eFile";

    String TYPE_EOG_TEMPLATE_FOLDER = "eogTemplate";

    /** The type folder. */
    String TYPE_FOLDER = "folder";

    /** The type case folder. */
    String TYPE_CASE_FOLDER = "caseFolder";

    String TYPE_PROCEEDING_NUMBER_FOLDER = "proceedingNumberFolder";

    String TYPE_EFILE_FOLDER_TWO = "eFileFolder";

    /** The type eog folder. */
    String TYPE_EOG_FOLDER = "eogFolder";

    /** The type id manual folder. */
    String TYPE_IDM_FOLDER = "idmFolder";

    // Trademark Properties
    /** The document name. */
    String DOCUMENT_NAME = "documentName";

    /** The document alias. */
    String DOCUMENT_ALIAS = "documentAlias";

    /** The doc code. */
    String DOC_CODE = "docCode";

    /** The mime type. */
    String MIME_TYPE = "mimeType";

    /** The legacy category. */
    String LEGACY_CATEGORY = "legacyCategory";

    /** The access level. */
    String ACCESS_LEVEL = "accessLevel";

    /** The redaction level. */
    String REDACTION_LEVEL = "redactionLevel";

    /** The group name. */
    String GROUP_NAME = "groupName";

    /** The created by user id. */
    String CREATED_BY_USER_ID = "createdByUserId";

    /** The modified by user id. */
    String MODIFIED_BY_USER_ID = "modifiedByUserId";

    /** The mail date. */
    String MAIL_DATE = "mailDate";

    /** The scan date. */
    String SCAN_DATE = "scanDate";

    /** The load date. */
    String LOAD_DATE = "loadDate";

    /** The reason for publication. */
    String REASON_FOR_PUBLICATION = "reasonForPublication";

    /** The facet identifier. */
    String FACET_IDENTIFIER = "facetIdentifier";

    /** The category sequence. */
    String CATEGORY_SEQUENCE = "categorySequence";

    /** The case related. */
    String CASE_RELATED = "caseRelated";

    /** The serial number. */
    String SERIAL_NUMBER = "serialNumber";

    String PROCEEDING_NUMBER = "proceedingNumber";

    /** The issue date. */
    String ISSUE_DATE = "issueDate";

    /** The business document related. */
    String BUSINESS_DOCUMENT_RELATED = "businessDocumentRelated";

    /** The business document type. */
    String BUSINESS_DOCUMENT_TYPE = "businessDocumentType";

    /** The direction. */
    String DIRECTION = "direction";

    /** The image related. */
    String IMAGE_RELATED = "imageRelated";

    /** The image height. */
    String IMAGE_HEIGHT = "imageHeight";

    /** The image width. */
    String IMAGE_WIDTH = "imageWidth";

    /** The image resolution x. */
    String IMAGE_RESOLUTION_X = "imageResolutionX";

    /** The image resolution y. */
    String IMAGE_RESOLUTION_Y = "imageResolutionY";

    /** The image color depth. */
    String IMAGE_COLOR_DEPTH = "imageColorDepth";

    /** The image compression type. */
    String IMAGE_COMPRESSION_TYPE = "imageCompressionType";

    /** The source media. */
    String SOURCE_MEDIA = "sourceMedia";

    /** The source medium. */
    String SOURCE_MEDIUM = "sourceMedium";

    /** The business version related. */
    String BUSINESS_VERSION_RELATED = "businessVersionRelated";

    /** The effective start date. */
    String EFFECTIVE_START_DATE = "effectiveStartDate";

    /** The effective end date. */
    String EFFECTIVE_END_DATE = "effectiveEndDate";

    /** The evidence related. */
    String EVIDENCE_RELATED = "evidenceRelated";

    /** The evidence capture date time. */
    String EVIDENCE_CAPTURE_DATE_TIME = "evidenceCaptureDateTime";

    /** The evidence source url. */
    String EVIDENCE_SOURCE_URL = "evidenceSourceURL";

    /** The evidence source type. */
    String EVIDENCE_SOURCE_TYPE = "evidenceCategory";

    /** The multimedia related. */
    String MULTIMEDIA_RELATED = "multimediaRelated";

    /** The codec audio. */
    String CODEC_AUDIO = "codecAudio";

    /** The audio compression type. */
    String AUDIO_COMPRESSION_TYPE = "audioCompressionType";

    /** The codec video. */
    String CODEC_VIDEO = "codecVideo";

    /** The video compression type. */
    String VIDEO_COMPRESSION_TYPE = "videoCompressionType";

    /** The mm duration. */
    String MM_DURATION = "mm-Duration";

    /** The mm start time. */
    String MM_START_TIME = "mm-StartTime";

    /** The mm end time. */
    String MM_END_TIME = "mm-Comment";

    /** The active. */
    String ACTIVE = "active";

    /** The supported. */
    String SUPPORTED = "supported";

    /** The converted. */
    String CONVERTED = "converted";

    /** The cont cd. */
    String CONT_CD = "cont-cd";

    /** The migrated. */
    String MIGRATED = "migrated";

    /** The migration method. */
    String MIGRATION_METHOD = "migrationMethod";

    /** The migration source. */
    String MIGRATION_SOURCE = "migrationSource";

    /** The stylesheet. */
    String STYLESHEET = "stylesheet";

    /** The attachments. */
    String ATTACHMENTS = "attachments";

    /** The original document version. */
    String ORIGINAL_DOCUMENT_VERSION = "originalDocumentVersion";

    /** 2A Evidence Bank Source *. */
    String EVIDENCE_BANK_TWO_A_LIB_SOURCE = "source";

    // e-file properties
    /** The trademark id. */
    String TRADEMARK_ID = "trademarkId";

    // Legacy Sync Properties
    /** The name. */
    String NAME = "name";

    /** The last ticrs sync date. */
    String LAST_TICRS_SYNC_DATE = "lastTICRSSyncDate";

    /** The ticrs doc count. */
    String TICRS_DOC_COUNT = "ticrsDocCount";

    /** The mark node ref. */
    String MARK_NODE_REF = "markNodeRef";

    String VERSION = "version";

    /** The name qname. */
    QName NAME_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, NAME);

    /** The last ticrs sync date qname. */
    QName LAST_TICRS_SYNC_DATE_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, LAST_TICRS_SYNC_DATE);

    /** The ticrs doc count qname. */
    QName TICRS_DOC_COUNT_QNAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, TICRS_DOC_COUNT);

    // Properties QNames

    /** The prop document name. */
    QName PROP_DOCUMENT_NAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, DOCUMENT_NAME);

    /** The prop document alias. */
    QName PROP_DOCUMENT_ALIAS = QName.createQName(TRADEMARK_MODEL_1_0_URI, DOCUMENT_ALIAS);

    /** The prop legacy category. */
    QName PROP_LEGACY_CATEGORY = QName.createQName(TRADEMARK_MODEL_1_0_URI, LEGACY_CATEGORY);

    /** The prop doc code. */
    QName PROP_DOC_CODE = QName.createQName(TRADEMARK_MODEL_1_0_URI, DOC_CODE);

    /** The prop access level. */
    QName PROP_ACCESS_LEVEL = QName.createQName(TRADEMARK_MODEL_1_0_URI, ACCESS_LEVEL);

    /** The prop access level. */
    QName PROP_GROUP_NAME = QName.createQName(TRADEMARK_MODEL_1_0_URI, GROUP_NAME);

    /** The prop mail date. */
    QName PROP_MAIL_DATE = QName.createQName(TRADEMARK_MODEL_1_0_URI, MAIL_DATE);

    /** The prop migration source. */
    QName PROP_MIGRATION_SOURCE = QName.createQName(TRADEMARK_MODEL_1_0_URI, MIGRATION_SOURCE);

    /** The prop scan date. */
    QName PROP_SCAN_DATE = QName.createQName(TRADEMARK_MODEL_1_0_URI, SCAN_DATE);

    /** The prop serial number. */
    QName PROP_SERIAL_NUMBER = QName.createQName(TRADEMARK_MODEL_1_0_URI, SERIAL_NUMBER);

    // Redaction Aspect Properties
    /** The prop original document version. */
    QName PROP_ORIGINAL_DOCUMENT_VERSION = QName.createQName(TRADEMARK_MODEL_1_0_URI, ORIGINAL_DOCUMENT_VERSION);

    /** The prop source for 2A evidence library bank source. */
    QName PROP_EVIDENCE_BANK_TWO_A_LIB_SOURCE = QName.createQName(TRADEMARK_MODEL_1_0_URI, EVIDENCE_BANK_TWO_A_LIB_SOURCE);

    // Virtual Mark Properties
    /** The prop mark node ref. */
    QName PROP_MARK_NODE_REF = QName.createQName(TRADEMARK_MODEL_1_0_URI, MARK_NODE_REF);

    /** The prop effective start date. */
    QName PROP_EFFECTIVE_START_DATE = QName.createQName(TRADEMARK_MODEL_1_0_URI, EFFECTIVE_START_DATE);


}
