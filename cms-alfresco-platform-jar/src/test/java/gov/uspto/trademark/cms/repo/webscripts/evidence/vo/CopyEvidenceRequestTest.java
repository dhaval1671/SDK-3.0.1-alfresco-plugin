/**
 * 
 */
package gov.uspto.trademark.cms.repo.webscripts.evidence.vo;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Test;

import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;

/**
 * @author kvenugopalan1
 *
 */
public class CopyEvidenceRequestTest {

    /**
     * Test method for
     * {@link gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest#getDocumentId()}.
     */
    @Test
    public void testGetDocumentId()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final CopyEvidenceRequest pojo = new CopyEvidenceRequest();
        final Field field = pojo.getClass().getDeclaredField("documentId");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getDocumentId();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    /**
     * Test method for
     * {@link gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest#setDocumentId(java.lang.String)}.
     */
    @Test
    public void testSetDocumentId()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final CopyEvidenceRequest pojo = new CopyEvidenceRequest();

        // when
        pojo.setDocumentId("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("documentId");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    /**
     * Test method for
     * {@link gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest#getMetadata()}.
     */
    @Test
    public void testGetMetadata()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final CopyEvidenceRequest pojo = new CopyEvidenceRequest();
        final Field field = pojo.getClass().getDeclaredField("metadata");
        field.setAccessible(true);
        Evidence evidence = new Evidence();
        field.set(pojo, evidence);

        // when
        final Evidence result = pojo.getMetadata();

        // then
        assertEquals("field wasn't retrieved properly", result, evidence);
    }

    /**
     * Test method for
     * {@link gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest#setMetadata(gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence)}.
     */
    @Test
    public void testSetMetadata()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final CopyEvidenceRequest pojo = new CopyEvidenceRequest();

        // when
        Evidence evidence = new Evidence();
        pojo.setMetadata(evidence);

        // then
        final Field field = pojo.getClass().getDeclaredField("metadata");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), evidence);
    }

}
