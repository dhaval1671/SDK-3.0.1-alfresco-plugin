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
import gov.uspto.trademark.cms.repo.helpers.TeasPdfNodeCreator;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.nodelocator.TeasPdfNodeLocator;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeasPdfDocServiceTest {

    public static boolean debug = false;

    @Test
    public void test01() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        QName qName5 = null;
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.retrieveVersionsList("hi!", "", qName5,
                    cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test02() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        QName qName5 = null;
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.retrieveVersionsList("", "", qName5,
                    cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test03() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.retrieveMetadata("hi!", "", "hi!", cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test04() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.retrieveMetadata("", "", "hi!", cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test05() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = teasPdfDocService2.delete("", "");
        CmsDataFilter cmsDataFilter9 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.retrieveContent("", "hi!", "hi!", cmsDataFilter9);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

    }

    @Test
    public void test06() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        QName qName5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.hardDeleteDocument("", "", qName5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test07() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.retrieveAttachmentContent("", "hi!", "");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test08() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = teasPdfDocService2.delete("", "");
        CmsDataFilter cmsDataFilter9 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.retrieveContent("", "hi!", "", cmsDataFilter9);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

    }

    @Test
    public void test09() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = teasPdfDocService2.delete("", "");
        CmsNodeLocator cmsNodeLocator6 = null;
        QName qName7 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.retrieveContent(cmsNodeLocator6, qName7, "hi!", "hi!", "");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

    }

    @Test
    public void test10() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = teasPdfDocService2.delete("", "");
        TmngAlfResponse tmngAlfResponse8 = teasPdfDocService2.delete("", "");
        Map<String, Serializable> map_str_serializable11 = null;
        TmngAlfResponse tmngAlfResponse12 = teasPdfDocService2.updateMetadata("", "hi!", map_str_serializable11);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse8);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse12);

    }

    @Test
    public void test11() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = teasPdfDocService2.delete("", "");
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.retrieveAttachmentContent("hi!", "hi!", "");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

    }

    @Test
    public void test12() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = teasPdfDocService2.delete("", "");
        QName qName8 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.hardDeleteDocument("hi!", "hi!", qName8);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

    }

    @Test
    public void test13() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = teasPdfDocService2.delete("", "");
        CmsNodeLocator cmsNodeLocator6 = null;
        QName qName7 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.retrieveContent(cmsNodeLocator6, qName7, "", "hi!", "hi!");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

    }

    @Test
    public void test14() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        NodeRef nodeRef3 = null;
        NodeRef nodeRef4 = null;
        QName qName5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.removeAssociation(nodeRef3, nodeRef4, qName5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test15() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = teasPdfDocService2.delete("", "");
        TmngAlfResponse tmngAlfResponse8 = teasPdfDocService2.delete("", "");
        Map<String, Serializable> map_str_serializable11 = null;
        TmngAlfResponse tmngAlfResponse12 = teasPdfDocService2.updateMetadata("hi!", "", map_str_serializable11);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse8);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse12);

    }

    @Test
    public void test16() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.retrieveMetadata("", "hi!", "hi!");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test17() throws Throwable {

        TeasPdfNodeCreator teasPdfNodeCreator0 = null;
        TeasPdfNodeLocator teasPdfNodeLocator1 = null;
        TeasPdfDocService teasPdfDocService2 = new TeasPdfDocService(teasPdfNodeCreator0, teasPdfNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            teasPdfDocService2.retrieveAttachmentContent("hi!", "", "");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

}
