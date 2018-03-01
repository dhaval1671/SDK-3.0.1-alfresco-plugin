package gov.uspto.trademark.cms.repo.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

public class WebScriptHelperTest {

    @Before
    public void setUp() throws Exception {
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testParseJsonString() throws JsonParseException {
        String jsonString = "{\"testKey\":\"testValue\"}";
        Map<String, Serializable> result = new HashMap<String, Serializable>();
        assertNotNull(result);
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Serializable> result1 = mapper.readValue(jsonString, Map.class);
            result = WebScriptHelper.parseJson(jsonString);
            assertEquals(result, result1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNotNull(result);
    }

}
