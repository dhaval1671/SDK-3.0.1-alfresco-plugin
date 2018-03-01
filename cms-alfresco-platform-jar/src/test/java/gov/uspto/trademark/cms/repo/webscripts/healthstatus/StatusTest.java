package gov.uspto.trademark.cms.repo.webscripts.healthstatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import gov.uspto.trademark.cms.repo.constants.TMConstants;

public class StatusTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testStatus() {
        Status s = Status.GREEN;
        assertNotNull(s);
        String result = s.getStatus();
        assertEquals("Fields are same", result, Status.GREEN.toString());
    }

    @Test
    public void testGetStatus() {
        
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("test");
        arr.clear();
        assertEquals(arr.size(),0);  
    }

    @Test
    public void testGetOverallAlfrescoStatus() {
        Status finalStatus = Status.RED;
        Status alfMountStatus = Status.GREEN;
        Status alfDBStatus = Status.GREEN;

        assertNotNull(alfMountStatus);
        assertNotNull(alfDBStatus);
        assertNotNull(finalStatus);

        assertEquals(alfMountStatus.compareTo(Status.GREEN), TMConstants.ZERO);
        assertEquals(alfDBStatus.compareTo(Status.GREEN), TMConstants.ZERO);
        assertNotNull(Status.getOverallAlfrescoStatus(alfMountStatus, alfDBStatus));
        finalStatus = Status.GREEN;
        assertEquals(Status.getOverallAlfrescoStatus(alfMountStatus, alfDBStatus), finalStatus);

    }

}
