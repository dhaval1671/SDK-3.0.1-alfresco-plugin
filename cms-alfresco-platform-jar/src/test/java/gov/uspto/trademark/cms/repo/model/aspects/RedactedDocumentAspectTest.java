package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

public class RedactedDocumentAspectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetOriginalDocumentVersion()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final RedactedDocumentAspect pojo = new RedactedDocumentAspect();
        final Field field = pojo.getClass().getDeclaredField("originalDocumentVersion");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getOriginalDocumentVersion();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetOriginalDocumentVersion()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final RedactedDocumentAspect pojo = new RedactedDocumentAspect();

        // when
        pojo.setOriginalDocumentVersion("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("originalDocumentVersion");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

}
