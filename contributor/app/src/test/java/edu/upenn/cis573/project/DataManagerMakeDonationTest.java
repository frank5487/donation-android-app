package edu.upenn.cis573.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Map;

public class DataManagerMakeDonationTest {

    @Test
    public void testForMakeDonation() {
        String mockJson = "{\n" +
                "  \"status\":" +
                " \"success\",\n" +
                "  \"data\": {\n" +
                "    \"contributor\": \"100\",\n" +
                "    \"fund\": \"202\",\n" +
                "    \"date\": \"08/08/2022\",\n" +
                "    \"amount\": 20\n" +
                "  }\n" +
                "}";
        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
//                String donation = "{\"contributor\":\"100\",\"fund\":\"202\",\"date\":" +
//                        "\"08/08/2022\",\"amount\":" + 20 + "}";
//                String data = "\"data\":" + donation;
//                String status = "\"status\":\"success\"";
//                String response = "{" + status + "," + data + "}";
//                return response;
                return mockJson;
            }
        });

        boolean result = dm.makeDonation("1", "1", "20");
        assertTrue(result);
    }

    @Test
    public void testForMakeDonationWithNegativeDonate() {

        String mockJson = "{\n" +
                "  \"status\":" +
                " \"success\",\n" +
                "  \"data\": {\n" +
                "    \"contributor\": \"100\",\n" +
                "    \"fund\": \"202\",\n" +
                "    \"date\": \"08/08/2022\",\n" +
                "    \"amount\": -10\n" +
                "  }\n" +
                "}";
        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
//                String donation = "{\"contributor\":\"100\",\"fund\":\"202\",\"date\":" +
//                        "\"08/08/2022\",\"amount\":" + -10 + "}";
//                String data = "\"data\":" + donation;
//                String status = "\"status\":\"success\"";
//                String response = "{" + status + "," + data + "}";
//                return response;
                return mockJson;
            }
        });

        boolean result = dm.makeDonation("1", "1", "-10");
        assertFalse(result);
    }

    @Test (expected = IllegalStateException.class)
    public void testForMakeDonationWithFalseJson() {

        String mockJson = "{\n" +
                "  \"status\":" +
                " \"success\",\n" +
                "  \"data\": {\n" +
                "    \"contributor\": \"100\",\n" +
                "    \"fund\": \"202\",\n" +
                "    \"date\": \"08/08/2022\",\n" +
                "    \"amount\": 10\n" +
                "  }\n" +
                "";
        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return mockJson;
            }
        });

        boolean result = dm.makeDonation("1", "1", "10");
        assertFalse(result);
    }

    @Test
    public void testForMakeDonationWithNonExistedJson() {

        String mockJson = "{\n" +
                "  \"stats\":" +
                " \"success\",\n" +
                "  \"data\": {\n" +
                "    \"contributor\": \"100\",\n" +
                "    \"fund\": \"202\",\n" +
                "    \"date\": \"08/08/2022\",\n" +
                "    \"amount\": 10\n" +
                "  }\n" +
                "}";
        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return mockJson;
            }
        });

        boolean result = dm.makeDonation("1", "1", "10");
        assertFalse(result);
    }
}
