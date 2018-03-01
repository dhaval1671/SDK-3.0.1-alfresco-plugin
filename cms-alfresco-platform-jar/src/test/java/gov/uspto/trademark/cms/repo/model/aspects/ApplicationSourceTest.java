package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

public class ApplicationSourceTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetSourceMedia()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ApplicationSource pojo = new ApplicationSource();
        final Field field = pojo.getClass().getDeclaredField("sourceMedia");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getSourceMedia();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetSourceMedia()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ApplicationSource pojo = new ApplicationSource();

        // when
        pojo.setSourceMedia("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("sourceMedia");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetSourceMedium()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ApplicationSource pojo = new ApplicationSource();
        final Field field = pojo.getClass().getDeclaredField("sourceMedium");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getSourceMedium();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetSourceMedium()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ApplicationSource pojo = new ApplicationSource();

        // when
        pojo.setSourceMedium("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("sourceMedium");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

}
