package alf.unit.services;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.alfresco.repo.model.Repository;
import org.alfresco.repo.nodelocator.NodeLocatorService;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.transaction.TransactionService;
import org.alfresco.util.ApplicationContextHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.extensions.webscripts.WebScriptException;

import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.BaseService;
import gov.uspto.trademark.cms.repo.services.BehaviorImageMarkService;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.services.EfileService;
import gov.uspto.trademark.cms.repo.services.EvidenceService;
import gov.uspto.trademark.cms.repo.services.VersionService;

/**
 * The Class BaseTest.
 */
public class BaseTest {

    /** The Constant ADMIN_USER_NAME. */
    private static final String ADMIN_USER_NAME = "admin";

    /** The log. */
    private static Logger LOG = Logger.getLogger(BaseTest.class);

    /** The application context. */
    protected static ApplicationContext applicationContext;

    /** The service registry. */
    protected static ServiceRegistry serviceRegistry;
    
    protected static TransactionService transactionService;

    /** The repository helper. */
    protected static Repository repositoryHelper;

    /** The node service. */
    protected static NodeService nodeService;

    /** The node locator service. */
    protected static NodeLocatorService nodeLocatorService;

    /** The file folder service. */
    protected static FileFolderService fileFolderService;

    /** The case service. */
    protected static CaseService caseService;

    /** The base service. */
    protected static BaseService baseService;

    /** The version service. */
    protected static VersionService versionService;

    /** The image mark service. */
    protected static BehaviorImageMarkService markService;

    /** The evidence service. */
    protected static EvidenceService evidenceService;

    /** The efile service. */
    protected static EfileService efileService;

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
        nodeLocatorService = (NodeLocatorService) applicationContext.getBean("nodeLocatorService");
        fileFolderService = (FileFolderService) applicationContext.getBean("fileFolderService");
        caseService = (CaseService) applicationContext.getBean("caseServiceBase");
        versionService = (VersionService) applicationContext.getBean("versionServiceBase");
        evidenceService = (EvidenceService) applicationContext.getBean("evidenceServiceBase");
        efileService = (EfileService) applicationContext.getBean("EfileServiceBase");

        AuthenticationUtil.setFullyAuthenticatedUser(ADMIN_USER_NAME);
        LOG.debug("BaseTest.initAppContext(): If you see this message, means your unit test logging is properly configured. Change it in log4j.properties");
        LOG.debug("BaseTest.initAppContext(): Application Context properly loaded");
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
     * Creates the case folder.
     *
     * @param caseNumber
     *            the case number
     */
    protected static void createCaseFolder(final String caseNumber) {
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<NodeRef> callback = new RetryingTransactionCallback<NodeRef>() {
            public NodeRef execute() throws Throwable {
                String serialNumber = caseNumber;
                NodeRef nr = caseService.getCaseFolderNodeRef(serialNumber, true);
                return nr;
            }
        };
        NodeRef nr1 = txnHelper.doInTransaction(callback, false, true);
        assertNotNull(nr1);
    }

    /**
     * Test wiring.
     */
    @Test
    public void testWiring() {
        assertNotNull(serviceRegistry);
        assertNotNull(repositoryHelper);
        assertNotNull(nodeService);
        assertNotNull(nodeLocatorService);
        assertNotNull(fileFolderService);
        assertNotNull(caseService);
        // assertNotNull(baseService);
        assertNotNull(versionService);
        assertNotNull(evidenceService);
        assertNotNull(efileService);
        LOG.debug("Done executing 'testWiring'");
    }

    /**
     * Process json string to map.
     *
     * @param metadata
     *            the metadata
     * @param clientProperties
     *            the client properties
     * @return the map
     */
    protected Map<String, Serializable> processJsonStringToMap(String metadata, Map<String, Serializable> clientProperties) {
        if (StringUtils.isNotBlank(metadata)) {
            try {
                clientProperties = WebScriptHelper.parseJson(metadata);
            } catch (JsonParseException jpe) {
                LOG.error("Exception is {}", jpe);
                throw new WebScriptException(org.springframework.http.HttpStatus.BAD_REQUEST.value(), jpe.getMessage());
            } catch (IOException e) {
                LOG.error("Unable to parse json {}", e);
                throw new WebScriptException(org.springframework.http.HttpStatus.BAD_REQUEST.value(), e.getMessage());
            }
        }
        return clientProperties;
    }

}
