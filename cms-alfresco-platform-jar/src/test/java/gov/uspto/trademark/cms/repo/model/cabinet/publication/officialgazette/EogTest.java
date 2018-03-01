/**
 * 
 */
package gov.uspto.trademark.cms.repo.model.cabinet.publication.officialgazette;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author kvenugopalan1
 *
 */
public class EogTest {

    /**
     * Test method for
     * {@link gov.uspto.trademark.cms.repo.model.cabinet.publication.officialgazette.Eog#getDocumentType()}.
     */
    @Test
    public void testGetDocumentType() {
        // given
        final Eog pojo = new Eog();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, Eog.TYPE);
    }

}
