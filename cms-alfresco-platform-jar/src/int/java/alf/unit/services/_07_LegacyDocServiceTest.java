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
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Legacy;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * Unit testing for AttachmentService.
 *
 */
public class _07_LegacyDocServiceTest extends CmsCommonTest {

    /** The Constant Attachment_FILE_NAME. */
    private static final String FILE_NAME = "FirstLegacy.pdf";

    /** The Constant CASE_NUMBER. */
    private static final String CASE_NUMBER = "88888888";

    /** The Constant JSON_STRING. */
    public static final String JSON_STRING = "{     \"modifiedByUserId\": \"User XYZ\",     \"documentAlias\": \"nickname\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\", \"legacyCategory\": \"Migrated\", \"docCode\": \"LGCY\", \"migrationMethod\":\"LZL\", \"migrationSource\":\"TICRS\" } ";

    /** The Constant CONTENT_FILE_PATH. */
    private static final String CONTENT_FILE_PATH = "src//test//resources//legacy//1_Legacy1.0.pdf";

    /** The doc q name. */
    QName docQName = TradeMarkModel.LEGACY_QNAME;
    
    /** The doc type. */
    String docType = Legacy.TYPE;
    /** The log. */
    private static Logger LOG = Logger.getLogger(_07_LegacyDocServiceTest.class);

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
        setupDocuments(CASE_NUMBER, docQName);
        createDocument();
        byte[] metadata = retrieveMetadata(docType, CASE_NUMBER, FILE_NAME);
        assertNotNull(metadata);
    }

    /**
     * Retrieve content.
     */
    @Test
    public void retrieveContent() {
        setupDocuments(CASE_NUMBER, docQName);
        createDocument();
        ContentReader contentReader = retrieveContent(docType, CASE_NUMBER, FILE_NAME);
        assertNotNull(contentReader);
        assertNotNull(contentReader.getContentInputStream());
    }

}
