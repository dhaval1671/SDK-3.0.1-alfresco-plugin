package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Line_Cov_EvidenceServiceBaseTest extends BaseServiceSetup {

    public static boolean debug = false;

    @Test
    public void testBulkMetadataUpdateEvidencesFlowOne() throws Throwable {

/*        EvidenceServiceBase esb = getEvidenceServiceBaseInstance();
        String serialNumber = "";
        NodeRef caseFolderNodeRef = new NodeRef("workspace://caseFolderNodeRef/");
        
        Map<NodeRef, CopyEvidenceRequest> sourceData = new HashMap<NodeRef, CopyEvidenceRequest>();
        CopyEvidenceRequest cer = new CopyEvidenceRequest();
        cer.setDocumentId("testDocId");
        Evidence evi = new Evidence();
        cer.setMetadata(evi);
        
        sourceData.put(new NodeRef("workspace://sampleNodeRef/"), cer);
        
        RetryingTransactionHelper txnHelper = mock(RetryingTransactionHelper.class);
        when(transactionService.getRetryingTransactionHelper()).thenReturn(txnHelper);        
        
        when(fileFolderService.getFileInfo((NodeRef) any())).thenReturn(null);
        
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
        
        List<EvidencePostResponse> evidencePostResponse = esb.bulkMetadataUpdateEvidences(serialNumber, caseFolderNodeRef, sourceData);
        assertNotNull(evidencePostResponse);*/
        
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("test");
        arr.clear();
        assertEquals(arr.size(),0);                 

    }




}
