package alf.integration.service.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import alf.integration.service.environments.DevAlfEnvironment;
import alf.integration.service.environments.FqtAlfEnvironment;
import alf.integration.service.environments.LocalhostAlfEnvironment;
import alf.integration.service.environments.PvtAlfEnvironment;
import alf.integration.service.environments.SitAlfEnvironment;
import alf.integration.service.url.helpers.UrlHelper;

/**
 * The Class TmnguiBaseTest.
 */
public class TmnguiBaseTest {

    /** The Constant ALFRESCO_HOST_KEY_TWO. */
    private static final String ALFRESCO_HOST_KEY_TWO = "admin";
    
    /** The Constant ALFRESCO_HOST_KEY. */
    private static final String ALFRESCO_HOST_KEY = "TmngUspto20!5_02";
    /*
     * @Rule public Timeout globalTimeout = new Timeout(10000); // 5 seconds max
     * per method tested
     */
    /** The Constant HOST_SERVER. */
    public static final String HOST_SERVER = ResourceManager.getProperty("host.server");
    
    /** The Constant CODE_LAYER_ON_IP_ADDRESS. */
    public static final String CODE_LAYER_ON_IP_ADDRESS = ResourceManager.getProperty("codeLayerOnIpAddress");
    
    /** The Constant BACKEND_ALFRESCO_HOST. */
    public static final String BACKEND_ALFRESCO_HOST = ResourceManager.getProperty("backend.alfresco.host");
    
    /** The alfresco url. */
    public static String ALFRESCO_URL = initializeUrl();
    
    /** The Constant ALF_ADMIN_LOGIN_USER_ID. */
    public static final String ALF_ADMIN_LOGIN_USER_ID = figureOutUserIdFromHostServer(HOST_SERVER, CODE_LAYER_ON_IP_ADDRESS, BACKEND_ALFRESCO_HOST);
    
    /** The Constant ALF_ADMIN_LOGIN_PWD. */
    public static final String ALF_ADMIN_LOGIN_PWD = figureOutPasswordFromHostServer(HOST_SERVER, CODE_LAYER_ON_IP_ADDRESS, BACKEND_ALFRESCO_HOST);
    
    /** The run against cms layer. */
    public static boolean runAgainstCMSLayer = !(hostIsAlfresco(null));
    
    /** The rest api needs auth. */
    public static boolean restApiNeedsAuth;

    /**
     * Initialize url.
     *
     * @return the string
     */
    final static String initializeUrl(){
        String lclUrl = null;
        if(hostIsAlfresco(CODE_LAYER_ON_IP_ADDRESS)){
            lclUrl = ResourceManager.getProperty("alfresco.url");
        }else{
            lclUrl = ResourceManager.getProperty("cms.url");
        }
        return lclUrl;
    }

