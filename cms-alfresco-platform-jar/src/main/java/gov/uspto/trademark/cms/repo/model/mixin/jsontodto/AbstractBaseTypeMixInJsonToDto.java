/**
 * 
 */
package gov.uspto.trademark.cms.repo.model.mixin.jsontodto;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author stank
 *
 */
public interface AbstractBaseTypeMixInJsonToDto {

    @JsonIgnore
    String getDocumentSize();

}
