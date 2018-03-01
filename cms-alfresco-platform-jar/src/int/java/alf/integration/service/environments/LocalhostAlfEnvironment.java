package alf.integration.service.environments;

/**
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class LocalhostAlfEnvironment {
    
    private static final String LOCALHOST_8080 = "localhost:8080";
    
    private String alfrescoVIPHostname = LOCALHOST_8080;
    private String alfrescoNodeOneHostname = LOCALHOST_8080;
    private String alfrescoNodeTwoHostname = LOCALHOST_8080;
    private String alfrescoShareUIUid = "admin";
    private String alfrescoShareUIPd = "admin";
     
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
