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
                "      {\"fund\": \"5588\",\"date\": \"07/08/2022\",\"amount\": 9},\n" +
                "      {\"fund\": \"55688\",\"date\": \"07/15/2022\",\"amount\": 30},\n" +
                "      {\"fund\": \"5588\",\"date\": \"07/15/2022\",\"amount\": 12}\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        String getNameMockJson = "{\n" +
                "  \"status\": " +
                "\"success\",\n" +
                "  \"data\": \"BillFunds\"\n" +
                "}";

        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (resource.equals("/findFundNameById")) return getNameMockJson;
                return mockJson;
            }
        });

        Contributor contributor = dm.attemptLogin("1234", "1234");

        assertNotNull(contributor);
        assertEquals("1", contributor.getId());
        assertEquals("lkk", contributor.getName());
    }

    @Test
    public void testAttemptLoginWithFailStatus() {
        String mockJson = "{\n" +
                "\"status\":\"fail\",\n" +
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
                "      {\"fund\": \"5588\",\"date\": \"07/08/2022\",\"amount\": 9},\n" +
                "      {\"fund\": \"55688\",\"date\": \"07/15/2022\",\"amount\": 30},\n" +
                "      {\"fund\": \"5588\",\"date\": \"07/15/2022\",\"amount\": 12}\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        String getNameMockJson = "{\n" +
                "  \"status\": " +
                "\"success\",\n" +
                "  \"data\": \"BillFunds\"\n" +
                "}";

        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (resource.equals("/findFundNameById")) return getNameMockJson;
                return mockJson;
            }
        });

        Contributor contributor = dm.attemptLogin("1234", "1234");

        assertNull(contributor);
    }

    @Test
    public void testAttemptLoginWithTypoForCVV() {
        String mockJson = "{\n" +
                "\"status\":\"success\",\n" +
                "  \"data\": {\n" +
                "    \"_id\": \"1\",\n" +
                "    \"name\": \"lkk\",\n" +
                "    \"email\": \"hp@com\",\n" +
                "    \"creditCardNumber\": \"1111\",\n" +
                "    \"creditCardCV\": \"222\",\n" +
                "    \"creditCardExpiryMonth\": 5,\n" +
                "    \"creditCardExpiryYear\": 25,\n" +
                "    \"creditCardPostCode\": \"15555\",\n" +
                "    \"donations\": [\n" +
                "      {\"fund\": \"55688\",\"date\": \"07/07/2022\",\"amount\": 29},\n" +
                "      {\"fund\": \"5588\",\"date\": \"07/08/2022\",\"amount\": 9},\n" +
                "      {\"fund\": \"55688\",\"date\": \"07/15/2022\",\"amount\": 30},\n" +
                "      {\"fund\": \"5588\",\"date\": \"07/15/2022\",\"amount\": 12}\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        String getNameMockJson = "{\n" +
                "  \"status\": " +
                "\"success\",\n" +
                "  \"data\": \"BillFunds\"\n" +
                "}";

        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (resource.equals("/findFundNameById")) return getNameMockJson;
                return mockJson;
            }
        });

        Contributor contributor = dm.attemptLogin("1234", "1234");

        assertNull(contributor);
    }
}
