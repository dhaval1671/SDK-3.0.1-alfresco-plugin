package alf.integration.service.dtos;

/**
 * The Class LegacyTicrsDto.
 */
public class LegacyTicrsDto {

    private String docType;
    private String versionFolder;
    private String fileName;
    
    public String getDocType() {
        return docType;
    }
    public void setDocType(String docType) {
        this.docType = docType;
    }
    public String getVersionFolder() {
        return versionFolder;
    }
    public void setVersionFolder(String versionFolder) {
        this.versionFolder = versionFolder;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
