import java.util.List;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UserInterface {
	
	
	private DataManager dataManager;
	private Organization org;
	private Scanner in = new Scanner(System.in);
	
	//	public UserInterface(DataManager dataManager, Organization org) {
	//	this.dataManager = dataManager;
	//	this.org = org;
	//}
	
	public UserInterface(DataManager dataManager) {
		this.dataManager = dataManager;
		this.org = null;
	}
	
	public void start() {
				
		while (true) {
			System.out.println("\n\n");
			if (org.getFunds().size() > 0) {
				System.out.println("There are " + org.getFunds().size() + " funds in this organization:");
			
				int count = 1;
				for (Fund f : org.getFunds()) {
					
					System.out.println(count + ": " + f.getName());
					
					count++;
				}
				System.out.println("Enter the fund number to see more information.");
			}
			System.out.println("Enter 0 to create a new fund, Enter -1 to delete a fund");
			System.out.println("Enter -1 to delete a fund");
			System.out.println("Enter -2 to update organization's password");
			System.out.println("Enter -3 to update organization's infomation");
			int option = in.nextInt();
			in.nextLine();
			System.out.println();
			if (option == 0) {
				createFund(); 
			} else if (option == -1) {
				delFund();
			} else if (option == -2) {
				updateOrgPasswd();
			} else if (option == -3) {
				updateOrgInfo();
			}
			else {
				displayFund(option);
			}
		}			
			
	}
	
	public void createFund() {
		  
		  System.out.print("Enter the fund name: ");
		  String name = in.nextLine().trim();
		  while (name.length() <= 2 || name.length() >=20){
		   System.out.println("Name too short or too long, please re-enter:");
		   name = in.nextLine().trim();
		  }
		  
		  System.out.print("Enter the fund description: ");
		  String description = in.nextLine().trim();
		  while (description.length() == 0){
		   System.out.println("Description can not be empty, please re-enter:");
		   description = in.nextLine().trim();
		  }
		  
		  System.out.print("Enter the fund target: ");
		  while(true) {
			  try {
				  String target_ = in.nextLine().trim();
				  long target = Long.valueOf(target_).longValue();
				  if (target>0) {
//				  in.nextLine();
				  Fund fund = dataManager.createFund(org.getId(), name, description, target);
				  org.getFunds().add(fund);
				  break;
				  }else {
					  System.out.println("Must be a positive number, please re-enter:");
				  }
				  
				  } catch (NumberFormatException e){
					  System.out.println("Must be a positive number, please re-enter:");
				  }
		  }
		  
//		  while (target <= 0){
//		   System.out.println("Must be a positive number, please re-enter:");
//		   target_ = in.nextLine().trim();
//		   target = in.nextInt();
//		  }
		  
		 }

    
	
	
//	 public void displayFund(int fundNumber) {
//  
//		Fund fund = org.getFunds().get(fundNumber - 1);
//		
//		
//		System.out.println("\n\n");
//		System.out.println("Here is information about this fund:");
//		System.out.println("Name: " + fund.getName());
//		System.out.println("Description: " + fund.getDescription());
//		System.out.println("Target: $" + fund.getTarget());
//		
//		List<Donation> donations = fund.getDonations();
//		System.out.println("Number of donations: " + donations.size());
//		for (Donation donation : donations) {
//		System.out.println("* " + donation.getContributorName() + ": $" + donation.getAmount() + " on " + donation.getDate());
//		}
//		
//		double sum = 0;
//			for (Donation donation: donations) {
//				sum = sum + donation.getAmount();
//			}
//		System.out.println("Total amount of donations: " + sum);
//		System.out.println("Persentage Achieved: " + (sum/fund.getTarget())*100 + "%");
//		
//		
//		System.out.println("Press the Enter key to go back to the listing of funds");
//		in.nextLine(); 
//	}
	
	 public void displayFund(int fundNumber) {
		  
		Fund fund = org.getFunds().get(fundNumber - 1);
		
		
		System.out.println("\n\n");
		System.out.println("Here is information about this fund:");
		System.out.println("Name: " + fund.getName());
		System.out.println("Description: " + fund.getDescription());
		System.out.println("Target: $" + fund.getTarget());
		
		List<Donation> donations = fund.getDonations();
		
		/*
		System.out.println("Number of donations: " + donations.size());
		for (Donation donation : donations) {
		System.out.println("* " + donation.getContributorName() + ": $" + donation.getAmount() + " on " + donation.getDate());
		}
		
		long sum = 0;
			for (Donation donation: donations) {
				sum = sum + donation.getAmount();
			}
		System.out.println("Total amount of donations: " + sum);
		System.out.println("Persentage Achieved: " + (sum/fund.getTarget())*100 + "%");
		*/
		File file = new File("cache.json");
		boolean flag = file.exists();
		boolean need_update = false;
		JSONObject new_json = null;
		
		System.out.println("\n");
		if (flag) {
			//System.out.println("Loading cache...");
			try {
				FileInputStream readIn = new FileInputStream(file);
				InputStreamReader read = new InputStreamReader(readIn, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(read);

				String oneLine= null;
				StringBuilder MyStringBuilder = new StringBuilder();

				while((oneLine= bufferedReader.readLine()) != null){
					MyStringBuilder.append(oneLine);
				}

				read.close();
				//System.out.println(MyStringBuilder.toString());
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(MyStringBuilder.toString());
				JSONObject data = null;
				String status;
				try {
					data = (JSONObject)json.get(fund.getName());
					status = (String)data.get("statue");
					if (status.equals(fund.getName())) {
						System.out.println("Cache loaded.");
						Long num = (Long)data.get("size");
						if (num != donations.size()) {
							need_update = true;
						}
						
					} else {
						need_update = true;
					}
				} catch (Exception e) {
					need_update = true;
					System.out.println("Test fund name");
				}
				
				new_json = json;

			} catch (Exception e) {
				System.out.println("Fail to read json file!");
				e.printStackTrace();
			}
			
		} else {
			//System.out.println("Creating cache...");
		}
		if (!flag || need_update) {
			if (new_json == null) {
				new_json = new JSONObject();
			}
			JSONObject don_data = new JSONObject();
			HashMap<String, Long> total = new HashMap<String, Long>();
			HashMap<String, Integer> count = new HashMap<String, Integer>();
			for (Donation donation : donations) {
				if (total.containsKey(donation.getContributorName())) {
					total.put(donation.getContributorName(), total.get(donation.getContributorName()) + donation.getAmount());
					count.put(donation.getContributorName(), count.get(donation.getContributorName()) + 1);
				} else {
					total.put(donation.getContributorName(), donation.getAmount());
					count.put(donation.getContributorName(), 1);
				}
			}
			don_data.put("detail", total);
			don_data.put("count", count);
			don_data.put("size", donations.size());
			don_data.put("statue", fund.getName());
			new_json.put(fund.getName(), don_data);
			StringWriter out = new StringWriter();
			try {
				new_json.writeJSONString(out);
				String jsonText = out.toString();
				BufferedWriter fout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("cache.json",false)));
				//System.out.println(jsonText);
				fout.write(jsonText);
				fout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (new_json != null) {
			JSONObject data = (JSONObject)new_json.get(fund.getName());
			HashMap<String, Long> total = (HashMap<String, Long>)data.get("detail");
			HashMap<String, Integer> count = (HashMap<String, Integer>)data.get("count");;
			Iterator < String > iterator = total.keySet().iterator();
			
	        while (iterator.hasNext()) {  
	        	String key = iterator.next();  
	        	System.out.println("* " + key + ", " + count.get(key) + " donations, $" + total.get(key) + " total");
	        }
	        System.out.println("\n");
		} else {
			System.out.println("Fail to load data!");
		}
		
		long sum = 0;
		for (Donation donation: donations) {
			sum = sum + donation.getAmount();
		}
		System.out.println("Total amount of donations: " + sum);
		System.out.println("Persentage Achieved: " + (sum/fund.getTarget())*100 + "%");
	
		System.out.println("Press the Enter key to go back to the listing of funds");
		in.nextLine(); 
	}

	public void delFund() {
		System.out.println("\nAll funds:");
		HashMap<String, String> data = new HashMap<String, String>();
		for (Fund f : org.getFunds()) {
			//System.out.println("Fund name:"+f.getName()+", fund id:"+f.getId());
			System.out.println("Fund name:"+f.getName());
			data.put(f.getName(), f.getId());		
		}
		while (true) {
			System.out.println("Enter fund name to delete a fund(Enter \"exit\" to exit):");
			String fund_name = in.next();
			in.nextLine();
			if (fund_name.equals("exit")) {
				break;
			}
			
			String res = this.dataManager.deleteFund(data.get(fund_name));
			if (res == null) {
				System.out.println("Fail to delete fund:" + fund_name);
			} else if (res.equals("success")) {
			 	System.out.println("Success!");
			 	List<Fund> newFund = org.getFunds();
			 	Iterator<Fund> it = newFund.iterator();
		        while (it.hasNext()) {
					Fund Fundit = it.next();
					if (fund_name.equals(Fundit.getName())) {
						it.remove();
					}
					
				}
			 	
			}
		}
	
		System.out.println("Press the Enter key to go back to the listing of funds");
		in.nextLine(); 
	
	}
	
	public String chooseLoginOrCreate() {
//		choose to login or create new organization

		while(true) {
			System.out.println("Please enter 1 to login in, enter 2 to create new organization account.");

			int option = in.nextInt();
			in.nextLine();
			
			if (option == 1) return "login";
			else if (option == 2) return "create new account";
		}
	}
	
	public Organization createOrg() {
		while(true) {
		  
		  System.out.print("Enter the login name: ");
		  String login_name = in.nextLine().trim();
		  while (login_name.length() ==0 || login_name.length() >=20){
		   System.out.println("Login name is empty or too long, please re-enter:");
		   login_name = in.nextLine().trim();
		  }
		  
		  System.out.print("Enter the password: ");
		  String password = in.nextLine().trim();
		  while (password.length() == 0){
		   System.out.println("Password can not be empty, please re-enter:");
		   password = in.nextLine().trim();
		  }
		  
		  System.out.print("Enter the organization name: ");
		  String org_name = in.nextLine().trim();
		  while (org_name.length() == 0 || org_name.length() >=20){
		   System.out.println("Organization name is empty or too long, please re-enter:");
		   org_name = in.nextLine().trim();
		  }
		  
		  System.out.print("Enter the organization description: ");
		  String org_description = in.nextLine().trim();
		  while (org_description.length() == 0){
		   System.out.println("Description can not be empty, please re-enter:");
		   org_description = in.nextLine().trim();
		  }
		  try {
			  System.out.println();
			  Organization org = dataManager.createOrg(login_name, password, org_name, org_description);
			  System.out.println();
			  return org;
		  } catch(Exception e) {}
		}
			  
	}
	
	public void updateOrgPasswd() {
		System.out.println("Update organization's password");
		if (true) {
			System.out.println("Please enter organization's old password: ");
			String input = in.next();
			in.nextLine();
			String res = this.dataManager.vertifyOrgPassword(this.org.getId(), input);
			if (res == null) {
				System.out.println("Sorry! The old password you entered is inconsistent with the actual old password");
			} else {
				System.out.println("Please input a new password: ");
				String newpw = in.next();
				in.nextLine();
				System.out.println("Please input a new password again to vertify: ");
				String newpw1 = in.next();
				in.nextLine();
				
				if (!newpw.equals(newpw1)) {
					System.out.println("Sorry! The new password you entered for the second time is not equal to the new password you entered for the first time");
					System.out.println("Please try again!");
				}
				res = this.dataManager.updateOrgPassword(this.org.getId(), input, newpw);
				if (res == null) {
					System.out.println("Fail to update organization's old password");
				} else if (res.equals("success")) {
					System.out.println("Success! Password changed successfully");
				} else if (res.equals("notequal")) {
					System.out.println("Sorry! The old password you entered is inconsistent with the actual old password");
				}
			}
			
		}
		

	}
	
	public void updateOrgInfo() {
		System.out.println("Update organization's infomation");
		if (true) {
			System.out.println("Please enter organization's password: ");
			String input = in.next();
			in.nextLine();
			String res = this.dataManager.vertifyOrgPassword(this.org.getId(), input);
			if (res == null) {
				System.out.println("Sorry! The password you entered is wrong");
			} else {
				System.out.println("Edit organization's name: ");
				String name = in.next();
				in.nextLine();
				System.out.println("Edit organization's description: ");
				String desc = in.next();
				in.nextLine();
				
				res = this.dataManager.updateOrgInfo(this.org.getId(), input, name, desc);
				if (res == null) {
					System.out.println("Fail to update organization's old infomation");
				} else if (res.equals("success")) {
					System.out.println("Success! Organization's infomation changed successfully");
				} else if (res.equals("notequal")) {
					System.out.println("Sorry! The password you entered is wrong");
				}
			}
			
		}
		

	}
	
/*	
	public static void main(String[] args) {
//		? do we need to create while true until get success.
//		while(true) {
		try {
//			DataManager ds = new DataManager(null);
			DataManager ds = new DataManager(new WebClient("localhost", 3001));
			
			// String login ="org1";
			// String password ="123";
	//		String password =null;
			String login = args[0];
			String password = args[1];

			Organization org = ds.attemptLogin(login, password);
			
			if (org == null) {
				System.out.println("Login failed.");
			}
			else {
				UserInterface ui = new UserInterface(ds, org);	
				ui.start();	
//				break;
			}
		}catch(Exception e){
			System.out.println(e);
		}
		}
//	}
 */

	
	public static void main(String[] args) {
//		while(true) {
		try {
			DataManager ds = new DataManager(new WebClient("localhost", 3001));
			UserInterface ui = new UserInterface(ds);
			String option = ui.chooseLoginOrCreate();
			if(option.equals("create new account")) {
				Organization org = ui.createOrg();
				ui.org = org;
				ui.createFund(); 
			} else if (option.equals("login")) {
//				need to check whether login and password still store in args
//				String login ="t1"; //"org1";
//				String password ="123";
				String login = args[0];
				String password = args[1];
				
				Organization org = ds.attemptLogin(login, password);
				
				if (org == null) System.out.println("Login failed.");
				else {
					System.out.println("Login succeeded.");
					ui.org = org;
				}	
//					break;
			}
			ui.start();
			
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
