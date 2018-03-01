package alf.unit.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.log4j.Logger;
import org.junit.Test;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * Unit testing for EvidenceService.
 *
 * @author stank{linkedin.com/in/sanjaytaunk}
 */
public class _01_IntializationTest extends BaseTest {

    /** The log. */
    private static Logger LOG = Logger.getLogger(_01_IntializationTest.class);

    /**
     * Verify paths in sequence.
     */
    @Test
    public void verifyPathsInSequence() {
        LOG.debug("### Executing "+ Thread.currentThread().getStackTrace()[1].getMethodName() +" ####");
        testGetCompanyHome();
        testGetCabinetFolder();
        testGetCaseFolder();
        testGetDocumentLibraryFolder();
        testEvidenceBankFolder();

    }

    /**
     * Test evidence bank folder.
     */
    private void testEvidenceBankFolder() {
        LOG.debug("### Executing "+ Thread.currentThread().getStackTrace()[1].getMethodName() +" ####");
        NodeRef eviBankFolder = getEvidenceBankFolder();
        assertNotNull(eviBankFolder);
        LOG.debug("Got Evidence Bank Folder NodeRef: " + eviBankFolder);  

    }

    /**
     * Test get document library folder.
     */
    private void testGetDocumentLibraryFolder() {
        LOG.debug("### Executing "+ Thread.currentThread().getStackTrace()[1].getMethodName() +" ####");
        NodeRef documentLibraryFolder = getDocumentLibraryFolder();
        assertNotNull(documentLibraryFolder);
        LOG.debug("Got Document_Library Folder NodeRef: " + documentLibraryFolder);        

    }

    /**
     * Test get company home.
     */
    private void testGetCompanyHome() {
        LOG.debug("### Executing "+ Thread.currentThread().getStackTrace()[1].getMethodName() +" ####");
        NodeRef companyHome = getompanyHomeFolder();
        assertNotNull(companyHome);
        String companyHomeName = (String) nodeService.getProperty(companyHome, ContentModel.PROP_NAME);
        assertNotNull(companyHomeName);
        assertEquals("Company Home", companyHomeName);
        LOG.debug("Got " + companyHomeName + " NodeRef: " + companyHome);
    }

    /**
     * Test get cabinet folder.
     */
    private void testGetCabinetFolder(){
        LOG.debug("### Executing "+ Thread.currentThread().getStackTrace()[1].getMethodName() +" ####");
       NodeRef cabinetFolder = getCabinetFolder();
       assertNotNull(cabinetFolder);
       LOG.debug("Got cabinet Folder NodeRef: " + cabinetFolder);
    }

    /**
     * Test get case folder.
     */
    private void testGetCaseFolder(){
        LOG.debug("### Executing "+ Thread.currentThread().getStackTrace()[1].getMethodName() +" ####");
       NodeRef caseFolder = getCaseFolder();
       assertNotNull(caseFolder);
       LOG.debug("Got case Folder NodeRef: " + caseFolder);
    }    

    /**
     * Gets the document library folder.
     *
     * @return the document library folder
     */
    private NodeRef getDocumentLibraryFolder(){
        LOG.debug("### Executing "+ Thread.currentThread().getStackTrace()[1].getMethodName() +" ####");
        List<String> pathElements = new ArrayList<String>();
        pathElements.add(TradeMarkModel.DOCUMENT_LIBRARY_FOLDER_NAME);
        NodeRef nr = null;
        try {
            nr = fileFolderService.resolveNamePath(repositoryHelper.getCompanyHome(), pathElements).getNodeRef();
        } catch (FileNotFoundException e) {
            if(LOG.isInfoEnabled()){
                LOG.info(e.getMessage(), e);
            }             
            LOG.error(e.getMessage(), e);
        }
        return nr;
    }    

    /**
     * Gets the case folder.
     *
     * @return the case folder
     */
    private NodeRef getCaseFolder(){
        LOG.debug("### Executing "+ Thread.currentThread().getStackTrace()[1].getMethodName() +" ####");
        List<String> pathElements = new ArrayList<String>();
        pathElements.add(TradeMarkModel.CABINET_FOLDER_NAME);
        pathElements.add(TradeMarkModel.CASE_FOLDER_NAME);
        NodeRef nr = null;
        try {
            nr = fileFolderService.resolveNamePath(repositoryHelper.getCompanyHome(), pathElements).getNodeRef();
        } catch (FileNotFoundException e) {
            if(LOG.isInfoEnabled()){
                LOG.info(e.getMessage(), e);
            }             
            LOG.error(e.getMessage(), e);
        }
        return nr;
    }

    /**
     * Gets the cabinet folder.
     *
     * @return the cabinet folder
     */
    private NodeRef getCabinetFolder(){
        LOG.debug("### Executing "+ Thread.currentThread().getStackTrace()[1].getMethodName() +" ####");
        List<String> pathElements = new ArrayList<String>();
        pathElements.add(TradeMarkModel.CABINET_FOLDER_NAME);
        NodeRef nr = null;
        try {
            nr = fileFolderService.resolveNamePath(repositoryHelper.getCompanyHome(), pathElements).getNodeRef();
        } catch (FileNotFoundException e) {
            if(LOG.isInfoEnabled()){
                LOG.info(e.getMessage(), e);
            }             
            LOG.error(e.getMessage(), e);
        }
        return nr;
    } 

    /**
     * Gets the ompany home folder.
     *
     * @return the ompany home folder
     */
    private NodeRef getompanyHomeFolder(){
        LOG.debug("### Executing "+ Thread.currentThread().getStackTrace()[1].getMethodName() +" ####");
        return nodeLocatorService.getNode("companyhome", null, null);
    }       

    /**
     * Gets the evidence bank folder.
     *
     * @return the evidence bank folder
     */
    private NodeRef getEvidenceBankFolder() {
        LOG.debug("### Executing "+ Thread.currentThread().getStackTrace()[1].getMethodName() +" ####");
        List<String> pathElements = new ArrayList<String>();
        pathElements.add(TradeMarkModel.DOCUMENT_LIBRARY_FOLDER_NAME);
        pathElements.add(TradeMarkModel.EVIDENCE_BANK_FOLDER_NAME);
        pathElements.add(TradeMarkModel.TWOAEVIDENCE_LIBRARY_FOLDER_NAME);
        NodeRef nr = null;
        try {
            nr = fileFolderService.resolveNamePath(repositoryHelper.getCompanyHome(), pathElements).getNodeRef();
        } catch (FileNotFoundException e) {
            if(LOG.isInfoEnabled()){
                LOG.info(e.getMessage(), e);
            }             
            LOG.error(e.getMessage(), e);
        }
        return nr;
    }    

}
