package gov.uspto.trademark.cms.repo.filters;

import java.io.Serializable;
import java.util.Map;

/**
 * This interface is used to filter the documents based on the filter
 * attributes.
 * 
 * @author vnondapaka
 *
 */
public interface CmsDataFilter {

    /**
     * This method is used to filter from the given hashmap.
     * 
     * @param hmMetadata
     * @return
     */
    boolean filter(Map<String, Serializable> hmMetadata);

    /**
     * Filter the documents based on access level.
     * 
     * @param accessLevel
     * @return
     */
    boolean filter(String accessLevel);

}
