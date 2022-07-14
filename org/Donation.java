import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Donation {
	
	private String fundId;
	private String contributorName;
	private long amount;
	private String date;
	
	public Donation(String fundId, String contributorName, long amount, String date) {
		this.fundId = fundId;
		this.contributorName = contributorName;
		this.amount = amount;
		this.date = date;
	}

	public String getFundId() {
		return fundId;
	}

	public String getContributorName() {
		return contributorName;
	}

	public long getAmount() {
		return amount;
	}

	public String getDate() {
		  try {
		   Date newDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date);
		   String formattedDate = new SimpleDateFormat("MMMMM dd, yyyy").format(newDate);
		   return formattedDate;
		  }   catch(Exception e){
		   System.out.println("bad Date format/unable to parse date");
		   return null;
		   }
		   
		 }
	
//	public static void main(String[] args) {
//		Donation d = new Donation("1","1",1,"2022-07-11T14:38:56.984Z");
//		System.out.println(d.getDate());
//		
//	}

}
