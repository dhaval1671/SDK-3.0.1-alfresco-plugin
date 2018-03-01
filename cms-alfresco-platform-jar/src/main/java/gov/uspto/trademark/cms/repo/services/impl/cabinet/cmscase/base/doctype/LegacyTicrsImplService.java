package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.nodedelete.DocumentHardDelete;
import gov.uspto.trademark.cms.repo.nodelocator.LegacyTicrsDocumentNodeLocator;
import gov.uspto.trademark.cms.repo.services.LegacyTicrsService;
import gov.uspto.trademark.cms.repo.services.impl.AbstractBaseService;

/**
 * Created by stank on 5/3/2017.
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
@Component("LegacyTicrsImplService")
public class LegacyTicrsImplService extends AbstractBaseService implements LegacyTicrsService {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[TMConstants.ONE].getClassName());

    @Autowired
    @Qualifier(value = "legacyTicrsDocumentNodeLocator")
    private LegacyTicrsDocumentNodeLocator legacyTicrsDocumentNodeLocator;

    @Autowired
    @Qualifier("DocumentHardDelete")
    private DocumentHardDelete documentHardDelete;

    @Override
    public ContentReader retrieveLegacyTicrsDocContent(String docType, String versionFolder, String fileName) {
        log.info("### Executing " + Thread.currentThread().getStackTrace()[TMConstants.ONE].getMethodName() + " ####");
        NodeRef nr = legacyTicrsDocumentNodeLocator.locateLegacyTicrsXslDocumentNode(docType, versionFolder, fileName);
        ContentReader contentReader = null;
        if (nr != null) {
            contentReader = serviceRegistry.getContentService().getReader(nr, ContentModel.PROP_CONTENT);
        }        
        return contentReader;
    }    

}
