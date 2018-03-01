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
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MarkDocServiceTest {

    public static boolean debug = false;

    @Test
    public void test01() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.retrieveVersionsList("hi!", "", qName5,
                    cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test02() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.retrieveVersionsList("", "", qName5, cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test03() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.retrieveMetadata("hi!", "", "hi!", cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test04() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.retrieveMetadata("", "", "hi!", cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test05() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = markDocService2.delete("", "");
        CmsDataFilter cmsDataFilter9 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.retrieveContent("", "hi!", "hi!", cmsDataFilter9);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

    }

    @Test
    public void test06() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.hardDeleteDocument("", "", qName5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test07() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        String str5 = markDocService2.getMarkFileNameWithDataModFix("", "hi!");
        QName qName8 = null;
        CmsDataFilter cmsDataFilter9 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.retrieveVersionsList("hi!", "USPTO-IMAGE-MARK.",
                    qName8, cmsDataFilter9);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertTrue("'" + str5 + "' != '" + "USPTO-IMAGE-MARK." + "'", str5.equals("USPTO-IMAGE-MARK."));

    }

    @Test
    public void test08() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        String str5 = markDocService2.getMarkFileNameWithDataModFix("", "hi!");
        CmsDataFilter cmsDataFilter9 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.retrieveContent("USPTO-IMAGE-MARK.", "", "USPTO-IMAGE-MARK.",
                    cmsDataFilter9);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertTrue("'" + str5 + "' != '" + "USPTO-IMAGE-MARK." + "'", str5.equals("USPTO-IMAGE-MARK."));

    }

    @Test
    public void test09() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = markDocService2.delete("", "");
        CmsNodeLocator cmsNodeLocator6 = null;
        QName qName7 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.retrieveContent(cmsNodeLocator6, qName7, "", "hi!", "");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

    }

    @Test
    public void test10() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        String str5 = markDocService2.getMarkFileNameWithDataModFix("", "hi!");
        TmngAlfResponse tmngAlfResponse8 = markDocService2.delete("hi!", "");

        // Regression assertion (captures the current behavior of the code)
        Assert.assertTrue("'" + str5 + "' != '" + "USPTO-IMAGE-MARK." + "'", str5.equals("USPTO-IMAGE-MARK."));

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse8);

    }

    @Test
    public void test11() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        Map<String, Serializable> map_str_serializable5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.updateMetadata("hi!", "USPTO-IMAGE-MARK.", map_str_serializable5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test12() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = markDocService2.delete("", "");
        String str8 = markDocService2.getMarkFileNameWithDataModFix("", "");
        CmsNodeLocator cmsNodeLocator9 = null;
        QName qName10 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.retrieveContent(cmsNodeLocator9, qName10, "USPTO-IMAGE-MARK.", "", "USPTO-IMAGE-MARK.");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertTrue("'" + str8 + "' != '" + "USPTO-IMAGE-MARK." + "'", str8.equals("USPTO-IMAGE-MARK."));

    }

    @Test
    public void test13() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        String str5 = markDocService2.getMarkFileNameWithDataModFix("", "hi!");
        CmsNodeLocator cmsNodeLocator6 = null;
        QName qName7 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.retrieveContent(cmsNodeLocator6, qName7, "", "hi!", "");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertTrue("'" + str5 + "' != '" + "USPTO-IMAGE-MARK." + "'", str5.equals("USPTO-IMAGE-MARK."));

    }

    @Test
    public void test14() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        String str5 = markDocService2.getMarkFileNameWithDataModFix("", "hi!");
        QName qName8 = null;
        CmsDataFilter cmsDataFilter9 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.retrieveVersionsList("", "USPTO-IMAGE-MARK.",
                    qName8, cmsDataFilter9);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertTrue("'" + str5 + "' != '" + "USPTO-IMAGE-MARK." + "'", str5.equals("USPTO-IMAGE-MARK."));

    }

    @Test
    public void test15() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = markDocService2.delete("", "");
        NodeRef nodeRef6 = null;
        NodeRef nodeRef7 = null;
        QName qName8 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.removeAssociation(nodeRef6, nodeRef7, qName8);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

    }

    @Test
    public void test16() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        MarkDocService markDocService2 = new MarkDocService(cmsNodeCreator0, cmsNodeLocator1);
        TmngAlfResponse tmngAlfResponse5 = markDocService2.delete("", "");
        String str8 = markDocService2.getMarkFileNameWithDataModFix("", "");
        Map<String, Serializable> map_str_serializable11 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markDocService2.updateMetadata("hi!", "", map_str_serializable11);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(tmngAlfResponse5);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertTrue("'" + str8 + "' != '" + "USPTO-IMAGE-MARK." + "'", str8.equals("USPTO-IMAGE-MARK."));

    }

}
