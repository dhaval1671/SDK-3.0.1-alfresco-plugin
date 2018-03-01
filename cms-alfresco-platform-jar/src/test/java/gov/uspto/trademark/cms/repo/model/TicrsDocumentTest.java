package gov.uspto.trademark.cms.repo.model;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Test;

import gov.uspto.trademark.cms.repo.model.aspects.ACLAspect;
import gov.uspto.trademark.cms.repo.model.aspects.ApplicationDates;
import gov.uspto.trademark.cms.repo.model.aspects.ApplicationSource;
import gov.uspto.trademark.cms.repo.model.aspects.CaseAspect;
import gov.uspto.trademark.cms.repo.model.aspects.LegacyDocumentTypeAspect;
import gov.uspto.trademark.cms.repo.model.aspects.MigratedAspect;
import gov.uspto.trademark.cms.repo.model.aspects.RedactedDocumentAspect;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Document;

public class TicrsDocumentTest {

    @Test
    public void testGetDocumentType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final TicrsDocument pojo = new TicrsDocument();
        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, TicrsDocument.TYPE);
    }

    @Test
    public void testGetRedactedDocument()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        final Field field = pojo.getClass().getDeclaredField("redactedDocument");
        field.setAccessible(true);
        RedactedDocumentAspect redactedDocumentAspect = new RedactedDocumentAspect();
        field.set(pojo, redactedDocumentAspect);

        // when
        final RedactedDocumentAspect result = pojo.getRedactedDocument();

        // then
        assertEquals("field wasn't retrieved properly", result, redactedDocumentAspect);
    }

    @Test
    public void testSetRedactedDocument()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        RedactedDocumentAspect redactedDocumentAspect = new RedactedDocumentAspect();
        // when
        pojo.setRedactedDocument(redactedDocumentAspect);

        // then
        final Field field = pojo.getClass().getDeclaredField("redactedDocument");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), redactedDocumentAspect);
    }

    @Test
    public void testGetLegacyDocumentType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        final Field field = pojo.getClass().getDeclaredField("legacyDocumentType");
        field.setAccessible(true);
        LegacyDocumentTypeAspect legacyDocumentType = new LegacyDocumentTypeAspect();
        field.set(pojo, legacyDocumentType);

        // when
        final LegacyDocumentTypeAspect result = pojo.getLegacyDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, legacyDocumentType);
    }

    @Test
    public void testSetLegacyDocumentType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        LegacyDocumentTypeAspect legacyDocumentType = new LegacyDocumentTypeAspect();
        // when
        pojo.setLegacyDocumentType(legacyDocumentType);

        // then
        final Field field = pojo.getClass().getDeclaredField("legacyDocumentType");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), legacyDocumentType);
    }

    @Test
    public void testGetDocumentAlias()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        final Field field = pojo.getClass().getDeclaredField("documentAlias");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getDocumentAlias();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetDocumentAlias()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        // when
        pojo.setDocumentAlias("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("documentAlias");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetaCLAspect()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
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
        final Document pojo = new Document();
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
        final Document pojo = new Document();
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
        final Document pojo = new Document();
        MigratedAspect migratedAspect = new MigratedAspect();
        // when
        pojo.setMigratedAspect(migratedAspect);

        // then
        final Field field = pojo.getClass().getDeclaredField("migratedAspect");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), migratedAspect);
    }

    @Test
    public void testGetApplicationSource()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        final Field field = pojo.getClass().getDeclaredField("applicationSource");
        field.setAccessible(true);
        ApplicationSource applicationSource = new ApplicationSource();
        field.set(pojo, applicationSource);

        // when
        final ApplicationSource result = pojo.getApplicationSource();

        // then
        assertEquals("field wasn't retrieved properly", result, applicationSource);
    }

    @Test
    public void testSetApplicationSource()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        ApplicationSource applicationSource = new ApplicationSource();
        // when
        pojo.setApplicationSource(applicationSource);

        // then
        final Field field = pojo.getClass().getDeclaredField("applicationSource");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), applicationSource);
    }

    @Test
    public void testGetApplicationDates()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        final Field field = pojo.getClass().getDeclaredField("applicationDates");
        field.setAccessible(true);
        ApplicationDates applicationDates = new ApplicationDates();
        field.set(pojo, applicationDates);

        // when
        final ApplicationDates result = pojo.getApplicationDates();

        // then
        assertEquals("field wasn't retrieved properly", result, applicationDates);
    }

    @Test
    public void testSetApplicationDates()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        ApplicationDates applicationDates = new ApplicationDates();
        // when
        pojo.setApplicationDates(applicationDates);

        // then
        final Field field = pojo.getClass().getDeclaredField("applicationDates");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), applicationDates);
    }

    @Test
    public void testGetCaseAspect()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        final Field field = pojo.getClass().getDeclaredField("caseAspect");
        field.setAccessible(true);
        CaseAspect caseAspect = new CaseAspect();
        field.set(pojo, caseAspect);

        // when
        final CaseAspect result = pojo.getCaseAspect();

        // then
        assertEquals("field wasn't retrieved properly", result, caseAspect);
    }

    @Test
    public void testSetCaseAspect()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        CaseAspect caseAspect = new CaseAspect();
        // when
        pojo.setCaseAspect(caseAspect);

        // then
        final Field field = pojo.getClass().getDeclaredField("caseAspect");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), caseAspect);
    }

    @Test
    public void testGetCreatedByUserId()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        final Field field = pojo.getClass().getDeclaredField("createdByUserId");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getCreatedByUserId();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetCreatedByUserId()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        CaseAspect caseAspect = new CaseAspect();
        // when
        pojo.setCaseAspect(caseAspect);

        // then
        final Field field = pojo.getClass().getDeclaredField("caseAspect");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), caseAspect);
    }

    @Test
    public void testGetModifiedByUserId()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        final Field field = pojo.getClass().getDeclaredField("modifiedByUserId");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getModifiedByUserId();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetModifiedByUserId()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        // when
        pojo.setModifiedByUserId("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("modifiedByUserId");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetRedactionLevel()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        final Field field = pojo.getClass().getDeclaredField("redactionLevel");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getRedactionLevel();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetRedactionLevel()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Document pojo = new Document();
        // when
        pojo.setRedactionLevel("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("redactionLevel");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

}
