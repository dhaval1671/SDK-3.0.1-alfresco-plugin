package alf.integration.service.all.base;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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

import alf.integration.service.base.TmnguiBaseTest;

/**
 * The Class CentralBase.
 */
public class CentralBase extends TmnguiBaseTest{

    /** The Constant urlPrefixCmsRestCase. */
    public static final String urlPrefixCmsRestCase = "/cms/rest/case/";

    /** The Constant urlPrefixCmsRestV1Case. */
    public static final String urlPrefixCmsRestV1Case = "/cms/rest/v1/case/";
    
    public static final String urlPrefixCmsRestV1AdminCase = "/cms/rest/v1/admin/case/";
    
    /** The Constant urlPrefixCmsRestPublication. */
    public static final String urlPrefixCmsRestPublication = "/cms/rest/publication/";
    
    public static final String urlPrefixCmsRestMadridIB = "/cms/rest/madridib/";
    
    public static final String urlPrefixLegalProceeding = "/cms/rest/legal-proceeding/";

    /** The log. */
    private static Logger LOG = Logger.getLogger(CentralBase.class);

    /** The client. */
    protected static Client client = null;

    /**  
     * @Title: getUrlPrefix
     * @Description:   
     * @return  
     * @return String   
     * @throws  
     */
    protected static String getUrlPrefix() {
        String urlPrefix = "";
        if(!TmnguiBaseTest.runAgainstCMSLayer){
            urlPrefix = ALFRESCO_URL +  urlPrefixCmsRestV1Case; 
        }else{
            urlPrefix = ALFRESCO_URL +  urlPrefixCmsRestCase; 
        }
        return urlPrefix;
    }

    /**
     * Creates the doc type new url format.
     *
     * @param efileParamMap the efile param map
     * @return the client response
     */
    protected static ClientResponse createDocument(Map<String, String> efileParamMap) {

        String urlValue = efileParamMap.get(ParameterKeys.URL.toString());
        //System.out.println(urlValue);
        String metadataValue = efileParamMap.get(ParameterKeys.METADATA.toString());    
        String contentAttachmentValue = efileParamMap.get(ParameterKeys.CONTENT_ATTACHEMENT.toString());        

        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        if(!TmnguiBaseTest.runAgainstCMSLayer){
            client.addFilter(new HTTPBasicAuthFilter(ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD));    
        }

        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(contentAttachmentValue);
        } catch (FileNotFoundException e) {
            if(LOG.isInfoEnabled()){
                LOG.info(e.getMessage(), e);
            }           
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        if(StringUtils.isNotBlank(metadataValue)){
            multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), metadataValue));
        }
        WebResource webResource = client.resource(urlValue);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.put(ClientResponse.class, multiPart);
        return response;

    }

    /**
     * Creates the or update redacted response.
     *
     * @param efileParamMap
     *            the efile param map
     * @return the client response
     */
    protected static ClientResponse updateDocument(Map<String, String> efileParamMap) {
    
        String urlValue = efileParamMap.get(ParameterKeys.URL.toString());
        String metadataValue = efileParamMap.get(ParameterKeys.METADATA.toString());
        String contentAttachmentValue = efileParamMap.get(ParameterKeys.CONTENT_ATTACHEMENT.toString());
    
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        if (!TmnguiBaseTest.runAgainstCMSLayer || TmnguiBaseTest.restApiNeedsAuth) {
            client.addFilter(new HTTPBasicAuthFilter(ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD));
        }
    
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString())
                .fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        if(null != contentAttachmentValue){
            FileInputStream content = null;
            try {
                content = new FileInputStream(contentAttachmentValue);
            } catch (FileNotFoundException e) {
                if (LOG.isInfoEnabled()) {
                    LOG.info(e.getMessage(), e);
                }
            }
            multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        }
        if(null != metadataValue){
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), metadataValue));
        }
        WebResource webResource = client.resource(urlValue);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        return response;
    
    }
    
    public void verifyIfRetriveContentIsAccurate(Map<String, String> presummedSizeOfTheFileOnDifferentServerEnvs, String fileSizeFrResponse) {
        if(HOST_SERVER.equalsIgnoreCase("localhost:8080")){
            String fileSize_4_1_9 = presummedSizeOfTheFileOnDifferentServerEnvs.get("4_1_9_" + HOST_SERVER);
            String fileSize_5_1 = presummedSizeOfTheFileOnDifferentServerEnvs.get("5_1_" + HOST_SERVER);
            if(fileSizeFrResponse.equals(fileSize_4_1_9)){
                assertEquals(fileSize_4_1_9, fileSizeFrResponse);
            }
            else if(fileSizeFrResponse.equals(fileSize_5_1)){
                assertEquals(fileSize_5_1, fileSizeFrResponse);
            }
            else{
                //fail the testcase.
                String expectedContentSizeForTheGivenEnvironment = presummedSizeOfTheFileOnDifferentServerEnvs.get(HOST_SERVER);
                assertEquals(expectedContentSizeForTheGivenEnvironment, fileSizeFrResponse);  
            }
        }else{
            String expectedContentSizeForTheGivenEnvironment = presummedSizeOfTheFileOnDifferentServerEnvs.get(HOST_SERVER);
            assertEquals(expectedContentSizeForTheGivenEnvironment, fileSizeFrResponse);            
        }
    } 
    
    

 

}
