package gov.uspto.trademark.cms.repo.webscripts;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.DuplicateChildNodeNameException;
import org.alfresco.service.transaction.TransactionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.extensions.webscripts.servlet.WebScriptServletRuntime;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.BaseRuntimeException;
import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.OfficeAction;

/**
 * This is the common web script used for all CMS CRUD services.
 * 
 * @author vnondapaka
 *
 */
public abstract class AbstractCmsCommonWebScript extends AbstractWebScript {

	private static final String EVIDENCE_LIBRARY_URL_SIGNATURE = "/libraries/evidences/content";

	/** The Constant LOG. */
	private static final Logger log = LoggerFactory.getLogger(AbstractCmsCommonWebScript.class);
	/** The Constant SERIAL_NUMBER_PATH_PARAMETER. */
	public static final String ID_PARAMETER = "id";

	/** The Constant DOC_TYPE. */
	public static final String DOC_TYPE = "docType";

	/** The Constant PUB_TYPE. */
	public static final String PUB_TYPE = "pub";

	/** The Constant FILE_NAME_PARAM. */
	public static final String FILE_NAME_PARAM = "fileName";

	/** The Constant METADATA_QS_PARAM. */
	public static final String METADATA_QS_PARAM = "metadata";

	/** The Constant CONTENT. */
	public static final String CONTENT = "content";

	/** The Constant VERSION_NUMBER_QS_PARAM. */
	public static final String VERSION_NUMBER_QS_PARAM = "version";

	/** The Constant NO_CACHE_NO_STORE_MUST_REVALIDATE. */
	private static final String NO_CACHE_NO_STORE_MUST_REVALIDATE = "no-cache, no-store, must-revalidate";

	/** The Constant CACHE_CONTROL. */
	private static final String CACHE_CONTROL = "Cache-Control";

	/** The Constant RENDITION_FORMAT. */
	public static final String RENDITION_FORMAT = "format";

	/** The Constant SERIAL_NUMBER_QS_SEPARATOR. */
	public static final String SERIAL_NUMBER_QS_SEPARATOR = ",";

	/** The Constant SERIAL_NUMBER_PATH_PARAMETER. */
	public static final String SERIAL_NUMBER_PATH_PARAMETER = "sn";

	/** The service registry. */
	@Autowired
	@Qualifier(value = "ServiceRegistry")
	protected ServiceRegistry serviceRegistry;
	
