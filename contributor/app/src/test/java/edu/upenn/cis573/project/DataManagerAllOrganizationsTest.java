package edu.upenn.cis573.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import android.util.Log;

public class DataManagerAllOrganizationsTest {

    @Test
    public void testForAllOrganizations() {
        String mockJson = "{\n" +
                "  \"status\":" +
                " \"success\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"_id\": \"55688\",\n" +
                "      \"name\": \"PHX\",\n" +
                "      \"funds\": [\n" +
                "        {\n" +
                "          \"_id\": \"234\",\n" +
                "          \"name\": \"smallHot\",\n" +
                "          \"target\": 200,\n" +
                "          \"totalDonations\": 20\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"_id\": \"7414\",\n" +
                "      \"name\": \"PNN\",\n" +
                "      \"funds\": [\n" +
                "        {\n" +
                "          \"_id\": \"235\",\n" +
                "          \"name\": \"BigCold\",\n" +
                "          \"target\": 300,\n" +
                "          \"totalDonations\": 30\n" +
                "        }\n" +
                "      ] \n" +
                "    }\n" +
                "  ]\n" +
                "}";

        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
//                String funds1 = "[{\"_id\":\"234\",\"name\":\"smallHot\",\"target\":" + 200 + ",\"totalDonations\":" + 20 + "}]";
//                String funds2 = "[{\"_id\":\"235\",\"name\":\"BigCold\",\"target\":" + 300 + ",\"totalDonations\":" + 30 +"}]";
//                String orgsInfo = "[{\"_id\":\"55688\",\"name\":\"PHX\",\"funds\":" + funds1 + "}, {\"_id\":\"7414\",\"name\":\"PNN\",\"funds\":" + funds2 +"}]";
//                String data = "\"data\":" + orgsInfo;
//                String status = "\"status\":\"success\"";
//                String response = "{" + status + "," + data + "}";
//                return response;
                return mockJson;
            }
        });

        List<Organization> allOrganizations = dm.getAllOrganizations();
        assertNotNull(allOrganizations);
        assertEquals(2, allOrganizations.size());
        assertEquals("PHX", allOrganizations.get(0).getName());
        assertEquals("PNN", allOrganizations.get(1).getName());
    }

    @Test
    public void testForAllOrganizationsWithFailStatus() {
        String mockJson = "{\n" +
                "  \"status\":" +
                " \"fail\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"_id\": \"55688\",\n" +
                "      \"name\": \"PHX\",\n" +
                "      \"funds\": [\n" +
                "        {\n" +
                "          \"_id\": \"234\",\n" +
                "          \"name\": \"smallHot\",\n" +
                "          \"target\": 200,\n" +
                "          \"totalDonations\": 20\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"_id\": \"7414\",\n" +
                "      \"name\": \"PNN\",\n" +
                "      \"funds\": [\n" +
                "        {\n" +
                "          \"_id\": \"235\",\n" +
                "          \"name\": \"BigCold\",\n" +
                "          \"target\": 300,\n" +
                "          \"totalDonations\": 30\n" +
                "        }\n" +
                "      ] \n" +
                "    }\n" +
                "  ]\n" +
                "}";

        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return mockJson;
            }
        });

        List<Organization> allOrganizations = dm.getAllOrganizations();
        assertNull(allOrganizations);
    }

    @Test
    public void testForAllOrganizationsWithTypoForFunds() {

        String mockJson = "{\n" +
                "  \"status\":" +
                " \"fail\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"_id\": \"55688\",\n" +
                "      \"name\": \"PHX\",\n" +
                "      \"fund\": [\n" +
                "        {\n" +
                "          \"_id\": \"234\",\n" +
                "          \"name\": \"smallHot\",\n" +
                "          \"target\": 200,\n" +
                "          \"totalDonations\": 20\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"_id\": \"7414\",\n" +
                "      \"name\": \"PNN\",\n" +
                "      \"fund\": [\n" +
                "        {\n" +
                "          \"_id\": \"235\",\n" +
                "          \"name\": \"BigCold\",\n" +
                "          \"target\": 300,\n" +
                "          \"totalDonations\": 30\n" +
                "        }\n" +
                "      ] \n" +
                "    }\n" +
                "  ]\n" +
                "}";
        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return mockJson;
            }
        });

        List<Organization> allOrganizations = dm.getAllOrganizations();
        assertNull(allOrganizations);
    }
}
