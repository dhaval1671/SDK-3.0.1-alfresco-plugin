package gov.uspto.trademark.cms.repo.identifiers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GlobalIdTest {

    @Test
    public void testSplit() {
        GlobalId gi = new GlobalId();
        String[] strArr = gi.split("res:343:444");
        assertNotNull(strArr);
    }

    @Test
    public void testIsValid() {
        //sample global id "abc:777:123"
        GlobalId gi = new GlobalId();
        boolean status = gi.isValid("res:343:444");
        assertTrue(status);
    }

}
