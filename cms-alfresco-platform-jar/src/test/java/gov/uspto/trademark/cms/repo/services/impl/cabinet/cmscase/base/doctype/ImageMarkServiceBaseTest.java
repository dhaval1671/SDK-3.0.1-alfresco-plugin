package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import gov.uspto.trademark.cms.repo.services.VersionService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImageMarkServiceBaseTest {

    public static boolean debug = false;

    @Test
    public void test01() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        ServiceRegistry serviceRegistry1 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry1);
        ServiceRegistry serviceRegistry3 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry3);
        VersionService versionService5 = null;
        imageMarkServiceBase0.setVersionService(versionService5);
        QName[] qName_array7 = new QName[] {};
        ArrayList<QName> arraylist_qName8 = new ArrayList<QName>();
        boolean b9 = Collections.addAll(arraylist_qName8, qName_array7);
        String[] str_array11 = new String[] { "" };
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.fileExists(arraylist_qName8, str_array11);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNotNull(qName_array7);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertTrue(b9 == false);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNotNull(str_array11);

    }

    @Test
    public void test02() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        ServiceRegistry serviceRegistry1 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry1);
        ServiceRegistry serviceRegistry3 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry3);
        QName qName5 = null;
        InputStream inputStream8 = null;
        Map<QName, Serializable> map_qName_serializable9 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.upload(qName5, "", "hi!", inputStream8, map_qName_serializable9);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test03() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        NodeRef nodeRef1 = null;
        QName qName2 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.getAssociations(nodeRef1, qName2);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test04() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        ServiceRegistry serviceRegistry1 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry1);
        ServiceRegistry serviceRegistry3 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry3);
        VersionService versionService5 = null;
        imageMarkServiceBase0.setVersionService(versionService5);
        NodeRef nodeRef7 = null;
        QName qName8 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.hasAspect(nodeRef7, qName8);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test05() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        ServiceRegistry serviceRegistry1 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry1);
        NodeRef nodeRef3 = null;
        QName qName4 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.getContentReader(nodeRef3, qName4);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test06() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        ServiceRegistry serviceRegistry1 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry1);
        ServiceRegistry serviceRegistry3 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry3);
        NodeRef nodeRef5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.getNodeProperties(nodeRef5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test07() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        ServiceRegistry serviceRegistry1 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry1);
        ServiceRegistry serviceRegistry3 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry3);
        QName qName6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.getTradeMarkNodeRef("", qName6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test08() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        ServiceRegistry serviceRegistry1 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry1);
        ServiceRegistry serviceRegistry3 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry3);
        VersionService versionService5 = null;
        imageMarkServiceBase0.setVersionService(versionService5);
        NodeRef nodeRef7 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.getNodeProperties(nodeRef7);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test09() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        ServiceRegistry serviceRegistry1 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry1);
        ServiceRegistry serviceRegistry3 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry3);
        VersionService versionService5 = null;
        imageMarkServiceBase0.setVersionService(versionService5);
        QName qName7 = null;
        NodeRef nodeRef8 = null;
        String[] str_array15 = new String[] { "", "", "hi!", "hi!", "hi!", "hi!" };
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.getFolderNodeRef(qName7, nodeRef8, str_array15);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNotNull(str_array15);

    }

    @Test
    public void test10() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        VersionService versionService1 = imageMarkServiceBase0.getVersionService();
        NodeRef nodeRef2 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.getType(nodeRef2);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(versionService1);

    }

    @Test
    public void test11() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        ServiceRegistry serviceRegistry1 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry1);
        ServiceRegistry serviceRegistry3 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry3);
        QName qName7 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.getTradeMarkNodeRef("hi!", "hi!", qName7);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test12() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        ServiceRegistry serviceRegistry1 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry1);
        NodeRef nodeRef3 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.getType(nodeRef3);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test13() throws Throwable {

        ImageMarkServiceBase imageMarkServiceBase0 = new ImageMarkServiceBase();
        ServiceRegistry serviceRegistry1 = null;
        imageMarkServiceBase0.setServiceRegistry(serviceRegistry1);
        QName qName3 = null;
        NodeRef nodeRef4 = null;
        String[] str_array7 = new String[] { "hi!", "hi!" };
        // The following exception was thrown during execution in test
        // generation
        try {
            imageMarkServiceBase0.getFolderNodeRef(qName3, nodeRef4, str_array7);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNotNull(str_array7);

    }

}
