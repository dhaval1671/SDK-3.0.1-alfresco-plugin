package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EvidenceDocServiceTest {

    public static boolean debug = false;

    @Test
    public void test01() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        ContentItem contentItem5 = null;
        Map<String, Serializable> map_str_serializable6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.create("hi!", "hi!", contentItem5, map_str_serializable6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test02() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.ticrsAdminDelete("hi!", "hi!", "hi!");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test03() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        ContentItem contentItem5 = null;
        Map<String, Serializable> map_str_serializable6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.update("", "", contentItem5, map_str_serializable6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test04() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.retrieveMetadata("", "hi!", "");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test05() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.retrieveMetadata("", "", "", cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test06() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        Map<String, Serializable> map_str_serializable5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.updateMetadata("hi!", "", map_str_serializable5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test07() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.retrieveContent("", "", "hi!");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test08() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.retrieveVersionsList("", "hi!", qName5,
                    cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test09() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        Map<String, Serializable> map_str_serializable5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.updateMetadata("hi!", "hi!", map_str_serializable5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test10() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.ticrsAdminDelete("", "hi!", "");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test11() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.retrieveMetadata("", "", "hi!");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test12() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.retrieveMetadata("hi!", "hi!", "");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test13() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        CmsNodeLocator cmsNodeLocator3 = null;
        QName qName4 = null;
        CmsDataFilter cmsDataFilter8 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.retrieveContent(cmsNodeLocator3, qName4, "hi!", "hi!", "",
                    cmsDataFilter8);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test14() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        NodeRef nodeRef3 = null;
        NodeRef nodeRef4 = null;
        QName qName5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.removeAssociation(nodeRef3, nodeRef4, qName5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test15() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.hardDeleteDocument("hi!", "", qName5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test16() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        ContentItem contentItem5 = null;
        Map<String, Serializable> map_str_serializable6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.update("", "hi!", contentItem5, map_str_serializable6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test17() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.retrieveVersionsList("hi!", "", qName5,
                    cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test18() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceDocService2.hardDeleteDocument("hi!", "hi!", qName5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test19() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        EvidenceDocService evidenceDocService2 = new EvidenceDocService(cmsNodeCreator0, cmsNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = evidenceDocService2.delete("", "");

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

    }

}
