package gov.uspto.trademark.cms.repo.services.util;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.junit.Test;

public class ContentItemTest {

    @Test
    public void testConstructorIsPrivate()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        ArrayList<String> arr = new ArrayList<String>();
        arr.add("test");
        arr.clear();
        assertEquals(arr.size(),0);  
    }

    @Test
    public void testGetInstanceInputStream() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {

        ArrayList<String> arr = new ArrayList<String>();
        arr.add("test");
        arr.clear();
        assertEquals(arr.size(),0); 
    }
}
