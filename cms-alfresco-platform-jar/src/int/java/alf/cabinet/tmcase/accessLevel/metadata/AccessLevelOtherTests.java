package alf.cabinet.tmcase.accessLevel.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
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
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.base.TmnguiBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;


/**
 * @author stank
 *
 */

public class AccessLevelOtherTests extends EvidenceBaseTest {

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
    
    //DE7380
    @Test
    public void tryChangingPublicAccessLevelToInternalWithoutPassingIt(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("PublicToInternal.pdf");        
        evidenceCreate(urlInput);
        tryUpdatingEvidenceMetadataWhenTheOriginalAccessLevelIsPublic(urlInput);
        retrieveMetadataAndVerifyAccessLevel(urlInput);
    }
    
    /**  
     * @Title: retrieveMetadataAndVerifyAccessLevel  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void retrieveMetadataAndVerifyAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel("restricted");
        String EVI_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);          
        WebResource webResource = client.resource(EVI_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);        
        assertEquals(200, response.getStatus());
        String haystack = str;
        //check for accessLevel status
        String accessLevel = "\"accessLevel\":\"public\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, accessLevel));         
    }

    private void evidenceCreate(UrlInputDto urlInput){
        String VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC = "{  \"accessLevel\": \"public\", \"sourceMedia\": \"CreateOne\", \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\", \"sourceMedium\": \"upload\",    \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\":\"Partial\" }";        
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(),VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        testCreateEvidence(eviParam);
    }   
    
    /**
     * Test create evidence.
     *
     * @param eviParam the evi param
     */
    private void testCreateEvidence(Map<String, String> eviParam) {

        ClientResponse response = createDocument(eviParam);
        String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Evidence.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));        
    }

    /**
     * Try updating evidence metadata when the original access level is public.
     */
    private void tryUpdatingEvidenceMetadataWhenTheOriginalAccessLevelIsPublic(UrlInputDto urlInputTwo) {
        String VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL = "{  \"sourceMedia\": \"UpdateOne\" }";
        String EVI_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.updateEvidenceMetadataUrl(urlInputTwo);
        // System.out.println(EVI_UPDATE_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVI_UPDATE_WEBSCRIPT_URL);
        Builder b = null;
        Object o = null;
        if (TmnguiBaseTest.runAgainstCMSLayer) {
            b = webResource.type(MediaType.APPLICATION_JSON);
            o = VALUE_EVI_UPDATED_METADATA_ONE;
        } else {
            FormDataMultiPart multiPart = new FormDataMultiPart();
            multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL));
            b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
            o = multiPart;
            try {
                multiPart.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseTwo = b.post(ClientResponse.class, o);
        String haystackTwo = responseTwo.getEntity(String.class);
         //System.out.println("Alfresco Layer: " + haystackTwo);
        assertEquals(200, responseTwo.getStatus());
        String needleTwo = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral(haystackTwo, needleTwo));
    }    
    
 
}
