package alf.integration.parallel.ticrs.admin.softdelete;

import org.junit.Test;

import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

public class SimulateParallelTicrsAdminDelete {

    private UrlInputDto getUrlInput() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("FirstMark.tif");
        return urlInput;
    }	
	
	
    @Test
    public void testTicrsAdminDelete() throws InterruptedException{
        
        //create sample Mark
        for(int i=0; i<1; i++){
            CreateSampleMarkThread cet = new CreateSampleMarkThread(i, getUrlInput());
            Thread t1 = new Thread(cet);
            t1.start();
            //System.out.println("i from for loop :" + i);
        }     
        
        //Pause for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }          
        
        //Try to delete evidences    
        //Issue parallel delete Mark requests
        for(int i=0; i<=1; i++){
            ParallelTicrsAdminDelete cet = new ParallelTicrsAdminDelete(i, getUrlInput());
            Thread t1 = new Thread(cet);
            t1.start();
            //System.out.println("i from for loop :" + i);
        }     
        
        //Pause for 5 seconds, So, child threads get enough time to finish themselves.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }         
        
        
    }


}