    private static String figureOutUserIdFromHostServer(String hostServer, String ipContext, String backEndAlfHost) {
        if(!hostIsAlfresco(null)){
            hostServer = UrlHelper.pointToAlfrescoHostInsteadOfCms(HOST_SERVER, CODE_LAYER_ON_IP_ADDRESS, BACKEND_ALFRESCO_HOST);
        }
        String hostAlfrescoPassword = null;
        
        if(StringUtils.isBlank(ipContext) || "alfrescoLayer".equalsIgnoreCase(ipContext)){
            boolean localhost_5_1_flag = "localhost:8080".equals(hostServer);
            boolean dev_4_1_9_flag = "dev-tm-alf-1.etc.uspto.gov:8080".equals(hostServer);
            boolean sit_4_1_9_flag = "sit-tmng-alf.etc.uspto.gov".equals(hostServer);
            boolean sit_4_1_9_NodeOneFlag = "sit-tm-alf-1.etc.uspto.gov:8080".equals(hostServer);
            boolean sit_4_1_9_NodeTwoFlag = "sit-tm-alf-2.etc.uspto.gov:8080".equals(hostServer);
            boolean fqt_4_1_9_flag = "fqt-tmng-alf.etc.uspto.gov".equals(hostServer);
            boolean fqt_4_1_9_NodeOneflag = "fqt-tm-alf-1.etc.uspto.gov:8080".equals(hostServer);
            boolean fqt_4_1_9_NodeTwoflag = "fqt-tm-alf-2.etc.uspto.gov:8080".equals(hostServer);            
            boolean pvt_4_1_9_flag = "pvt-tmng-alf.etc.uspto.gov".equals(hostServer);
            boolean pvt_4_1_9_NodeOneflag = "pvt-tm-alf-1.etc.uspto.gov:8080".equals(hostServer);
            boolean pvt_4_1_9_NodeTwoflag = "pvt-tm-alf-2.etc.uspto.gov:8080".equals(hostServer);
            boolean demoflag = "demo-tmng-alf-1.dev.uspto.gov:8080".equals(HOST_SERVER);
            boolean dev_5_1_flag = "tmng-alf-1.dev.uspto.gov:8080".equals(hostServer);
            boolean sit_5_1_VIP_flag = "tmng-alfupgrade.sit.uspto.gov".equals(hostServer);
            boolean sit_5_1_7_flag = "tm-alf-7.sit.uspto.gov:8080".equals(hostServer);
            boolean sit_5_1_8_flag = "tm-alf-8.sit.uspto.gov:8080".equals(hostServer);
            boolean fqt_5_1_VIP_flag = "tmng-alfupgrade.fqt.uspto.gov".equals(hostServer);
            boolean fqt_5_1_1_flag = "tmng-alf-1.fqt.uspto.gov:8080".equals(hostServer);
            boolean fqt_5_1_2_flag = "tmng-alf-2.fqt.uspto.gov:8080".equals(hostServer);
            boolean pvt_5_1_VIP_flag = "tmng-alfupgrade.pvt.uspto.gov".equals(hostServer);
            boolean pvt_5_1_1_flag = "tmng-alf-3.pvt.uspto.gov:8080".equals(hostServer);
            boolean pvt_5_1_2_flag = "tmng-alf-4.pvt.uspto.gov:8080".equals(hostServer);
            
            if (       dev_4_1_9_flag 
                    || sit_4_1_9_flag || sit_4_1_9_NodeOneFlag || sit_4_1_9_NodeTwoFlag 
                    || fqt_4_1_9_flag || fqt_4_1_9_NodeOneflag || fqt_4_1_9_NodeTwoflag
                    || pvt_4_1_9_flag || pvt_4_1_9_NodeOneflag || pvt_4_1_9_NodeTwoflag 
                    || demoflag 
               ) {
                hostAlfrescoPassword = ALFRESCO_HOST_KEY;
            } else if( localhost_5_1_flag ){
                hostAlfrescoPassword = getLocalhostAlfUserId();
            } else if( dev_5_1_flag ){
                hostAlfrescoPassword = getDevAlfUserId();
            } else if( sit_5_1_VIP_flag || sit_5_1_7_flag || sit_5_1_8_flag ){
                hostAlfrescoPassword = getSitAlfUserId();
            } else if( fqt_5_1_VIP_flag || fqt_5_1_1_flag || fqt_5_1_2_flag ){
                hostAlfrescoPassword = getFqtAlfUserId();
            } else if( pvt_5_1_VIP_flag || pvt_5_1_1_flag || pvt_5_1_2_flag ){
                hostAlfrescoPassword = getPvtAlfUserId();
            } else{
                hostAlfrescoPassword = ALFRESCO_HOST_KEY_TWO;
            }
            
        }else if("cmsLayer".equalsIgnoreCase(ipContext)){
            boolean localhost = "localhost:8080".equals(backEndAlfHost);
            boolean devflag = "dev-tm-alf-1.etc.uspto.gov:8080".equals(backEndAlfHost);
            boolean sitCmsCulsterflag = "sit-tmng-alf.etc.uspto.gov".equals(backEndAlfHost);
            boolean sitCmsNodeOneFlag = "sit-tm-alf-1.etc.uspto.gov:8080".equals(backEndAlfHost);
            boolean sitCmsNodeTwoFlag = "sit-tm-alf-2.etc.uspto.gov:8080".equals(backEndAlfHost);
            boolean fqtflag = "fqt-tmng-alf.etc.uspto.gov".equals(backEndAlfHost);
            if ( devflag || sitCmsCulsterflag || sitCmsNodeOneFlag || sitCmsNodeTwoFlag || fqtflag) {
                hostAlfrescoPassword = ALFRESCO_HOST_KEY;
            } else if( localhost ){
                hostAlfrescoPassword = ALFRESCO_HOST_KEY_TWO;
            }
        }
        
        return hostAlfrescoPassword;
    }

