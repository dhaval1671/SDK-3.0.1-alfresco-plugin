package alf.sporadic.bulk.operation.evidence.DE14915;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import alf.integration.service.all.base.ParameterValues;
import gov.uspto.trademark.cms.repo.model.aspects.ACLAspect;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;
import gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest;

public class tempMain {

    public static void main(String[] args) {

        int arraySize = 50;
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
            copyEvidenceRequests[i] = new CopyEvidenceRequest();
            copyEvidenceRequests[i].setDocumentId("/case/" + serialNumber + "/evidence/" + fileName);
            copyEvidenceRequests[i].setMetadata(eviMetadata);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);        
        try {
            String s = mapper.writeValueAsString(copyEvidenceRequests);
            System.out.println(s);
        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
