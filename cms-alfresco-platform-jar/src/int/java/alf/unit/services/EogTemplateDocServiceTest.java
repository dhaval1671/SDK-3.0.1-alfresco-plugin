package alf.unit.services;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;
import org.junit.Test;

import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.CmsValidator;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Attachment;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * Unit testing for AttachmentService.
 *
 */
public class EogTemplateDocServiceTest extends CmsCommonTest {

    /** The Constant VALUE_REGISTRATION_CERTIFICATE_FILE_NAME. */
    public static final String FILE_NAME = "EogTemplate.jpg";

    /** The Constant CASE_NUMBER. */
    private static final String CASE_NUMBER = "88888888";

    /** The Constant JSON_STRING. */
    private static final String JSON_STRING = "{  } ";

    /** The Constant CONTENT_FILE_PATH. */
    private static final String CONTENT_FILE_PATH = "src//test//resources//eogtemplate//eogTemplate.jpg";

    /** The doc q name. */
    private QName docQName = TradeMarkModel.DOCUMENT_QNAME;

    /** The doc type. */
    String docType = Attachment.TYPE;
    /** The log. */
    private static Logger LOG = Logger.getLogger(EogTemplateDocServiceTest.class);

    public static final String ADMIN_USER_NAME = "admin";

    /**
     * Creates the Attachment inside case folder.
     */
    @Test
    public void createEogTemplate() {
        setupDocuments(CASE_NUMBER, docQName);

        LOG.info("### Executing " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ####");
        Map<String, Serializable> metadataMap = getMetadata(JSON_STRING);
        TmngAlfResponse response = createDocument(docType, CASE_NUMBER, FILE_NAME, getContent(CONTENT_FILE_PATH), metadataMap);
        LOG.info("response>>>" + response);
        assertNotNull(response);
        LOG.info("Created " + FILE_NAME + " in " + CASE_NUMBER);
    }

    public TmngAlfResponse createDocument(String docType, String id, String fileName, InputStream content,
            Map<String, Serializable> metadata) {

        return eogTemplateDocService.create(id, fileName, ContentItem.getInstance(content), metadata);
    }

    public void setupDocuments(String id, QName qName) {
        try {

            NodeRef nodeRef = eogTemplateNodeLocator.locateNode(id);
            LOG.info("nodeRef>>" + nodeRef);
            if (nodeRef == null) {
                eogTemplateNodeCreator.createNode(id);
            } else {
                deleteDocumentsByDocType(nodeRef, qName);
            }
        } catch (SerialNumberNotFoundException e) {
            // do Nothing
        }

    }

    /**
     * Retrieve content.
     */
    @Test
    public void retrieveContent() {
        createEogTemplate();
        ContentReader contentReader = retrieveContent(docType, CASE_NUMBER, FILE_NAME);
        assertNotNull(contentReader);
        assertNotNull(contentReader.getContentInputStream());
    }

    public ContentReader retrieveContent(String docType, String id, String fileName) {
        return eogTemplateDocService.retrieveContent(id, fileName, null);
    }

    /**
     * Gets the notice input stream.
     *
     * @param filePath
     *            the file path
     * @return the notice input stream
     */
    public static InputStream getContent(String filePath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            return (fis);
        } catch (FileNotFoundException e) {
            LOG.error("File Not Found", e);
        }
        return null;

    }

    public static Map<String, Serializable> getMetadata(String json) {
        return CmsValidator.parseJsonReturningMapWithCaseSensitiveKeys(json);

    }

}
