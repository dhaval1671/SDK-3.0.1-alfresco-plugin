package gov.uspto.trademark.cms.repo.webscripts.cases.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * Returns a list of documents external solr server was able to index for the given search string.
 * number
 * <p/>
 * Created by stank on 9/5/2014.
 */
public class SearchExternalSolrWebScript extends AbstractCmsCommonWebScript {

    private String externalSolrHost;
    
    public void setExternalSolrHost(String externalSolrHost) {
		this.externalSolrHost = externalSolrHost;
	}

	/** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(SearchCaseFoldersWebScript.class);

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
        processSearch(response, searchInfo, listOfString, skipCount, maxItems);
    }
    
    /* (non-Javadoc)
     * @see gov.uspto.trademark.cms.repo.webscripts.CmsCommonWebScript#validateRequest(org.springframework.extensions.webscripts.WebScriptRequest)
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

    private void processSearch(WebScriptResponse response, SearchInfo searchInfo,
            final List<Result> listOfString, Integer skipCount, Integer maxItems) {
        log.debug("Search Strings :: " + listOfString);
        RunAsWork<JSONObject> work = executeWork(searchInfo);
        JSONObject sogjReturned = AuthenticationUtil.runAs(work, AuthenticationUtil.getSystemUserName());
        response.setContentType(TMConstants.APPLICATION_JSON);
        try {
        	response.getOutputStream().write(sogjReturned.toString().getBytes("utf-8"));
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage(), e);
            }
        }
    }

    private RunAsWork<JSONObject> executeWork(final SearchInfo searchInfo) {
        RunAsWork<JSONObject> work = new RunAsWork<JSONObject>() {
            @Override
            public JSONObject doWork() {
                return executeQueryOnExternalSolrServer(searchInfo);
            }
        };
        return work;
    }

    private JSONObject executeQueryOnExternalSolrServer(final SearchInfo searchInfo) {

    	String[] arry = StringUtils.split(externalSolrHost, ':');
    	String HOSTNAME = arry[0];
    	int PORT = Integer.valueOf(arry[1]);    	
    	
    	HttpClient client = new HttpClient();
    	client.getState().setCredentials(
    		new AuthScope(HOSTNAME, PORT,AuthScope.ANY_REALM),
    		new UsernamePasswordCredentials("", ""));
    	HttpClientParams params = new HttpClientParams();
    	params.setParameter(HttpClientParams.ALLOW_CIRCULAR_REDIRECTS, Boolean.TRUE);
    	client.setParams(params);
    	HttpMethod httpMethod = new GetMethod("http://" + externalSolrHost + "/solr/collection1/select?q="+ searchInfo.getSearchText() +"&wt=json&indent=true");
    	Reader reader = null;
        JSONObject json = null;
		try {
			int responseCode = client.executeMethod(httpMethod);
			if(200 == responseCode){
			reader = new BufferedReader(new InputStreamReader(httpMethod.getResponseBodyAsStream()));	        
			json = new JSONObject(new JSONTokener(reader));
			}else{
				throw new TmngCmsException.CMSWebScriptException(HttpStatus.valueOf(responseCode), httpMethod.getResponseBodyAsString());
			}
			httpMethod.releaseConnection();
		} catch (IOException | JSONException e) {
			throw new TmngCmsException.CMSWebScriptException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
		}	        
    	return json;
	}      
    
}