package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

public class MultimediaRelatedAspectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetAudioCodec()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("audioCodec");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getAudioCodec();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetAudioCodec()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();

        // when
        pojo.setAudioCodec("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("audioCodec");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetAudioCompressionType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("audioCompressionType");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getAudioCompressionType();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetAudioCompressionType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();

        // when
        pojo.setAudioCompressionType("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("audioCompressionType");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetVideoCodec()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("videoCodec");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getVideoCodec();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetVideoCodec()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();

        // when
        pojo.setVideoCodec("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("videoCodec");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetVideoCompressionType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("videoCompressionType");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getVideoCompressionType();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetVideoCompressionType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();

        // when
        pojo.setVideoCompressionType("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("videoCompressionType");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetMultimediaDuration()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("multimediaDuration");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getMultimediaDuration();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetMultimediaDuration()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();

        // when
        pojo.setMultimediaDuration("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("multimediaDuration");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetMultimediaStartTime()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("multimediaStartTime");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getMultimediaStartTime();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetMultimediaStartTime()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();

        // when
        pojo.setMultimediaStartTime("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("multimediaStartTime");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetMultimediaComment()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("multimediaComment");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getMultimediaComment();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetMultimediaComment()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();

        // when
        pojo.setMultimediaComment("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("multimediaComment");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetActive() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("active");
        field.setAccessible(true);
        Boolean b = new Boolean("true");
        field.set(pojo, b);

        // when
        final Boolean result = pojo.getActive();

        // then
        assertEquals("field wasn't retrieved properly", result, b);
    }

    @Test
    public void testSetActive() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();

        // when
        Boolean b = new Boolean("true");
        pojo.setActive(b);

        // then
        final Field field = pojo.getClass().getDeclaredField("active");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), b);
    }

    @Test
    public void testGetSupported()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("supported");
        field.setAccessible(true);
        Boolean b = new Boolean("true");
        field.set(pojo, b);

        // when
        final Boolean result = pojo.getSupported();

        // then
        assertEquals("field wasn't retrieved properly", result, b);
    }

    @Test
    public void testSetSupported()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();

        // when
        Boolean b = new Boolean("true");
        pojo.setSupported(b);

        // then
        final Field field = pojo.getClass().getDeclaredField("supported");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), b);
    }

    @Test
    public void testGetConverted()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("converted");
        field.setAccessible(true);
        Boolean b = new Boolean("true");
        field.set(pojo, b);

        // when
        final Boolean result = pojo.getConverted();

        // then
        assertEquals("field wasn't retrieved properly", result, b);
    }

    @Test
    public void testSetConverted()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();

        // when
        Boolean b = new Boolean("true");
        pojo.setConverted(b);

        // then
        final Field field = pojo.getClass().getDeclaredField("converted");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), b);
    }

    @Test
    public void testGetContCD() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("contCD");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getContCD();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetContCD() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();

        // when
        pojo.setContCD("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("contCD");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetDescription()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("description");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getDescription();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetDescription()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MultimediaRelatedAspect pojo = new MultimediaRelatedAspect();

        // when
        pojo.setDescription("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("description");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

}
