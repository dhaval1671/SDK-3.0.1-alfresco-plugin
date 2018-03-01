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
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * Created by bgummadi on 3/31/2014.
 */
public class CmsValidator {

    /**
     * Instantiates a new web script helper.
     */
    private CmsValidator() {

    }

    /**
     * Parses the json.
     *
     * @param jsonString
     *            the json string
     * @return the map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Serializable> parseJsonReturningMapWithCaseSensitiveKeys(String jsonString) {
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
        if (clientProperties != null) {
            String lgcyCat = (String) clientProperties.get(TradeMarkModel.LEGACY_CATEGORY);
            String docCode = (String) clientProperties.get(TradeMarkModel.DOC_CODE);
            if ((lgcyCat != null) || (docCode != null)) {
                String migrationMethod = (String) clientProperties.get(TradeMarkModel.MIGRATION_METHOD);
                if (migrationMethod == null) {
                    throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                            "User trying to migrate doc type without '" + TradeMarkModel.MIGRATION_METHOD + "'.");
                }
            }
        }
    }

}