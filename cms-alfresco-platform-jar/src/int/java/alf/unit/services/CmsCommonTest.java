package alf.unit.services;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.repo.model.Repository;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.alfresco.util.ApplicationContextHelper;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.CaseNodeCreator;
import gov.uspto.trademark.cms.repo.helpers.CmsValidator;
import gov.uspto.trademark.cms.repo.helpers.EogTemplateNodeCreator;
import gov.uspto.trademark.cms.repo.helpers.SubmissionNodeCreator;
import gov.uspto.trademark.cms.repo.nodelocator.CaseNodeLocator;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.nodelocator.EfileNodeLocator;
import gov.uspto.trademark.cms.repo.nodelocator.EogTemplateNodeLocator;
import gov.uspto.trademark.cms.repo.nodelocator.SubmissionNodeLocator;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.DocumentServiceFactory;
import gov.uspto.trademark.cms.repo.services.EfileService;
import gov.uspto.trademark.cms.repo.services.impl.CaseServiceBase;
import gov.uspto.trademark.cms.repo.services.impl.DocumentRedactionService;
import gov.uspto.trademark.cms.repo.services.impl.EfileSubmissionsService;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.EogTemplateDocService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * The Class BaseTest.
 */
public class CmsCommonTest {

    /** The Constant ADMIN_USER_NAME. */
    public static final String ADMIN_USER_NAME = "admin";

    /** The log. */
    private static Logger LOG = Logger.getLogger(CmsCommonTest.class);

    /** The application context. */
    protected static ApplicationContext applicationContext;

    /** The service registry. */
    protected static ServiceRegistry serviceRegistry;

    /** The repository helper. */
    protected static Repository repositoryHelper;

    /** The node service. */
    protected static NodeService nodeService;

    /** The case node locator. */
    protected CmsNodeLocator caseNodeLocator;

    protected EfileNodeLocator eFileNodeLocator;

    /** The case node creator. */
    protected CaseNodeCreator caseNodeCreator;

    /** The document service map. */
    protected Map<String, String> documentServiceMap;

    /** The document service factory. */
    protected DocumentServiceFactory documentServiceFactory;

    /** The document redaction service. */
    protected DocumentRedactionService documentRedactionService;

    protected CmsNodeLocator submissionNodeLocator;

    protected CmsNodeCreator submissionNodeCreator;

    protected static EfileService efileService;

    protected EfileSubmissionsService efileSubmissionService;

    protected CaseService caseServiceBase;

    protected CmsNodeLocator eogTemplateNodeLocator;

    protected CmsNodeCreator eogTemplateNodeCreator;

    protected EogTemplateDocService eogTemplateDocService;

    String[] cabinetFolderPath = new String[] { TradeMarkModel.CABINET_FOLDER_NAME };// ,
    String[] submissionFolderPath = new String[] { TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.SUBMISSIONS_FOLDER_NAME };

    /**
     * Inits the app context.
     */
    @BeforeClass
    public static void initAppContext() {
        // TODO: Make testing properly working without need for helpers
        // TODO: Provide this in an SDK base class
        ApplicationContextHelper.setUseLazyLoading(false);
        ApplicationContextHelper.setNoAutoStart(true);
        applicationContext = ApplicationContextHelper.getApplicationContext(new String[] {
                "classpath:alfresco/application-context.xml", "classpath:alfresco/module/cms-alfresco-repo-amp/module-context.xml" });
        serviceRegistry = (ServiceRegistry) applicationContext.getBean("ServiceRegistry");
        repositoryHelper = (Repository) applicationContext.getBean("repositoryHelper");
        nodeService = (NodeService) applicationContext.getBean("NodeService");

        AuthenticationUtil.setFullyAuthenticatedUser(ADMIN_USER_NAME);
        LOG.debug("BaseTest.initAppContext(): If you see this message, means your unit test logging is properly configured. Change it in log4j.properties");
        LOG.debug("BaseTest.initAppContext(): Application Context properly loaded");
    }

