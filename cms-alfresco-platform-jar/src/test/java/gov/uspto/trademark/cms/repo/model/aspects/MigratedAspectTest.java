package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

public class MigratedAspectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetMigrationMethod()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MigratedAspect pojo = new MigratedAspect();
        final Field field = pojo.getClass().getDeclaredField("migrationMethod");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getMigrationMethod();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetMigrationMethod()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MigratedAspect pojo = new MigratedAspect();

        // when
        pojo.setMigrationMethod("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("migrationMethod");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetMigrationSource()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MigratedAspect pojo = new MigratedAspect();
        final Field field = pojo.getClass().getDeclaredField("migrationSource");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getMigrationSource();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetMigrationSource()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final MigratedAspect pojo = new MigratedAspect();

        // when
        pojo.setMigrationSource("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("migrationSource");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

}
