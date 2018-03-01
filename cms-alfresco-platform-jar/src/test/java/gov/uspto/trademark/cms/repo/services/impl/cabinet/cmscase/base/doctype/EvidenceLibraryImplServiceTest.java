package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.InputStream;
import java.io.Serializable;
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
public class EvidenceLibraryImplServiceTest {

    public static boolean debug = false;

    @Test
    public void test01() throws Throwable {

        EvidenceLibraryImplService evidenceLibraryImplService0 = new EvidenceLibraryImplService();
        QName qName1 = null;
        InputStream inputStream4 = null;
        Map<QName, Serializable> map_qName_serializable5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceLibraryImplService0.upload(qName1, "", "", inputStream4, map_qName_serializable5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test02() throws Throwable {

        EvidenceLibraryImplService evidenceLibraryImplService0 = new EvidenceLibraryImplService();
        NodeRef nodeRef1 = null;
        QName qName2 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceLibraryImplService0.getContentReader(nodeRef1, qName2);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test03() throws Throwable {

        EvidenceLibraryImplService evidenceLibraryImplService0 = new EvidenceLibraryImplService();
        NodeRef nodeRef1 = null;
        QName qName2 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceLibraryImplService0.getProperty(nodeRef1, qName2);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test04() throws Throwable {

        EvidenceLibraryImplService evidenceLibraryImplService0 = new EvidenceLibraryImplService();
        VersionService versionService1 = evidenceLibraryImplService0.getVersionService();
        VersionService versionService2 = evidenceLibraryImplService0.getVersionService();
        VersionService versionService3 = null;
        evidenceLibraryImplService0.setVersionService(versionService3);
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceLibraryImplService0.getEvidenceBankFolderNodeRef();
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(versionService1);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(versionService2);

    }

    @Test
    public void test05() throws Throwable {

        EvidenceLibraryImplService evidenceLibraryImplService0 = new EvidenceLibraryImplService();
        VersionService versionService1 = evidenceLibraryImplService0.getVersionService();
        NodeRef nodeRef2 = null;
        QName qName3 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceLibraryImplService0.getContentReader(nodeRef2, qName3);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(versionService1);

    }

    @Test
    public void test06() throws Throwable {

        EvidenceLibraryImplService evidenceLibraryImplService0 = new EvidenceLibraryImplService();
        VersionService versionService1 = evidenceLibraryImplService0.getVersionService();
        VersionService versionService2 = evidenceLibraryImplService0.getVersionService();
        VersionService versionService3 = null;
        evidenceLibraryImplService0.setVersionService(versionService3);
        ServiceRegistry serviceRegistry5 = null;
        evidenceLibraryImplService0.setServiceRegistry(serviceRegistry5);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(versionService1);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(versionService2);

    }

    @Test
    public void test07() throws Throwable {

        EvidenceLibraryImplService evidenceLibraryImplService0 = new EvidenceLibraryImplService();
        VersionService versionService1 = evidenceLibraryImplService0.getVersionService();
        QName qName2 = null;
        InputStream inputStream5 = null;
        Map<QName, Serializable> map_qName_serializable6 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceLibraryImplService0.upload(qName2, "hi!", "hi!", inputStream5, map_qName_serializable6);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(versionService1);

    }

    @Test
    public void test08() throws Throwable {

        EvidenceLibraryImplService evidenceLibraryImplService0 = new EvidenceLibraryImplService();
        VersionService versionService1 = null;
        evidenceLibraryImplService0.setVersionService(versionService1);
        NodeRef nodeRef3 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceLibraryImplService0.getNodeProperties(nodeRef3);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test10() throws Throwable {

        EvidenceLibraryImplService evidenceLibraryImplService0 = new EvidenceLibraryImplService();
        VersionService versionService1 = evidenceLibraryImplService0.getVersionService();
        VersionService versionService2 = evidenceLibraryImplService0.getVersionService();
        VersionService versionService3 = null;
        evidenceLibraryImplService0.setVersionService(versionService3);
        NodeRef nodeRef5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
             evidenceLibraryImplService0.getType(nodeRef5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(versionService1);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNull(versionService2);

    }

    @Test
    public void test11() throws Throwable {

        EvidenceLibraryImplService evidenceLibraryImplService0 = new EvidenceLibraryImplService();
        CaseService caseService1 = null;
        evidenceLibraryImplService0.setCaseService(caseService1);
        NodeRef nodeRef3 = null;
        QName qName4 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceLibraryImplService0.hasAspect(nodeRef3, qName4);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

}
