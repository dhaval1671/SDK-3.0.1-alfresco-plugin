package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class BusinessVersionAspectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetEffectiveStartDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final BusinessVersionAspect pojo = new BusinessVersionAspect();
        final Field field = pojo.getClass().getDeclaredField("effectiveStartDate");
        field.setAccessible(true);
        Date d = new Date();
        field.set(pojo, d);

        // when
        final Date result = pojo.getEffectiveStartDate();

        // then
        assertEquals("field wasn't retrieved properly", result, d);
    }

    @Test
    public void testSetEffectiveStartDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final BusinessVersionAspect pojo = new BusinessVersionAspect();

        // when
        Date d = new Date();
        pojo.setEffectiveStartDate(d);

        // then
        final Field field = pojo.getClass().getDeclaredField("effectiveStartDate");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), d);
    }

    @Test
    public void testGetEffectiveEndDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final BusinessVersionAspect pojo = new BusinessVersionAspect();
        final Field field = pojo.getClass().getDeclaredField("effectiveEndDate");
        field.setAccessible(true);
        Date d = new Date();
        field.set(pojo, d);

        // when
        final Date result = pojo.getEffectiveEndDate();

        // then
        assertEquals("field wasn't retrieved properly", result, d);
    }

    @Test
    public void testSetEffectiveEndDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final BusinessVersionAspect pojo = new BusinessVersionAspect();

        // when
        Date d = new Date();
        pojo.setEffectiveEndDate(d);

        // then
        final Field field = pojo.getClass().getDeclaredField("effectiveEndDate");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), d);
    }

}
