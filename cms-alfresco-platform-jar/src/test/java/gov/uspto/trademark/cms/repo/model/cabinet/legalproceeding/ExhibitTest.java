package gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExhibitTest {

    @Test
    public void testGetDocumentType() {
        // given
        final Exhibit pojo = new Exhibit();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, Exhibit.TYPE);
    }

}
