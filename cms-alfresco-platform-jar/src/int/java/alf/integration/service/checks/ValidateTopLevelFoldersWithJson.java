package alf.integration.service.checks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.HelperUtilUrl;

/**
 * The Class CheckTopLevelFolders.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class ValidateTopLevelFoldersWithJson extends HealthCheckBaseType {

    /**
     * Tear down.
     */
    @After
    public void tearDown() {

    }

    /**
     * Check the type of cabinet folder.
     */
    @Test
    public void bulkValidateTopLevelFolders() {
        UrlInputDto urlInput = new UrlInputDto();
        String BULK_VALIDATE_URL = HelperUtilUrl.bulkValidateTopLevelFolders(urlInput);  
        //System.out.println(BULK_VALIDATE_URL);
        WebResource webResource = client.resource(BULK_VALIDATE_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.header(API_SECURITY_KEY, VALUE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);        
        String metadataValue = "{   \"TopLevelFolders\": [{       \"name\": \"cabinet\",      \"type\": \"cm:folder\",        \"children\": [{          \"name\": \"case\",             \"type\": \"tm:cabinet\",           \"children\": []      }, {            \"name\": \"madridib\",             \"type\": \"tm:cabinet\",           \"children\": []      }, {            \"name\": \"petition\",             \"type\": \"tm:cabinet\",           \"children\": []      }, {            \"name\": \"publication\",          \"type\": \"tm:cabinet\",           \"children\": [{              \"name\": \"Official Gazette\",                 \"type\": \"tm:eogFolder\",                 \"children\": []          }, {                \"name\": \"ID Manual\",                \"type\": \"tm:idmFolder\",                 \"children\": []          }]      }, {            \"name\": \"legal-proceeding\",             \"type\": \"tm:cabinet\",           \"children\": []      }, {            \"name\": \"quality-review\",           \"type\": \"tm:cabinet\",           \"children\": []      }, {            \"name\": \"submissions\",          \"type\": \"tm:submissionFolder\",          \"children\": []      }]  }, {        \"name\": \"Document_Library\",         \"type\": \"cm:folder\",        \"children\": [{          \"name\": \"Evidence Bank\",            \"type\": \"cm:folder\",            \"children\": [{              \"name\": \"2A\",               \"type\": \"cm:folder\",                \"children\": []          }]      }]  }, {        \"name\": \"drive\",        \"type\": \"tm:folder\",        \"children\": [{          \"name\": \"eFile\",            \"type\": \"tm:efileFolder\",           \"children\": []      }, {            \"name\": \"eogTemplate\",          \"type\": \"cm:folder\",            \"children\": []      }, {            \"name\": \"tram\",             \"type\": \"tm:tramFolder\",            \"children\": []      }]  }] }";
        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), metadataValue));
        ClientResponse response = b.post(ClientResponse.class, multiPart);        
        String str = response.getEntity(String.class);
        //System.out.println(str);        
        String haystack = str;        
        assertEquals(200, response.getStatus());
        //check for presence of image mark
        String imgMark = "Official Gazette";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, imgMark));         
    }


    
    
}