    private static String getLocalhostAlfUserId() {
        LocalhostAlfEnvironment lae = new LocalhostAlfEnvironment();
        return lae.getAlfrescoShareUIUid();
    }

    private static String getDevAlfUserId() {
        DevAlfEnvironment dae = new DevAlfEnvironment();
        return dae.getAlfrescoShareUIUid();
    }

    private static String getSitAlfUserId() {
        SitAlfEnvironment sae = new SitAlfEnvironment();
        return sae.getAlfrescoShareUIUid();
    }

    private static String getFqtAlfUserId() {
        FqtAlfEnvironment fae = new FqtAlfEnvironment();
        return fae.getAlfrescoShareUIUid();
    }

    private static String getPvtAlfUserId() {
        PvtAlfEnvironment pae = new PvtAlfEnvironment();
        return pae.getAlfrescoShareUIUid();
    }

    /**
     * Figure out password from host server.
     *
     * @param hostServer the host server
     * @param ipContext the ip context
     * @param backEndAlfHost the back end alf host
     * @return the string
     */
    public static String figureOutPasswordFromHostServer(String hostServer, String ipContext, String backEndAlfHost) {
        
        if(!hostIsAlfresco(null)){
            hostServer = UrlHelper.pointToAlfrescoHostInsteadOfCms(HOST_SERVER, CODE_LAYER_ON_IP_ADDRESS, BACKEND_ALFRESCO_HOST);
        }
        String hostAlfrescoPassword = null;
        
        if(StringUtils.isBlank(ipContext) || "alfrescoLayer".equalsIgnoreCase(ipContext)){
            boolean localhost_5_1_flag = "localhost:8080".equals(hostServer);
            boolean dev_4_1_9_flag = "dev-tm-alf-1.etc.uspto.gov:8080".equals(hostServer);
            boolean sit_4_1_9_flag = "sit-tmng-alf.etc.uspto.gov".equals(hostServer);
            boolean sit_4_1_9_NodeOneFlag = "sit-tm-alf-1.etc.uspto.gov:8080".equals(hostServer);
            boolean sit_4_1_9_NodeTwoFlag = "sit-tm-alf-2.etc.uspto.gov:8080".equals(hostServer);
            boolean fqt_4_1_9_flag = "fqt-tmng-alf.etc.uspto.gov".equals(hostServer);
            boolean fqt_4_1_9_NodeOneflag = "fqt-tm-alf-1.etc.uspto.gov:8080".equals(hostServer);
            boolean fqt_4_1_9_NodeTwoflag = "fqt-tm-alf-2.etc.uspto.gov:8080".equals(hostServer);
            boolean pvt_4_1_9_flag = "pvt-tmng-alf.etc.uspto.gov".equals(hostServer);
            boolean pvt_4_1_9_NodeOneflag = "pvt-tm-alf-1.etc.uspto.gov:8080".equals(hostServer);
            boolean pvt_4_1_9_NodeTwoflag = "pvt-tm-alf-2.etc.uspto.gov:8080".equals(hostServer);
            boolean demoflag = "demo-tmng-alf-1.dev.uspto.gov:8080".equals(HOST_SERVER);
            boolean dev_5_1_flag = "tmng-alf-1.dev.uspto.gov:8080".equals(hostServer);
            boolean sit_5_1_VIP_flag = "tmng-alfupgrade.sit.uspto.gov".equals(hostServer);
            boolean sit_5_1_7_flag = "tm-alf-7.sit.uspto.gov:8080".equals(hostServer);
            boolean sit_5_1_8_flag = "tm-alf-8.sit.uspto.gov:8080".equals(hostServer);
            boolean fqt_5_1_VIP_flag = "tmng-alfupgrade.fqt.uspto.gov".equals(hostServer);
            boolean fqt_5_1_1_flag = "tmng-alf-1.fqt.uspto.gov:8080".equals(hostServer);
            boolean fqt_5_1_2_flag = "tmng-alf-2.fqt.uspto.gov:8080".equals(hostServer);
            boolean pvt_5_1_VIP_flag = "tmng-alfupgrade.pvt.uspto.gov".equals(hostServer);
            boolean pvt_5_1_1_flag = "tmng-alf-3.pvt.uspto.gov:8080".equals(hostServer);
            boolean pvt_5_1_2_flag = "tmng-alf-4.pvt.uspto.gov:8080".equals(hostServer);    
            
            if (       dev_4_1_9_flag 
                    || sit_4_1_9_flag || sit_4_1_9_NodeOneFlag || sit_4_1_9_NodeTwoFlag 
                    || fqt_4_1_9_flag || fqt_4_1_9_NodeOneflag || fqt_4_1_9_NodeTwoflag
                    || pvt_4_1_9_flag || pvt_4_1_9_NodeOneflag || pvt_4_1_9_NodeTwoflag 
                    || demoflag 
               ) {
                hostAlfrescoPassword = ALFRESCO_HOST_KEY;
            } else if( localhost_5_1_flag ){
                hostAlfrescoPassword = getLocalhostAlfPassword();
            } else if( dev_5_1_flag ){
                hostAlfrescoPassword = getDevAlfPassword();
            } else if( sit_5_1_VIP_flag || sit_5_1_7_flag || sit_5_1_8_flag ){
                hostAlfrescoPassword = getSitAlfPassword();
            } else if( fqt_5_1_VIP_flag || fqt_5_1_1_flag || fqt_5_1_2_flag ){
                hostAlfrescoPassword = getFqtAlfPassword();
            } else if( pvt_5_1_VIP_flag || pvt_5_1_1_flag || pvt_5_1_2_flag ){
                hostAlfrescoPassword = getPvtAlfPassword();
            } else{
                hostAlfrescoPassword = ALFRESCO_HOST_KEY_TWO;
            }
            
        }else if("cmsLayer".equalsIgnoreCase(ipContext)){
            boolean localhost = "localhost:8080".equals(backEndAlfHost);
            boolean devflag = "dev-tm-alf-1.etc.uspto.gov:8080".equals(backEndAlfHost);
            boolean sitCmsCulsterflag = "sit-tmng-alf.etc.uspto.gov".equals(backEndAlfHost);
            boolean sitCmsNodeOneFlag = "sit-tm-alf-1.etc.uspto.gov:8080".equals(backEndAlfHost);
            boolean sitCmsNodeTwoFlag = "sit-tm-alf-2.etc.uspto.gov:8080".equals(backEndAlfHost);
            boolean fqtflag = "fqt-tmng-alf.etc.uspto.gov".equals(backEndAlfHost);
            if ( devflag || sitCmsCulsterflag || sitCmsNodeOneFlag || sitCmsNodeTwoFlag || fqtflag) {
                hostAlfrescoPassword = ALFRESCO_HOST_KEY;
            } else if( localhost ){
                hostAlfrescoPassword = ALFRESCO_HOST_KEY_TWO;
            }
        }
        
        return hostAlfrescoPassword;
    }