    /**
     * Setup.
     * 
     * @throws org.alfresco.service.cmr.model.FileNotFoundException
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setup() throws org.alfresco.service.cmr.model.FileNotFoundException {
        LOG.debug("### Executing " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ####");
        caseNodeLocator = (CaseNodeLocator) applicationContext.getBean("caseNodeLocator");
        caseNodeCreator = (CaseNodeCreator) applicationContext.getBean("caseNodeCreator");
        eFileNodeLocator = (EfileNodeLocator) applicationContext.getBean("eFileNodeLocator");
        documentServiceFactory = (DocumentServiceFactory) applicationContext.getBean("documentServiceFactory");
        documentServiceMap = ((Map<String, String>) applicationContext.getBean("documentServiceMap"));
        documentRedactionService = (DocumentRedactionService) applicationContext.getBean("documentRedactionService");

        submissionNodeLocator = (SubmissionNodeLocator) applicationContext.getBean("submissionNodeLocator");
        submissionNodeCreator = (SubmissionNodeCreator) applicationContext.getBean("submissionNodeCreator");
        efileService = (EfileService) applicationContext.getBean("EfileServiceBase");
        efileSubmissionService = (EfileSubmissionsService) applicationContext.getBean("submissionsService");
        caseServiceBase = (CaseServiceBase) applicationContext.getBean("caseServiceBase");

        eogTemplateNodeLocator = (EogTemplateNodeLocator) applicationContext.getBean("eogTemplateNodeLocator");
        eogTemplateNodeCreator = (EogTemplateNodeCreator) applicationContext.getBean("eogTemplateNodeCreator");
        eogTemplateDocService = (EogTemplateDocService) applicationContext.getBean("eogTemplateDocService");
    }

    /**
     * Process json.
     *
     * @param jsonString
     *            the json string
     * @return the map
     */
    @SuppressWarnings("unchecked")
    protected static Map<String, Serializable> processJson(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Serializable> map = null;
        try {
            map = mapper.readValue(jsonString, Map.class);
        } catch (JsonParseException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info(e.getMessage(), e);
            }
        } catch (JsonMappingException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info(e.getMessage(), e);
            }
        } catch (IOException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info(e.getMessage(), e);
            }
        }
        return map;
    }

    /**
     * Test wiring.
     */
    @Test
    public void testWiring() {
        assertNotNull(serviceRegistry);
        assertNotNull(repositoryHelper);
        assertNotNull(nodeService);
        assertNotNull(caseNodeLocator);
        assertNotNull(caseNodeCreator);
        assertNotNull(eFileNodeLocator);
        assertNotNull(documentServiceFactory);
        // assertNotNull(baseService);
        assertNotNull(documentServiceMap);
        LOG.debug("Done executing 'testWiring'");
    }

    /**
     * Setup documents.
     *
     * @param id
     *            the id
     * @param qName
     *            the q name
     */
    public void setupDocuments(String id, QName qName) {
        try {

            NodeRef nodeRef = caseNodeLocator.locateNode(id);
            LOG.info("nodeRef>>" + nodeRef);
            if (nodeRef == null) {
                caseNodeCreator.createNode(id);
            } else {
                deleteDocumentsByDocType(nodeRef, qName);
            }
        } catch (SerialNumberNotFoundException e) {
            // do Nothing
        }

    }

    /**
     * Delete documents by doc type.
     *
     * @param nodeRef
     *            the node ref
     * @param qName
     *            the q name
     */
    protected void deleteDocumentsByDocType(NodeRef nodeRef, QName qName) {
        Set<QName> qnameSet = new HashSet<QName>();
        qnameSet.add(qName);
        List<ChildAssociationRef> documents = serviceRegistry.getNodeService().getChildAssocs(nodeRef, qnameSet);
        for (ChildAssociationRef childAssociationRef : documents) {
            nodeService.deleteNode(childAssociationRef.getChildRef());
        }

    }

    /**
     * Creates the document.
     *
     * @param docType
     *            the doc type
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param metadata
     *            the metadata
     * @return the tmng alf response
     */
    public TmngAlfResponse createDocument(String docType, String id, String fileName, InputStream content,
            Map<String, Serializable> metadata) {
        return documentServiceFactory.getDocumentService(documentServiceMap.get(docType)).create(id, fileName,
                ContentItem.getInstance(content), metadata);
    }

    public TmngAlfResponse deleteDocument(String docType, String id, String fileName) {
        return documentServiceFactory.getDocumentService(documentServiceMap.get(docType)).delete(id, fileName);
    }

    /**
     * Update document.
     *
     * @param docType
     *            the doc type
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param metadata
     *            the metadata
     * @return the tmng alf response
     */
    public TmngAlfResponse updateDocument(String docType, String id, String fileName, InputStream content,
            Map<String, Serializable> metadata) {
        return documentServiceFactory.getDocumentService(documentServiceMap.get(docType)).update(id, fileName,
                ContentItem.getInstance(content), metadata);
    }

    /**
     * Retrieve metadata.
     *
     * @param docType
     *            the doc type
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @return the byte[]
     */
    public byte[] retrieveMetadata(String docType, String id, String fileName) {
        return documentServiceFactory.getDocumentService(documentServiceMap.get(docType)).retrieveMetadata(id, fileName, null,
                null);
    }

    /**
     * Retrieve content.
     *
     * @param docType
     *            the doc type
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @return the content reader
     */
    public ContentReader retrieveContent(String docType, String id, String fileName) {
        return documentServiceFactory.getDocumentService(documentServiceMap.get(docType)).retrieveContent(id, fileName, null,
                null);
    }

    /**
     * Gets the notice.
     *
     * @param json
     *            the json
     * @return the notice
     */
    public static Map<String, Serializable> getMetadata(String json) {
        return CmsValidator.parseJsonReturningMapWithCaseSensitiveKeys(json);

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

}
