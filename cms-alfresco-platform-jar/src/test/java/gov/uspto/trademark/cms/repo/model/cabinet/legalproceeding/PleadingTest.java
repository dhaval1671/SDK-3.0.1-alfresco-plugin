package gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PleadingTest {

    @Test
    public void testGetDocumentType() {
        // given
        final Pleading pojo = new Pleading();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, Pleading.TYPE);
    }

}
