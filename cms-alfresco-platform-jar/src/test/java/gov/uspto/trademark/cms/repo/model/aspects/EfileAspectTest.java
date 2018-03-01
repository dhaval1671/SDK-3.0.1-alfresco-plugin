package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class EfileAspectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetCustomProperties()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Map<String, String> customProperties = new HashMap<String, String>();
        // given
        final EfileAspect pojo = new EfileAspect();
        final Field field = pojo.getClass().getDeclaredField("customProperties");
        field.setAccessible(true);
        customProperties.put("1", "TESTA");
        field.set(pojo, customProperties);

        // when
        final Map<String, String> result = pojo.getCustomProperties();

        // then
        assertEquals("field wasn't retrieved properly", result, customProperties);
    }

    @Test
    public void testSetCustomProperties()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final EfileAspect pojo = new EfileAspect();

        // when
        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put("1", "TESTA");
        pojo.setCustomProperties(customProperties);

        // then
        final Field field = pojo.getClass().getDeclaredField("customProperties");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), customProperties);
    }

}
