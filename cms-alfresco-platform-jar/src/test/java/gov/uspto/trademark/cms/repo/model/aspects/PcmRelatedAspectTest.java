package gov.uspto.trademark.cms.repo.model.aspects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class PcmRelatedAspectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetPcmABSN()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmABSN");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getPcmABSN();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetPcmABSN()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        pojo.setPcmABSN("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmABSN");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetPcmAFN() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmAFN");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getPcmAFN();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetPcmAFN() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        pojo.setPcmAFN("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmAFN");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetPcmNoteNum()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmNoteNum");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getPcmNoteNum();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetPcmNoteNum()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        pojo.setPcmNoteNum("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmNoteNum");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetPcmSeqNum()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmSeqNum");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getPcmSeqNum();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetPcmSeqNum()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        pojo.setPcmSeqNum("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmSeqNum");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetPcmReplaceBySeqNum()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmReplaceBySeqNum");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getPcmReplaceBySeqNum();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetPcmReplaceBySeqNum()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        pojo.setPcmReplaceBySeqNum("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmReplaceBySeqNum");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetPcmFileSuffix()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmFileSuffix");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getPcmFileSuffix();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetPcmFileSuffix()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        pojo.setPcmFileSuffix("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmFileSuffix");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetPcmMediaType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmMediaType");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getPcmMediaType();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetPcmMediaType()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        pojo.setPcmMediaType("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmMediaType");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetPcmOriginalFileName()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmOriginalFileName");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getPcmOriginalFileName();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetPcmOriginalFileName()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        pojo.setPcmOriginalFileName("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmOriginalFileName");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetPcmRsn() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmRsn");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getPcmRsn();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetPcmRsn() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        pojo.setPcmRsn("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmRsn");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetPcmFileSize()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmFileSize");
        field.setAccessible(true);
        field.set(pojo, "1");

        // when
        final String result = pojo.getPcmFileSize();

        // then
        assertEquals("field wasn't retrieved properly", result, "1");
    }

    @Test
    public void testSetPcmFileSize()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        pojo.setPcmFileSize("1");

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmFileSize");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), "1");
    }

    @Test
    public void testGetPcmCreateDateTime()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmCreateDateTime");
        field.setAccessible(true);
        Date d = new Date();
        field.set(pojo, d);

        // when
        final Date result = pojo.getPcmCreateDateTime();

        // then
        assertEquals("field wasn't retrieved properly", result, d);
    }

    @Test
    public void testSetPcmCreateDateTime()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        Date d = new Date();
        pojo.setPcmCreateDateTime(d);

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmCreateDateTime");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), d);
    }

    @Test
    public void testGetPcmUpdateDateTime()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmUpdateDateTime");
        field.setAccessible(true);
        Date d = new Date();
        field.set(pojo, d);

        // when
        final Date result = pojo.getPcmUpdateDateTime();

        // then
        assertEquals("field wasn't retrieved properly", result, d);
    }

    @Test
    public void testSetPcmUpdateDateTime()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        Date d = new Date();
        pojo.setPcmUpdateDateTime(d);

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmUpdateDateTime");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), d);
    }

    @Test
    public void testGetPcmLastModifiedDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmLastModifiedDate");
        field.setAccessible(true);
        Date d = new Date();
        field.set(pojo, d);

        // when
        final Date result = pojo.getPcmLastModifiedDate();

        // then
        assertEquals("field wasn't retrieved properly", result, d);
    }

    @Test
    public void testSetPcmLastModifiedDate()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        Date d = new Date();
        pojo.setPcmLastModifiedDate(d);

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmLastModifiedDate");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), d);
    }

    @Test
    public void testGetPcmOracleApplyTime()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();
        final Field field = pojo.getClass().getDeclaredField("pcmOracleApplyTime");
        field.setAccessible(true);
        Date d = new Date();
        field.set(pojo, d);

        // when
        final Date result = pojo.getPcmOracleApplyTime();

        // then
        assertEquals("field wasn't retrieved properly", result, d);
    }

    @Test
    public void testSetPcmOracleApplyTime()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // given
        final PcmRelatedAspect pojo = new PcmRelatedAspect();

        // when
        Date d = new Date();
        pojo.setPcmOracleApplyTime(d);

        // then
        final Field field = pojo.getClass().getDeclaredField("pcmOracleApplyTime");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(pojo), d);
    }

}
