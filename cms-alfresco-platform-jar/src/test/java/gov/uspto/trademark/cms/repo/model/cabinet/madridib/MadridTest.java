package gov.uspto.trademark.cms.repo.model.cabinet.madridib;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MadridTest {

    @Test
    public void testGetDocumentType() {
        // given
        final Madrid pojo = new Madrid();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, Madrid.TYPE);
    }

}
