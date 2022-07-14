package edu.upenn.cis573.project;

import org.json.JSONException;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.util.Log;

public class DataManagerAttemptLoginTest {

    @Test
    public void testAttemptLogin() throws JSONException {
        String mockJson = "{\n" +
                "\"status\":\"success\",\n" +
                "  \"data\": {\n" +
                "    \"_id\": \"1\",\n" +
                "    \"name\": \"lkk\",\n" +
                "    \"email\": \"hp@com\",\n" +
                "    \"creditCardNumber\": \"1111\",\n" +
                "    \"creditCardCVV\": \"222\",\n" +
                "    \"creditCardExpiryMonth\": 5,\n" +
                "    \"creditCardExpiryYear\": 25,\n" +
                "    \"creditCardPostCode\": \"15555\",\n" +
                "    \"donations\": [\n" +
                "      {\"fund\": \"55688\",\"date\": \"07/07/2022\",\"amount\": 29},\n" +
                "      {\"fund\": \"5588\",\"date\": \"07/08/2022\",\"amount\": 9}\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                String donations = "[{\"fund\":\"55688\",\"date\":\"07/07/2022\",\"amount\":" + 29 + "}]";
                String contributorInfo = "\"_id\":\"1\",\"name\":\"lkk\",\"email\":\"hp@com\"," +
                        "\"creditCardNumber\":\"1111\",\"creditCardCVV\":\"222\"," +
                        "\"creditCardExpiryMonth\":" + 05 + ",\"creditCardExpiryYear\":"+ 25 + "," +
                        "\"creditCardPostCode\":\"15555\",\"donations\":" + donations;
                String data = "\"data\":" + "{" + contributorInfo + "}";
                String status = "\"status\":\"success\"";
                String response = "{" + status + "," + data + "}";
                return response;
            }
        });

        Contributor contributor = dm.attemptLogin("1234", "1234");

        assertNotNull(contributor);
        assertEquals("1", contributor.getId());
        assertEquals("lkk", contributor.getName());
    }

    @Test
    public void testAttemptLoginTwo() {
        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                String donations = "[{\"fund\":\"55688\",\"date\":\"07/07/2022\",\"amount\":" + 29 + "}]";
                String contributorInfo = "\"_id\":\"1\",\"name\":\"lkk\",\"email\":\"hp@com\"," +
                        "\"creditCardNumber\":\"1111\",\"creditCardCVV\":\"222\"," +
                        "\"creditCardExpiryMonth\":" + 05 + ",\"creditCardExpiryYear\":"+ 25 + "," +
                        "\"creditCardPostCode\":\"15555\",\"donations\":" + donations;
                String data = "\"data\":" + "{" + contributorInfo + "}";
                String status = "\"status\":\"fail\"";
                String response = "{" + status + "," + data + "}";
                return response;
            }
        });

        Contributor contributor = dm.attemptLogin("1234", "1234");

        assertNull(contributor);
    }

    @Test
    public void testAttemptLoginThree() {
        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                String donations = "[{\"fund\":\"55688\",\"date\":\"07/07/2022\",\"amount\":" + 29 + "}]";
                String contributorInfo = "\"_id\":\"1\",\"name\":\"lkk\",\"email\":\"hp@com\"," +
                        "\"creditCardNumber\":\"1111\",\"creditCardCVV\":\"222\"," +
                        "\"creditCardExpiryMonth\":" + 05 + ",\"creditCardExpiryYear\":"+ 25 + "," +
                        "\"creditCardPostCode\":\"15555\",\"donations\":" + donations;
                String data = "\"data\":" + "{" + contributorInfo + "}";
                String status = "\"status\":\"fail\"";
                String response = "{" + status + "," + data + "";
                return response;
            }
        });

        Contributor contributor = dm.attemptLogin("1234", "1234");

        assertNull(contributor);
    }
}
