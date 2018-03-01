package gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UndesignatedTest {

    @Test
    public void testGetDocumentType() {
        // given
        final Undesignated pojo = new Undesignated();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, Undesignated.TYPE);
    }

}
