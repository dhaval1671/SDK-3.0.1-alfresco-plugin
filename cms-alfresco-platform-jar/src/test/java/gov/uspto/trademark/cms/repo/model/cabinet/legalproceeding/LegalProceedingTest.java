package gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Test;

import gov.uspto.trademark.cms.repo.model.aspects.ACLAspect;
import gov.uspto.trademark.cms.repo.model.aspects.BusinessVersionAspect;
import gov.uspto.trademark.cms.repo.model.aspects.MigratedAspect;

public class LegalProceedingTest {

    @Test
    public void testGetDocumentType() {
        // given
        final LegalProceeding pojo = new LegalProceeding();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, LegalProceeding.TYPE);
    }

    @Test
    public void testGetProceedingNumber()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();
        final Field field = pojo.getClass().getDeclaredField("proceedingNumber");
        field.setAccessible(true);

        field.set(pojo, "1");

        // when
        final String result = pojo.getProceedingNumber();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetProceedingNumber()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();

        // when
        pojo.setProceedingNumber("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("proceedingNumber");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetProceedingType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();
        final Field field = pojo.getClass().getDeclaredField("proceedingType");
        field.setAccessible(true);

        field.set(pojo, "1");

        // when
        final String result = pojo.getProceedingType();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetProceedingType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();

        // when
        pojo.setProceedingType("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("proceedingType");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetEntryDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();
        final Field field = pojo.getClass().getDeclaredField("entryDate");
        field.setAccessible(true);

        field.set(pojo, "1");

        // when
        final String result = pojo.getEntryDate();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetEntryDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();

        // when
        pojo.setEntryDate("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("entryDate");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetIdentifier()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();
        final Field field = pojo.getClass().getDeclaredField("identifier");
        field.setAccessible(true);

        field.set(pojo, "1");

        // when
        final String result = pojo.getIdentifier();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetIdentifier()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();

        // when
        pojo.setIdentifier("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("identifier");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetBusinessVersionAspect()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();
        BusinessVersionAspect businessVersionAspect = new BusinessVersionAspect();
        final Field field = pojo.getClass().getDeclaredField("businessVersionAspect");
        field.setAccessible(true);

        field.set(pojo, businessVersionAspect);

        // when
        final BusinessVersionAspect result = pojo.getBusinessVersionAspect();

        // then
        assertEquals("field wasn't retrieved properly", result, businessVersionAspect);
    }

    @Test
    public void testSetBusinessVersionAspect()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();

        // when
        BusinessVersionAspect businessVersionAspect = new BusinessVersionAspect();
        pojo.setBusinessVersionAspect(businessVersionAspect);

        // then
        final Field field = pojo.getClass().getDeclaredField("businessVersionAspect");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), businessVersionAspect);
    }

    @Test
    public void testGetaCLAspect()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();
        final Field field = pojo.getClass().getDeclaredField("aCLAspect");
        field.setAccessible(true);
        ACLAspect aCLAspect = new ACLAspect();
        field.set(pojo, aCLAspect);

        // when
        final ACLAspect result = pojo.getaCLAspect();

        // then
        assertEquals("field wasn't retrieved properly", result, aCLAspect);
    }

    @Test
    public void testSetaCLAspect()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();
        ACLAspect aCLAspect = new ACLAspect();
        // when
        pojo.setaCLAspect(aCLAspect);

        // then
        final Field field = pojo.getClass().getDeclaredField("aCLAspect");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), aCLAspect);
    }

    @Test
    public void testGetMigratedAspect()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();
        final Field field = pojo.getClass().getDeclaredField("migratedAspect");
        field.setAccessible(true);
        MigratedAspect migratedAspect = new MigratedAspect();
        field.set(pojo, migratedAspect);

        // when
        final MigratedAspect result = pojo.getMigratedAspect();

        // then
        assertEquals("field wasn't retrieved properly", result, migratedAspect);
    }

    @Test
    public void testSetMigratedAspect()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final LegalProceeding pojo = new LegalProceeding();
        MigratedAspect migratedAspect = new MigratedAspect();
        // when
        pojo.setMigratedAspect(migratedAspect);

        // then
        final Field field = pojo.getClass().getDeclaredField("migratedAspect");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), migratedAspect);
    }
}
