package gov.uspto.trademark.cms.repo.filters;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.uspto.trademark.cms.repo.constants.AccessLevel;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * Access Level Filter which filters out data based on the filter
 */
public class AccessLevelFilter implements CmsDataFilter {

    private final String accessLevel;
    private CmsDataFilter chainedFilter;

    public AccessLevelFilter(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public AccessLevelFilter(String accessLevel, CmsDataFilter chainedFilter) {
        this.accessLevel = accessLevel;
        this.chainedFilter = chainedFilter;
    }

    @Override
    public boolean filter(Map<String, Serializable> hmMetadata) {
        boolean filtered = false;
        String accessLevelPropertyValue = (String) hmMetadata.get(TradeMarkModel.ACCESS_LEVEL);
        if (accessLevelPropertyValue != null) {
            accessLevelPropertyValue = accessLevelPropertyValue.toLowerCase();
        }
        List<String> accessLevels = AccessLevel.getAccessLevelHierarchy(accessLevel);
        if (!accessLevels.contains(accessLevelPropertyValue)) {
            filtered = true;
        }

        if (!filtered && chainedFilter != null) {
            filtered = chainedFilter.filter(hmMetadata);
        }

        return filtered;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.filters.CmsDataFilter#filter(java.lang.
     * String )
     */
    @Override
    public boolean filter(String accessLevel) {
        Map<String, Serializable> hmMetadata = new HashMap<String, Serializable>();
        hmMetadata.put(TradeMarkModel.ACCESS_LEVEL_QNAME.getLocalName(), accessLevel);
        return filter(hmMetadata);
    }
}
