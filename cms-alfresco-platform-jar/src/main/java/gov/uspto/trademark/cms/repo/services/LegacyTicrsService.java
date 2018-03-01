package gov.uspto.trademark.cms.repo.services;

import org.alfresco.service.cmr.repository.ContentReader;
import org.springframework.stereotype.Component;

/**
 * Created by stank on May/19/2017
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
@Component
public interface LegacyTicrsService extends BaseService {
    
    ContentReader retrieveLegacyTicrsDocContent(String docType, String versionFolder, String fileName);


}
