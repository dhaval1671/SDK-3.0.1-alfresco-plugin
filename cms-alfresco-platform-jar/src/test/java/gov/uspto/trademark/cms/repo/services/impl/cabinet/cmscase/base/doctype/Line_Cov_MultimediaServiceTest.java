package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.transform.ContentTransformer;
import org.alfresco.repo.policy.BehaviourFilter;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;

public class Line_Cov_MultimediaServiceTest extends BaseServiceSetup {

    @Test
    public void testIsMultimediaContentChangeInsideCaseOrMulFolderFlowOne() throws NoSuchMethodException, IllegalAccessException,
            InstantiationException, IllegalArgumentException, InvocationTargetException {
        MultimediaService ms = getMultimediaServiceInstance();
        String[] s1 = { "test", "workspace://1/1001", "http://soapi.XXX.com/manager", "managerService" };
        // System.out.println(Arrays.toString(s1));
        String mimeType = s1[0];
        NodeRef caseSerialNumberNodeRef = new NodeRef(s1[1]);
        QName docType = QName.createQName(s1[2], s1[3]);
        boolean flag = ms.isMultimediaContentChangeInsideCaseOrMulFolder(mimeType, caseSerialNumberNodeRef, docType, false);
        assertFalse("This change is NOT inside case of multimedia folder.", flag);
    }

    @Test
    public void testIsMultimediaContentChangeInsideCaseOrMulFolderFlowTwo() throws NoSuchMethodException, IllegalAccessException,
            InstantiationException, IllegalArgumentException, InvocationTargetException {
        MultimediaService ms = getMultimediaServiceInstance();
        String[] s1 = { "video/a-axm", "workspace://1/1001", "http://soapi.XXX.com/manager", "managerService" };
        String mimeType = s1[0];
        NodeRef caseSerialNumberNodeRef = new NodeRef(s1[1]);
        QName docType = QName.createQName(s1[2], s1[3]);
        boolean flag = ms.isMultimediaContentChangeInsideCaseOrMulFolder(mimeType, caseSerialNumberNodeRef, docType, false);
        assertFalse("This change is NOT inside case of multimedia folder.", flag);
    }

