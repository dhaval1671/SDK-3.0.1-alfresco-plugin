package gov.uspto.trademark.cms.repo.webscripts.healthstatus;

import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * This is alfresco hello rest api.
 *
 * @author stank
 */
public class AlfrescoHelloWebScript extends AbstractCmsCommonWebScript {

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
        // do nothings
    }

    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        // nothing to validate here.
    }
}