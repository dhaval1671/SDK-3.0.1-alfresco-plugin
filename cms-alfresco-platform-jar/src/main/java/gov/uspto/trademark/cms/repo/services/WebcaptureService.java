package gov.uspto.trademark.cms.repo.services;

import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.extensions.webscripts.servlet.FormData.FormField;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.WebcaptureResponse;

/**
 * Created by stank on May/3/2017
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
@Component
public interface WebcaptureService extends BaseService {
    
    NodeRef getDocumentLibraryFolderNodeRef();

    TmngAlfResponse createWebcapture(String userid, String fileName, FormField content);

    TmngAlfResponse deleteWebcapture(String userid, String fileName);

    WebcaptureResponse renameWebcapture(String userid, String fileName, String newFileName);

    NodeRef getWebcaptureFolderNodeRef();
}
