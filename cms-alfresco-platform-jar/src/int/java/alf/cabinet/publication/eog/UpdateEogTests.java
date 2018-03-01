package alf.cabinet.publication.eog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import alf.integration.service.all.base.EogBaseTest;
import alf.integration.service.all.base.EogParameterValues;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.dtos.EogUrlInputDto;
import alf.integration.service.url.helpers.publication.PublicationCreateUrl;
import alf.integration.service.url.helpers.publication.PublicationUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkPublicationTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.officialgazette.Eog;

/**
 * A simple class demonstrating how to run out-of-container tests loading
 * Alfresco application context.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class UpdateEogTests extends EogBaseTest {

    /** The log. */
    private static Logger LOG = Logger.getLogger(UpdateEogTests.class);    
    
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
     * Note create and recreate test.
     */
    @Test
    public void eogCreateAndUpdateTest() {
        Map<String, String> eogParam = new HashMap<String, String>();
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_20150609.toString());
        urlInput.setFileName(UPDATE_EOG_FILE_NAME);
        //Create document.
        String EOG_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createEogUrl(urlInput);
        //System.out.println(EOG_CREATE_WEBSCRIPT_URL);
        eogParam.put(ParameterKeys.URL.toString(), EOG_CREATE_WEBSCRIPT_URL);
        eogParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EogBaseTest.CREATE_CONTENT_EOG_ATTACHMENT);
        ClientResponse response = createDocument(eogParam);
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\"";
        assertFalse(containsStringLiteral(haystack, needle));
        // check for valid docuemnt ID
        String validDocumentID = "/publication/(.*?)/" + Eog.TYPE + "/";// ;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, validDocumentID));
        
        //Update EOG
        String EVI_UPDATE_WEBSCRIPT_URL = PublicationUpdateUrl.updateEogUrl(urlInput);
        // System.out.println(EVI_UPDATE_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVI_UPDATE_WEBSCRIPT_URL);
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(UPDATE_CONTENT_EOG_ATTACHMENT);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseTwo = b.post(ClientResponse.class, multiPart);
        String haystackTwo = responseTwo.getEntity(String.class);
        //System.out.println("Alfresco Layer: " + haystack);
        assertEquals(200, responseTwo.getStatus());
        // check for valid docuemnt ID
        String validDocumentIDTwo = "/publication/(.*?)/" + Eog.TYPE + "/";// ;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, validDocumentIDTwo));        
        String needleTwo = "\"version\"";
        assertFalse(containsStringLiteral(haystackTwo, needleTwo));  
    }

  

}
