package gov.uspto.trademark.cms.repo.filters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import gov.uspto.trademark.cms.repo.constants.TMConstants;

public class DocumentMetadataFilter implements CmsDataFilter {

    private static final String ABSENT_DIRECTIVE = "_ABSENT_";
    private static final String VERSION = "version";
    private static final String DOCUMENT_NAME = "documentName";
    private static final String MIGRATION_METHOD = "migrationMethod";
    private static final String MIGRATION_SOURCE = "migrationSource";
    private static final String IDENTIFIER = "identifier";
    private static final String DOC_CODE = "docCode";
    private static final String LEGACY_CATEGORY = "legacyCategory";
    private final String filterKey;
    private final String filterValue;
    private CmsDataFilter docMetadataFilter;
    private static final Map<String, List<String>> map = createMap();

    public DocumentMetadataFilter(String metadataName, String metadataValue, String type) {

        if (("LP".equals(type) && map.get(type).contains(metadataName))
                || ("CASE".equals(type) && map.get(type).contains(metadataName))) {
            String metaDataName = parseIncomingKeyToBackendAlfrescoDTOKey(metadataName);
            this.filterKey = metaDataName;
            this.filterValue = metadataValue;
        } else {
            this.filterKey = null;
            this.filterValue = null;
        }
    }

    public DocumentMetadataFilter(String metadataName, String metadataValue, CmsDataFilter dataFilter) {
        String metaDataName = parseIncomingKeyToBackendAlfrescoDTOKey(metadataName);
        this.filterKey = metaDataName;
        this.filterValue = metadataValue;
        this.docMetadataFilter = dataFilter;
    }

    private static Map<String, List<String>> createMap() {
        // create map to store
        Map<String, List<String>> map = new HashMap<String, List<String>>();

        // create list one and store values
        List<String> lplist = new ArrayList<String>();
        lplist.add(VERSION);
        lplist.add(DOCUMENT_NAME);
        lplist.add(MIGRATION_METHOD);
        lplist.add(MIGRATION_SOURCE);
        lplist.add(IDENTIFIER);
        // create list two and store values
        List<String> caseList = new ArrayList<String>();
        caseList.add(VERSION);
        caseList.add(DOCUMENT_NAME);
        caseList.add(MIGRATION_METHOD);
        caseList.add(MIGRATION_SOURCE);
        caseList.add(DOC_CODE);
        caseList.add(LEGACY_CATEGORY);

        // put values into map
        map.put("LP", lplist);
        map.put("CASE", caseList);

        return map;
    }

    private String parseIncomingKeyToBackendAlfrescoDTOKey(String metadataName) {
        String backendDTOKey = metadataName;
        if ("version".equals(metadataName)) {
            backendDTOKey = TMConstants.VERSION_LABEL;
        }
        return backendDTOKey;
    }

    @Override
    public boolean filter(Map<String, Serializable> hmMetadata) {
        boolean blnFiltered = false;
        String propertyValue = null;
        Serializable sObj = hmMetadata.get(filterKey);

        if (filterKey != null) {
            if (sObj instanceof String) {
                propertyValue = (String) sObj;
            } else if (sObj instanceof Integer || sObj instanceof Long) {
                propertyValue = sObj.toString();
            }
            if (filterValue != null && (isAbsent(filterValue, propertyValue) || notAbsent(filterValue, propertyValue))) {
                blnFiltered = true;
            }
            if (docMetadataFilter != null && !blnFiltered) {
                blnFiltered = docMetadataFilter.filter(hmMetadata);
            }
        }
        return blnFiltered;
    }

    private boolean notAbsent(String filterValue2, String propertyValue) {

        return (!filterValue2.equals(ABSENT_DIRECTIVE) && propertyValue == null)
                || (!filterValue2.equals(ABSENT_DIRECTIVE) && !propertyValue.equalsIgnoreCase(filterValue2));

    }

    private boolean isAbsent(String filterValue2, String propertyValue) {
        return filterValue2.equals(ABSENT_DIRECTIVE) && StringUtils.isNotEmpty(propertyValue);

    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.filters.CmsDataFilter#filter(java.lang.
     * String )
     */
    @Override
    public boolean filter(String accessLevel) {
        throw new UnsupportedOperationException();
    }
}
