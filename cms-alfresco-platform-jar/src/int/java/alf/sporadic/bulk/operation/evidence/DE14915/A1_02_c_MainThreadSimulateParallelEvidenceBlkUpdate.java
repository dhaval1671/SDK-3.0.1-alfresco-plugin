package alf.sporadic.bulk.operation.evidence.DE14915;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sun.jersey.api.client.ClientResponse;

import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.aspects.ACLAspect;
import gov.uspto.trademark.cms.repo.model.aspects.ApplicationDates;
import gov.uspto.trademark.cms.repo.model.aspects.EvidenceAspect;
import gov.uspto.trademark.cms.repo.model.aspects.MigratedAspect;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;
import gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest;

public class A1_02_c_MainThreadSimulateParallelEvidenceBlkUpdate extends EvidenceBaseTest{

    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {

        int arraySize = 2;
        // create infrastructure files
        final CopyEvidenceRequest[] copyEvidenceRequests = createSampleEvidenceFiles(arraySize);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(TMConstants.DATETIME_FORMAT));
        mapper.setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);    
        String metadataOne = "";        
        CopyEvidenceRequest[] newCopyOfMetadataOne = new CopyEvidenceRequest[copyEvidenceRequests.length];
        System.arraycopy( copyEvidenceRequests, 0, newCopyOfMetadataOne, 0, copyEvidenceRequests.length );
        for(CopyEvidenceRequest cer: newCopyOfMetadataOne){
            Evidence eviMetadata = cer.getMetadata();
            eviMetadata.setModifiedByUserId("Updated By thread_"+0);
        }
        metadataOne = mapper.writeValueAsString(newCopyOfMetadataOne);
        System.out.println(metadataOne);  
        
        ObjectMapper mapperTwo = new ObjectMapper();
        mapperTwo.setDateFormat(new SimpleDateFormat(TMConstants.DATETIME_FORMAT));
        mapperTwo.setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);    
        String metadataTwo = "";        
        CopyEvidenceRequest[] newCopyOfMetadataTwo = new CopyEvidenceRequest[copyEvidenceRequests.length];
        System.arraycopy( copyEvidenceRequests, 0, newCopyOfMetadataTwo, 0, copyEvidenceRequests.length );
        for(CopyEvidenceRequest cer: newCopyOfMetadataTwo){
            Evidence eviMetadata = cer.getMetadata();
            eviMetadata.setModifiedByUserId("Updated By thread_"+1);
        }
        metadataTwo = mapperTwo.writeValueAsString(newCopyOfMetadataTwo);
        System.out.println(metadataTwo);         
        

        A1_03_a_ImplBulkEvidenceUpdate cetOne = new A1_03_a_ImplBulkEvidenceUpdate(metadataOne , 0 );
        Thread t1 = new Thread(cetOne);

        A1_03_a_ImplBulkEvidenceUpdate cetTwo = new A1_03_a_ImplBulkEvidenceUpdate(metadataTwo , 1 );
        Thread t2 = new Thread(cetTwo);        
        
        t1.start();
        t2.start();
        
/*        for (int i = 0; i < 2; i++) {
            A1_02_a_ImplBulkEvidenceUpdate cet = new A1_02_a_ImplBulkEvidenceUpdate(copyEvidenceRequests , i );
            Thread t1 = new Thread(cet);
            t1.start();
            System.out.println("i from for loop :" + i);
        }*/

    }

    private static CopyEvidenceRequest[] createSampleEvidenceFiles(int arraySize) {
        
        CopyEvidenceRequest[] copyEvidenceRequests = new CopyEvidenceRequest[arraySize];
        // create large number of evidences.
        for (int i = 0; i < arraySize; i++) {
            String serialNumber = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
            String fileName = "Evidence_" + i + ".pdf";
            Evidence eviMetadata = new Evidence();
            ACLAspect aclAsp = new ACLAspect();
            aclAsp.setAccessLevel("public");
            eviMetadata.setaCLAspect(aclAsp);
            eviMetadata.setDocumentAlias("documentAlias" + fileName);
            eviMetadata.setModifiedByUserId("User XYZ");
            EvidenceAspect ea = new EvidenceAspect();
            ea.setEvidenceCategory("evidenceCat");
            eviMetadata.setEvidenceAspect(ea);
            MigratedAspect migratedAspect = new MigratedAspect();
            migratedAspect.setMigrationMethod("migrationMethod");
            migratedAspect.setMigrationSource("migrationSource");
            eviMetadata.setMigratedAspect(migratedAspect);
            ApplicationDates ad = new ApplicationDates();

            String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
            String newstr = "2014-04-23T13:42:24.962-04:00";
            DateTimeFormatter formatter = DateTimeFormat.forPattern(DATETIME_FORMAT);
            DateTime dt = formatter.parseDateTime(newstr);
            Calendar c = Calendar.getInstance();
            c.setTime(dt.toDate());
            Date d = c.getTime();
            // System.out.println(new DateTime(d).toString(DATETIME_FORMAT));
            ad.setLoadDate(d);

            eviMetadata.setApplicationDates(ad);
            copyEvidenceRequests[i] = new CopyEvidenceRequest();
            copyEvidenceRequests[i].setDocumentId("/case/" + serialNumber + "/evidence/" + fileName);
            copyEvidenceRequests[i].setMetadata(eviMetadata);

            // create evidence file in alfresco
            testCreateEvidence(fileName, serialNumber);
            
        }
        return copyEvidenceRequests;
    }
    
    static private void testCreateEvidence(String VALUE_FILE_NAME, String VALUE_SERIAL_NUMBER) {
        Map<String, String> eviParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(VALUE_SERIAL_NUMBER);
        urlInput.setFileName(VALUE_FILE_NAME);
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), EvidenceBaseTest.VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);        
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);

        String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Evidence.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));        
    }    

}
