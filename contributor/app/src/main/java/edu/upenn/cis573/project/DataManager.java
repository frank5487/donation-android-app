package edu.upenn.cis573.project;

import android.util.JsonReader;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONStringer;
import org.json.JSONTokener;

public class DataManager {

    private WebClient client;
    private Map<String, String> cache;

    public DataManager(WebClient client) {
        if (client == null) {
            throw new IllegalStateException("client is null");
        }
        this.client = client;
        cache = new HashMap<>();
    }


    /**
     * Attempt to log in to the Contributor account using the specified login and password.
     * This method uses the /findContributorByLoginAndPassword endpoint in the API
     * @return the Contributor object if successfully logged in, null otherwise
     */
    public Contributor attemptLogin(String login, String password) {
        if (login == null || password == null) {
            throw new IllegalArgumentException("input must not be null");
        }
        if (client == null) {
            throw new IllegalStateException("client is null");
        }
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("login", login);
            map.put("password", password);
            String response = client.makeRequest("/findContributorByLoginAndPassword", map);
            if (response == null) {
                throw new IllegalStateException("connect to the wrong port...");
            }

            if (!isJson(response)) {
                throw new IllegalStateException("this is not a json format data...");
            }
            JSONObject json = new JSONObject(response);
            String status = (String)json.get("status");
            if (status.equals("error")) {
                throw new IllegalStateException("something wrong from the response data");
            }

            if (status.equals("success")) {
                JSONObject data = (JSONObject)json.get("data");
                String id = (String)data.get("_id");
                String name = (String)data.get("name");
                String email = (String)data.get("email");
                String creditCardNumber = (String)data.get("creditCardNumber");
                String creditCardCVV = (String)data.get("creditCardCVV");
                String creditCardExpiryMonth = ((Integer)data.get("creditCardExpiryMonth")).toString();
                String creditCardExpiryYear = ((Integer)data.get("creditCardExpiryYear")).toString();
                String creditCardPostCode = (String)data.get("creditCardPostCode");

                Contributor contributor = new Contributor(id, name, email, creditCardNumber, creditCardCVV, creditCardExpiryYear, creditCardExpiryMonth, creditCardPostCode);

                List<Donation> donationList = new LinkedList<>();

                JSONArray donations = (JSONArray)data.get("donations");

                for (int i = 0; i < donations.length(); i++) {

                    JSONObject jsonDonation = donations.getJSONObject(i);

                    String fundId = (String) jsonDonation.get("fund");
                    if (!cache.containsKey(fundId)) {
                        String fundName = getFundName(fundId);
//                        System.out.println("in login(), put id, name into cache" + fundId + " " +  fundName);
                        cache.put(fundId, fundName);
                    }
                    String fund = cache.get(fundId);
//                    String fund = getFundName((String)jsonDonation.get("fund"));
                    String date = (String)jsonDonation.get("date");
                    long amount = (Integer)jsonDonation.get("amount");

                    Donation donation = new Donation(fund, name, amount, date);
                    donationList.add(donation);

                }

                contributor.setDonations(donationList);

                return contributor;

            }

            return null;

        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the name of the fund with the specified ID using the /findFundNameById endpoint
     * @return the name of the fund if found, "Unknown fund" if not found, null if an error occurs
     */
    public String getFundName(String id) {
        if (id == null) {
            throw new IllegalArgumentException("invalid input...");
        }
        if (client == null) {
            throw new IllegalStateException("client is null");
        }
        if (cache.containsKey(id)) {
//            System.out.println("in getFundName(), get value from cache");
            return cache.get(id);
        }
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            String response = client.makeRequest("/findFundNameById", map);
            if (response == null) {
                throw new IllegalStateException("connect to the wrong port...");
            }

            if (!isJson(response)) {
                throw new IllegalStateException("this is not a json format data...");
            }
            JSONObject json = new JSONObject(response);
            String status = (String)json.get("status");
            if (status.equals("error")) {
                throw new IllegalStateException("something wrong from the response data");
            }

            if (status.equals("success")) {
                String name = (String)json.get("data");
//                System.out.println("in getFundName(), put id, name into cache");
                cache.put(id, name);
                return name;
            }
            else return "Unknown Fund";

        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get information about all of the organizations and their funds.
     * This method uses the /allOrgs endpoint in the API
     * @return a List of Organization objects if successful, null otherwise
     */
    public List<Organization> getAllOrganizations() {
        if (client == null) {
            throw new IllegalStateException("client is null");
        }
        try {
            Map<String, Object> map = new HashMap<>();
            String response = client.makeRequest("/allOrgs", map);
            if (response == null) {
                throw new IllegalStateException("connect to the wrong port...");
            }

            if (!isJson(response)) {
                throw new IllegalStateException("this is not a json format data...");
            }

            JSONObject json = new JSONObject(response);
            String status = (String)json.get("status");
            if (status.equals("error")) {
                throw new IllegalStateException("something wrong from the response data");
            }

            if (status.equals("success")) {

                List<Organization> organizations = new LinkedList<>();

                JSONArray data = (JSONArray)json.get("data");

                for (int i = 0; i < data.length(); i++) {

                    JSONObject obj = data.getJSONObject(i);

                    String id = (String)obj.get("_id");
                    String name = (String)obj.get("name");

                    Organization org = new Organization(id, name);

                    List<Fund> fundList = new LinkedList<>();

                    JSONArray array = (JSONArray)obj.get("funds");

                    for (int j = 0; j < array.length(); j++) {

                        JSONObject fundObj = array.getJSONObject(j);

                        id = (String)fundObj.get("_id");
                        name = (String)fundObj.get("name");
                        long target = (Integer)fundObj.get("target");
                        long totalDonations = (Integer)fundObj.get("totalDonations");

                        Fund fund = new Fund(id, name, target, totalDonations);

                        fundList.add(fund);

                    }

                    org.setFunds(fundList);

                    organizations.add(org);

                }

                return organizations;

            }

            return null;

        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Make a donation to the specified fund for the specified amount.
     * This method uses the /makeDonation endpoint in the API
     * @return true if successful, false otherwise
     */
    // TODO: has a bug here, we should check if contributor and fund already existed first, (it turns out that api has checked for me already)
    //  also the amount of money must be greater than and be an Integer type which is regulated by the schema
    public boolean makeDonation(String contributorId, String fundId, String amount) {
        if (contributorId == null || fundId == null || amount == null) {
            throw new IllegalArgumentException("invalid input...");
        }
        if (client == null) {
            throw new IllegalStateException("client is null");
        }

        try {

            Map<String, Object> map = new HashMap<>();
            map.put("contributor", contributorId);
            map.put("fund", fundId);
            map.put("amount", amount);
            if (Integer.parseInt(amount) <= 0) {
                return false;
            }
            String response = client.makeRequest("/makeDonation", map);
            if (response == null) {
                throw new IllegalStateException("connect to the wrong port...");
            }

            if (!isJson(response)) {
                throw new IllegalStateException("this is not a json format data...");
            }

            JSONObject json = new JSONObject(response);
            String status = (String)json.get("status");

            if (status.equals("error")) {
                throw new IllegalStateException("something wrong from the response data");
            }

            return status.equals("success");

        }
        catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

    private boolean isJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return true;
        } catch (JSONException e) {
            // e.printStackTrace();
            return false;
        }
    }
}
