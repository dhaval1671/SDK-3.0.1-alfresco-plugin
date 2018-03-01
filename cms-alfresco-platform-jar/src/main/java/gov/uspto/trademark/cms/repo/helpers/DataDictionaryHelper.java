package gov.uspto.trademark.cms.repo.helpers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.constants.TradeMarkProperties;

/**
 * Data dictionary helper. Provides helper functions for data-type
 * transformations
 *
 * Created by bgummadi on 3/31/2014.
 */
public class DataDictionaryHelper {

    /** The Constant LOG. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DataDictionaryHelper.class);

    /**
     * Convert parameters to properties.
     *
     * @param params
     *            the params
     * @return the map
     */
    public static Map<QName, Serializable> convertParametersToProperties(Map<String, String> params) {
        Map<QName, Serializable> properties = new HashMap<QName, Serializable>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            properties.put(getTradeMarkPropertyQName(entry.getKey()), entry.getValue());
        }
        return properties;
    }

    /**
     * Gets the trade mark property q name.
     *
     * @param propertyName
     *            the property name
     * @return the trade mark property q name
     */
    public static QName getTradeMarkPropertyQName(String propertyName) {
        QName propertyQName;
        try {
            propertyQName = QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI,
                    TradeMarkProperties.getTradeMarkType(propertyName).getPropertyName());
        } catch (Exception e) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(e.getMessage(), e);
            }
            throw new TmngCmsException.PropertyNotFoundException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return propertyQName;
    }

}