package alf.cabinet.tmcase.mark.image;

import static org.junit.Assert.assertEquals;

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

import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * A simple class demonstrating how to run out-of-container tests loading
 * Alfresco application context.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveMarkMetadataWithBadSerialNumberTests extends MarkBaseTest {

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
     * Test retrieve mark metadata with bad serial format1.
     */
    @Test
    public void testRetrieveMarkMetadataWithBadSerialFormat1() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT1.toString());
        urlInput.setFileName("MarkOne");
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);    
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        ClientResponse response = b.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, response.getStatus());
    }

    /**
     * Test retrieve mark metadata with bad serial format2.
     */
    @Test
    public void testRetrieveMarkMetadataWithBadSerialFormat2() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT2.toString());
        urlInput.setFileName("MarkOne");
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);         
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        ClientResponse response = b.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, response.getStatus());
    }  

    /**
     * Test retrieve mark metadata with bad serial format3.
     */
    @Test
    public void testRetrieveMarkMetadataWithBadSerialFormat3() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT3.toString());
        urlInput.setFileName("MarkOne");
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);        
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        ClientResponse response = b.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, response.getStatus());
    } 

    /**
     * Test retrieve mark metadata with bad serial format4.
     */
    @Test
    public void testRetrieveMarkMetadataWithBadSerialFormat4() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT4.toString());
        urlInput.setFileName("MarkOne");
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);           
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        ClientResponse response = b.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, response.getStatus());
    } 

    /**
     * Test retrieve mark metadata with bad serial format5.
     */
    @Test
    public void testRetrieveMarkMetadataWithBadSerialFormat5() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT5.toString());
        urlInput.setFileName("MarkOne");
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);         
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        ClientResponse response = b.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, response.getStatus());
    } 

    /**
     * Test retrieve mark metadata with bad serial format6.
     */
    @Test
    public void testRetrieveMarkMetadataWithBadSerialFormat6() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT6.toString());
        urlInput.setFileName("MarkOne");
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);          
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        ClientResponse response = b.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, response.getStatus());
    } 

    /**
     * Test retrieve mark metadata with bad serial format7.
     */
    @Test
    public void testRetrieveMarkMetadataWithBadSerialFormat7() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT7.toString());
        urlInput.setFileName("MarkOne");
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);         
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        ClientResponse response = b.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, response.getStatus());
    } 

    /**
     * Test retrieve mark metadata with bad serial format8.
     */
    @Test
    public void testRetrieveMarkMetadataWithBadSerialFormat8() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT8.toString());
        urlInput.setFileName("MarkOne");
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);        
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        ClientResponse response = b.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, response.getStatus());
    } 

    /**
     * Test retrieve mark metadata with bad serial format9.
     */
    @Test
    public void testRetrieveMarkMetadataWithBadSerialFormat9() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT9.toString());
        urlInput.setFileName("MarkOne");
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);        
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        ClientResponse response = b.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, response.getStatus());
    }     

}
