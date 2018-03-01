package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.namespace.QName;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegistrationCertificateDocServiceTest {

    public static boolean debug = false;

    @Test
    public void test01() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.retrieveContent("", "", "hi!", cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test02() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        Map<String, Serializable> map_str_serializable5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.updateMetadata("hi!", "",
                    map_str_serializable5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test03() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.retrieveContent("", "hi!", "");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test04() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        Map<String, Serializable> map_str_serializable5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.updateMetadata("", "hi!",
                    map_str_serializable5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test05() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.retrieveMetadata("hi!", "hi!", "hi!", cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test06() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.retrieveMetadata("", "hi!", "hi!", cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test07() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.delete("hi!", "");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test08() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        ContentItem contentItem5 = null;
        Map<String, Serializable> map_str_serializable6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.create("", "", contentItem5, map_str_serializable6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test09() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        CmsNodeLocator cmsNodeLocator3 = null;
        QName qName4 = null;
        CmsDataFilter cmsDataFilter8 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.retrieveContent(cmsNodeLocator3, qName4, "hi!",
                    "hi!", "", cmsDataFilter8);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test10() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.retrieveVersionsList("", "hi!",
                    qName5, cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test11() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        Map<String, Serializable> map_str_serializable5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.updateMetadata("hi!", "hi!",
                    map_str_serializable5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test12() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        QName qName5 = null;
        CmsDataFilter cmsDataFilter6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.retrieveVersionsList("hi!", "hi!",
                    qName5, cmsDataFilter6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test13() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.delete("", "hi!");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test14() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.retrieveContent("", "", "");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test15() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.retrieveMetadata("", "hi!", "hi!");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test16() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        CmsNodeLocator cmsNodeLocator3 = null;
        QName qName4 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.retrieveContent(cmsNodeLocator3, qName4, "", "hi!",
                    "hi!");
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test17() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        ContentItem contentItem5 = null;
        Map<String, Serializable> map_str_serializable6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.update("hi!", "", contentItem5, map_str_serializable6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test18() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        ContentItem contentItem5 = null;
        Map<String, Serializable> map_str_serializable6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.update("hi!", "hi!", contentItem5, map_str_serializable6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test19() throws Throwable {

        CmsNodeCreator cmsNodeCreator0 = null;
        CmsNodeLocator cmsNodeLocator1 = null;
        RegistrationCertificateDocService registrationCertificateDocService2 = new RegistrationCertificateDocService(
                cmsNodeCreator0, cmsNodeLocator1);
        ContentItem contentItem5 = null;
        Map<String, Serializable> map_str_serializable6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            registrationCertificateDocService2.create("hi!", "", contentItem5, map_str_serializable6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

}
