package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

public class EvidenceBankAspectTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testGetSource() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EvidenceBankAspect pojo = new EvidenceBankAspect();
        final Field field = pojo.getClass().getDeclaredField("source");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getSource();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetSource() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EvidenceBankAspect pojo = new EvidenceBankAspect();

        // when
        pojo.setSource("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("source");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

}