    @Autowired
    @Qualifier("TransactionService")    
    protected TransactionService transactionService;	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.extensions.webscripts.WebScript#execute(org.
	 * springframework.extensions.webscripts.WebScriptRequest,
	 * org.springframework.extensions.webscripts.WebScriptResponse)
	 */
	@Override
	public void execute(WebScriptRequest webScriptRequest, WebScriptResponse webScriptResponse) throws IOException {
		try {
			/*
			 * START - added to troubleshoot OA metadata loss issue in
			 * production, please remove after troubleshooting.
			 */
			if (log.isDebugEnabled()) {
				Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
				String docType = urlParameters.get(DOC_TYPE);
				HttpServletRequest hsr = WebScriptServletRuntime.getHttpServletRequest(webScriptRequest);
				if (StringUtils.equalsIgnoreCase(docType, OfficeAction.TYPE)
						&& !(StringUtils.equalsIgnoreCase(hsr.getMethod(), TMConstants.GET))) {
					log.debug(hsr.getMethod() + " :: " + hsr.getRequestURL().toString());
					String metadata = webScriptRequest.getParameter(METADATA_QS_PARAM);
					log.debug("### metadata: " + metadata);
				}
			}
			/*
			 * END - added to troubleshoot OA metadata loss issue in production,
			 * please remove after troubleshooting.
			 */

			// implement Generic Validation framework
			validateRequest(webScriptRequest);
			// Set common cache headers
			webScriptResponse.setHeader(CACHE_CONTROL, NO_CACHE_NO_STORE_MUST_REVALIDATE);
			executeService(webScriptRequest, webScriptResponse);
		} catch (DuplicateChildNodeNameException e) {
			sendResponse(webScriptResponse, HttpStatus.CONFLICT, e.getMessage(), e);
		} catch (TmngCmsException.PropertyNotFoundException e) {
			sendResponse(webScriptResponse, org.springframework.http.HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (AlfrescoRuntimeException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.getRootCause().printStackTrace(pw);
			sendResponse(webScriptResponse, org.springframework.http.HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (TmngCmsException.SerialNumberNotFoundException e) {
			sendResponse(webScriptResponse, e.getStatusCode(), e.getMessage(), e);
		} catch (TmngCmsException.FileCheckFailedException e) {
			sendResponse(webScriptResponse, e.getStatusCode(), e.getMessage(), e);
		} catch (TmngCmsException.CorruptedDocIdException e) {
			sendResponse(webScriptResponse, e.getStatusCode(), e.getMessage(), e);
		} catch (TmngCmsException.CMSRuntimeException e) {
            if(null != e.getStatusCode()){
                sendResponse(webScriptResponse, e.getStatusCode(), e.getMessage(), e);
            }else{
                sendResponse(webScriptResponse, HttpStatus.FORBIDDEN, e.getMessage(), e);    
            }		    
		} catch (TmngCmsException.UpdateEvidenceContentFailedException e) {
			sendResponse(webScriptResponse, HttpStatus.FORBIDDEN, e.getMessage(), e);
		} catch (TmngCmsException.DocumentDoesNotExistException e) {
			sendResponse(webScriptResponse, HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (TmngCmsException.CMSWebScriptException e) {
			if (null != webScriptResponse) {
				sendResponse(webScriptResponse, e.getStatusCode(), e.getMessage(), e);
			}
		} catch (BaseRuntimeException e) {
			sendResponse(webScriptResponse, e.getStatusCode(), e.getMessage(), e);
		}
	}

	/**
	 * This method is used to execute the web script specific functionality.
	 *
	 * @param webScriptRequest
	 *            the web script request
	 * @param webScriptResponse
	 *            the web script response
	 */
	protected abstract void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webScriptResponse);

	/**
	 * This method validates the request parameters.
	 *
	 * @param webScriptRequest
	 *            the web script request
	 */
	public void validateRequest(WebScriptRequest webScriptRequest) {
		String serialNumber = WebScriptHelper.getUrlParameters(webScriptRequest).get(
				AbstractCmsCommonWebScript.SERIAL_NUMBER_PATH_PARAMETER);
		String idParam = WebScriptHelper.getUrlParameters(webScriptRequest).get(ID_PARAMETER);
		HttpServletRequest hsr = WebScriptServletRuntime.getHttpServletRequest(webScriptRequest);
		boolean evidenceLibraryUrl = hsr.getRequestURL().toString()
				.contains(AbstractCmsCommonWebScript.EVIDENCE_LIBRARY_URL_SIGNATURE);
		if (StringUtils.isBlank(serialNumber) && StringUtils.isBlank(idParam) && !evidenceLibraryUrl) {
			throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
					"Serial Number and TrademarkId both found blank");
		}

		String id = null;
		if (StringUtils.isNotBlank(serialNumber)) {
			id = serialNumber;
		} else if (StringUtils.isNotBlank(idParam)) {
			id = idParam;
		}
		if ((id != null) && WebScriptHelper.isBadCaseSerialNumber(id)) {
			// move error messages to a property file
			throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
					"Please verify incoming serialNumber: '" + ID_PARAMETER
							+ "', it has to match, greater than or equal to eight character alpha numeric pattern.");
		}
	}

	/**
	 * Check serial number.
	 *
	 * @param serialNumber
	 *            the serial number
	 */
	public static void checkSerialNumber(String serialNumber) {
		if (WebScriptHelper.isBadCaseSerialNumber(serialNumber)) {
			throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
					"Please verify incoming serialNumber: '" + serialNumber
							+ "', it has to match, greater than or equal to eight character alpha numeric pattern.");
		}
	}

	/**
	 * Returns the request body as a String.
	 *
	 * @param webScriptRequest
	 *            the web script request
	 * @return the content as string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected String getContentAsString(WebScriptRequest webScriptRequest) {
		try {
			return webScriptRequest.getContent().getContent();
		} catch (IOException e) {
			throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
					"Couldn't read incoming content stream.", e);
		}
	}

	/**
	 * @param webScriptResponse
	 *            the web script response
	 * @param httpstatus
	 *            the httpstatus
	 * @param errorMessage
	 *            the error message
	 * @param e
	 *            the e
	 * @return void
	 * @Title: sendResponse
	 * @Description:
	 */
	protected void sendResponse(WebScriptResponse webScriptResponse, HttpStatus httpstatus, String errorMessage,
			Exception e) {
		if (null != httpstatus) {
			webScriptResponse.setStatus(httpstatus.value());
		} else {
			webScriptResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		}

		webScriptResponse.setContentType(TMConstants.TEXT_CONTENT);
		try {
			log.info("TMNG Exception message:: " + errorMessage);
			if (log.isDebugEnabled()) {
				log.debug("TMNG Exception message:: " + errorMessage, e);
			}
			webScriptResponse.getOutputStream().write(WebScriptHelper.toJson(errorMessage));
		} catch (IOException e1) {
			if (log.isDebugEnabled()) {
				log.debug(e1.getMessage(), e1);
			}
			throw new WebScriptException(org.springframework.http.HttpStatus.BAD_REQUEST.value(), e.getMessage(), e);
		}
	}

}
