package alf.unit.services;

import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Attachment;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * Unit testing for AttachmentService.
 *
 */
public class _04_AttachmentServiceTest extends CmsCommonTest {

    /** The Constant Attachment_FILE_NAME. */
    private static final String FILE_NAME = "FirstAttachment.pdf";

    /** The Constant CASE_NUMBER. */
    private static final String CASE_NUMBER = "88888888";

    /** The Constant JSON_STRING. */
    private static final String JSON_STRING = "{     \"modifiedByUserId\": \"User XYZ\",     \"documentAlias\": \"nickname\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\" } ";

    /** The Constant UPDATE_JSON_STRING. */
    private static final String UPDATE_JSON_STRING = "{     \"modifiedByUserId\": \"Second User\",     \"documentAlias\": \"nickname\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\" } ";

    /** The Constant CONTENT_FILE_PATH. */
    private static final String CONTENT_FILE_PATH = "src//test//resources//note//1_Note1.0.pdf";

    /** The doc q name. */
    QName docQName = TradeMarkModel.ATTACHMENT_QNAME;
    
    /** The doc type. */
    String docType = Attachment.TYPE;
    /** The log. */
    private static Logger LOG = Logger.getLogger(_04_AttachmentServiceTest.class);

    /**
     * Inits the setup.
     */

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

    /**
     * Creates the Attachment inside case folder.
     */
    @Test
    public void createAttachment() {
        setupDocuments(CASE_NUMBER, docQName);

        LOG.info("### Executing " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ####");
        Map<String, Serializable> metadataMap = getMetadata(JSON_STRING);
        TmngAlfResponse response = createDocument(docType, CASE_NUMBER, FILE_NAME, getContent(CONTENT_FILE_PATH), metadataMap);
        LOG.info("response>>>" + response);
        assertNotNull(response);
        LOG.info("Created " + FILE_NAME + " in " + CASE_NUMBER);
    }

    /**
     * Retrieve metadata.
     */
    @Test
    public void retrieveMetadata() {
        createAttachment();
        byte[] metadata = retrieveMetadata(docType, CASE_NUMBER, FILE_NAME);
        assertNotNull(metadata);
    }

    /**
     * Retrieve content.
     */
    @Test
    public void retrieveContent() {
        createAttachment();
        ContentReader contentReader = retrieveContent(docType, CASE_NUMBER, FILE_NAME);
        assertNotNull(contentReader);
        assertNotNull(contentReader.getContentInputStream());
    }

    /**
     * Update attachment.
     */
    @Test
    public void UpdateAttachment() {
        createAttachment();
        ContentReader contentReader = retrieveContent(docType, CASE_NUMBER, FILE_NAME);
        assertNotNull(contentReader);

        Map<String, Serializable> metadataMap = getMetadata(UPDATE_JSON_STRING);
        TmngAlfResponse response = updateDocument(docType, CASE_NUMBER, FILE_NAME, getContent(CONTENT_FILE_PATH), metadataMap);
        LOG.info("response>>>" + response);
        assertNotNull(response);
        LOG.info("Created " + FILE_NAME + " in " + CASE_NUMBER);

    }

}
