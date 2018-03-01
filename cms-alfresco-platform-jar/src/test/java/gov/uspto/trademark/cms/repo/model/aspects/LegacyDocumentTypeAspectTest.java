package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

public class LegacyDocumentTypeAspectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetDocCode()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final LegacyDocumentTypeAspect pojo = new LegacyDocumentTypeAspect();
        final Field field = pojo.getClass().getDeclaredField("docCode");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getDocCode();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetDocCode()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final LegacyDocumentTypeAspect pojo = new LegacyDocumentTypeAspect();

        // when
        pojo.setDocCode("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("docCode");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetLegacyCategory()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final LegacyDocumentTypeAspect pojo = new LegacyDocumentTypeAspect();
        final Field field = pojo.getClass().getDeclaredField("legacyCategory");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getLegacyCategory();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetLegacyCategory()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final LegacyDocumentTypeAspect pojo = new LegacyDocumentTypeAspect();

        // when
        pojo.setLegacyCategory("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("legacyCategory");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

}
