package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

public class ImageRelatedAspectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetImageHeight()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ImageRelatedAspect pojo = new ImageRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("imageHeight");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getImageHeight();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetImageHeight()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ImageRelatedAspect pojo = new ImageRelatedAspect();

        // when
        pojo.setImageHeight("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("imageHeight");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetImageWidth()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ImageRelatedAspect pojo = new ImageRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("imageWidth");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getImageWidth();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetImageWidth()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ImageRelatedAspect pojo = new ImageRelatedAspect();

        // when
        pojo.setImageWidth("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("imageWidth");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetImageResolutionX()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ImageRelatedAspect pojo = new ImageRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("imageResolutionX");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getImageResolutionX();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetImageResolutionX()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ImageRelatedAspect pojo = new ImageRelatedAspect();

        // when
        pojo.setImageResolutionX("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("imageResolutionX");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetImageResolutionY()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ImageRelatedAspect pojo = new ImageRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("imageResolutionY");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getImageResolutionY();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetImageResolutionY()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ImageRelatedAspect pojo = new ImageRelatedAspect();

        // when
        pojo.setImageResolutionY("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("imageResolutionY");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetImageColorDepth()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ImageRelatedAspect pojo = new ImageRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("imageColorDepth");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getImageColorDepth();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetImageColorDepth()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ImageRelatedAspect pojo = new ImageRelatedAspect();

        // when
        pojo.setImageColorDepth("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("imageColorDepth");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetImageCompressionType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ImageRelatedAspect pojo = new ImageRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("imageCompressionType");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getImageCompressionType();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetImageCompressionType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ImageRelatedAspect pojo = new ImageRelatedAspect();

        // when
        pojo.setImageCompressionType("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("imageCompressionType");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

}
