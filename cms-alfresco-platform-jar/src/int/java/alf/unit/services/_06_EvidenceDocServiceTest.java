package alf.unit.services;

import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * Unit testing for AttachmentService.
 *
 */
public class _06_EvidenceDocServiceTest extends CmsCommonTest {

    /** The Constant Attachment_FILE_NAME. */
    private static final String FILE_NAME = "FirstEvidence.pdf";

    /** The Constant CASE_NUMBER. */
    private static final String CASE_NUMBER = "88888888";

    /** The Constant JSON_STRING. */
    public static final String JSON_STRING = "{     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"internal\",     \"documentCode\": \"EVI\",     \"version\": \"1.0\",     \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"lexisnexis\" }";
    
    /** The Constant UPDATE_JSON_STRING. */
    public static final String UPDATE_JSON_STRING = "{     \"modifiedByUserId\": \"User Modified\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"internal\",     \"documentCode\": \"EVI\",     \"version\": \"1.0\",     \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"lexisnexis\" }";

    /** The Constant CONTENT_FILE_PATH. */
    private static final String CONTENT_FILE_PATH = "src//test//resources//evidence//1_Evidence1.0.pdf";

    /** The doc q name. */
    QName docQName = TradeMarkModel.EVIDENCE_QNAME;
    
    /** The doc type. */
    String docType = Evidence.TYPE;
    /** The log. */
    private static Logger LOG = Logger.getLogger(_06_EvidenceDocServiceTest.class);

    /**
     * Inits the setup.
     */

    @Before
    public void initSetup() {
        LOG.debug("### Executing " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ####");
        // super.setup();
    }

    /**
     * Creates the Attachment inside case folder.
     */
    @Test
    public void createDocument() {
        setupDocuments(CASE_NUMBER, docQName);
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
        createDocument();
        byte[] metadata = retrieveMetadata(docType, CASE_NUMBER, FILE_NAME);
        assertNotNull(metadata);
    }

    /**
     * Retrieve content.
     */
    @Test
    public void retrieveContent() {
        createDocument();
        ContentReader contentReader = retrieveContent(docType, CASE_NUMBER, FILE_NAME);
        assertNotNull(contentReader);
        assertNotNull(contentReader.getContentInputStream());
    }

    /**
     * Update note.
     */
    @Test
    public void UpdateNote() {
        createDocument();
        ContentReader contentReader = retrieveContent(docType, CASE_NUMBER, FILE_NAME);
        assertNotNull(contentReader);

        Map<String, Serializable> metadataMap = getMetadata(UPDATE_JSON_STRING);
        TmngAlfResponse response = updateDocument(docType, CASE_NUMBER, FILE_NAME, getContent(CONTENT_FILE_PATH), metadataMap);
        LOG.info("response>>>" + response);
        assertNotNull(response);
        LOG.info("Created " + FILE_NAME + " in " + CASE_NUMBER);

    }
}
