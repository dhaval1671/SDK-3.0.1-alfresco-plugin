package alf.integration.service.environments;

/**
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class DevAlfEnvironment {
    
    private static final String TMNG_ALF_1_DEV_USPTO_GOV_8080 = "tmng-alf-1.dev.uspto.gov:8080";
    
    private String alfrescoVIPHostname = TMNG_ALF_1_DEV_USPTO_GOV_8080;
    private String alfrescoNodeOneHostname = TMNG_ALF_1_DEV_USPTO_GOV_8080;
    private String alfrescoNodeTwoHostname = TMNG_ALF_1_DEV_USPTO_GOV_8080;
    private String alfrescoShareUIUid = "admin";
    private String alfrescoShareUIPd = "TmngUspto20!5_02";
    
    public String getAlfrescoVIPHostname() {
        return alfrescoVIPHostname;
    }
    public void setAlfrescoVIPHostname(String alfrescoVIPHostname) {
        this.alfrescoVIPHostname = alfrescoVIPHostname;
    }
    public String getAlfrescoNodeOneHostname() {
        return alfrescoNodeOneHostname;
    }
    public void setAlfrescoNodeOneHostname(String alfrescoNodeOneHostname) {
        this.alfrescoNodeOneHostname = alfrescoNodeOneHostname;
    }
    public String getAlfrescoNodeTwoHostname() {
        return alfrescoNodeTwoHostname;
    }
    public void setAlfrescoNodeTwoHostname(String alfrescoNodeTwoHostname) {
        this.alfrescoNodeTwoHostname = alfrescoNodeTwoHostname;
    }
    public String getAlfrescoShareUIUid() {
        return alfrescoShareUIUid;
    }
    public void setAlfrescoShareUIUid(String alfrescoShareUIUid) {
        this.alfrescoShareUIUid = alfrescoShareUIUid;
    }
    public String getAlfrescoShareUIPd() {
        return alfrescoShareUIPd;
    }
    public void setAlfrescoShareUIPd(String alfrescoShareUIPd) {
        this.alfrescoShareUIPd = alfrescoShareUIPd;
    }
     

}
