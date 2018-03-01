package alf.integration.service.environments;

/**
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class SitAlfEnvironment {
    
    private String alfrescoVIPHostname = "tmng-alfupgrade.sit.uspto.gov";
    private String alfrescoNodeOneHostname = "tm-alf-7.sit.uspto.gov:8080";
    private String alfrescoNodeTwoHostname = "tm-alf-8.sit.uspto.gov:8080";
    private String alfrescoShareUIUid = "cmsTmngSIT";
    private String alfrescoShareUIPd = "C@SS!TCl@ne!#"; //[From Oct-05-2017]
    
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
