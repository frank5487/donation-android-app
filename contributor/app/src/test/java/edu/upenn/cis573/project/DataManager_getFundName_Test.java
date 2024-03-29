package edu.upenn.cis573.project;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.util.Log;

public class DataManager_getFundName_Test {

    @Test
    public void testGetFundName() {

        DataManager dm = new DataManager(new WebClient(null, 0) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\",\"data\":\"Snoopy\"}";
            }
        });

        String name = dm.getFundName("12345");
        // Log.v("name: ", name);
        // System.out.println(name);
        assertNotNull(name);
        assertEquals("Snoopy", name);


    }

    @Test
    public void testGetFundNameWithFailStatus() {

        DataManager dm = new DataManager(new WebClient(null, 0) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"fail\",\"data\":\"Snoopy\"}";
            }
        });

        String name = dm.getFundName("12345");
        assertNotNull(name);
        assertEquals("Unknown Fund", name);
    }

    @Test
    public void testGetFundNameWithEmptyData() {

        DataManager dm = new DataManager(new WebClient(null, 0) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"success\"}";
            }
        });

        String name = dm.getFundName("12345");
        assertNull(name);
    }

    @Test
    public void testGetFundNameWithCache() {

        String mockJson = "{\n" +
                "  \"status\":" +
                " \"success\",\n" +
                "  \"data\": \"Ahoy\"\n" +
                "}";

        DataManager dm = new DataManager(new WebClient(null, 0) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return mockJson;
            }
        });

        String name = dm.getFundName("12345");
        assertEquals("Ahoy", name);
        name = dm.getFundName("12345"); // get value from cache
    }
}
