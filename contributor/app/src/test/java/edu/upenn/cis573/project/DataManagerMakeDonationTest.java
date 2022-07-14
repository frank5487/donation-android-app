package edu.upenn.cis573.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Map;

public class DataManagerMakeDonationTest {

    @Test
    public void testForMakeDonation() {

        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                String donation = "{\"contributor\":\"100\",\"fund\":\"202\",\"date\":" +
                        "\"08/08/2022\",\"amount\":" + 20 + "}";
                String data = "\"data\":" + donation;
                String status = "\"status\":\"success\"";
                String response = "{" + status + "," + data + "}";
                return response;
            }
        });

        boolean result = dm.makeDonation("1", "1", "20");
        assertTrue(result);
    }

    @Test
    public void testForMakeDonationTwo() {

        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                String donation = "{\"contributor\":\"100\",\"fund\":\"202\",\"date\":" +
                        "\"08/08/2022\",\"amount\":" + -10 + "}";
                String data = "\"data\":" + donation;
                String status = "\"status\":\"success\"";
                String response = "{" + status + "," + data + "}";
                return response;
            }
        });

        boolean result = dm.makeDonation("1", "1", "-10");
        assertFalse(result);
    }

    @Test
    public void testForMakeDonationThree() {

        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                String donation = "{\"contributor\":\"100\",\"fund\":\"202\",\"date\":" +
                        "\"08/08/2022\",\"amount\":" + -10 + "}";
                String data = "\"data\":" + donation;
                String status = "\"status\":\"success\"";
                String response = "{" + status + "," + data + "";
                return response;
            }
        });

        boolean result = dm.makeDonation("1", "1", "10");
        assertFalse(result);
    }
}
