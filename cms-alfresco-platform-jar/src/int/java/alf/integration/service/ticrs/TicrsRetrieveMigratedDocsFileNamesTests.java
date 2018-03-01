package alf.integration.service.ticrs;

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
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.TestCaseConstants;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.TicrsRelatedUrl;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**

 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class TicrsRetrieveMigratedDocsFileNamesTests extends CentralBase {

    private static final String TICRS_MARK_MIGRATION_JPEG = "TicrsMarkMigration.jpeg";
    private static final String TICRS_SPECIMEN_MIGRATION_JPEG = "TicrsSpecimenMigration.jpeg";
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

    /**
     * Test retrieve summary metadata.
     */
    @Test
    public void retrieveTicrsFileNameFromCase() {
        //create sample ticrs files.
        createTicrsSpecimen();
        createTicrsMark();
        //retrieve ticrs file names from case.
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777806_TICRS.toString());
        String TICRS_RETRIEVE_CASE_FILENAME_WEBSCRIPT_URL = TicrsRelatedUrl.ticrsRetrieveCaseFileNames(urlInput);
        //System.out.println(TICRS_RETRIEVE_CASE_FILENAME_WEBSCRIPT_URL);
        WebResource webResource = client.resource(TICRS_RETRIEVE_CASE_FILENAME_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());        
        String haystack = str;
        String fileNameOne = TicrsRetrieveMigratedDocsFileNamesTests.TICRS_SPECIMEN_MIGRATION_JPEG;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, fileNameOne));      
        String fileNameTwo = TicrsRetrieveMigratedDocsFileNamesTests.TICRS_MARK_MIGRATION_JPEG;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, fileNameTwo));           
    }

    private void createTicrsSpecimen(){
        String TICRS_METADATA = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\", \"legacyCategory\": \"Migrated\", \"docCode\": \"MRK\", \"migrationMethod\":\"LZL\", \"migrationSource\":\"TICRS\" }";        
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SPECIMEN.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777806_TICRS.toString());
        urlInput.setFileName(TicrsRetrieveMigratedDocsFileNamesTests.TICRS_SPECIMEN_MIGRATION_JPEG);
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        //System.out.println(MARK_CREATE_WEBSCRIPT_URL);
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(), TICRS_METADATA);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TestCaseConstants.CREATE_CONTENT_ATTACHMENT);
        testCreateDocument(mrkParam, urlInput);
    }      
    
    private void createTicrsMark(){
        String TICRS_METADATA = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\", \"legacyCategory\": \"Migrated\", \"docCode\": \"MRK\", \"migrationMethod\":\"LZL\", \"migrationSource\":\"TICRS\" }";        
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777806_TICRS.toString());
        urlInput.setFileName(TicrsRetrieveMigratedDocsFileNamesTests.TICRS_MARK_MIGRATION_JPEG);
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(), TICRS_METADATA);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TestCaseConstants.CREATE_CONTENT_ATTACHMENT);
        testCreateDocument(mrkParam, urlInput);
    }  
    
    /**
     * Simulate image mark migration from legacy world create.
     *
     * @param mrkParam the mrk param
     * @param urlInput the url input
     */
    private void testCreateDocument(Map<String, String> mrkParam, UrlInputDto urlInput ){
        ClientResponse response = createDocument(mrkParam);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(201, response.getStatus());
    }       
    
}
