package gov.uspto.trademark.cms.repo.webscripts.evidence.vo;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Test;

public class DeleteEvidenceRequestTest {

    @Test
    public void testGetDocumentId()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final DeleteEvidenceRequest pojo = new DeleteEvidenceRequest();
        final Field field = pojo.getClass().getDeclaredField("documentId");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getDocumentId();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetDocumentId()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final DeleteEvidenceRequest pojo = new DeleteEvidenceRequest();

        // when
        pojo.setDocumentId("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("documentId");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

}
