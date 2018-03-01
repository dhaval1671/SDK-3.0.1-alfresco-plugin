package gov.uspto.trademark.cms.repo.helpers;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;

/**
 * Created by stank on Nov/16/2016.
 */
public class LegalProceedingValidator {

    /**
     * Instantiates a new web script helper.
     */
    private LegalProceedingValidator() {

    }

    /**
     * Parses the json.
     *
     * @param jsonString
     *            the json string
     * @return the map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Serializable> parseJson(String jsonString) {
        if (StringUtils.isNotBlank(jsonString)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(jsonString, Map.class);
            } catch (JsonParseException e) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            } catch (JsonMappingException e) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            } catch (IOException e) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
        } else {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Incoming metadata should NOT be blank.");
        }

    }

    /**
     * Validate properties.
     *
     * @param clientProperties
     *            the client properties
     * @return void
     * @Title: runChecksWhileCreatingFromNGDomain
     * @Description:
     */
    public static void validateProperties(Map<String, Serializable> clientProperties) {
        // So far(as of Nov/16/2016) nothing identified for validation.
    }

}