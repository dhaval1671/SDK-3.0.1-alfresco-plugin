package alf.cabinet.tmcase.mark.multimedia.video;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.TransformVideoMarkBaseTest;
import alf.integration.service.all.base.VideoMarkBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class ImageToMultimediaMarkMutationTest.
 *
 * @author stank
 */

public class multimediaUpdateToTxtMimeTypeTest extends VideoMarkBaseTest{

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        //System.out.println("setting up CreateMultimediaMarkTest.setup()");
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
     * Multimedia create and recreate test.
     */
    @Test
    public void firstCreateImageMarkThenTryToUpdateMultimediaMarkTest(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        String VALUE_MRK_FILE_NAME = "VideoMarkUpdateByTxtDisguisedAsWMV_MarkFlow4.wmv";
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        createVideoMark(urlInput);
        tryToUpdateVideoMarkWithImageMark(urlInput);
    }

    private void createVideoMark(UrlInputDto urlInput) {
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        String VALUE_MRK_METADATA_ONE = "{     \"accessLevel\": \"restricted\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";
        mrkParam.put(ParameterKeys.METADATA.toString(), VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TransformVideoMarkBaseTest.CONTENT_MM_TEETAR_VIDEO_SONG); 
        ClientResponse response = createDocument(mrkParam);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(201, response.getStatus());        
    }      

    private void tryToUpdateVideoMarkWithImageMark(UrlInputDto urlInput) {
        String MARK_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInput); 
        //System.out.println(MARK_UPDATE_WEBSCRIPT_URL);
        Map<String, String> mulMarkParam = new HashMap<String, String>();
                
        mulMarkParam.put(ParameterKeys.URL.toString(), MARK_UPDATE_WEBSCRIPT_URL);
        String VALUE_MRK_METADATA_ONE = "{     \"accessLevel\": \"public\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";
        mulMarkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        //mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        //mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), "src//test//resources//mark//image//2_Mark1.0.jpg");
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TransformVideoMarkBaseTest.CONTENT_MM_TXT_DISGUISED_AS_AVI);
        ClientResponse response = CentralBase.updateDocument(mulMarkParam);        
        //String haystack = response.getEntity(String.class);
        //System.out.println(haystack);             
        assertEquals(400, response.getStatus());        
       
    }    
 
}