    private static String getLocalhostAlfPassword() {
        LocalhostAlfEnvironment lae = new LocalhostAlfEnvironment();
        return lae.getAlfrescoShareUIPd();
    }

    private static String getDevAlfPassword() {
        DevAlfEnvironment dae = new DevAlfEnvironment();
        return dae.getAlfrescoShareUIPd();
    }

    private static String getSitAlfPassword() {
        SitAlfEnvironment sae = new SitAlfEnvironment();
        return sae.getAlfrescoShareUIPd();
    }

    private static String getFqtAlfPassword() {
        FqtAlfEnvironment fae = new FqtAlfEnvironment();
        return fae.getAlfrescoShareUIPd();
    }

    private static String getPvtAlfPassword() {
        PvtAlfEnvironment pae = new PvtAlfEnvironment();
        return pae.getAlfrescoShareUIPd();
    }

    /**
     * Host is alfresco.
     * @param ipContext TODO
     *
     * @return true, if successful
     */
    private static boolean hostIsAlfresco(String ipContext) {
        boolean hostIsAlfresco = false;
        if(StringUtils.isBlank(ipContext)){
            boolean devflag = "dev-tm-wbs-1.etc.uspto.gov:8080".equals(HOST_SERVER);
            boolean sitCmsCulsterflag = "sit-tmng-cms.etc.uspto.gov".equals(HOST_SERVER);
            boolean sitCmsNodeOneFlag = "sit-tm-wbs-1.etc.uspto.gov:8080".equals(HOST_SERVER);
            boolean sitCmsNodeTwoFlag = "sit-tm-wbs-2.etc.uspto.gov:8080".equals(HOST_SERVER);
            boolean fqtflag = "fqt-tmng-cms.etc.uspto.gov".equals(HOST_SERVER);
            if ( devflag || sitCmsCulsterflag || sitCmsNodeOneFlag || sitCmsNodeTwoFlag || fqtflag ) {
                hostIsAlfresco = false;
            }else{
                hostIsAlfresco = true;
            } 
        }else if("cmsLayer".equalsIgnoreCase(ipContext)){
            hostIsAlfresco = false;
        }else if("alfrescoLayer".equalsIgnoreCase(ipContext)){
            hostIsAlfresco = true;
        }
        return hostIsAlfresco;
    }

