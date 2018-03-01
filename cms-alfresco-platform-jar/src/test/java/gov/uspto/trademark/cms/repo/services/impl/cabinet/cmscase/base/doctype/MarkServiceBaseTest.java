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

import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.services.VersionService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MarkServiceBaseTest {

    public static boolean debug = false;

    @Test
    public void test01() throws Throwable {

        MarkServiceBase markServiceBase0 = new MarkServiceBase();
        NodeRef nodeRef1 = null;
        QName qName2 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markServiceBase0.getContentReader(nodeRef1, qName2);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test02() throws Throwable {

        MarkServiceBase markServiceBase0 = new MarkServiceBase();
        QName qName1 = null;
        InputStream inputStream4 = null;
        Map<QName, Serializable> map_qName_serializable5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markServiceBase0.upload(qName1, "hi!", "hi!", inputStream4, map_qName_serializable5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test03() throws Throwable {

        MarkServiceBase markServiceBase0 = new MarkServiceBase();
        QName qName1 = null;
        NodeRef nodeRef2 = null;
        String[] str_array8 = new String[] { "", "", "", "", "" };
        // The following exception was thrown during execution in test
        // generation
        try {
            markServiceBase0.getFolderNodeRef(qName1, nodeRef2, str_array8);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNotNull(str_array8);

    }

    @Test
    public void test04() throws Throwable {

        MarkServiceBase markServiceBase0 = new MarkServiceBase();
        NodeRef nodeRef1 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markServiceBase0.getType(nodeRef1);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test05() throws Throwable {

        MarkServiceBase markServiceBase0 = new MarkServiceBase();
        QName qName3 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markServiceBase0.getTradeMarkNodeRef("hi!", "", qName3);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test06() throws Throwable {

        MarkServiceBase markServiceBase0 = new MarkServiceBase();
        QName qName2 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markServiceBase0.getTradeMarkNodeRef("", qName2);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test07() throws Throwable {

        MarkServiceBase markServiceBase0 = new MarkServiceBase();
        VersionService versionService1 = markServiceBase0.getVersionService();
        QName[] qName_array2 = new QName[] {};
        ArrayList<QName> arraylist_qName3 = new ArrayList<QName>();
        boolean b4 = Collections.addAll(arraylist_qName3, qName_array2);
        String[] str_array5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markServiceBase0.fileExists(arraylist_qName3, str_array5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(versionService1);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNotNull(qName_array2);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertTrue(b4 == false);

    }

    @Test
    public void test08() throws Throwable {

        MarkServiceBase markServiceBase0 = new MarkServiceBase();
        QName qName3 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markServiceBase0.getTradeMarkNodeRef("hi!", "hi!", qName3);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test09() throws Throwable {

        MarkServiceBase markServiceBase0 = new MarkServiceBase();
        NodeRef nodeRef1 = null;
        QName qName2 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markServiceBase0.hasAspect(nodeRef1, qName2);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test10() throws Throwable {

        MarkServiceBase markServiceBase0 = new MarkServiceBase();
        NodeRef nodeRef1 = null;
        QName qName2 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markServiceBase0.getProperty(nodeRef1, qName2);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test11() throws Throwable {

        MarkServiceBase markServiceBase0 = new MarkServiceBase();
        CaseService caseService1 = null;
        markServiceBase0.setCaseService(caseService1);
        VersionService versionService3 = markServiceBase0.getVersionService();
        NodeRef nodeRef4 = null;
        QName qName5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markServiceBase0.getAssociations(nodeRef4, qName5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(versionService3);

    }

    @Test
    public void test12() throws Throwable {

        MarkServiceBase markServiceBase0 = new MarkServiceBase();
        CaseService caseService1 = null;
        markServiceBase0.setCaseService(caseService1);
        ServiceRegistry serviceRegistry3 = null;
        markServiceBase0.setServiceRegistry(serviceRegistry3);
        QName qName5 = null;
        NodeRef nodeRef7 = null;
        InputStream inputStream9 = null;
        Map<QName, Serializable> map_qName_serializable10 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            markServiceBase0.upload(qName5, "", nodeRef7, "hi!", inputStream9, map_qName_serializable10);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

}
