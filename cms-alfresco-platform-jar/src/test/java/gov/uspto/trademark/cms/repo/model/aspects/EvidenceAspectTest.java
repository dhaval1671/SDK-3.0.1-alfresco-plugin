package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EvidenceAspectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetEvidenceSourceUrl()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EvidenceAspect pojo = new EvidenceAspect();
        final Field field = pojo.getClass().getDeclaredField("evidenceSourceUrl");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getEvidenceSourceUrl();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetEvidenceSourceUrl()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EvidenceAspect pojo = new EvidenceAspect();

        // when
        pojo.setEvidenceSourceUrl("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("evidenceSourceUrl");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetEvidenceSourceType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EvidenceAspect pojo = new EvidenceAspect();
        final Field field = pojo.getClass().getDeclaredField("evidenceSourceType");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getEvidenceSourceType();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetEvidenceSourceType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EvidenceAspect pojo = new EvidenceAspect();

        // when
        pojo.setEvidenceSourceType("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("evidenceSourceType");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetEvidenceSourceTypeId()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EvidenceAspect pojo = new EvidenceAspect();
        final Field field = pojo.getClass().getDeclaredField("evidenceSourceTypeId");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getEvidenceSourceTypeId();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetEvidenceSourceTypeId()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EvidenceAspect pojo = new EvidenceAspect();

        // when
        pojo.setEvidenceSourceTypeId("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("evidenceSourceTypeId");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetEvidenceGroupNames()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EvidenceAspect pojo = new EvidenceAspect();
        final Field field = pojo.getClass().getDeclaredField("evidenceGroupNames");
        field.setAccessible(true);
        List<String> a = new ArrayList<String>();
        a.add("Test");
        field.set(pojo, a);

        // when
        final List<String> result = pojo.getEvidenceGroupNames();

        // then
        assertEquals("field wasn't retrieved properly", result, a);
    }

    @Test
    public void testSetEvidenceGroupNames()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EvidenceAspect pojo = new EvidenceAspect();

        // when
        List<String> a = new ArrayList<String>();
        a.add("Test");
        pojo.setEvidenceGroupNames(a);

        // then
        final Field field = pojo.getClass().getDeclaredField("evidenceGroupNames");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), a);
    }

    @Test
    public void testGetEvidenceCategory()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EvidenceAspect pojo = new EvidenceAspect();
        final Field field = pojo.getClass().getDeclaredField("evidenceCategory");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getEvidenceCategory();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetEvidenceCategory()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EvidenceAspect pojo = new EvidenceAspect();

        // when
        pojo.setEvidenceCategory("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("evidenceCategory");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

}
