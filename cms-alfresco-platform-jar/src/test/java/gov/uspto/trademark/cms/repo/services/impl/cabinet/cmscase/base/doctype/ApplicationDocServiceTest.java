package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import gov.uspto.trademark.cms.repo.TmngCmsException.CMSRuntimeException;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationDocServiceTest {

    @Test
    public void test01() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        ContentItem contentItem5 = null;
        Map<String, Serializable> map_str_serializable6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            TmngAlfResponse tmngAlfResponse7 = applicationDocService2.create("hi!", "hi!", contentItem5, map_str_serializable6);
            Assert.assertNull(tmngAlfResponse7);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test02() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            TmngAlfResponse tmngAlfResponse6 = applicationDocService2.ticrsAdminDelete("hi!", "hi!", "hi!");
            Assert.assertNull(tmngAlfResponse6);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test03() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        ContentItem contentItem5 = null;
        Map<String, Serializable> map_str_serializable6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            TmngAlfResponse tmngAlfResponse7 = applicationDocService2.update("", "", contentItem5, map_str_serializable6);
            Assert.assertNull(tmngAlfResponse7);
        } catch (CMSRuntimeException e) {
            // Expected exception.
        }

    }

    @Test
    public void test04() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            byte[] byte_array6 = applicationDocService2.retrieveMetadata("", "hi!", "");
            Assert.assertNull(byte_array6);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test05() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            byte[] byte_array7 = applicationDocService2.retrieveMetadata("", "", "", cmsDataFilter6);
            Assert.assertNull(byte_array7);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test06() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        Map<String, Serializable> map_str_serializable5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            TmngAlfResponse tmngAlfResponse6 = applicationDocService2.updateMetadata("hi!", "", map_str_serializable5);
            Assert.assertNull(tmngAlfResponse6);
        } catch (CMSRuntimeException e) {
            // Expected exception.
        }

    }

    @Test
    public void test07() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            ContentReader contentReader6 = applicationDocService2.retrieveContent("", "", "hi!");
            Assert.assertNull(contentReader6);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test08() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            List<? extends TmngAlfResponse> list_wildcard7 = applicationDocService2.retrieveVersionsList("", "hi!", qName5,
                    cmsDataFilter6);
            Assert.assertNull(list_wildcard7);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test09() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        Map<String, Serializable> map_str_serializable5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            TmngAlfResponse tmngAlfResponse6 = applicationDocService2.updateMetadata("hi!", "hi!", map_str_serializable5);
            Assert.assertNull(tmngAlfResponse6);
        } catch (CMSRuntimeException e) {
            // Expected exception.
        }

    }

    @Test
    public void test10() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            TmngAlfResponse tmngAlfResponse6 = applicationDocService2.ticrsAdminDelete("", "hi!", "");
            Assert.assertNull(tmngAlfResponse6);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test11() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            byte[] byte_array6 = applicationDocService2.retrieveMetadata("", "", "hi!");
            Assert.assertNull(byte_array6);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test12() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            byte[] byte_array6 = applicationDocService2.retrieveMetadata("hi!", "hi!", "");
            Assert.assertNull(byte_array6);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test13() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        CmsNodeLocator cmsNodeLocator3 = null;
        QName qName4 = null;
        CmsDataFilter cmsDataFilter8 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            ContentReader contentReader9 = applicationDocService2.retrieveContent(cmsNodeLocator3, qName4, "hi!", "hi!", "", cmsDataFilter8);
            Assert.assertNull(contentReader9);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test14() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        NodeRef nodeRef3 = null;
        NodeRef nodeRef4 = null;
        QName qName5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            boolean b6 = applicationDocService2.removeAssociation(nodeRef3, nodeRef4, qName5);
            Assert.assertNull(b6);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test15() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            TmngAlfResponse tmngAlfResponse6 = applicationDocService2.hardDeleteDocument("hi!", "", qName5);
            Assert.assertNull(tmngAlfResponse6);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test16() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        ContentItem contentItem5 = null;
        Map<String, Serializable> map_str_serializable6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            TmngAlfResponse tmngAlfResponse7 = applicationDocService2.update("", "hi!", contentItem5, map_str_serializable6);
            Assert.assertNull(tmngAlfResponse7);
        } catch (CMSRuntimeException e) {
            // Expected exception.
        }

    }

    @Test
    public void test17() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            List<? extends TmngAlfResponse> list_wildcard7 = applicationDocService2.retrieveVersionsList("hi!", "", qName5,
                    cmsDataFilter6);
            Assert.assertNull(list_wildcard7);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test18() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            TmngAlfResponse tmngAlfResponse6 = applicationDocService2.hardDeleteDocument("hi!", "hi!", qName5);
            Assert.assertNull(tmngAlfResponse6);
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test19() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        ApplicationDocService applicationDocService2 = new ApplicationDocService(cmsNodeCreator0, cmsNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = applicationDocService2.delete("", "");

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

    }

}
