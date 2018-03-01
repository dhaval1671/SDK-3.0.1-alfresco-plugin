package gov.uspto.trademark.cms.repo.webscripts.doclib.evidencelibrary;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.extensions.webscripts.servlet.WebScriptServletRuntime;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.PathResolver;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.EvidenceLibraryService;
import gov.uspto.trademark.cms.repo.services.WebcaptureService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * A webscript to get Content for a given evidence file from 2A Evidence
 * Library.
 *
 * @author stank
 */
public class Evidence2aFileAndFolderContentsWebScript extends AbstractCmsCommonWebScript {

    private static final String FOLDER_PATH_WEBCAPTURE = "folder-path/webcapture";

    private static final String FILE_PATH_WEBCAPTURE = "file-path/webcapture";

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(Evidence2aFileAndFolderContentsWebScript.class);

    @Autowired
    @Qualifier(value = "EvidenceLibraryImplService")
    private EvidenceLibraryService evidenceLibraryService;
    
    @Autowired
    @Qualifier(value = "WebcaptureImplService")
    private WebcaptureService webcaptureService;    

    private NodeRef apiParentNodeRef; 

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.webscripts.BaseWebscript#executeAction(org
     * .springframework.extensions.webscripts.WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    public void executeService(WebScriptRequest webRequest, WebScriptResponse webResponse) {

        apiParentNodeRef = evidenceLibraryService.getEvidenceBankFolderNodeRef();
        
        /** The serve file contents. */
        Boolean serveFileContents = Boolean.FALSE;

        /** The serve folder contents. */
        Boolean serveFolderContents = Boolean.FALSE;

        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webRequest);
        final String path = urlParameters.get(TMConstants.PATH_PARAM);

        HttpServletRequest hsr = WebScriptServletRuntime.getHttpServletRequest(webRequest);
        String str1 = hsr.getRequestURI();
        String filePath = PathResolver.FILE_PATH;
        String folderPath = PathResolver.FOLDER_PATH;
        if (StringUtils.isNotBlank(str1)) {
            if (str1.toLowerCase().contains(filePath.toLowerCase())) {
                serveFileContents = Boolean.TRUE;
                if(str1.toLowerCase().contains(FILE_PATH_WEBCAPTURE)){
                    apiParentNodeRef = webcaptureService.getDocumentLibraryFolderNodeRef();
                }                
            } else if (str1.toLowerCase().contains(folderPath.toLowerCase())) {
                serveFolderContents = Boolean.TRUE;
                if(str1.toLowerCase().contains(FOLDER_PATH_WEBCAPTURE)){
                    apiParentNodeRef = webcaptureService.getDocumentLibraryFolderNodeRef();
                }                 
            }
            
        }

        if (serveFileContents) {
            if (StringUtils.isNotBlank(path)) {
                serveFileContents(webResponse, path);
            } else {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Path parameter missing");
            }
        } else if (serveFolderContents) {
            NodeRef nodeRef = apiParentNodeRef;
            serveFolderContents(webResponse, path, nodeRef);
        }
    }

    /**
     * Serve folder contents.
     *
     * @param webResponse
     *            the web response
     * @param path
     *            the path
     * @param nodeRef
     *            the node ref
     */
    private void serveFolderContents(WebScriptResponse webResponse, String path, NodeRef nodeRef) {
        NodeRef folderNodeRef;
        if (StringUtils.isNotBlank(path)) {
            path = path.replace(PathResolver.EVIDENCE_LIBRARY_FOLDER_PATH_PREFIX, "");
            path = path.startsWith("/") ? path.substring(1) : path;
            if (StringUtils.isBlank(path)) {
                folderNodeRef = nodeRef;
            } else {
                String[] folderPath = path.split(TMConstants.FORWARD_SLASH);
                folderNodeRef = evidenceLibraryService.getFolderNodeRef(null, nodeRef, folderPath);
            }
        } else {
            folderNodeRef = nodeRef;
        }
        if (folderNodeRef == null) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, "Folder path does not exists");
        } else {
            if (serviceRegistry.getFileFolderService().getFileInfo(folderNodeRef).isFolder()) {
                List<FileInfo> fileList = serviceRegistry.getFileFolderService().listFiles(folderNodeRef);
                List<FileInfo> folderList = serviceRegistry.getFileFolderService().listFolders(folderNodeRef);
                Map<String, List<Map<String, String>>> results = evidenceLibraryService.get2aEvidenceFolderContents(fileList,
                        folderList);
                byte[] jsonObject = WebScriptHelper.toJson(results);
                webResponse.setContentType(TMConstants.APPLICATION_JSON);
                try {
                    webResponse.getOutputStream().write(jsonObject);
                } catch (IOException e) {
                    if (log.isDebugEnabled()) {
                        log.debug(e.getMessage(), e);
                    }
                }
            } else {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Verify folder path");
            }
        }
    }

    /**
     * Serve file contents.
     *
     * @param webResponse
     *            the web response
     * @param path
     *            the path
     */
    private void serveFileContents(WebScriptResponse webResponse, String path) {
        path = path.replace(PathResolver.EVIDENCE_LIBRARY_FILE_PATH_PREFIX, "");
        path = path.startsWith("/") ? path.substring(1) : path;
        String[] pathparts = path.split(TMConstants.FORWARD_SLASH);
        String fileName = pathparts[pathparts.length - 1];
        ContentReader creader = null;
        NodeRef nr = apiParentNodeRef;
        NodeRef fileNodeRef = evidenceLibraryService.getFolderNodeRef(null, nr, pathparts);
        if (fileNodeRef == null) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, "File path does not exists");
        } else {
            creader = evidenceLibraryService.get2aEvidenceFileContent(fileNodeRef);
            if (creader == null) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Verify file path");
            } else {
                webResponse.setContentType(creader.getMimetype());
                webResponse.setContentEncoding(creader.getEncoding());
                webResponse.addHeader(HttpHeaders.CONTENT_LENGTH, "" + creader.getSize());
                webResponse.addHeader("Content-Disposition", "inline; filename=" + fileName);
                try {
                    FileCopyUtils.copy(creader.getContentInputStream(), webResponse.getOutputStream());
                } catch (ContentIOException e) {
                    if (log.isDebugEnabled()) {
                        log.debug(e.getMessage(), e);
                    }
                } catch (IOException e) {
                    if (log.isDebugEnabled()) {
                        log.debug(e.getMessage(), e);
                    }
                }
            }
        }
    }

}