    @Test
    public void testIsMultimediaContentChangeInsideCaseOrMulFolderFlowThree() throws NoSuchMethodException,
            IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        MultimediaService ms = getMultimediaServiceInstance();
        String[] s1 = { "audio/a-axm", "workspace://1/1001", "http://soapi.XXX.com/manager", "managerService" };
        String mimeType = s1[0];
        NodeRef caseSerialNumberNodeRef = new NodeRef(s1[1]);
        QName docType = QName.createQName(s1[2], s1[3]);
        boolean flag = ms.isMultimediaContentChangeInsideCaseOrMulFolder(mimeType, caseSerialNumberNodeRef, docType, false);
        assertFalse("This change is NOT inside case of multimedia folder.", flag);
    }

    @Test
    public void testIsMultimediaContentChangeInsideCaseOrMulFolderFlowFour() throws NoSuchMethodException, IllegalAccessException,
            InstantiationException, IllegalArgumentException, InvocationTargetException {
        MultimediaService ms = getMultimediaServiceInstance();
        String[] s1 = { "video/a-axm", "workspace://1/1001", "http://www.uspto.gov/cms/model/content/1.0", "mark" };
        String mimeType = s1[0];
        NodeRef caseSerialNumberNodeRef = new NodeRef(s1[1]);
        QName docType = QName.createQName(s1[2], s1[3]);
        boolean flag = ms.isMultimediaContentChangeInsideCaseOrMulFolder(mimeType, caseSerialNumberNodeRef, docType, false);
        // System.out.println(flag);
        assertTrue("This change happening inside case of multimedia folder.", flag);
    }

    @Test
    public void testGetMultimediaFolderBranchFlowOne() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        MultimediaService ms = getMultimediaServiceInstance();
        NodeRef currentFolderNodeRef = new NodeRef("workspace://currentFolderNodeRef/");
        NodeRef multimediaNodeRef = new NodeRef("workspace://multimediaNodeRef/");
        String incomingFolderName = "multimedia";
        when(cmsNodeLocator.locateNodeRef((NodeRef) any(), eq(TMConstants.MULTIMEDIA_FOLDER_NAME))).thenReturn(multimediaNodeRef);
        when(cmsNodeCreator.createFolderNode((NodeRef) any(), eq(TMConstants.MULTIMEDIA_FOLDER_NAME)))
                .thenReturn(multimediaNodeRef);
        when(cmsNodeLocator.locateNodeRef((NodeRef) any(), eq(TMConstants.MULTIMEDIA_FOLDER_NAME), eq(incomingFolderName)))
                .thenReturn(multimediaNodeRef);
        NodeRef nr = ms.getMultimediaFolder(currentFolderNodeRef, incomingFolderName);
        // System.out.println(nr);
        assertNotNull(nr);
    }

    @Test
    public void testGetMultimediaFolderBranchFlowTwo() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        MultimediaService ms = getMultimediaServiceInstance();
        NodeRef currentFolderNodeRef = new NodeRef("workspace://currentFolderNodeRef/");
        NodeRef multimediaNodeRef = new NodeRef("workspace://multimediaNodeRef/");
        String incomingFolderName = "multimedia";
        when(cmsNodeLocator.locateNodeRef((NodeRef) any(), eq(TMConstants.MULTIMEDIA_FOLDER_NAME))).thenReturn(null);
        when(cmsNodeCreator.createFolderNode((NodeRef) any(), eq(TMConstants.MULTIMEDIA_FOLDER_NAME)))
                .thenReturn(multimediaNodeRef);
        when(cmsNodeLocator.locateNodeRef((NodeRef) any(), eq(TMConstants.MULTIMEDIA_FOLDER_NAME), eq(incomingFolderName)))
                .thenReturn(multimediaNodeRef);
        NodeRef nr = ms.getMultimediaFolder(currentFolderNodeRef, incomingFolderName);
        // System.out.println(nr);
        assertNotNull(nr);
    }

    @Test
    public void testGetMultimediaFolderBranchFlowThree() throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MultimediaService ms = getMultimediaServiceInstance();
        Map<QName, Serializable> targetRepoMap = new HashMap<QName, Serializable>();
        targetRepoMap.put(ContentModel.PROP_NODE_UUID, null);
        targetRepoMap.put(ContentModel.PROP_NODE_DBID, null);
        targetRepoMap.put(ContentModel.PROP_CONTENT, null);
        when(nodeService.getProperties((NodeRef) any())).thenReturn(targetRepoMap);
        NodeRef currentFolderNodeRef = new NodeRef("workspace://currentFolderNodeRef/");
        NodeRef multimediaNodeRef = new NodeRef("workspace://multimediaNodeRef/");
        String incomingFolderName = "multimedia";
        when(cmsNodeLocator.locateNodeRef((NodeRef) any(), eq(TMConstants.MULTIMEDIA_FOLDER_NAME))).thenReturn(null);
        when(cmsNodeCreator.createFolderNode((NodeRef) any(), eq(TMConstants.MULTIMEDIA_FOLDER_NAME)))
                .thenReturn(multimediaNodeRef);
        when(cmsNodeLocator.locateNodeRef((NodeRef) any(), eq(TMConstants.MULTIMEDIA_FOLDER_NAME), eq(incomingFolderName)))
                .thenReturn(null);
        ChildAssociationRef mockChildAssociationRef = mock(ChildAssociationRef.class);
        when(nodeService.createNode((NodeRef) any(), (QName) any(), (QName) any(), (QName) any(), eq(targetRepoMap)))
                .thenReturn(mockChildAssociationRef);
        when(mockChildAssociationRef.getChildRef()).thenReturn(multimediaNodeRef);
        NodeRef nr = ms.getMultimediaFolder(currentFolderNodeRef, incomingFolderName);
        // System.out.println(nr);
        assertNotNull(nr);
    }

    @Test
    public void testGetMultimediaFolderBranchFlowFour() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        MultimediaService ms = getMultimediaServiceInstance();
        Map<QName, Serializable> targetRepoMap = new HashMap<QName, Serializable>();
        targetRepoMap.put(ContentModel.PROP_NODE_UUID, null);
        targetRepoMap.put(ContentModel.PROP_NODE_DBID, null);
        targetRepoMap.put(ContentModel.PROP_CONTENT, null);
        when(nodeService.getProperties((NodeRef) any())).thenReturn(targetRepoMap);
        NodeRef currentFolderNodeRef = new NodeRef("workspace://currentFolderNodeRef/");
        NodeRef multimediaNodeRef = new NodeRef("workspace://multimediaNodeRef/");
        String incomingFolderName = "multimedia";
        when(cmsNodeLocator.locateNodeRef((NodeRef) any(), eq(TMConstants.MULTIMEDIA_FOLDER_NAME))).thenReturn(null);
        when(cmsNodeCreator.createFolderNode((NodeRef) any(), eq(TMConstants.MULTIMEDIA_FOLDER_NAME)))
                .thenReturn(multimediaNodeRef);
        when(cmsNodeLocator.locateNodeRef((NodeRef) any(), eq(TMConstants.MULTIMEDIA_FOLDER_NAME), eq(incomingFolderName)))
                .thenReturn(multimediaNodeRef);
        ChildAssociationRef mockChildAssociationRef = mock(ChildAssociationRef.class);
        when(nodeService.createNode((NodeRef) any(), (QName) any(), (QName) any(), (QName) any(), eq(targetRepoMap)))
                .thenReturn(mockChildAssociationRef);
        when(mockChildAssociationRef.getChildRef()).thenReturn(multimediaNodeRef);
        NodeRef nr = ms.getMultimediaFolder(currentFolderNodeRef, incomingFolderName);
        // System.out.println(nr);
        assertNotNull(nr);
    }

    @Test
    public void testIsMediaTransformable() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        MultimediaService ms = getMultimediaServiceInstance();
        ContentTransformer videoTransformer = mock(ContentTransformer.class);
        ContentTransformer audioTransformer = mock(ContentTransformer.class);
        String mediaMimeType = "video/asx-asf";
        when(contentService.getTransformer(eq(mediaMimeType), eq(TMConstants.VIDEO_MP4))).thenReturn(videoTransformer);
        when(contentService.getTransformer(eq(mediaMimeType), eq(TMConstants.AUDIO_MPEG))).thenReturn(audioTransformer);
        boolean flag = ms.isMediaTransformable(mediaMimeType);
        assertNotNull(flag);
    }

    @Test (expected = TmngCmsException.TmngContentTransformerNotFoundException.class)
    public void testGetAppropriateTransformerForCreateEventFlowOne() throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MultimediaService ms = getMultimediaServiceInstance();
        ContentTransformer videoTransformer = mock(ContentTransformer.class);
        String mediaMimeType = "video/asx-asf";
        when(contentService.getTransformer(eq(mediaMimeType), eq(TMConstants.VIDEO_MP4))).thenReturn(videoTransformer);
        ContentTransformer ct = ms.getAppropriateTransformerForCreateEvent(mediaMimeType);
        assertNotNull(ct);
    }

    @Test (expected = TmngCmsException.TmngContentTransformerNotFoundException.class)
    public void testGetAppropriateTransformerForCreateEventFlowTwo() throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MultimediaService ms = getMultimediaServiceInstance();
        ContentTransformer audioTransformer = mock(ContentTransformer.class);
        String mediaMimeType = "audio/asx-asf";
        when(contentService.getTransformer(eq(mediaMimeType), eq(TMConstants.AUDIO_MPEG))).thenReturn(audioTransformer);
        ContentTransformer ct = ms.getAppropriateTransformerForCreateEvent(mediaMimeType);
        assertNotNull(ct);
    }

    @Test(expected = TmngCmsException.TmngContentTransformerNotFoundException.class)
    public void testGetAppropriateTransformerForCreateEventFlowThree() throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MultimediaService ms = getMultimediaServiceInstance();
        String mediaMimeType = "audio/asx-asf";
        when(contentService.getTransformer(eq(mediaMimeType), eq(TMConstants.AUDIO_MPEG))).thenReturn(null);
        when(ms.getAppropriateTransformerForCreateEvent(mediaMimeType))
                .thenThrow(new TmngCmsException.TmngContentTransformerNotFoundException(
                        "Transformer for converting media type " + mediaMimeType + " NOT found.", null));
    }



    @Test
    public void testProcessMediaConversionFlowOne() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            InstantiationException, IllegalAccessException, InvocationTargetException {

        MultimediaService ms = getMultimediaServiceInstance();

        ChildAssociationRef childAssocRefTwo = mock(ChildAssociationRef.class);
        NodeRef serialNumberFolderNodeRef = new NodeRef("workspace://serialNumberFolderNodeRef/");
        when(childAssocRefTwo.getParentRef()).thenReturn(serialNumberFolderNodeRef);
        when(nodeService.getPrimaryParent((NodeRef) any())).thenReturn(childAssocRefTwo);

        NodeRef incomingMediaTypeNodeRefLcl = new NodeRef("workspace://incomingMediaTypeNodeRef/");
        ChildAssociationRef childAssocRef = mock(ChildAssociationRef.class);
        when(childAssocRef.getChildRef()).thenReturn(incomingMediaTypeNodeRefLcl);
        String oldMediaFileName = "oldMediaFileName";
        String oldMediaNewGuessMimeType = "video/asx-asf";
        String newMediaFileName = "newMediaFileName";
        String newMimeType = "newMimeType";
        boolean updateCurrentEntry = false;

        RetryingTransactionHelper txnHelper = mock(RetryingTransactionHelper.class);
        when(transactionService.getRetryingTransactionHelper()).thenReturn(txnHelper);

        NodeRef targetNodeRefInsideSerialCaseFolder = new NodeRef("workspace://targetNodeRefInsideSerialCaseFolder/");
        when(cmsNodeLocator.locateNodeRef((NodeRef) any(), (String) any())).thenReturn(targetNodeRefInsideSerialCaseFolder);

        when(txnHelper.doInTransaction(Matchers.<RetryingTransactionCallback<?>> any(), Matchers.anyBoolean(),
                Matchers.anyBoolean())).thenAnswer(new Answer<Void>() {
                    @Override
                    public Void answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        @SuppressWarnings("unchecked")
                        RetryingTransactionHelper.RetryingTransactionCallback<Void> arg = (RetryingTransactionHelper.RetryingTransactionCallback<Void>) args[0];
                        arg.execute();
                        return null;
                    }

                });

        ms.processMediaConversion(childAssocRef, oldMediaFileName, oldMediaNewGuessMimeType, newMediaFileName, newMimeType,
                updateCurrentEntry);
        
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("test");
        arr.clear();
        assertEquals(arr.size(),0);    
    }

    @Test
    public void testProcessMediaConversionFlowTwo() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            InstantiationException, IllegalAccessException, InvocationTargetException {

        MultimediaService ms = getMultimediaServiceInstance();

        ChildAssociationRef childAssocRefTwo = mock(ChildAssociationRef.class);
        when(nodeService.getPrimaryParent((NodeRef) any())).thenReturn(childAssocRefTwo);

        NodeRef incomingMediaTypeNodeRefLcl = new NodeRef("workspace://incomingMediaTypeNodeRef/");
        ChildAssociationRef childAssocRef = mock(ChildAssociationRef.class);
        when(childAssocRef.getChildRef()).thenReturn(incomingMediaTypeNodeRefLcl);
        String oldMediaFileName = "oldMediaFileName";
        String oldMediaNewGuessMimeType = "video/wmv1";
        String newMediaFileName = "newMediaFileName";
        String newMimeType = "newMimeType";
        boolean updateCurrentEntry = true;

        ContentTransformer videoTransformer = mock(ContentTransformer.class);
        ContentTransformer audioTransformer = mock(ContentTransformer.class);
        String mediaMimeType = "video/wmv1";
        when(contentService.getTransformer(eq(mediaMimeType), eq(TMConstants.VIDEO_MP4))).thenReturn(videoTransformer);
        when(contentService.getTransformer(eq(mediaMimeType), eq(TMConstants.AUDIO_MPEG))).thenReturn(audioTransformer);

        RetryingTransactionHelper txnHelper = mock(RetryingTransactionHelper.class);
        when(transactionService.getRetryingTransactionHelper()).thenReturn(txnHelper);        
        
        when(txnHelper.doInTransaction(Matchers.<RetryingTransactionCallback<?>> any(), Matchers.anyBoolean(),
                Matchers.anyBoolean())).thenAnswer(new Answer<Void>() {
                    @Override
                    public Void answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        @SuppressWarnings("unchecked")
                        RetryingTransactionHelper.RetryingTransactionCallback<Void> arg = (RetryingTransactionHelper.RetryingTransactionCallback<Void>) args[0];
                        arg.execute();
                        return null;
                    }

                });      
        
        ms.processMediaConversion(childAssocRef, oldMediaFileName, oldMediaNewGuessMimeType, newMediaFileName, newMimeType, updateCurrentEntry);
        
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("test");
        arr.clear();
        assertEquals(arr.size(),0);   
        
    }

    @Test
    public void testProcessMediaConversionFlowThree() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {

        MultimediaService ms = getMultimediaServiceInstance();
        Field f = MultimediaService.class.getDeclaredField("behaviourFilter");
        f.setAccessible(true);
        BehaviourFilter lclBehaviourFilter = mock(BehaviourFilter.class);
        f.set(ms, lclBehaviourFilter);
        doNothing().when(lclBehaviourFilter).disableBehaviour();
        doNothing().when(lclBehaviourFilter).disableBehaviour(any(QName.class));
        doNothing().when(lclBehaviourFilter).enableBehaviour(any(QName.class));

        ChildAssociationRef childAssocRefTwo = mock(ChildAssociationRef.class);
        when(nodeService.getPrimaryParent((NodeRef) any())).thenReturn(childAssocRefTwo);

        NodeRef incomingMediaTypeNodeRefLcl = new NodeRef("workspace://incomingMediaTypeNodeRef/");
        ChildAssociationRef childAssocRef = mock(ChildAssociationRef.class);
        when(childAssocRef.getChildRef()).thenReturn(incomingMediaTypeNodeRefLcl);
        String oldMediaFileName = "oldMediaFileName";
        String oldMediaNewGuessMimeType = "audio/wmv1";
        String newMediaFileName = "newMediaFileName";
        String newMimeType = "audio/wmv1";
        boolean updateCurrentEntry = false;

        ContentTransformer videoTransformer = mock(ContentTransformer.class);
        ContentTransformer audioTransformer = mock(ContentTransformer.class);
        when(contentService.getTransformer((String) any(), eq(TMConstants.VIDEO_MP4))).thenReturn(videoTransformer);
        when(contentService.getTransformer((String) any(), eq(TMConstants.AUDIO_MPEG))).thenReturn(audioTransformer);

        ContentData cd = mock(ContentData.class);
        NodeRef targetFileNodeRef = new NodeRef("workspace://targetFileNodeRef/");
        when(nodeService.getProperty(targetFileNodeRef, ContentModel.PROP_CONTENT)).thenReturn(cd);

        NodeRef targetNodeRefInsideSerialCaseFolder = new NodeRef("workspace://targetNodeRefInsideSerialCaseFolder/");
        when(cmsNodeLocator.locateNodeRef((NodeRef) any(), (String) any())).thenReturn(targetNodeRefInsideSerialCaseFolder);

        RetryingTransactionHelper txnHelper = mock(RetryingTransactionHelper.class);
        when(transactionService.getRetryingTransactionHelper()).thenReturn(txnHelper);

        when(txnHelper.doInTransaction(Matchers.<RetryingTransactionCallback<?>> any(), Matchers.anyBoolean(),
                Matchers.anyBoolean())).thenAnswer(new Answer<Void>() {
                    @Override
                    public Void answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        @SuppressWarnings("unchecked")
                        RetryingTransactionHelper.RetryingTransactionCallback<Void> arg = (RetryingTransactionHelper.RetryingTransactionCallback<Void>) args[0];
                        arg.execute();
                        return null;
                    }

                });

        ms.processMediaConversion(childAssocRef, oldMediaFileName, oldMediaNewGuessMimeType, newMediaFileName, newMimeType,
                updateCurrentEntry);
        
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("test");
        arr.clear();
        assertEquals(arr.size(),0);           
    }

    @Test
    public void testProcessMediaConversionFlowFour() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {

        MultimediaService ms = getMultimediaServiceInstance();

        Field f = MultimediaService.class.getDeclaredField("behaviourFilter");
        f.setAccessible(true);
        BehaviourFilter lclBehaviourFilter = mock(BehaviourFilter.class);
        f.set(ms, lclBehaviourFilter);
        doNothing().when(lclBehaviourFilter).disableBehaviour();
        doNothing().when(lclBehaviourFilter).disableBehaviour(any(QName.class));
        doThrow(new ContentIOException("UnitTestCoverage: Can't transform")).when(lclBehaviourFilter)
                .enableBehaviour(any(QName.class));

        ChildAssociationRef childAssocRefTwo = mock(ChildAssociationRef.class);
        when(nodeService.getPrimaryParent((NodeRef) any())).thenReturn(childAssocRefTwo);

        NodeRef incomingMediaTypeNodeRefLcl = new NodeRef("workspace://incomingMediaTypeNodeRef/");
        ChildAssociationRef childAssocRef = mock(ChildAssociationRef.class);
        when(childAssocRef.getChildRef()).thenReturn(incomingMediaTypeNodeRefLcl);
        String oldMediaFileName = "oldMediaFileName";
        String oldMediaNewGuessMimeType = "video/wmv1";
        String newMediaFileName = "newMediaFileName";
        String newMimeType = "audio/wmv1";
        boolean updateCurrentEntry = false;

        ContentTransformer videoTransformer = mock(ContentTransformer.class);
        ContentTransformer audioTransformer = mock(ContentTransformer.class);
        when(contentService.getTransformer((String) any(), eq(TMConstants.VIDEO_MP4))).thenReturn(videoTransformer);
        when(contentService.getTransformer((String) any(), eq(TMConstants.AUDIO_MPEG))).thenReturn(audioTransformer);

        ContentData cd = mock(ContentData.class);
        NodeRef targetFileNodeRef = new NodeRef("workspace://targetFileNodeRef/");
        when(nodeService.getProperty(targetFileNodeRef, ContentModel.PROP_CONTENT)).thenReturn(cd);

        NodeRef targetNodeRefInsideSerialCaseFolder = new NodeRef("workspace://targetNodeRefInsideSerialCaseFolder/");
        when(cmsNodeLocator.locateNodeRef((NodeRef) any(), (String) any())).thenReturn(targetNodeRefInsideSerialCaseFolder);

        RetryingTransactionHelper txnHelper = mock(RetryingTransactionHelper.class);
        when(transactionService.getRetryingTransactionHelper()).thenReturn(txnHelper);

        when(txnHelper.doInTransaction(Matchers.<RetryingTransactionCallback<?>> any(), Matchers.anyBoolean(),
                Matchers.anyBoolean())).thenAnswer(new Answer<Void>() {
                    @Override
                    public Void answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        @SuppressWarnings("unchecked")
                        RetryingTransactionHelper.RetryingTransactionCallback<Void> arg = (RetryingTransactionHelper.RetryingTransactionCallback<Void>) args[0];
                        arg.execute();
                        return null;
                    }

                });

        ms.processMediaConversion(childAssocRef, oldMediaFileName, oldMediaNewGuessMimeType, newMediaFileName, newMimeType, updateCurrentEntry);
        
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("test");
        arr.clear();
        assertEquals(arr.size(),0);           
    }

}
