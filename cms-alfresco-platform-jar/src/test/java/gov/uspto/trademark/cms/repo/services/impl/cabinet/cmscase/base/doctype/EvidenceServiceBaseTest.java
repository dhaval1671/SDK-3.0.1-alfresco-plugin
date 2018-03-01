package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.alfresco.repo.model.Repository;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.services.VersionService;
import gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EvidenceServiceBaseTest {

    public static boolean debug = false;

    @Test
    public void test01() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        VersionService versionService1 = null;
        evidenceServiceBase0.setVersionService(versionService1);
        QName qName3 = null;
        NodeRef nodeRef4 = null;
        String[] str_array8 = new String[] { "", "", "hi!" };
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.getFolderNodeRef(qName3, nodeRef4, str_array8);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNotNull(str_array8);

    }

    @Test
    public void test02() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        NodeRef nodeRef2 = null;
        Map<NodeRef, CopyEvidenceRequest> map_nodeRef_copyEvidenceRequest3 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.bulkMetadataUpdateEvidences("hi!",
                    nodeRef2, map_nodeRef_copyEvidenceRequest3);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test03() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        NodeRef nodeRef1 = null;
        QName qName2 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.getContentReader(nodeRef1, qName2);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test04() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        VersionService versionService1 = null;
        evidenceServiceBase0.setVersionService(versionService1);
        NodeRef nodeRef3 = null;
        QName qName4 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.getProperty(nodeRef3, qName4);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test05() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        VersionService versionService1 = null;
        evidenceServiceBase0.setVersionService(versionService1);
        NodeRef nodeRef4 = null;
        Map<NodeRef, CopyEvidenceRequest> map_nodeRef_copyEvidenceRequest5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.bulkMetadataUpdateEvidences("", nodeRef4,
                    map_nodeRef_copyEvidenceRequest5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test06() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        NodeRef nodeRef1 = null;
        QName qName2 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.getAssociations(nodeRef1, qName2);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test07() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        Repository repository1 = null;
        evidenceServiceBase0.setRepositoryHelper(repository1);
        NodeRef nodeRef3 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.getNodeProperties(nodeRef3);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test08() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        CaseService caseService1 = null;
        evidenceServiceBase0.setCaseService(caseService1);
        QName[] qName_array3 = new QName[] {};
        ArrayList<QName> arraylist_qName4 = new ArrayList<QName>();
        boolean b5 = Collections.addAll(arraylist_qName4, qName_array3);
        String[] str_array9 = new String[] { "", "", "hi!" };
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.fileExists(arraylist_qName4, str_array9);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNotNull(qName_array3);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertTrue(b5 == false);

        // Regression assertion (captures the current behavior of the code)
        Assert.assertNotNull(str_array9);

    }

    @Test
    public void test09() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        VersionService versionService1 = null;
        evidenceServiceBase0.setVersionService(versionService1);
        NodeRef nodeRef3 = null;
        QName qName4 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.hasAspect(nodeRef3, qName4);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test11() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        CaseService caseService1 = null;
        evidenceServiceBase0.setCaseService(caseService1);
        NodeRef nodeRef4 = null;
        Map<NodeRef, CopyEvidenceRequest> map_nodeRef_copyEvidenceRequest5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.copyEvidences("", nodeRef4,
                    map_nodeRef_copyEvidenceRequest5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test12() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        VersionService versionService1 = null;
        evidenceServiceBase0.setVersionService(versionService1);
        Repository repository3 = null;
        evidenceServiceBase0.setRepositoryHelper(repository3);
        NodeRef nodeRef5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.getType(nodeRef5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test13() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        CaseService caseService1 = null;
        evidenceServiceBase0.setCaseService(caseService1);
        NodeRef nodeRef4 = null;
        Map<NodeRef, CopyEvidenceRequest> map_nodeRef_copyEvidenceRequest5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.bulkMetadataUpdateEvidences("", nodeRef4,
                    map_nodeRef_copyEvidenceRequest5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

    @Test
    public void test14() throws Throwable {

        EvidenceServiceBase evidenceServiceBase0 = new EvidenceServiceBase();
        Repository repository1 = null;
        evidenceServiceBase0.setRepositoryHelper(repository1);
        Repository repository3 = null;
        evidenceServiceBase0.setRepositoryHelper(repository3);
        NodeRef nodeRef5 = null;
        // The following exception was thrown during execution in test
        // generation
        try {
            evidenceServiceBase0.getType(nodeRef5);
            Assert.fail("Expected exception of type java.lang.NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception.
        }

    }

}
