package gov.uspto.trademark.cms.repo;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class BaseRuntimeExceptionTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetStatusCode()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final BaseRuntimeException pojo = new BaseRuntimeException("Test");
        final Field field = pojo.getClass().getDeclaredField("statusCode");
        field.setAccessible(true);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        field.set(pojo, status);

        // when
        final HttpStatus result = pojo.getStatusCode();

        // then
        assertEquals("field wasn't retrieved properly", result, status);
    }

    @Test
    public void testSetStatusCode()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        HttpStatus status = HttpStatus.BAD_REQUEST;
    	// given
        final BaseRuntimeException pojo = new BaseRuntimeException(status, null, null);
        // when
        // then
        final Field field = pojo.getClass().getDeclaredField("statusCode");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), status);
    }

    @Test
    public void testGetStatusText()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final BaseRuntimeException pojo = new BaseRuntimeException("Test");
        final Field field = pojo.getClass().getDeclaredField("statusText");
        field.setAccessible(true);
        field.set(pojo, "Test");

        // when
        final String result = pojo.getStatusText();

        // then
        assertEquals("field wasn't retrieved properly", result, "Test");
    }

    @Test
    public void testSetStatusText()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
    	final BaseRuntimeException pojo = new BaseRuntimeException(null, "Test", null);
        // when
        // then
        final Field field = pojo.getClass().getDeclaredField("statusText");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "Test");
    }

    @Test
    public void testGetMessage()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final BaseRuntimeException pojo = new BaseRuntimeException("Test");
        final Field field = pojo.getClass().getDeclaredField("statusText");
        field.setAccessible(true);
        field.set(pojo, "Test");

        // when
        final String result = pojo.getMessage();

        // then
        assertEquals("field wasn't retrieved properly", result, "Test");
    }

    @Test
    public void testGetInnerException()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final BaseRuntimeException pojo = new BaseRuntimeException("Test");
        final Field field = pojo.getClass().getDeclaredField("innerException");
        field.setAccessible(true);
        Throwable t = new Exception();
        field.set(pojo, t);

        // when
        final Throwable result = pojo.getInnerException();

        // then
        assertEquals("field wasn't retrieved properly", result, t);
    }

    @Test
    public void testSetInnerException()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        Exception t = new Exception();
    	final BaseRuntimeException pojo = new BaseRuntimeException(null, null, t);
        // when
        // then
        final Field field = pojo.getClass().getDeclaredField("innerException");
        field.setAccessible(true);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(field.get(pojo),t));
    }

}
