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
        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                String funds1 = "[{\"_id\":\"234\",\"name\":\"smallHot\",\"target\":" + 200 + ",\"totalDonations\":" + 20 + "}]";
                String funds2 = "[{\"_id\":\"235\",\"name\":\"BigCold\",\"target\":" + 300 + ",\"totalDonations\":" + 30 +"}]";
                String orgsInfo = "[{\"_id\":\"55688\",\"name\":\"PHX\",\"funds\":" + funds1 + "}, {\"_id\":\"7414\",\"name\":\"PNN\",\"funds\":" + funds2 +"}]";
                String data = "\"data\":" + orgsInfo;
                String status = "\"status\":\"success\"";
                String response = "{" + status + "," + data + "}";
                return response;
            }
        });

        List<Organization> allOrganizations = dm.getAllOrganizations();
        assertNotNull(allOrganizations);
        assertEquals(2, allOrganizations.size());
        assertEquals("PHX", allOrganizations.get(0).getName());
        assertEquals("PNN", allOrganizations.get(1).getName());
    }

    @Test
    public void testForAllOrganizationsTwo() {
        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                String funds1 = "[{\"_id\":\"234\",\"name\":\"smallHot\",\"target\":" + 200 + ",\"totalDonations\":" + 20 + "}]";
                String funds2 = "[{\"_id\":\"235\",\"name\":\"BigCold\",\"target\":" + 300 + ",\"totalDonations\":" + 30 +"}]";
                String orgsInfo = "[{\"_id\":\"55688\",\"name\":\"PHX\",\"funds\":" + funds1 + "}, {\"_id\":\"7414\",\"name\":\"PNN\",\"funds\":" + funds2 +"}]";
                String data = "\"data\":" + orgsInfo;
                String status = "\"status\":\"fail\"";
                String response = "{" + status + "," + data + "}";
                return response;
            }
        });

        List<Organization> allOrganizations = dm.getAllOrganizations();
        assertNull(allOrganizations);
    }

    @Test
    public void testForAllOrganizationsThree() {
        DataManager dm = new DataManager(new WebClient(null, 0) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                String funds1 = "[{\"_id\":\"234\",\"name\":\"smallHot\",\"target\":" + 200 + ",\"totalDonations\":" + 20 + "}]";
                String funds2 = "[{\"_id\":\"235\",\"name\":\"BigCold\",\"target\":" + 300 + ",\"totalDonations\":" + 30 +"}]";
                String orgsInfo = "[{\"_id\":\"55688\",\"name\":\"PHX\",\"funds\":" + funds1 + "}, {\"_id\":\"7414\",\"name\":\"PNN\",\"funds\":" + funds2 +"}]";
                String data = "\"data\":" + orgsInfo;
                String status = "\"status\":\"success\"";
                String response = "{" + status + "," + data + "";
                return response;
            }
        });

        List<Organization> allOrganizations = dm.getAllOrganizations();
        assertNull(allOrganizations);
    }
}
