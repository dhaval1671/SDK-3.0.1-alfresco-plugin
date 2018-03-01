package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class ApplicationDatesTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetMailDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ApplicationDates pojo = new ApplicationDates();
        final Field field = pojo.getClass().getDeclaredField("mailDate");
        field.setAccessible(true);
        Date d = new Date();
        field.set(pojo, d);

        // when
        final Date result = pojo.getMailDate();

        // then
        assertEquals("field wasn't retrieved properly", result, d);
    }

    @Test
    public void testSetMailDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ApplicationDates pojo = new ApplicationDates();

        // when
        Date d = new Date();
        pojo.setMailDate(d);

        // then
        final Field field = pojo.getClass().getDeclaredField("mailDate");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), d);
    }

    @Test
    public void testGetScanDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ApplicationDates pojo = new ApplicationDates();
        final Field field = pojo.getClass().getDeclaredField("scanDate");
        field.setAccessible(true);
        Date d = new Date();
        field.set(pojo, d);

        // when
        final Date result = pojo.getScanDate();

        // then
        assertEquals("field wasn't retrieved properly", result, d);
    }

    @Test
    public void testSetScanDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ApplicationDates pojo = new ApplicationDates();

        // when
        Date d = new Date();
        pojo.setScanDate(d);

        // then
        final Field field = pojo.getClass().getDeclaredField("scanDate");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), d);
    }

    @Test
    public void testGetLoadDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ApplicationDates pojo = new ApplicationDates();
        final Field field = pojo.getClass().getDeclaredField("loadDate");
        field.setAccessible(true);
        Date d = new Date();
        field.set(pojo, d);

        // when
        final Date result = pojo.getLoadDate();

        // then
        assertEquals("field wasn't retrieved properly", result, d);
    }

    @Test
    public void testSetLoadDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ApplicationDates pojo = new ApplicationDates();

        // when
        Date d = new Date();
        pojo.setLoadDate(d);

        // then
        final Field field = pojo.getClass().getDeclaredField("loadDate");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), d);
    }

}
