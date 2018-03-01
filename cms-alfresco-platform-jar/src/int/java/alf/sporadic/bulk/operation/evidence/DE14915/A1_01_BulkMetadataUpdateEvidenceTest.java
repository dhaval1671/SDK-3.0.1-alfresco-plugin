package alf.sporadic.bulk.operation.evidence.DE14915;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.aspects.ACLAspect;
import gov.uspto.trademark.cms.repo.model.aspects.ApplicationDates;
import gov.uspto.trademark.cms.repo.model.aspects.EvidenceAspect;
import gov.uspto.trademark.cms.repo.model.aspects.MigratedAspect;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;
import gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest;

/**
 * The Class BulkMetadataUpdateEvidenceTest.
 *
 * @author stank
 */

public class A1_01_BulkMetadataUpdateEvidenceTest extends EvidenceBaseTest {

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
     * Apply bulk metadata to evi happy path.
     */
    @Test
    public void applyBulkMetadataToEviHappyPath(){
        
        int arraySize = 10;
        CopyEvidenceRequest[] copyEvidenceRequests = new CopyEvidenceRequest[arraySize];
        // create large number of evidences.
        for (int i = 0; i < arraySize; i++) {
            String serialNumber = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
            String fileName = "Evidence_" + i + ".pdf";
            Evidence eviMetadata = new Evidence();
            ACLAspect aclAsp = new ACLAspect();
            aclAsp.setAccessLevel("public");
            eviMetadata.setaCLAspect(aclAsp);
            eviMetadata.setDocumentAlias("documentAlias" + fileName);
            eviMetadata.setModifiedByUserId("User XYZ");
            EvidenceAspect ea = new EvidenceAspect();
            ea.setEvidenceCategory("evidenceCat");
            eviMetadata.setEvidenceAspect(ea);
            MigratedAspect migratedAspect = new MigratedAspect();
            migratedAspect.setMigrationMethod("migrationMethod");
            migratedAspect.setMigrationSource("migrationSource");
            eviMetadata.setMigratedAspect(migratedAspect);
            ApplicationDates ad = new ApplicationDates();

            
            if(i == 600){
                String startDateString = "06/TwentySeven/2007";
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
                Date startDate = null;
                try {
                    startDate = df.parse(startDateString);
                    String d = df.format(startDate);
                    System.out.println(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }                
                ad.setLoadDate(startDate);
            }else{
                String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
                String newstr = "2014-04-23T13:42:24.962-04:00";        
                DateTimeFormatter formatter = DateTimeFormat.forPattern(DATETIME_FORMAT);
                DateTime dt = formatter.parseDateTime(newstr);        
                Calendar c = Calendar.getInstance();
                c.setTime(dt.toDate());
                Date d = c.getTime();
                //System.out.println(new DateTime(d).toString(DATETIME_FORMAT));                 
                ad.setLoadDate(d);
            }
            eviMetadata.setApplicationDates(ad);
            copyEvidenceRequests[i] = new CopyEvidenceRequest();
            copyEvidenceRequests[i].setDocumentId("/case/" + serialNumber + "/evidence/" + fileName);
            copyEvidenceRequests[i].setMetadata(eviMetadata);
            
            //create evidence file in alfresco
            testCreateEvidence(fileName, serialNumber);
        }

        testBulkMetadataUpdateToEvisHappyPath(copyEvidenceRequests);

    }

    /**
     * Test bulk metadata update to evis happy path.
     * @param copyEvidenceRequests 
     */
    private void testBulkMetadataUpdateToEvisHappyPath(CopyEvidenceRequest[] copyEvidenceRequests) {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(TMConstants.DATETIME_FORMAT));
        mapper.setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);    
        String metadata = "";
        try {
            metadata = mapper.writeValueAsString(copyEvidenceRequests);
            System.out.println(metadata);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }         
        
        //String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {         \"accessLevel\": \"public\",     \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"usingNewBulk\",             \"sourceMedia\": \"paper\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceCategory\": \"web\"         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/another-proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"sourceMedium\": \"usingNewBulk\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceGroupNames\": [                 \"working\",                 \"suggested\"             ]         }     } ]";
        //metadata = "[{\"documentId\":\"/case/77777777/evidence/Evidence_0.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_0.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-2junk:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_1.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_1.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_2.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_2.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_3.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_3.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_4.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_4.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_5.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_5.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_6.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_6.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_7.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_7.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_8.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_8.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_9.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_9.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}}]\"";
        
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        String WEBSCRIPT_URL = CaseOtherUrl.bulkMetadataUpdateToEvidenceType(urlInput);  
        System.out.println(WEBSCRIPT_URL);
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));        
    }

 
    /**
     * Test create evidence.
     *
     * @param VALUE_FILE_NAME the value file name
     * @param VALUE_SERIAL_NUMBER the value serial number
     */
    private void testCreateEvidence(String VALUE_FILE_NAME, String VALUE_SERIAL_NUMBER) {
        Map<String, String> eviParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(VALUE_SERIAL_NUMBER);
        urlInput.setFileName(VALUE_FILE_NAME);
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), EvidenceBaseTest.VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);        
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
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


}
