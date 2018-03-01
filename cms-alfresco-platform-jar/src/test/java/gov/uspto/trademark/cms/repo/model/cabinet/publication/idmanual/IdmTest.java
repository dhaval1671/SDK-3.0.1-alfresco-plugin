package gov.uspto.trademark.cms.repo.model.cabinet.publication.idmanual;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IdmTest {

    @Test
    public void testGetDocumentType() {
        // given
        final Idm pojo = new Idm();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, Idm.TYPE);
    }

}
