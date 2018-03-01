package gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MotionTest {

    @Test
    public void testGetDocumentType() {
        // given
        final Motion pojo = new Motion();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, Motion.TYPE);
    }

}
