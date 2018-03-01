package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class DocumentVersionAspectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetDocumentStartDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final DocumentVersionAspect pojo = new DocumentVersionAspect();
        final Field field = pojo.getClass().getDeclaredField("documentStartDate");
        field.setAccessible(true);
        Date d = new Date();
        field.set(pojo, d);

        // when
        final Date result = pojo.getDocumentStartDate();

        // then
        assertEquals("field wasn't retrieved properly", result, d);
    }

    @Test
    public void testSetDocumentStartDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final DocumentVersionAspect pojo = new DocumentVersionAspect();

        // when
        Date d = new Date();
        pojo.setDocumentStartDate(d);

        // then
        final Field field = pojo.getClass().getDeclaredField("documentStartDate");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), d);
    }

    @Test
    public void testGetDocumentEndDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final DocumentVersionAspect pojo = new DocumentVersionAspect();
        final Field field = pojo.getClass().getDeclaredField("documentEndDate");
        field.setAccessible(true);
        Date d = new Date();
        field.set(pojo, d);

        // when
        final Date result = pojo.getDocumentEndDate();

        // then
        assertEquals("field wasn't retrieved properly", result, d);
    }

    @Test
    public void testSetDocumentEndDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final DocumentVersionAspect pojo = new DocumentVersionAspect();

        // when
        Date d = new Date();
        pojo.setDocumentEndDate(d);

        // then
        final Field field = pojo.getClass().getDeclaredField("documentEndDate");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), d);
    }

    @Test
    public void testGetNewContentIndicator()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final DocumentVersionAspect pojo = new DocumentVersionAspect();
        final Field field = pojo.getClass().getDeclaredField("newContentIndicator");
        field.setAccessible(true);
        Boolean b = new Boolean("true");
        field.set(pojo, b);

        // when
        final Boolean result = pojo.getNewContentIndicator();

        // then
        assertEquals("field wasn't retrieved properly", result, b);
    }

    @Test
    public void testSetNewContentIndicator()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final DocumentVersionAspect pojo = new DocumentVersionAspect();

        // when
        Boolean b = new Boolean("true");
        pojo.setNewContentIndicator(b);

        // then
        final Field field = pojo.getClass().getDeclaredField("newContentIndicator");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), b);
    }

}
