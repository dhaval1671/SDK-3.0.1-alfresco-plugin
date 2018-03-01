package gov.uspto.trademark.cms.repo.webscripts.healthstatus;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AlfrescoHealthStatusResponseTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetName() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final AlfrescoHealthStatusResponse pojo = new AlfrescoHealthStatusResponse();
        final Field field = pojo.getClass().getDeclaredField("name");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getName();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetName() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final AlfrescoHealthStatusResponse pojo = new AlfrescoHealthStatusResponse();

        // when
        pojo.setName("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("name");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetStatus() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final AlfrescoHealthStatusResponse pojo = new AlfrescoHealthStatusResponse();
        final Field field = pojo.getClass().getDeclaredField("status");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getStatus();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetStatus() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final AlfrescoHealthStatusResponse pojo = new AlfrescoHealthStatusResponse();

        // when
        pojo.setStatus("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("status");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");

    }

    @Test
    public void testGetDetails()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final AlfrescoHealthStatusResponse pojo = new AlfrescoHealthStatusResponse();
        final Field field = pojo.getClass().getDeclaredField("details");
        field.setAccessible(true);
        List<Detail> details = new ArrayList<Detail>();
        Detail d = new Detail();
        d.setDetails("a");
        field.set(pojo, details);

        // when
        final List<Detail> result = pojo.getDetails();

        // then
        assertEquals("field wasn't retrieved properly", result, details);
    }

    @Test
    public void testSetDetails()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final AlfrescoHealthStatusResponse pojo = new AlfrescoHealthStatusResponse();

        // when
        List<Detail> details = new ArrayList<Detail>();
        Detail d = new Detail();
        d.setDetails("a");
        details.add(d);
        pojo.setDetails(details);

        // then
        final Field field = pojo.getClass().getDeclaredField("details");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), details);

    }

}
