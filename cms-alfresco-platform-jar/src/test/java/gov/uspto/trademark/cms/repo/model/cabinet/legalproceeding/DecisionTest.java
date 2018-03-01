package gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DecisionTest {

    @Test
    public void testGetDocumentType() {
        // given
        final Decision pojo = new Decision();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, Decision.TYPE);
    }

}
