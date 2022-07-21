import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class DataManager_attemptLogin_Test {

	@Test
	public void testSuccessfulLogin() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				if(resource=="/findOrgByLoginAndPassword") {
				return "{\"status\":\"success\","
						+ "\"data\":{\"_id\":\"62cc2b599397e92ec0263d34\",\"login\":\"org1\",\"password\":\"123\",\"name\":\"org1\",\"description\":\"organization 1\","
						+ "\"funds\":[{\"target\":1000,\"_id\":\"62cc35f79397e92ec0263d89\",\"name\":\"test\",\"description\":\"test\",\"org\":\"62cc2b599397e92ec0263d34\","
						+ "\"donations\":[{\"_id\":\"62cc36009397e92ec0263d90\",\"contributor\":\"62cc35809397e92ec0263d7b\",\"fund\":\"62cc35f79397e92ec0263d89\",\"date\":\"2022-07-11T14:38:56.984Z\",\"amount\":100,\"__v\":0}],\"__v\":0}],\"__v\":0}}";
				}
				
				else if (resource=="/findContributorNameById") {
					return "{\"status\":\"success\",\"data\":\"ctb1\"}";
				}
				else return null;
			}
			
		}
		);

		Organization o = dm.attemptLogin("org1", "123");
		
		assertNotNull(o);
		assertEquals("organization 1", o.getDescription());
		assertEquals("62cc2b599397e92ec0263d34", o.getId());
		assertEquals("org1", o.getName());
		Fund f = o.getFunds().get(0);
		assertEquals(1000, f.getTarget());
		assertEquals("test", f.getDescription());
		assertEquals("62cc35f79397e92ec0263d89", f.getId());
		assertEquals("test", f.getName());
		Donation d=o.getFunds().get(0).getDonations().get(0);
		assertEquals("ctb1", d.getContributorName());
		assertEquals(100, d.getAmount());
		assertEquals("July 11, 2022", d.getDate());
//		assertEquals("2022-07-11T14:38:56.984Z", d.getDate());
		
		Organization o1 = dm.attemptLogin("org1", "123");
	}
	
	@Test
	public void testFailedLogin() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\": \"login failed\"}";

			}
			
		}
		);

		Organization o = dm.attemptLogin("org1", "234");
		
		assertNull("failed to login.",o);
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void testErrorLogin() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return "{\"status\": \"error\", \"data\" : \"err\"}";

			}
			
		}
		);

		Organization o = dm.attemptLogin("", "sfgdsger");
		
//		assertNull("failed to login.",o);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionErrorInLogin() {

		DataManager dm = new DataManager(new WebClient("localhost", 3001) 
		{
			
			@Override
			public String makeRequest(String resource, Map<String, Object> queryParams) {
				return null;
			}
			
		}
		);

		Organization o = dm.attemptLogin(null, null);
		
//		assertNull("encountered an exception error.",o);
		
	}
	
//	@Test
//	public void testLogin() {
//
//		DataManager dm = new DataManager(new WebClient("localhost", 3001));
//
//		Organization o = dm.attemptLogin("org1", "123");
//		System.out.println(o);
//		
//		
//	}

}
