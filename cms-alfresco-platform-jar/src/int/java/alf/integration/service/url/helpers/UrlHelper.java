package alf.integration.service.url.helpers;

import org.apache.commons.lang.StringUtils;

/**
 * The Class UrlHelper.
 */
public class UrlHelper {

    /**
     * The main method.
     *
     * @param arg0 the arguments
     */
    public static void main(String[] arg0){

    }

    /**
     * Point to alfresco servers instead of cms.
     *
     * @param alfrescoContextUrl the alfresco context url
     * @param ipContext the ip context
     * @param backEndAlfHost the back end alf host
     * @return the string
     */
    public static String pointToAlfrescoUrlInsteadOfCmsUrl(String alfrescoContextUrl, String ipContext, String backEndAlfHost) {
        String alfUrl = null;

        String hostOne = StringUtils.remove(alfrescoContextUrl, "http://");
        String hostTwo = StringUtils.remove(hostOne, "/trademark");
        String hostFinal = StringUtils.remove(hostTwo, "/alfresco/s");
        String s = UrlHelper.pointToAlfrescoHostInsteadOfCms(hostFinal, ipContext, backEndAlfHost);
        
        alfUrl = "http://" + s + "/alfresco/s";
/*        if(StringUtils.isBlank(ipContext)){
            boolean devCmsUrl = "http://dev-tm-wbs-1.etc.uspto.gov:8080/trademark".equals(alfrescoContextUrl);
            boolean sitCmsCulsterUrl = "http://sit-tmng-cms.etc.uspto.gov/trademark".equals(alfrescoContextUrl);
            boolean sitCmsCulsterNodeOneUrl = "http://sit-tm-wbs-1.etc.uspto.gov:8080/trademark".equals(alfrescoContextUrl);
            boolean sitCmsCulsterNodeTwoUrl = "http://sit-tm-wbs-2.etc.uspto.gov:8080/trademark".equals(alfrescoContextUrl);
            boolean fqtCmsUrl = "http://fqt-tmng-cms.etc.uspto.gov".equals(alfrescoContextUrl);
    
            if (devCmsUrl) {
                alfUrl = "http://dev-tm-alf-1.etc.uspto.gov:8080/alfresco/s";
            } else if (sitCmsCulsterUrl) {
                alfUrl = "http://sit-tmng-alf.etc.uspto.gov/alfresco/s";
            } else if (sitCmsCulsterNodeOneUrl) {
                alfUrl = "http://sit-tm-alf-1.etc.uspto.gov:8080/alfresco/s";
            } else if (sitCmsCulsterNodeTwoUrl) {
                alfUrl = "http://sit-tm-alf-2.etc.uspto.gov:8080/alfresco/s";
            } else if (fqtCmsUrl) {
                alfUrl = "http://fqt-tmng-alf.etc.uspto.gov/alfresco/s";
            } else {
                alfUrl = alfrescoContextUrl;
            }
        }else if("cmsLayer".equalsIgnoreCase(ipContext)){
            alfUrl = "http://" + backEndAlfHost + "/alfresco/s";
        }*/
        
        
        return alfUrl;
    }  

    /**
     * Point to alfresco host instead of cms.
     *
     * @param host the host
     * @param ipContext the ip context
     * @param backEndAlfHost the back end alf host
     * @return the string
     */
    public static String pointToAlfrescoHostInsteadOfCms(String host, String ipContext, String backEndAlfHost) {
        String alfHost = null;
        
        if(StringUtils.isBlank(ipContext) || "alfrescoLayer".equalsIgnoreCase(ipContext)){
            boolean devCmsHost = "dev-tm-wbs-1.etc.uspto.gov:8080".equals(host);
            boolean sitCmsCulsterHost = "sit-tmng-cms.etc.uspto.gov".equals(host);
            boolean sitCmsCulsterNodeOneHost = "sit-tm-wbs-1.etc.uspto.gov:8080".equals(host);
            boolean sitCmsCulsterNodeTwoHost = "sit-tm-wbs-2.etc.uspto.gov:8080".equals(host);
            boolean fqtCmsHost = "fqt-tmng-cms.etc.uspto.gov".equals(host);
            boolean demoflag = "demo-tmng-alf-1.dev.uspto.gov:8080".equals(host);
            if (devCmsHost) {
                alfHost = "dev-tm-alf-1.etc.uspto.gov:8080";
            } else if (sitCmsCulsterHost) {
                alfHost = "sit-tmng-alf.etc.uspto.gov";
            } else if (sitCmsCulsterNodeOneHost) {
                alfHost = "sit-tm-alf-1.etc.uspto.gov:8080";
            } else if (sitCmsCulsterNodeTwoHost) {
                alfHost = "sit-tm-alf-2.etc.uspto.gov:8080";
            } else if (fqtCmsHost) {
                alfHost = "fqt-tmng-alf.etc.uspto.gov";
            }else if (demoflag) {
                alfHost = "demo-tmng-alf-1.dev.uspto.gov:8080";
            } else {
                alfHost = host;
            }
        }else if("cmsLayer".equalsIgnoreCase(ipContext)){
                alfHost = backEndAlfHost;
        }
        
        return alfHost;
    }     

}
