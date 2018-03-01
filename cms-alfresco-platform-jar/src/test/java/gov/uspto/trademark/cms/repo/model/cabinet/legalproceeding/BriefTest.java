package gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BriefTest {

    @Test
    public void testGetDocumentType() {
        // given
        final Brief pojo = new Brief();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, Brief.TYPE);
    }

}
