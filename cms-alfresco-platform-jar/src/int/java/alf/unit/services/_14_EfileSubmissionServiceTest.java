package alf.unit.services;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

import org.alfresco.model.ApplicationModel;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Attachment;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Receipt;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Summary;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;
import gov.uspto.trademark.cms.repo.webscripts.efile.vo.SubmissionJson;

/**
 * Unit testing for AttachmentService.
 *
 */
public class _14_EfileSubmissionServiceTest extends CmsCommonTest {

    /** The Constant Attachment_FILE_NAME. */
    private static final String ATTACHMENT_FILE_NAME = "Attachment_Efile.pdf";
    private static final String FILE_NAME = "Efile12.pdf";

    /** The Constant CASE_NUMBER. */
    private static final String GLOBAL_ID = "eee:456:666";

    /** The Constant JSON_STRING. */
    public static final String JSON_STRING = "{     \"customProperties\": {         \"eFileProperty\": \"Property Value\"     } }";

    public static final String SUBMISSION_JSON_STRING = "[{ \"documentId\": \"/drive/efile/eee:456:666/Efile12.pdf\", \"serialNumbers\": [\"12352896\"],"
            + "\"documentType\": \"receipt\",\"metadata\": {\"modifiedByUserId\": \"User XYZ\",\"documentAlias\": \"nickname\",\"sourceSystem\": \"TMNG\","
            + "\"sourceMedia\": \"electronic\",\"sourceMedium\": \"upload\",\"accessLevel\": \"public\",\"scanDate\": \"2014-04-23T13:42:24.962-04:00\"}}]";

    public static final String SUBMISSION_JSON_ATTACHMENT_STRING = "[{ \"documentId\": \"/drive/efile/eee:456:666/Attachment_Efile.pdf\", \"serialNumbers\": [\"12352899\"],"
            + "\"documentType\": \"attachment\",\"metadata\": {\"modifiedByUserId\": \"User XYZ\",\"documentAlias\": \"nickname\",\"sourceSystem\": \"TMNG\","
            + "\"sourceMedia\": \"electronic\",\"sourceMedium\": \"upload\",\"accessLevel\": \"public\",\"scanDate\": \"2014-04-23T13:42:24.962-04:00\"}}]";

    /** The Constant CONTENT_FILE_PATH. */
    private static final String CONTENT_FILE_PATH = "src//test//resources//efile//1_Efile1.0.pdf";

    /** The doc q name. */
    QName docQName = TradeMarkModel.SUMMARY_QNAME;

    /** The doc type. */
    String docType = Summary.TYPE;
    /** The log. */
    private static Logger LOG = Logger.getLogger(_14_EfileSubmissionServiceTest.class);

    @Before
    public void initSetup() {
        LOG.debug("### Executing " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ####");
        // super.setup();
    }

    /**
     * Delete files.
     */
    @AfterClass
    public static void deleteFiles() {

    }

    public void setupSubmissionFolder() {

        NodeRef nodeRef = null;
        try {
            nodeRef = submissionNodeLocator.locateNodeRef(submissionFolderPath);
        } catch (SerialNumberNotFoundException e) {
            LOG.error("submission Folder does not exists");
        }

        LOG.debug("submission Node>>>" + nodeRef);

        if (nodeRef == null) {
            NodeRef submission = null;
            try {
                submission = serviceRegistry.getFileFolderService()
                        .resolveNamePath(repositoryHelper.getCompanyHome(), Arrays.asList(cabinetFolderPath)).getNodeRef();
            } catch (FileNotFoundException e) {
                LOG.error("cabinet folder does not exist");
            }
            LOG.debug("submission>>" + submission);
            QName SUBMISSIONS_FOLDER_QNAME = QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI, "submissionFolder");
            serviceRegistry.getFileFolderService().create(submission, TradeMarkModel.SUBMISSIONS_FOLDER_NAME,
                    SUBMISSIONS_FOLDER_QNAME);
        }
    }

    /**
     * Creates the Attachment inside case folder.
     */
    public void createEfileDocument(String globalId, String fileName) {
        setupSubmissionFolder();
        NodeRef efileNodeRef = null;
        try {
            efileNodeRef = eFileNodeLocator.locateNode(globalId, fileName, TradeMarkModel.EFILE_QNAME);
        } catch (DocumentDoesNotExistException e) {
            LOG.error("document does not exist");
        } catch (SerialNumberNotFoundException e) {
            LOG.error("serialnumber not found");
        }
        if (efileNodeRef == null) {
            Map<String, Serializable> metadataMap = getMetadata(JSON_STRING);
            TmngAlfResponse response = efileService.create(globalId, fileName,
                    ContentItem.getInstance(getContent(CONTENT_FILE_PATH)), metadataMap);
            LOG.info("response>>>" + response);
            assertNotNull(response);
        }
        LOG.info("Created " + fileName + " in " + globalId);
    }

    public void cleanupSubmissions(String id, QName qName) {
        try {

            NodeRef nodeRef = submissionNodeLocator.locateNode(id);
            LOG.info("nodeRef>>" + nodeRef);
            if (nodeRef != null) {
                deleteDocumentsByDocType(nodeRef, qName);
            }
        } catch (SerialNumberNotFoundException e) {
            // do Nothing
        }

    }

    @Test
    public void submitEFile() throws IOException, FileNotFoundException {
        setupDocuments("12352896", ApplicationModel.TYPE_FILELINK);
        cleanupSubmissions(GLOBAL_ID, TradeMarkModel.RECEIPT_QNAME);
        createEfileDocument(GLOBAL_ID, FILE_NAME);
        SubmissionJson[] submissionJson = JacksonHelper.unMarshall(SUBMISSION_JSON_STRING, SubmissionJson[].class);
        efileSubmissionService.submitEfile(submissionJson);
        retrieveLinkedReceiptDocument();
    }

    public void retrieveLinkedReceiptDocument() {
        ContentReader contentReader = retrieveContent(Receipt.TYPE, "12352896", FILE_NAME);
        assertNotNull(contentReader);
        assertNotNull(contentReader.getContentInputStream());
    }

    @Test
    public void submitAttachmentEFile() throws IOException, FileNotFoundException {
        setupDocuments("12352899", ApplicationModel.TYPE_FILELINK);
        cleanupSubmissions(GLOBAL_ID, TradeMarkModel.ATTACHMENT_QNAME);
        createEfileDocument(GLOBAL_ID, ATTACHMENT_FILE_NAME);
        SubmissionJson[] submissionJson = JacksonHelper.unMarshall(SUBMISSION_JSON_ATTACHMENT_STRING, SubmissionJson[].class);
        efileSubmissionService.submitEfile(submissionJson);
    }

    @Test
    public void retrieveLinkedAttachmentDocument() {
        ContentReader contentReader = retrieveContent(Attachment.TYPE, "12352899", ATTACHMENT_FILE_NAME);
        assertNotNull(contentReader);
        assertNotNull(contentReader.getContentInputStream());
    }

}
