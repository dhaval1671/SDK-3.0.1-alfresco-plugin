package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.AccessLevel;

public class ACLAspectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetAccessLevel()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ACLAspect pojo = new ACLAspect();
        final Field field = pojo.getClass().getDeclaredField("accessLevel");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getAccessLevel();

        // then
        AccessLevel accessLevelEnum = AccessLevel.getAccessLevel("internal");
        if (null == accessLevelEnum) {
            throw new TmngCmsException.AccessLevelRuleViolationException(HttpStatus.FORBIDDEN,
                    "'accessLevel' value NOT compliant to standard values");
        }
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetAccessLevel()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final ACLAspect pojo = new ACLAspect();

        // when
        pojo.setAccessLevel("internal");

        // then
        final Field field = pojo.getClass().getDeclaredField("accessLevel");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "internal");
    }

}
