package edu.upenn.cis573.project;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DataManagerSignUpTest {

    private DataManager dm;
    private Map<String, Object> map;

    @Test(expected=IllegalStateException.class)
    public void testSignUp_WebClientIsNull() {

        dm = new DataManager(null);
        map = new HashMap<>();
        map.put("login", "123");
        map.put("password", "123");
        map.put("name", "asd");
        map.put("email", "hot@eee.com");
        map.put("creditCardNumber", "1234");
        map.put("creditCardCVV", "123");
        map.put("creditCardExpiryMonth", "08");
        map.put("creditCardExpiryYear", "2029");
        map.put("creditCardPostCode", "55555");
        dm.createContributor(map);
        fail("DataManager.createContributor does not throw IllegalStateException when WebClient is null");

    }

    @Test(expected=IllegalArgumentException.class)
    public void testCreateContributor_ContributorInfoIsNull() {

        dm = new DataManager(new WebClient("10.0.2.2", 3001));
        dm.createContributor(null);
        fail("DataManager.createContributor does not throw IllegalArgumentxception when contributorId is null");

    }

    @Test(expected=IllegalStateException.class)
    public void testCreateContributor_WebClientCannotConnectToServer() {

        dm = new DataManager(new WebClient("10.0.2.2", 3002));
        map = new HashMap<>();
        map.put("login", "123");
        map.put("password", "123");
        map.put("name", "asd");
        map.put("email", "hot@eee.com");
        map.put("creditCardNumber", "1234");
        map.put("creditCardCVV", "123");
        map.put("creditCardExpiryMonth", "08");
        map.put("creditCardExpiryYear", "2029");
        map.put("creditCardPostCode", "55555");
        dm.createContributor(map);
        fail("DataManager.createContributor does not throw IllegalStateException when WebClient cannot connect to server");

    }

    @Test(expected=IllegalStateException.class)
    public void testCreateContributor_WebClientReturnsNull() {

        dm = new DataManager(new WebClient("10.0.2.2", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return null;
            }
        });
        map = new HashMap<>();
        map.put("login", "123");
        map.put("password", "123");
        map.put("name", "asd");
        map.put("email", "hot@eee.com");
        map.put("creditCardNumber", "1234");
        map.put("creditCardCVV", "123");
        map.put("creditCardExpiryMonth", "08");
        map.put("creditCardExpiryYear", "2029");
        map.put("creditCardPostCode", "55555");
        dm.createContributor(map);
        fail("DataManager.getMakeDonation does not throw IllegalStateException when WebClient returns null");

    }


    @Test(expected=IllegalStateException.class)
    public void testCreateContributor_WebClientReturnsError() {

        dm = new DataManager(new WebClient("10.0.2.2", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"error\",\"error\":\"An unexpected database error occurred\"}";
            }
        });
        map = new HashMap<>();
        map.put("login", "123");
        map.put("password", "123");
        map.put("name", "asd");
        map.put("email", "hot@eee.com");
        map.put("creditCardNumber", "1234");
        map.put("creditCardCVV", "123");
        map.put("creditCardExpiryMonth", "08");
        map.put("creditCardExpiryYear", "2029");
        map.put("creditCardPostCode", "55555");
        dm.createContributor(map);
        fail("DataManager.createContributor does not throw IllegalStateException when WebClient returns error");

    }

    @Test(expected=IllegalStateException.class)
    public void testCreateContributor_WebClientReturnsMalformedJSON() {

        dm = new DataManager(new WebClient("10.0.2.2", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "I AM NOT JSON!";
            }
        });
        map = new HashMap<>();
        map.put("login", "123");
        map.put("password", "123");
        map.put("name", "asd");
        map.put("email", "hot@eee.com");
        map.put("creditCardNumber", "1234");
        map.put("creditCardCVV", "123");
        map.put("creditCardExpiryMonth", "08");
        map.put("creditCardExpiryYear", "2029");
        map.put("creditCardPostCode", "55555");
        dm.createContributor(map);
        fail("DataManager.createContributor does not throw IllegalStateException when WebClient returns malformed JSON");

    }

    @Test
    public void testCreateContributor() {
        String mockJsonForFindByLogin = "{\n" +
                "  \"status\":" +
                " \"not found\",\n" +
                "  \"data\": \"this login id has not been taken yet\"\n" +
                "}";

        String mockJsonForCreateContributor = "{\n" +
                "  \"status\":" +
                " \"success\",\n" +
                "  \"data\": \"Successfully created a new contributor\"\n" +
                "}";

        String mockJsonForLogin = "{\n" +
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

        dm = new DataManager(new WebClient("10.0.2.2", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (resource.equals("/findContributorByLogin")) return mockJsonForFindByLogin;
                else if (resource.equals("/findContributorByLoginAndPassword")) return mockJsonForLogin;
                else if (resource.equals("/findFundNameById")) return getNameMockJson;
                return mockJsonForCreateContributor;

            }
        });
        map = new HashMap<>();
        map.put("login", "asd");
        map.put("password", "123");
        map.put("name", "asd");
        map.put("email", "hot@eee.com");
        map.put("creditCardNumber", "1234");
        map.put("creditCardCVV", "123");
        map.put("creditCardExpiryMonth", "08");
        map.put("creditCardExpiryYear", "2029");
        map.put("creditCardPostCode", "55555");
        assertTrue(dm.createContributor(map));
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateContributorWithBlankInput() {
        String mockJsonForFindByLogin = "{\n" +
                "  \"status\":" +
                " \"not found\",\n" +
                "  \"data\": \"this login id has not been taken yet\"\n" +
                "}";

        String mockJsonForCreateContributor = "{\n" +
                "  \"status\":" +
                " \"success\",\n" +
                "  \"data\": \"Successfully created a new contributor\"\n" +
                "}";

        String mockJsonForLogin = "{\n" +
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

        dm = new DataManager(new WebClient("10.0.2.2", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (resource.equals("/findContributorByLogin")) return mockJsonForFindByLogin;
                else if (resource.equals("/findContributorByLoginAndPassword")) return mockJsonForLogin;
                else if (resource.equals("/findFundNameById")) return getNameMockJson;
                return mockJsonForCreateContributor;

            }
        });
        map = new HashMap<>();
        map.put("login", "asd");
        map.put("password", "");
        map.put("name", "asd");
        map.put("email", "hot@eee.com");
        map.put("creditCardNumber", "1234");
        map.put("creditCardCVV", "123");
        map.put("creditCardExpiryMonth", "08");
        map.put("creditCardExpiryYear", "2029");
        map.put("creditCardPostCode", "55555");
        dm.createContributor(map);
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateContributorWithDuplicatedLoginID() {
        String mockJsonForFindByLogin = "{\n" +
                "  \"status\":" +
                " \"success\",\n" +
                "  \"data\": \"this login ID has been taken\"\n" +
                "}";

        String mockJsonForCreateContributor = "{\n" +
                "  \"status\":" +
                " \"success\",\n" +
                "  \"data\": \"Successfully created a new contributor\"\n" +
                "}";

        String mockJsonForLogin = "{\n" +
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

        dm = new DataManager(new WebClient("10.0.2.2", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (resource.equals("/findContributorByLogin")) return mockJsonForFindByLogin;
                else if (resource.equals("/findContributorByLoginAndPassword")) return mockJsonForLogin;
                else if (resource.equals("/findFundNameById")) return getNameMockJson;
                return mockJsonForCreateContributor;

            }
        });
        map = new HashMap<>();
        map.put("login", "123");
        map.put("password", "123");
        map.put("name", "asd");
        map.put("email", "hot@eee.com");
        map.put("creditCardNumber", "1234");
        map.put("creditCardCVV", "123");
        map.put("creditCardExpiryMonth", "08");
        map.put("creditCardExpiryYear", "2029");
        map.put("creditCardPostCode", "55555");
        dm.createContributor(map);
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateContributorWithDuplicatedLoginIDErrorStatus() {
        String mockJsonForFindByLogin = "{\n" +
                "  \"status\":" +
                " \"error\",\n" +
                "  \"data\": \"bad\"\n" +
                "}";

        String mockJsonForCreateContributor = "{\n" +
                "  \"status\":" +
                " \"success\",\n" +
                "  \"data\": \"Successfully created a new contributor\"\n" +
                "}";

        String mockJsonForLogin = "{\n" +
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

        dm = new DataManager(new WebClient("10.0.2.2", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (resource.equals("/findContributorByLogin")) return mockJsonForFindByLogin;
                else if (resource.equals("/findContributorByLoginAndPassword")) return mockJsonForLogin;
                else if (resource.equals("/findFundNameById")) return getNameMockJson;
                return mockJsonForCreateContributor;

            }
        });
        map = new HashMap<>();
        map.put("login", "123");
        map.put("password", "123");
        map.put("name", "asd");
        map.put("email", "hot@eee.com");
        map.put("creditCardNumber", "1234");
        map.put("creditCardCVV", "123");
        map.put("creditCardExpiryMonth", "08");
        map.put("creditCardExpiryYear", "2029");
        map.put("creditCardPostCode", "55555");
        dm.createContributor(map);
    }


    @Test(expected = IllegalStateException.class)
    public void testCreateContributorWithErrorStatus() {
        String mockJsonForFindByLogin = "{\n" +
                "  \"status\":" +
                " \"not found\",\n" +
                "  \"data\": \"good\"\n" +
                "}";

        String mockJsonForCreateContributor = "{\n" +
                "  \"status\":" +
                " \"error\",\n" +
                "  \"data\": \"Successfully created a new contributor\"\n" +
                "}";

        String mockJsonForLogin = "{\n" +
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

        dm = new DataManager(new WebClient("10.0.2.2", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (resource.equals("/findContributorByLogin")) return mockJsonForFindByLogin;
                else if (resource.equals("/findContributorByLoginAndPassword")) return mockJsonForLogin;
                else if (resource.equals("/findFundNameById")) return getNameMockJson;
                return mockJsonForCreateContributor;

            }
        });
        map = new HashMap<>();
        map.put("login", "123");
        map.put("password", "123");
        map.put("name", "asd");
        map.put("email", "hot@eee.com");
        map.put("creditCardNumber", "1234");
        map.put("creditCardCVV", "123");
        map.put("creditCardExpiryMonth", "08");
        map.put("creditCardExpiryYear", "2029");
        map.put("creditCardPostCode", "55555");
        dm.createContributor(map);
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateContributorWithConnectionError() {
        String mockJsonForFindByLogin = "{\n" +
                "  \"status\":" +
                " \"not found\",\n" +
                "  \"data\": \"this login id has not been taken yet\"\n" +
                "}";

        String mockJsonForCreateContributor = "java.net.ConnectException";

        String mockJsonForLogin = "{\n" +
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

        dm = new DataManager(new WebClient("10.0.2.2", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (resource.equals("/findContributorByLogin")) return mockJsonForFindByLogin;
                else if (resource.equals("/findContributorByLoginAndPassword")) return mockJsonForLogin;
                else if (resource.equals("/findFundNameById")) return getNameMockJson;
                return mockJsonForCreateContributor;

            }
        });
        map = new HashMap<>();
        map.put("login", "asd");
        map.put("password", "123");
        map.put("name", "asd");
        map.put("email", "hot@eee.com");
        map.put("creditCardNumber", "1234");
        map.put("creditCardCVV", "123");
        map.put("creditCardExpiryMonth", "08");
        map.put("creditCardExpiryYear", "2029");
        map.put("creditCardPostCode", "55555");
        assertTrue(dm.createContributor(map));
    }
}
