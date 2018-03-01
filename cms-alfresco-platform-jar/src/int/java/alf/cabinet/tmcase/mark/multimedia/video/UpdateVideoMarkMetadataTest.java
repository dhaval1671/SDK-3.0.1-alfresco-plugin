package alf.cabinet.tmcase.mark.multimedia.video;

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

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.GenericMarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.VideoMarkBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;

/**
 * @author stank
 *
 */

public class UpdateVideoMarkMetadataTest extends GenericMarkBaseTest{

    /** The log. */
    private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[TMConstants.ONE].getClassName());

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        log.info("### Executing " + Thread.currentThread().getStackTrace()[TMConstants.ONE].getMethodName() + " ####");
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
     * Mark update test.
     */
    @Test
    public void updateMultimediaMarkJustMetadataTest() {
        Map<String, String> multimediaMarkUpdateParam = new HashMap<String, String>();
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInputOne.setFileName("updateMultimediaMarkContentOneEight.wqe");
        createMultimediaMark(urlInputOne); 
        updateMultimediaMark(multimediaMarkUpdateParam, urlInputOne);  
    }      

	private void updateMultimediaMark(Map<String, String> multimediaMarkUpdateParam, UrlInputDto urlInputOne) {
		String MARK_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateMetadataUrl(urlInputOne); 
        multimediaMarkUpdateParam.put(ParameterKeys.URL.toString(), MARK_UPDATE_WEBSCRIPT_URL);
        
        String VALUE_MRK_METADATA_ONE = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";        
        multimediaMarkUpdateParam.put(ParameterKeys.METADATA.toString(), VALUE_MRK_METADATA_ONE);
        
        multimediaMarkUpdateParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),VideoMarkBaseTest.CONTENT_MMRK_AVI);
        ClientResponse response = CentralBase.updateDocument(multimediaMarkUpdateParam);    	
        String haystack = response.getEntity(String.class);
        //System.out.println(haystack);            	

        assertEquals(200, response.getStatus());

        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for 'mark' type in documentId url
        String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));

        //check for 'image' post fix in documentId url
        String docIdImagePostFix = "/"+ urlInputOne.getFileName() +"\"";//;
        assertTrue(containsStringLiteral( haystack, docIdImagePostFix));
        
        testRetrieveMmMarkMetadata(urlInputOne);
	}  
	
    private void testRetrieveMmMarkMetadata(UrlInputDto urlInput) {
    	urlInput.setAccessLevel("internal");
        String MM_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);        
        //System.out.println(MM_METADATA_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MM_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(200, response.getStatus());
        String haystack = str;
        String accessLevel = "\"accessLevel\":\"internal\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, accessLevel));       
    }	
    
	private void createMultimediaMark(UrlInputDto urlInput) {
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        // System.out.println(MARK_CREATE_WEBSCRIPT_URL);
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        String VALUE_MRK_METADATA_ONE = "{     \"accessLevel\": \"public\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";
        mrkParam.put(ParameterKeys.METADATA.toString(), VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), VideoMarkBaseTest.CONTENT_MMRK_3GP);

        ClientResponse response = createDocument(mrkParam);
        String str = response.getEntity(String.class);
        // System.out.println(str);

        assertEquals(201, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral(haystack, needle));

        // check for 'mark' type in documentId url
        String validDocumentID = "/" + MarkDoc.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));		
		
	}         

}
