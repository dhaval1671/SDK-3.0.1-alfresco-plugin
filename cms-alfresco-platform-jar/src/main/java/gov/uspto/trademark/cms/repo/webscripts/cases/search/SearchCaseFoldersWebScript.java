package gov.uspto.trademark.cms.repo.webscripts.cases.search;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.domain.node.ContentDataWithId;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.DocumentId;

/**
 * Returns a list of all documents with complete metadata for a given serial
 * number
 * <p/>
 * Created by stank on 9/5/2014.
 */
public class SearchCaseFoldersWebScript extends AbstractCmsCommonWebScript {

    /** The Constant DEFAULT_SKIP_COUNT. */
    private static final int DEFAULT_SKIP_COUNT = 0;

    /** The Constant DEFAULT_PAGE_SIZE. as per rally story US10158 */
    private static final int DEFAULT_PAGE_SIZE = 50;

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(SearchCaseFoldersWebScript.class);

    /** The case service. */
    private CaseService caseService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.webscripts.BaseWebscript#executeAction(org
     * .springframework.extensions.webscripts.WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    protected void executeService(WebScriptRequest request, WebScriptResponse response) {

        final String requestBody = getContentAsString(request);
        final SearchOutGoingJson sogj = new SearchOutGoingJson();
        SearchInfo searchInfo = new SearchInfo();
        final List<Result> listOfString = new ArrayList<Result>();

        SearchIncomingJson searchJson = null;
        try {
            searchJson = JacksonHelper.unMarshall(requestBody, SearchIncomingJson.class);
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        String searchString = searchJson.getQuery().getMatch();
        searchInfo.setSearchText(searchString);

        Pagination pagination = searchJson.getPagination();
        Integer skipCount = null;
        Integer maxItems = null;
        if (pagination != null) {
            skipCount = pagination.getFrom();
            maxItems = pagination.getSize();
        }
        // Search the item
        NodeRef cabinetCaseFolderNodeRef = caseService.getCabinetCaseFolderNodeRef();
        Path path = this.serviceRegistry.getNodeService().getPath(cabinetCaseFolderNodeRef);
        String pathStr = path.toPrefixString(serviceRegistry.getNamespaceService());

        String prefixSearchQuery = "((PATH:\"" + pathStr + "//*\" AND (TYPE:\"{" + TradeMarkModel.TRADEMARK_MODEL_1_0_URI
                + "}document\" AND (";
        String postfixSearchQuery = "))) AND -TYPE:\"cm:thumbnail\" AND -TYPE:\"cm:failedThumbnail\" AND -TYPE:\"cm:rating\" )"
                + " AND NOT ASPECT:\"sys:hidden\"";

        String query = generateSearchQuery(prefixSearchQuery, searchString, postfixSearchQuery);

        processSearch(response, sogj, searchInfo, listOfString, skipCount, maxItems, query);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.webscripts.CmsCommonWebScript#
     * validateRequest(org.springframework.extensions.webscripts.
     * WebScriptRequest)
     */
    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        String errorMsg = null;
        final String requestBody = getContentAsString(webScriptRequest);
        errorMsg = "Missing search query json";
        // Check for request body
        if (StringUtils.isBlank(requestBody)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, errorMsg);
        }
    }

