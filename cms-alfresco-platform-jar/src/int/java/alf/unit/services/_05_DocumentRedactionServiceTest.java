package alf.unit.services;

import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;
import org.junit.Test;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.AccessLevelFilter;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Attachment;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * Unit testing for AttachmentService.
 *
 */
public class _05_DocumentRedactionServiceTest extends CmsCommonTest {

    /** The Constant Attachment_FILE_NAME. */
    private static final String FILE_NAME = "FirstAttachment.pdf";

    /** The Constant CASE_NUMBER. */
    private static final String CASE_NUMBER = "88888888";

    /** The Constant JSON_STRING. */
    private static final String JSON_STRING = "{     \"modifiedByUserId\": \"User XYZ\",     \"documentAlias\": \"nickname\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\" } ";

    /** The Constant REDACT_JSON_STRING. */
    private static final String REDACT_JSON_STRING = "{     \"modifiedByUserId\": \"Second User\",     \"documentAlias\": \"nickname\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\", \"redactionLevel\": \"Partial\"} ";

    /** The Constant CONTENT_FILE_PATH. */
    private static final String CONTENT_FILE_PATH = "src//test//resources//note//1_Note1.0.pdf";

    /** The doc q name. */
    QName docQName = TradeMarkModel.ATTACHMENT_QNAME;
    
    /** The doc type. */
    String docType = Attachment.TYPE;
    /** The log. */
    private static Logger LOG = Logger.getLogger(_05_DocumentRedactionServiceTest.class);

    /**
     * Test redaction.
     */
    @Test
    public void testRedaction() {
        // First create and redact the document
        redactDocument();
        retrieveRedactMetadata();
        retrieveRedactContent();
        retrieveOriginalContent();
        retrieveOriginalMetadata();
        restoreOriginal();
        retrieveOriginalMetadata();
    }

    /**
     * Redact document.
     */
    public void redactDocument() {
        // Cleanup the folder with existing documents
        setupDocuments(CASE_NUMBER, docQName);
        // Create a document
        createDocument();
        ContentReader contentReader = retrieveContent(docType, CASE_NUMBER, FILE_NAME);
        assertNotNull(contentReader);

        Map<String, Serializable> metadataMap = getMetadata(REDACT_JSON_STRING);
        TmngAlfResponse response = documentRedactionService.redactDocument(CASE_NUMBER, FILE_NAME, ContentItem.getInstance(getContent(CONTENT_FILE_PATH)),
                docType, metadataMap);
        LOG.info("response>>>" + response);
        assertNotNull(response);
        LOG.info("Created " + FILE_NAME + " in " + CASE_NUMBER);

    }

    /**
     * Retrieve redact metadata.
     */
    public void retrieveRedactMetadata() {
        byte[] metadata = documentRedactionService.retrieveRedactionMetadata(CASE_NUMBER, FILE_NAME, docType);
        assertNotNull(metadata);
    }

    /**
     * Retrieve redact content.
     */
    public void retrieveRedactContent() {
        ContentReader content = documentRedactionService.retrieveRedactionContent(CASE_NUMBER, FILE_NAME, docType);
        assertNotNull(content);
        assertNotNull(content.getContentInputStream());
    }

    /**
     * Retrieve original metadata.
     */
    public void retrieveOriginalMetadata() {
        String accessLevelValue = "restricted";
                AccessLevelFilter accessLevelFilter = new AccessLevelFilter(accessLevelValue);
        byte[] metadata = documentRedactionService.retrieveOriginalMetadata(CASE_NUMBER, FILE_NAME, docType, accessLevelFilter);
        assertNotNull(metadata);
    }

    /**
     * Retrieve original content.
     */
    public void retrieveOriginalContent() {
        String accessLevelValue = "restricted";
        AccessLevelFilter accessLevelFilter = new AccessLevelFilter(accessLevelValue);
        ContentReader content = documentRedactionService.retrieveOriginalContent(CASE_NUMBER, FILE_NAME, docType, accessLevelFilter);
        assertNotNull(content);
        assertNotNull(content.getContentInputStream());
    }

    /**
     * Restore original.
     */
    public void restoreOriginal() {
        TmngAlfResponse response = documentRedactionService.restoreToOriginalVersion(CASE_NUMBER, FILE_NAME, docType);
        assertNotNull(response);
    }

    /**
     * Creates the Attachment inside case folder.
     */
    @Test
    public void createDocument() {
        setupDocuments(CASE_NUMBER, docQName);

        LOG.info("### Executing " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ####");
        Map<String, Serializable> metadataMap = getMetadata(JSON_STRING);
        TmngAlfResponse response = createDocument(docType, CASE_NUMBER, FILE_NAME, getContent(CONTENT_FILE_PATH), metadataMap);
        LOG.info("response>>>" + response);
        assertNotNull(response);
        LOG.info("Created " + FILE_NAME + " in " + CASE_NUMBER);
    }

}
