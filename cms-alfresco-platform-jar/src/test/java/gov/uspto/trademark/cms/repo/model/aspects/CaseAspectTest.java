package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CaseAspectTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetSerialNumber()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final CaseAspect pojo = new CaseAspect();
        final Field field = pojo.getClass().getDeclaredField("serialNumber");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getSerialNumber();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetSerialNumber()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final CaseAspect pojo = new CaseAspect();

        // when
        pojo.setSerialNumber("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("serialNumber");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

}
