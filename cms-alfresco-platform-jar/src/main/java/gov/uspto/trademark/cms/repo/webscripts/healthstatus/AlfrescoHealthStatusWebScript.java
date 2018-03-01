package gov.uspto.trademark.cms.repo.webscripts.healthstatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.services.impl.AlfrescoHealthStatusService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * This is alfresco hello rest api.
 *
 * @author stank
 */
public class AlfrescoHealthStatusWebScript extends AbstractCmsCommonWebScript {

    @Autowired
    @Qualifier(value = "alfrescoHealthStatusService")
    private AlfrescoHealthStatusService alfrescoHealthStatusService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.webscripts.CmsCommonWebScript#executeService
     * (org.springframework.extensions.webscripts.WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    protected void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webScriptResponse) {
        AlfrescoHealthStatusResponse ahsr = new AlfrescoHealthStatusResponse();
        ahsr.setName(TMConstants.ALFRESCO);

        Detail detailStatusOfAlfMount = alfrescoHealthStatusService.checkStatusOfAlfrescoMount();
        Detail detailStatusOfAlfDB = alfrescoHealthStatusService.checkStatusOfAlfrescoDB();

        String overallStatus = processOverallStatus(detailStatusOfAlfMount, detailStatusOfAlfDB);
        ahsr.setStatus(overallStatus);

        List<Detail> detailsAlfChecks = new ArrayList<Detail>();
        detailsAlfChecks.add(detailStatusOfAlfMount);
        detailsAlfChecks.add(detailStatusOfAlfDB);
        ahsr.setDetails(detailsAlfChecks);
        webScriptResponse.setContentType(TMConstants.APPLICATION_JSON);
        try {
            webScriptResponse.getOutputStream().write(JacksonHelper.generateClientJsonFrDTO(ahsr));
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    private String processOverallStatus(Detail detailStatusOfAlfMount, Detail detailStatusOfAlfDB) {
        String finalStatus = null;
        Status mountPoint = Status.valueOf(detailStatusOfAlfMount.getStatus());
        Status db = Status.valueOf(detailStatusOfAlfDB.getStatus());
        finalStatus = Status.getOverallAlfrescoStatus(mountPoint, db).getStatus();
        return finalStatus;
    }

    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        // nothing to validate here.
    }
}