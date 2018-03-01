package alf.unit.services;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import gov.uspto.trademark.cms.repo.filters.DocumentMetadataFilter;
import gov.uspto.trademark.cms.repo.webscripts.beans.CaseDocumentMetadataResponse;

/**
 * Unit testing for Case Service.
 *
 */
public class _15_CaseServiceTest extends CmsCommonTest {

    /** The log. */
    private static Logger LOG = Logger.getLogger(_15_CaseServiceTest.class);

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
     * This
     */
    @Test
    public void testGetAllDocuments() {
        List<CaseDocumentMetadataResponse> caseDocuments = caseServiceBase.getAllDocumentsProperties("88888888", null);
        assertNotNull(caseDocuments);
    }

    @Test
    public void testGetAllDocumentsForFilter() {
        List<CaseDocumentMetadataResponse> caseDocuments = caseServiceBase.getAllDocumentsProperties("88888888",
                new DocumentMetadataFilter("migrationSource", "_ABSENT_", ""));
        LOG.debug("****CaseDocuments in testGetAllDocumentsForFilter****" + caseDocuments);
        assertNotNull(caseDocuments);
    }

    @Test
    public void testGetAllDocumentsForFilterSourceTICRS() {
        List<CaseDocumentMetadataResponse> caseDocuments = caseServiceBase.getAllDocumentsProperties("88888888",
                new DocumentMetadataFilter("migrationSource", "TICRS", ""));
        LOG.debug("****CaseDocuments in testGetAllDocumentsForFilterSourceTICRS****" + caseDocuments);
        assertNotNull(caseDocuments);
    }

    @Test
    public void testGetAllDocumentsForLinkDocuments() {
        // For Case with Link Documents to Submissions
        List<CaseDocumentMetadataResponse> caseLinkDocuments = caseServiceBase.getAllDocumentsProperties("12352896", null);
        assertNotNull(caseLinkDocuments);

    }
}