    /**
     * Process search.
     *
     * @param response
     *            the response
     * @param sogj
     *            the sogj
     * @param searchInfo
     *            the search info
     * @param listOfString
     *            the list of string
     * @param skipCount
     *            the skip count
     * @param maxItems
     *            the max items
     * @param query
     *            the query
     */
    private void processSearch(WebScriptResponse response, final SearchOutGoingJson sogj, SearchInfo searchInfo,
            final List<Result> listOfString, Integer skipCount, Integer maxItems, String query) {
        log.debug("Search Query :: " + query);
        final SearchParameters sp = new SearchParameters();
        sp.setLanguage(SearchService.LANGUAGE_SOLR_FTS_ALFRESCO);
        StoreRef storeRef = new StoreRef(StoreRef.PROTOCOL_WORKSPACE, "SpacesStore");
        sp.addStore(storeRef);
        sp.setQuery(query);

        if (skipCount != null) {
            sp.setSkipCount(skipCount);
            searchInfo.setSkipCount(skipCount);
        } else {
            sp.setSkipCount(SearchCaseFoldersWebScript.DEFAULT_SKIP_COUNT);
            searchInfo.setSkipCount(SearchCaseFoldersWebScript.DEFAULT_SKIP_COUNT);
        }

        if (maxItems != null) {
            if (maxItems < 0) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                        "Negative numbers are NOT allowed for 'size' attribute.");
            } else {
                sp.setMaxItems(maxItems);
                searchInfo.setMaxItemsPerPage(maxItems);
            }
        } else {
            sp.setMaxItems(SearchCaseFoldersWebScript.DEFAULT_PAGE_SIZE);
            searchInfo.setMaxItemsPerPage(SearchCaseFoldersWebScript.DEFAULT_PAGE_SIZE);
        }

        RunAsWork<SearchOutGoingJson> work = executeWork(sp, sogj, listOfString, searchInfo);
        SearchOutGoingJson sogjReturned = AuthenticationUtil.runAs(work, AuthenticationUtil.getSystemUserName());
        response.setContentType(TMConstants.APPLICATION_JSON);
        try {
            response.getOutputStream().write(JacksonHelper.generateClientJsonFrDTO(sogjReturned));
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage(), e);
            }
        }
    }

    /**
     * Execute work.
     *
     * @param sp
     *            the sp
     * @param sogj
     *            the sogj
     * @param listOfString
     *            the list of string
     * @param searchInfo
     *            the search info
     * @return the run as work
     */
    private RunAsWork<SearchOutGoingJson> executeWork(final SearchParameters sp, final SearchOutGoingJson sogj,
            final List<Result> listOfString, final SearchInfo searchInfo) {
        RunAsWork<SearchOutGoingJson> work = new RunAsWork<SearchOutGoingJson>() {
            @Override
            public SearchOutGoingJson doWork() {
                ResultSet rs = null;
                try {
                    rs = serviceRegistry.getSearchService().query(sp);
                    Long searchHitCount = rs.getNumberFound();
                    searchInfo.setSearchHitCount(searchHitCount);
                    sogj.setSearchInfo(searchInfo);
                    List<NodeRef> refs = rs.getNodeRefs();
                    if (refs != null) {
                        for (NodeRef ref : refs) {
                            Map<QName, Serializable> properties = serviceRegistry.getNodeService().getProperties(ref);
                            String serialNumberLocal = (String) (properties.get(TradeMarkModel.SERIAL_NUMBER_QNAME));
                            QName qn = serviceRegistry.getNodeService().getType(ref);
                            String type = qn.getLocalName();
                            String typeMarker = TradeMarkDocumentTypes.getTradeMarkType(type).getAlfrescoTypeName();
                            String documentName = (String) properties.get(ContentModel.PROP_NAME);
                            String documentId = new DocumentId(TradeMarkModel.CASE_FOLDER_NAME, serialNumberLocal, typeMarker,
                                    documentName).getId();
                            Metadata metadata = getMetadata(ref, properties);
                            Result result = new Result();
                            result.setDocumentId(documentId);
                            result.setMetadata(metadata);
                            listOfString.add(result);
                        }
                        sogj.setResults(listOfString);
                    }

                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                }
                return sogj;
            }

        };
        return work;
    }

    /**
     * Gets the metadata.
     *
     * @param ref
     *            the ref
     * @param properties
     *            the properties
     * @return the metadata
     */
    private Metadata getMetadata(NodeRef ref, Map<QName, Serializable> properties) {

        Metadata metadata = new Metadata();
        String documentName = (String) (properties.get(ContentModel.PROP_NAME));
        metadata.setDocumentName(documentName);

        String documentAlias = (String) (properties.get(TradeMarkModel.PROP_DOCUMENT_ALIAS));
        metadata.setDocumentAlias(documentAlias);

        String legacyCategory = (String) (properties.get(TradeMarkModel.PROP_LEGACY_CATEGORY));
        metadata.setLegacyCategory(legacyCategory);

        String docCode = (String) (properties.get(TradeMarkModel.PROP_DOC_CODE));
        metadata.setDocCode(docCode);

        String accessLevel = (String) (properties.get(TradeMarkModel.PROP_ACCESS_LEVEL));
        metadata.setAccessLevel(accessLevel);

        Date mailDate = (Date) (properties.get(TradeMarkModel.PROP_MAIL_DATE));
        metadata.setMailDate(mailDate);

        QName qname = serviceRegistry.getNodeService().getType(ref);
        TradeMarkDocumentTypes tmCaseDocumentType = TradeMarkDocumentTypes.getTradeMarkType(qname.getLocalName());
        metadata.setDocumentType(tmCaseDocumentType.getAlfrescoTypeName());

        String mimeType = null;
        ContentDataWithId contentDataID = (ContentDataWithId) (properties.get(ContentModel.PROP_CONTENT));
        if (contentDataID != null) {
            mimeType = contentDataID.getMimetype();
        }
        metadata.setMimeType(mimeType);

        return metadata;
    }

    /**
     * Generate search query.
     *
     * @param prefixQuery
     *            the prefix query
     * @param searchString
     *            the search string
     * @param postfixQuery
     *            the postfix query
     * @return the string
     */
    private String generateSearchQuery(String prefixQuery, String searchString, String postfixQuery) {
        String query = null;
        if (WebScriptHelper.isQuoted(searchString)) {
            query = cookSearchQueryForQuotedString(prefixQuery, searchString, postfixQuery);
        } else {
            query = cookSearchQueryForUnquotedString(prefixQuery, searchString, postfixQuery);
        }
        return query;
    }

    /**
     * Cook search query for quoted string.
     *
     * @param prefixQuery
     *            the prefix query
     * @param searchString
     *            the search string
     * @param postfixQuery
     *            the postfix query
     * @return the string
     */
    private String cookSearchQueryForQuotedString(String prefixQuery, String searchString, String postfixQuery) {
        String unquotedText = WebScriptHelper.unquote(searchString);
        String midfix = "(@{" + NamespaceService.CONTENT_MODEL_1_0_URI + "}name:\"" + unquotedText + "\" TEXT:\"" + unquotedText
                + "\" )";
        String query = null;
        query = prefixQuery + midfix + postfixQuery;
        return query;
    }

    /**
     * Cook search query for unquoted string.
     *
     * @param prefixQuery
     *            the prefix query
     * @param searchString
     *            the search string
     * @param postfixQuery
     *            the postfix query
     * @return the string
     */
    private String cookSearchQueryForUnquotedString(String prefixQuery, String searchString, String postfixQuery) {
        String query = null;
        String midfix = null;
        String[] strArray = searchString.split(" ");
        midfix = getQueryPuzzle(strArray);
        query = prefixQuery + midfix + postfixQuery;
        return query;
    }

    /**
     * Gets the query puzzle.
     *
     * @param elements
     *            the elements
     * @return the query puzzle
     */
    private static String getQueryPuzzle(String[] elements) {
        StringBuilder midFix = new StringBuilder();
        for (String unquotedText : elements) {
            midFix.append("(@{" + NamespaceService.CONTENT_MODEL_1_0_URI + "}name:\"").append(unquotedText).append("\" TEXT:\"")
                    .append(unquotedText).append("\" )");
        }
        return midFix.toString();
    }

    /**
     * Gets the case service.
     *
     * @return the case service
     */
    public CaseService getCaseService() {
        return caseService;
    }

    /**
     * Sets the case service.
     *
     * @param caseService
     *            the new case service
     */
    public void setCaseService(CaseService caseService) {
        this.caseService = caseService;
    }

}