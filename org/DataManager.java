
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataManager {

	private final WebClient client;
	private Map<String, String> FindNameById = new HashMap<>(); 

	public DataManager(WebClient client) {
//		add defensive programming
		if(client==null||client.port!=3001) {
			System.out.println("Client is null or port not equals 3001.");
			throw new IllegalStateException(); //||client.port!=3001, change port to visible or modify in webclient
		}
		this.client = client;
	}

	/**
	 * Attempt to log the user into an Organization account using the login and password.
	 * This method uses the /findOrgByLoginAndPassword endpoint in the API
	 * @return an Organization object if successful; null if unsuccessful
	 */
	public Organization attemptLogin(String login, String password) {

		try {
//			add defensive programming
			if (login==null||password==null) {
				System.out.println("Id or password is null.");
				throw new IllegalArgumentException();
			}
			Map<String, Object> map = new HashMap<>();
			map.put("login", login);
			map.put("password", password);
			String response = client.makeRequest("/findOrgByLoginAndPassword", map);
//			System.out.println(response);
//			add defensive programming
//			response="I like";
			if(response==null) {
				System.out.println("Fail to get response from API.");
				throw new IllegalStateException();
			}

			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(response);
			String status = (String)json.get("status");


			if (status.equals("success")) {
				JSONObject data = (JSONObject)json.get("data");
				String fundId = (String)data.get("_id");
				String name = (String)data.get("name");
				String description = (String)data.get("description"); //add 'i'
//				add assert
				assert fundId!=null && name!=null;
				Organization org = new Organization(fundId, name, description);

				JSONArray funds = (JSONArray)data.get("funds");
				Iterator it = funds.iterator();
				while(it.hasNext()){
					JSONObject fund = (JSONObject) it.next(); 
					fundId = (String)fund.get("_id");
					name = (String)fund.get("name");
					description = (String)fund.get("description");
					long target = (Long)fund.get("target");
//					add assert
					assert fundId!=null && name!=null;
					assert target > 0;

					Fund newFund = new Fund(fundId, name, description, target);

					JSONArray donations = (JSONArray)fund.get("donations");
					List<Donation> donationList = new LinkedList<>();
					Iterator it2 = donations.iterator();
					while(it2.hasNext()){
						JSONObject donation = (JSONObject) it2.next();
						String contributorId = (String)donation.get("contributor");
//						System.out.println(contributorId);
						String contributorName = this.getContributorName(contributorId);
//						System.out.println(contributorName);
						long amount = (Long)donation.get("amount");
//						add assert
						assert fundId!=null && contributorName!=null;
						assert amount > 0 && amount<=target;
						String date = (String)donation.get("date");
						donationList.add(new Donation(fundId, contributorName, amount, date));
					}

					newFund.setDonations(donationList);

					org.addFund(newFund);

				}

				return org;
			}
//			add else if: login failed
			else if (status.equals("login failed")){
				System.out.println("Login failed, please try again.");
				return null;
			}
//			add else if: error
			else if (status.equals("error")){
				System.out.println("Enconter an error in login, please try again.");
				throw new IllegalStateException();
			}
			else return null;
			
		}
		catch (IllegalStateException e) {
			throw new IllegalStateException();
		}catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}catch (Exception e) { //do we need to restrict the type of exception for parse?
//			System.out.println(e);
			throw new IllegalStateException();
		}
	}

	/**
	 * Look up the name of the contributor with the specified ID.
	 * This method uses the /findContributorNameById endpoint in the API.
	 * @return the name of the contributor on success; null if no contributor is found
	 */
	public String getContributorName(String id) {

		try {
//			add defensive programming
			if(id==null) throw new IllegalArgumentException();
//			add 2.1 return cache, shall we put it before makeRequest or if get null value after makeRequest?
			if (FindNameById.containsKey(id)) {
				System.out.println("get from cache");
				return FindNameById.get(id);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("id", id);
			String response = client.makeRequest("/findContributorNameById", map);
//			System.out.println(response);
//			add defensive programming
			if(response==null) {
				System.out.println("Fail to get response from API.");
				throw new IllegalStateException();
			}

			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(response);
			String status = (String)json.get("status");

			if (status.equals("success")) {
				String name = (String)json.get("data");
//				System.out.println(name);
//				add 2.1 put into cache
				FindNameById.put(id, name);
				return name;
			}
//			add else if: not found
			else if (status.equals("not found")){
				System.out.println("Cannot find the name by id, please check id and try again.");
				return null;
			}
//			add else if: error
			else if (status.equals("error")){
				System.out.println("Enconter an error in finding contributor's name, please try again.");
				throw new IllegalStateException();
			}
			else return null;

		}
		catch (IllegalStateException e) {
			throw new IllegalStateException();
		}catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}catch (Exception e) { //do we need to restrict the type of exception for parse?
			throw new IllegalStateException();
//			return null;
		}	
	}

	/**
	 * This method creates a new fund in the database using the /createFund endpoint in the API
	 * @return a new Fund object if successful; null if unsuccessful
	 */
	public Fund createFund(String orgId, String name, String description, long target) {

		try {
//			add assumption that target > 0
			if(target <=0) {
				System.out.println("target should be greater than 0.");
				return null;
			}
//			add defensive programming
			if(orgId==null||name==null||description==null) {
				System.out.println("organzation Id, name or description is null.");
				throw new IllegalArgumentException();
			}
			Map<String, Object> map = new HashMap<>();
			map.put("orgId", orgId);
			map.put("name", name);
			map.put("description", description);
			map.put("target", target);
			String response = client.makeRequest("/createFund", map);
//			System.out.println(response);
//			add defensive programming
			if(response==null) {
				System.out.println("Fail to get response from API.");
				throw new IllegalStateException();
			}

			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(response);
			String status = (String)json.get("status");
//			System.out.println(status);

			if (status.equals("success")) {
				JSONObject fund = (JSONObject)json.get("data");
				String fundId = (String)fund.get("_id");
				return new Fund(fundId, name, description, target);
			}
//			add else if: error
			else if (status.equals("error")){
				System.out.println("Enconter an error in creating new fund, please try again.");
				throw new IllegalStateException();
			}
			else return null;

		}
		catch (IllegalStateException e) {
			throw new IllegalStateException();
		}catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}catch (Exception e) { //do we need to restrict the type of exception for parse?
			throw new IllegalStateException();
//			return null;
		}	
	}


}
