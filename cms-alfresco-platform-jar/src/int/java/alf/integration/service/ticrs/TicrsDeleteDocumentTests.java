package alf.integration.service.ticrs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.TicrsRelatedUrl;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class DeleteEfileTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class TicrsDeleteDocumentTests extends CentralBase {

    /** The log. */
    public static Logger LOG = Logger.getLogger(TicrsDeleteDocumentTests.class);    

    /** The client. */
    public Client client = null;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD));        
    }    

    /**
     * Tear down.
     */
    @After
    public void tearDown() {

    }    

    @Test
    public void createMarkAndTryToDeleteUsinngTicrsApi(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777805_TICRS_ADMIN_DELETE.toString());
        urlInput.setFileName("ticrsMarkToBeDelete.jpg");        
        createMarkForDeletion(urlInput);        
        deleteDocumentUsingTicrsAdminDeleteApi(urlInput);          
        retryDeletion(urlInput); 
    }

    private void createMarkForDeletion(UrlInputDto urlInput) {
        //create sample mark
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(),MarkBaseTest.VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        ClientResponse responseOne = createDocument(mrkParam);
        //String strOne = responseOne.getEntity(String.class);
        //System.out.println(strOne);   
        assertEquals(201, responseOne.getStatus());
    }

    private void deleteDocumentUsingTicrsAdminDeleteApi(UrlInputDto urlInput) {
        //delete using ticrs api.
        String TICRS_ADMIN_DELETE_WEBSCRIPT_URL = TicrsRelatedUrl.ticrsAdminDeleteUrl(urlInput);
        //System.out.println(TICRS_ADMIN_DELETE_WEBSCRIPT_URL);
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);
        WebResource webResource = client.resource(TICRS_ADMIN_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseTwo = b.delete(ClientResponse.class);        
        String strTwo = responseTwo.getEntity(String.class);
        //System.out.println(strTwo);   
        assertEquals(200, responseTwo.getStatus()); 
        String docIdImagePostFix = "\"documentId\":\"/case/"+urlInput.getSerialNumber()+"/mark/"+urlInput.getFileName()+"\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( strTwo, docIdImagePostFix));
    }

    private void retryDeletion(UrlInputDto urlInput) {
        //retryDeleteOperation
        String TICRS_ADMIN_DELETE_WEBSCRIPT_URL_TWO= TicrsRelatedUrl.ticrsAdminDeleteUrl(urlInput);       
        DefaultApacheHttpClientConfig configThree = new DefaultApacheHttpClientConfig();
        configThree.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient clientThree = ApacheHttpClient.create(configThree);
        WebResource webResourceThree = clientThree.resource(TICRS_ADMIN_DELETE_WEBSCRIPT_URL_TWO);
        Builder bThree = webResourceThree.type(MediaType.APPLICATION_JSON);
        ClientResponse responseThree = bThree.delete(ClientResponse.class);        
        //String strThree = responseThree.getEntity(String.class);
        //System.out.println(strThree);   
        assertEquals(404, responseThree.getStatus());
    }

}
