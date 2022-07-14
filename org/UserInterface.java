import java.util.List;
import java.util.Scanner;

public class UserInterface {
	
	
	private DataManager dataManager;
	private Organization org;
	private Scanner in = new Scanner(System.in);
	
	public UserInterface(DataManager dataManager, Organization org) {
		this.dataManager = dataManager;
		this.org = org;
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
			System.out.println("Enter 0 to create a new fund");
			int option = in.nextInt();
			in.nextLine();
			if (option == 0) {
				createFund(); 
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
		  long target = in.nextInt();
		  while (target <= 0){
		   System.out.println("Must be a positive number, please re-enter:");
		   target = in.nextInt();
		  }
		  in.nextLine();

		  Fund fund = dataManager.createFund(org.getId(), name, description, target);
		  org.getFunds().add(fund);

		  
		 }

    
	
	
	 public void displayFund(int fundNumber) {
  
		Fund fund = org.getFunds().get(fundNumber - 1);
		
		
		System.out.println("\n\n");
		System.out.println("Here is information about this fund:");
		System.out.println("Name: " + fund.getName());
		System.out.println("Description: " + fund.getDescription());
		System.out.println("Target: $" + fund.getTarget());
		
		List<Donation> donations = fund.getDonations();
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
		
		
		System.out.println("Press the Enter key to go back to the listing of funds");
		in.nextLine(); 
	}
	
	
	public static void main(String[] args) {
		
		DataManager ds = new DataManager(new WebClient("localhost", 3001));
		
//		String login ="org1";
//		String password ="123";
		String login = args[0];
		String password = args[1];
		
		
		Organization org = ds.attemptLogin(login, password);
		
		if (org == null) {
			System.out.println("Login failed.");
		}
		else {

			UserInterface ui = new UserInterface(ds, org);
		
			ui.start();
		
		}
	}

}
