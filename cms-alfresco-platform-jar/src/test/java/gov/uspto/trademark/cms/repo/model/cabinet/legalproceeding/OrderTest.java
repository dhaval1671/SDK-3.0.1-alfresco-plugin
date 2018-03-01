package gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OrderTest {

    @Test
    public void testGetDocumentType() {
        // given
        final Order pojo = new Order();

        // when
        final String result = pojo.getDocumentType();

        // then
        assertEquals("field wasn't retrieved properly", result, Order.TYPE);
    }

}
