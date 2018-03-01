package gov.uspto.trademark.cms.repo.webscripts.efile.vo;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Document;

public class SubmissionJsonTest {

    @Test
    public void testGetDocumentId()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final SubmissionJson pojo = new SubmissionJson();
        final Field field = pojo.getClass().getDeclaredField("documentId");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getDocumentId();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetDocumentId()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final SubmissionJson pojo = new SubmissionJson();

        // when
        pojo.setDocumentId("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("documentId");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetSerialNumbers()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final SubmissionJson pojo = new SubmissionJson();
        final Field field = pojo.getClass().getDeclaredField("serialNumbers");
        field.setAccessible(true);
        List<String> a = new ArrayList<String>();
        a.add("1");
        field.set(pojo, a);

        // when
        final List<String> result = pojo.getSerialNumbers();

        // then
        assertEquals("field wasn't retrieved properly", result, a);
    }

    @Test
    public void testSetSerialNumbers()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final SubmissionJson pojo = new SubmissionJson();

        // when
        List<String> a = new ArrayList<String>();
        a.add("1");
        pojo.setSerialNumbers(a);

        // then
        final Field field = pojo.getClass().getDeclaredField("serialNumbers");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), a);
    }

    @Test
    public void testGetDocumentType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final SubmissionJson pojo = new SubmissionJson();
        final Field field = pojo.getClass().getDeclaredField("documentType");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetDocumentType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final SubmissionJson pojo = new SubmissionJson();

        // when
        pojo.setDocumentType("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("documentType");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetMetadata()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final SubmissionJson pojo = new SubmissionJson();
        final Field field = pojo.getClass().getDeclaredField("metadata");
        field.setAccessible(true);
        Document d = new Document();
        field.set(pojo, d);

        // when
        final Document result = pojo.getMetadata();

        // then
        assertEquals("field wasn't retrieved properly", result, d);
    }

    @Test
    public void testSetMetadata()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final SubmissionJson pojo = new SubmissionJson();

        // when
        Document d = new Document();
        pojo.setMetadata(d);

        // then
        final Field field = pojo.getClass().getDeclaredField("metadata");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), d);
    }

}
