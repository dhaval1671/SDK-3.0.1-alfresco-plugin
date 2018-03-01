package gov.uspto.trademark.cms.repo.model.drive.efile;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.uspto.trademark.cms.repo.model.TradeMarkContent;
import gov.uspto.trademark.cms.repo.model.aspects.EfileAspect;

public class EfileTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetId() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final Efile pojo = new Efile();
        final Field field = pojo.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getId();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetId() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final Efile pojo = new Efile();

        // when
        pojo.setId("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("id");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetEfileAspect()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final Efile pojo = new Efile();
        final Field field = pojo.getClass().getDeclaredField("efileAspect");
        field.setAccessible(true);
        EfileAspect efileAspect = new EfileAspect();
        field.set(pojo, efileAspect);

        // when
        final EfileAspect efileAspect1 = pojo.getEfileAspect();

        // then
        assertEquals("field wasn't retrieved properly", efileAspect1, efileAspect);

    }

    @Test
    public void testSetEfileAspect()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final Efile pojo = new Efile();

        // when
        EfileAspect efileAspect = new EfileAspect();
        pojo.setEfileAspect(efileAspect);

        // then
        final Field field = pojo.getClass().getDeclaredField("efileAspect");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), efileAspect);

    }

    @Test
    public void testGetDocumentName()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final TradeMarkContent pojo = new TradeMarkContent();
        final Field field = pojo.getClass().getDeclaredField("documentName");
        field.setAccessible(true);
        field.set(pojo, "TestDoc");

        // when
        final String result = pojo.getDocumentName();

        // then
        assertEquals("field wasn't retrieved properly", result, "TestDoc");
    }

    @Test
    public void testSetDocumentName()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final TradeMarkContent pojo = new TradeMarkContent();

        // when
        pojo.setDocumentName("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("documentName");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testEfileGetDocumentType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final Efile pojo = new Efile();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, Efile.TYPE);
    }

    @Test
    public void testGetDocumentType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final TradeMarkContent pojo = new TradeMarkContent();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, TradeMarkContent.TYPE);
    }

}
