package alf.integration.service.environments;

/**
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class PvtAlfEnvironment {
    
    private String alfrescoVIPHostname = "tmng-alfupgrade.pvt.uspto.gov";
    private String alfrescoNodeOneHostname = "tmng-alf-3.pvt.uspto.gov:8080";
    private String alfrescoNodeTwoHostname = "tmng-alf-4.pvt.uspto.gov:8080";
    private String alfrescoShareUIUid = "cmsTmngPVT";
    private String alfrescoShareUIPd = "U59T0@lf50ct#1";
    
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
