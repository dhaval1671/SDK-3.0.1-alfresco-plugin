package alf.sporadic.bulk.operation.evidence.DE14915;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;

/**
 * The Class BulkMetadataUpdateEvidenceTest.
 *
 * @author stank
 */

public class A1_03_a_ImplBulkEvidenceUpdate extends EvidenceBaseTest implements Runnable{

    private String metadata;

    public A1_03_a_ImplBulkEvidenceUpdate(String metadataOne, int i2) {
        this.metadata = metadataOne;
    }


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

    @Override
    public void run() {
        
        //String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {         \"accessLevel\": \"public\",     \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"usingNewBulk\",             \"sourceMedia\": \"paper\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceCategory\": \"web\"         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/another-proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"sourceMedium\": \"usingNewBulk\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceGroupNames\": [                 \"working\",                 \"suggested\"             ]         }     } ]";
        //metadata = "[{\"documentId\":\"/case/77777777/evidence/Evidence_0.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_0.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-2junk:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_1.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_1.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_2.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_2.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_3.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_3.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_4.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_4.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_5.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_5.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_6.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_6.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_7.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_7.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_8.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_8.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}},{\"documentId\":\"/case/77777777/evidence/Evidence_9.pdf\",\"metadata\":{\"documentAlias\":\"documentAliasEvidence_9.pdf\",\"modifiedByUserId\":\"User XYZ\",\"accessLevel\":\"public\",\"migrationMethod\":\"migrationMethod\",\"migrationSource\":\"migrationSource\",\"loadDate\":\"2014-04-23T13:42:24.962-0400\",\"evidenceCategory\":\"evidenceCat\",\"multimediaProps\":{},\"documentType\":\"evidence\"}}]\"";
        String metadata = this.metadata;
        
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        String WEBSCRIPT_URL = CaseOtherUrl.bulkMetadataUpdateToEvidenceType(urlInput);  
        System.out.println(WEBSCRIPT_URL);
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        System.out.println("### metadata being submitted: " + metadata);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());
        //String haystack = str;
        //String needle = "\"version\":\"1.1\"";
        //assertTrue(containsStringLiteral( haystack, needle));              
    }


}
