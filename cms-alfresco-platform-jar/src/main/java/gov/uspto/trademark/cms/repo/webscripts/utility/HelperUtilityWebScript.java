package gov.uspto.trademark.cms.repo.webscripts.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.UserTransaction;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.evaluator.IsSubTypeEvaluator;
import org.alfresco.repo.action.executer.AddFeaturesActionExecuter;
import org.alfresco.repo.model.Repository;
import org.alfresco.repo.policy.BehaviourFilter;
import org.alfresco.repo.security.authentication.AuthenticationContext;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionCondition;
import org.alfresco.service.cmr.action.ActionService;
import org.alfresco.service.cmr.action.CompositeAction;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.rule.Rule;
import org.alfresco.service.cmr.rule.RuleService;
import org.alfresco.service.cmr.rule.RuleType;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.util.TempFileProvider;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.model.aspects.EvidenceAspect;
import gov.uspto.trademark.cms.repo.model.aspects.MultimediaRelatedAspect;
import gov.uspto.trademark.cms.repo.model.aspects.mixin.EvidenceAspectMixIn;
import gov.uspto.trademark.cms.repo.model.aspects.mixin.MultimediaAspectMixIn;
import gov.uspto.trademark.cms.repo.model.mixin.BaseTypeMixIn;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * A utility WebScript to do housekeeping and troubleshooting. This is not used
 * along with services. This is purely for local development purposes.
 *
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class HelperUtilityWebScript extends AbstractCmsCommonWebScript {

    /** The Constant PARAM_ONE. */
    private static final String PARAM_ONE = "paramOne";

    /** The Constant EXECUTING. */
    private static final String EXECUTING = "Executing ";

    /** The Constant UTF_8. */
    private static final String UTF_8 = "UTF-8";

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(HelperUtilityWebScript.class);

    /** The Constant STRING. */
    private static final String STRING = "###";

    /** The Constant TRADEMARKMODEL. */
    private static final String TRADEMARKMODEL = "trademarkmodel";

    /** The Constant TRADEMARK_MODEL. */
    private static final String TRADEMARK_MODEL = "Trademark.Model";

    /** The Constant HEADER_API_SECURITY_KEY. */
    public static final String HEADER_API_SECURITY_KEY = "API-Security-Key";

    /** The Constant RULE_ADD_CLASSIFABLE_ASPECT. */
    private static final String RULE_ADD_EVIDENCE_BANK_ASPECT = "Add Evidence Bank Aspect Rule";

    /** The api security key. */
    private String apiSecurityKey;

    /** The repository helper. */
    protected Repository repositoryHelper;

    /** The authentication context. */
    private AuthenticationContext authenticationContext;

    /** The case node creator. */
    @Autowired
    @Qualifier(value = "caseNodeCreator")
    protected CmsNodeCreator cmsNodeCreator;

    /** The cms node locator. */
    @Autowired
    @Qualifier(value = "caseNodeLocator")
    CmsNodeLocator cmsNodeLocator;

    @Autowired
    @Qualifier(value = "policyBehaviourFilter")
    private BehaviourFilter behaviourFilter;
    
    @Autowired
	@Qualifier("RuleService")
    protected RuleService ruleService;    

    /**
     * Sets the authentication context.
     *
     * @param authenticationContext
     *            the new authentication context
     */
    public void setAuthenticationContext(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    /**
     * Sets the repository helper.
     *
     * @param repositoryHelper
     *            the new repository helper
     */
    public void setRepositoryHelper(Repository repositoryHelper) {
        this.repositoryHelper = repositoryHelper;
    }

    /**
     * Sets the api security key.
     *
     * @param apiSecurityKey
     *            the new api security key
     */
    public void setApiSecurityKey(String apiSecurityKey) {
        this.apiSecurityKey = apiSecurityKey;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.webscripts.BaseWebscript#executeAction(org
     * .springframework.extensions.webscripts.WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    public void executeService(WebScriptRequest webRequest, WebScriptResponse webResponse) {

        String apiSecurityKeyLocal = webRequest.getHeader(HEADER_API_SECURITY_KEY);
        validateApiSecurityKey(apiSecurityKeyLocal);

        Map<String, String> templateArgs = webRequest.getServiceMatch().getTemplateVars();
        String methodName = templateArgs.get("testCaseName");

        runSecondBatch(webRequest, webResponse, methodName);

        runThirdBatch(methodName);

        runFourthBatch(webRequest, webResponse, methodName);

        runFifthBatch(methodName, webRequest, webResponse);

    }

    private void checkPrintStreamEncoding(ByteArrayOutputStream os, String str) {
        PrintStream ps = null;
        try {
            ps = new PrintStream(os, true, UTF_8);
            ps.append(str + "\n");
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("", e);
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            ps = null;
        }
    }

    private void runSecondBatch(WebScriptRequest webRequest, WebScriptResponse webResponse, String methodName) {
        if (StringUtils.equals("getAllExistingPropertiesOfTradeMarkModel", methodName)) {
            getAllExistingPropertiesOfTradeMarkModel();
        }

        if (StringUtils.equals("deleteTestFolder", methodName)) {
            deleteTestFolder(webRequest, webResponse);
        }

        if (StringUtils.equals("deleteEfileTestFolder", methodName)) {
            deleteEfileTestFolder(webRequest, webResponse);
        }
        if (StringUtils.equals("deleteEogTemplateTestFolder", methodName)) {
            deleteEogTemplateTestFolder(webRequest, webResponse);
        }

    }

    private void runThirdBatch(String methodName) {

        if (StringUtils.equals("studyContentType", methodName)) {

            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat(TMConstants.DATETIME_FORMAT));

            mapper.getSerializationConfig().addMixInAnnotations(AbstractBaseType.class, BaseTypeMixIn.class);
            mapper.getSerializationConfig().addMixInAnnotations(EvidenceAspect.class, EvidenceAspectMixIn.class);
            mapper.getSerializationConfig().addMixInAnnotations(MultimediaRelatedAspect.class, MultimediaAspectMixIn.class);
        }
    }

    private void runFourthBatch(WebScriptRequest webRequest, WebScriptResponse webResponse, String methodName) {
        if (StringUtils.equals("createTopLevelFolders", methodName)) {
            createTopLevelFolders(webRequest, webResponse);
        }

        if (StringUtils.equals("bulkValidateTopLevelFolders", methodName)) {
            bulkValidateTopLevelFolders(webRequest, webResponse);
        }
    }

    private void runFifthBatch(String methodName, WebScriptRequest webRequest, WebScriptResponse webResponse) {

        if (StringUtils.equals("deleteEogTestFolder", methodName)) {
            deleteEogTestFolder(webRequest, webResponse);
        }

        if (StringUtils.equals("deleteIdmTestFolder", methodName)) {
            deleteIdmTestFolder(webRequest, webResponse);
        }

        if (StringUtils.equals("deleteMadridTestFolder", methodName)) {
            deleteMadridTestFolder(webRequest, webResponse);
        }
        if (StringUtils.equals("deleteLegalProceedingTestFolder", methodName)) {
            deleteLegalProceedingTestFolder(webRequest, webResponse);
        }
        if (StringUtils.equals("deleteEvidenceBankWebcaptureFolder", methodName)) {
            deleteDocLibWebcaptureFolder(webRequest, webResponse);
        }
        if (StringUtils.equals("getAlfrescoTempDir", methodName)) {
            getAlfrescoTempDir(webRequest, webResponse);
        }
        if (StringUtils.equals("deleteAllCaseChildFolder", methodName)) {
        	deleteAllCaseChildFolder(webRequest, webResponse);
        }

    }

    private void deleteAllCaseChildFolder(WebScriptRequest webRequest, WebScriptResponse webResponse) {
    	String[] caseFolderPath = new String[] { TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.CASE_FOLDER_NAME };
        List<String> pathElements = Arrays.asList(caseFolderPath);
        NodeRef caseFolderNodeRef = null;
        try {
            caseFolderNodeRef = serviceRegistry.getFileFolderService().resolveNamePath(repositoryHelper.getCompanyHome(), pathElements).getNodeRef();
            Set<QName> qnameSet = new HashSet<QName>();
            qnameSet.add(ContentModel.TYPE_FOLDER);
            List<ChildAssociationRef> childRefList = serviceRegistry.getNodeService().getChildAssocs(caseFolderNodeRef, qnameSet);
            for (ChildAssociationRef car : childRefList) {
                NodeRef nr = car.getChildRef();
                NodeService ns = serviceRegistry.getNodeService();
                ns.addAspect(nr, ContentModel.ASPECT_TEMPORARY, null);
                ns.deleteNode(nr);
            }
        } catch (FileNotFoundException e) {
        	log.debug(caseFolderPath[caseFolderPath.length - 1] + " File or Folder doesn't exist");
        }		
	}

	private void getAlfrescoTempDir(WebScriptRequest webRequest, WebScriptResponse webResponse) {
        File tempDir = TempFileProvider.getTempDir();
        String absolutePath = tempDir.getAbsolutePath();
        log.debug("Alfresco temp dir path : " + absolutePath);
    }

    private void deleteMadridTestFolder(WebScriptRequest webRequest, WebScriptResponse webResponse) {
        log.debug(STRING + HelperUtilityWebScript.EXECUTING + Thread.currentThread().getStackTrace()[1].getMethodName() + STRING);
        Map<String, String> templateArgs = webRequest.getServiceMatch().getTemplateVars();
        String madridTestFolderToDelete = templateArgs.get(HelperUtilityWebScript.PARAM_ONE);
        log.debug("param1: " + madridTestFolderToDelete);
        List<String> pathElements = Arrays.asList(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.MADRIDIB_FOLDER_NAME,
                madridTestFolderToDelete);
        deletePublicationFolder(webResponse, pathElements);
    }

    private void deleteLegalProceedingTestFolder(WebScriptRequest webRequest, WebScriptResponse webResponse) {
        log.debug(STRING + HelperUtilityWebScript.EXECUTING + Thread.currentThread().getStackTrace()[1].getMethodName() + STRING);
        Map<String, String> templateArgs = webRequest.getServiceMatch().getTemplateVars();
        String legalProceedingTestFolderToDelete = templateArgs.get(HelperUtilityWebScript.PARAM_ONE);
        log.debug("param 1: " + legalProceedingTestFolderToDelete);
        List<String> pathElements = Arrays.asList(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.LEGAL_PROCEEDING_FOLDER_NAME,
                legalProceedingTestFolderToDelete.substring(TMConstants.ZERO, TMConstants.THREE));
        deletePublicationFolder(webResponse, pathElements);
    }

    private void deleteIdmTestFolder(WebScriptRequest webRequest, WebScriptResponse webResponse) {
        log.debug(STRING + HelperUtilityWebScript.EXECUTING + Thread.currentThread().getStackTrace()[1].getMethodName() + STRING);
        Map<String, String> templateArgs = webRequest.getServiceMatch().getTemplateVars();
        String dateFolderToDelete = templateArgs.get(HelperUtilityWebScript.PARAM_ONE);
        log.debug("param One " + dateFolderToDelete);
        List<String> pathElements = Arrays.asList(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.PUBLICATION_FOLDER_NAME,
                TradeMarkModel.IDM_FOLDER_NAME, dateFolderToDelete);
        deletePublicationFolder(webResponse, pathElements);

    }

    public void deletePublicationFolder(WebScriptResponse webScriptResponse, List<String> pathElements) {
        NodeRef nodeRef = null;
        try {
            nodeRef = serviceRegistry.getFileFolderService().resolveNamePath(repositoryHelper.getCompanyHome(), pathElements)
                    .getNodeRef();
            NodeService ns = serviceRegistry.getNodeService();
            ns.addAspect(nodeRef, ContentModel.ASPECT_TEMPORARY, null);
            ns.deleteNode(nodeRef);
        } catch (FileNotFoundException e) {
            log.info("", e);
            webScriptResponse.setStatus(HttpStatus.OK.value());
        }
    }

    private void deleteEogTestFolder(WebScriptRequest webRequest, WebScriptResponse webResponse) {
        log.debug(STRING + HelperUtilityWebScript.EXECUTING + Thread.currentThread().getStackTrace()[1].getMethodName() + STRING);
        Map<String, String> templateArgs = webRequest.getServiceMatch().getTemplateVars();
        String dateFolderToDelete = templateArgs.get(HelperUtilityWebScript.PARAM_ONE);
        log.debug("param One: " + dateFolderToDelete);
        List<String> pathElements = Arrays.asList(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.PUBLICATION_FOLDER_NAME,
                TradeMarkModel.EOG_FOLDER_NAME, dateFolderToDelete);
        deletePublicationFolder(webResponse, pathElements);

    }

    private void deleteDocLibWebcaptureFolder(WebScriptRequest webRequest, WebScriptResponse webResponse) {
        log.debug(STRING + HelperUtilityWebScript.EXECUTING + Thread.currentThread().getStackTrace()[1].getMethodName() + STRING);
        Map<String, String> templateArgs = webRequest.getServiceMatch().getTemplateVars();
        String webcaptureFolderToDelete = templateArgs.get(HelperUtilityWebScript.PARAM_ONE);
        log.debug("Param One : " + webcaptureFolderToDelete);
        List<String> pathElements = Arrays.asList(TradeMarkModel.DOCUMENT_LIBRARY_FOLDER_NAME, webcaptureFolderToDelete);
        deletePublicationFolder(webResponse, pathElements);
    }

    private void bulkValidateTopLevelFolders(WebScriptRequest webRequest, WebScriptResponse webResponse) {

        String metadata = webRequest.getParameter(METADATA_QS_PARAM);
        NodeRef companyHomeNodeRef = repositoryHelper.getCompanyHome();
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(metadata);
            JsonNode topLevelFolders = rootNode.get("TopLevelFolders");
            Iterator<JsonNode> iterator = topLevelFolders.getElements();
            walkOverNodesForValidation("Company Home", companyHomeNodeRef, iterator, webResponse, os);
        } catch (JsonProcessingException e) {
            log.debug("JSON data expected: ", e);
        } catch (IOException e) {
            log.debug("JSON data read failed: ", e);
        }

        try {
            webResponse.getOutputStream().write(os.toByteArray());
        } catch (IOException e) {
            log.debug("Write to output stream failed: ", e);
        }
    }

    private void walkOverNodesForValidation(String fullParentPath, NodeRef parentNodeRefIncoming, Iterator<JsonNode> iterator,
            WebScriptResponse webResponse, ByteArrayOutputStream os) {
        while (iterator.hasNext()) {
            JsonNode topNodes = iterator.next();
            traverseAllNodesForValidation(fullParentPath, parentNodeRefIncoming, topNodes, webResponse, os);
        }
    }

    /**
     * Traverse all nodes for validation.
     *
     * @param fullParentPath
     *            the full parent path
     * @param parentNodeRefIncoming
     *            the parent node ref incoming
     * @param node
     *            the node
     * @param webResponse
     *            the web response
     * @param os
     *            the os
     */
    public void traverseAllNodesForValidation(String fullParentPath, NodeRef parentNodeRefIncoming, JsonNode node,
            WebScriptResponse webResponse, ByteArrayOutputStream os) {
        NamespaceService nss = this.serviceRegistry.getNamespaceService();
        Iterator<String> fieldNames = node.getFieldNames();
        String parentNameOne = null;
        String parentTypeOne = null;
        String lclFullPath = fullParentPath;
        while (fieldNames.hasNext()) {
            String fieldNameOne = fieldNames.next();
            JsonNode fieldValueOne = node.get(fieldNameOne);
            if ("name".equals(fieldNameOne)) {
                parentNameOne = fieldValueOne.asText();
            }
            if ("type".equals(fieldNameOne)) {
                parentTypeOne = fieldValueOne.asText();
            }
            lclFullPath = processContainerNode(lclFullPath, parentNodeRefIncoming, webResponse, os, nss, parentNameOne,
                    parentTypeOne, fieldValueOne);
        }
    }

    private String processContainerNode(String fullParentPath, NodeRef parentNodeRefIncoming, WebScriptResponse webResponse,
            ByteArrayOutputStream os, NamespaceService nss, String parentName, String parentType, JsonNode fieldValue) {
        String result = fullParentPath;
        if (fieldValue.isContainerNode()) {
            String three = "current parent: \""
                    + this.serviceRegistry.getNodeService().getProperty(parentNodeRefIncoming, ContentModel.PROP_NAME)
                    + "\"  validating : " + parentName + " :: " + parentType;

            checkPrintStreamEncoding(os, "\n" + three);

            String str0 = parentType;
            String[] strArray = str0.split(":");
            String prefix = strArray[0];
            String localName = strArray[1];
            String nameSpaceUri = nss.getNamespaceURI(prefix);
            QName incomingQName = QName.createQName(nameSpaceUri, localName);

            List<String> pathElements = Arrays.asList(parentName);
            FileInfo fileInfo = null;
            try {
                fileInfo = this.serviceRegistry.getFileFolderService().resolveNamePath(parentNodeRefIncoming, pathElements);
            } catch (FileNotFoundException e) {
                log.trace("Did not file the file in Alfresco: ", e);
                webResponse.setStatus(org.springframework.http.HttpStatus.NOT_FOUND.value());
                checkPrintStreamEncoding(os, pathElements.toString() + "\n");
                return fullParentPath;
            }

            NodeRef parentNodeRef = matchQname(webResponse, os, parentName, incomingQName, fileInfo);
            result = fullParentPath + "," + parentName;
            walkOverNodesForValidation(result, parentNodeRef, fieldValue.getElements(), webResponse, os);
            result = fullParentPath;
        }
        return result;
    }

    private NodeRef matchQname(WebScriptResponse webResponse, ByteArrayOutputStream os, String parentName, QName incomingQName,
            FileInfo fileInfo) {
        NodeRef nodeRef = fileInfo.getNodeRef();
        QName qn = this.serviceRegistry.getNodeService().getType(nodeRef);
        if (!(qn.isMatch(incomingQName))) {
            webResponse.setStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR.value());
            StringBuilder sb = new StringBuilder();
            sb.append("QName did not match");
            sb.append("QName from alfresco: " + qn.toString());
            sb.append("QName from json: " + incomingQName.toString());
            checkPrintStreamEncoding(os, sb.toString() + "\n");
        } else {
            String msg = parentName + " :: " + "QName from json: " + incomingQName.toString() + "<-:->" + "QName from alfresco: "
                    + qn.toString() + "\n";
            checkPrintStreamEncoding(os, msg);
        }

        NodeRef parentNodeRef = fileInfo.getNodeRef();
        if ("Evidence Bank".equals(parentName)) {
            log.debug("validate if it has rule...");
            checkIfEvidenceBankAspectIsApplied(parentNodeRef, webResponse, os);
        }
        return parentNodeRef;
    }

    private void checkIfEvidenceBankAspectIsApplied(NodeRef evidenceBankNodeRef, WebScriptResponse webResponse,
            ByteArrayOutputStream os) {
        if (evidenceBankNodeRef != null) {
            StringBuilder sb = new StringBuilder();
            String nodeName = (String) this.serviceRegistry.getNodeService().getProperty(evidenceBankNodeRef,
                    ContentModel.PROP_NAME);
            if (nodeName != null && !hasRule(evidenceBankNodeRef, RULE_ADD_EVIDENCE_BANK_ASPECT)) {
                webResponse.setStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR.value());
                sb.append("\n " + nodeName + " does NOT have rule applied to it.");
            } else {
                sb.append("\n " + nodeName + " have rule applied to it.");
            }
            checkPrintStreamEncoding(os, sb.toString() + "\n");
        }
    }

    private void createTopLevelFolders(WebScriptRequest webRequest, WebScriptResponse webResponse) {
        String metadata = webRequest.getParameter(METADATA_QS_PARAM);
        NodeRef companyHomeNodeRef = repositoryHelper.getCompanyHome();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(metadata);
            JsonNode topLevelFolders = rootNode.get("TopLevelFolders");
            Iterator<JsonNode> iterator = topLevelFolders.getElements();
            walkOverNodes(companyHomeNodeRef, iterator);
            TopLevelFolders tlf = JacksonHelper.unMarshall(metadata, TopLevelFolders.class);
            JSONObject json = new JSONObject(tlf);
            webResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            webResponse.setContentType(TMConstants.APPLICATION_JSON);
            webResponse.getOutputStream().write(json.toString().getBytes(Charset.forName(UTF_8)));
        } catch (JsonProcessingException e) {
            log.debug("JSON processing error", e);
        } catch (IOException e) {
            log.debug("", e);
        }
    }

    private void walkOverNodes(NodeRef parentNodeRefIncoming, Iterator<JsonNode> iterator) {
        while (iterator.hasNext()) {
            JsonNode topNodes = iterator.next();
            traverseAllNodes(parentNodeRefIncoming, topNodes);
        }
    }

    /**
     * Traverse all nodes.
     *
     * @param parentNodeRefIncoming
     *            the parent node ref incoming
     * @param node
     *            the node
     */
    public void traverseAllNodes(NodeRef parentNodeRefIncoming, JsonNode node) {
        NamespaceService nss = this.serviceRegistry.getNamespaceService();
        Iterator<String> fieldNames = node.getFieldNames();
        String parentNameTwo = null;
        String parentTypeTwo = null;
        while (fieldNames.hasNext()) {
            String fieldNameTwo = fieldNames.next();
            JsonNode fieldValueTwo = node.get(fieldNameTwo);
            if ("name".equals(fieldNameTwo)) {
                parentNameTwo = fieldValueTwo.asText();
            }
            if ("type".equals(fieldNameTwo)) {
                parentTypeTwo = fieldValueTwo.asText();
            }
            if (fieldValueTwo.isContainerNode()) {
                log.debug("current parent: " + parentNodeRefIncoming + "  Creating Parent before going to children: "
                        + parentNameTwo + " :: " + parentTypeTwo);
                String nameOfFolder = parentNameTwo;
                String str0 = parentTypeTwo;
                String[] strArray = new String[TMConstants.ONE];
                if (null != str0 && str0.contains(":")) {
                    strArray = str0.split(":");
                } else {
                    strArray[0] = str0;
                }
                String prefix = strArray[0];
                String localName = strArray[1];
                String nameSpaceUri = nss.getNamespaceURI(prefix);
                QName incomingQName = QName.createQName(nameSpaceUri, localName);
                authenticationContext.setSystemUserAsCurrentUser();
                FileInfo fi = this.serviceRegistry.getFileFolderService().create(parentNodeRefIncoming, nameOfFolder,
                        incomingQName);
                NodeRef parentNodeRef = fi.getNodeRef();

                if ("Evidence Bank".equals(parentNameTwo)) {
                    log.debug("applying rule...");
                    applyEvidenceBankAspect(parentNodeRef);
                }
                walkOverNodes(parentNodeRef, fieldValueTwo.getElements());
            }
        }
    }

    private void applyEvidenceBankAspect(NodeRef evidenceBankNodeRef) {

        ActionService actionService = serviceRegistry.getActionService();
        UserTransaction userTransaction = transactionService.getUserTransaction();
        try {
            userTransaction.begin();
            if ((evidenceBankNodeRef != null) && (!hasRule(evidenceBankNodeRef, RULE_ADD_EVIDENCE_BANK_ASPECT))) {

                Rule rule = new Rule();
                rule.setRuleType(RuleType.INBOUND);
                rule.setTitle(RULE_ADD_EVIDENCE_BANK_ASPECT);
                rule.applyToChildren(true); // set this to true if you want to
                                            // cascade to sub folders

                CompositeAction compositeAction = actionService.createCompositeAction();
                rule.setAction(compositeAction);

                Map<String, Serializable> actionMap = new HashMap<String, Serializable>();
                actionMap.put(IsSubTypeEvaluator.PARAM_TYPE, ContentModel.TYPE_CONTENT);
                ActionCondition actionCondition = actionService.createActionCondition(IsSubTypeEvaluator.NAME, actionMap);
                compositeAction.addActionCondition(actionCondition);

                // The Add Aspect Action
                Action action = actionService.createAction(AddFeaturesActionExecuter.NAME);
                action.setTitle("Add Evidence Bank Aspect Action");
                action.setExecuteAsynchronously(true);

                Map<String, Serializable> ruleParameters = new HashMap<String, Serializable>(1);
                // The Aspect to add
                ruleParameters.put(AddFeaturesActionExecuter.PARAM_ASPECT_NAME, TradeMarkModel.ASPECT_EVIDENCE_BANK);
                action.setParameterValues(ruleParameters);

                compositeAction.addAction(action);

                ruleService.saveRule(evidenceBankNodeRef, rule);
            }

            userTransaction.commit();
        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (Exception e1) {
                log.debug("", e1);
            }
            authenticationContext.clearCurrentSecurityContext();
            throw new AlfrescoRuntimeException("case Bootstrap failed", e);
        } finally {
            authenticationContext.clearCurrentSecurityContext();
        }
    }

    private boolean hasRule(NodeRef nodeRef, String ruleTitle) {
        List<Rule> rules = ruleService.getRules(nodeRef);

        boolean hasRule = false;

        for (Rule rule : rules) {
            if (ruleTitle.equals(rule.getTitle())) {
                hasRule = true;
                break;
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("hasRule = " + hasRule);
        }

        return hasRule;
    }

    private void deleteTestFolder(WebScriptRequest webRequest, WebScriptResponse webResponse) {
        log.debug(STRING + HelperUtilityWebScript.EXECUTING + Thread.currentThread().getStackTrace()[1].getMethodName() + STRING);
        Map<String, String> templateArgs = webRequest.getServiceMatch().getTemplateVars();
        String serialNumberToDelete = templateArgs.get(HelperUtilityWebScript.PARAM_ONE);
        log.debug("paramOne " + serialNumberToDelete);
        List<String> pathElements = Arrays.asList(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.CASE_FOLDER_NAME,
                serialNumberToDelete.substring(TMConstants.ZERO, TMConstants.THREE));
        deletePublicationFolder(webResponse, pathElements);

    }

    private void deleteEfileTestFolder(WebScriptRequest webRequest, WebScriptResponse webResponse) {
        log.debug(STRING + HelperUtilityWebScript.EXECUTING + Thread.currentThread().getStackTrace()[1].getMethodName() + STRING);
        Map<String, String> templateArgs = webRequest.getServiceMatch().getTemplateVars();
        String efileIdToDelete = templateArgs.get(HelperUtilityWebScript.PARAM_ONE);
        String allFolderToBeDeleted = templateArgs.get("paramTwo");
        NodeService ns = serviceRegistry.getNodeService();
        List<String> pathElements = Arrays.asList(TradeMarkModel.EFILE_DRIVE, TradeMarkModel.TYPE_EFILE_FOLDER);
        NodeRef efileFolderNodeRef = null;
        NodeRef submissionsNodeRef = null;
        try {
            efileFolderNodeRef = serviceRegistry.getFileFolderService()
                    .resolveNamePath(repositoryHelper.getCompanyHome(), pathElements).getNodeRef();
        } catch (FileNotFoundException e) {
            log.info("", e);
        }
        Set<QName> qnameSet = new HashSet<QName>();
        qnameSet.add(ContentModel.TYPE_FOLDER);
        List<ChildAssociationRef> childNodeRefs = ns.getChildAssocs(efileFolderNodeRef, qnameSet);

        if ("all".equalsIgnoreCase(allFolderToBeDeleted)) {
            for (ChildAssociationRef childAssociationRef : childNodeRefs) {
                NodeRef childeNR = childAssociationRef.getChildRef();
                String nodeName = (String) ns.getProperty(childeNR, ContentModel.PROP_NAME);
                log.debug(STRING + "Remove/Delete: " + nodeName + STRING);
                ns.addAspect(childeNR, ContentModel.ASPECT_TEMPORARY, null);
                ns.deleteNode(childeNR);
            }
        } else {
            for (ChildAssociationRef childAssociationRef : childNodeRefs) {
                NodeRef childeNR = childAssociationRef.getChildRef();
                String nodeName = (String) ns.getProperty(childeNR, ContentModel.PROP_NAME);
                log.debug(STRING + "Erase/delete: " + nodeName + STRING);
                if (efileIdToDelete.matches(nodeName + ".*")) {
                    ns.addAspect(childeNR, ContentModel.ASPECT_TEMPORARY, null);
                    ns.deleteNode(childeNR);
                    break;
                }
            }
        }

        // clean up submissions folder as well
        List<String> submissionPathElements = Arrays.asList(TradeMarkModel.CABINET_FOLDER_NAME,
                TradeMarkModel.SUBMISSIONS_FOLDER_NAME);
        try {
            submissionsNodeRef = serviceRegistry.getFileFolderService()
                    .resolveNamePath(repositoryHelper.getCompanyHome(), submissionPathElements).getNodeRef();
        } catch (FileNotFoundException e) {
            log.info("", e);
        }
        List<ChildAssociationRef> submissionChildNodeRefs = ns.getChildAssocs(submissionsNodeRef, qnameSet);
        for (ChildAssociationRef childAssociationRef : submissionChildNodeRefs) {
            NodeRef childeNR = childAssociationRef.getChildRef();
            String nodeName = (String) ns.getProperty(childeNR, ContentModel.PROP_NAME);
            log.debug(STRING + "Removing/Deleting: " + nodeName + STRING);
            if (efileIdToDelete.matches(nodeName + ".*")) {
                ns.deleteNode(childeNR);
                break;
            }
        }
        webResponse.setStatus(HttpStatus.OK.value());
    }

    private void deleteEogTemplateTestFolder(WebScriptRequest webRequest, WebScriptResponse webResponse) {
        log.debug(STRING + HelperUtilityWebScript.EXECUTING + Thread.currentThread().getStackTrace()[1].getMethodName() + STRING);
        Map<String, String> templateArgs = webRequest.getServiceMatch().getTemplateVars();
        String efileIdToDelete = templateArgs.get(HelperUtilityWebScript.PARAM_ONE);
        String allFolderToBeDeleted = templateArgs.get("paramTwo");
        NodeService ns = serviceRegistry.getNodeService();
        List<String> pathElements = Arrays.asList(TradeMarkModel.EFILE_DRIVE, TradeMarkModel.TYPE_EOG_TEMPLATE_FOLDER);
        NodeRef efileFolderNodeRef = null;
        try {
            efileFolderNodeRef = serviceRegistry.getFileFolderService()
                    .resolveNamePath(repositoryHelper.getCompanyHome(), pathElements).getNodeRef();
        } catch (FileNotFoundException e) {
            log.info("", e);
        }
        QName qn = QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI, TradeMarkModel.TYPE_EOG_FOLDER);
        Set<QName> qnameSet = new HashSet<QName>();
        qnameSet.add(qn);
        List<ChildAssociationRef> childNodeRefs = ns.getChildAssocs(efileFolderNodeRef, qnameSet);

        if ("all".equalsIgnoreCase(allFolderToBeDeleted)) {
            for (ChildAssociationRef childAssociationRef : childNodeRefs) {
                NodeRef childeNR = childAssociationRef.getChildRef();
                String nodeName = (String) ns.getProperty(childeNR, ContentModel.PROP_NAME);
                log.debug(STRING + "Deleting/Removing: " + nodeName + STRING);
                ns.addAspect(childeNR, ContentModel.ASPECT_TEMPORARY, null);
                ns.deleteNode(childeNR);
            }
        } else {
            for (ChildAssociationRef childAssociationRef : childNodeRefs) {
                NodeRef childeNR = childAssociationRef.getChildRef();
                String nodeName = (String) ns.getProperty(childeNR, ContentModel.PROP_NAME);
                log.debug(STRING + "Deleting: " + nodeName + STRING);
                if (efileIdToDelete.matches(nodeName + ".*")) {
                    ns.addAspect(childeNR, ContentModel.ASPECT_TEMPORARY, null);
                    ns.deleteNode(childeNR);
                    break;
                }
            }
        }

        webResponse.setStatus(HttpStatus.OK.value());
    }

    private void validateApiSecurityKey(String apiSecurityKey) {
        if (StringUtils.isBlank(apiSecurityKey)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Missing API-Security-Key.");
        } else if (!this.apiSecurityKey.equals(apiSecurityKey)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "API-Security-Key does NOT match.");
        }
    }

    private void getAllExistingPropertiesOfTradeMarkModel() {

        QName model = QName.createQName(TRADEMARK_MODEL, TRADEMARKMODEL);
        Collection<QName> modelProps = serviceRegistry.getDictionaryService().getProperties(model);
        log.debug("START: print all properties for: " + model.toString());
        for (QName qn : modelProps) {
            log.debug(qn.toString());
        }
        log.debug("END: print all properties for: " + model.toString());
    }

    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        if (StringUtils.isBlank(WebScriptHelper.getUrlParameters(webScriptRequest).get("testCaseName"))) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "'testCaseName' is a mandatory parameter");
        }
    }

}