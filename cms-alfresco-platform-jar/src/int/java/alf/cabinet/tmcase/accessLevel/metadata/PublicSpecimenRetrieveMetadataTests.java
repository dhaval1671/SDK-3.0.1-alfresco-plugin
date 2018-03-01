package alf.cabinet.tmcase.accessLevel.metadata;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

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

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.SpecimenBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Specimen;

/**
 * Retrieve Specimen metadata test cases.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class PublicSpecimenRetrieveMetadataTests extends CentralBase {

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

    /**
     * Test retrieve specimen metadata.
     */
    @Test
    public void createAndRetrveRestrictedSpecimenMetadataWithClientReqAccessLevelPublic() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SPECIMEN.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777784_NUMBER.toString());
        urlInput.setFileName("AccessLevelPublicSpecimen_RtrveMetadata.pdf");
        
        //create specimen
        specimenCreateAndRecreateTest(urlInput);
        
        retrievePublicSpecimenWithClientRequestPublicAccessLevel(urlInput); 
        retrievePublicSpecimenWithClientRequestInternalAccessLevel(urlInput);
        retrievePublicSpecimenWithClientRequestRestrictedAccessLevel(urlInput);
    }
    
    private void retrievePublicSpecimenWithClientRequestPublicAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel("public");
        String NOTICE_RETRIEVE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);
        //System.out.println(NOTICE_RETRIEVE_METADATA_WEBSCRIPT_URL);
        WebResource webResource = client.resource(NOTICE_RETRIEVE_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
        String haystack = str;
        String docType = "\"accessLevel\"(.*?)\""+ "public" + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docType));        
    }

    private void retrievePublicSpecimenWithClientRequestInternalAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel("internal");
        String NOTICE_RETRIEVE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);
        //System.out.println(NOTICE_RETRIEVE_METADATA_WEBSCRIPT_URL);
        WebResource webResource = client.resource(NOTICE_RETRIEVE_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
        String haystack = str;
        String docType = "\"accessLevel\"(.*?)\""+ "public" + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docType));        
    }    
    
    private void retrievePublicSpecimenWithClientRequestRestrictedAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel("restricted");
        String NOTICE_RETRIEVE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);
        //System.out.println(NOTICE_RETRIEVE_METADATA_WEBSCRIPT_URL);
        WebResource webResource = client.resource(NOTICE_RETRIEVE_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
        String haystack = str;
        String docType = "\"accessLevel\"(.*?)\""+ "public" + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docType));
    }    
    
    private void specimenCreateAndRecreateTest(UrlInputDto urlInput) {
        String VALUE_NOTICE_METADATA = "{  \"accessLevel\": \"public\",  \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",    \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"specimenSourceType\": \"OA\",     \"specimenSourceTypeId\": \"123\",     \"specimenSourceUrl\": \"http://local/specimen/source/url\",     \"specimenGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"specimenCategory\": \"nexis-lexis\", \"redactionLevel\":\"Partial\" }";        
        Map<String, String> specimenParam = new HashMap<String, String>();
        String NOTICE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput); 
        //System.out.println(NOTICE_CREATE_WEBSCRIPT_URL);
        specimenParam.put(ParameterKeys.URL.toString(),NOTICE_CREATE_WEBSCRIPT_URL);
        specimenParam.put(ParameterKeys.METADATA.toString(),VALUE_NOTICE_METADATA);
        specimenParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SpecimenBaseTest.CONTENT_SPECIMEN_ATTACHMENT);        
        ClientResponse response = createDocument(specimenParam);        
        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ Specimen.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));  
    }    



}