    /**
     * Contains string literal.
     *
     * @param haystack the haystack
     * @param needle the needle
     * @return true, if successful
     */
    public static boolean containsStringLiteral(String haystack, String needle) {
        if (haystack == null || needle == null || haystack.equals("")) {
            return false;
        }
        if (needle.equals("")) {
            return true;
        }
        Pattern p = Pattern.compile(needle, Pattern.LITERAL);
        Matcher m = p.matcher(haystack);
        return m.find();
    }

    /**
     * Contains string with reg ex.
     *
     * @param haystack the haystack
     * @param needle the needle
     * @return true, if successful
     */
    public static boolean containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(String haystack, String needle) {
        return containsStringOfGivenRegExForSuppliedCount(haystack, needle, 1);
    }
    
    public static boolean containsStringOfGivenRegExForSuppliedCount(String haystack, String needle, int numberOfStringOccurence) {
        // System.out.println("haystack -> " + haystack);
        // System.out.println("needle -> " + needle);
        if (haystack == null || needle == null || haystack.equals("")) {
            return false;
        }
        if (needle.equals("")) {
            return true;
        }

        Pattern p = Pattern.compile(needle);
        Matcher m = p.matcher(haystack);

        int count = 0;
        while (m.find()){
            count++;    
        }
        
        boolean expectedMatchFound = false;
        if(numberOfStringOccurence <= count){
            expectedMatchFound = true;
        }else{
            expectedMatchFound = false;
        }
        
        return expectedMatchFound;
    }    

