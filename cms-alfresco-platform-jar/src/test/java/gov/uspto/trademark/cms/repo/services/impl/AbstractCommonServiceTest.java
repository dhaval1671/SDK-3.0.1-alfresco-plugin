package gov.uspto.trademark.cms.repo.services.impl;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype.BaseServiceSetup;

public class AbstractCommonServiceTest extends BaseServiceSetup{
    @Autowired
    @Qualifier(value = "caseNodeLocator")
    CmsNodeLocator cmsNodeLocator;

    protected CmsNodeLocator cmsNodeLocator1;

    /** The cms node creator. */
    protected CmsNodeCreator cmsNodeCreator;

    @Test
    public void testAbstractCommonService() {
        cmsNodeLocator1 = cmsNodeLocator;
        assertNull(cmsNodeLocator);
        assertNull(cmsNodeLocator1);
    }

}
