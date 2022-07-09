package edu.upenn.cis573.project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
    public void testGetFundNameTwo() {

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
    public void testGetFundNameThree() {

        DataManager dm = new DataManager(new WebClient(null, 0) {

            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return "{\"status\":\"fail\",\"data\":\"Snoopy\"";
            }
        });

        String name = dm.getFundName("12345");
        assertNull(name);
    }

    @Test
    public void testAttemptLogin() throws JSONException {

//        String donations = "[{\"fund\":\"55688\",\"date\":\"07/07/2022\",\"amount\":" + 29 + "}]";
//        System.out.println(donations);
//        String contributorInfo = "\"_id\":\"1\",\"name\":\"lkk\",\"email\":\"hp@com\"," +
//                        "\"creditCardNumber\":\"1111\",\"creditCardCVV\":\"222\"," +
//                        "\"creditCardExpiryMonth\":" + 05 + ",\"creditCardExpiryYear\":"+ 25 + "," +
//                        "\"creditCardPostCode\":\"15555\",\"donations\":" + donations;
//        System.out.println(contributorInfo);
//        String data = "\"data\":" + "{" + contributorInfo + "}";
//        System.out.println(data);
//        String status = "\"status\":\"success\"";
//        System.out.println(status);
//        String response = "{" + status + "," + data + "}";
//        System.out.println(response);

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

    @Test
    public void testDateFormat() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        String s1 = sdf.format(date);
//        System.out.println(s1);
        String p1 = "2022-07-09T04:21:04.807Z";
        p1 = p1.substring(0, 10);
        Date parse = sdf2.parse(p1);
        System.out.println(parse);
        String format = sdf.format(parse);
        System.out.println(format);
    }
}