    /**
     * Extract string with reg ex.
     *
     * @param haystack the haystack
     * @param needle the needle
     * @return the string
     */
    public String extractStringWithRegEx(String haystack, String needle) {
        if (haystack == null || needle == null || haystack.equals("")) {
            return null;
        }
        if (needle.equals("")) {
            return null;
        }

        Pattern p = Pattern.compile(needle);
        Matcher m = p.matcher(haystack);

        if (m.find()) {

            return m.group();
        }

        return null;

    }

/*    *//**
     * The main method.
     *
     * @param agr0 the arguments
     *//*
    public static void main(String[] agr0) {

        TmnguiBaseTest tbt = new TmnguiBaseTest();

        String haystack2 = "[{\"documentId\":\"/case/77777780/mark/Mark_1_Version1.0.jpeg\",\"documentType\":\"mark\",\"version\":\"1.0\",\"metadata\":{\"creationTime\":\"2015-11-19T21:10:54.822-0500\",\"modificationTime\":\"2015-11-19T21:10:54.822-0500\",\"mimeType\":\"image/jpeg\",\"documentSize\":\"15138\",\"version\":\"1.0\",\"documentName\":\"Mark_1_Version1.0.jpeg\",\"documentAlias\":\"nickname\",\"createdByUserId\":\"CoderX\",\"modifiedByUserId\":\"metFor1.2\",\"redactionLevel\":\"None\",\"accessLevel\":\"public\",\"serialNumber\":\"77777780\",\"sourceMedia\":\"E\",\"sourceMedium\":\"Email\",\"mailDate\":\"2013-06-25T07:41:19.000-0400\",\"scanDate\":\"2013-06-16T05:33:22.000-0400\",\"loadDate\":\"2015-11-19T21:10:54.878-0500\",\"effectiveStartDate\":\"2013-07-05T20:00:00.000-0400\",\"multimediaProps\":{},\"imageProps\":{},\"documentType\":\"mark\"},\"serialNumber\":\"77777780\"},{\"documentId\":\"/case/77777780/mark/FirstMultimediaMark.avi\",\"documentType\":\"mark\",\"version\":\"1.0\",\"metadata\":{\"creationTime\":\"2015-11-19T21:10:54.970-0500\",\"modificationTime\":\"2015-11-19T21:10:54.970-0500\",\"mimeType\":\"video/x-msvideo\",\"documentSize\":\"675840\",\"version\":\"1.0\",\"documentName\":\"FirstMultimediaMark.avi\",\"documentAlias\":\"nickname\",\"modifiedByUserId\":\"User XYZ\",\"redactionLevel\":\"None\",\"accessLevel\":\"public\",\"serialNumber\":\"77777780\",\"sourceMedia\":\"electronic\",\"sourceMedium\":\"upload\",\"mailDate\":\"2014-04-23T13:42:24.962-0400\",\"scanDate\":\"2014-04-23T13:42:24.962-0400\",\"loadDate\":\"2015-11-19T21:10:55.023-0500\",\"effectiveStartDate\":\"2014-04-23T13:42:24.962-0400\",\"multimediaProps\":{\"audioCodec\":\"FFmpeg\",\"audioCompressionType\":\"MPEG-1\",\"videoCodec\":\"DivX\",\"videoCompressionType\":\"H.264\",\"multimediaDuration\":\"01:02:03.123\",\"multimediaStartTime\":\"00:04:05.678\",\"multimediaComment\":\"No comments\"},\"imageProps\":{},\"documentType\":\"mark\"},\"serialNumber\":\"77777780\"},{\"documentId\":\"/case/77777780/legacy/Legacy_1_Version1.0.pdf\",\"documentType\":\"legacy\",\"version\":\"1.0\",\"metadata\":{\"creationTime\":\"2015-11-19T21:10:55.210-0500\",\"modificationTime\":\"2015-11-19T21:10:55.210-0500\",\"mimeType\":\"application/pdf\",\"documentSize\":\"80280\",\"version\":\"1.0\",\"documentName\":\"Legacy_1_Version1.0.pdf\",\"documentAlias\":\"DocNameForTmngUiDisplay\",\"modifiedByUserId\":\"CreateLegacyIntegraTestV_1_0\",\"redactionLevel\":\"None\",\"accessLevel\":\"public\",\"migrationMethod\":\"LZL\",\"migrationSource\":\"TICRS\",\"serialNumber\":\"77777780\",\"sourceMedia\":\"electronic\",\"sourceMedium\":\"upload\",\"scanDate\":\"2014-04-23T13:42:24.962-0400\",\"loadDate\":\"2015-11-19T21:10:55.210-0500\",\"docCode\":\"LGCY\",\"legacyCategory\":\"Migrated\",\"multimediaProps\":{},\"documentType\":\"legacy\"},\"serialNumber\":\"77777780\"},{\"documentId\":\"/case/77777780/note/Note_1_Version1.0.pdf\",\"documentType\":\"note\",\"version\":\"1.0\",\"metadata\":{\"creationTime\":\"2015-11-19T21:10:55.327-0500\",\"modificationTime\":\"2015-11-19T21:10:55.327-0500\",\"mimeType\":\"application/pdf\",\"documentSize\":\"80280\",\"version\":\"1.0\",\"documentName\":\"Note_1_Version1.0.pdf\",\"documentAlias\":\"DocNameForTmngUiDisplay\",\"modifiedByUserId\":\"CreateNoteIntegraTestV_1_0\",\"redactionLevel\":\"None\",\"accessLevel\":\"public\",\"serialNumber\":\"77777780\",\"sourceMedia\":\"electronic\",\"sourceMedium\":\"upload\",\"mailDate\":\"2014-04-23T13:42:24.962-0400\",\"scanDate\":\"2014-04-23T13:42:24.962-0400\",\"loadDate\":\"2015-11-19T21:10:55.327-0500\",\"documentType\":\"note\"},\"serialNumber\":\"77777780\"},{\"documentId\":\"/case/77777780/notice/Notice_1_Version1.0.pdf\",\"documentType\":\"notice\",\"version\":\"1.0\",\"metadata\":{\"creationTime\":\"2015-11-19T21:10:55.458-0500\",\"modificationTime\":\"2015-11-19T21:10:55.458-0500\",\"mimeType\":\"application/pdf\",\"documentSize\":\"80280\",\"version\":\"1.0\",\"documentName\":\"Notice_1_Version1.0.pdf\",\"documentAlias\":\"DocNameForTmngUiDisplay\",\"modifiedByUserId\":\"CreateNoticeIntegraTestV_1_0\",\"redactionLevel\":\"None\",\"accessLevel\":\"public\",\"serialNumber\":\"77777780\",\"sourceMedia\":\"electronic\",\"sourceMedium\":\"upload\",\"mailDate\":\"2014-04-23T13:42:24.962-0400\",\"scanDate\":\"2014-04-23T13:42:24.962-0400\",\"loadDate\":\"2015-11-19T21:10:55.458-0500\",\"documentType\":\"notice\"},\"serialNumber\":\"77777780\"},{\"documentId\":\"/case/77777780/evidence/x-search-found.pdf\",\"documentType\":\"evidence\",\"version\":\"1.1\",\"metadata\":{\"creationTime\":\"2015-11-19T21:10:55.573-0500\",\"modificationTime\":\"2015-11-19T21:10:55.801-0500\",\"mimeType\":\"application/pdf\",\"documentSize\":\"80280\",\"version\":\"1.1\",\"documentName\":\"x-search-found.pdf\",\"documentAlias\":\"DocNameForTmngUiDisplay\",\"modifiedByUserId\":\"User XYZ\",\"redactionLevel\":\"None\",\"accessLevel\":\"public\",\"serialNumber\":\"77777780\",\"sourceMedia\":\"electronic\",\"sourceMedium\":\"upload\",\"scanDate\":\"2013-06-16T05:33:22.000-0400\",\"loadDate\":\"2015-11-19T21:10:55.573-0500\",\"evidenceSourceUrl\":\"http://local/evidence/source/url\",\"evidenceSourceType\":\"OA\",\"evidenceSourceTypeId\":\"123\",\"evidenceGroupNames\":[\"sent\",\"suggested\"],\"evidenceCategory\":\"nexis-lexis\",\"multimediaProps\":{},\"documentType\":\"evidence\"},\"serialNumber\":\"77777780\"},{\"documentId\":\"/case/77777780/evidence/my-findings.pdf\",\"documentType\":\"evidence\",\"version\":\"1.1\",\"metadata\":{\"creationTime\":\"2015-11-19T21:10:55.673-0500\",\"modificationTime\":\"2015-11-19T21:10:55.810-0500\",\"mimeType\":\"application/pdf\",\"documentSize\":\"80280\",\"version\":\"1.1\",\"documentName\":\"my-findings.pdf\",\"documentAlias\":\"DocNameForTmngUiDisplay\",\"modifiedByUserId\":\"User XYZ\",\"redactionLevel\":\"None\",\"accessLevel\":\"public\",\"serialNumber\":\"77777780\",\"sourceMedia\":\"electronic\",\"sourceMedium\":\"upload\",\"scanDate\":\"2013-06-16T05:33:22.000-0400\",\"loadDate\":\"2015-11-19T21:10:55.673-0500\",\"evidenceSourceUrl\":\"http://local/evidence/source/url\",\"evidenceSourceType\":\"OA\",\"evidenceSourceTypeId\":\"123\",\"evidenceGroupNames\":[\"working\",\"sent\"],\"evidenceCategory\":\"nexis-lexis\",\"multimediaProps\":{},\"documentType\":\"evidence\"},\"serialNumber\":\"77777780\"},{\"documentId\":\"/case/77777780/summary/Summary_1_Version1.0.docx\",\"documentType\":\"summary\",\"version\":\"1.0\",\"metadata\":{\"creationTime\":\"2015-11-19T21:10:56.193-0500\",\"modificationTime\":\"2015-11-19T21:10:56.193-0500\",\"mimeType\":\"application/vnd.openxmlformats-officedocument.wordprocessingml.document\",\"documentSize\":\"19894\",\"version\":\"1.0\",\"documentName\":\"Summary_1_Version1.0.docx\",\"documentAlias\":\"DocNameForTmngUiDisplay\",\"modifiedByUserId\":\"CreateSummaryIntegraTestV_1_0\",\"redactionLevel\":\"None\",\"accessLevel\":\"public\",\"serialNumber\":\"77777780\",\"sourceMedia\":\"electronic\",\"sourceMedium\":\"upload\",\"scanDate\":\"2014-04-23T13:42:24.962-0400\",\"loadDate\":\"2015-11-19T21:10:56.193-0500\",\"documentType\":\"summary\"},\"serialNumber\":\"77777780\"},{\"documentId\":\"/case/77777780/response/Response_1_Version1.0.pdf\",\"documentType\":\"response\",\"version\":\"1.0\",\"metadata\":{\"creationTime\":\"2015-11-19T21:10:56.324-0500\",\"modificationTime\":\"2015-11-19T21:10:56.324-0500\",\"mimeType\":\"application/pdf\",\"documentSize\":\"80280\",\"version\":\"1.0\",\"documentName\":\"Response_1_Version1.0.pdf\",\"documentAlias\":\"DocNameForTmngUiDisplay\",\"modifiedByUserId\":\"CreateResponseIntegraTestV_1_0\",\"redactionLevel\":\"PARTIAL\",\"accessLevel\":\"public\",\"serialNumber\":\"77777780\",\"sourceMedia\":\"electronic\",\"sourceMedium\":\"upload\",\"scanDate\":\"2014-04-23T13:42:24.962-0400\",\"loadDate\":\"2015-11-19T21:10:56.324-0500\",\"documentType\":\"response\"},\"serialNumber\":\"77777780\"},{\"documentId\":\"/case/77777780/receipt/Receipt_1_Version1.0.pdf\",\"documentType\":\"receipt\",\"version\":\"1.0\",\"metadata\":{\"creationTime\":\"2015-11-19T21:10:56.431-0500\",\"modificationTime\":\"2015-11-19T21:10:56.431-0500\",\"mimeType\":\"application/pdf\",\"documentSize\":\"30915\",\"version\":\"1.0\",\"documentName\":\"Receipt_1_Version1.0.pdf\",\"documentAlias\":\"DocNameForTmngUiDisplay\",\"modifiedByUserId\":\"CreateReceiptIntegraTestV_1_0\",\"redactionLevel\":\"None\",\"accessLevel\":\"public\",\"serialNumber\":\"77777780\",\"sourceMedia\":\"electronic\",\"sourceMedium\":\"upload\",\"scanDate\":\"2014-04-23T13:42:24.962-0400\",\"loadDate\":\"2015-11-19T21:10:56.431-0500\",\"documentType\":\"receipt\"},\"serialNumber\":\"77777780\"},{\"documentId\":\"/case/77777780/signature/Signature_1_Version1.0.docx\",\"documentType\":\"signature\",\"version\":\"1.0\",\"metadata\":{\"creationTime\":\"2015-11-19T21:10:56.523-0500\",\"modificationTime\":\"2015-11-19T21:10:56.523-0500\",\"mimeType\":\"application/pdf\",\"documentSize\":\"30915\",\"version\":\"1.0\",\"documentName\":\"Signature_1_Version1.0.docx\",\"documentAlias\":\"DocNameForTmngUiDisplay\",\"modifiedByUserId\":\"CreateSignatureIntegraTestV_1_0\",\"redactionLevel\":\"None\",\"accessLevel\":\"public\",\"serialNumber\":\"77777780\",\"sourceMedia\":\"electronic\",\"sourceMedium\":\"upload\",\"scanDate\":\"2014-04-23T13:42:24.962-0400\",\"loadDate\":\"2015-11-19T21:10:56.523-0500\",\"documentType\":\"signature\"},\"serialNumber\":\"77777780\"},{\"documentId\":\"/case/77777780/signature/P-Signature_1_Version1.0.docx\",\"documentType\":\"signature\",\"version\":\"1.0\",\"metadata\":{\"creationTime\":\"2015-11-19T21:10:56.608-0500\",\"modificationTime\":\"2015-11-19T21:10:56.608-0500\",\"mimeType\":\"application/pdf\",\"documentSize\":\"30915\",\"version\":\"1.0\",\"documentName\":\"P-Signature_1_Version1.0.docx\",\"documentAlias\":\"DocNameForTmngUiDisplay\",\"modifiedByUserId\":\"CreateSignatureIntegraTestV_1_0\",\"redactionLevel\":\"None\",\"accessLevel\":\"public\",\"serialNumber\":\"77777780\",\"sourceMedia\":\"electronic\",\"sourceMedium\":\"upload\",\"scanDate\":\"2014-04-23T13:42:24.962-0400\",\"loadDate\":\"2015-11-19T21:10:56.608-0500\",\"documentType\":\"signature\"},\"serialNumber\":\"77777780\"}]        ";
        String needle2 = "\"documentId\"(.+?)\"/case/(.*?)/mark/(.*?)\"";

        String truth = tbt.extractStringWithRegEx(haystack2, needle2);
        System.out.println("##" + truth);

        boolean status = TmnguiBaseTest.containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack2, needle2);
        System.out.println("##" + status);
    }*/

}
