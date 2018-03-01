package gov.uspto.trademark.cms.repo.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * Created by bgummadi on 3/31/2014.
 */
public class WebScriptHelper {

    /** The Constant EOG_DATE_FORMAT. */
    public static final String EOG_DATE_FORMAT = "yyyyMMdd";

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(WebScriptHelper.class);

    /** The Constant CASE_SERIAL_NUMBER_PATTERN. */
    public static final Pattern CASE_SERIAL_NUMBER_PATTERN = Pattern.compile("^[a-zA-Z0-9]{8,}$");

    /** The Constant SINGLE_QUOTE. */
    private static final String SINGLE_QUOTE = "\'";

    /** The Constant DOUBLE_QUOTE. */
    private static final String DOUBLE_QUOTE = "\"";

    /**
     * Instantiates a new web script helper.
     */
    private WebScriptHelper() {

    }

    /**
     * Parses the json.
     *
     * @param jsonString
     *            the json string
     * @return the map
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Serializable> parseJson(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, Map.class);
    }

    /**
     * Parses the json.
     *
     * @param src
     *            the src
     * @return the map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Serializable> parseJson(byte[] src) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Serializable> map = null;
        try {
            map = mapper.readValue(src, Map.class);
        } catch (JsonParseException e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage(), e);
            }
        } catch (JsonMappingException e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage(), e);
            }
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage(), e);
            }
        }
        return map;
    }

    /**
     * Parses the json.
     *
     * @param <T>
     *            the generic type
     * @param repositoryProperties
     *            the repository properties
     * @param target
     *            the target
     * @return the t
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static <T> T parseJson(Map<String, Serializable> repositoryProperties, Class<T> target) throws IOException {
        T t = null;
        ObjectMapper mapper = new ObjectMapper();
        t = mapper.readValue(mapper.writeValueAsBytes(repositoryProperties), target);
        return t;
    }

    /**
     * To json.
     *
     * @param object
     *            the object
     * @return the byte[]
     */
    public static byte[] toJson(Object object) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);
        try {
            mapper.writeValue(out, object);
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage(), e);
            }
        }
        return out.toByteArray();
    }

    /**
     * To json as string.
     *
     * @param object
     *            the object
     * @return the string
     */
    public static String toJsonAsString(Object object) {
        String outputString = null;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(out, object);
            outputString = out.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage(), e);
            }
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage(), e);
            }
        }
        return outputString;
    }

    /**
     * Checks if is bad case serial number.
     *
     * @param value
     *            the value
     * @return true, if is bad case serial number
     */
    public static boolean isBadCaseSerialNumber(String value) {
        boolean flag = false;

        if (value == null) {
            flag = true;
        } else if (StringUtils.isBlank(value)) {
            flag = true;
        } else if (!(CASE_SERIAL_NUMBER_PATTERN.matcher(value.trim()).matches())) {
            flag = true;
        }
        return flag;
    }

    /**
     * Gets the request parameters as map.
     *
     * @param req
     *            the req
     * @return the request parameters as map
     */
    public static Map<String, String> getRequestParametersAsMap(WebScriptRequest req) {
        Map<String, String> params = new HashMap<String, String>();
        for (String name : req.getParameterNames()) {
            params.put(name, req.getParameter(name));
        }
        return params;
    }

    /**
     * Stringify map.
     *
     * @param properties
     *            the properties
     * @return the map
     */
    public static Map<String, Serializable> stringifyMap(Map<QName, Serializable> properties) {
        Map<String, Serializable> convertedMap = new HashMap<String, Serializable>();
        for (Entry<QName, Serializable> s : properties.entrySet()) {
            convertedMap.put(s.getKey().getLocalName(), s.getValue());
        }
        if (convertedMap.get("content") != null) {
            convertedMap.put("content", convertedMap.get("content").toString());
        }
        return convertedMap;
    }

    /**
     * Generate alf repo props fr dto.
     *
     * @param obj
     *            the obj
     * @return the map
     */
    @SuppressWarnings("unchecked")
    public static Map<QName, Serializable> generateAlfRepoPropsFrDTO(Object obj) {
        ObjectMapper mapper = JacksonHelper.incomingMapperDtoToAlfRepo();
        Map<QName, Serializable> repositoryMap = new HashMap<QName, Serializable>();
        if (obj != null) {
            Map<String, Serializable> repoPropMap = mapper.convertValue(obj, Map.class);
            for (Entry<String, Serializable> s : repoPropMap.entrySet()) {

                QName qn = null;
                if (TMConstants.VERSION_LABEL.equals(s.getKey())) {
                    qn = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, s.getKey());
                } else {
                    qn = QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI, s.getKey());
                }

                repositoryMap.put(qn, repoPropMap.get(s.getKey()));
            }
        }
        return repositoryMap;
    }

    /**
     * Gets the redacted file name.
     *
     * @param documentName
     *            the document name
     * @return the redacted file name
     */
    public static String getRedactedFileName(String documentName) {
        return FilenameUtils.getBaseName(documentName) + "-redacted." + FilenameUtils.getExtension(documentName);
    }

    /**
     * Determines if this is a quoted argument - either single or double quoted.
     *
     * @param argument
     *            the argument to check
     * @return true when the argument is quoted
     */
    public static boolean isQuoted(final String argument) {
        return argument.startsWith(SINGLE_QUOTE) && argument.endsWith(SINGLE_QUOTE)
                || argument.startsWith(DOUBLE_QUOTE) && argument.endsWith(DOUBLE_QUOTE);
    }

    /**
     * Same method as above but using the ?: syntax which is shorter. You can
     * use whichever you prefer. This String util method removes single or
     * double quotes from a string if its quoted. for input string = "mystr1"
     * output will be = mystr1 for input
     * 
     * string = 'mystr2' output will be = mystr2
     *
     * @param s
     *            the s
     * @return value unquoted, null if input is null.
     */
    public static String unquote(String s) {
        if (s != null && ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'")))) {
            s = s.substring(TMConstants.ONE, s.length() - TMConstants.ONE);
        }
        return s;
    }

    /**
     * Gets the flavour of metadata uri.
     *
     * @param s
     *            the s
     * @return the flavour of metadata uri
     */
    public static String getFlavourOfMetadataURI(String s) {
        String[] separated = s.split("/");
        return separated[separated.length - TMConstants.TWO];
    }

    /**
     * Gets the flavour of content uri.
     *
     * @param s
     *            the s
     * @return the flavour of content uri
     */
    public static String getFlavourOfContentURI(String s) {
        String[] separated = s.split("/");
        return separated[separated.length - TMConstants.ONE];
    }

    /**
     * Gets the template variable substitutions map.
     *
     * @param req
     *            The webscript request
     * @return The template variable substitutions
     */
    public static Map<String, String> getUrlParameters(WebScriptRequest req) {
        if (req.getServiceMatch() == null) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                    "The matching API Service for the request is null.");
        }

        Map<String, String> templateVars = req.getServiceMatch().getTemplateVars();
        if (templateVars == null) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                    "The template variable substitutions map is null");
        }

        return templateVars;
    }

    /**
     * Convert an input stream to a byte array.
     *
     * @param inputStream
     *            the input stream
     * @return byte array
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static byte[] convertToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FileCopyUtils.copy(inputStream, out);
        return out.toByteArray();
    }

    /**
     * Checks if is valid eog date.
     *
     * @param eogWeeklyFolderDate
     *            the eog weekly folder date
     * @return true, if is valid eog date
     */
    public static boolean isValidEogDate(String eogWeeklyFolderDate) {
        boolean valid = true;
        DateFormat formatter = new SimpleDateFormat(WebScriptHelper.EOG_DATE_FORMAT);
        formatter.setLenient(false);
        try {
            formatter.parse(eogWeeklyFolderDate);
        } catch (ParseException e) {
            // If input date is in different format or invalid.
            valid = false;
        }
        return valid;
    }

}