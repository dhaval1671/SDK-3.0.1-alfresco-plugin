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
import alf.integration.service.all.base.VideoMarkBaseTest;
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

public class TicrsAdminDeleteMultimediaTests extends CentralBase {

    /** The log. */
    public static Logger LOG = Logger.getLogger(TicrsAdminDeleteMultimediaTests.class);    

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
    public void createSpecimenMultimediaAndTryDeletingUsingTicrsAdminDeleteApi(){
    	UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SPECIMEN.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("specimenMultimediaToBeDeletedByTicrsMultimediaApi.avi");        
        createMultimediaSpecimenForDeletion(urlInput);        
        deleteDocumentUsingTicrsAdminDeleteApiThisShouldAlsoDeleteFileInsideMultimediaFolder(urlInput);          
    }

    private void createMultimediaSpecimenForDeletion(UrlInputDto urlInput) {
    	String VALUE_SPECIMEN_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateSpecimenIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";    	
        Map<String, String> mulMarkParam = new HashMap<String, String>();
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        //System.out.println(MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(), VALUE_SPECIMEN_METADATA);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), VideoMarkBaseTest.CONTENT_MMRK_AVI); 
        ClientResponse responseOne = createDocument(mulMarkParam);
        //String strOne = responseOne.getEntity(String.class);
        //System.out.println(strOne);   
        assertEquals(201, responseOne.getStatus());
    }

    private void deleteDocumentUsingTicrsAdminDeleteApiThisShouldAlsoDeleteFileInsideMultimediaFolder(UrlInputDto urlInput) {
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
        String docIdImagePostFix = "\"documentId\":\"/case/"+urlInput.getSerialNumber()+"/specimen/"+urlInput.getFileName()+"\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( strTwo, docIdImagePostFix));
    }



}
