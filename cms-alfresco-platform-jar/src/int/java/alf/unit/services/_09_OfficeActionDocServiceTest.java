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
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.OfficeAction;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * Unit testing for AttachmentService.
 *
 */
public class _09_OfficeActionDocServiceTest extends CmsCommonTest {

    /** The Constant Attachment_FILE_NAME. */
    private static final String FILE_NAME = "FirstOfficeAction.pdf";

    /** The Constant CASE_NUMBER. */
    private static final String CASE_NUMBER = "88888888";

    /** The Constant JSON_METADATA_WITHOUT_EVI_DOC_LIST. */
    public static final String JSON_METADATA_WITHOUT_EVI_DOC_LIST = "{     \"docSubType\": \"EVI\",     \"docCategory\": \"Migrated\",     \"accessLevel\": \"internal\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"mailDate\": \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_FILE_PATH. */
    private static final String CONTENT_FILE_PATH = "src//test//resources//officeaction//1_OfficeAction1.0.pdf";

    /** The doc q name. */
    QName docQName = TradeMarkModel.OFFICE_ACTION_QNAME;
    
    /** The doc type. */
    String docType = OfficeAction.TYPE;
    /** The log. */
    private static Logger LOG = Logger.getLogger(_09_OfficeActionDocServiceTest.class);

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
    public void createOfficeActionWithoutEvidence() {
        setupDocuments(CASE_NUMBER, docQName);
        Map<String, Serializable> metadataMap = getMetadata(JSON_METADATA_WITHOUT_EVI_DOC_LIST);
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
        createOfficeActionWithoutEvidence();
        byte[] metadata = retrieveMetadata(docType, CASE_NUMBER, FILE_NAME);
        assertNotNull(metadata);
    }

    /**
     * Retrieve content.
     */
    @Test
    public void retrieveContent() {
        setupDocuments(CASE_NUMBER, docQName);
        createOfficeActionWithoutEvidence();
        ContentReader contentReader = retrieveContent(docType, CASE_NUMBER, FILE_NAME);
        assertNotNull(contentReader);
        assertNotNull(contentReader.getContentInputStream());
    }

}
