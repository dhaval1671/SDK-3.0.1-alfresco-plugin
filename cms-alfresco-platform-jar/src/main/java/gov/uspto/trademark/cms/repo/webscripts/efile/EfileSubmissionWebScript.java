package gov.uspto.trademark.cms.repo.webscripts.efile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.identifiers.BehaviorIdStrategy;
import gov.uspto.trademark.cms.repo.services.impl.EfileSubmissionsService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;
import gov.uspto.trademark.cms.repo.webscripts.efile.vo.SubmissionJson;

/**
 * A webscript to get Efile Content for a given Efile.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class EfileSubmissionWebScript extends AbstractCmsCommonWebScript {

    /** The Constant INVALID_DOCUMENT_ID. */
    private static final String INVALID_DOCUMENT_ID = "Invalid documentId ";

    /** The supported document type. */
    private static final List<String> supportedDocumentType = new ArrayList<String>();

    static {
        supportedDocumentType.add(TradeMarkDocumentTypes.TYPE_RECEIPT.getAlfrescoTypeName());
        supportedDocumentType.add(TradeMarkDocumentTypes.TYPE_SIGNATURE.getAlfrescoTypeName());
        supportedDocumentType.add(TradeMarkDocumentTypes.TYPE_ATTACHMENT.getAlfrescoTypeName());
    }

    /** The global id. */
    @Autowired
    private BehaviorIdStrategy globalId;

    @Autowired
    private EfileSubmissionsService submissionsService;

    /**
     * This method is used to execute the web script specific functionality.
     *
     * @param webScriptRequest
     *            the web script request
     * @param webScriptResponse
     *            the web script response
     */
    @Override
    protected void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webScriptResponse) {
        try {
            SubmissionJson[] submissionJson = JacksonHelper.unMarshall(webScriptRequest.getContent().getInputStream(),
                    SubmissionJson[].class);
            this.validateSubmissionInput(submissionJson);
            for (SubmissionJson efileCopyRequest : submissionJson) {
                String reqDocType = efileCopyRequest.getDocumentType();
                if (!(supportedDocumentType.contains(reqDocType))) {
                    throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                            "This API does NOT support " + reqDocType + " type document.");
                }
                verifyTargetSerialNumbersAreValidIds(efileCopyRequest.getSerialNumbers());
            }
            List<TmngAlfResponse> responses = submissionsService.submitEfile(submissionJson);
            webScriptResponse.setContentType(TMConstants.APPLICATION_JSON);
            webScriptResponse.getOutputStream().write(JacksonHelper.generateClientJsonFrDTO(responses));
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (TmngCmsException.InvalidGlobalIdException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (TmngCmsException.DocumentDoesNotExistException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (TmngCmsException.SerialNumberNotFoundException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (TmngCmsException.CMSRuntimeException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * This method validates the request parameters.
     *
     * @param submissionJsonArray
     *            the web script request
     */
    public void validateSubmissionInput(SubmissionJson[] submissionJsonArray) {
        for (SubmissionJson submissionJson : submissionJsonArray) {

            // validate serial number format
            for (String serialNumberLocal : submissionJson.getSerialNumbers()) {
                AbstractCmsCommonWebScript.checkSerialNumber(serialNumberLocal);
            }
            // validate documentId
            int count = StringUtils.countMatches(submissionJson.getDocumentId(), TMConstants.FORWARD_SLASH);
            if (count != TMConstants.FOUR) {
                throw new TmngCmsException.InvalidGlobalIdException(INVALID_DOCUMENT_ID + submissionJson.getDocumentId());
            }
            // validate globalid format
            globalId.isValid(submissionJson.getDocumentId().split(TMConstants.FORWARD_SLASH)[TMConstants.THREE]);
        }
    }

    /**
     * This method validates the request parameters.
     *
     * @param webScriptRequest
     *            the web script request
     */
    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        // Nothing to validate
    }

    /**
     * Verify target serial numbers are valid ids.
     *
     * @param serialNumbers
     *            the serial numbers
     */
    private void verifyTargetSerialNumbersAreValidIds(List<String> serialNumbers) {
        for (String serialNumberLocal : serialNumbers) {
            AbstractCmsCommonWebScript.checkSerialNumber(serialNumberLocal);
        }
    }

}